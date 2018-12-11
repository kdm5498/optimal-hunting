package main.java.people;

/**
 * This class represents a single member of a hunter-gatherer group.
 * @author Kyle McVay
 */
public class Person {
	private int daysDeficient;
	private int deficientStreak;
	private boolean inParty;
	private int minutesForaged;
	
	/** The maximum weight a person can carry */
	public static int MAX_WEIGHT;
	/** The number of calories this person burns per minute at rest */
	public static int CALS_BURNED_AT_REST;
	/** The number of calories this person burns per minute while foraging */
	public static int CALS_BURNED_FORAGING;
	/** The ratio of meat calories to plant calories this person requires */
	public static double MEAT_RATIO;
	
	/**
	 * Initializes a new Person with default values
	 */
	public Person() {
		this.daysDeficient = 0;
		this.deficientStreak = 0;
		this.setInParty(false);
	}

	/**
	 * Retrieves the number of days this person has been calorie deficient total.
	 * @return The number of days this person has been deficient in calories total
	 */
	public int getDaysDeficient() {
		return daysDeficient;
	}
	
	/**
	 * Sets the number of days this person has been calorie deficient total.
	 * @return Number of days in a row this person has not met calorie requirements
	 */
	public int getDeficientStreak() {
		return this.deficientStreak;
	}
	
	/**
	 * Indicates that this person did not meet their calorie requirements from meat for a day.
	 * @throws StarvationException Thrown if person has starved.
	 */
	public void defficientDay() throws StarvationException {
		this.daysDeficient++;
		this.deficientStreak++;
		if(this.deficientStreak >= 20) {
			throw new StarvationException("This person has starved");
		}
	}
	
	/**
	 * Resets the deficiency streak for this person
	 */
	public void resetDefecientStreak() {
		this.deficientStreak = 0;
	}

	/**
	 * Retrieves if person is in a party.
	 * @return True if person is in a party
	 */
	public boolean isInParty() {
		return inParty;
	}

	/**
	 * Sets if person is in party.
	 * @param inParty - Sets if person is in a party
	 */
	public void setInParty(boolean inParty) {
		this.inParty = inParty;
	}
	
	/**
	 * Feeds this person. If not enough calories are given, adds a deficient day.
	 * @param calsMeat - Calories of meat to feed.
	 * @param calsPlants - Calories of plants to feed.
	 * @throws StarvationException Thrown if person has starved.
	 */
	public void feed(double calsMeat, double calsPlants) throws StarvationException {
		if(calsMeat < this.getCalsMeatNeeded() || calsPlants < this.getCalsPlantsNeeded()) {
			defficientDay();
		} else {
			resetDefecientStreak();
		}
	}
	
	/**
	 * Retrieves the carry weight of this person. Carry weight is affected by the number of days in a row this person has been calorie deficient.
	 * @return Carry weight of person.
	 */
	public double getCarryWeight() {
		return MAX_WEIGHT * (1 - this.deficientStreak * .05);
	}

	/**
	 * Retrieves the number of minutes this person has foraged.
	 * @return The minutes foraged.
	 */
	public int getMinutesForaged() {
		return minutesForaged;
	}

	/**
	 * Sets the number of minutes this person has foraged.
	 * @param minutesForaged - The minutes foraged to set.
	 */
	public void setMinutesForaged(int minutesForaged) {
		this.minutesForaged = minutesForaged;
	}
	
	/**
	 * Retrieves the number of calories from meat this person needs for this day.
	 * @return Calories needed.
	 */
	public double getCalsMeatNeeded() {
		return ((getMinutesForaged() * CALS_BURNED_FORAGING) + (((24 * 60) - getMinutesForaged()) * CALS_BURNED_AT_REST)) * MEAT_RATIO;
	}
	
	/**
	 * Retrieves the number of calories from plants this person needs for this day.
	 * @return Calories needed.
	 */
	public double getCalsPlantsNeeded() {
		return ((getMinutesForaged() * CALS_BURNED_FORAGING) + (((24 * 60) - getMinutesForaged()) * CALS_BURNED_AT_REST)) * (1 - MEAT_RATIO);
	}
}
