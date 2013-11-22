import java.util.ArrayList;

public class ClassificationFilter {
	/**
	 * Smooths out noise of a classification array
	 * @param classification - the arraylist of each frame's classification
	 * @param window - an odd integer of the filter's window size
	 * @return a smoothed arraylist containing each frame's classification
	 */
	public static ArrayList<String> ModeFilter(ArrayList<String> classification, int window){
		ArrayList<String> smoothedClassification = new ArrayList<String>();
		int approachCount, entryCount, onCount, offCount, exitCount, noneCount;
		for(int i = 0; i < window/2; i++){
			smoothedClassification.add(i, classification.get(i));
		}
		for(int i = window/2; (i + window/2) < classification.size(); i++){
			approachCount = 0;
			entryCount = 0;
			onCount = 0;
			offCount = 0;
			exitCount = 0;
			noneCount = 0;
			for(int j = 0; j < window; j++){
				if(classification.get(i+j-window/2).equals(DataUtils.APPROACH_EVENT)){
					approachCount++;
				}
				else if(classification.get(i+j-window/2).equals(DataUtils.ENTER_EVENT)){
					entryCount++;
				}
				else if(classification.get(i+j-window/2).equals(DataUtils.ON_EVENT)){
					onCount++;
				}
				else if(classification.get(i+j-window/2).equals(DataUtils.OFF_EVENT)){
					offCount++;
				}
				else if(classification.get(i+j-window/2).equals(DataUtils.EXIT_EVENT)){
					exitCount++;
				}
				else if(classification.get(i+j-window/2).equals(DataUtils.NO_EVENT)){
					noneCount++;
				}
			}
			String smoothClass = max(approachCount, entryCount, onCount, offCount, exitCount, noneCount);
			smoothedClassification.add(i, smoothClass);
		}
		for(int i = classification.size() - window/2; i < classification.size(); i++){
			smoothedClassification.add(i, classification.get(i));
		}
		return smoothedClassification;
	}
	/**
	 * Takes the counts of events and returns the max
	 * @param approachCount
	 * @param entryCount
	 * @param onCount
	 * @param offCount
	 * @param exitCount
	 * @param noneCount
	 * @return the max classification in string form
	 */
	private static String max(int approachCount, int entryCount, int onCount, int offCount, int exitCount, int noneCount){
		String maxClass;
		int maxCount;
		if(approachCount > entryCount){
			maxClass = DataUtils.APPROACH_EVENT;
			maxCount = approachCount;
		}
		else{
			maxClass = DataUtils.ENTER_EVENT;
			maxCount = entryCount;
		}
		if(maxCount <= onCount){
			maxClass = DataUtils.ON_EVENT;
			maxCount = onCount;
		}
		if(maxCount <= offCount){
			maxClass = DataUtils.OFF_EVENT;
			maxCount = offCount;
		}
		if(maxCount < exitCount){
			maxClass = DataUtils.EXIT_EVENT;
			maxCount = exitCount;
		}
		if(maxCount < noneCount){
			maxClass = DataUtils.NO_EVENT;
			maxCount = noneCount;
		}
		return maxClass;
	}
}
