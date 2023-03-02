/*
  1. Реализовать программу, в которой два потока поочередно пишут ping и pong.
*/
class Pingpong implements Runnable {
	private final String name;
	private final Object monitor;

	Pingpong(String name, Object monitor) {
		this.name = name;
		this.monitor = monitor;
	}

	@Override public void run() {
		synchronized (monitor) {
			while (true) {
				System.out.println(name);

				try {
					Thread.sleep(1000);
					monitor.notify(); // информировать другой поток о своем завершении
					monitor.wait(); // и ожидать его завершения
				} catch (InterruptedException ex) { ex.printStackTrace(); }
			}
		}
	}

	public static void main(String[] args) {
		Object mon = new Object();
		Thread ping = new Thread(new Pingpong("ping", mon));
		Thread pong = new Thread(new Pingpong("pong", mon));
		ping.start();
		pong.start();
	}
}