package neural;

import java.util.List;

public class Net {
	private String id;
	
	private Layer inputLayer;
	private List<Layer> hiddenLayers;
	private Layer outputLayer;
	
	public Net(String id, Layer inputLayer, List<Layer> hiddenLayers, Layer outputLayer) {
		this.setId(id);
		this.setInputLayer(inputLayer);
		this.setHiddenLayers(hiddenLayers);
		this.setOutputLayer(outputLayer);
	}
	
	public Net(String id, Layer inputLayer, Layer outputLayer) {
		this.setId(id);
		this.setInputLayer(inputLayer);
		this.setOutputLayer(outputLayer);
	}

	/**
	 * @return the inputLayer
	 */
	public Layer getInputLayer() {
		return inputLayer;
	}

	/**
	 * @param inputLayer the inputLayer to set
	 */
	public void setInputLayer(Layer inputLayer) {
		this.inputLayer = inputLayer;
	}

	/**
	 * @return the hiddenLayers
	 */
	public List<Layer> getHiddenLayers() {
		return hiddenLayers;
	}

	/**
	 * @param hiddenLayers the hiddenLayers to set
	 */
	public void setHiddenLayers(List<Layer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}

	/**
	 * @return the outputLayer
	 */
	public Layer getOutputLayer() {
		return outputLayer;
	}

	/**
	 * @param outputLayer the outputLayer to set
	 */
	public void setOutputLayer(Layer outputLayer) {
		this.outputLayer = outputLayer;
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
	
	public double getOutput() {
		return outputLayer.getNeurons().get(0).calculateOutput();
	}
}
