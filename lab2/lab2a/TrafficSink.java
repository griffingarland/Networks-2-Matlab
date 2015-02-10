import java.io.*;
import java.net.*;
import java.util.*; 

public class TrafficSink {
	public static void main(String[] args) throws IOException 
	{
		DatagramSocket socket = new DatagramSocket(4444);
		
		PrintStream pout = null;
		byte[] buf = new byte[1500];
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		System.out.println("Waiting ..."); 
		try {
 			// Open file
 			FileOutputStream fout =  new FileOutputStream("sink.txt");
 			pout = new PrintStream(fout);

 			// Receive first packet
 			socket.receive(p);
 			long prevTime = System.nanoTime();
 			pout.println(1 + " " + 0 + " " + p.getLength());
 			//System.out.println("packetLength: " + p.getLength());
 			
 			
			int seqNo = 2;
			
			// Infinite loop to receive packets
			while (true) {
				socket.receive(p);
				long currTime = System.nanoTime();
				int time = (int)(currTime - prevTime)/1000;
				pout.println(seqNo + " " + time + " " + p.getLength());
				//System.out.println("packetLength: " + p.getLength());
				prevTime = currTime;
				seqNo++;
			}

		} catch (IOException e) {
			// Exception
			System.out.println("IOException: " + e.getMessage());  
		} finally {
			// Close file streams
			try { 
				pout.close();
			} catch (Exception e) { 
				System.out.println("IOException: " +  e.getMessage());  
			} 
		}
	}
}
