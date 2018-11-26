package genetics;

public class ActivationFunction {
	public double getOutput(double in) {
		if(in > 1) {
			return 1;
		} else {
			return 0;
		}
	}
}
