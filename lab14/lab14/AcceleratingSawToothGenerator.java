package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.state = 0;
        this.factor = factor;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        double newState = normalize(state);
        if (state == 0) {
            period *= factor; // the period changed by the factor after resetting
        }
        return newState;
    }

    private double normalize(int state) {
        return state * 2.0 / period - 1.0;
    }
    
    public static void main(String[] args) {
		Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
	}
}
