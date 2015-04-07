import java.io.*;
import java.net.*;
import java.util.concurrent.*;
/*
Your task is to build a software tool, called the Estimator. The Estimator is a Java
program which sends sequences of probe packets to the Black Box. The probe packets
are returned by the Black Box to the Estimator. The Estimator creates timestamps for
each packet sent to the Black Box and for each packet returning from the Black Box.
Using these timestamps, the Estimator computes the parameters of the Black Box (b, R, T).

The parameters are: 
	T – delay parameter (in microseconds),
	R – rate parameter (in Mbps),
	b – burst parameter (in Bits). 
*/



public class TrafficEstimator {
	// data structure that records the sequence numbers of packets together with the timestamps
	// Key - sequenceNumber
	// Value - TimeStamp
    public static ConcurrentHashMap<Integer,TimeStamp> map = new ConcurrentHashMap<Integer, TimeStamp> ();

    public static void main(String[] args) throws IOException {
        
	    //BlackBox at localhost:4444
	    String hostname = args[0]; // localhost
	    InetAddress addr = InetAddress.getByName(hostname); 
	    int blackbox_port = Integer.parseInt(args[1]); // just give it 4444 always, 4444 by default
	    int return_port = 5555; //sink's port

	    int N = Integer.parseInt(args[2]);
	    int L = Integer.parseInt(args[3]);
	    int r = Integer.parseInt(args[4]);

	    // Send probe packets to BlackBox
	    new TrafficGenerator(addr, blackbox_port, N, L, r, return_port).start(); // start running a thread

	    // Receive traffic from BlackBox to compute the parameters
	    new TrafficSink(N, L, return_port).start(); // start running another thread

    }
}
