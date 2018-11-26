package genetics;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
	private String id;
	protected List<Connection> inputConnections;
	protected List<Connection> outputConnections;
	protected InputSummingFunction inputSummingFunction;
	protected ActivationFunction activationFunction;
	
	public Neuron() {
		this.inputConnections = new ArrayList<>();
		this.outputConnections = new ArrayList<>();
		this.inputSummingFunction = new InputSummingFunction();
		this.activationFunction = new ActivationFunction();
	}
	
	public double calculateOutput() {
		double totalInput = inputSummingFunction.getOutput(inputConnections);
		
		return activationFunction.getOutput(totalInput);
	}
}
