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
}
