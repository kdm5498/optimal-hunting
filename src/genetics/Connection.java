package genetics;

public class Connection {
	protected Neuron fromNeuron;
	protected Neuron toNeuron;
	protected double weight;
	
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = Math.random();
	}
	
	public Connection(Neuron fromNeuron, Neuron toNeuron, double weight) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = weight;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeightedInput() {
		return fromNeuron.calculateOutput() * weight;
	}
	
	public Neuron getFromNeuron() {
		return this.fromNeuron;
	}
	
	public Neuron getToNeuron() {
		return this.toNeuron;
	}
}
