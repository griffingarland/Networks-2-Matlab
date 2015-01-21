clc;clear all;
format long g;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Reading data from the file
%Note: - time is in miliseconds and framesize is in Bytes
%      - file is sorted in transmit sequence
%  Column 1:   index of frame (in display sequence)
%  Column 2:   time of frame in ms (in display sequence)
%  Column 3:   type of frame (I, P, B)
%  Column 4:   size of frame (in Bytes)
%  Column 5-7: not used
%
% Since we are interested in the transmit sequence we ignore Columns 1 and
% 2. So, we are only interested in the following columns: 
%       Column 3:  assigned to type_f
%       Column 4:   assigned to framesize_f
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
[index, time, type_f, framesize_f, dummy1, dymmy2, dymmy3 ] = textread('movietrace.data', '%f %f %c %f %f %f %f');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   CODE FOR EXERCISE 2.2   (version: Spring 2007)
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Extracting the I,P,B frmes characteristics from the source file
%frame size of I frames  : framesize_I
%frame size of P frames  : framesize_p 
%frame size of B frames  : framesize_B
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
a=0;
b=0;
c=0;
framesize_I=zeros(3375,1);
framesize_B=zeros(40497,1);
framesize_P=zeros(10125,1);
avg_size=zeros(53997,1);
avg_time=zeros(53997,1);
smallest=100000;
largest=0;
peak=0;
temp_peak=0;
total_bytes=0;
for i=1:length(index)
    if framesize_f(i) < smallest
        smallest=framesize_f(i);
    end
    if framesize_f(i) > largest
        largest=framesize_f(i);
    end
    if type_f(i)=='I'
        a=a+1;
        framesize_I(a)=framesize_f(i);
    end
     if type_f(i)=='B'
         b=b+1;
         framesize_B(b)=framesize_f(i);
     end
     if type_f(i)=='P'
         c=c+1;
         framesize_P(c)=framesize_f(i);
     end
     avg_size(i)=framesize_f(i);
     total_bytes=total_bytes+framesize_f(i);

end

%1st dot
disp('I frames:');
disp(a);
disp('B frames:');
disp(b);
disp('P frames');
disp(c);
disp('total frames');
disp(a+b+c);
disp('total bytes');
disp(total_bytes);

%2nd dot
disp('smallest Frame');
disp(smallest);
disp('largest Frame');
disp(largest);
disp('mean size');
disp(mean(avg_size));

%3rd dot
disp('smallest I');
disp(min(framesize_I));
disp('largest I');
disp(max(framesize_I));
disp('mean I');
disp(mean(framesize_I));
disp('smallest B');
disp(min(framesize_B));
disp('largest B');
disp(max(framesize_B));
disp('mean B');
disp(mean(framesize_B));
disp('smallest P');
disp(min(framesize_P));
disp('largest P');
disp(max(framesize_P));
disp('mean P');
disp(mean(framesize_P));

tobesorted=sortrows([index,time]);
sorted=tobesorted(:,2);
mean_time=mean(diff(sorted));
disp(mean_time);

%4th dot
disp('Mean bitrate');
disp(mean(avg_size)/mean_time);

%5th dot
disp('Peak bitrate');
disp(largest/mean_time);

%6th dot
disp('peak to mean bitrate');
disp((largest/mean_time)/(mean(avg_size)/mean_time));


%part 2.2 1
figure(2);
plot(index,framesize_f);
title('Frame size as a function of sequence number');
ylabel('Size of frame');
xlabel('Sequence number');

%Part 2.2 2
%Force label the y axis by setting the 'yticklabel' to get the relative
%frequncies instead of absolute frequencies
figure(4);
hist(framesize_P,10125);
title('Relative frequency of P frame by Size');
labels=[0,.0005,.001,.0015,.002,.0025,.003];
set(gca,'YTickLabel',labels);
ylabel('Relative frequency');
xlabel('Size of P frame (Bytes)');

figure(5);
hist(framesize_I,3375);
title('Relative frequency of I frame by Size');
labels=[0,.0006,.0012,.0018,.0024,.003,.0036];
%set(gca,'YTickLabel',labels);
ylabel('Relative frequency');
xlabel('Size of I frame (Bytes)');

figure(6);
hist(framesize_B,40497);
title('Relative frequency of B frame by Size');
labels=[0,.0005,.001,.0015,.002,.0025,.003];
%set(gca,'YTickLabel',labels);
ylabel('Relative frequency');
xlabel('Size of B frame (Bytes)');


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Hint1: You may use the MATLAB functions 'length()','mean()','max()','min()'.
%       which calculate the length,mean,max,min of a
%       vector (for example max(framesize_P) will give you the size of
%       largest P frame
%Hint2: Use the 'plot' function to graph the framesize as a function of the frame
%       sequence number. 
%Hint3: Use the function 'hist' to show the distribution of the frames. Before 
%       that function type 'figure(2);' to indicate your figure number.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
%   CODE FOR EXERCISE 2.3   (version: Spring 2007)
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%The following code will generates Plot 1. You generate Plot2 , Plot3 on
%your own. 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% The next line assigns a label (figure number) to the figure 
figure(3);

initial_point=1;
ag_frame=500;

jj=initial_point;
i=1;
bytes_f=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f(i)=bytes_f(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,1);bar(bytes_f);
set(gca, 'XTickLabel',{1,10001,20001,30001,40001,50001,60001})
title('Amount of data per 500 frames');
ylabel('Bytes');
xlabel('Frame');

%plot 2, starting at frame 3000, 50 frames per bar

initial_point=3000;
ag_frame=50;

jj=initial_point;
i=1;
bytes_f=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f(i)=bytes_f(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,2);bar(bytes_f);
set(gca, 'XTickLabel',{3000,4000,5000,6000,7000,8000,9000})
title('Amount of data per 50 frames');
ylabel('Bytes');
xlabel('Frame');

%plot 2, starting at frame 3000, 50 frames per bar

initial_point=5010;
ag_frame=5;

jj=initial_point;
i=1;
bytes_f=zeros(1,100);
while i<=100
while ((jj-initial_point)<=ag_frame*i && jj<length(framesize_f))
bytes_f(i)=bytes_f(i)+framesize_f(jj);
jj=jj+1;
end
i=i+1;
end
subplot(3,1,3);bar(bytes_f);
set(gca, 'XTickLabel',{5000,5100,5200,5300,5400,5500,5600})
title('Amount of data per 5 frames');
ylabel('Bytes');
xlabel('Frame');
