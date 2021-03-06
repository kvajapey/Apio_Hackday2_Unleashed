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


    public static String Classify(double meanAccelX, double meanAccelY, double meanAccelZ,
    							  double meanGyroX, double meanGyroY, double meanGyroZ,
    							  double meanMagnetX,double meanMagnetY, double meanMagnetZ, 
    							  double meanRotateX, double meanRotateY, double meanRotateZ, 
                                  double fAccelX2, double fAccelX14, double fAccelX15,
                                  double fAccelY1, double fAccelY2, double fAccelY5, double fAccelY7, double fAccelY13, double fAccelY14,
                                  double fAccelZ3, double fAccelZ4, double fAccelZ6, double fAccelZ13,
                                  double fGyroX1, double fGyroX2, double fGyroX3, double fGyroX4, double fGyroX5, double fGyroX6, 
                                  double fGyroX9, double fGyroX12,
                                  double fGyroY0, double fGyroY1, double fGyroY2, double fGyroY5, double fGyroY6, double fGyroY10,
                                  double fGyroZ0, double fGyroZ3, double fGyroZ5, double fGyroZ6, double fGyroZ9,
                                  double fMagnetX0, double fMagnetZ7,
                                  double fRotateX0, double fRotateY12, double fRotateZ0, double fRotateZ15,
                                  double varAccelX, double varAccelY, double varAccelZ, 
                                  double varGyroX, double varGyroY, double varGyroZ, 
                                  double varMagnetX, double varMagnetY, double varMagnetZ,
                                  double varRotateX, double varRotateY, double varRotateZ){
    	
    	String classification = "";

    			if(meanGyroZ <= 0.117577){
    				if(varMagnetY <= 2.865007){
    					if(fGyroX1 <= 97.643965){
    						if(fGyroY6 <= 115.269621){
    							if(fMagnetZ7 <= 44.9855){
    								if(fAccelZ13 <= 0.3383){
    									if(meanMagnetX <= -23.752013){
    										if(meanRotateZ <= 1.861838){
    											classification = DataUtils.ON_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    									else{
    										if(meanGyroX <= 0.033767){
    											if(meanMagnetY <= 30.113415){
    												if(varMagnetX <= 14.442537){
    													if(varMagnetZ <= 0.511947){
    														if(meanMagnetY <= -32.480147){
    															if(varRotateY <= 0.000238){
    																classification = DataUtils.NO_EVENT;
    															}
    															else{
    																classification = DataUtils.ON_EVENT;
    															}
    														}
    														else{
    															if(meanRotateY <= -0.598883){
    																if(meanRotateY <= -0.600551){
    																	if(meanAccelZ <= -0.00263){
    																		classification = DataUtils.ON_EVENT;
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
    																if(meanRotateX <= 0.005662){
    																	if(meanMagnetX <= 0.102056){
    																		classification = DataUtils.NO_EVENT;
    																	}
    																	else{
    																		if(meanRotateZ <= -0.05207){
    																			classification = DataUtils.NO_EVENT;
    																		}
    																		else{
    																			if(varGyroY <= 0.008034){
    																				classification = DataUtils.ON_EVENT;
    																			}
    																			else{
    																				classification = DataUtils.NO_EVENT;
    																			}
    																		}
    																	}
    																}
    																else{
    																	classification = DataUtils.NO_EVENT;
    																}
    															}
    														}
    													}
    													else{
    														if(varMagnetZ <= 13.9335){
    															if(varMagnetZ <= 0.542388){
    																if(meanRotateZ <= -0.186836){
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
    														else{
    															if(meanRotateX <= 0.018052){
    																classification = DataUtils.NO_EVENT;
    															}
    															else{
    																if(varMagnetX <= 3.968143){
    																	classification = DataUtils.APPROACH_EVENT;
    																}
    																else{
    																	classification = DataUtils.NO_EVENT;
    																}
    															}
    														}
    													}
    												}
    												else{
    													if(meanRotateX <= 0.01987){
    														classification = DataUtils.ON_EVENT;
    													}
    													else{
    														classification = DataUtils.NO_EVENT;
    													}
    												}
    											}
    											else{
    												if(meanMagnetY <= 30.236296){
    													if(varRotateY <= 0.000599){
    														classification = DataUtils.ENTER_EVENT;
    													}
    													else{
    														classification = DataUtils.NO_EVENT;
    													}
    												}
    												else{
    													if(fGyroX3 <= 9.287404){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														if(varAccelX <= 0.004356){
    															if(varMagnetX <= 2.280859){
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
    											}
    										}
    										else{
    											if(fGyroY5 <= 0.479374){
    												if(fRotateY12 <= 0.005145){
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
    								}
    								else{
    									if(meanGyroZ <= 0.070779){
    										classification = DataUtils.NO_EVENT;
    									}
    									else{
    										if(varAccelZ <= 0.019851){
    											classification = DataUtils.OFF_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    								}
    							}
    							else{
    								if(meanRotateX <= 0.017741){
    									if(meanMagnetX <= 4.079888){
    										if(meanRotateX <= 0.014505){
    											classification = DataUtils.NO_EVENT;
    										}
    										else{
    											if(meanAccelY <= -0.005503){
    												classification = DataUtils.ON_EVENT;
    											}
    											else{
    												classification = DataUtils.NO_EVENT;
    											}
    										}
    									}
    									else{
    										classification = DataUtils.ENTER_EVENT;
    									}
    								}
    								else{
    									if(meanGyroZ <= -0.012032){
    										if(meanRotateZ <= 0.104958){
    											if(fGyroX6 <= 1.472332){
    												classification = DataUtils.NO_EVENT;
    											}
    											else{
    												if(fAccelY7 <= 0.125465){
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
    									else{
    										classification = DataUtils.NO_EVENT;
    									}
    								}
    							}
    						}
    						else{
    							classification = DataUtils.ENTER_EVENT;
    						}
    					}
    					else{
    						if(meanRotateZ <= -2.137576){
    							classification = DataUtils.APPROACH_EVENT;
    						}
    						else{
    							if(meanMagnetZ <= -21.376254){
    								classification = DataUtils.APPROACH_EVENT;
    							}
    							else{
    								if(varAccelZ <= 0.377416){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									classification = DataUtils.APPROACH_EVENT;
    								}
    							}
    						}
    					}
    				}
    				else{
    					if(meanRotateZ <= 0.10327){
    						if(varAccelX <= 0.026515){
    							if(varMagnetX <= 137.851746){
    								if(fGyroX6 <= 76.435696){
    									if(meanAccelZ <= 0.104841){
    										if(varGyroZ <= 0.017856){
    											if(fAccelY13 <= 0.013963){
    												if(varRotateX <= 0.007085){
    													classification = DataUtils.NO_EVENT;
    												}
    												else{
    													classification = DataUtils.ON_EVENT;
    												}
    											}
    											else{
    												classification = DataUtils.ON_EVENT;
    											}
    										}
    										else{
    											if(fGyroX4 <= 209.141531){
    												if(meanRotateY <= -0.048199){
    													if(varRotateY <= 0.001325){
    														classification = DataUtils.ENTER_EVENT;
    													}
    													else{
    														if(meanMagnetZ <= -44.44256){
    															classification = DataUtils.ENTER_EVENT;
    														}
    														else{
    															if(meanMagnetY <= 32.475278){
    																classification = DataUtils.NO_EVENT;
    															}
    															else{
    																classification = DataUtils.ENTER_EVENT;
    															}
    														}
    													}
    												}
    												else{
    													if(meanAccelX <= -0.010972){
    														if(meanGyroZ <= -0.093689){
    															classification = DataUtils.APPROACH_EVENT;
    														}
    														else{
    															if(varRotateY <= 0.013809){
    																if(fGyroY6 <= 36.622197){
    																	if(fAccelY2 <= 0.494162){
    																		if(meanMagnetX <= 6.96169){
    																			classification = DataUtils.NO_EVENT;
    																		}
    																		else{
    																			classification = DataUtils.APPROACH_EVENT;
    																		}
    																	}
    																	else{
    																		if(varAccelY <= 0.008524){
    																			if(varMagnetX <= 7.158048){
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
    																	classification = DataUtils.APPROACH_EVENT;
    																}
    															}
    															else{
    																classification = DataUtils.APPROACH_EVENT;
    															}
    														}
    													}
    													else{
    														if(fAccelX2 <= 6.830311){
    															if(fAccelX15 <= 0.640613){
    																if(fAccelY7 <= 1.509647){
    																	if(fAccelZ13 <= 0.522165){
    																		if(meanMagnetX <= -14.848911){
    																			if(fGyroY0 <= 28.233161){
    																				if(fAccelY13 <= 0.031198){
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
    																			if(meanRotateY <= 0.064571){
    																				if(meanMagnetX <= 5.188278){
    																					if(meanGyroZ <= -0.081449){
    																						if(fGyroZ6 <= 7.209478){
    																							classification = DataUtils.NO_EVENT;
    																						}
    																						else{
    																							classification = DataUtils.ENTER_EVENT;
    																						}
    																					}
    																					else{
    																						if(fGyroY2 <= 0.32866){
    																							classification = DataUtils.APPROACH_EVENT;
    																						}
    																						else{
    																							if(meanAccelY <= 0.01954){
    																								if(fGyroX6 <= 22.776337){
    																									classification = DataUtils.NO_EVENT;
    																								}
    																								else{
    																									if(fMagnetX0 <= 4121.590151){
    																										classification = DataUtils.NO_EVENT;
    																									}
    																									else{
    																										classification = DataUtils.APPROACH_EVENT;
    																									}
    																								}
    																							}
    																							else{
    																								if(varAccelZ <= 0.018311){
    																									classification = DataUtils.APPROACH_EVENT;
    																								}
    																								else{
    																									classification = DataUtils.NO_EVENT;
    																								}
    																							}
    																						}
    																					}
    																				}
    																				else{
    																					classification = DataUtils.APPROACH_EVENT;
    																				}
    																			}
    																			else{
    																				if(varGyroZ <= 0.023925){
    																					if(meanGyroX <= -0.000855){
    																						classification = DataUtils.ENTER_EVENT;
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
    																		if(fGyroX3 <= 42.258083){
    																			if(fAccelY1 <= 0.479564){
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
    																}
    																else{
    																	if(meanRotateY <= 0.042373){
    																		classification = DataUtils.NO_EVENT;
    																	}
    																	else{
    																		if(meanGyroZ <= -0.074382){
    																			classification = DataUtils.ENTER_EVENT;
    																		}
    																		else{
    																			classification = DataUtils.NO_EVENT;
    																		}
    																	}
    																}
    															}
    															else{
    																if(fGyroX5 <= 2.368408){
    																	classification = DataUtils.ON_EVENT;
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
    									}
    									else{
    										if(meanMagnetZ <= 18.095601){
    											classification = DataUtils.APPROACH_EVENT;
    										}
    										else{
    											if(varAccelY <= 0.014476){
    												classification = DataUtils.APPROACH_EVENT;
    											}
    											else{
    												classification = DataUtils.NO_EVENT;
    											}
    										}
    									}
    								}
    								else{
    									if(varGyroY <= 0.296322){
    										classification = DataUtils.NO_EVENT;
    									}
    									else{
    										classification = DataUtils.APPROACH_EVENT;
    									}
    								}
    							}
    							else{
    								if(fRotateZ0 <= 170.212452){
    									classification = DataUtils.OFF_EVENT;
    								}
    								else{
    									classification = DataUtils.NO_EVENT;
    								}
    							}
    						}
    						else{
    							if(meanRotateZ <= -0.025214){
    								if(meanGyroX <= -0.106639){
    									if(fAccelY13 <= 1.299037){
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
    							else{
    								if(meanRotateX <= 1.656239){
    									classification = DataUtils.NO_EVENT;
    								}
    								else{
    									classification = DataUtils.OFF_EVENT;
    								}
    							}
    						}
    					}
    					else{
    						if(fGyroX4 <= 502.655959){
    							if(fAccelZ3 <= 56.652309){
    								if(meanGyroZ <= -0.395847){
    									if(fRotateZ15 <= 0.078022){
    										classification = DataUtils.EXIT_EVENT;
    									}
    									else{
    										if(meanGyroY <= -0.105642){
    											classification = DataUtils.ON_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    								}
    								else{
    									if(fAccelY5 <= 0.000188){
    										classification = DataUtils.ON_EVENT;
    									}
    									else{
    										if(meanMagnetZ <= 31.153382){
    											if(meanGyroX <= -0.045264){
    												if(meanMagnetZ <= -44.601541){
    													classification = DataUtils.ENTER_EVENT;
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
    											if(meanMagnetX <= -3.940307){
    												if(meanRotateX <= 1.582958){
    													classification = DataUtils.NO_EVENT;
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
    								}
    							}
    							else{
    								classification = DataUtils.OFF_EVENT;
    							}
    						}
    						else{
    							classification = DataUtils.EXIT_EVENT;
    						}
    					}
    				}
    			}
    			else{
    				if(varGyroY <= 0.533124){
    					if(fAccelZ4 <= 7.426417){
    						if(fGyroY6 <= 38.989472){
    							if(fAccelY5 <= 7.880753){
    								if(meanMagnetZ <= -36.132692){
    									if(varAccelX <= 0.006474){
    										classification = DataUtils.EXIT_EVENT;
    									}
    									else{
    										classification = DataUtils.NO_EVENT;
    									}
    								}
    								else{
    									if(meanRotateZ <= 1.303819){
    										if(meanRotateX <= -0.723775){
    											classification = DataUtils.EXIT_EVENT;
    										}
    										else{
    											if(meanAccelX <= 0.011334){
    												if(fGyroY1 <= 113.630038){
    													if(fGyroX9 <= 22.992886){
    														if(fAccelY13 <= 0.212816){
    															if(fGyroZ5 <= 48.911185){
    																if(fGyroX6 <= 18.336469){
    																	classification = DataUtils.NO_EVENT;
    																}
    																else{
    																	if(varGyroX <= 0.097519){
    																		classification = DataUtils.OFF_EVENT;
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
    														else{
    															if(varAccelY <= 0.012366){
    																if(varAccelZ <= 0.008308){
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
    														if(meanMagnetX <= 0.740071){
    															classification = DataUtils.NO_EVENT;
    														}
    														else{
    															classification = DataUtils.OFF_EVENT;
    														}
    													}
    												}
    												else{
    													if(meanMagnetX <= -15.007516){
    														classification = DataUtils.NO_EVENT;
    													}
    													else{
    														if(meanRotateZ <= 0.993066){
    															classification = DataUtils.OFF_EVENT;
    														}
    														else{
    															classification = DataUtils.NO_EVENT;
    														}
    													}
    												}
    											}
    											else{
    												if(meanRotateX <= 0.001988){
    													classification = DataUtils.EXIT_EVENT;
    												}
    												else{
    													classification = DataUtils.NO_EVENT;
    												}
    											}
    										}
    									}
    									else{
    										if(varMagnetY <= 11380.590181){
    											if(meanRotateX <= -0.057113){
    												classification = DataUtils.EXIT_EVENT;
    											}
    											else{
    												if(meanRotateY <= 0.069503){
    													if(fGyroX2 <= 54.712983){
    														if(fGyroX3 <= 2.738167){
    															if(fAccelZ4 <= 0.962971){
    																classification = DataUtils.EXIT_EVENT;
    															}
    															else{
    																classification = DataUtils.NO_EVENT;
    															}
    														}
    														else{
    															if(fGyroY2 <= 97.248353){
    																if(fAccelX14 <= 0.424812){
    																	if(meanRotateX <= 0.027671){
    																		if(varMagnetX <= 6.954143){
    																			classification = DataUtils.NO_EVENT;
    																		}
    																		else{
    																			if(varGyroX <= 0.206558){
    																				classification = DataUtils.EXIT_EVENT;
    																			}
    																			else{
    																				classification = DataUtils.NO_EVENT;
    																			}
    																		}
    																	}
    																	else{
    																		if(meanMagnetZ <= 6.108268){
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
    															else{
    																classification = DataUtils.EXIT_EVENT;
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
    										}
    										else{
    											classification = DataUtils.EXIT_EVENT;
    										}
    									}
    								}
    							}
    							else{
    								classification = DataUtils.EXIT_EVENT;
    							}
    						}
    						else{
    							if(fGyroX6 <= 13.697814){
    								classification = DataUtils.ON_EVENT;
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
    				else{
    					if(varGyroX <= 0.149777){
    						classification = DataUtils.APPROACH_EVENT;
    					}
    					else{
    						if(fRotateX0 <= 12614.362017){
    							if(fGyroZ9 <= 73.553596){
    								if(meanAccelZ <= -0.174145){
    									if(fGyroX3 <= 64.339329){
    										classification = DataUtils.ENTER_EVENT;
    									}
    									else{
    										classification = DataUtils.NO_EVENT;
    									}
    								}
    								else{
    									if(varAccelX <= 0.411635){
    										classification = DataUtils.NO_EVENT;
    									}
    									else{
    										if(meanRotateX <= 1.490278){
    											classification = DataUtils.EXIT_EVENT;
    										}
    										else{
    											classification = DataUtils.NO_EVENT;
    										}
    									}
    								}
    							}
    							else{
    								if(varGyroZ <= 1.7321){
    									classification = DataUtils.APPROACH_EVENT;
    								}
    								else{
    									classification = DataUtils.NO_EVENT;
    								}
    							}
    						}
    						else{
    							if(meanMagnetY <= 49.744624){
    								classification = DataUtils.NO_EVENT;
    							}
    							else{
    								classification = DataUtils.APPROACH_EVENT;
    							}
    						}
    					}
    				}
    			}

    	
    	return classification;
    }

}
