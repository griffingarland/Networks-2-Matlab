import java.io.*;
import java.net.*;

public class TrafficSink extends Thread{

	/*
	N: number of packets of the train;
	L: packet size of the train (in Byte);
	r: average bit rate of the train (in kbps)
	*/
	static int N;
	static int L;
	static int port;
    
	static boolean listening = true;

    public TrafficSink (int N, int L, int return_port) {
        this.N = N;
        this.L = L;
        this.port = return_port;
        System.out.println("SeqNo seq_num size time(ms)");
    }

    // convert byte array to integer
    public static int fromByteArray(byte [] value, int start, int length) {
        int Return = 0;
        for (int i = start; i < start + length; i++) {
            Return = (Return << 8) + (value[i] & 0xff);
        }
        return Return;
    }

    public void run() {
		// main code for receiving packets from BlackBox goes here
        PrintStream pout = null;
        
        try {
			// Create a UDP socket to listen to Blackbox at port 5555
            DatagramSocket socket = new DatagramSocket(port);
            FileOutputStream fout =  new FileOutputStream("output_sink.txt");
            pout = new PrintStream (fout);
    		
            int packet_size = (int) L/N;
            byte[] buf = new byte[packet_size];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            
            long receive_timestamp = 0;
            int seqNo = 0;
            while (listening) {
                socket.receive(packet);              
                //get seqNo from packet
                seqNo = fromByteArray(packet.getData(), 2, 2);
				long time_to_record;
                //normalize time for first packet
                if (seqNo == 1) 
                	time_to_record = System.nanoTime() /1000;
                else{
                	// time we received packet from BlackBox
		            long time_received = System.nanoTime();
		            //current received packet time minus the last received packet time
		            time_to_record = time_received - receive_timestamp;
		            // update received timestamp for next iteration of packet receive
		            receive_timestamp = time_received;
                }
             	//convert to microseconds
		        time_to_record = time_to_record/1000;
				
                //update the hashmap at the key -> sequenceNumber
                Integer sequenceNumber = new Integer(seqNo);
                TimeStamp ts = TrafficEstimator.map.get(sequenceNumber);
                ts.setReceive(time_to_record);
                TrafficEstimator.map.put(sequenceNumber, ts);
                //System.out.println("Receiving packet " + seqNo + " with timestamp = " + time_to_record);
                if(seqNo == N)
                	break;
            }
			    
            //normalize the first packet with seqNo 1
            TrafficEstimator.map.put(new Integer(1), TrafficEstimator.map.get(new Integer(1)).normalize());
			
            //get culmulative timestamps
            for (seqNo = 2; seqNo <= N; seqNo++) {
				//System.out.println("Yo" + seqNo);
                TimeStamp ts = TrafficEstimator.map.get(new Integer(seqNo - 1));
                TrafficEstimator.map.put(new Integer(seqNo), TrafficEstimator.map.get(new Integer(seqNo)).add(ts));

            }

            //print out the hashmap as well as write out the data to file
            for (seqNo = 1; seqNo <= N; seqNo++) {
                //System.out.println("YoLo" + seqNo);
                
                TimeStamp ts = TrafficEstimator.map.get(new Integer(seqNo));
                // TODO Delete this later
                System.out.println(seqNo + " " + ts.print());
                pout.println(seqNo + "\t"+  ts.sendTime + "\t" + ts.receiveTime); 
            }

        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();   
        }
        catch (IOException e) {
            e.printStackTrace();   
        }
        finally {
        	System.out.println("PACKET RECEIVE END");
                
            // nothing to do here
            pout.close(); 
        }
    
        
    }
}
