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


    public static String Classify(double meanAccelX, double meanAccelY, double meanAccelZ, double meanGyroY, double meanGyroZ,
    							  double meanMagnetX,double meanMagnetY, 
    							  double meanMagnetZ, double meanRotateX,
                                  double meanRotateY, double meanRotateZ, double fAccelX1, double fAccelX2, 
                                  double fAccelX4, double fAccelX6, double fAccelX8, double fAccelX9, double fAccelX12,
                                  double fAccelY0, double fAccelY2, double fAccelY4, double fAccelY5,
                                  double fAccelY6, double fAccelY7, double fAccelY14,
                                  double fAccelZ1, double fAccelZ2, double fAccelZ3, double fAccelZ5, 
                                  double fAccelZ6, double fAccelZ10, double fAccelZ11, double fAccelZ14,
                                  double fGyroX0, double fGyroX1, double fGyroX2,
                                  double fGyroX3, double fGyroX4, double fGyroX6, double fGyroX7, double fGyroX8, double fGyroX14, 
                                  double fGyroY2, double fGyroY3, double fGyroY4, double fGyroY5,
                                  double fGyroY6, double fGyroY7,
                                  double fGyroY10, double fGyroZ0, double fGyroZ1, double fGyroZ6, double fGyroZ9, double fGyroZ10, 
                                  double fMagnetX4, double fMagnetX5,
                                  double fMagnetY6, double fRotateX12, double fRotateY15, double fRotateZ14,
                                  double fAccelZ13,
                                  double varAccelX, double varAccelY, double varAccelZ, double varGyroX,
                                  double varGyroY, double varGyroZ, double varMagnetX, double varMagnetY, double varMagnetZ,
                                  double varRotateX, double varRotateY, double varRotateZ){
    	
    	String classification = "";
 
    			if(meanGyroZ <= 0.120001){
    				if(meanAccelX <= -0.016144){
    					if(meanGyroZ <= -0.078047){
    						classification = DataUtils.APPROACH_EVENT;
    					}
    					else{
    						if(meanAccelZ <= 0.077496){
    							if(fGyroY6 <= 42.186772){
    								if(fAccelY0 <= 1.490388){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									if(fGyroY4 <= 7.248862){
    										classification = DataUtils.NO_EVENT;
    									}
    									else{
    										classification = DataUtils.APPROACH_EVENT;
    									}
    								}
    							}
    							else{
    								classification = DataUtils.APPROACH_EVENT;
    							}
    						}
    						else{
    							classification = DataUtils.APPROACH_EVENT;
    						}
    					}
    				}
    				else{
    					if(varGyroZ <= 0.140113){
    						if(fRotateX12 <= 0.009356){
    							if(varMagnetZ <= 3.885789){
    								if(meanRotateY <= -0.090079){
    									classification = DataUtils.ENTER_EVENT;
    								}
    								else{
    									if(fGyroX7 <= 48.824662){
    										if(fAccelZ13 <= 1.140609){
    											if(meanMagnetY <= 30.113415){
    												if(varMagnetX <= 14.434749){
    													if(fMagnetX5 <= 47.442443){
    														if(varGyroY <= 0.014537){
    															if(fGyroZ1 <= 11.282681){
    																if(fAccelX4 <= 0.252499){
    																	if(meanMagnetZ <= 2.858685){
    																		classification = DataUtils.NO_EVENT;
    																	}
    																	else{
    																		if(varGyroX <= 0.000398){
    																			classification = DataUtils.ON_EVENT;
    																		}
    																		else{
    																			classification = DataUtils.NO_EVENT;
    																		}
    																	}
    																}
    																else{
    																	if(varMagnetZ <= 0.276872){
    																		classification = DataUtils.NO_EVENT;
    																	}
    																	else{
    																		classification = DataUtils.ON_EVENT;
    																	}
    																}
    															}
    															else{
    																if(meanMagnetX <= 3.153319){
    																	classification = DataUtils.NO_EVENT;
    																}
    																else{
    																	classification = DataUtils.ON_EVENT;
    																}
    															}
    														}
    														else{
    															classification = DataUtils.NO_EVENT;
    														}
    													}
    													else{
    														if(meanGyroZ <= -0.03329){
    															if(fAccelY7 <= 0.011692){
    																classification = DataUtils.APPROACH_EVENT;
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
    												else{
    													if(fAccelY6 <= 0.007539){
    														classification = DataUtils.ON_EVENT;
    													}
    													else{
    														classification = DataUtils.NO_EVENT;
    													}
    												}
    											}
    											else{
    												if(fGyroY3 <= 18.386221){
    													if(meanRotateZ <= -0.203649){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														classification = DataUtils.ENTER_EVENT;
    													}
    												}
    												else{
    													classification = DataUtils.ON_EVENT;
    												}
    											}
    										}
    										else{
    											classification = DataUtils.OFF_EVENT;
    										}
    									}
    									else{
    										classification = DataUtils.ON_EVENT;
    									}
    								}
    							}
    							else{
    								if(fGyroX3 <= 124.310286){
    									if(meanAccelZ <= -0.034269){
    										classification = DataUtils.EXIT_EVENT;
    									}
    									else{
    										if(varMagnetX <= 0.551662){
    											if(meanMagnetX <= 3.394434){
    												classification = DataUtils.NO_EVENT;
    											}
    											else{
    												classification = DataUtils.ON_EVENT;
    											}
    										}
    										else{
    											if(meanRotateZ <= -0.176669){
    												if(meanMagnetZ <= -15.538512){
    													if(fAccelX9 <= 0.053828){
    														if(meanMagnetY <= -20.537115){
    															classification = DataUtils.NO_EVENT;
    														}
    														else{
    															if(fAccelY0 <= 0.29805){
    																if(fAccelX4 <= 0.360788){
    																	classification = DataUtils.APPROACH_EVENT;
    																}
    																else{
    																	classification = DataUtils.NO_EVENT;
    																}
    															}
    															else{
    																classification = DataUtils.APPROACH_EVENT;
    															}
    														}
    													}
    													else{
    														classification = DataUtils.NO_EVENT;
    													}
    												}
    												else{
    													if(fGyroX2 <= 38.502526){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														classification = DataUtils.APPROACH_EVENT;
    													}
    												}
    											}
    											else{
    												if(meanRotateY <= 0.142281){
    													if(fMagnetY6 <= 0.746977){
    														if(varRotateY <= 0.00089){
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
    													if(fAccelY2 <= 0.544714){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														classification = DataUtils.APPROACH_EVENT;
    													}
    												}
    											}
    										}
    									}
    								}
    								else{
    									if(varGyroX <= 0.171628){
    										classification = DataUtils.APPROACH_EVENT;
    									}
    									else{
    										classification = DataUtils.ON_EVENT;
    									}
    								}
    							}
    						}
    						else{
    							if(fAccelZ14 <= 0.728669){
    								if(fRotateZ14 <= 0.13899){
    									if(varRotateZ <= 0.001092){
    										if(varRotateY <= 0.002392){
    											classification = DataUtils.NO_EVENT;
    										}
    										else{
    											classification = DataUtils.ON_EVENT;
    										}
    									}
    									else{
    										if(fGyroZ0 <= 1.355223){
    											classification = DataUtils.APPROACH_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    								}
    								else{
    									classification = DataUtils.EXIT_EVENT;
    								}
    							}
    							else{
    								classification = DataUtils.ENTER_EVENT;
    							}
    						}
    					}
    					else{
    						if(varRotateX <= 0.424688){
    							if(fGyroX4 <= 206.970507){
    								if(fGyroY6 <= 107.099033){
    									if(varRotateY <= 0.0213){
    										if(meanMagnetZ <= -26.034245){
    											if(fGyroX8 <= 15.654201){
    												if(varMagnetX <= 3.095328){
    													classification = DataUtils.APPROACH_EVENT;
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
    											if(fGyroY7 <= 67.951096){
    												if(varAccelZ <= 0.007593){
    													classification = DataUtils.ENTER_EVENT;
    												}
    												else{
    													if(meanMagnetZ <= -23.336588){
    														if(meanRotateX <= -0.01185){
    															classification = DataUtils.ENTER_EVENT;
    														}
    														else{
    															if(varAccelY <= 0.006433){
    																classification = DataUtils.APPROACH_EVENT;
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
    											}
    											else{
    												classification = DataUtils.APPROACH_EVENT;
    											}
    										}
    									}
    									else{
    										classification = DataUtils.ENTER_EVENT;
    									}
    								}
    								else{
    									classification = DataUtils.ENTER_EVENT;
    								}
    							}
    							else{
    								if(fGyroY5 <= 15.717066){
    									classification = DataUtils.ON_EVENT;
    								}
    								else{
    									classification = DataUtils.NO_EVENT;
    								}
    							}
    						}
    						else{
    							classification = DataUtils.OFF_EVENT;
    						}
    					}
    				}
    			}
    			else{
    				if(meanAccelY <= -0.01497){
    					if(fGyroY2 <= 35.630146){
    						classification = DataUtils.NO_EVENT;
    					}
    					else{
    						classification = DataUtils.ON_EVENT;
    					}
    				}
    				else{
    					if(varMagnetY <= 11578.760267){
    						if(fRotateY15 <= 0.017243){
    							if(meanRotateX <= -0.058105){
    								classification = DataUtils.EXIT_EVENT;
    							}
    							else{
    								if(fAccelZ1 <= 23.632284){
    									if(fAccelZ2 <= 20.915379){
    										if(meanRotateZ <= 1.044205){
    											if(fGyroZ6 <= 17.169328){
    												if(meanRotateY <= -0.04028){
    													classification = DataUtils.OFF_EVENT;
    												}
    												else{
    													if(fAccelZ11 <= 1.439616){
    														if(fGyroX0 <= 84.52982){
    															classification = DataUtils.NO_EVENT;
    														}
    														else{
    															if(fGyroX3 <= 13.556415){
    																if(fAccelZ10 <= 0.475303){
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
    														classification = DataUtils.OFF_EVENT;
    													}
    												}
    											}
    											else{
    												if(fGyroZ9 <= 12.767469){
    													classification = DataUtils.NO_EVENT;
    												}
    												else{
    													classification = DataUtils.OFF_EVENT;
    												}
    											}
    										}
    										else{
    											if(fGyroY7 <= 89.500835){
    												if(meanMagnetX <= -15.604019){
    													if(fAccelX1 <= 1.243456){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														classification = DataUtils.EXIT_EVENT;
    													}
    												}
    												else{
    													if(varAccelX <= 0.003994){
    														if(varMagnetY <= 4.224105){
    															if(fAccelZ13 <= 0.246248){
    																classification = DataUtils.NO_EVENT;
    															}
    															else{
    																classification = DataUtils.EXIT_EVENT;
    															}
    														}
    														else{
    															classification = DataUtils.EXIT_EVENT;
    														}
    													}
    													else{
    														if(fAccelX6 <= 6.83324){
    															classification = DataUtils.NO_EVENT;
    														}
    														else{
    															if(fAccelY5 <= 0.369853){
    																classification = DataUtils.EXIT_EVENT;
    															}
    															else{
    																classification = DataUtils.NO_EVENT;
    															}
    														}
    													}
    												}
    											}
    											else{
    												classification = DataUtils.EXIT_EVENT;
    											}
    										}
    									}
    									else{
    										classification = DataUtils.EXIT_EVENT;
    									}
    								}
    								else{
    									classification = DataUtils.OFF_EVENT;
    								}
    							}
    						}
    						else{
    							if(meanMagnetZ <= -18.085738){
    								classification = DataUtils.NO_EVENT;
    							}
    							else{
    								classification = DataUtils.EXIT_EVENT;
    							}
    						}
    					}
    					else{
    						classification = DataUtils.EXIT_EVENT;
    					}
    				}
    			}

    	
    	return classification;
    }

}
