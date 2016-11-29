import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by IRCT on 11/18/2016.
 * This is main activity awhere the interaction takes place
 */
public class Main {
    //Hold message from initial file checker
    private static String fileScanMessage;
    //checks if file is readable
    private static boolean isValid = true;
    //stores user input
    private static String filePathGiven;
    //remembers an input that is of a valid file
    private static String filePathValid;
    //holds a list of websites from FileHelper
    private static List<Website> listOfValidWebsites;
    //time measure variable start,end & total
    private static long startTime;
    private static long endTime;
    private static long totalTime;
    //stores the amount of lines in the provided file
    private static int count;

    public static void main(String[] args) throws IOException {
        //Ask user for a file input
        System.out.println("***Search Console*** Please, provide a filepath for search, to configure website library:\n");
         //Initialize Scanner
         Scanner console = new Scanner(System.in);
         //write input to variable
         filePathGiven = console.nextLine();
         //get return string -message, by running method initialFileAnalysis
         String message = initialFileAnalysis(filePathGiven);
         //print returned message
         System.out.print(message);

         //If file is Invalid go trough this loop
         while (!isValid) {
             //inform user and ask for file again
            System.out.print("***Search Console*** Are you sure following file path:   "
                    + filePathGiven + "   ,is a correct path? :)\n");
            System.out.print("***Search Console*** Here, try again!: \n");
             //listen on next line and re-write filePathGiven
            filePathGiven = console.nextLine();
             //get return string -message, by running method initialFileAnalysis
             message = initialFileAnalysis(filePathGiven);
             //print message
            System.out.print(message);
        }
        //Pass FilePathValid to the FileHelper Class and Store returned list in the listOfAllWebsites
        listOfValidWebsites = FileHelper.providedFile(filePathValid);
         //create inverted index, using hashmap
         InvertedIndex index = new InvertedIndex(new HashMap<>());
         // Inform user of what is happening
         System.out.println("\n***Search Console*** Starting the process of building searchable list...");
         //check start time
         startTime=System.nanoTime();
         //initialize buildList method and provide list to populate
         index.buildList(listOfValidWebsites);
         //check end time
         endTime=System.nanoTime();
         //calculate total time
         totalTime= (endTime - startTime)/100000;
         //informs user about build time result
         System.out.println("***Search Console*** The list has been built in: " + totalTime + " ms...");
         // awaits command line input from user, to search
         System.out.print("\n   Type for search: [string] or quit: [***exit]\n\n");
         // while loop to go trough user input and perform search, edit, quit, etc.
         while (console.hasNext()) {
            //store search input from scanner
            String searchInput = console.next();
            // to load code from a different location.
            if (searchInput.equals("***exit")) {
                System.out.println(" ***Search Console*** User calls for ***exit \n"
                        + "*** Shutting Down Application ***\n");
                return;
            }
            //Finds if the user input can be found in list, using shared interface method find keyword
            List<Website> queryMatch = index.findKeyword(searchInput);
             if (queryMatch.isEmpty()) {
                System.out.println("***Search Console*** Unfortunately, there are no records for:  "
                        + searchInput + " in our database! \n");
             } else{
                 int i = 1;
                 //for each website object where there was sucess with query match
                 for (Website website : queryMatch) {
                     // print out the number
                     System.out.println("Record number: " + i);
                     //getTitle and Url of a website object to print for user
                     System.out.println("Website Title:" + website.getTitle()
                            + "\n Website's URL: " + website.getUrl() + "\n\n");
                    i++;
                }
            }
        }
    }
//This method checks the validity file and catches all Input Stream Exceptions to secure the application from just failing when there is wrong input
    private static String initialFileAnalysis(String filePath) {
        //reset count back to 0
        count = 0;
        //try block
            try {
                //initialize FileReader with FilePath
                FileReader fr = new FileReader(filePath);
                //initialize scanner to read file
                Scanner scan = new Scanner(fr);
                //while loop to count lines
                while (scan.hasNextLine()) {
                    scan.nextLine();
                    //add +1 for each line in file
                    count++;
                }

                if (count == 0) {
                    isValid = false;
                } else {
                    //provide a message if the file is OK
                    fileScanMessage = "***Search Console*** The file you have provided contains:"
                            + count + " lines... Sending file for web analysis!";
                    //set sivalid to be true so the while statement in main doesnt do anything
                    isValid = true;
                    //save provided file path to be then processed by File Reader
                    filePathValid = filePath;

                }
                //close scanner
                scan.close();
            //catch exceptions like FileNotFound
            } catch (IOException e) {
                //informs user about the files issue
                fileScanMessage = "***Search Console*** File not found or could not have been opened & "
                        + count + " lines of text were found :'(\n";
                //set isValid false, main will do while loop, to ask user again for file path and repeat until conditions are met
                isValid = false;

            }
            //give back the file status message
            return fileScanMessage;
        }
}


