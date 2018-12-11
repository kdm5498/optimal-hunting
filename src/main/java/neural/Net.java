package main.java.neural;

import java.util.List;

/**
 * This class represents a full neural network. A network contains at least an input and output layer, and possibly a hidden layer.
 * @author Kyle McVay
 */
public class Net {
	private String id;
	
	private Layer inputLayer;
	private List<Layer> hiddenLayers;
	private Layer outputLayer;
	
	/**
	 * Constructs a new Neural Net with specified input, hidden and output layers.
	 * @param id - Id of network.
	 * @param inputLayer - Input layer of network.
	 * @param hiddenLayers - Hidden layers of network.
	 * @param outputLayer - Output layer of network.
	 */
	public Net(String id, Layer inputLayer, List<Layer> hiddenLayers, Layer outputLayer) {
		this.setId(id);
		this.setInputLayer(inputLayer);
		this.setHiddenLayers(hiddenLayers);
		this.setOutputLayer(outputLayer);
	}
	
	/**
	 * Constructs a new Neural Net with specified input and output layers.
	 * @param id - Id of network.
	 * @param inputLayer - Input layer of network.
	 * @param outputLayer - Output layer of network.
	 */
	public Net(String id, Layer inputLayer, Layer outputLayer) {
		this.setId(id);
		this.setInputLayer(inputLayer);
		this.setOutputLayer(outputLayer);
	}

	/**
	 * Retrieves the input layer of this network.
	 * @return The input layer.
	 */
	public Layer getInputLayer() {
		return inputLayer;
	}

	/**
	 * Sets the input layer of this network.
	 * @param inputLayer - The input layer to set.
	 */
	public void setInputLayer(Layer inputLayer) {
		this.inputLayer = inputLayer;
	}

	/**
	 * Retrieves the hidden layers of this network.
	 * @return The hidden layers.
	 */
	public List<Layer> getHiddenLayers() {
		return hiddenLayers;
	}

	/**
	 * Sets the hidden layers of this network.
	 * @param hiddenLayers - The hidden layers to set.
	 */
	public void setHiddenLayers(List<Layer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}

	/**
	 * Retrieves the output layer of this network.
	 * @return The output layer.
	 */
	public Layer getOutputLayer() {
		return outputLayer;
	}

	/**
	 * Sets the output layer of this network.
	 * @param outputLayer - The output layer to set.
	 */
	public void setOutputLayer(Layer outputLayer) {
		this.outputLayer = outputLayer;
	}

	/**
	 * Retrieves the id of this network.
	 * @return The id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this network.
	 * @param id - The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retrieves the output of this network.
	 * @return Output of the first output neuron in output layer.
	 */
	public double getOutput() {
		return outputLayer.getNeurons().get(0).calculateOutput();
	}
}
