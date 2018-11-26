package genetics;

import java.util.List;

public class InputSummingFunction {
	public double getOutput(List<Connection> inputConnections) {
		double weightedSum = 0.0;
		for(Connection connection: inputConnections) {
			weightedSum += connection.getWeightedInput();
		}
		
		return weightedSum;
	}
}
