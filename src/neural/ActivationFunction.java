package neural;

public class ActivationFunction {
	public double getOutput(double in) {
		if(in < 10) {
			return 0;
		} else if(in >= 10 && in < 100) {
			return 1;
		} else {
			return 2;
		}
	}
}
