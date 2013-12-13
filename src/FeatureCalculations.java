import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/1/13
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
/*
* Filename: FeatureCalculations.java
* This class takes an input file and calculates features to be used for
* event classification of raw data
 */
public class FeatureCalculations {

    //frames rate for collected data
    public static final int FPS = 32;
    //seconds per window of feature calculation
    public static final int SPW = 3;
    //frames per window of feature calculation
    public static final int FPW = FPS*SPW;
    //number of directions for each sensor (X,Y,Z)
    public static final int numDirections = 3;
    //maximum frequency for FFT calculation
    public static final int maxFreq = 16;
    //file name to pull raw data from


        //test commit

    /*
    * Indexes for each direction
    * 0 - X, 1 - Y, 2 - Z
    * 0 - roll, 1 - pitch, 2 - yaw
    */

    //Arraylists to store mean, variance, FFT for each type of sensor
    //accelerometer, gyrometer, magnetometer, phone rotation
    //mean
    public static ArrayList<Double>[] meanAccelList;
    public static ArrayList<Double> meanGyroList[];
    public static ArrayList<Double> meanMagnetList[];
    public static ArrayList<Double> meanRotateList[];

    //fourier transforms
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierAccelList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierGyroList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierMagnetList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierRotateList;

    //variance
    public static ArrayList<Double> varAccelList[];
    public static ArrayList<Double> varGyroList[];
    public static ArrayList<Double> varMagnetList[];
    public static ArrayList<Double> varRotateList[];

    //Arraylists to get the input raw data (+1 for time arraylist for each sensor)
    public static ArrayList<Double> inAccelList[];
    public static ArrayList<Double> inGyroList[];
    public static ArrayList<Double> inMagnetList[];
    public static ArrayList<Double> inRotateList[];

    //current window of data that is being processed
    public static ArrayList<Double> currAccelWindow;
    public static ArrayList<Double> currGyroWindow;
    public static ArrayList<Double> currMagnetWindow;
    public static ArrayList<Double> currRotateWindow;

    //arraylist of all the classifications at each time
    public static ArrayList<String> classifications;

