package main.java.neural;

/**
 * This class represents an input neuron. It allows for a value to be input rather than an input neuron.
 * @author Kyle McVay
 */
public class InputNeuron extends Neuron {
	private double value;
	
	/**
	 * Constructs a new Input Neuron with default values.
	 */
	public InputNeuron() {
		super();
	}
	
	/**
	 * Constructs a new Input Neuron with specified value.
	 * @param value - Initial value of Neuron.
	 */
	public InputNeuron(double value) {
		this();
		this.setValue(value);
	}
	
	/**
	 * Constructs a new Input Neuron with specified value and id.
	 * @param value - Initial value of Neuron.
	 * @param id - Id of Neuron.
	 */
	public InputNeuron(double value, String id) {
		this(value);
		this.setId(id);
	}
	
	/**
	 * Calculates the output of this Neuron
	 * @return Value of this Neuron.
	 */
	@Override
	public double calculateOutput() {
		return this.getValue();
	}

	/**
	 * Retrieves the value of this Neuron.
	 * @return Value of neuron.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value of this Neuron.
	 * @param value - Value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
