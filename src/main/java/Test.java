import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Author: Spikerman
 * Created Date: 11/4/17
 */


//file format testing, checking if each record is ended with /n and if each attribute is splitted by /t
public class Test {


    public static void main(String[] args) {
        try {
            File file = new File("tweets.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //StringBuffer stringBuffer = new StringBuffer();
            String line;
            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split("\\t");
                System.out.println(count + ":  " + line);

                count++;
            }
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
