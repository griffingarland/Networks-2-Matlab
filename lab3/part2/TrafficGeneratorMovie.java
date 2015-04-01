import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.Thread;
public class TrafficGeneratorMovie {
	public static void main(String[] args) throws IOException, InterruptedException {
	    
		BufferedReader bis = null; 
		String currentLine = null; 
		try {  
			/*
			 * Open input file as a BufferedReader
			 */ 
			File fin = new File("movietracespaces.data"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output_movie.txt");
			PrintStream pout = new PrintStream (fout);
			int counter = 1;
			float timeDiff = 0;
			String addr_IP = args[0];
			int N = Integer.parseInt(args[1]);
			DatagramSocket socket = new DatagramSocket();
			float firstTime = 0;
			int Fsize = 0;
			float time=0;
			int SeqNo=0;
			    // simulate delay
			/*
			 *  Read file line-by-line until the end of the file 
			 */


			// simulate delay
			long prevTime = System.nanoTime();

			while (Fsize != 0 || (currentLine = bis.readLine()) != null) { 
			
				if(Fsize == 0) {
				/*
				 *  Parse line and break up into elements 
				 */
				StringTokenizer st = new StringTokenizer(currentLine); 
				String col1 = st.nextToken(); 
				String col2 = st.nextToken();
				String col3  = st.nextToken(); 
				
				/*
				 *  Convert each element to desired data type 
				 */
				SeqNo 	= Integer.parseInt(col1);
				time 	= Float.parseFloat(col2)*1000; //takes too long when in nanoseconds
				Fsize 	= Integer.parseInt(col3);
				

				}
				
				InetAddress addr = InetAddress.getByName(addr_IP);
			    DatagramPacket packet = null;
				byte [] buf;

				if (Fsize > 1480) {
					buf = new byte[1480];
					Fsize = Fsize - 1480;
					packet = new DatagramPacket(buf, 1480, addr, 4444);
				}
				else {
					buf = new byte[Fsize];
					packet = new DatagramPacket(buf, Fsize, addr, 4444);
					Fsize = 0;
				}

				// Make up our own data buffer for UDP packet?
					buf[0] = (byte)2;
					
					int time_nano = (int) (time - firstTime);
					firstTime = time;
					//Distance between them is affected by 1/N
					//ie N = 2 means double traffic and half the time, as per the handout
				int wait_time;
				if(Fsize == 0) {
					wait_time = time_nano/N;
				}
				else {
					wait_time = 12330000; //approx time to transmit 1480 bits at 15 MBps in ns
				}
					long current_time;
					float send_time = prevTime + wait_time;
			    	do{
			    		// nothing
			    		current_time = System.nanoTime();
			    	} while(current_time < send_time);
			    	prevTime = current_time;
				
					socket.send(packet);
				pout.println(SeqNo + " " + wait_time + " " + packet.getLength());
				}		
		} catch (IOException e) {  
			// catch io errors from FileInputStream or readLine()  
			System.out.println("IOException: " + e.getMessage());  
		} finally {
			// Close files   
			if (bis != null) { 
				try { 
					bis.close(); 
				} catch (IOException e) { 
					System.out.println("IOException: " +  e.getMessage());  
				} 
			} 
		} 
	}
}

