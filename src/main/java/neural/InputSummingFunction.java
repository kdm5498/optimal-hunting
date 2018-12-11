package main.java.neural;

import java.util.List;

/**
 * Represents the summing function for the inputs of a neuron.
 * @author Kyle McVay
 */
public class InputSummingFunction {
	/**
	 * Retrieves the output of the input connections.
	 * @param inputConnections - Input connections.
	 * @return Weighted sum of the inputs of the input connections.
	 */
	public double getOutput(List<Connection> inputConnections) {
		double weightedSum = 0.0;
		for(Connection connection: inputConnections) {
			weightedSum += connection.getWeightedInput();
		}
		
		return weightedSum;
	}
}
