package main.java.people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.resource.Resource;

/**
 * This class represents a hunting or gathering party. A party is made up of a number of People, and events occur on a per party basis.
 * @author Kyle McVay
 */
public class Party {
	private List<Person> members;
	private boolean hunting;
	private boolean active;
	private boolean isGathering;
	private int gatherTimeRemaining;
	private Map<Resource, Double> obtained;
	
	/**
	 * Initializes a new party with an empty list of members.
	 * @param hunting - True if party is hunting, false if party is gathering.
	 */
	public Party(boolean hunting) {
		this(hunting, new ArrayList<Person>());
	}
	
	/**
	 * Initializes a new party with the given list of members.
	 * @param hunting - True if party is hunting, false if party is gathering.
	 * @param members - Members to place in this party.
	 */
	public Party(boolean hunting, List<Person> members) {
		this.setHunting(hunting);
		this.setMembers(members);
		this.setActive(true);
		this.setGathering(false);
		this.setGatherTimeRemaining(0);
		this.setObtained(new HashMap<Resource, Double>());
	}

	/**
	 * Retrieves the members of this party.
	 * @return The members of this party.
	 */
	public List<Person> getMembers() {
		return members;
	}

	/**
	 * Sets the members of this party.
	 * @param members - New members of this party.
	 */
	public void setMembers(List<Person> members) {
		this.members = members;
	}

	/**
	 * Retrieves if this party is hunting or gathering.
	 * @return True if this party is hunting, false if this party is gathering.
	 */
	public boolean isHunting() {
		return hunting;
	}

	/**
	 * Sets if this party is hunting or gathering.
	 * @param hunting - True if this party will hunt, false if it will gather.
	 */
	public void setHunting(boolean hunting) {
		this.hunting = hunting;
	}
	
	/**
	 * Adds a person to this hunting party.
	 * @param newMember - Person to add.
	 */
	public void addMember(Person newMember) {
		newMember.setInParty(true);
		this.getMembers().add(newMember);
	}
	
	/**
	 * Removes a person from this hunting party.
	 * @param toRemove - Person to remove.
	 */
	public void removeMember(Person toRemove) {
		toRemove.setInParty(false);
		this.getMembers().remove(toRemove);
	}
	
	/**
	 * Retrieves the size of this party.
	 * @return The size of this party
	 */
	public int size() {
		return this.getMembers().size();
	}

	/**
	 * Retrieves if this party is currently active.
	 * @return True if party is still active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets if this party is active.
	 * @param active - True if party is active.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Retrieves if party is currently exploiting a resource. Not to be confused with isHunting.
	 * @return True if party is exploiting a resource.
	 */
	public boolean isGathering() {
		return isGathering;
	}

	/**
	 * Sets if party is currently exploiting a resource. Not to be confused with setHunting.
	 * @param isGathering - True if party is exploiting a resource.
	 */
	public void setGathering(boolean isGathering) {
		this.isGathering = isGathering;
	}

	/**
	 * Retrieves the gathering time remaining.
	 * @return The gather time remaining.
	 */
	public int getGatherTimeRemaining() {
		return gatherTimeRemaining;
	}

	/**
	 * Sets the gathering time remaining.
	 * @param gatherTimeRemaining - The gather time remaining to set.
	 */
	public void setGatherTimeRemaining(int gatherTimeRemaining) {
		this.gatherTimeRemaining = gatherTimeRemaining;
	}

	/**
	 * Retrieves the current weight carried by this party.
	 * @return Sum of the weights of all obtained resources.
	 */
	public double getCurrentWeight() {
		double currentWeight = 0.0;
		
		for(Double weight: this.obtained.values()) {
			currentWeight += weight.doubleValue();
		}
		
		return currentWeight;
	}
	
	/**
	 * Retrieves the maximum weight this party can carry.
	 * @return Maximum weight limit of party.
	 */
	public double getMaxWeight() {
		double maxWeight = 0.0;
		for(Person person: this.members) {
			maxWeight += person.getCarryWeight();
		}
		return maxWeight;
	}

	/**
	 * Retrieves the current carried calories of this party.
	 * @return The current calories of party.
	 */
	public double getCurrentCals() {
		double currentCals = 0.0;
		
		for(Resource resource: this.obtained.keySet()) {
			currentCals += this.obtained.get(resource) * (resource.getCalories() / resource.getWeight());
		}
		
		return currentCals;
	}

	/**
	 * Retrieves the obtained resources of this party.
	 * @return The obtained resources.
	 */
	public Map<Resource, Double> getObtained() {
		return this.obtained;
	}

	/**
	 * Sets the obtained resources of this party.
	 * @param obtained - the obtained resources map to set.
	 */
	public void setObtained(Map<Resource, Double> obtained) {
		this.obtained = obtained;
	}
	
	/**
	 * Adds an amount of a resource to the party's currently obtained resources.
	 * @param resource - Resource to add.
	 * @param amount - Pounds of resource to add.
	 */
	private void addResource(Resource resource, double amount) {
		this.obtained.put(resource, this.obtained.getOrDefault(resource, 0.0) + amount);
	}
	
	/**
	 * Removes an amount of a resource from a party's currently obtained resources.
	 * @param resource - Resource to remove.
	 * @param amount - Pounds of resource to remove.
	 */
	private void removeResource(Resource resource, double amount) {
		this.obtained.put(resource, this.obtained.getOrDefault(resource, 0.0) - amount);
		if(this.obtained.get(resource) <= 0.0) {
			this.obtained.remove(resource);
		}
	}
	
	/**
	 * Optimizes the currently carried resources. Removes enough of the lowest cal/lb ratio resource to fit requested new resource.
	 * @param toAdd - Resource to add.
	 * @param amount - Pounds of resource to add.
	 */
	public void optimizeResources(Resource toAdd, double amount) {
		if(this.getCurrentWeight() + amount < this.getMaxWeight()) {
			addResource(toAdd, amount);
			return;
		}
		
		if(this.getCurrentWeight() < this.getMaxWeight()) {
			addResource(toAdd, this.getMaxWeight() - this.getCurrentWeight());
		}
		
		Resource lowestValue = toAdd;
		
		for(Resource resource: this.obtained.keySet()) {
			if(resource.getCalories() / resource.getWeight() < lowestValue.getCalories() / lowestValue.getWeight()) {
				lowestValue = resource;
			}
		}
		
		if(lowestValue.getName().equals(toAdd.getName())) {
			addResource(toAdd, this.getMaxWeight() - this.getCurrentWeight());
		} else {
			removeResource(lowestValue, this.getCurrentWeight() + amount - this.getMaxWeight());
			this.optimizeResources(toAdd, amount);
		}
	}
	
	/**
	 * Advances the minutes foraged for each member by one minute.
	 */
	public void advanceMinutesForaged() {
		for(Person person: this.getMembers()) {
			person.setMinutesForaged(person.getMinutesForaged() + 1);
		}
	}
	
	/**
	 * Resets all member's minutes foraged to 0.
	 */
	public void newDay() {
		for(Person person: this.getMembers()) {
			person.setMinutesForaged(0);
		}
	}
}
