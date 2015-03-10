import java.io.*; 
import java.util.*; 
import java.net.*;
import java.lang.*;
import TokenBucket.TokenBucket;

class TrafficShaper {  
  public static void main (String[] args) { 
    int experiment = Integer.parseInt(args[0]);

    TokenBucket tb;
    if ( experiment == 1 ) {
      
      tb = new TokenBucket (4444,    
                  "localhost",  
                  4445,  
                  100,    
                  100*100,     
                  100, 
                  250000,  
                  "bucket_ex1.txt");
      new Threadtlb).start();

    } else if ( experiment == 2 ) {
      
      tb = new TokenBucket (4444,  
                  "localhost", 
                  4445,  
                  100, 
                  100*100, 
                  500,  
                  250000, 
                  "bucket_ex2.txt");
      new Thread(tb).start();
    } else if ( experiment == 3 ) {
      
      tb = new TokenBucket (4444,   
                  "localhost",  
                  4445,  
                  100,   
                  100*100,       
                  10000,   
                  500000,   
                  "bucket_ex3.txt"); 
      new Thread(lb).start();
    } else if ( experiment == 4 ) {
      tb = new TokenBucket (4444, 
                  "localhost",
                  4445,
                  100,
                  100*100,
                  500,
                  250000,
                  "bucket_ex3.txt");
      new Thread(tb).start();
    }
  }  
}
