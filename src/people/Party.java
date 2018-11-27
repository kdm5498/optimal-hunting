package people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resource.Resource;

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
	 * Initializes a new party with an empty list of members
	 */
	public Party(boolean hunting) {
		this(hunting, new ArrayList<Person>());
	}
	
	/**
	 * Initializes a new party with the given list of members
	 * @param members - Members to place in this party
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
	 * @return The members of this party
	 */
	public List<Person> getMembers() {
		return members;
	}

	/**
	 * @param members - New members of this party
	 */
	public void setMembers(List<Person> members) {
		this.members = members;
	}

	/**
	 * @return True if this party is hunting, false if this party is gathering
	 */
	public boolean isHunting() {
		return hunting;
	}

	/**
	 * @param hunting - True if this party will hunt, false if it will gather
	 */
	public void setHunting(boolean hunting) {
		this.hunting = hunting;
	}
	
	/**
	 * Adds a person to this hunting party
	 * @param newMember - Person to add
	 */
	public void addMember(Person newMember) {
		newMember.setInParty(true);
		this.getMembers().add(newMember);
	}
	
	/**
	 * Removes a person from this hunting party
	 * @param toRemove - Person to remove
	 */
	public void removeMember(Person toRemove) {
		toRemove.setInParty(false);
		this.getMembers().remove(toRemove);
	}
	
	/**
	 * @return The size of this party
	 */
	public int size() {
		return this.getMembers().size();
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the isGathering
	 */
	public boolean isGathering() {
		return isGathering;
	}

	/**
	 * @param isGathering the isGathering to set
	 */
	public void setGathering(boolean isGathering) {
		this.isGathering = isGathering;
	}

	/**
	 * @return the gatherTimeRemaining
	 */
	public int getGatherTimeRemaining() {
		return gatherTimeRemaining;
	}

	/**
	 * @param gatherTimeRemaining the gatherTimeRemaining to set
	 */
	public void setGatherTimeRemaining(int gatherTimeRemaining) {
		this.gatherTimeRemaining = gatherTimeRemaining;
	}

	/**
	 * @return the currentWeight
	 */
	public double getCurrentWeight() {
		double currentWeight = 0.0;
		
		for(Double weight: this.obtained.values()) {
			currentWeight += weight.doubleValue();
		}
		
		return currentWeight;
	}
	
	public double getMaxWeight() {
		double maxWeight = 0.0;
		for(Person person: this.members) {
			maxWeight += person.getCarryWeight();
		}
		return maxWeight;
	}

	/**
	 * @return the currentCals
	 */
	public double getCurrentCals() {
		double currentCals = 0.0;
		
		for(Resource resource: this.obtained.keySet()) {
			currentCals += this.obtained.get(resource) * (resource.getCalories() / resource.getWeight());
		}
		
		return currentCals;
	}

	/**
	 * @return the obtained
	 */
	public Map<Resource, Double> getObtained() {
		return this.obtained;
	}

	/**
	 * @param obtained the obtained to set
	 */
	public void setObtained(Map<Resource, Double> obtained) {
		this.obtained = obtained;
	}
	
	private void addResource(Resource resource, double amount) {
		this.obtained.put(resource, this.obtained.getOrDefault(resource, 0.0) + amount);
	}
	
	private void removeResource(Resource resource, double amount) {
		this.obtained.put(resource, this.obtained.getOrDefault(resource, 0.0) - amount);
		if(this.obtained.get(resource) <= 0.0) {
			this.obtained.remove(resource);
		}
	}
	
	public void optimizeResources(Resource toAdd, double amount) {
		if(this.getCurrentWeight() + amount < this.getMaxWeight()) {
			addResource(toAdd, amount);
			return;
		}
		
		Resource lowestValue = toAdd;
		
		for(Resource resource: this.obtained.keySet()) {
			if(resource.getCalories() / resource.getWeight() < lowestValue.getCalories() / lowestValue.getWeight()) {
				lowestValue = resource;
			}
		}
		
		if(lowestValue.equals(toAdd)) {
			addResource(toAdd, this.getMaxWeight() - this.getCurrentWeight());
		} else {
			removeResource(lowestValue, this.getCurrentWeight() + amount - this.getMaxWeight());
			optimizeResources(toAdd, amount);
		}
	}
}
