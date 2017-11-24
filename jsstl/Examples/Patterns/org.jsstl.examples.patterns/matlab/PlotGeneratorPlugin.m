clear all
close all;

%%%%%%%% import trajectory

cd ../data/xxx
time = dlmread('time.dat');
trajscript=zeros(length(time), 32, 32, 2);

for y=1:1:32     
    for x=1:1:32 
        fname=sprintf('values_%d_%d_xA.dat',x,y);         
        trajscript(:,x,y,1) = dlmread(fname);
        fname=sprintf('values_%d_%d_xB.dat',x,y);
        trajscript(:,x,y,2) = dlmread(fname);
    end
end



runs=length(time);
xMax=length(trajscript(1,:,1,1));
yMax=length(trajscript(1,1,:,1));


resultDataQuant=zeros(xMax,yMax);
resultDataBool=zeros(xMax,yMax);

cd ..

fileID = fopen('spotformation_qualitative.csv','r+');
dataBool = textscan(fileID,'%d_%d %f', 'Delimiter',';');
fclose(fileID); 
fileID = fopen('spotformation_quantitative.csv','r+');
dataQuant = textscan(fileID,'%d_%d %f', 'Delimiter',';');
fclose(fileID); 
% %%%% conversion  to traj in a grid
    i=1;
    for y=1:1:yMax     
        for x=1:1:xMax
            resultDataBool(x,y)=dataBool{3}(i);
            resultDataQuant(x,y)=dataQuant{3}(i);
            
            i=i+1;          
        end
    end 

    % %%%% loading the whole quantitative spatio-temporal signal
cd ../data/datapluginquant

for y=1:1:32     
    for x=1:1:32 
        fname=sprintf('spotformation_quant__%d_%d.csv',x,y);         
        script(:,:,x,y) = dlmread(fname);
        monitor(:,x,y) = script(:,2,x,y);
    end
end


cd ..
cd ../matlab

k = 20;
% subplot 1 (the simulation of A at steady state)
positionVector = [0.05, 0.30, 0.24, 0.3];
subplot('Position',positionVector);% second subplot
A(:,:)=trajscript(length(trajscript),:,:,1);
surf(A,'LineStyle','none');
view(2);
set(gca,'FontSize',k)
grid off;
colormap jet
set(gca,'FontSize',k)
axis([1 xMax 1 yMax]);
title('(a)','FontSize',k);
colorbar('FontSize',k);

% subplot 2 (boolean semantics)
positionVector = [0.35, 0.30, 0.23, 0.3];
subplot('Position',positionVector);%
resultDatabool(:,:) = monitor(1,:,:);
surf(resultDataBool,'LineStyle','none');
view(2);
set(gca,'FontSize',k)
grid off;
colormap jet
set(gca,'FontSize',k)
axis([1 xMax 1 yMax]);
title('(b)','FontSize',k);

% subplot 3 (quantitative semantics)
positionVector = [0.64, 0.30, 0.24, 0.3];
subplot('Position',positionVector);% second subplot
resultDataQuant(:,:) = monitor(1,:,:);
surf(resultDataQuant,'LineStyle','none');
view(2);
set(gca,'FontSize',k)
grid off;
colormap jet
axis([1 xMax 1 yMax]);
title('(c)','FontSize',k);
% Create colorbar
colorbar('FontSize',k);
% 



