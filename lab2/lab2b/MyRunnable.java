import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.Thread;	

public class MyRunnable implements Runnable {

	    public DatagramPacket packet;
	    public DatagramSocket socket;
	    public MyRunnable(DatagramPacket packet, DatagramSocket socket) {
	        this.packet = packet;
	        this.socket = socket;
	    }

	    public void run() {
	        // code in the other thread, can reference "var" variable
	    	try {
	    		socket.send(packet);
	    	}
	    	catch (IOException e) {
				// Exception
				System.out.println("IOException: " + e.getMessage());
	    	}
	    }
}