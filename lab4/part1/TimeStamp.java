import java.io.*;
import java.net.*;
import java.lang.*;


public class TimeStamp {
    public long sendTime = 0;
    public long receiveTime = 0;

     /* constructor */
    public TimeStamp(long send) {
        this.sendTime = send;
    }
    public TimeStamp setReceive(long receive) {
        this.receiveTime = receive;
        return this;
    }

    public String print() {
        return " " + String.valueOf(sendTime) + " " + String.valueOf(receiveTime);
    }

    public TimeStamp normalize () {
        this.receiveTime = this.receiveTime - this.sendTime;
        this.sendTime = 0;
        return this;
    }

    public TimeStamp add(TimeStamp ts) {
        this.sendTime = this.sendTime + ts.sendTime;
        this.receiveTime = this.receiveTime + ts.receiveTime;
        return this;
    }
}
