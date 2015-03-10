import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;
import TokenBucket.TokenBucket;

class TrafficShaper {  
  public static void main (String[] args) { 

	int Rate = Integer.parseInt(args[0]);
    int Size = Integer.parseInt(args[1]);

    TokenBucket token_bucket;

      token_bucket = new TokenBucket (4444,         // inPort"localhost",   
                  "localhost",      // outAddress
                  4445,         // outPort 
                  10000,          // maxPacketSize (bytes)
                  Size,        // bufferCapacity (bytes)
                  Size,          // bucketSize (num)
                  Rate,         // bucketRate (tokens/sec)
                  "bucket.txt");  // fileName
      new Thread(token_bucket).start();
  }  
}
