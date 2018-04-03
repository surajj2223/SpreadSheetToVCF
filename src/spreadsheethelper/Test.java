package spreadsheethelper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

public class Test implements ResultListener {


    public void doConversion() throws IOException, InvalidFormatException {
        File spreadSheetFile = new File("spreadsheet.xls");
        Reader reader = new Reader(spreadSheetFile);
        reader.initialize(3,new String[]{"V", "AB"});
        reader.convertToVCF(this);
    }

    @Override
    public void vcfResultFiles(File[] files) {
        for(File file: files) {
            System.out.println("File - "+file.getName()+" is created at - "+file.getAbsolutePath());
        }
    }
}
