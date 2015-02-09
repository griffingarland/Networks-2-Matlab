clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_gen, time_gen, packetsize_gen] = textread('poisson3.txt', '%f %f %f');
[packet_no_sink, time_sink, packetsize_sink] = textread('sink.txt', '%f %f %f');

count_gen = max(packet_no_gen);
count_sink = max(packet_no_sink);

% initialize cumulative arrays
cumulative_gen = zeros(1, count_gen);
cumulative_sink = zeros(1, count_sink);

% initialize first packet, hardcoded
cumulative_gen(1) = packetsize_gen(1);
cumulative_sink(1) = packetsize_sink(1);

% start with second packet
i = 2;

% for generator
while i <= count_gen
    cumulative_gen(i) = cumulative_gen(i-1) + packetsize_gen(i);
    i = i + 1;
end

i = 2;

% for the sink
while i <= count_sink
    cumulative_sink(i) = cumulative_sink(i-1) + packetsize_sink(i);
    time_sink(i) = time_sink(i) + time_sink(i-1);
    i = i + 1;
end

% Make plot
figure(1); 
plot(time_gen, cumulative_gen, time_sink, cumulative_sink);

hleg1 = legend('Trace data', 'Output data');

set(hleg1, 'Location', 'NorthWest');

title('Trace file vs Output file');

xlabel('Arrival time (usec)');

ylabel('Cumulative arrival (bytes)');