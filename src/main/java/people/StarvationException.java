package main.java.people;

/**
 * Represents a person starving from lack of calories.
 * @author Kyle McVay
 */
public class StarvationException extends Exception {
	private static final long serialVersionUID = 4424016842649657312L;

	/**
	 * Constructs a new starvation exception with specified message.
	 * @param message - Message of exception.
	 */
	public StarvationException(String message) {
		super(message);
	}
}
