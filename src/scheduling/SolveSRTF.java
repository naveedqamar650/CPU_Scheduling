/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class SolveSRTF extends SolveSJF
{
    private List<Process> seenProcesses;
    private List<Process> readyQueue;
    
    public SolveSRTF(final List<Process> processes) {
        super(processes);
        this.processes.get(0).setWaitingTime(0);
    }
    
    @Override
    public void solve() {
        final int start = this.processes.get(0).getArrivalTime();
        if (this.checkAllATIsTheSame()) {
            for (int count = 0; count < this.processes.size(); ++count) {
                if (!this.contains(this.resultList, this.processes.get(count).getProcessID())) {
                    this.resultList.add(this.processes.get(count));
                    this.queue.add(this.processes.get(count));
                }
            }
            final int bound = this.processes.get(this.processes.size() - 1).getArrivalTime();
            int arrivalTime = this.processes.get(0).getArrivalTime();
            int j = 0;
            for (int count2 = 0; count2 < this.resultList.size(); ++count2) {
                final int finishTime = this.resultList.get(count2).getBurstTime();
                int i = 0;
                if (!this.queue.isEmpty()) {
                    this.queue.remove(0);
                }
                for (int count3 = arrivalTime; count3 < arrivalTime + finishTime; ++count3) {
                    this.ganttChartText.add(String.valueOf(this.resultList.get(count2).getProcessID()) + "(" + count3 + ")");
                    this.cpuStates.add(this.resultList.get(count2).getProcessID());
                    ++i;
                    this.remainingBurstTimeList.add(this.resultList.get(count2).getBurstTime() - i);
                    this.remainingBurstTimePositionList.add(this.resultList.get(count2).getNo());
                    this.valueProgressList.add(i);
                    this.processProgressList.add(this.resultList.get(count2));
                    if (!this.checkAllATIsTheSame() && j <= bound) {
                        ++j;
                        for (int count4 = count2 + 1; count4 < this.resultList.size(); ++count4) {
                            if (!this.contains(this.resultList.get(count4).getProcessID()) && this.resultList.get(count4).getArrivalTime() <= count3 + 1) {
                                this.queue.add(this.resultList.get(count4));
                            }
                        }
                    }
                    if (this.queue.size() == 0) {
                        this.waitingTimeList.add(new ArrayList<Integer>(0));
                        this.waitingTimePositionList.add(new ArrayList<Integer>(0));
                    }
                    else {
                        for (int count5 = 0; count5 < this.queue.size(); ++count5) {
                            this.queue.get(count5).setWaitingTime(this.queue.get(count5).getWaitingTime() + 1);
                        }
                        Collections.sort(this.queue, new SJFComparetor());
                        final List<Integer> position = new ArrayList<Integer>(1);
                        final List<Integer> waitingT = new ArrayList<Integer>(1);
                        for (int count6 = 0; count6 < this.queue.size(); ++count6) {
                            position.add(this.queue.get(count6).getNo());
                            if (this.queue.get(count6).getWaitingTime() == -1) {
                                waitingT.add(0);
                            }
                            else {
                                waitingT.add(this.queue.get(count6).getWaitingTime());
                            }
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
        else {
            final List<Process> re = new ArrayList<Process>();
            final List<Process> avai = new ArrayList<Process>();
            final List<Process> seen = new ArrayList<Process>();
            avai.add(this.processes.get(0));
            seen.add(this.processes.get(0));
            Process runP = this.processes.get(0);
            if (this.processes.size() > 1) {
                for (int count7 = 1; count7 < this.processes.size(); ++count7) {
                    if (this.processes.get(count7).getArrivalTime() == runP.getArrivalTime()) {
                        this.processes.get(count7).setWaitingTime(0);
                    }
                }
            }
            int at = runP.getArrivalTime();
            while (seen.size() < this.processes.size()) {
                final int max = at + runP.getBurstTime();
                int count8 = 0;
                for (int c = at; c < max; ++c) {
                    boolean shouldBreak = false;
                    runP.setRemainingTime(runP.getRemainingTime() - 1);
                    this.remainingBurstTimeList.add(runP.getRemainingTime());
                    this.remainingBurstTimePositionList.add(runP.getNo());
                    this.cpuStates.add(runP.getProcessID());
                    final Process temp = new Process(runP.getProcessID(), runP.getArrivalTime(), runP.getBurstTime(), runP.getNo());
                    temp.setRemainingTime(runP.getRemainingTime());
                    re.add(temp);
                    ++at;
                    ++count8;
                    for (final Process p2 : this.processes) {
                        if (p2.getArrivalTime() <= c + 1 && !this.contains(avai, p2.getProcessID())) {
                            avai.add(p2);
                            seen.add(p2);
                        }
                    }
                    for (final Process p2 : this.processes) {
                        if (!p2.getProcessID().equals(runP.getProcessID()) && p2.getArrivalTime() <= c + 1 && p2.getRemainingTime() > 0) {
                            p2.setWaitingTime(p2.getWaitingTime() + 1);
                        }
                    }
                    final List<Integer> waiting = new ArrayList<Integer>();
                    final List<Integer> position2 = new ArrayList<Integer>();
                    for (final Process p3 : avai) {
                        waiting.add(p3.getWaitingTime());
                        position2.add(p3.getNo());
                    }
                    this.waitingTimeList.add(waiting);
                    this.waitingTimePositionList.add(position2);
                    final int value = runP.getBurstTime() - runP.getRemainingTime();
                    this.valueProgressList.add(value);
                    this.processProgressList.add(runP);
                    final List<Process> tempL = new ArrayList<Process>();
                    for (final Process p4 : avai) {
                        if (p4.getRemainingTime() > 0) {
                            tempL.add(p4);
                        }
                    }
                    Collections.sort(tempL, (Comparator<? super Process>)new SRTFComparetor());
                    String s = "";
                    for (final Process p5 : tempL) {
                        if (p5.getRemainingTime() > 0 && !p5.getProcessID().equals(runP.getProcessID())) {
                            s = String.valueOf(s) + p5.getProcessID() + " ";
                            System.out.print(String.valueOf(p5.getProcessID()) + "-" + p5.getRemainingTime() + "-" + p5.getArrivalTime() + ",  ");
                        }
                    }
                    this.readyQueueList.add(s);
                    System.out.println();
                    System.out.println(String.valueOf(runP.getProcessID()) + " -- " + runP.getRemainingTime() + " c : " + c + " max: " + max);
                    if (runP.getRemainingTime() == 0) {
                        runP = this.getSTProcess(avai);
                    }
                    else {
                        for (final Process p5 : avai) {
                            if (p5.getRemainingTime() < runP.getRemainingTime() && p5.getRemainingTime() > 0) {
                                runP = p5;
                            }
                        }
                        shouldBreak = true;
                    }
                    if (shouldBreak) {
                        break;
                    }
                }
            }
            System.out.println("At: " + at);
            while (!this.checkAllRemainingTimeIsZero()) {
                while (runP.getRemainingTime() > 0) {
                    runP.setRemainingTime(runP.getRemainingTime() - 1);
                    this.remainingBurstTimeList.add(runP.getRemainingTime());
                    this.remainingBurstTimePositionList.add(runP.getNo());
                    final int value2 = runP.getBurstTime() - runP.getRemainingTime();
                    this.valueProgressList.add(value2);
                    this.processProgressList.add(runP);
                    this.cpuStates.add(runP.getProcessID());
                    final Process temp2 = new Process(runP.getProcessID(), runP.getArrivalTime(), runP.getBurstTime(), runP.getNo());
                    temp2.setRemainingTime(runP.getRemainingTime());
                    re.add(temp2);
                    for (final Process p6 : this.processes) {
                        if (!p6.getProcessID().equals(runP.getProcessID()) && p6.getArrivalTime() <= at + 1 && p6.getRemainingTime() > 0) {
                            p6.setWaitingTime(p6.getWaitingTime() + 1);
                        }
                    }
                    ++at;
                    final List<Integer> waiting2 = new ArrayList<Integer>();
                    final List<Integer> position3 = new ArrayList<Integer>();
                    for (final Process p7 : avai) {
                        waiting2.add(p7.getWaitingTime());
                        position3.add(p7.getNo());
                    }
                    this.waitingTimeList.add(waiting2);
                    this.waitingTimePositionList.add(position3);
                    final List<Process> tempL2 = new ArrayList<Process>();
                    for (final Process p2 : avai) {
                        if (p2.getRemainingTime() > 0) {
                            tempL2.add(p2);
                        }
                    }
                    Collections.sort(tempL2, (Comparator<? super Process>)new SRTFComparetor());
                    String s2 = "";
                    for (final Process p8 : tempL2) {
                        if (p8.getRemainingTime() > 0 && !p8.getProcessID().equals(runP.getProcessID())) {
                            s2 = String.valueOf(s2) + p8.getProcessID() + " ";
                            System.out.print(String.valueOf(p8.getProcessID()) + "-" + p8.getRemainingTime() + ", ");
                        }
                    }
                    System.out.println();
                    this.readyQueueList.add(s2);
                    System.out.println(String.valueOf(runP.getProcessID()) + " -- " + runP.getRemainingTime() + " c : ");
                }
                if (this.checkAllRemainingTimeIsZero()) {
                    break;
                }
                runP = this.getSTProcess(avai);
            }
            for (int count9 = 0; count9 < re.size(); ++count9) {
                System.out.print(String.valueOf(re.get(count9).getProcessID()) + "(" + count9 + ")" + " ");
                this.ganttChartText.add(String.valueOf(re.get(count9).getProcessID()) + "(" + (count9 + start) + ")");
            }
            for (final String s3 : this.readyQueueList) {
                System.out.println(s3);
            }
            System.out.println(this.readyQueueList.size());
        }
    }
    
    private boolean checkAllRemainingTimeIsZero() {
        for (final Process p : this.processes) {
            if (p.getRemainingTime() > 0) {
                return false;
            }
        }
        return true;
    }
    
    private Process getSTProcess(final List<Process> list) {
        Process p = null;
        for (final Process pr : list) {
            if (pr.getRemainingTime() > 0) {
                p = pr;
                break;
            }
        }
        for (final Process pr : list) {
            if (pr.getRemainingTime() > 0 && pr.getRemainingTime() < p.getRemainingTime()) {
                p = pr;
            }
            else {
                if (pr.getRemainingTime() != p.getRemainingTime() || pr.getArrivalTime() >= p.getArrivalTime()) {
                    continue;
                }
                p = pr;
            }
        }
        return p;
    }
}