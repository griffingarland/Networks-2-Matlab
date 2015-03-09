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
    int T = Integer.parseInt(args[1]);
    int N = Integer.parseInt(args[2]);
    int L = Integer.parseInt(args[3]);
    int port = 4444;

    try {  
      // Get addr and output file
        InetAddress addr = InetAddress.getByName(hostname);
      FileOutputStream file_out = new FileOutputStream("trace.txt");
      PrintStream print_out = new PrintStream(file_out);

      // Create socket
      DatagramSocket socket = new DatagramSocket();
      int i = 0;
      int seq_no = 0;
      long time_start = System.nanoTime();
      long time_current = 0;
      long time_elapsed = 0;

      while (true) {
        // "Every T msec transmit a group of N packets each with size L bytes"
        for (i = 0; i < N; i++) {
          byte[] packet = new byte[L];

          DatagramPacket p = new DatagramPacket(packet, L, addr, port);
          socket.send(p);

          // Increase seq_no and set time_elapsed
          seq_no++;
          time_current = System.nanoTime();
          time_elapsed = (time_current - time_start) / 1000;
          time_start = time_current;

          // now print the output to trace.txt
          print_out.println(seq_no + " " + time_elapsed + " " + L);
        }

        // Now sleep for T msecs
        long current_time;
        long target_time = time_start + T * 1000;
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
