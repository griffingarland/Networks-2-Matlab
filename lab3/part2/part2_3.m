clc;clear all;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%Reading data from a file

%Note that time is in micro seconds and packetsize is in Bytes

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

close all;

clearvars;

size = 10000;



%1..........backlog

[arrival_time, packetsize_p, back_log1, back_log2, priority] = textread('scheduler.txt', '%f %f %f %f %f');

time_array = zeros(1,size);

time_array = cumsum(arrival_time);

figure(1);

subplot(2,1,1);

plot(time_array, back_log1);

title('Backlog for Poisson (N=1)');

xlabel('time (in microseconds)');

ylabel('backlog (in bytes)');

subplot(2,1,2);

plot(time_array, back_log2);

title('Backlog for Video (N=1)');

xlabel('time (in microseconds)');

ylabel('backlog (in bytes)');



%1..........waiting time

%sink1 is priority 1 traffic, poisson traffic

[packet_no_p1, packetsize_p_sink1, arrival_time_sink1] = textread('output_poisson.txt', '%f %f %f');

[arrival_time1, packetsize_p1, back_log11, back_log21, priority2] = textread('scheduler.txt', '%f %f %f %f %f');



%get culmulative time array of sink

time_array_sink = zeros(1,size);

time_array_sink = cumsum(arrival_time_sink1);

time_array_sch = zeros(1,size);

time_array_sch = cumsum(arrival_time1);



size = 100;

waiting_time1 = zeros(1,size);

i=1;

while i<=size

    j=i;

    while (((back_log11(i) + packetsize_p1(i)) > 102400) && j< size )

        j = j+1

    end

    waiting_time1(i) = time_array_sink(i) - time_array_sch(j); %microseconds!!!

    if waiting_time1(i) < 0

        waiting_time1(i) = 0;

    end

    i = i+1;

end

figure(2);

time_array_to_plot = zeros(1,size);

i=1;

while i<=size

    time_array_to_plot(i) = time_array_sink(i);

    i=i+1;

end

subplot(2,1,1);

plot(time_array_to_plot,waiting_time1);

title('Waiting time for Poisson (N=1)');

xlabel('time (in microseconds)');

ylabel('time (in microseconds)');

size=5000;


%sink2 is priority 2 traffic, video traffic

[packet_no_p1, packetsize_p_sink1, arrival_time_sink2] = textread('output_movie.txt', '%f %f %f');

[arrival_time2, packetsize_p12, back_log111, back_log21, priority2] = textread('scheduler.txt', '%f %f %f %f %f');



%get culmulative time array of sink

time_array_sink2 = zeros(1,size);

time_array_sink2 = cumsum(arrival_time_sink2);

time_array_sch2 = zeros(1,size);

time_array_sch2 = cumsum(arrival_time2);



size = 1000;

waiting_time2 = zeros(1,size);

i=1;

while i<=size

    j=i;

    while (((back_log21(i) + packetsize_p12(i)) > 102400) && j< size )

        j = j+1;

    end

    waiting_time2(i) = time_array_sink2(i) - time_array_sch2(j); %microseconds!!!

    if waiting_time2(i) < 0

        waiting_time2(i) = 0;

    end

    i = i+1;

end



time_array_to_plot2 = zeros(1,size);

i=1;

while i<=size

    time_array_to_plot2(i) = time_array_sink(i);

    i=i+1;

end

subplot(2,1,2);

plot(time_array_to_plot2,waiting_time2);

title('Waiting time for Video (N=1)');

xlabel('time (in microseconds)');

ylabel('time (in microseconds)');







%number of discarded packets

%poisson's information is packetsize_p1, back_log11, interr time in

%arrival_time

size=125000;

num_of_discard1 = zeros(1,size);

time_for_plotting = zeros(1,size);

i=1;

while i<=size

    if ((back_log11(i) + packetsize_p1(i)) > 102400)

        num_of_discard1(i) = 1;

    end

    time_for_plotting(i) = arrival_time1(i);

    i = i +1;

end



figure(3);

subplot(2,1,1);

plot (cumsum(time_for_plotting),cumsum(num_of_discard1));

title('Number of Discarded packets for Poisson (N=1)');

ylabel('total discarded packets');

xlabel('time (in microseconds)');



%number of discarded packets

%videos's information is packetsize_p11, back_log21, arrival_time2

%[arrival_time2, packetsize_p12, back_log111, back_log21, priority2]

size=50000;

num_of_discard2 = zeros(1,size);

time_for_plotting2 = zeros(1,size);

i=1;

while i<=size

    if ((back_log21(i) + packetsize_p12(i)) > 102400)

        num_of_discard2(i) = 1;

    end

    time_for_plotting2(i) = arrival_time2(i);

    i = i +1;

end





subplot(2,1,2);

plot (cumsum(time_for_plotting2),cumsum(num_of_discard2));

title('Number of Discarded packets for Video (N=1)');

ylabel('total discarded packets');

xlabel('time (in microseconds)');
