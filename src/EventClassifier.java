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


    public static String Classify(double meanGyroY, double meanGyroZ, double meanMagnetX,double meanMagnetY, double meanMagnetZ,
                                  double meanRotateY, double meanRotateZ, double fAccelX2, double fAccelX4, double fAccelX8,
                                  double fAccelY0, double fAccelY6, double fAccelZ6, double fGyroX0, double fGyroX1,
                                  double fGyroX3, double fGyroY3, double fGyroZ10, double varAccelY, double varAccelZ,
                                  double varGyroY, double varGyroZ, double varMagnetY, double varMagnetZ){
    	
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
