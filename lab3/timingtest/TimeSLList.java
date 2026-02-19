package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        SLList<Integer> sl = new SLList<>();
        AList<Integer> nList = new AList<>();
        AList<Double> timesList = new AList<>();
        AList<Integer> opCounts = new AList<>();
        nList.addLast(1000);
        nList.addLast(2000);
        nList.addLast(4000);
        nList.addLast(8000);
        nList.addLast(16000);
        nList.addLast(32000);
        nList.addLast(64000);
        nList.addLast(128000);

        int getTimes = 10000;

        for (int i = 0; i < nList.size(); i++) {
            int N = nList.get(i);
            double timeSpent = getLastHelper(N, getTimes);
            opCounts.addLast(getTimes);
            timesList.addLast(timeSpent);
        }

        printTimingTable(nList, timesList, opCounts);
    }

    private static double getLastHelper(int n, int getTimes) {
        SLList<Integer> sl = new SLList<>();
        for (int i = 0; i < n; i++) {
            sl.addLast(i);
        }
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < getTimes; i++) {
            int last = sl.getLast();
        }
        return sw.elapsedTime();
    }

}
