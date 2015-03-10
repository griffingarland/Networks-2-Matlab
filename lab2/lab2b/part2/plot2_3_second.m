clc; clear all; clf;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Time unit: micro seconds 
% Packet size: bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Reading trace, output and bucket_ex file
%[packet_no_bucket, time_bucket, packetsize_bucket] = textread('./ex3/bucket_ex3.txt', '%f %f %f');

[time, size, backlog, content] = textread('./ex1/bucket_ex1.txt', '%f %f %f %f');

count_bucket = length(backlog);

% Make plot
figure(1); 
%plot(cumulative_time_token, cumulative_token,time_gen, cumulative_gen,cumulative_time_sink, cumulative_sink);

subplot(2,1,1);plot(backlog, time);
title('Backlog');
xlabel('Arrival time (usec)');
ylabel('X(t)');

subplot(2,1,2);plot(content, time);
title('Content of token bucket');
xlabel('Arrival time (usec)');
ylabel('L(t)');

