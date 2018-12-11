package main.java.neural;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a layer of a neural network. A layer contains many neurons.
 * @author Kyle McVay
 */
public class Layer {
	private String id;
	private List<Neuron> neurons;
	
	/**
	 * Constructs a new Layer with an empty list of neurons.
	 * @param id - Id of layer.
	 */
	public Layer(String id) {
		this.setId(id);
		this.setNeurons(new ArrayList<>());
	}
	
	/**
	 * Constructs a new layer with the specified list of neurons.
	 * @param id - Id of layer.
	 * @param neurons - List of neurons.
	 */
	public Layer(String id, List<Neuron> neurons) {
		this.setNeurons(neurons);
	}

	/**
	 * Retrieves the list of neurons in this layer.
	 * @return Neurons in this layer.
	 */
	public List<Neuron> getNeurons() {
		return neurons;
	}

	/**
	 * Sets the list of neurons in this layer.
	 * @param neurons - The neurons to set.
	 */
	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	/**
	 * Retrieves the id of this layer.
	 * @return Id of layer.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this layer.
	 * @param id - The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retrieves the first neuron in this layer specified by the given id.
	 * @param id - Id to find.
	 * @return Found neuron, or null if not found.
	 */
	public Neuron getNeuron(String id) {
		Neuron neuron = null;
		
		for(Neuron n: neurons) {
			if(n.getId().equals(id)) {
				neuron = n;
				break;
			}
		}
		
		return neuron;
	}
}
