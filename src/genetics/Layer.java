package genetics;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	private String id;
	protected List<Neuron> neurons;
	
	public Layer(String id) {
		this.id = id;
		neurons = new ArrayList<>();
	}
	
	public Layer(String id, List<Neuron> neurons) {
		this.neurons = neurons;
	}
}
