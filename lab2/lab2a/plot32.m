clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_gen, time_gen, packetsize_gen] = textread('movietrace.txt', '%f %f %f');
[packet_no_sink, time_sink, packetsize_sink] = textread('sink.txt', '%f %f %f');
[token_time_diff, token_size, test, test2] = textread('bucket.txt', '%f %f %f %f');

count_gen = length(time_gen);
count_sink = max(packet_no_sink);
count_token = max(packet_no_gen);

% initialize cumulative arrays
cumulative_gen = zeros(1, count_gen);
cumulative_sink = zeros(1, count_sink);
cumulative_token = zeros(1, count_sink);
cumulative_time_sink = zeros(1, count_sink);
cumulative_time_token = zeros(1, count_sink);

% initialize first packet, hardcoded
cumulative_gen(1) = packetsize_gen(1);
cumulative_sink(1) = packetsize_sink(1);
cumulative_token(1) = token_size(1);
cumulative_time_sink(1) = time_sink(1);
cumulative_time_token(1) = token_time_diff(1);

% start with second packet
i = 2;

% for generator
while i <= count_gen
    cumulative_gen(i) = cumulative_gen(i-1) + packetsize_gen(i);
    i = i + 1;
end

% start with second packet
i = 2;

% for token bucket
while i <= count_sink
    cumulative_token(i) = cumulative_token(i-1) + token_size(i);
    cumulative_time_token(i) = token_time_diff(i) + cumulative_time_token(i-1);
    i = i + 1;
end

i = 2;

% for the sink
while i <= count_sink
    cumulative_sink(i) = cumulative_sink(i-1) + packetsize_sink(i);
    cumulative_time_sink(i) = time_sink(i) + cumulative_time_sink(i-1);
    i = i + 1;
end

disp(length(time_gen));
disp(length(cumulative_gen));

% Make plot
figure(1); 
%plot(cumulative_time_token, cumulative_token,time_gen, cumulative_gen,cumulative_time_sink, cumulative_sink);

subplot(3,1,2);plot(cumulative_time_token, cumulative_token);
title('Token Bucket Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(3,1,1);plot(time_gen, cumulative_gen);
title('Trace file data sent over time');
xlabel('Arrival time (usec)');
ylabel('Arrival (bytes)');

subplot(3,1,3);plot(cumulative_time_sink, cumulative_sink);
title('Sink Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');
