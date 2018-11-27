package people;

/**
 * This class represents a single member of a hunter-gatherer group.
 * @author Kyle McVay
 */
public class Person {
	private int daysDeficient;
	private int deficientStreak;
	private boolean inParty;
	
	/** The maximum weight a person can carry */
	public static final int MAX_WEIGHT = 30;
	
	/** The number of calories needed per day from meat */
	public static final int CALS_NEEDED_MEAT = 1000;
	
	/** The number of calories needed per day from plants */
	public static final int CALS_NEEDED_PLANT = 1000;
	
	/**
	 * Initializes a new Person with default values
	 */
	public Person() {
		this.daysDeficient = 0;
		this.deficientStreak = 0;
		this.setInParty(false);
	}

	/**
	 * @return The number of days this person has been deficient in calories total
	 */
	public int getDaysDeficient() {
		return daysDeficient;
	}
	
	/**
	 * @return Number of days in a row this person has not met calorie requirements
	 */
	public int getDeficientStreak() {
		return this.deficientStreak;
	}
	
	/**
	 * Indicates that this person did not meet their calorie requirements from meat for a day
	 * @throws StarvationException 
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
	 * @return True if person is in a party
	 */
	public boolean isInParty() {
		return inParty;
	}

	/**
	 * @param inParty - Sets if person is in a party
	 */
	public void setInParty(boolean inParty) {
		this.inParty = inParty;
	}
	
	public void feed(double calsMeat, double calsPlants) throws StarvationException {
		if(calsMeat < CALS_NEEDED_MEAT || calsPlants < CALS_NEEDED_PLANT) {
			defficientDay();
		} else {
			resetDefecientStreak();
		}
	}
	
	public double getCarryWeight() {
		return MAX_WEIGHT * (1 - this.deficientStreak * .05);
	}
}
