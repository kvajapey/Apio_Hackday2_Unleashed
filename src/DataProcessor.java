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

/*
 * Filename: DataProcessor
 * This file take a single input file and classifies it
 * Runs the feature classifier in order to determine good or bad
 *
 */
public class DataProcessor {

    //list of filtered and unfiltered classifications
    public ArrayList<String> classifications, filteredClassifications;
    //list of timestamps
    public ArrayList<Double> timestamps;

    //event lists
    public ArrayList<Integer> approach = new ArrayList<Integer>();
    public ArrayList<Integer> entry = new ArrayList<Integer>();
    public ArrayList<Integer> ignitionOn = new ArrayList<Integer>();
    public ArrayList<Integer> ignitionOff = new ArrayList<Integer>();
    public ArrayList<Integer> exit = new ArrayList<Integer>();

    //classification boolean
    public boolean classification;

    //method to run processor on the raw data
    public void runProcessor(String fileName) throws IOException {

        //calculate the features used for event classification
        FeatureCalculations.CalculateFeatures(fileName);

        //get list of the classifications
        classifications = FeatureCalculations.getClassifications();
        //get list of timestamps
        timestamps = FeatureCalculations.getTimestamps();

        //size of filter window
        int window = (FeatureCalculations.FPS);

        //make window odd
        if(window % 2 == 0){
            window++;
        }

        //filer the classifications using a Mode filter
        filteredClassifications = ClassificationFilter.ModeFilter(classifications, window);

        //check if the classifications are good
        classification = isClassificataionGood(filteredClassifications);

        //if the file is good then write info to new file
        if(classification){


            String output;

            output = fileName + "," + approach.get(0) + "," + entry.get(0) + "," + ignitionOn.get(0) + "," +
                    ignitionOff.get(0) + "," + exit.get(0) + "\n";

            //write the file information to a CSV file with the timestamps for each event
            TestFileClassifier.wr.append(output);

        }
        //if it is a bad file get the classifications
        else{
        	System.out.printf("Bad File approaches: %d entries: %d ignition ons: %d ignitionoffs: %d exits: %d\n", 
        						approach.size(), entry.size(), ignitionOn.size(), ignitionOff.size(), exit.size());
        	for(int k = 0; k < approach.size(); k++){
        		System.out.printf("Approach time %d: %d\n", approach.size(), approach.get(k));
        	}
        	for(int k = 0; k < entry.size(); k++){
        		System.out.printf("Entry time %d: %d\n", entry.size(), entry.get(k));
        	}
        	for(int k = 0; k < ignitionOn.size(); k++){
        		System.out.printf("Ignition on time %d: %d\n", ignitionOn.size(), ignitionOn.get(k));
        	}
        	for(int k = 0; k < ignitionOff.size(); k++){
        		System.out.printf("Ignition off time %d: %d\n", ignitionOff.size(), ignitionOff.get(k));
        	}
        	for(int k = 0; k < exit.size(); k++){
        		System.out.printf("Exit time %d: %d\n", exit.size(), exit.get(k));
        	}
        }

    }

    //see if the file is good or bad
    public boolean getClassification(){
        return classification;
    }

    //check if the file is useful
    public boolean isClassificataionGood(ArrayList<String> classified){

        boolean isGood = false;
        System.out.println("timestamp size:" + timestamps.size());
        System.out.println("classified size:" + classified.size());
        findEventTimes(classified);

        //criteria for a good file
        //if there is more than one of each classification the file is not useful
        if(approach.size() == 1 && entry.size() == 1 && ignitionOn.size() == 1 && ignitionOff.size() == 1 && exit.size() == 1){
            if(approach.get(0) < entry.get(0) && entry.get(0) < ignitionOn.get(0) && ignitionOn.get(0) < ignitionOff.get(0) &&
            ignitionOff.get(0) < exit.get(0)){
                isGood = true;
            }

        }

        return isGood;
    }

    //find the times of each of the events
    public void findEventTimes(ArrayList<String> classified){

        int i, typeCount;

        String currEvent;

        //store all the found events in each respective array
        for(i = 0; i < classified.size() && i < timestamps.size(); i++){
            currEvent = classified.get(i);
            typeCount = 0;
            while((i < classified.size()) && currEvent.equals(classified.get(i))){
                typeCount++;
                i++;
            }

            i--;

            if(currEvent.equals(DataUtils.APPROACH_EVENT)){
                approach.add((int) Math.round(timestamps.get(i - (typeCount / 2))));
            }

            else if(currEvent.equals(DataUtils.ENTER_EVENT)){
                entry.add((int) Math.round(timestamps.get(i - (typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.ON_EVENT)){
                ignitionOn.add((int) Math.round(timestamps.get(i - (typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.OFF_EVENT)){
                ignitionOff.add((int) Math.round(timestamps.get(i - (typeCount/2))));
            }

            else if(currEvent.equals(DataUtils.EXIT_EVENT)){
                exit.add((int) Math.round(timestamps.get(i - (typeCount/2))));
            }
        }
    }
}
