clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_gen, time_gen, packetsize_gen] = textread('ethernet_short.txt', '%f %f %f');
[packet_no_sink, time_sink, packetsize_sink] = textread('output.txt', '%f %f %f');
[packet_bucket_arrival, packet_size, X, L] = textread('bucket.txt', '%f %f %f %f');

count_sink = length(packet_no_sink);
count_gen = length(packet_no_gen);
count_bucket = length(packet_bucket_arrival);

% initialize cumulative arrays
cumulative_sink = zeros(1, count_sink);
cumulative_bucket = zeros(1, count_bucket);
cumulative_time_bucket = zeros(1, count_bucket);
cumulative_time_sink = zeros(1, count_sink);
cumulative_time_gen = zeros(1, count_bucket);
cumulative_time_backlog = zeros(1, count_bucket);

% initialize first packet, hardcoded
cumulative_sink(1) = packetsize_sink(1);
cumulative_time_sink(1) = time_sink(1);
cumulative_bucket(1) = packet_bucket_arrival(1);
cumulative_time_bucket(1) = L(1); 
cumulative_time_backlog(1) = 0;

i = 2;

while i <= count_bucket
   cumulative_bucket(i) = cumulative_bucket(i-1) + packet_bucket_arrival(i);
   cumulative_time_bucket(i) = L(i); 
   i = i+1;
end

i = 2;

while i <= count_bucket
   cumulative_time_backlog(i) = X(i); 
   i = i+1;
end

% start with second packet
i = 2;

% for the sink
while i <= count_bucket
    cumulative_time_gen(i) = packet_bucket_arrival(i) + cumulative_time_gen(i-1);
    i = i + 1;
end

% start with second packet
i = 2;

% for the sink
while i <= count_sink
    cumulative_sink(i) = cumulative_sink(i-1) + packetsize_sink(i);
    cumulative_time_sink(i) = time_sink(i) + cumulative_time_sink(i-1);
    i = i + 1;
end

disp(length(time_gen));

% Make plot
figure(1); 
%plot(cumulative_time_token, cumulative_token,time_gen, cumulative_gen,cumulative_time_sink, cumulative_sink);

subplot(4,1,1);plot(cumulative_bucket, cumulative_time_gen);
title('Bucket Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(4,1,2);plot(cumulative_time_sink, cumulative_sink);
title('Sink Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(4,1,3);plot(cumulative_bucket,cumulative_time_bucket);
title('Bucket Fullness over time');
xlabel('Arrival time (usec)');
ylabel('Fullness (bytes)');

subplot(4,1,4);plot(cumulative_bucket,cumulative_time_backlog);
title('Backlog over time');
xlabel('Arrival time (usec)');
ylabel('Backlog (bytes)');