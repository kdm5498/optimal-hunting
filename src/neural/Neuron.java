package neural;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
	private String id;
	private List<Connection> inputConnections;
	private List<Connection> outputConnections;
	private InputSummingFunction inputSummingFunction;
	private ActivationFunction activationFunction;
	
	public Neuron() {
		this.setInputConnections(new ArrayList<>());
		this.setOutputConnections(new ArrayList<>());
		this.setInputSummingFunction(new InputSummingFunction());
		this.setActivationFunction(new ActivationFunction());
	}
	
	public Neuron(String id) {
		this();
		this.setId(id);
	}
	
	public double calculateOutput() {
		double totalInput = inputSummingFunction.getOutput(inputConnections);
		
		return activationFunction.getOutput(totalInput);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the outputConnections
	 */
	public List<Connection> getOutputConnections() {
		return outputConnections;
	}

	/**
	 * @param outputConnections the outputConnections to set
	 */
	public void setOutputConnections(List<Connection> outputConnections) {
		this.outputConnections = outputConnections;
	}

	/**
	 * @return the inputConnections
	 */
	public List<Connection> getInputConnections() {
		return inputConnections;
	}

	/**
	 * @param inputConnections the inputConnections to set
	 */
	public void setInputConnections(List<Connection> inputConnections) {
		this.inputConnections = inputConnections;
	}

	/**
	 * @return the inputSummingFunction
	 */
	public InputSummingFunction getInputSummingFunction() {
		return inputSummingFunction;
	}

	/**
	 * @param inputSummingFunction the inputSummingFunction to set
	 */
	public void setInputSummingFunction(InputSummingFunction inputSummingFunction) {
		this.inputSummingFunction = inputSummingFunction;
	}

	/**
	 * @return the activationFunction
	 */
	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	/**
	 * @param activationFunction the activationFunction to set
	 */
	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}
}
