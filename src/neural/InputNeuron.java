package neural;

public class InputNeuron extends Neuron {
	private double value;
	
	public InputNeuron() {
		super();
	}
	
	public InputNeuron(double value) {
		this();
		this.setValue(value);
	}
	
	public InputNeuron(double value, String id) {
		this(value);
		this.setId(id);
	}
	
	@Override
	public double calculateOutput() {
		return value;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
