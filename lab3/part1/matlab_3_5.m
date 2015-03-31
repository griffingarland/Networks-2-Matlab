clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
close all;
clearvars;
%size = 50000;
size = 5000;

%1......trace file
[packet_no_p, time_p, packetsize_p] = textread('poisson3.data', '%f %f %f');
figure(1);
time_array = zeros(1,size);
cumulative_arrival = zeros(1,size);

time_array(1) = time_p(1);
cumulative_arrival(1) = packetsize_p(1);
i=2
while i<=size
    time_array(i) = time_p(i);
    cumulative_arrival(i) = cumulative_arrival(i-1) + packetsize_p(i);
    i=i+1;
end
    
subplot(3,1,1);
plot(time_array,cumulative_arrival);
title('Trace file (poisson3.data)');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');

%2..........traffic scheduler output
[arrival_time, packetsize_p2, back_log] = textread('scheduler.txt5', '%f %f %f');
time_array2 = zeros(1,size);
cumulative_arrival2 = zeros(1,size);

time_array2(1) = arrival_time(1);
cumulative_arrival2(1) = packetsize_p2(1);
i=2
while i<=size
    time_array2(i) = time_array2(i-1) + arrival_time(i);
    cumulative_arrival2(i) = cumulative_arrival2(i-1) + packetsize_p2(i);
    i=i+1;
end

subplot(3,1,2);
plot(time_array2,cumulative_arrival2);
title('Token Bucket');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');


%3............. sink output
[packet_no_p3, packetsize_p3, arrival_time] = textread('output.txt5', '%f %f %f');
time_array3 = zeros(1,size);
cumulative_arrival3 = zeros(1,size);

time_array3(1) = arrival_time(1);
cumulative_arrival3(1) = packetsize_p3(1);
i=2
while i<=size
    time_array3(i) = time_array3(i-1) + arrival_time(i);
    cumulative_arrival3(i) = cumulative_arrival3(i-1) + packetsize_p3(i);
    i=i+1;
end

subplot(3,1,3);
plot(time_array3,cumulative_arrival3);
title('Traffic Sink');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');


%backlog
figure(2);
subplot(1,1,1);
back_log_to_plot = zeros(1,size);
i=1;
while i<=size
    back_log_to_plot(i) = back_log(i);
    i=i+1;
end
axis([1 size -100 11000])
h1 = plot(time_array2, back_log_to_plot,'b')
hold on
%hkeg1= legend(h1,'backlog');

title('Backlog for N=5');
xlabel('time (in microseconds)');
ylabel('backlog (bytes)');


%plot all three arrrivals on one plot
figure(3);
h2 = plot(time_array,cumulative_arrival, 'r', time_array2,cumulative_arrival2, 'g', time_array3,cumulative_arrival3, 'b' );
hold on
hkeg2 = legend(h2, 'trace file', 'traffic shaper', 'traffic sink');
title('Poisson data');
xlabel('time (in microseconds)');
ylabel('culmulative arrival (in bytes)');



%waiting time
time_array4 = zeros(1,size);
time_array4 = cumsum(time_array2); %cumsum of traffic scheduler
time_array5 = zeros(1,size);
time_array5 = cumsum(time_array3); %cumsum of sink output
figure(4);
plot(time_array, time_array4,'r', time_array, time_array5, 'g');
title('compare culsum time');
xlabel('red is scheduler');
ylabel('green is sink');

waiting_time1 = zeros(1,size); 
i=1;
while i<=size
    j=i;
    while (((back_log(i) + packetsize_p2(i)) > 102400) && j< size )
        j = j+1;
    end
    waiting_time1(i) = time_array5(i) - time_array4(j); %microseconds!!!
    if waiting_time1(i) < 0
        waiting_time1(i) = 0;
    end
    i = i+1;
end
figure(5);
plot(time_array3, waiting_time1);
title('Waiting time (N=5)');
xlabel('time (in microseconds)');
ylabel('time (in microseconds)');


%number of discarded packets
num_of_discard1 = zeros(1, size);
i=1;
while i<=size
    back_log_to_plot(i) = back_log(i);
    if ((back_log(i) + packetsize_p2(i)) > 102400)
        num_of_discard1(i) = 1;
    end
    i=i+1;
end

figure(6);
plot(time_array2, cumsum(num_of_discard1),'b'); %plot cumsum of the discards
title('Number of Discarded packets for N=5');
xlabel('time (in microseconds)');
ylabel('total discarded packets');
