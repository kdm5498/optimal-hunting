package neural;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	private String id;
	private List<Neuron> neurons;
	
	public Layer(String id) {
		this.setId(id);
		setNeurons(new ArrayList<>());
	}
	
	public Layer(String id, List<Neuron> neurons) {
		this.setNeurons(neurons);
	}

	/**
	 * @return the neurons
	 */
	public List<Neuron> getNeurons() {
		return neurons;
	}

	/**
	 * @param neurons the neurons to set
	 */
	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
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
