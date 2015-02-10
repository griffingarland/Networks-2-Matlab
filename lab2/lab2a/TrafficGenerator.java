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
			File fin = new File("movietrace.txt"); 
			FileReader fis = new FileReader(fin);  
			bis = new BufferedReader(fis);  
			int counter = 1;
			float timeDiff = 0;
			float prevTime = 0;


			    // simulate delay
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
				
				// Make up our own data buffer for UDP packet?
				int oldsize = 0;
				if ( size > 1024 ) {
					oldsize = size - 1024;
					size = 1024;
				}				
				byte [] buf = new byte[size];					
				InetAddress addr = InetAddress.getByName(args[0]);
			    DatagramPacket packet = new DatagramPacket(buf, size, addr, 4444);
			    DatagramSocket socket = new DatagramSocket();
			    // simulate delay
			    
			    long start = System.nanoTime();
			    
			    
			    if(SeqNo == 1)
			    {
			    	prevTime = time;
			    }
			    if(SeqNo != 1)
			    {
			    	//Thread.sleep((long) ((time - prevTime) / 1000));
			    	
			    	do{
			    		// nothing
			    		counter++;
			    	} while((System.nanoTime() - start) / 1000 < (time - prevTime));
			    	prevTime = time;
			    }
			    
			  //  System.out.println("seq:" + SeqNo + " time (us): " + time);
			    socket.send(packet);
			    //MyRunnable myRunnable = new MyRunnable(packet, socket);
		            //Thread t = new Thread(myRunnable);
		            //t.start();
		     	while(oldsize > 0) {
					int size2 = 0;
					if(oldsize > 1024)
						size2 = 1024;
					else
						size2=oldsize;
					byte [] buf_new = new byte[size2];					
				    DatagramPacket packet_new = new DatagramPacket(buf_new, buf_new.length, addr, 4444);
					oldsize -= 1024;
			    	socket.send(packet_new);
				}		
				
				
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

