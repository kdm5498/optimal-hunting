package people;

/**
 * This class represents a single member of a hunter-gatherer group.
 * @author Kyle McVay
 */
public class Person {
	private int calsMeat;
	private int calsPlant;
	private int daysDeficient;
	private int deficientStreak;
	
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
		setCalsMeat(0);
		setCalsPlant(0);
		this.daysDeficient = 0;
		this.deficientStreak = 0;
	}

	/**
	 * @return Number of calories from meat this person has eaten today
	 */
	public int getCalsMeat() {
		return calsMeat;
	}

	/**
	 * @param calsMeat - The number of calories from meat this person has now eaten today
	 */
	public void setCalsMeat(int calsMeat) {
		this.calsMeat = calsMeat;
	}

	/**
	 * @return Number of calories from plants this person has eaten today
	 */
	public int getCalsPlant() {
		return calsPlant;
	}

	/**
	 * @param calsPlant - The number of calories from plants this person has now eaten today
	 */
	public void setCalsPlant(int calsPlant) {
		this.calsPlant = calsPlant;
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
	 */
	public void defficientDay() {
		this.daysDeficient++;
		this.deficientStreak++;
	}
	
	/**
	 * Resets the deficiency streak for this person
	 */
	public void resetDefecientStreak() {
		this.deficientStreak = 0;
	}
}
