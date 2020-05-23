package tictak;

public class Data {
    private int state=1;

    public int getState() { return state; }

    public synchronized void Tic(){
            System.out.print("Tic-");
            state = 2;
    }
    public synchronized void Tak(){
            System.out.print("Tak-");
            state = 3;
    }
    public synchronized void Toe(){
            System.out.println("Toe");
            state = 1;
    }
}
