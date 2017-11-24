clear all
close all;
%%%%%%%% load simulations

cd ..
cd data
load('traj.mat', 'traj');
cd ..
cd matlab

runs=length(traj);
xMax=length(traj(1,:,1,1));
yMax=length(traj(1,1,:,1));


%import result file.txt
cd ..
cd data
dataQuant = importdata('dataSatQuant.txt');
dataBool = importdata('dataSatBool.txt');

resultDataQuant=zeros(xMax,yMax);
resultDataBool=zeros(xMax,yMax);
% %%%% conversion  to traj in a grid  
    i=1;
    for y=1:1:yMax     
        for x=1:1:xMax
            resultDataQuant(x,y)=dataQuant(1,i);
            resultDataBool(x,y)=dataBool(1,i);
            i=i+1;          
        end
    end   

 
k = 20;
% subplot 1 (the simulation of A at steady state)
positionVector = [0.05, 0.30, 0.24, 0.3];
subplot('Position',positionVector);% second subplot
A(:,:)=traj(length(traj),:,:,1);
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

savefig('solution.fig')

cd ..
cd matlab