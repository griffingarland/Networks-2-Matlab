
import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;
import PacketScheduler.PacketScheduler;

class FIFOScheduler {
		public static void main(String[] args) {
		
	     PacketScheduler scheduler = new PacketScheduler(4444, "localhost", 4445, 
			         1000*1000*1000*1000, 2, 1480, 
				           new long [] {1000*1480,10000*1480}, 
				        "scheduler.txt"); 		   
		new Thread(scheduler).start();
		
		
		}
}
