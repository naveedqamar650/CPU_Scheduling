/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class SolvePriority extends SolveSJF
{
    public SolvePriority(final List<Process> processes) {
        super((List)processes);
        if (this.checkAllATIsTheSame()) {
            Collections.sort((List<Process>)processes, (Comparator<? super Process>)new PriorityCompare());
        }
        else {
            Collections.sort((List<Process>)processes, (Comparator<? super Process>)new Comparator<Process>() {
                @Override
                public int compare(final Process p1, final Process p2) {
                    final int at = p1.getArrivalTime() - p2.getArrivalTime();
                    if (at != 0) {
                        return at;
                    }
                    return (p1.getPriority() - p2.getPriority()) * -1;
                }
            });
        }
        this.p1 = this.processes.get(0);
        System.out.println(String.valueOf(this.p1.getPriority()) + "pri " + (this.p1.getPriority() - processes.get(1).getPriority()));
        (this.resultList = new ArrayList()).add(this.p1);
        this.queue = new ArrayList();
    }
    
    public void solve() {
        int max = this.p1.getArrivalTime() + this.p1.getBurstTime();
        if (this.processes.size() > 1 && !this.checkAllATIsTheSame()) {
            for (int count = 1; count < this.processes.size(); ++count) {
                if (this.processes.get(count).getArrivalTime() == this.p1.getArrivalTime()) {
                    this.processes.get(count).setWaitingTime(0);
                }
            }
        }
        while (this.resultList.size() < this.processes.size()) {
            final List<Process> temp = new ArrayList<Process>();
            for (int count2 = 0; count2 < this.processes.size(); ++count2) {
                if (this.processes.get(count2).getArrivalTime() <= max && !this.contains(this.resultList, this.processes.get(count2).getProcessID())) {
                    temp.add(this.processes.get(count2));
                }
            }
            Collections.sort(temp, new PriorityCompare());
            if (temp.size() != 0) {
                this.resultList.add(temp.get(0));
            }
            max += this.resultList.get(this.resultList.size() - 1).getBurstTime();
        }
        for (final Process p : this.resultList) {
            System.out.println(p);
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
            System.out.println(this.resultList.get(count3) + ":");
            for (final Process P : this.queue) {
                System.out.println(P);
            }
            System.out.println("\n");
            for (int count4 = arrivalTime; count4 < arrivalTime + finishTime; ++count4) {
                this.ganttChartText.add(String.valueOf(this.resultList.get(count3).getProcessID()) + "(" + count4 + ")");
                this.cpuStates.add(this.resultList.get(count3).getProcessID());
                ++i;
                this.remainingBurstTimeList.add(this.resultList.get(count3).getBurstTime() - i);
                this.remainingBurstTimePositionList.add(this.resultList.get(count3).getNo());
                this.valueProgressList.add(i);
                this.processProgressList.add(this.resultList.get(count3));
                if (j <= bound) {
                    ++j;
                    for (int count5 = count3 + 1; count5 < this.resultList.size(); ++count5) {
                        if (!this.contains(this.resultList.get(count5).getProcessID()) && this.resultList.get(count5).getArrivalTime() <= count4 + 1) {
                            this.queue.add(this.resultList.get(count5));
                        }
                    }
                }
                if (this.queue.size() == 0) {
                    this.waitingTimeList.add(new ArrayList(0));
                    this.waitingTimePositionList.add(new ArrayList(0));
                }
                else {
                    for (int count6 = 0; count6 < this.queue.size(); ++count6) {
                        this.queue.get(count6).setWaitingTime(this.queue.get(count6).getWaitingTime() + 1);
                    }
                    Collections.sort((List<Process>)this.queue, (Comparator<? super Process>)new PriorityCompare());
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
                for (final Process p2 : this.queue) {
                    readyQueue = String.valueOf(readyQueue) + p2.getProcessID() + " ";
                }
                this.readyQueueList.add(readyQueue);
            }
            arrivalTime += finishTime;
        }
    }
}
