import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kvajapey
 * Date: 11/22/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventClassifier {

    public static String fileName;
    public static double approach, entry, ignitionOn, ignitionOff, exit;

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



    public static String Classify(double meanAccelX, double meanAccelY, double meanAccelZ, double meanGyroX,
                                  double meanGyroY, double meanGyroZ, double meanMagnetX,double meanMagnetY, double meanMagnetZ,
                                  double meanRotateX, double meanRotateY, double meanRotateZ, double fAccelX0,
                                  double fAccelX1, double fAccelX2, double fAccelX3, double fAccelX4, double fAccelX5,
                                  double fAccelX6, double fAccelX7, double fAccelX8, double fAccelX9, double fAccelX10,
                                  double fAccelX11, double fAccelX12, double fAccelX13, double fAccelX14, double fAccelX15,
                                  double fAccelY0, double fAccelY1, double fAccelY2, double fAccelY3, double fAccelY4,
                                  double fAccelY5, double fAccelY6, double fAccelY7, double fAccelY8, double fAccelY9,
                                  double fAccelY10, double fAccelY11, double fAccelY12, double fAccelY13, double fAccelY14,
                                  double fAccelY15, double fAccelZ0, double fAccelZ1, double fAccelZ2, double fAccelZ3,
                                  double fAccelZ4, double fAccelZ5, double fAccelZ6, double fAccelZ7, double fAccelZ8,
                                  double fAccelZ9, double fAccelZ10, double fAccelZ11, double fAccelZ12, double fAccelZ13,
                                  double fAccelZ14, double fAccelZ15, double fGyroX0, double fGyroX1, double fGyroX2,
                                  double fGyroX3, double fGyroX4, double fGyroX5, double fGyroX6, double fGyroX7, double fGyroX8,
                                  double fGyroX9, double fGyroX10, double fGyroX11, double fGyroX12, double fGyroX13, double fGyroX14,
                                  double fGyroX15, double fGyroY0, double fGyroY1, double fGyroY2, double fGyroY3, double fGyroY4,
                                  double fGyroY5, double fGyroY6, double fGyroY7, double fGyroY8, double fGyroY9,
                                  double fGyroY10, double fGyroY11, double fGyroY12, double fGyroY13, double fGyroY14,
                                  double fGyroY15, double fGyroZ0, double fGyroZ1, double fGyroZ2, double fGyroZ3, double fGyroZ4,
                                  double fGyroZ5, double fGyroZ6, double fGyroZ7, double fGyroZ8, double fGyroZ9, double fGyroZ10,
                                  double fGyroZ11, double fGyroZ12, double fGyroZ13, double fGyroZ14, double fGyroZ15,
                                  double fMagnetX0, double fMagnetX1, double fMagnetX2, double fMagnetX3, double fMagnetX4,
                                  double fMagnetX5, double fMagnetX6, double fMagnetX7, double fMagnetX8, double fMagnetX9,
                                  double fMagnetX10, double fMagnetX11, double fMagnetX12, double fMagnetX13, double fMagnetX14,
                                  double fMagnetX15, double fMagnetY0, double fMagnetY1, double fMagnetY2, double fMagnetY3,
                                  double fMagnetY4, double fMagnetY5, double fMagnetY6, double fMagnetY7, double fMagnetY8,
                                  double fMagnetY9, double fMagnetY10, double fMagnetY11, double fMagnetY12, double fMagnetY13,
                                  double fMagnetY14, double fMagnetY15, double fMagnetZ0, double fMagnetZ1, double fMagnetZ2,
                                  double fMagnetZ3, double fMagnetZ4, double fMagnetZ5, double fMagnetZ6, double fMagnetZ7,
                                  double fMagnetZ8, double fMagnetZ9, double fMagnetZ10, double fMagnetZ11, double fMagnetZ12,
                                  double fMagnetZ13, double fMagnetZ14, double fMagnetZ15, double fRotateX0, double fRotateX1,
                                  double fRotateX2, double fRotateX3, double fRotateX4, double fRotateX5, double fRotateX6,
                                  double fRotateX7, double fRotateX8, double fRotateX9, double fRotateX10, double fRotateX11,
                                  double fRotateX12, double fRotateX13, double fRotateX14, double fRotateX15, double fRotateY0,
                                  double fRotateY1, double fRotateY2, double fRotateY3, double fRotateY4, double fRotateY5,
                                  double fRotateY6, double fRotateY7, double fRotateY8, double fRotateY9, double fRotateY10,
                                  double fRotateY11, double fRotateY12, double fRotateY13, double fRotateY14, double fRotateY15,
                                  double fRotateZ0, double fRotateZ1, double fRotateZ2, double fRotateZ3, double fRotateZ4,
                                  double fRotateZ5, double fRotateZ6, double fRotateZ7, double fRotateZ8, double fRotateZ9,
                                  double fRotateZ10, double fRotateZ11, double fRotateZ12, double fRotateZ13, double fRotateZ14,
                                  double fRotateZ15, double varAccelX, double varAccelY, double varAccelZ, double varGyroX,
                                  double varGyroY, double varGyroZ, double varMagnetX, double varMagnetY, double varMagnetZ,
                                  double varRotateX, double varRotateY, double varRotateZ){

    }

}
