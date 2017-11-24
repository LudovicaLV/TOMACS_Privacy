%patternSize
patternSize = 32;
labels = {'RED'};
depth =  5;

pCount = 4;
npCount = 4; % for each
npRcount = 4;

% parameters :
PatternParameters.CA = 5.6;  % diffusion
PatternParameters.CB = 24.5; % diffusion
% PatternParameters.CA = 0.2;  % diffusion
% PatternParameters.CB = 20; % diffusion
PatternParameters.dt = 0.01;
PatternParameters.T = 50;

NPparameters(1) = PatternParameters;
NPparameters(1).CA = 0.2;
NPparameters(1).CB = 20;

NPparameters(2) = PatternParameters;
NPparameters(2).CA = 1;
NPparameters(2).CB = 3;


NPparameters(3) = PatternParameters;
NPparameters(3).CA = 0.1;
NPparameters(3).CB = 1;

NPparameters(4) = PatternParameters;
NPparameters(4).CA = 0.08;
NPparameters(4).CB = 19.3;


NPparameters(4) = PatternParameters;
NPparameters(4).CA = 6;

% random parameters:

Rparam = PatternParameters;

% im = zeros(patternSize, patternSize, 3);

% plot all:
% im(:,:,1) = TuringSimulation2(patternSize, PatternParameters, true);

load('Astart');
load('Bstart');

noise =0;
%traj = TuringFixedSim(Astart, Bstart, patternSize, PatternParameters, false);
trajectory = TuringFixedSimStoch(Astart, Bstart, patternSize, PatternParameters, noise);

traj=trajectory(1:50:length(trajectory),:,:,:);
%[trajfin, traj] = SimulationTemporal(patternSize, PatternParameters, true);

% figure, 

Atraj=traj(:,:,:,1);
Btraj=traj(:,:,:,2);

tend=length(Atraj);
X=zeros(patternSize,patternSize);
X(:,:)=Atraj(length(Atraj),:,:);
surf(X)
view(2);
%contourf(X)
xlabel('X','Fontsize', 18);
ylabel('Y','Fontsize', 18);
zlabel('A','Fontsize', 18)
set(gca,'FontSize',18)
colormap jet
colorbar('FontSize',18);
axis([1 patternSize 1 patternSize]);


% mkdir('stochDataPattern')
% cd stochDataPattern
cd ../data/stochDataPattern
for y=1:1:32     
    for x=1:1:32
        fname=sprintf('values_%d_%d_xA.dat',x,y);         
        dlmwrite(fname,traj(:,x,y,1));
        fname=sprintf('values_%d_%d_xB.dat',x,y);
        dlmwrite(fname,traj(:,x,y,2));
    end
end
time = 0:1:length(traj)-1;

dlmwrite('time.dat',time');
cd ..
cd ..
cd matlab
