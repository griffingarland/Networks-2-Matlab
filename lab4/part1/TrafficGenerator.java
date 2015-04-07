import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;

class TrafficGenerator extends Thread {  

	static InetAddress addr;
	static int blackbox_port;
	/*
	N: number of packets of the train;
	L: packet size of the train (in Byte);
	r: average bit rate of the train (in kbps)
	*/
	static int N;
	static int L;
	static int r;
	static int return_port;


	public TrafficGenerator (InetAddress addr, int blackbox_port, int N, int L, int r, int return_port) {
		this.addr = addr;
		this.blackbox_port = blackbox_port;
		this.N = N;
		this.L = L;
		this.r = r;
		this.return_port = return_port;
		
		System.out.println("N = " + N + " packets in train");
		System.out.println("L = " + L + " bytes total");
		System.out.println("r = " + r + " kbps average rate");
		System.out.println("****************************");

	}


	/** 
	 * Converts an integer to a byte array. 
	 * @param value an integer 
	 * @return a byte array representing the integer 
	 *	 
	 * This produces a byte array of with 4 bytes. If the value passed to the method is a short 
	 * (a 16 bit integer used for port numbers) its value will be stored in the last 2 bytes of the 
	 * array (with the first two bytes set to zero).
	 */
	public static byte[] toByteArray(int value) {
		byte[] Result = new byte[4];
		Result[3] = (byte) ((value >>> (8*0)) & 0xFF);
		Result[2] = (byte) ((value >>> (8*1)) & 0xFF); 
		Result[1] = (byte) ((value >>> (8*2)) & 0xFF); 
		Result[0] = (byte) ((value >>> (8*3)) & 0xFF);
		return Result;
	}

	public void run () { 
		try {  
			// main code for generating probe packets goes here
			// Create a UDP datagram socket
			DatagramSocket socket = new DatagramSocket();
			//long r_in_Bps = r * 1000 / 8; //kbps * 1000 / 8
			//long packet_size_in_B = L/N;
			//long interval = (packet_size_in_B *1000 *1000 *1000)/ r_in_Bps;
			
			long interval = ((long)(L/N) * 1000 * 1000 * 1000)/ (long)(r * 1000 / 8); //kbps * 1000 / 8

			System.out.println((long) (L/N) + " B per packet");
			System.out.println(interval + " nanoseconds interval");
			System.out.println("for " + N + " packets");

			int packet_size = (int) L/N;
			long send_timestamp = System.nanoTime(); // when was the last packet sent?
			//Timestamps must be normalized to the Send Timestamp of the first probe packet, whose timestamp is set to zero. 
			long timestamp = 0;
			int seqNo;
			for (seqNo = 1; seqNo <= N; seqNo++){
				
				/*
				An issue with a remote (or shared used) Black Box is that the Black Box does not 
				have a prior knowledge of the server port number of the Estimator. Thus, we add 
				the requirement that the port number of the UDP server port of the Estimator is 
				included in each packet sent from the Estimator to the Black Box. 		
				*/
				byte [] buf = new byte[packet_size];

				// put return_port in first 2 bytes of buf
				System.arraycopy(toByteArray(return_port), 2, buf, 0, 2);

				//add seqNo to the next two bytes
				System.arraycopy(toByteArray(seqNo), 2, buf, 2, 2);
				// create a UDP probe packet to send to BlackBox
				DatagramPacket packet = new DatagramPacket(buf, packet_size, addr, blackbox_port);
				
				long time_to_send = send_timestamp + interval;
				
				long current_time;
			    do {
			        current_time = System.nanoTime();
			    } while ( current_time < time_to_send ); // wait until it's time_to_send
			    send_timestamp = current_time;

				// normalize time for first packet
			    if (seqNo == 1) {
			    	timestamp = System.nanoTime() / 1000;
			    }
			    else {
			    	timestamp = send_timestamp - timestamp;
			    	timestamp = timestamp / 1000;
			    }
			    // put the timestamp into the TimeStamp object
			    TimeStamp ts = new TimeStamp(timestamp);
			    // Store that TimeStamp object into the hashmap for the packet with current seqNo
			    Integer sequenceNumber = new Integer(seqNo);
			    TrafficEstimator.map.put(sequenceNumber, ts);
			    timestamp = send_timestamp;
			    // Finally, send the packet to BlackBox
				System.out.println("Sending packet " + seqNo + " with timestamp = " + timestamp);	
				socket.send(packet);
			}
			
		}
		catch (SocketException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally {
			// do nothing
			System.out.println("PACKET GENERATION DONE");
		}
		 
	}  
}



