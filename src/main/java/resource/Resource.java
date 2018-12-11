package main.java.resource;

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
	private double weight;
	private String name;
	private double successRate;
	
	/**
	 * Initializes a new resource with the given values.
	 * 
	 * @param calories - Number of calories a resource is worth
	 * @param animal - True if resource is an animal, false if resource is a plant
	 * @param gatherTime - Time it takes to gather this resource
	 * @param processTime - Time it takes to process this resource
	 * @param encounterRate - Number of minutes it takes on average to find this resource.
	 * @param weight - Weight of resource.
	 * @param name - Name of resource.
	 * @param successRate - Chance of successful gather of resource.
	 */
	public Resource(int calories, boolean animal, int gatherTime, int processTime, int encounterRate, double weight, String name, double successRate) {
		this.calories = calories;
		this.animal = animal;
		this.gatherTime = gatherTime;
		this.processTime = processTime;
		this.encounterRate = encounterRate;
		this.weight = weight;
		this.name = name;
		this.successRate = successRate;
	}

	/**
	 * Retrieves the number of calories that this resource is worth.
	 * @return The calories this resource is worth.
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * Retrieves if this resource is an animal.
	 * @return True if the resource is an animal.
	 */
	public boolean isAnimal() {
		return animal;
	}

	/**
	 * Retrieves the time it takes to gather this resource.
	 * @return The time it takes to hunt/gather this resource.
	 */
	public int getGatherTime() {
		return gatherTime;
	}

	/**
	 * Retrieves the processing time of this resource.
	 * @return The processing time of this resource.
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * Retrieves the encounter rate of this resource.
	 * @return The encounter rate of this resource. Encounter rate is an int representing the number of minutes on average between each sighting.
	 */
	public int getEncounterRate() {
		return encounterRate;
	}

	/**
	 * Retrieves the weight of this resource.
	 * @return The weight in pounds of this resource.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Retrieves the name of this resource.
	 * @return Name of resource.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Checks for equality between this resource and obj.
	 * @param obj - Object to test against.
	 * @return True if obj is a resource and names are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Resource)) {
			return false;
		}
		Resource other = (Resource)obj;
		if(other.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the success rate of gathering this resource.
	 * @return The success rate.
	 */
	public double getSuccessRate() {
		return successRate;
	}
}
