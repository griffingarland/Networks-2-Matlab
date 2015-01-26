clc;clear all; clf;
format long g;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from a file
%Note that time is in micro seconds and packetsize is in Bytes
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[packet_no_p, time_p, packetsize_p] = textread('poisson1.data', '%f %f %f');

%%%%%%%%%%%%%%%%%%%%%%%%%Exercise 1.2%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generate Plot 1; You generate Plot2 , Plot3.
%Hint1: For Plot2 and Plot3, you only need to change 'initial_p', the
%       initial time in microseconds, and 'ag_frame', the time period of
%       aggregation
%Hint2: After adding Plot2 and Plot3 to this part, you can use 'subplot(3,1,2);'
%       and 'subplot(3,1,3);' respectively to show 3 plots in the same figure.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
figure(1);
jj=1;
i=1;
diff=zeros(124999,1);
initial_p=0;
ag_time=1000000;
bytes_p_one=zeros(1,100);
bytes_p_two=zeros(1,100);
bytes_p_three=zeros(1,100);

%plot one, from 0 seconds on, 1 sec timestep
%also calculates mean/variance

while time_p(jj)<=initial_p
    jj=jj+1;
end
diff(1)=time_p(1)/1000000;
while i<=100
while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
bytes_p_one(i)=bytes_p_one(i)+packetsize_p(jj);
if(jj>1)
    diff(jj)=(time_p(jj)-time_p(jj-1))/1000000;
end
jj=jj+1;
end
i=i+1;
end
disp('part 1 mean:');
disp(mean(diff));
disp('part 1 var:');
disp(var(diff));

%plot two, from 30 seconds on, .1 sec timestep
jj=1;
i=1;
ag_time=100000;
initial_p=30;
while time_p(jj)<=initial_p
    jj=jj+1;
end

while i<=100
while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
bytes_p_two(i)=bytes_p_two(i)+packetsize_p(jj);
jj=jj+1;
end
i=i+1;
end


%plot three, from 50.2 seconds on, .01 sec timestep
jj=1;
i=1;
ag_time=10000;
initial_p=50.2;
while time_p(jj)<=initial_p
    jj=jj+1;
end

while i<=100
while ((time_p(jj)-initial_p)<=ag_time*i && jj<length(packetsize_p))
bytes_p_three(i)=bytes_p_three(i)+packetsize_p(jj);
jj=jj+1;
end
i=i+1;
end

%%%%%%%%
subplot(3,1,1);bar(bytes_p_one);
title('Bytes per Second with a 1 second timestamp');
ylabel('Bytes');
xlabel('Time (s)');
subplot(3,1,2);bar(bytes_p_two);
set(gca, 'XTickLabel',{30.0,32.0,34.0,36.0,38.0,40.0,42.0})
title('Bytes per Second with a .1 second timestamp');
ylabel('Bytes');
xlabel('Time (s)');
subplot(3,1,3);bar(bytes_p_three);
title('Bytes per Second with a .01 second timestamp');
set(gca, 'XTickLabel',{50.20,50.22,50.24,50.26,50.28,50.30,50.32})
ylabel('Bytes');
xlabel('Time (s)');
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Note: Run the same MATLAB code for Exercise 1.3 and 1.4 but change the
%second line of the code in order to read the files 'poisson2.data' and
%'poisson3.data' respectively.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


