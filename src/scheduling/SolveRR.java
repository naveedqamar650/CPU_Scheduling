/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class SolveRR extends SolveFCFS
{
    private List<Process> seenProcesses;
    private List<Process> readyQueue;
    private int quantiumTime;
    
    public SolveRR(final List<Process> processes, final int quantiumTime) {
        super(processes, 3);
        this.seenProcesses = new ArrayList<Process>();
        this.readyQueue = new ArrayList<Process>();
        this.quantiumTime = quantiumTime;
        if (!this.checkAllATIsTheSame()) {
            Collections.sort(this.processes, new Comparator<Process>() {
                @Override
                public int compare(final Process p1, final Process p2) {
                    return p1.getArrivalTime() - p2.getArrivalTime();
                }
            });
            this.readyQueue.add(this.processes.get(0));
            this.seenProcesses.add(this.processes.get(0));
            this.readyQueue.get(0).setWaitingTime(0);
        }
        else {
            for (final Process p : this.processes) {
                this.readyQueue.add(p);
                System.out.println(p);
            }
        }
    }
    
    @Override
    public void solve() {
        int arrivalTime = this.readyQueue.get(0).getArrivalTime();
        final int i = 0;
        Process preemptiveProcess = null;
        System.out.printf("%b", this.checkAllRemainingTimeIsZero());
        if (this.processes.size() > 1) {
            for (int count = 1; count < this.processes.size(); ++count) {
                if (this.processes.get(count).getArrivalTime() == this.processes.get(0).getArrivalTime()) {
                    this.processes.get(count).setWaitingTime(0);
                }
            }
        }
        while (this.readyQueue.size() > 0 && !this.checkAllRemainingTimeIsZero()) {
            final Process runningProcess = this.readyQueue.get(0);
            System.out.println(runningProcess.getProcessID());
            this.readyQueue.remove(0);
            if (preemptiveProcess != null) {
                this.readyQueue.add(preemptiveProcess);
            }
            int max = arrivalTime;
            if (runningProcess.getRemainingTime() < this.quantiumTime) {
                max += runningProcess.getRemainingTime();
            }
            else {
                max += this.quantiumTime;
            }
            for (int count2 = arrivalTime; count2 < max; ++count2) {
                if (!this.checkAllATIsTheSame() && this.seenProcesses.size() < this.processes.size()) {
                    for (int count3 = 0; count3 < this.processes.size(); ++count3) {
                        if (this.processes.get(count3).getArrivalTime() <= count2 + 1 && !this.contains(this.seenProcesses, this.processes.get(count3).getProcessID())) {
                            this.seenProcesses.add(this.processes.get(count3));
                            this.readyQueue.add(this.processes.get(count3));
                        }
                    }
                }
                this.ganttChartText.add(String.valueOf(runningProcess.getProcessID()) + "(" + count2 + ")");
                this.cpuStates.add(runningProcess.getProcessID());
                runningProcess.setRemainingTime(runningProcess.getRemainingTime() - 1);
                this.remainingBurstTimeList.add(runningProcess.getRemainingTime());
                this.remainingBurstTimePositionList.add(runningProcess.getNo());
                final List<Integer> waiting = new ArrayList<Integer>();
                final List<Integer> position = new ArrayList<Integer>();
                for (final Process p : this.readyQueue) {
                    p.setWaitingTime(p.getWaitingTime() + 1);
                    waiting.add(p.getWaitingTime());
                    position.add(p.getNo());
                }
                this.waitingTimeList.add(waiting);
                this.waitingTimePositionList.add(position);
                final int progress = runningProcess.getBurstTime() - runningProcess.getRemainingTime();
                this.valueProgressList.add(progress);
                this.processProgressList.add(runningProcess);
                if (count2 == max - 1) {
                    if (runningProcess.getRemainingTime() > 0) {
                        preemptiveProcess = runningProcess;
                    }
                    else {
                        preemptiveProcess = null;
                    }
                }
                String s = "";
                for (final Process p2 : this.readyQueue) {
                    s = String.valueOf(s) + p2.getProcessID() + " ";
                }
                this.readyQueueList.add(s);
            }
            arrivalTime = max;
        }
        for (final Process p3 : this.processes) {
            if (p3.getRemainingTime() > 0) {
                System.out.println(p3.getRemainingTime());
                while (p3.getRemainingTime() > 0) {
                    this.ganttChartText.add(String.valueOf(p3.getProcessID()) + "(" + arrivalTime + ")");
                    this.cpuStates.add(p3.getProcessID());
                    p3.setRemainingTime(p3.getRemainingTime() - 1);
                    this.remainingBurstTimeList.add(p3.getRemainingTime());
                    this.remainingBurstTimePositionList.add(p3.getNo());
                    final List<Integer> waiting2 = new ArrayList<Integer>();
                    final List<Integer> position2 = new ArrayList<Integer>();
                    waiting2.add(p3.getWaitingTime());
                    position2.add(p3.getNo());
                    this.waitingTimeList.add(waiting2);
                    this.waitingTimePositionList.add(position2);
                    final int progress2 = p3.getBurstTime() - p3.getRemainingTime();
                    this.valueProgressList.add(progress2);
                    this.processProgressList.add(p3);
                    this.readyQueueList.add("");
                    ++arrivalTime;
                    System.out.println(String.valueOf(p3.getProcessID()) + " " + p3.getRemainingTime());
                }
            }
        }
        System.out.println();
        System.out.println(this.ganttChartText);
    }
    
    private boolean contains(final List<Process> list, final String ID) {
        for (final Process p : list) {
            if (p.getProcessID().equals(ID)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkAllRemainingTimeIsZero() {
        for (final Process p : this.processes) {
            if (p.getRemainingTime() > 0) {
                return false;
            }
        }
        return true;
    }
}