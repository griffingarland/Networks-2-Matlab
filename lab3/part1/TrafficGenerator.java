import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.Thread;
public class TrafficGenerator {
	public static void main(String[] args) throws IOException, InterruptedException {
	    
		BufferedReader bis = null; 
		String currentLine = null; 
		try {  
			/*
			 * Open input file as a BufferedReader
			 */ 
			File fin = new File("poisson3.data"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			FileOutputStream fout =  new FileOutputStream("output.txt");
			PrintStream pout = new PrintStream (fout);
			int counter = 1;
			float timeDiff = 0;
			String addr_IP = args[0];
			int N = Integer.parseInt(args[1]);
			int priority = Integer.parseInt(args[2]);
			System.out.println("Setting priority to: " + priority);
			DatagramSocket socket = new DatagramSocket();
			float firstTime = 0;
			    // simulate delay
			/*
			 *  Read file line-by-line until the end of the file 
			 */


			// simulate delay
			long prevTime = System.nanoTime();

			while ( (currentLine = bis.readLine()) != null) { 
				
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
				int SeqNo 	= Integer.parseInt(col1);
				float time 	= Float.parseFloat(col2);
				int size 	= Integer.parseInt(col3);
				
				// Make up our own data buffer for UDP packet?
				byte [] buf = new byte[size];
				if (priority == 1) {
					buf[0] = (byte)1;
				}
				else if (priority == 2) {
					buf[0] = (byte)2;
				}				
				InetAddress addr = InetAddress.getByName(addr_IP);
			    DatagramPacket packet = new DatagramPacket(buf, size, addr, 4445);
			    
					int time_nano = (int) (time - firstTime)*1000;
					firstTime = time;
					int wait_time = time_nano/N;
					long current_time;
					float send_time = prevTime + wait_time;
			    	do{
			    		// nothing
			    		current_time = System.nanoTime();
			    	} while(current_time < send_time);
			    	prevTime = current_time;
			    
			    socket.send(packet);
				System.out.println(wait_time);
			    pout.println(SeqNo + "\t" + wait_time + "\t" + packet.getLength());
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

