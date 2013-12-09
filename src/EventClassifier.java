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


    public static String Classify(double meanAccelX, double meanGyroY, double meanGyroZ, double meanMagnetX,double meanMagnetY, 
    							  double meanMagnetZ, double meanRotateX,
                                  double meanRotateY, double meanRotateZ, double fAccelX2, double fAccelX4, double fAccelX8,
                                  double fAccelX12,
                                  double fAccelY0, double fAccelY4, double fAccelY6, double fAccelY14,
                                  double fAccelZ3, double fAccelZ5, 
                                  double fAccelZ6, 
                                  double fGyroX0, double fGyroX1,
                                  double fGyroX3, double fGyroX6, double fGyroX7, double fGyroX14, double fGyroY3, double fGyroY6,
                                  double fGyroY10, double fGyroZ0, double fGyroZ10, double fMagnetX4, 
                                  double varAccelY, double varAccelZ, double varGyroX,
                                  double varGyroY, double varGyroZ, double varMagnetY, double varMagnetZ){
    	
    	String classification = "";
    	
    			if(meanGyroZ <= 0.120001){
    				if(meanAccelX <= -0.016144){
    					if(meanRotateZ <= -0.046406){
    						if(fMagnetX4 <= 19.836762){
    							classification = DataUtils.NO_EVENT;
    						}
    						else{
    							classification = DataUtils.APPROACH_EVENT;
    						}
    					}
    					else{
    						classification = DataUtils.NO_EVENT;
    					}
    				}
    				else{
    					if(varGyroZ <= 0.140113){
    						if(varMagnetZ <= 3.885789){
    							if(meanRotateY <= -0.090079){
    								classification = DataUtils.ENTER_EVENT;
    							}
    							else{
    								if(fGyroX7 <= 48.824662){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									classification = DataUtils.ON_EVENT;
    								}
    							}
    						}
    						else{
    							if(varAccelY <= 0.00096){
    								classification = DataUtils.NO_EVENT;
    							}
    							else{
    								if(meanRotateZ <= 0.052386){
    									if(varAccelY <= 0.014399){
    										if(varMagnetY <= 38.632175){
    											if(fGyroX14 <= 0.583359){
    												if(meanMagnetZ <= -45.330868){
    													classification = DataUtils.NO_EVENT;
    												}
    												else{
    													if(fGyroY6 <= 10.609309){
    														if(fAccelY0 <= 0.335436){
    															classification = DataUtils.NO_EVENT;
    														}
    														else{
    															classification = DataUtils.APPROACH_EVENT;
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
    						}
    					}
    					else{
    						if(varGyroX <= 0.490516){
    							if(fGyroX6 <= 57.799229){
    								if(meanRotateX <= 0.031622){
    									if(fGyroX3 <= 85.536212){
    										if(fAccelZ5 <= 3.068793){
    											if(varMagnetY <= 2.281914){
    												classification = DataUtils.NO_EVENT;
    											}
    											else{
    												classification = DataUtils.ENTER_EVENT;
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
									classification = DataUtils.APPROACH_EVENT;
								}
    						}
    						else{
								classification = DataUtils.NO_EVENT;
							}
    					}
    				}
    			}
    			else{
    				if(meanRotateZ <= 1.044205){
    					if(fGyroY6 <= 38.714256){
    						if(varAccelY <= 0.006277){
    							classification = DataUtils.NO_EVENT;
    						}
    						else{
    							if(fAccelZ3 <= 6.781954){
    								if(fGyroY6 <= 2.488325){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									classification = DataUtils.OFF_EVENT;
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
    					if(fAccelX12 <= 0.434575){
    						if(fAccelY4 <= 0.142988){
    							classification = DataUtils.NO_EVENT;
    						}
    						else{
    							if(fAccelZ5 <= 0.566344){
    								classification = DataUtils.NO_EVENT;
    							}
    							else{
    								if(fAccelY14 <= 0.023261){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									if(fGyroY10 <= 5.772663){
    										if(fGyroZ0 <= 102.352593){
    											classification = DataUtils.EXIT_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    									else{
											classification = DataUtils.NO_EVENT;
										}
    								}
    							}
    						}
    					}
    					else{
							classification = DataUtils.NO_EVENT;
						}
    				}
    			}

    			
    	
    	
    	return classification;
    }

}
