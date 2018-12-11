package main.java.neural;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Neuron.
 * @author Kyle McVay
 */
public class Neuron {
	private String id;
	private List<Connection> inputConnections;
	private List<Connection> outputConnections;
	private InputSummingFunction inputSummingFunction;
	private ActivationFunction activationFunction;
	
	/**
	 * Constructs a new Neuron with default values.
	 */
	public Neuron() {
		this.setInputConnections(new ArrayList<>());
		this.setOutputConnections(new ArrayList<>());
		this.setInputSummingFunction(new InputSummingFunction());
		this.setActivationFunction(new ActivationFunction());
	}
	
	/**
	 * Constructs a new Neuron with specified id.
	 * @param id - Id to set.
	 */
	public Neuron(String id) {
		this();
		this.setId(id);
	}
	
	/**
	 * Calculates the output of this neuron.
	 * @return Sum of the input connections passed through activation function.
	 */
	public double calculateOutput() {
		double totalInput = this.getInputSummingFunction().getOutput(this.getInputConnections());
		
		return this.getActivationFunction().getOutput(totalInput);
	}

	/**
	 * retrieves the id of this neuron.
	 * @return The id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this neuron.
	 * @param id - The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves the output connections of this neuron.
	 * @return The output connections.
	 */
	public List<Connection> getOutputConnections() {
		return outputConnections;
	}

	/**
	 * Sets the output connections of this neuron.
	 * @param outputConnections - The output connections to set.
	 */
	public void setOutputConnections(List<Connection> outputConnections) {
		this.outputConnections = outputConnections;
	}

	/**
	 * Retrieves the input connections of this neuron.
	 * @return The input connections.
	 */
	public List<Connection> getInputConnections() {
		return inputConnections;
	}

	/**
	 * Sets the input connections of this neuron.
	 * @param inputConnections - the input connections to set.
	 */
	public void setInputConnections(List<Connection> inputConnections) {
		this.inputConnections = inputConnections;
	}

	/**
	 * Retrieves input summing function of this neuron.
	 * @return The input summing function.
	 */
	public InputSummingFunction getInputSummingFunction() {
		return inputSummingFunction;
	}

	/**
	 * Sets the input summing function of this neuron.
	 * @param inputSummingFunction - the input summing function to set.
	 */
	public void setInputSummingFunction(InputSummingFunction inputSummingFunction) {
		this.inputSummingFunction = inputSummingFunction;
	}

	/**
	 * Retrieves the activation function of this neuron.
	 * @return The activation function.
	 */
	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	/**
	 * Sets the activation function of this neuron.
	 * @param activationFunction - The activation function to set.
	 */
	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}
}
