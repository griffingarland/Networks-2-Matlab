import java.io.*;
import java.net.*;

public class TrafficGenerator {
	public static void main(String[] args) throws IOException {
	    InetAddress addr = InetAddress.getByName(args[0]);
	    byte[]      buf  = args[1].getBytes();
	    DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, 4444);
	    DatagramSocket socket = new DatagramSocket();
	    socket.send(packet);
	    
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
				
				
				/*
				 *  Display content of file 
				 */
//				System.out.println("SeqNo:  " + SeqNo); 
//				System.out.println("Frame time:   " + Ftime); 
//				System.out.println("Frame type:        " + Ftype); 
//				System.out.println("Frame size:       " + Fsize + "\n"); 

				// TODO Re-scale the values
				// TODO Send UDP packets
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

