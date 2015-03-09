import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;
import TokenBucket.TokenBucket;

/* 
 *  ECE466 Lab 2b - Part 2
 *  David Zhang 997480088
 */

class TrafficShaper {  
  public static void main (String[] args) { 
    int experiment = Integer.parseInt(args[0]);

    TokenBucket lb;
    if ( experiment == 1 ) {
      /*
       * This experiment runs these
       * generator at 800kbps
       * shaper at 2000kbps  
       */
      lb = new TokenBucket (4444,         // inPort"localhost",   
                  "localhost",      // outAddress
                  4445,         // outPort 
                  100,          // maxPacketSize (bytes)
                  100*100,        // bufferCapacity (bytes)
                  100,          // bucketSize (num)
                  250000,         // bucketRate (tokens/sec)
                  "bucket_ex1.txt");  // fileName
      new Thread(lb).start();

    } else if ( experiment == 2 ) {
      /*
       * This experiment runs these
       * generator at 800kbps
       * shaper at 2000kbps  
       */
      lb = new TokenBucket (4444,         // inPort"localhost",   
                  "localhost",      // outAddress
                  4445,         // outPort 
                  100,          // maxPacketSize (bytes)
                  100*100,        // bufferCapacity (bytes)
                  500,          // bucketSize (num)
                  250000,         // bucketRate (tokens/sec)
                  "bucket_ex2.txt");  // fileName
      new Thread(lb).start();
    } else if ( experiment == 3 ) {
      /*
       * This experiment runs these
       * generator at 2000kpbs
       * shaper at 2000kbps  
       */
      lb = new TokenBucket (4444,         // inPort"localhost",   
                  "localhost",      // outAddress
                  4445,         // outPort 
                  100,          // maxPacketSize (bytes)
                  100*100,        // bufferCapacity (bytes)
                  10000,          // bucketSize (num)
                  500000,         // bucketRate (tokens/sec)
                  "bucket_ex3.txt");  // fileName
      new Thread(lb).start();
    } else if ( experiment == 4 ) {
      /*
       * This experiment runs max
       */
      lb = new TokenBucket (4444,         // inPort"localhost",   
                  "localhost",      // outAddress
                  4445,         // outPort 
                  100,          // maxPacketSize (bytes)
                  100*100,        // bufferCapacity (bytes)
                  500,          // bucketSize (num)
                  250000,         // bucketRate (tokens/sec)
                  "bucket_ex3.txt");  // fileName
      new Thread(lb).start();
    }
  }  
}
