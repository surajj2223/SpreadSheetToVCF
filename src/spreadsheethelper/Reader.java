package spreadsheethelper;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Reader {

    private HSSFSheet mSpreadsheet;
    private HashMap<String, Integer> cellNameMap;
    private int startCellNumber;
    private String[] columnNames; // Name, Contact, Email

    public Reader(File file) throws  IOException {
        FileInputStream fin = new FileInputStream(file);
        HSSFWorkbook mWorkbook = new HSSFWorkbook(fin);
        mSpreadsheet = mWorkbook.getSheetAt(0);
        cellNameMap = new HashMap<>();
    }

    public void initialize(int startCellNumber, String[] columnNames){
        HSSFRow row = mSpreadsheet.getRow(0);
        for (Cell it : row) {
            cellNameMap.put(it.getStringCellValue(),it.getColumnIndex());
        }
        this.columnNames = columnNames;
        this.startCellNumber = startCellNumber;
    }

    public HSSFSheet getmSpreadsheet() {
        return mSpreadsheet;
    }

    public void convertToVCF(final ResultListener listener) {
        new Thread(() -> {
            File[] files  = new File[1];
            try {
                files[0] = getVCF(startCellNumber, false);
                listener.vcfResultFiles(files);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private File getVCF(int startCell, boolean considerEnd) throws IOException {
        ArrayList<Contact> contactList = new ArrayList<>();
        if(considerEnd){
            for(int i = startCell; i<=501; i++){
                contactList.add(makeContact(i));
            }
        } else {
            for(int i = startCellNumber; i<=mSpreadsheet.getLastRowNum(); i++) {
                contactList.add(makeContact(i));
            }
        }
        return makeVCF(contactList);
    }

    private Contact makeContact(int cellNumber) {
        int nameColumn = cellNameMap.get(columnNames[0]);
        int contactColumn = cellNameMap.get(columnNames[1]);
        HSSFRow currentRow = mSpreadsheet.getRow(cellNumber);
        String name = currentRow.getCell(nameColumn).getStringCellValue();
        String contactNumber = currentRow.getCell(contactColumn).getStringCellValue();
        return new Contact(name, contactNumber);
    }

    private File makeVCF(ArrayList contactsList) throws IOException {
        Iterator<Contact> it = contactsList.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while(it.hasNext()){
            Contact thisContact = it.next();
            VCard vCard = new VCard();
            vCard.addFormattedName(new FormattedName(thisContact.getName()));
            vCard.addTelephoneNumber(new Telephone(thisContact.getContact()));
            stringBuilder.append("\n").append(Ezvcard.write(vCard).version(VCardVersion.V3_0).go());
        }
        return makeFile(stringBuilder.toString());
    }

    private File makeFile(String vcfString) throws IOException {
        File file = new File("001.vcf");
        file.createNewFile();
        FileOutputStream fout = new FileOutputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fout));
        dataOutputStream.writeBytes(vcfString);
        dataOutputStream.close();
        return file;
    }


}
