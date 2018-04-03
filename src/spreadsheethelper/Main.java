package spreadsheethelper;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.FormattedName;
import ezvcard.property.Telephone;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;

public class Main {
    public static void main(String ...a){
//        VCard vCard = new VCard();
//        vCard.addFormattedName(new FormattedName("Suraj Malviya"));
//        vCard.addTelephoneNumber(new Telephone("+91-7049994945"));
//        String str = Ezvcard.write(vCard).version(VCardVersion.V3_0).go();
//        System.out.println(str);
//        File file = new File("001.vcf");
//        try {
//            file.createNewFile();
//
//            FileOutputStream fout = new FileOutputStream(file);
//            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fout));
//            dataOutputStream.writeBytes(str);
//            dataOutputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Test test = new Test();
        try {
            test.doConversion();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

    }
}
