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


    //rotate x y z is roll pitch yaw
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
    	
    	String classification = "";
    			 if(meanGyroZ <= 0.194623){
    			    if(meanGyroY <= -0.101792){
    			    	if(meanRotateZ <= 0.095252){
    			    		if(varAccelZ <= 0.003763){
    			    			classification =  "entry";
    			    		}
    			    		if(varAccelZ > 0.003763){
    			    			if(fAccelX8 <= 0.498985){
    			    				classification = "none";
    			    			}
    			    			else{
    			    				classification = "entry";
    			    			}
    			    		}
    			    	}
    			    	if(meanRotateZ > 0.095252){
    			    		if(meanRotateZ <= 0.597347){
    			    			classification = "ignition off";
    			    		}
    			    		else{
    			    			classification = "none";
    			    		}
    			    	}
    			     }
    				else{
    					if(varMagnetZ <= 0.219706){
    						if(meanMagnetZ <= 8.375016){
    							if(varGyroY <= 0.003047){
    								if(fAccelX4 <= 0.049607){
    									if(fGyroZ10 <= 0.035395){
    										if(meanMagnetZ <= 0.498086){
    											classification = "none";
    										}
    										else{
    											if(meanMagnetX <= 0.258174){
    												if(fGyroX1 <= 0.019404){
    													classification = "none";
    												}
    												else{
    													classification = "ignition on";
    												}
    											}
    											else{
    												classification = "none";
    											}
    										}
    									}
    									else{
    										classification = "none";
    									}
    								}
    								else{
    									classification = "ignition on";
    								}
    							}
    							else{
    								classification = "none";
    							}
    						}
    						else{
    							classification = "ignition off";
    						}
    					}
    			|   |   Variance Magnet Z > 0.219706
    			|   |   |   FFT Gyro Y3 <= 25.325398
    			|   |   |   |   FFT Accel Z0 <= 0.226009
    			|   |   |   |   |   TimeStamp <= 89.107
    			|   |   |   |   |   |   Variance Magnet Y <= 12.802869
    			|   |   |   |   |   |   |   FFT Gyro X2 <= 7.408758:  none (1573.0/46.0)
    			|   |   |   |   |   |   |   FFT Gyro X2 > 7.408758
    			|   |   |   |   |   |   |   |   TimeStamp <= 59.426:  none (83.0/1.0)
    			|   |   |   |   |   |   |   |   TimeStamp > 59.426:  ignition on (93.0/35.0)
    			|   |   |   |   |   |   Variance Magnet Y > 12.802869
    			|   |   |   |   |   |   |    Mean Roll <= 0.021415:  entry (100.0/56.0)
    			|   |   |   |   |   |   |    Mean Roll > 0.021415:  none (79.0)
    			|   |   |   |   |   TimeStamp > 89.107
    			|   |   |   |   |   |    Mean Roll <= 0.014129
    			|   |   |   |   |   |   |   FFT Accel Y3 <= 0.012517:  none (61.0/3.0)
    			|   |   |   |   |   |   |   FFT Accel Y3 > 0.012517:  ignition off (78.0/21.0)
    			|   |   |   |   |   |    Mean Roll > 0.014129:  none (73.0)
    			|   |   |   |   FFT Accel Z0 > 0.226009
    			|   |   |   |   |   TimeStamp <= 52.501
    			|   |   |   |   |   |   TimeStamp <= 47.558
    			|   |   |   |   |   |   |    Variance Accel Z <= 0.029882:  none (2392.0/33.0)
    			|   |   |   |   |   |   |    Variance Accel Z > 0.029882
    			|   |   |   |   |   |   |   |   TimeStamp <= 7.497
    			|   |   |   |   |   |   |   |   |    Mean Magnet Y <= 0.580754:  none (71.0)
    			|   |   |   |   |   |   |   |   |    Mean Magnet Y > 0.580754:  approach (90.0/18.0)
    			|   |   |   |   |   |   |   |   TimeStamp > 7.497:  none (120.0/4.0)
    			|   |   |   |   |   |   TimeStamp > 47.558
    			|   |   |   |   |   |   |    Mean Roll <= 0.05112
    			|   |   |   |   |   |   |   |    Mean Magnet X <= 3.614566:  none (158.0/5.0)
    			|   |   |   |   |   |   |   |    Mean Magnet X > 3.614566:  approach (85.0/41.0)
    			|   |   |   |   |   |   |    Mean Roll > 0.05112:  approach (132.0/25.0)
    			|   |   |   |   |   TimeStamp > 52.501:  none (2954.0/45.0)
    			|   |   |   FFT Gyro Y3 > 25.325398
    			|   |   |   |   FFT Accel Y6 <= 0.170844:  none (182.0/32.0)
    			|   |   |   |   FFT Accel Y6 > 0.170844:  ignition on (65.0/21.0)
    				}
    			 }
    			 Mean Gyro Z > 0.194623
    			|    Mean Yaw <= 0.857356
    			|   |   TimeStamp <= 51.501:  none (379.0/44.0)
    			|   |   TimeStamp > 51.501
    			|   |   |    Variance Gyro Y <= 0.026237:  ignition off (81.0/16.0)
    			|   |   |    Variance Gyro Y > 0.026237
    			|   |   |   |    Mean Magnet Y <= -0.047374:  none (366.0/41.0)
    			|   |   |   |    Mean Magnet Y > -0.047374
    			|   |   |   |   |    Variance Gyro Z <= 0.114537:  ignition off (82.0/23.0)
    			|   |   |   |   |    Variance Gyro Z > 0.114537:  none (314.0/90.0)
    			|    Mean Yaw > 0.857356
    			|   |    Variance Accel Y <= 0.003998:  none (266.0/1.0)
    			|   |    Variance Accel Y > 0.003998
    			|   |   |    Mean Magnet Z <= 5.698
    			|   |   |   |    Mean Magnet Z <= -0.285576
    			|   |   |   |   |   FFT Accel X2 <= 1.40773
    			|   |   |   |   |   |   FFT Gyro X0 <= 77.108073
    			|   |   |   |   |   |   |    Mean Yaw <= 2.729261
    			|   |   |   |   |   |   |   |   FFT Gyro X3 <= 2.469406:  none (68.0/7.0)
    			|   |   |   |   |   |   |   |   FFT Gyro X3 > 2.469406
    			|   |   |   |   |   |   |   |   |    Variance Gyro Z <= 0.261348
    			|   |   |   |   |   |   |   |   |   |   FFT Accel Z6 <= 0.131369:  exit (60.0/2.0)
    			|   |   |   |   |   |   |   |   |   |   FFT Accel Z6 > 0.131369
    			|   |   |   |   |   |   |   |   |   |   |   Variance Magnet Y <= 4.976276:  none (119.0/55.0)
    			|   |   |   |   |   |   |   |   |   |   |   Variance Magnet Y > 4.976276:  exit (61.0/8.0)
    			|   |   |   |   |   |   |   |   |    Variance Gyro Z > 0.261348:  none (70.0/14.0)
    			|   |   |   |   |   |   |    Mean Yaw > 2.729261:  none (78.0/3.0)
    			|   |   |   |   |   |   FFT Gyro X0 > 77.108073:  none (63.0)
    			|   |   |   |   |   FFT Accel X2 > 1.40773:  none (107.0/1.0)
    			|   |   |   |    Mean Magnet Z > -0.285576:  none (283.0/27.0)
    			|   |   |    Mean Magnet Z > 5.698:  exit (74.0/21.0)

    			
    	return classification;
    }

}
