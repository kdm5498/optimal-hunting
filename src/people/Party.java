package people;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a hunting or gathering party. A party is made up of a number of People, and events occur on a per party basis.
 * @author Kyle McVay
 */
public class Party {
	private List<Person> members;
	private boolean hunting;
	
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
		this.getMembers().add(newMember);
	}
	
	/**
	 * Removes a person from this hunting party
	 * @param toRemove - Person to remove
	 */
	public void removeMember(Person toRemove) {
		this.getMembers().remove(toRemove);
	}
}
