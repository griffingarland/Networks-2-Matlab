JAVA_HOME=/cad2/ece419s/java/jdk1.6.0/
JAVAC=${JAVA_HOME}/bin/javac -source 1.6

#java -classpath . BlackBox/BlackBox
all:
	${JAVAC} *.java
	${JAVAC} BlackBox/*.java 

clean:
	rm BlackBox/*.class
	rm *.class
	#rm *.class *.*~ *~

blackbox:
	${JAVAC} BlackBox/*.java

clean_blackbox:
	rm BlackBox/*.class
	
