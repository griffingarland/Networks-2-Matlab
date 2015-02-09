all:
	javac TrafficSink.java TrafficGenerator.java Receiver.java Sender.java ReadFileWriteFile.java Bucket.java Buffer.java TokenBucket.java TokenBucketReceiver.java TokenBucketSender.java Main.java
	mkdir -p TokenBucket
	mv TokenBucket.class TokenBucket
	mv TokenBucketSender.class TokenBucket
	mv Buffer.class TokenBucket
	mv Bucket.class TokenBucket
	mv TokenBucketReceiver.class TokenBucket
clean:
	rm -f *.class
	rm -rf TokenBucket
