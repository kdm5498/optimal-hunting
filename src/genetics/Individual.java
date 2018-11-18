package genetics;

import resource.Resource;

/**
 * This class represents a single possible algorithm that can be used to optimize hunting strategies.
 * @author Kyle McVay
 */
public class Individual {
	/**
	 * Initializes an Individual with a randomized algorithm
	 */
	public Individual() {
		
	}
	
	/**
	 * Takes in the current state of a hunting/gathering party and decides exactly what they should do.
	 * 
	 * @param partySize - Current size of the hunting/gathering party
	 * @param currentWeight - The amount of weight in pounds the party is currently carrying of meat or plants
	 * @param currentCals - The amount of calories the carried food is worth
	 * @param currentTime - The current time of day, int between 0 and 480 to represent 8 hours of gathering time
	 * @param currentResource - The resource the party has just found, if any
	 * 
	 * @return Number representing action to take. 0 for ignore, 1 for hunt, 2 for turn back.
	 */
	public int Decide(int partySize, double currentWeight, double currentCals, int currentTime, Resource currentResource) {
		return 0;
	}
}