    /*
    * Method to calculate various features for raw input data
    * @param filename
     */
    public static void CalculateFeatures(String fileName) throws IOException{

        //Arraylists to store mean, variance, FFT for each type of sensor
        //numdirections = 3 for each sensor
        //mean
        meanAccelList = new ArrayList[numDirections];
        meanGyroList = new ArrayList[numDirections];
        meanMagnetList = new ArrayList[numDirections];
        meanRotateList = new ArrayList[numDirections];

        //fourier transform
        fourierAccelList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierGyroList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierMagnetList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierRotateList = new ArrayList<ArrayList<ArrayList<Double>>>();

        //variance
        varAccelList = new ArrayList[numDirections];
        varGyroList = new ArrayList[numDirections];
        varMagnetList = new ArrayList[numDirections];
        varRotateList = new ArrayList[numDirections];

        //Arraylists to get the input raw data (+1 for time arraylist for each sensor)
        inAccelList = new ArrayList[numDirections+1];
        inGyroList = new ArrayList[numDirections+1];
        inMagnetList = new ArrayList[numDirections+1];
        inRotateList = new ArrayList[numDirections+1];

        //current window of data that is being processed
        currAccelWindow = new ArrayList<Double>();
        currGyroWindow = new ArrayList<Double>();
        currMagnetWindow = new ArrayList<Double>();
        currRotateWindow = new ArrayList<Double>();

        //arraylist of each classification of each timestamp
        classifications = new ArrayList<String>();

        //create a data object that will pull all the required raw data
        //readfile reads the info from the input file
        Data rawData = (ReadFile.readfile(fileName));

        //set all the raw data from data file
        //input accelerometer: X, Y, Z, timestamp
        inAccelList[0] = rawData.getAccelerometerXData();
        inAccelList[1] = rawData.getAccelerometerYData();
        inAccelList[2] = rawData.getAccelerometerZData();
        inAccelList[3] = rawData.getAccelTime();

        //input gyrometer data: X, Y, Z, timestamp
        inGyroList[0] = rawData.getGyroscopeXData();
        inGyroList[1] = rawData.getGyroscopeYData();
        inGyroList[2] = rawData.getGyroscopeZData();
        inGyroList[3] = rawData.getGyroTime();
        //System.out.println(inGyroList[0].size());

        //input magnetometer data: X, Y, Z, timestamp
        inMagnetList[0] = rawData.getMagnetometerXData();
        inMagnetList[1] = rawData.getMagnetometerYData();
        inMagnetList[2] = rawData.getMagnetometerZData();
        inMagnetList[3] = rawData.getMagnetTime();
        //System.out.println(inMagnetList[0].size());

        //input rotation data: X, Y, Z, timestamp
        inRotateList[0] = rawData.getRollData();
        inRotateList[1] = rawData.getPitchData();
        inRotateList[2] = rawData.getYawData();
        inRotateList[3] = rawData.getRPYTime();


        //length (number of entries) of each type of sensor data
        int accelLength = inAccelList[0].size();
        int gyroLength = inGyroList[0].size();
        int magnetLength = inMagnetList[0].size();
        int rotateLength = inRotateList[0].size();
        int timeLength = inRotateList[0].size();
        //System.out.println(timeLength);

        int i, j, k, n;
        //for loop to go through x, y, z, directions for each sensor
        for(i = 0; i < numDirections; i++){

            //initialize 2D arraylists
            meanAccelList[i] = new ArrayList<Double>();
            meanGyroList[i] = new ArrayList<Double>();
            meanMagnetList[i] = new ArrayList<Double>();
            meanRotateList[i] = new ArrayList<Double>();
            varAccelList[i] = new ArrayList<Double>();
            varGyroList[i] = new ArrayList<Double>();
            varMagnetList[i] = new ArrayList<Double>();
            varRotateList[i] = new ArrayList<Double>();
            fourierAccelList.add(new ArrayList<ArrayList<Double>>());
            fourierGyroList.add(new ArrayList<ArrayList<Double>>());
            fourierMagnetList.add(new ArrayList<ArrayList<Double>>());
            fourierRotateList.add(new ArrayList<ArrayList<Double>>());

            //initialize 3D arraylists for fourier transforms
            //data, 3 directions, 16 frequencies
            for(n = 0; n < maxFreq; n++){
            	fourierAccelList.get(i).add(new ArrayList<Double>());
            	fourierGyroList.get(i).add(new ArrayList<Double>());
            	fourierMagnetList.get(i).add(new ArrayList<Double>());
            	fourierRotateList.get(i).add(new ArrayList<Double>());
            }

            //go through the length of the accel data
            for(j = 0; j < (accelLength - FPW); j++){

                //get 32 frames at a time to process with a step size of 1 (overlapping windows)
                for(k = 0; k < FPW; k++){
                    //set the current processing window
                    currAccelWindow.add(k, inAccelList[i].get(j+k));
                }

                //add the calculated mean to the mean and variance lists
                meanAccelList[i].add(j, calcMean(currAccelWindow));
                varAccelList[i].add(j, calcVariance(currAccelWindow, calcMean(currAccelWindow)));

                //get the fourier transforms of maxFreq different frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierAccelList.get(i).get(n).add(j, goertzel(currAccelWindow, n+1, FPS));
                }   
                currAccelWindow.clear();
            }

            //go through length of gyro data
            for(j = 0; j < (gyroLength - FPW); j++){

                for(k = 0; k < FPW; k++){
                    //window for processing
                    currGyroWindow.add(k, inGyroList[i].get(j+k));
                }

                //mean and variance
                meanGyroList[i].add(j, calcMean(currGyroWindow));
                varGyroList[i].add(j, calcVariance(currGyroWindow, calcMean(currGyroWindow)));

                //fourier transforms for 16 frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierGyroList.get(i).get(n).add(j, goertzel(currGyroWindow, n+1, FPS));
                } 
                currGyroWindow.clear();
            }

