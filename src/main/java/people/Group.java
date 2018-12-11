package main.java.people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.resource.Resource;

/**
 * This class represents a group of hunters and gatherers. A group consists of many people and several parties.
 * @author Kyle McVay
 */
public class Group {
	private List<Party> parties;
	private List<Person> members;
	private double excessMeat;
	private double excessPlants;
	private Map<Resource, Integer> totalGathered;
	private int daysSurvived = 0;
	
	/**
	 * Initializes a new group with default values
	 */
	public Group() {
		this(new ArrayList<Party>(), new ArrayList<Person>());
	}
	
	/**
	 * Initializes a new group with the specified list of parties and members
	 * @param parties - Parties in the group
	 * @param members - Members of the group
	 */
	public Group(List<Party> parties, List<Person> members) {
		this.setParties(parties);
		this.setMembers(members);
		this.setTotalGathered(new HashMap<Resource, Integer>());
	}

	/**
	 * Retrieves all parties in this group.
	 * @return List of all parties currently active in this group.
	 */
	public List<Party> getParties() {
		return parties;
	}

	/**
	 * Sets the list of parties in this group.
	 * @param parties - List of parties in this group.
	 */
	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	/**
	 * Retrieves all members in this group.
	 * @return Members of this group.
	 */
	public List<Person> getMembers() {
		return members;
	}

	/**
	 * Sets the list of members in this group.
	 * @param members - Members of this group to set.
	 */
	public void setMembers(List<Person> members) {
		this.members = members;
	}
	
	/**
	 * Retrieves the number of assigned members of this group.
	 * @return The number of members of this group currently assigned to a party.
	 */
	public int allocatedMembers() {
		int allocated = 0;
		
		for(Party party: this.getParties()) {
			allocated += party.size();
		}
		
		return allocated;
	}
	
	/**
	 * Allocates members of the group to a hunting or gathering party.
	 * 
	 * @param partySize - Size of party to allocate
	 * @param hunting - Type of party to create, True for a hunting party, false for a gathering party.
	 * 
	 * @throws IllegalArgumentException - Thrown if there are not enough unallocated members to form a party of the requested size
	 */
	public void allocateParty(int partySize, boolean hunting) {
		// Check to make sure there are enough free members
		if(this.getMembers().size() - this.allocatedMembers() < partySize) {
			throw new IllegalArgumentException("Not enough free members to allocate a party of that size");
		}
		
		// Create a new party
		Party newParty = new Party(hunting);
		
		// Allocate unassigned members to the party
		for(Person member: this.getMembers()) {
			if(!member.isInParty()) {
				newParty.addMember(member);
			}
			if(newParty.size() == partySize) {
				break;
			}
		}
		
		// Add the allocated party to the list of parties
		this.getParties().add(newParty);
	}
	
	/**
	 * Feeds all members of this group. and adds extra resources to excess stores.
	 * @throws StarvationException Thrown if a member of the group starves to death.
	 */
	public void feedGroup() throws StarvationException {
		double totalCalsMeat = 0.0;
		double totalCalsPlants = 0.0;
		
		// Add up every group's contribution
		for(Party party: this.getParties()) {
			if(party.isHunting()) {
				totalCalsMeat += party.getCurrentCals();
			} else {
				totalCalsPlants += party.getCurrentCals();
			}
		}
		
		// New resources, share equally to start
		double shareMeat = totalCalsMeat / this.getMembers().size();
		double sharePlants = totalCalsPlants / this.getMembers().size();
		
		double redistributedMeat = 0;
		double redistributedPlants = 0;
		Map<Person, Double> sharesMeat = new HashMap<Person, Double>();
		Map<Person, Double> sharesPlants = new HashMap<Person, Double>();
		
		// If anyone has more than they need, put it back in pool for distribution
		for(Person member: this.getMembers()) {
			sharesMeat.put(member, shareMeat);
			sharesPlants.put(member, sharePlants);
			if(member.getCalsMeatNeeded() < shareMeat) {
				redistributedMeat += shareMeat - member.getCalsMeatNeeded();
				sharesMeat.put(member, member.getCalsMeatNeeded());
			}
			if(member.getCalsPlantsNeeded() < sharePlants) {
				redistributedPlants += sharePlants - member.getCalsPlantsNeeded();
				sharesPlants.put(member, member.getCalsPlantsNeeded());
			}
		}
		
		// Fill out calories for as many members as possible from extra food
		for(Person member: this.getMembers()) {
			if(member.getCalsMeatNeeded() > sharesMeat.get(member)) {
				double calsNeeded = member.getCalsMeatNeeded() - sharesMeat.get(member);
				if(redistributedMeat >= calsNeeded) {
					redistributedMeat -= calsNeeded;
					sharesMeat.put(member, member.getCalsMeatNeeded());
				} else {
					sharesMeat.put(member, sharesMeat.get(member) + redistributedMeat);
					redistributedMeat = 0;
				}
			}
			if(member.getCalsPlantsNeeded() > sharesPlants.get(member)) {
				double calsNeeded = member.getCalsPlantsNeeded() - sharesPlants.get(member);
				if(redistributedPlants >= calsNeeded) {
					redistributedPlants -= calsNeeded;
					sharesPlants.put(member, member.getCalsPlantsNeeded());
				} else {
					sharesPlants.put(member, sharesPlants.get(member) + redistributedPlants);
					redistributedPlants = 0;
				}
			}
		}
		
		// Add anything left to score
		excessMeat += redistributedMeat;
		excessPlants += redistributedPlants;
		
		// Feed each person their share
		for(Person member: this.getMembers()) {
			member.feed(sharesMeat.get(member), sharesPlants.get(member));
		}
	}

	/**
	 * Retrieves the excess meat gathered by this group.
	 * @return The excess meat in calories.
	 */
	public double getExcessMeat() {
		return excessMeat;
	}

	/**
	 * Sets the excess meat gathered by this group.
	 * @param excessMeat - The excess meat to set in calories.
	 */
	public void setExcessMeat(double excessMeat) {
		this.excessMeat = excessMeat;
	}

	/**
	 * Retrieves the excess plants gathered by this group.
	 * @return The excess plants in calories.
	 */
	public double getExcessPlants() {
		return excessPlants;
	}

	/**
	 * Sets the excess plants gathered by this group.
	 * @param excessPlants - The excess plants to set in calories.
	 */
	public void setExcessPlants(double excessPlants) {
		this.excessPlants = excessPlants;
	}

	/**
	 * Retrieves all the resources gathered by this group.
	 * @return The total resources gathered.
	 */
	public Map<Resource, Integer> getTotalGathered() {
		return totalGathered;
	}

	/**
	 * Sets the resources gathered by this group.
	 * @param totalGathered - The total resources gathered to set.
	 */
	public void setTotalGathered(Map<Resource, Integer> totalGathered) {
		this.totalGathered = totalGathered;
	}

	/**
	 * Retrieves the number of days this group has survived.
	 * @return The days survived.
	 */
	public int getDaysSurvived() {
		return daysSurvived;
	}

	/**
	 * Sets the days that this group has survived.
	 * @param daysSurvived - The days survived to set.
	 */
	public void setDaysSurvived(int daysSurvived) {
		this.daysSurvived = daysSurvived;
	}
	
	/**
	 * Gets the max number of days a member of the group was deficient in calories.
	 * @return Days a member was deficient in calories.
	 */
	public int daysDeficient() {
		int max = 0;
		for(Person member: this.getMembers()) {
			if(member.getDaysDeficient() > max) {
				max = member.getDaysDeficient();
			}
		}
		return max;
	}
}
