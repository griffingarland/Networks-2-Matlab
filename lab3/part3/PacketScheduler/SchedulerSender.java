package PacketScheduler;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringTokenizer;

/**
 * Removes and sends packets from buffers to a given address and port.
 */
public class SchedulerSender implements Runnable
{	
	/**
	 * senderActiveUntil holds the time (in ns) when next packet can be sent, i.e. time when last sending ends.
	 * NOTE: this is not the actual time it takes to send packet, 
	 * it is the time it would take to send packet at given link capacity.
	 */
	private long senderActiveUntil;
	
	// destination port
	private int destPort;
	// destination address
	private InetAddress destAddress;
	// socket used to send packets
	private DatagramSocket socket;
	// buffers from which packets are sent
	private Buffer[] buffers;
	// link capacity at which packet scheduler operates (pbs)
	private long linkCapacity;

	
	/**
	 * Constructor. Creates socket.
	 * @param buffers Buffers from which packets are sent.
	 * @param destAddress IP address to which packets are sent.
	 * @param destPort Port to which packets are sent.
	 * @param linkCapacity Link capacity at which FIFO scheduler operates (bps).
	 */
	public SchedulerSender(Buffer[] buffers, InetAddress destAddress, int destPort, long linkCapacity)
	{
		this.senderActiveUntil = 0l;
		this.buffers = buffers;
		this.destAddress = destAddress;
		this.destPort = destPort;
		this.linkCapacity = linkCapacity;
		
		try
		{
			socket = new DatagramSocket();
		} 
		catch (SocketException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Send packet using socket.
	 * @param packet Packet to send.
	 * @param startTime Time when sending of this packet was started.
	 */
	public synchronized void sendPacket(DatagramPacket packet, long startTime)
	{
		try 
		{
			// change destination of packet (do forwarding)
			packet.setAddress(destAddress);
			packet.setPort(destPort);
			
			// time it would take to send packet with given link capacity
			long sendingTime = (long)((((float)packet.getLength()*8)/linkCapacity)*1000000000);
			
			socket.send(packet);
			
			// time before next packet can be sent (simulate link capacity)
			long timeToWait = sendingTime - (System.nanoTime() - startTime);
			
			// set when next packet can be sent
			senderActiveUntil = startTime+timeToWait;
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Remove packets form buffers and send them.
	 * This method is invoked when starting a thread for this class.
	 */
	public void run()
	{
		long start_time = System.nanoTime();
		PrintStream pout1 = null;
			PrintStream pout2 = null;
			PrintStream pout3 = null;
	try {
			FileOutputStream fout1 =  new FileOutputStream("scheduler1.txt");
			pout1 = new PrintStream (fout1);
			
			FileOutputStream fout2 =  new FileOutputStream("scheduler2.txt");
			pout2 = new PrintStream (fout2);

			FileOutputStream fout3 =  new FileOutputStream("scheduler3.txt");
			pout3 = new PrintStream (fout3);

	} catch (IOException e)	{
		System.out.println("hi");
	}


			PrintStream[] out = {pout1, pout2, pout3};		

			int fairshare = 0;
		int max_rate = 10;
		int []rate = new int[3];
			rate[0] = 8;
			rate[1] = 6;
			rate[2] = 2;

			int[] weight = new int[3];
			weight[0] = 3;
			weight[1] = 1;
			weight[2] = 1;

			double[] share = new double[3];

			for (int i = 0; i < max_rate; i++) 
			{
				int result = Math.min(rate[0], i*weight[0]) + Math.min(rate[1], i*weight[1]) + Math.min(rate[2], i*weight[2]);
				if (result >= max_rate) {
					fairshare = i;
					break;
				}
			}

			//Fairshaire implementation
			share[0] = Math.min(rate[0],fairshare);
			share[1] = Math.min(rate[1],fairshare);
			share[2] = Math.min(rate[2],fairshare);


			while(true)
		{
			DatagramPacket packet = null;	
			// number of empty buffers
			int noEmpty = 0;
				
			// get time when next packet can be sent 
			long startTime = System.nanoTime();
			long nextSendOK = senderActiveUntil;
		
			for(int i = 0; i<buffers.length; i++) {	
			long start = System.nanoTime();


			while((System.nanoTime() - start) < share[i]*1000*1000*1000/max_rate) {
				// if no packet is in transmission look for next packet to send
				if (System.nanoTime() >= nextSendOK)
				{
					/*
					 * Check if there is a packet in queue.
					 * If there is send packet, remove it form queue.
					 * If there is no packet increase noEmpty that keeps track of number of empty queues 
					 */
					if ((packet = buffers[i].peek()) != null)
					{
						out[i].println((System.nanoTime() - start_time)/1000 + "\t" + packet.getLength() + "\t" + i+1);
						sendPacket(packet, startTime);
						buffers[i].removePacket();
					}
					else
					{
						noEmpty++;
					}
	
	
	
					/*
					 * TODO:
					 * Implement sending of a SINGLE packet from packet scheduler.
					 * Variable noEmpty must be set to total number of queues if all are empty. 
					 * 
					 * NOTE: The code you are adding sends at most one packet!
					 * 
					 * Look at the example above to find out how to check if a particular queue is empty.
					 * Once you have found from which queue to send, send a packet and remove it from that queue 
					 * (as in example above).
					 */
				}
				else
				{
					// wait until it is possible to send
					long timeToWait = nextSendOK-startTime;
					try 
					{
						Thread.sleep(timeToWait/1000000, (int)timeToWait%1000000);
					} 
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
			}
			}	
			// there are no packets in buffers to send. Wait for one to arrive to buffer.
			// (busy wait)
			if (noEmpty == buffers.length)
			{	
				boolean anyNotEmpty = false;
				for (int i=0; i<buffers.length; i++)
				{
					if (buffers[i].getSize()>0)
					{
						anyNotEmpty = true;
					}
				}
				while(!anyNotEmpty)
				{
					for (int i=0; i<buffers.length; i++)
					{
						if (buffers[i].getSize()>0)
						{
							anyNotEmpty = true;
						}
					}
				}
			}	
		}
	}
}
