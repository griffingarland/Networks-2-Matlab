import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.Thread;
public class TrafficGenerator {
	public static void main(String[] args) throws IOException {
	    
		BufferedReader bis = null; 
		String currentLine = null; 
		PrintStream pout = null;
	    try {  
			/*
			 * Open input file as a BufferedReader
			 */ 
	    	// TODO Open Poisson traffic file (in the lab handout link, download and rename here
			File fin = new File("poisson3.txt"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			
			/*
			 *  Read file line-by-line until the end of the file 
			 */
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
				
				// TODO Send UDP packets
				// Make up our own data buffer for UDP packet?
				byte [] buf; // initialized to zeros by default					
				InetAddress addr = InetAddress.getByName(args[0]);
			    DatagramPacket packet = new DatagramPacket(buf, size, addr, 4444);
			    DatagramSocket socket = new DatagramSocket();
			    // simulate delay
			    if (SeqNo > 1)
			    	java.lang.Thread.sleep(time/1000);
				socket.send(packet);
				
				
			} 
		} catch (IOException e) {  
			// catch io errors from FileInputStream or readLine()  
			System.out.println("IOException: " + e.getMessage());  
		} finally {
			// Close files   
			if (bis != null) { 
				try { 
					bis.close(); 
					pout.close();
				} catch (IOException e) { 
					System.out.println("IOException: " +  e.getMessage());  
				} 
			} 
		} 
	}
}

