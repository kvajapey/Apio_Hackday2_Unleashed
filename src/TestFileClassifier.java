import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 12/8/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */

/* This is the main file used for our raw data classifier
 * Reads input folder of raw collected data files
 * output a folder of the classified files and a list of names of good and bad files
 *
 */
public class TestFileClassifier {

    //inputs for writing the CSV files with event timestamps
    public static FileWriter fstream;
    public static BufferedWriter wr;

    public static void main(String args[]) {

        File folder;
        String inputName, extension;
        ArrayList<String> files = new ArrayList<String>();

        //writer for all the good files
        FileWriter goodFstream;
        BufferedWriter goodWr;
        //writer for all the bad files
        FileWriter badFstream;
        BufferedWriter badWr;

        DataProcessor dataProcessor;

        try{
            Scanner scan;
            scan = new Scanner(System.in);

            goodFstream = new FileWriter("Good_Files.txt");
            goodWr = new BufferedWriter(goodFstream);
            badFstream = new FileWriter("Bad_Files.txt");
            badWr = new BufferedWriter(badFstream);
            fstream = new FileWriter("File_Classifications.csv");
            wr = new BufferedWriter(fstream);

            //get input files or folders
            System.out.print("Please input folder name to read raw data files from, or a file name: ");
            inputName = scan.next();

            int i = inputName.lastIndexOf(".");
            if(i > 0){
                extension = inputName.substring(i+1);
            }
            else{
                extension = "folder";
            }

            String output;

            output = "Filename,Approach Time,Entry Time,Ignition on,Ignition off,Exit Time\n";
            wr.write(output);

            //check if the input name is a text file
            if(extension.equals("txt")){
                String fileName = inputName;
                dataProcessor = new DataProcessor();
                dataProcessor.runProcessor(fileName);
            }
            //if not a file the it is a folder
            else{
                String folderName = inputName;
                folder = new File(folderName);

                //go through all the files in the folder
                for(final File fileEntry: folder.listFiles()){

                    files.add(fileEntry.getName());
                }

                //run data processor on each file
                for(String fileName:files){

                    dataProcessor = new DataProcessor();
                    dataProcessor.runProcessor(folderName + "/" + fileName);

                    if(dataProcessor.getClassification()){
                        goodWr.append(fileName + "\n");
                    }
                    else{
                        badWr.append(fileName + "\n");
                    }

                }
            }


            wr.close();
            goodWr.close();
            badWr.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
