/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class SolveFCFS
{
    protected List<Process> processes;
    protected List<String> ganttChartText;
    protected List<String> cpuStates;
    protected List<Integer> valueProgressList;
    protected List<Process> processProgressList;
    protected List<String> readyQueueList;
    protected List<Process> queue;
    protected List<List<Integer>> waitingTimeList;
    protected List<List<Integer>> waitingTimePositionList;
    protected List<Integer> remainingBurstTimeList;
    protected List<Integer> remainingBurstTimePositionList;
    private static final int ROUND_ROBIN = 3;
    
    public SolveFCFS(final List<Process> processes, final int algorithm) {
        this.processes = processes;
        if (!this.checkAllATIsTheSame()) {
            Collections.sort(this.processes, new FCFSCompare());
            for (final Process p : this.processes) {
                if (p.getArrivalTime() == 0) {
                    p.setWaitingTime(0);
                }
            }
        }
        else {
            for (final Process p : this.processes) {
                p.setWaitingTime(0);
            }
        }
        for (final Process p : this.processes) {
            System.out.println(p);
        }
        this.ganttChartText = new ArrayList<String>();
        this.cpuStates = new ArrayList<String>();
        this.valueProgressList = new ArrayList<Integer>();
        this.processProgressList = new ArrayList<Process>();
        this.readyQueueList = new ArrayList<String>();
        this.queue = new ArrayList<Process>();
        this.remainingBurstTimePositionList = new ArrayList<Integer>();
        this.remainingBurstTimeList = new ArrayList<Integer>();
        this.waitingTimeList = new ArrayList<List<Integer>>();
        this.waitingTimePositionList = new ArrayList<List<Integer>>();
    }
    
    public List<String> getGanttChart() {
        return this.ganttChartText;
    }
    
    public List<String> getCPUStates() {
        return this.cpuStates;
    }
    
    public List<Process> getProcesses() {
        return this.processes;
    }
    
    public List<Integer> getValueProgressList() {
        return this.valueProgressList;
    }
    
    public List<Process> getProcessProgressList() {
        return this.processProgressList;
    }
    
    public List<String> getReadyQueueList() {
        return this.readyQueueList;
    }
    
    public List<Integer> getRemainingBurstTimeList() {
        return this.remainingBurstTimeList;
    }
    
    public List<Integer> getRemainingBurstTimePositionList() {
        return this.remainingBurstTimePositionList;
    }
    
    public List<List<Integer>> getWaitingTimePositionList() {
        return this.waitingTimePositionList;
    }
    
    public List<List<Integer>> getWaitingTimeList() {
        return this.waitingTimeList;
    }
    
    public void solve() {
        int arrivalTime = this.processes.get(0).getArrivalTime();
        int j = 0;
        final int max = this.processes.get(this.processes.size() - 1).getArrivalTime();
        if (this.processes.size() > 1) {
            for (int count = 1; count < this.processes.size(); ++count) {
                if (this.processes.get(count).getArrivalTime() == this.processes.get(0).getArrivalTime()) {
                    this.processes.get(count).setWaitingTime(0);
                }
            }
        }
        for (int count = 0; count < this.processes.size(); ++count) {
            final int finishTime = this.processes.get(count).getBurstTime();
            int i = 0;
            if (!this.queue.isEmpty()) {
                this.queue.remove(0);
            }
            for (int count2 = arrivalTime; count2 < arrivalTime + finishTime; ++count2) {
                this.ganttChartText.add(String.valueOf(this.processes.get(count).getProcessID()) + "(" + count2 + ")");
                this.cpuStates.add(this.processes.get(count).getProcessID());
                ++i;
                this.remainingBurstTimeList.add(this.processes.get(count).getBurstTime() - i);
                this.remainingBurstTimePositionList.add(this.processes.get(count).getNo());
                this.valueProgressList.add(i);
                this.processProgressList.add(this.processes.get(count));
                if (j <= max) {
                    ++j;
                    for (int count3 = count + 1; count3 < this.processes.size(); ++count3) {
                        if (!this.contains(this.processes.get(count3).getProcessID()) && this.processes.get(count3).getArrivalTime() <= count2 + 1) {
                            this.queue.add(this.processes.get(count3));
                        }
                    }
                }
                if (this.queue.size() == 0) {
                    this.waitingTimeList.add(new ArrayList<Integer>(0));
                    this.waitingTimePositionList.add(new ArrayList<Integer>(0));
                }
                else {
                    for (int count4 = 0; count4 < this.queue.size(); ++count4) {
                        this.queue.get(count4).setWaitingTime(this.queue.get(count4).getWaitingTime() + 1);
                    }
                    final List<Integer> position = new ArrayList<Integer>(1);
                    final List<Integer> waitingT = new ArrayList<Integer>(1);
                    for (int count5 = 0; count5 < this.queue.size(); ++count5) {
                        position.add(this.queue.get(count5).getNo());
                        waitingT.add(this.queue.get(count5).getWaitingTime());
                    }
                    this.waitingTimeList.add(waitingT);
                    this.waitingTimePositionList.add(position);
                }
                String readyQueue = "";
                for (final Process p : this.queue) {
                    readyQueue = String.valueOf(readyQueue) + p.getProcessID() + " ";
                }
                this.readyQueueList.add(readyQueue);
            }
            arrivalTime += finishTime;
        }
    }
    
    private boolean contains(final String s) {
        for (final Process p : this.queue) {
            if (p.getProcessID().equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean checkAllATIsTheSame() {
        final int t = this.processes.get(0).getArrivalTime();
        for (final Process p : this.processes) {
            if (p.getArrivalTime() != t) {
                return false;
            }
        }
        return true;
    }
}