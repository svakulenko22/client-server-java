package tictak;

import tictak.Worker;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Data d = new Data();

        Worker w1 = new Worker(0, d);
        Worker w2 = new Worker(1, d);
        Worker w3 = new Worker(2, d);

        try {
            w1.join();
            w2.join();
            w3.join();
        }
        catch (Exception e){
            System.out.println("Interrupted");
        }
        System.out.println("end of main.");
    }
}
