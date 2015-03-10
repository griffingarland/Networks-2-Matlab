clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_gen, time_gen, packetsize_gen] = textread('poisson_short.data', '%f %f %f');
[packet_no_sink, time_sink, packetsize_sink] = textread('output_short.txt', '%f %f %f');

count_sink = length(packet_no_sink);
count_gen = length(packet_no_gen);

% initialize cumulative arrays
cumulative_sink = zeros(1, count_sink);
cumulative_gen = zeros(1, count_gen);
cumulative_time_sink = zeros(1, count_sink);
cumulative_time_gen = zeros(1, count_gen);

% initialize first packet, hardcoded
cumulative_sink(1) = packetsize_sink(1);
cumulative_time_sink(1) = time_sink(1);

% start with second packet
i = 2;

% for the sink
while i <= count_gen
    cumulative_gen(i) = cumulative_gen(i-1) + packetsize_gen(i);
    cumulative_time_gen(i) = time_gen(i) + cumulative_time_gen(i-1);
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

subplot(2,1,1);plot(cumulative_time_gen, cumulative_gen);
title('Poisson trace file packets sent over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');

subplot(2,1,2);plot(cumulative_time_sink, cumulative_sink);
title('Sink Arrivals over time');
xlabel('Arrival time (usec)');
ylabel('Arrivals (bytes)');