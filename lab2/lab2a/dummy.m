clc;clear all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_s, time_s, packetsize_s] = textread('poisson3.txt', '%f %f %f');
[packet_no_r, time_r, packetsize_r] = textread('sink.txt', '%f %f %f');

% init cumulative arrays and set first to first packet size
count_s = max(packet_no_s);
count_r = max(packet_no_r);
cumulative_s = zeros(1,count_s);
cumulative_r = zeros(1,count_r);
cumulative_s(1) = packetsize_s(1);
cumulative_r(1) = packetsize_r(1);

i = 2;
while i <= count_s
    cumulative_s(i) = cumulative_s(i-1) + packetsize_s(i);
    i = i+1;
end
i = 2;
while i <= count_r
    cumulative_r(i) = cumulative_r(i-1) + packetsize_s(i);
    time_r(i) = time_r(i) + time_r(i-1);
    i = i+1;
end

% Plotting
figure(1); 
plot(time_s, cumulative_s, time_r, cumulative_r);
hleg1 = legend('Poisson', 'Received');
set(hleg1, 'Location', 'NorthWest');
title('Poisson data vs Received data');
xlabel('Arrival time (usec)');
ylabel('Cumulative arrival (bytes)');