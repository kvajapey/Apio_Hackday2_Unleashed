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


    public static void main(String args[]){

        String fileName;
        double approach, entry, ignitionOn, ignitionOff, exit;


        Scanner scan;
        scan = new Scanner(System.in);

        System.out.print("Please print the filename of input data: ");
        fileName = scan.next();
        System.out.print("Please print the approach time of input data (-1 for none): ");
        approach = scan.nextDouble();
        System.out.print("Please print the entry time of input data (-1 for none): ");
        entry = scan.nextDouble();
        System.out.print("Please print the ignition on time of input data (-1 for none): ");
        ignitionOn = scan.nextDouble();
        System.out.print("Please print the ignition off time of input data (-1 for none): ");
        ignitionOff = scan.nextDouble();
        System.out.print("Please print the exit time of input data (-1 for none): ");
        exit = scan.nextDouble();

        FeatureCalculations fCalc = new FeatureCalculations(fileName, approach, entry, ignitionOn, ignitionOff, exit);

        classifications = fCalc.getClassifications();
        timestamps = fCalc.getTimestamps();

        int window = (FeatureCalculations.FPS/2);

        if(window % 2 == 0){
            window++;
        }

        filteredClassifications = ClassificationFilter.ModeFilter(classifications, window);

    }

    public static boolean isClassificataionGood(ArrayList<String> classified){

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

            if(currEvent.equals("approach")){
                approach.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals("entry")){
                entry.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals("ignition on")){
                ignitionOn.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals("ignition off")){
                ignitionOff.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

            else if(currEvent.equals("exit")){
                exit.add((int) Math.round(timestamps.get(i+(typeCount/2))));
            }

        }
    }
}