            //go through magnetometer data
            for(j = 0; j < (magnetLength - FPW); j++){

                for(k = 0; k < FPW; k++){
                    //magnetometer processing window
                    currMagnetWindow.add(k, inMagnetList[i].get(j+k));
                }

                //mean and variance
                meanMagnetList[i].add(j, calcMean(currMagnetWindow));
                varMagnetList[i].add(j, calcVariance(currMagnetWindow, calcMean(currMagnetWindow)));

                //fourier transforms for 16 frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierMagnetList.get(i).get(n).add(j, goertzel(currMagnetWindow, n+1, FPS));
                }
                currMagnetWindow.clear();
            }

            //rotation data
            for(j = 0; j < (rotateLength - FPW); j++){

                for(k = 0; k < FPW; k++){
                    //rotation data window
                    currRotateWindow.add(k, inRotateList[i].get(j+k));
                }

                //mean and varience
                meanRotateList[i].add(j, calcMean(currRotateWindow));
                varRotateList[i].add(j, calcVariance(currRotateWindow, calcMean(currRotateWindow)));
                //fourier transforms for 16 frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierRotateList.get(i).get(n).add(j, goertzel(currRotateWindow, n+1, FPS));
                } 
                currRotateWindow.clear();
            }
        }

        //string for output information
        String output;

        //the total number of timestamps in the data file
        timeLength = meanGyroList[0].size();

        //for loop for each time input in the data file
        for(i = 0; i < (timeLength); i++){

            //add all of the following features to each line in the data file
            classifications.add(i, EventClassifier.Classify(meanAccelList[0].get(i), meanAccelList[1].get(i), meanAccelList[2].get(i), 
            		meanGyroList[0].get(i), meanGyroList[1].get(i), meanGyroList[2].get(i), meanMagnetList[0].get(i),
                    meanMagnetList[1].get(i), meanMagnetList[2].get(i), meanRotateList[0].get(i), meanRotateList[1].get(i), meanRotateList[2].get(i),
                    fourierAccelList.get(0).get(2).get(i), fourierAccelList.get(0).get(14).get(i), fourierAccelList.get(0).get(15).get(i),
                    fourierAccelList.get(1).get(1).get(i), fourierAccelList.get(1).get(2).get(i), 
                    fourierAccelList.get(1).get(5).get(i), fourierAccelList.get(1).get(7).get(i), 
                    fourierAccelList.get(1).get(13).get(i), fourierAccelList.get(1).get(14).get(i), 
                    fourierAccelList.get(2).get(3).get(i), 
                    fourierAccelList.get(2).get(4).get(i), fourierAccelList.get(2).get(6).get(i), fourierAccelList.get(2).get(13).get(i),
                    fourierGyroList.get(0).get(1).get(i), fourierGyroList.get(0).get(2).get(i), fourierGyroList.get(0).get(3).get(i), 
                    fourierGyroList.get(0).get(4).get(i), fourierGyroList.get(0).get(5).get(i),
                    fourierGyroList.get(0).get(6).get(i), fourierGyroList.get(0).get(9).get(i), fourierGyroList.get(0).get(12).get(i),
                    fourierGyroList.get(1).get(0).get(i), fourierGyroList.get(1).get(1).get(i), fourierGyroList.get(1).get(2).get(i), 
                    fourierGyroList.get(1).get(5).get(i), fourierGyroList.get(1).get(6).get(i), fourierGyroList.get(1).get(10).get(i),
                    fourierGyroList.get(2).get(0).get(i), fourierGyroList.get(2).get(3).get(i), 
                    fourierGyroList.get(2).get(5).get(i), fourierGyroList.get(2).get(6).get(i), fourierGyroList.get(2).get(9).get(i),
                    fourierMagnetList.get(0).get(0).get(i), fourierMagnetList.get(2).get(7).get(i),
                    fourierRotateList.get(0).get(0).get(i), fourierRotateList.get(1).get(12).get(i), 
                    fourierRotateList.get(2).get(0).get(i), fourierRotateList.get(2).get(15).get(i), 
                    varAccelList[1].get(i), varAccelList[0].get(i),
                    varAccelList[2].get(i), varGyroList[0].get(i), varGyroList[1].get(i), varGyroList[2].get(i),
                    varMagnetList[0].get(i), varMagnetList[1].get(i), varMagnetList[2].get(i), 
                    varRotateList[0].get(i), varRotateList[1].get(i), varRotateList[2].get(i)));

        }
    }

    //get the list of all the classifications for the data file
    public static ArrayList<String> getClassifications(){
        return classifications;
    }

    //get the list of all the timestamps from the data file
    public static ArrayList<Double> getTimestamps(){
        return inAccelList[3];
    }

    //calculate the mean of the input arraylist
    public static double calcMean(ArrayList<Double> d){
        double total = 0;
        double mean = 0;
        int i=0;

        if(d.isEmpty()){
            return 0;
        }
        while(i<d.size()){
            total += d.get(i);
            i++;
        }
        mean = total/d.size();

        return mean;
    }

    //calculate the variance
    public static double calcVariance(ArrayList<Double> ar, double mean){
        int i = 0;
        double variance = 0;
        double totdiffsq = 0;
        double[] diffsq = new double[ar.size()];
        for(i=0; i<ar.size(); i++){
            totdiffsq += Math.pow((ar.get(i)-mean), 2);
            //System.out.println(totdiffsq + "\n");
        }
        variance = totdiffsq/ar.size();
        return variance;
    }

    //calculate the FFT for each of the frequencies
    private static double goertzel(ArrayList<Double> Data, double freq, double sr)
    {
        double s_prev = 0;
        double s_prev2 = 0;
        double coeff = 2 * Math.cos((2 * Math.PI * freq) / sr);
        double s;
        for (int i = 0; i < Data.size(); i++) {
            double sample = Data.get(i);
            s = sample + coeff * s_prev - s_prev2;
            s_prev2 = s_prev;
            s_prev = s;
        }
        double power = s_prev2 * s_prev2 + s_prev * s_prev - coeff * s_prev2 * s_prev;
        return power;
    }

}
