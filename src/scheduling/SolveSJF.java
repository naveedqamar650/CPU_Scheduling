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

public class SolveSJF extends SolveFCFS
{
    protected List<Process> resultList;
    protected Process p1;
    protected List<Process> queue;
    
    public SolveSJF(final List<Process> processes) {
        super(processes, 2);
        if (!this.checkAllATIsTheSame()) {
            Collections.sort(this.processes, new CompareProcess());
        }
        else {
            Collections.sort(this.processes, new SJFComparetor());
        }
        this.p1 = this.processes.get(0);
        System.out.println("P1 : \n" + this.p1);
        (this.resultList = new ArrayList<Process>()).add(this.p1);
        this.queue = new ArrayList<Process>();
        if (this.checkAllATIsTheSame()) {
            this.queue.add(this.p1);
        }
    }
    
    @Override
    public void solve() {
        if (this.processes.size() > 1) {
            for (int count = 1; count < this.processes.size(); ++count) {
                if (this.processes.get(count).getArrivalTime() == this.p1.getArrivalTime()) {
                    this.processes.get(count).setWaitingTime(0);
                }
            }
        }
        if (!this.checkAllATIsTheSame()) {
            int max = this.p1.getArrivalTime() + this.p1.getBurstTime();
            while (this.resultList.size() < this.processes.size()) {
                final List<Process> temp = new ArrayList<Process>();
                for (int count2 = 0; count2 < this.processes.size(); ++count2) {
                    if (this.processes.get(count2).getArrivalTime() <= max && !this.contains(this.resultList, this.processes.get(count2).getProcessID())) {
                        temp.add(this.processes.get(count2));
                    }
                }
                Collections.sort(temp, new SJFComparetor());
                if (temp.size() != 0) {
                    this.resultList.add(temp.get(0));
                }
                max += this.resultList.get(this.resultList.size() - 1).getBurstTime();
            }
        }
        else {
            for (int count = 0; count < this.processes.size(); ++count) {
                if (!this.contains(this.resultList, this.processes.get(count).getProcessID())) {
                    this.resultList.add(this.processes.get(count));
                    this.queue.add(this.processes.get(count));
                }
            }
        }
        final int bound = this.processes.get(this.processes.size() - 1).getArrivalTime();
        int arrivalTime = this.processes.get(0).getArrivalTime();
        int j = 0;
        for (int count3 = 0; count3 < this.resultList.size(); ++count3) {
            final int finishTime = this.resultList.get(count3).getBurstTime();
            int i = 0;
            if (!this.queue.isEmpty()) {
                this.queue.remove(0);
            }
            for (int count4 = arrivalTime; count4 < arrivalTime + finishTime; ++count4) {
                this.ganttChartText.add(String.valueOf(this.resultList.get(count3).getProcessID()) + "(" + count4 + ")");
                this.cpuStates.add(this.resultList.get(count3).getProcessID());
                ++i;
                this.remainingBurstTimeList.add(this.resultList.get(count3).getBurstTime() - i);
                this.remainingBurstTimePositionList.add(this.resultList.get(count3).getNo());
                this.valueProgressList.add(i);
                this.processProgressList.add(this.resultList.get(count3));
                if (!this.checkAllATIsTheSame() && j <= bound) {
                    ++j;
                    for (int count5 = count3 + 1; count5 < this.resultList.size(); ++count5) {
                        if (!this.contains(this.resultList.get(count5).getProcessID()) && this.resultList.get(count5).getArrivalTime() <= count4 + 1) {
                            this.queue.add(this.resultList.get(count5));
                        }
                    }
                }
                if (this.queue.size() == 0) {
                    this.waitingTimeList.add(new ArrayList<Integer>(0));
                    this.waitingTimePositionList.add(new ArrayList<Integer>(0));
                }
                else {
                    for (int count6 = 0; count6 < this.queue.size(); ++count6) {
                        this.queue.get(count6).setWaitingTime(this.queue.get(count6).getWaitingTime() + 1);
                    }
                    Collections.sort(this.queue, new SJFComparetor());
                    final List<Integer> position = new ArrayList<Integer>(1);
                    final List<Integer> waitingT = new ArrayList<Integer>(1);
                    for (int count7 = 0; count7 < this.queue.size(); ++count7) {
                        position.add(this.queue.get(count7).getNo());
                        if (this.queue.get(count7).getWaitingTime() == -1) {
                            waitingT.add(0);
                        }
                        else {
                            waitingT.add(this.queue.get(count7).getWaitingTime());
                        }
                    }
                    this.waitingTimeList.add(waitingT);
                    this.waitingTimePositionList.add(position);
                }
                String readyQueue = "";
                for (final Process p : this.queue) {
                    readyQueue = String.valueOf(readyQueue) + p.getProcessID() + " ";
                }
                System.out.println(readyQueue);
                this.readyQueueList.add(readyQueue);
            }
            arrivalTime += finishTime;
        }
        System.out.println(this.readyQueueList.size());
    }
    
    protected boolean contains(final List<Process> processes, final String ID) {
        for (int count = 0; count < processes.size(); ++count) {
            if (processes.get(count).getProcessID().equals(ID)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean contains(final String s) {
        for (final Process p : this.queue) {
            if (p.getProcessID().equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    public List<Process> getResult() {
        return this.resultList;
    }
}