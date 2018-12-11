package main.java.neural;

/**
 * This class represents the activation function of an output neuron.
 * @author Kyle McVay
 */
public class ActivationFunction {
	/**
	 * Retrieves the output of a neuron.
	 * @param in - Input value.
	 * @return 0, 1 or 2
	 */
	public double getOutput(double in) {
		if(in < 1000) {
			return 0;
		} else if(in >= 1000 && in < 10000) {
			return 1;
		} else {
			return 2;
		}
	}
}
