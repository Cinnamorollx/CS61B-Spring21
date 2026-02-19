package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> nList = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        nList.addLast(1000);
        nList.addLast(2000);
        nList.addLast(4000);
        nList.addLast(8000);
        nList.addLast(16000);
        nList.addLast(32000);
        nList.addLast(64000);
        nList.addLast(128000);
        nList.addLast(256000);
//        nList.addLast(10000000);

        for (int i = 0; i < nList.size(); i++) {
            int n = nList.get(i);
            Double spentTime = addLastHelper(n);
            times.addLast(spentTime);
            opCounts.addLast(n);
        }

        printTimingTable(nList, times, opCounts);
    }

    private static double addLastHelper(int n) {
        Stopwatch sw = new Stopwatch();
        AList<Integer> al = new AList<>();
        for (int i = 0; i < n; i++) {
            al.addLast(i);
        }
        return sw.elapsedTime();
    }
}
