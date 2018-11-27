package people;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a group of hunters and gatherers. A group consists of many people and several parties.
 * @author Kyle McVay
 */
public class Group {
	private List<Party> parties;
	private List<Person> members;
	private double excessMeat;
	private double excessPlants;
	
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
	}

	/**
	 * @return List of all parties currently active in this group
	 */
	public List<Party> getParties() {
		return parties;
	}

	/**
	 * @param parties - List of parties in this group
	 */
	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	/**
	 * @return Members of this group
	 */
	public List<Person> getMembers() {
		return members;
	}

	/**
	 * @param members - Members of this group to set
	 */
	public void setMembers(List<Person> members) {
		this.members = members;
	}
	
	/**
	 * @return The number of members of this group currently assigned to a party
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
	
	public void feedGroup() throws StarvationException {
		double totalCalsMeat = 0.0;
		double totalCalsPlants = 0.0;
		
		for(Party party: this.parties) {
			if(party.isHunting()) {
				totalCalsMeat += party.getCurrentCals();
			} else {
				totalCalsPlants += party.getCurrentCals();
			}
		}
		
		// New resources
		double shareMeat = totalCalsMeat / this.members.size();
		double sharePlants = totalCalsPlants / this.members.size();
		
		// Take surplus from stores as needed, or add surplus to stores
		if(shareMeat < Person.CALS_NEEDED_MEAT) {
			double difference = Person.CALS_NEEDED_MEAT - shareMeat;
			double neededSurplus = difference * this.members.size();
			if(neededSurplus < this.excessMeat) {
				shareMeat = Person.CALS_NEEDED_MEAT;
				this.excessMeat -= neededSurplus;
			} else {
				double availableSurplus = this.excessMeat / this.members.size();
				shareMeat += availableSurplus;
				this.excessMeat = 0;
			}
		} else {
			this.excessMeat += (shareMeat - Person.CALS_NEEDED_MEAT) * members.size();
		}
		if(sharePlants < Person.CALS_NEEDED_PLANT) {
			double difference = Person.CALS_NEEDED_PLANT - sharePlants;
			double neededSurplus = difference * this.members.size();
			if(neededSurplus < this.excessPlants) {
				sharePlants = Person.CALS_NEEDED_PLANT;
				this.excessPlants -= neededSurplus;
			} else {
				double availableSurplus = this.excessPlants / this.members.size();
				sharePlants += availableSurplus;
				this.excessPlants = 0;
			}
		} else {
			this.excessPlants += (sharePlants - Person.CALS_NEEDED_PLANT) * members.size();
		}
		
		// Feed each person their share
		for(Person member: this.members) {
			member.feed(shareMeat, sharePlants);
		}
	}

	/**
	 * @return the excessMeat
	 */
	public double getExcessMeat() {
		return excessMeat;
	}

	/**
	 * @param excessMeat the excessMeat to set
	 */
	public void setExcessMeat(double excessMeat) {
		this.excessMeat = excessMeat;
	}

	/**
	 * @return the excessPlants
	 */
	public double getExcessPlants() {
		return excessPlants;
	}

	/**
	 * @param excessPlants the excessPlants to set
	 */
	public void setExcessPlants(double excessPlants) {
		this.excessPlants = excessPlants;
	}
}
