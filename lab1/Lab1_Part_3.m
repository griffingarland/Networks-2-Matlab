clc;clear all;
format long g;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading the data and putting the first 100000 entries in variables 
%Note that time is in seconds and framesize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
no_entries=100000;
[time1, framesize1] = textread('BC-pAug89-small.TL', '%f %f');
time=time1(1:no_entries);
framesize=framesize1(1:no_entries);
clear time1 framesize1
%%%%%%%%%%%%%%%%%%%%%%%%%Exercise %%%3.2%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generate Plot 1; You generate Plot2 , Plot3.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Part 3.3.1
figure(1);
jj=1;
i=1;
initial_p=0;
ag_time=1;
bytes_p=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p(i)=bytes_p(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,1);bar(bytes_p);
title('Amount of data per second');
ylabel('Bytes');
xlabel('Time (s)');

%Part 3.3.2
jj=1;
i=1;
initial_p=20;
ag_time=.1;
bytes_p2=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p2(i)=bytes_p2(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,2);bar(bytes_p2);
set(gca, 'XTickLabel',{20,22,24,26,28,30,'N/A'})
title('Amount of data per .1 second');
ylabel('Bytes');
xlabel('Time (s)');

%Part 3.3.3
jj=1;
i=1;
initial_p=90;
ag_time=.01;
bytes_p3=zeros(1,100);
while time(jj)<=initial_p
    jj=jj+1;
end
while i<=100
while ((time(jj)-initial_p)<=ag_time*i && jj<no_entries)
bytes_p3(i)=bytes_p3(i)+framesize(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,3);bar(bytes_p3);
set(gca, 'XTickLabel',{90,90.2,90.4,90.6,90.8,91,'N/A'})
title('Amount of data per .01 seconds');
ylabel('Bytes');
xlabel('Time (s)');

%Part 3.2
disp('num packets');
disp(numel(framesize));
disp('total number of bytes');
sumframesize=sum(framesize);
disp(sumframesize);
disp('mean bit rate');
averagerate=sumframesize/time(end);
disp(averagerate);
disp('max bit rate');
peakrate=max(framesize./diff([0.0; time]));
disp(peakrate);
disp('peak to mean bitrate');
disp(peakrate/averagerate);

%Part 3.2.1
figure(2);
bar(time,framesize);
title('packet size as a function of time');
ylabel('Time (s)');
xlabel('Packet Size (Bytes)');

%Part 3.2.2
figure(4)
hist(framesize,100000);
title('Relative frequency of Packet Size');
labels=[0,.05,.1,.15,.2,.25,.3];
set(gca,'YTickLabel',labels);
ylabel('Relative frequency');
xlabel('Packet Size (Bytes)');

