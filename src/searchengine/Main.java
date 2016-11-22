import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IRCT on 11/18/2016.
 * This is main activity where the interaction takes place
 */


public class Main {
    //variable to count line in file ?is File empty?
   static int count = 0;

    public static void main(String[] args) throws IOException {
        //defined bool to determine if txt file is empty
        boolean empty = true;

        //Hold the path for file
        String filePath;

        //asks user for an input
        System.out.println("*Console:> Please, provide file\n");

        //while loop to check validity of a file
            while (true) {
            //initialize variable sc - scanner for user input
            Scanner sc = new Scanner(System.in);
                //variable holding path value of file provided
                filePath = sc.next();
            //File input
            File inputFile = new File(filePath);

            //Check the file validity
            if (filePath.length() > 0) {
                // try processing files else, terminate the programme
                try {
                    count = initialCheckFile(filePath);
                    System.out.println("Console:> The file " + filePath
                            + " contains " + count + " lines");

                    while (empty = true) {
                        if ((count == 0)) {
                            System.out.println("Console:> The file " + filePath + " you have provided is empty. \n"
                                    + " Please, provide new file name: ");
                            filePath = sc.next();
                            count = initialCheckFile(filePath);
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Console:> The file " + filePath
                            + " could not be opened.");
                    System.out.println(" Please, provide new file: ");
                    filePath = sc.next();
                    initialCheckFile(filePath);
                }

                System.out.println(" Console:> Building Lists...");
                List<Website> listOfAllWebsites = FileHelper.providedFile(filePath);

            }
            }
    }

    private static int initialCheckFile(String filePath) throws FileNotFoundException {
        count = 0;
        FileReader fr = new FileReader(filePath);
        Scanner s = new Scanner(fr);
        while (s.hasNextLine()) {
            s.nextLine();
            count++;
        }

        s.close();

        return count;
    }
}
