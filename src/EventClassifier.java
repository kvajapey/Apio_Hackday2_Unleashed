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
    public static String Classify(double meanGyroY, double meanGyroZ, double meanMagnetX,double meanMagnetY, double meanMagnetZ,
                                  double meanRotateY, double meanRotateZ,
                                  double fAccelX2, double fAccelX4,
                                  double fAccelX8,
                                  double fAccelY0,
                                  double fAccelY6,
                                  double fAccelZ6,
                                  double fGyroX0, double fGyroX1,
                                  double fGyroX3,
                                  double fGyroY3,
                                  double fGyroZ10,
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
    					classification = DataUtils.ENTER_EVENT;
    				}
    				else{
    					if(fAccelX8 <= 0.498985){
    						classification = DataUtils.NO_EVENT;
    					}
    					else{
    						classification = DataUtils.ENTER_EVENT;
    					}
    				}
    			}
    			else{
    				if(meanRotateZ <= 0.597347){
    					classification = DataUtils.OFF_EVENT;
    				}
    				else{
    					classification = DataUtils.NO_EVENT;
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
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									if(meanMagnetX <= 0.258174){
    										if(fGyroX1 <= 0.019404){
    											classification = DataUtils.NO_EVENT;
    										}
    										else{
    											classification = DataUtils.ON_EVENT;
    										}
    									}
    									else{
    										classification = DataUtils.NO_EVENT;
    									}
    								}
    							}
    							else{
    								classification = DataUtils.NO_EVENT;
    							}
    						}
    						else{
    							classification = DataUtils.ON_EVENT;
    						}
    					}
    					else{
    						classification = DataUtils.NO_EVENT;
    					}
    				}
    				else{
    					classification = DataUtils.OFF_EVENT;
    				}
    			}
    			else{
    				if(fGyroY3 <= 25.325398){
    					classification = DataUtils.NO_EVENT;
    				}
    				else{
    					if(fAccelY6 <= 0.170844){
    						classification = DataUtils.NO_EVENT;
    					}
    					else{
    						classification = DataUtils.ON_EVENT;
    					}
    				}
    			}
    		}
    	}
    	else{
    		if(meanRotateZ <= 0.857356){
    			if(meanMagnetZ <= -19.455691){
    				classification = DataUtils.NO_EVENT;
    			}
    			else{
    				if(fAccelY0 <= 0.049148){
    					classification = DataUtils.OFF_EVENT;
    				}
    				else{
    					if(meanMagnetY <= 2.168877){
    						classification = DataUtils.NO_EVENT;
    					}
    					else{
    						if(meanRotateY <= 0.085565){
    							classification = DataUtils.NO_EVENT;
    						}
    						else{
    							classification = DataUtils.OFF_EVENT;
    						}
    					}
    				}
    			}
    		}
    		else{
    			if(varAccelY <= 0.003998){
    				classification = DataUtils.NO_EVENT;
    			}
    			else{
    				if(meanMagnetZ <= 5.698){
    					if(meanMagnetZ <= -0.285576){
    						if(fAccelX2 <= 1.40773){
    							if(fGyroX0 <= 77.108073){
    								if(meanRotateZ <= 2.729261){
    									if(fGyroX3 <= 2.469406){
    										classification = DataUtils.NO_EVENT;
    									}
    									else{
    										if(varGyroZ <= 0.261348){
    											if(fAccelZ6 <= 0.131369){
    												classification = DataUtils.EXIT_EVENT;
    											}
    											else{
    												if(varMagnetY <= 4.976276){
    													classification = DataUtils.NO_EVENT;
    												}
    												else{
    													classification = DataUtils.EXIT_EVENT;
    												}
    											}
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    								}
    								else{
    									classification = DataUtils.NO_EVENT;
    								}
    							}
    							else{
    								classification = DataUtils.NO_EVENT;
    							}
    						}
    						else{
    							classification = DataUtils.NO_EVENT;
    						}
    					}
    					else{
    						classification = DataUtils.NO_EVENT;
    					}
    				}
    				else{
    					classification = DataUtils.EXIT_EVENT;
    				}
    			}
    		}
    	}
    	
    	
    	return classification;
    }

}
