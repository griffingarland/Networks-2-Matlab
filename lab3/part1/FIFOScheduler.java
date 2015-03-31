import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;
import PacketScheduler.PacketScheduler;

class FIFOScheduler {
		public static void main(String[] args) {
		
	     PacketScheduler scheduler = new PacketScheduler(4444, "localhost", 4445, 
			         45*1000*1000*1000, 1, 1024, 
				           new long [] {100*1024}, 
				        "scheduler.txt"); 		   
		new Thread(scheduler).start();
		
		
		}
}
