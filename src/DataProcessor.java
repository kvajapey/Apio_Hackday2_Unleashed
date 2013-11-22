import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/22/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataProcessor {

    public static ArrayList<String> classifications, filteredClassifications;
    public static ArrayList<Double> timestamps;

    public static ArrayList<Integer> approach = new ArrayList<Integer>();
    public static ArrayList<Integer> entry = new ArrayList<Integer>();
    public static ArrayList<Integer> ignitionOn = new ArrayList<Integer>();
    public static ArrayList<Integer> ignitionOff = new ArrayList<Integer>();
    public static ArrayList<Integer> exit = new ArrayList<Integer>();


    public static void main(String args[]) throws IOException {

        String fileName;
        //double approach, entry, ignitionOn, ignitionOff, exit;


        Scanner scan;
        scan = new Scanner(System.in);

        System.out.print("Please print the filename of input data: ");
        fileName = scan.next();
        /*System.out.print("Please print the approach time of input data (-1 for none): ");
        approach = scan.nextDouble();
        System.out.print("Please print the entry time of input data (-1 for none): ");
        entry = scan.nextDouble();
        System.out.print("Please print the ignition on time of input data (-1 for none): ");
        ignitionOn = scan.nextDouble();
        System.out.print("Please print the ignition off time of input data (-1 for none): ");
        ignitionOff = scan.nextDouble();
        System.out.print("Please print the exit time of input data (-1 for none): ");
        exit = scan.nextDouble();  */

        FeatureCalculations fCalc = new FeatureCalculations(fileName);

        classifications = fCalc.getClassifications();
        timestamps = fCalc.getTimestamps();

        int window = (FeatureCalculations.FPS/2);

        //make window odd
        if(window % 2 == 0){
            window++;
        }

        filteredClassifications = ClassificationFilter.ModeFilter(classifications, window);

        //if the file is good then write info to new file
        if(isClassificataionGood(filteredClassifications)){
            FileWriter fstream = new FileWriter("File_Classifications.csv");
            BufferedWriter wr = new BufferedWriter(fstream);

            String output;

            output = "Filename,Approach Time,Entry Time,Ignition on,Ignition off,Exit Time";
            wr.write(output);

            output = fileName + "," + approach.get(0) + "," + entry.get(0) + "," + ignitionOn.get(0) + "," +
                    ignitionOff.get(0) + "," + exit.get(0);

            wr.write(output);

            wr.close();
        }

    }

    public static boolean isClassificataionGood(ArrayList<String> classified){

        boolean isGood = false;
        findEventTimes(classified);

        if(approach.size() == 1 && entry.size() == 1 && ignitionOn.size() == 1 && ignitionOff.size() == 1 && exit.size() == 1){
            if(approach.get(0) < entry.get(0) && entry.get(0) < ignitionOn.get(0) && ignitionOn.get(0) < ignitionOff.get(0) &&
            ignitionOff.get(0) < exit.get(0)){
                isGood = true;
            }

        }

        return isGood;
    }

    public static void findEventTimes(ArrayList<String> classified){

        int i, typeCount;

        String currEvent;

        for(i = 0; i < timestamps.size(); i++){
            currEvent = classified.get(i);
            typeCount = 0;
            while(currEvent.equals(classified.get(i))){
                typeCount++;
                i++;
            }

            if(currEvent.equals(DataUtils.APPROACH_EVENT)){
                approach.add((int) Math.round(timestamps.get(i + (typeCount / 2))));
            }

            else if(currEvent.equals(DataUtils.ENTER_EVENT)){
                entry.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.ON_EVENT)){
                ignitionOn.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.OFF_EVENT)){
                ignitionOff.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.EXIT_EVENT)){
                exit.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }
        }
    }
}
