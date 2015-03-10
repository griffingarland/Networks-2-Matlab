import java.io.*; 
import java.util.*; 
import java.lang.*;
import java.net.*;

// Griffin Garland
// Samprit Raihan

// For every T msec we transmit a group of N packets, each with size L bytes
// Rate = NL8/T bpS

// Usage, make first:
// java TrafficGenerator [hostname] [T] [N] [L]
// java TrafficGenerator localhost 1000 2 100

class TrafficGenerator {  
  public static void main (String[] args) 
  { 
    String hostname = args[0];
    int port = 4444;
	int T = Integer.parseInt(args[1]);
    int N = Integer.parseInt(args[2]);
    int L = Integer.parseInt(args[3]);

    try {  
	  InetAddress addr = InetAddress.getByName(hostname);
      File file = new File("poisson3.data");
	  FileReader file_in = new FileReader(file);
	  BufferedReader buffer_in = new BufferedReader(file_in);
	  FileOutputStream file_out = new FileOutputStream("output.txt");
      PrintStream print_out = new PrintStream(file_out);

      // create a socket
      DatagramSocket socket = new DatagramSocket();
      //Start at 0th packet
	  int seq_no = 0;
      long time_start = System.nanoTime();
      long time_current = 0;
      long time_elapsed = 0;
	  long time_target;
	  String line_in = null;
	  int Fsize = 0;
		int printsize = 0;

      while (Fsize != 0 || (line_in = buffer_in.readLine()) != null) {
        for (int i = 0; i < N; i++) {

		//We want to do this if we can send any packets (ie we can read something from the file or Fsize is nonzero) 
		if((Fsize != 0) || (i==0) || (Fsize == 0) && ( i !=0) && (line_in = buffer_in.readLine()) != null)
		{

		//Fsize is zero meaning we should read another packet
		if(Fsize == 0) {	
			//Tokensize String
			StringTokenizer st = new StringTokenizer(line_in); 
			String col1 = st.nextToken(); 
			String col2 = st.nextToken(); 
			String col3  = st.nextToken(); 
				
			//Convert each element to desired data type 
			int SeqNo2 	= Integer.parseInt(col1);
			float t2 	= Float.parseFloat(col2);  
			Fsize 	= Integer.parseInt(col3);
			System.out.println("Reading " + SeqNo2);
		}

//		System.out.println("Fsize " + Fsize);
		DatagramPacket p = null;
		if(Fsize > L)
		{
			byte[] packet = new byte[L];
			Fsize = Fsize - L;
			printsize = L;
   			p = new DatagramPacket(packet, L, addr, port);
		}
		else
		{
       		byte[] packet = new byte[Fsize];
			printsize = Fsize;
			Fsize = 0;
            p = new DatagramPacket(packet, Fsize, addr, port);
		}
//		System.out.println("Fsize " + Fsize);
          socket.send(p);

          // Increase seq_no and set time_elapsed
          seq_no++;
          time_current = System.nanoTime();
          time_elapsed = (time_current - time_start) / 1000;
          time_start = time_current;

          // now print the output to trace.txt
          print_out.println(seq_no + " " + time_elapsed + " " + printsize);
        }
		}

        // Now sleep for T msecs
        time_target = time_start + T * 1000;
        do{
          time_current = System.nanoTime();
        } while (time_target > time_current );
		//Break when we are done sleeping
		//We can't use the actual sleep because it won't really sleep us for as long as we want
        System.out.println(seq_no + " slept for: " + (time_current - time_start)/1000);
      } 

    } catch (IOException e) {  
      // catch io errors from FileInputStream or readLine()  
      System.out.println("IOException: " + e.getMessage());  
    }
  }  
}
