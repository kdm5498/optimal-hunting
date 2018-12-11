package main.java.neural;

/**
 * This class represents a connection between two neurons.
 * @author Kyle McVay
 */
public class Connection {
	protected Neuron fromNeuron;
	protected Neuron toNeuron;
	protected double weight;
	
	/**
	 * Constructs a new Connection with random weighting.
	 * @param fromNeuron - Neuron connection is from.
	 * @param toNeuron - Neuron connection is to.
	 */
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.setWeight(Math.random());
	}
	
	/**
	 * Constructs a new Connection.
	 * @param fromNeuron - Neuron connection is from.
	 * @param toNeuron - Neuron connection is to.
	 * @param weight - Weight of connection.
	 */
	public Connection(Neuron fromNeuron, Neuron toNeuron, double weight) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.setWeight(weight);
	}
	
	/**
	 * Retrieves the weight of this connection.
	 * @return Weight of connection.
	 */
	public double getWeight() {
		return this.weight;
	}
	
	/**
	 * Sets the weight of this connection.
	 * @param weight - Weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * Retrieves the weighted input of this connection. Weighted input is the output of the from Neuron multiplied by the weight.
	 * @return Weighted input.
	 */
	public double getWeightedInput() {
		return this.getFromNeuron().calculateOutput() * this.getWeight();
	}
	
	/**
	 * Retrieves the Neuron this connection is from.
	 * @return Neuron connection is from.
	 */
	public Neuron getFromNeuron() {
		return this.fromNeuron;
	}
	
	/**
	 * Retrieves the Neuron this connection is to.
	 * @return Neuron connection is to.
	 */
	public Neuron getToNeuron() {
		return this.toNeuron;
	}
}
