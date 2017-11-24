close all;

% patternSize
patternSize = 32;

% parameters :
PatternParameters.CA = 5.6;  % diffusion
PatternParameters.CB = 24.6; % diffusion
PatternParameters.dt = 0.01;
PatternParameters.T = 50;

[trajectory, counter] = TuringSimulation(patternSize, PatternParameters, false);
traj=trajectory(1:50:length(trajectory),:,:,:);

% %%%%% animation pattern formation
% for t=1:1:length(traj(:,1,1,1))
%     A(:,:)=traj(t,:,:,1);
%     surf(A)
%     view(2);
%     %contourf(X)
%     xlabel('X','Fontsize', 18);
%     ylabel('Y','Fontsize', 18);
%     zlabel('A','Fontsize', 18);
%     set(gca,'FontSize',18);
%     colormap jet
%     colorbar('FontSize',18);
%     axis([1 patternSize 1 patternSize]);
%     pause(0.01);
% end
% %  

A(:,:)=traj(length(traj),:,:,1);
surf(A)

cd ../data 
mkdir('xxx')
cd xxx
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
cd ../matlab


