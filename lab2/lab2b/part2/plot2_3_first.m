clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Reading trace, output and bucket_ex file
[packet_no_gen, time_gen, packetsize_gen] = textread('./ex3/trace.txt', '%f %f %f');
[packet_no_bucket, time_bucket, packetsize_bucket] = textread('./ex3/bucket_ex3.txt', '%f %f %f');
[packet_no_sink, time_sink, packetsize_sink] = textread('./ex3/output.txt', '%f %f %f');

count_sink = length(packet_no_sink);
count_bucket = length(packet_no_bucket);
count_gen = length(packet_no_gen);

% initialize cumulative arrays
cumulative_sink = zeros(1, count_sink);
cumulative_gen = zeros(1, count_gen);
cumulative_bucket = zeros(1, count_bucket);

cumulative_time_sink = zeros(1, count_sink);
cumulative_time_gen = zeros(1, count_gen);
cumulative_time_bucket = zeros(1, count_bucket);

% initialize first packet, hardcoded
cumulative_sink(1) = packetsize_sink(1);
cumulative_time_sink(1) = time_sink(1);

cumulative_bucket(1) = packetsize_bucket(1);
cumulative_time_bucket(1) = time_bucket(1);

%%%%%%%%%%%%%%%%
% For gen
%%%%%%%%%%%%%%%%
% start with second packet
i = 2;

% for the gen
while i <= count_gen
    cumulative_gen(i) = cumulative_gen(i-1) + packetsize_gen(i);
    cumulative_time_gen(i) = time_gen(i) + cumulative_time_gen(i-1);
    i = i + 1;
end

%%%%%%%%%%%%%%%%
% For bucket
%%%%%%%%%%%%%%%%

i = 2;

% for the gen
while i <= count_bucket
    cumulative_bucket(i) = cumulative_bucket(i-1) + packetsize_bucket(i);
    cumulative_time_bucket(i) = time_bucket(i) + cumulative_time_bucket(i-1);
    i = i + 1;
end

%%%%%%%%%%%%%%%%
% For sink
%%%%%%%%%%%%%%%%

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

subplot(3,1,1);plot(cumulative_time_gen, cumulative_gen);
title('Traffic Generator packets sent over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(3,1,3);plot(cumulative_time_bucket, cumulative_bucket);
title('Traffic Shaper/Bucket Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(3,1,2);plot(cumulative_time_sink, cumulative_sink);
title('Traffic Sink Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');
