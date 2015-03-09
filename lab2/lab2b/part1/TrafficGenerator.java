import java.io.*; 
import java.util.*; 
import java.lang.*;
import java.net.*;

// Create traffic based on T N L
// "Every T msec transmit a group of N packets each with size L bytes"
// Rate = NL8/T bps

// Run traffic Generator like 
// TrafficGeneration hostname T N L

class TrafficGenerator {  
  public static void main (String[] args) 
  { 
    String hostname = args[0];
    int T_msec = Integer.parseInt(args[1]);
    int N_packets = Integer.parseInt(args[2]);
    int L_bytes = Integer.parseInt(args[3]);
    int port = 4445;

    try {  
      // Get addr and output file
        InetAddress addr = InetAddress.getByName(hostname);
      FileOutputStream fout = new FileOutputStream("trace.txt");
      PrintStream pout = new PrintStream(fout);

      // Create socket
      DatagramSocket socket = new DatagramSocket();
      int i = 0;
      int seq_no = 0;
      long time_start = System.nanoTime();
      long time_cur = 0;
      long time_elapsed = 0;

      while (true) {
        // "Every T msec transmit a group of N packets each with size L bytes"
        for (i = 0; i < N_packets; i++) {
          byte[] packet = new byte[L_bytes];

          DatagramPacket p = new DatagramPacket(packet, L_bytes, addr, port);
          socket.send(p);

          // Increase seq_no and set time_elapsed
          seq_no++;
          time_cur = System.nanoTime();
          time_elapsed = (time_cur - time_start) / 1000;
          time_start = time_cur;

          // now print the output to trace.txt
          pout.println(seq_no + " " + time_elapsed + " " + L_bytes);
        }

        // Now sleep for T msecs
        long current_time;
        long target_time = time_start + T_msec * 1000;
        do{
          current_time = System.nanoTime();
        } while (target_time > current_time );
        System.out.println(seq_no + " Slept for " + (current_time - time_start)/1000);
      } 

    } catch (IOException e) {  
      // catch io errors from FileInputStream or readLine()  
      System.out.println("IOException: " + e.getMessage());  
    }
  }  

  public static void nanoSleep(long nano)
  {
      long start = System.nanoTime();
      long end = 0;
      long dur = nano;
      do{
          end = System.nanoTime();
      } while(start + dur >= end);
  }
  
  public static void usecSleep(long usec)
  {
      long start = System.nanoTime();
      long end = 0;
      long dur = usec * 1000;
      do{
          end = System.nanoTime();
      } while(start + dur >= end);
  }

  public static void secSleep(double sec)
  {
    int ms = (int)sec*1000;
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());  
    }
  }
}
