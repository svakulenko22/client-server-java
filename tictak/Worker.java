package tictak;

public class Worker extends Thread{

	private int id;
	private Data data;

	static int counter = 0;
	int remainder;
	static Object lock=new Object();

	public Worker(int id, Data d){
		this.id = id;
		data = d;
		this.start();
	}

	@Override
	public void run() {
		super.run();
		while (counter < 13) {
			synchronized (lock) {
				while (counter % 3 != id) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				switch (counter % 3) {
					case 0:
						System.out.print("Tic-");
						break;
					case 1:
						System.out.print("Tak-");
						break;
					default:
						System.out.println("Toe");
				}
				counter++;
				lock.notifyAll();
			}
		}
	}
}
