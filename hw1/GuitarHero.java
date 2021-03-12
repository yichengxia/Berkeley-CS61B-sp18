/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static double[] CONCERT = new double[37];
    private static synthesizer.GuitarString[] string = new synthesizer.GuitarString[37];
    private static int index;

    public static void main(String[] args) {
        /* create guitar strings */
        for (int i = 0; i < 37; i += 1) {
            CONCERT[i] = 440.0 * Math.pow(2, (i - 24.0) / 12.0);
            string[i] = new synthesizer.GuitarString(CONCERT[i]);
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                index = keyboard.indexOf(key);
                if (index != -1) {
                    string[index].pluck();
                }
            }

            if (index != -1) {
                /* compute the superposition of samples */
                double sample = string[index].sample();

                /* play the sample on standard audio */
                StdAudio.play(sample);

                /* advance the simulation of each guitar string by one step */
                string[index].tic();
            }
        }
    }
}
