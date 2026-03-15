package gh2;
import deque.ArrayDeque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {

    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private GuitarString[] guitarStringArr;
    private static final double baseConcert = 440.0;


    public static void main(String[] args) {
        GuitarHero gh = new GuitarHero();
        while (true) {
            GuitarString ggs = null;
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    GuitarString gs = gh.guitarStringArr[index];
                    gs.pluck();
                    ggs = gs;
                } else {
                    System.out.println("wrong key");
                }
            }
            double sample = gh.getAllSample();
            StdAudio.play(sample);
            gh.ticAllString();
        }
    }


    public GuitarHero() {
        int length = keyboard.length();
        guitarStringArr = new GuitarString[length];
        for (int i = 0; i < length; i++) {
            double concert = getConcertHelper(i);
            guitarStringArr[i] = new GuitarString(concert);
        }
    }

    private static double getConcertHelper(int i) {
        return baseConcert * Math.pow(2, (i - 24) / 12.0);
    }

    public double getAllSample() {
        double result = 0;
        for (GuitarString gs : this.guitarStringArr) {
            result += gs.sample();
        }
        return result;
    }

    public void ticAllString() {
        for (GuitarString gs : this.guitarStringArr) {
            gs.tic();
        }
    }



}
