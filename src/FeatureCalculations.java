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
public class FeatureCalculations {

    //frames rate for collected data
    public static final int FPS = 32;
    //number of directions for each sensor (X,Y,Z)
    public static final int numDirections = 3;
    //maximum frequency for FFT calculation
    public static final int maxFreq = 16;
    //file name to pull raw data from


        //test commit

        /*
        * 0 - X, 1 - Y, 2 - Z
        * 0 - roll, 1 - pitch, 2 - yaw
        */

    //Arraylists to store mean, variance, FFT for each type of sensor
    public static ArrayList<Double>[] meanAccelList;
    public static ArrayList<Double> meanGyroList[];
    public static ArrayList<Double> meanMagnetList[];
    public static ArrayList<Double> meanRotateList[];

    public static ArrayList<ArrayList<ArrayList<Double>>> fourierAccelList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierGyroList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierMagnetList;
    public static ArrayList<ArrayList<ArrayList<Double>>> fourierRotateList;

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

    public static ArrayList<String> classifications;

    public static void CalculateFeatures(String fileName) throws IOException{

        //Arraylists to store mean, variance, FFT for each type of sensor
        meanAccelList = new ArrayList[numDirections];
        meanGyroList = new ArrayList[numDirections];
        meanMagnetList = new ArrayList[numDirections];
        meanRotateList = new ArrayList[numDirections];

        fourierAccelList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierGyroList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierMagnetList = new ArrayList<ArrayList<ArrayList<Double>>>();
        fourierRotateList = new ArrayList<ArrayList<ArrayList<Double>>>();

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

        ReadFile reader = new ReadFile();
        Data rawData = (reader.readfile(fileName));

        //set all the raw data from data file
        inAccelList[0] = rawData.getAccelerometerXData();
        inAccelList[1] = rawData.getAccelerometerYData();
        inAccelList[2] = rawData.getAccelerometerZData();
        inAccelList[3] = rawData.getAccelTime();


        inGyroList[0] = rawData.getGyroscopeXData();
        inGyroList[1] = rawData.getGyroscopeYData();
        inGyroList[2] = rawData.getGyroscopeZData();
        inGyroList[3] = rawData.getGyroTime();
        System.out.println(inGyroList[0].size());

        inMagnetList[0] = rawData.getMagnetometerXData();
        inMagnetList[1] = rawData.getMagnetometerYData();
        inMagnetList[2] = rawData.getMagnetometerZData();
        inMagnetList[3] = rawData.getMagnetTime();
        System.out.println(inMagnetList[0].size());


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
            for(n = 0; n < maxFreq; n++){
            	fourierAccelList.get(i).add(new ArrayList<Double>());
            	fourierGyroList.get(i).add(new ArrayList<Double>());
            	fourierMagnetList.get(i).add(new ArrayList<Double>());
            	fourierRotateList.get(i).add(new ArrayList<Double>());
            }
            //go through the length of the accel data
            for(j = 0; j < (accelLength - FPS); j++){

                //get 32 frames at a time to process with a step size of 1 (overlapping windows)
                for(k = 0; k < FPS; k++){
                    currAccelWindow.add(k, inAccelList[i].get(j+k));
                }
                meanAccelList[i].add(j, calcMean(currAccelWindow));
                varAccelList[i].add(j, calcVariance(currAccelWindow, calcMean(currAccelWindow)));
                //get the fourier transforms of maxFreq different frequencies
                for(n = 0; n < maxFreq; n++){
                    fourierAccelList.get(i).get(n).add(j, goertzel(currAccelWindow, n+1, currAccelWindow.size()));
                }   
                currAccelWindow.clear();
            }
            //System.out.println(meanAccelList[i].size());
            //this

            //go through length of gyro data
            for(j = 0; j < (gyroLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currGyroWindow.add(k, inGyroList[i].get(j+k));
                }
                meanGyroList[i].add(j, calcMean(currGyroWindow));
                varGyroList[i].add(j, calcVariance(currGyroWindow, calcMean(currGyroWindow)));
                for(n = 0; n < maxFreq; n++){
                    fourierGyroList.get(i).get(n).add(j, goertzel(currGyroWindow, n+1, currGyroWindow.size()));
                } 
                currGyroWindow.clear();
            }
            //System.out.println(meanGyroList[i].size());

            for(j = 0; j < (magnetLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currMagnetWindow.add(k, inMagnetList[i].get(j+k));
                }
                meanMagnetList[i].add(j, calcMean(currMagnetWindow));
                varMagnetList[i].add(j, calcVariance(currMagnetWindow, calcMean(currMagnetWindow)));
                for(n = 0; n < maxFreq; n++){
                    fourierMagnetList.get(i).get(n).add(j, goertzel(currMagnetWindow, n+1, currMagnetWindow.size()));
                }
                currMagnetWindow.clear();
            }
            //System.out.println(meanMagnetList[i].size());


            for(j = 0; j < (rotateLength - FPS); j++){

                for(k = 0; k < FPS; k++){
                    currRotateWindow.add(k, inRotateList[i].get(j+k));
                }
                meanRotateList[i].add(j, calcMean(currRotateWindow));
                varRotateList[i].add(j, calcVariance(currRotateWindow, calcMean(currRotateWindow)));
                for(n = 0; n < maxFreq; n++){
                    fourierRotateList.get(i).get(n).add(j, goertzel(currRotateWindow, n+1, currRotateWindow.size()));
                } 
                currRotateWindow.clear();
            }
            System.out.println(meanRotateList[i].size());

        }

        String output;

        timeLength = meanGyroList[0].size();

        for(i = 0; i < (timeLength); i++){

            classifications.add(i, EventClassifier.Classify(meanGyroList[1].get(i), meanGyroList[2].get(i), meanMagnetList[0].get(i),
                    meanMagnetList[1].get(i), meanMagnetList[2].get(i), meanRotateList[1].get(i), meanRotateList[2].get(i),
                    fourierAccelList.get(0).get(2).get(i), fourierAccelList.get(0).get(4).get(i), fourierAccelList.get(0).get(8).get(i),
                    fourierAccelList.get(1).get(0).get(i), fourierAccelList.get(1).get(6).get(i), fourierAccelList.get(2).get(6).get(i),
                    fourierGyroList.get(0).get(0).get(i), fourierGyroList.get(0).get(1).get(i), fourierGyroList.get(0).get(3).get(i),
                    fourierGyroList.get(1).get(3).get(i), fourierGyroList.get(2).get(10).get(i), varAccelList[1].get(i),
                    varAccelList[2].get(i), varGyroList[1].get(i), varGyroList[2].get(i), varMagnetList[1].get(i),
                    varMagnetList[2].get(i)));

//            output = "" + inAccelList[3].get(i);
//            for(j = 0; j < numDirections; j++){
//                output += "," + meanAccelList[j].get(i) + "," + meanGyroList[j].get(i) + "," + meanMagnetList[j].get(i) +
//                        "," + meanRotateList[j].get(i) + "," + varAccelList[j].get(i) + "," + varGyroList[j].get(i) +
//                        "," + varMagnetList[j].get(i) + "," + varRotateList[j].get(i);
//                for(k = 0; k < maxFreq; k++){
//                    output += "," + fourierAccelList.get(j).get(k).get(i) + "," + fourierGyroList.get(j).get(k).get(i) + "," +
//                            fourierMagnetList.get(j).get(k).get(i) + "," + fourierRotateList.get(j).get(k).get(i);
//                }
//            }
        }
    }

    public static ArrayList<String> getClassifications(){
        return classifications;
    }

    public static ArrayList<Double> getTimestamps(){
        return inAccelList[3];
    }

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
