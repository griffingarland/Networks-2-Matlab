import java.io.*;
import java.net.*;

public class TrafficSink {
    public static void main(String[] args) throws IOException 
    {
        // Create socket
        DatagramSocket socket = new DatagramSocket(4445);
        
        // Set buf size
        byte[] buf = new byte[65507];
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        System.out.println("Waiting ..."); 
        PrintStream pout = null;

        try {
            // Output file stream
            FileOutputStream fout =  new FileOutputStream("output.txt");
            pout = new PrintStream(fout);

            // Hardcode first packet too time = 0
            socket.receive(p);
            long timestamp = System.nanoTime();
            pout.println(1 + " " + 0 + " " + p.getLength());
            
            // Loop receiver
            int seqNo = 2;
            while (true) {
                socket.receive(p);
                long newtime = System.nanoTime();
                int time = (int)(newtime - timestamp)/1000;
                pout.println(seqNo + " " + time + " " + p.getLength());
                timestamp = newtime;
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
