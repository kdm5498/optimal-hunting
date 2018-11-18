package resource;

/**
 * This class represents a plant or animal that a hunting/gathering party may encounter while away from camp.
 * @author Kyle McVay
 */
public class Resource {
	private int calories;
	private boolean animal;
	private int gatherTime;
	private int processTime;
	private int encounterRate;
	
	/**
	 * Initializes a new resource with the given values
	 * 
	 * @param calories - Number of calories a resource is worth
	 * @param animal - True if resource is an animal, false if resource is a plant
	 * @param gatherTime - Time it takes to gather this resource
	 * @param processTime - Time it takes to process this resource
	 * @param encounterRate - Number of minutes it takes on average to find this resource.
	 */
	public Resource(int calories, boolean animal, int gatherTime, int processTime, int encounterRate) {
		this.calories = calories;
		this.animal = animal;
		this.gatherTime = gatherTime;
		this.processTime = processTime;
		this.encounterRate = encounterRate;
	}

	/**
	 * @return The calories this resource is worth
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * @return If the resource is an animal
	 */
	public boolean isAnimal() {
		return animal;
	}

	/**
	 * @return The time it takes to hunt/gather this resource
	 */
	public int getGatherTime() {
		return gatherTime;
	}

	/**
	 * @return The processing time of this resource.
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * @return The encounter rate of this resource. Encounter rate is an int representing the number of minutes on average between each sighting.
	 */
	public int getEncounterRate() {
		return encounterRate;
	}
}
