/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
public class Process
{
    private String processID;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int no;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime;
    
    public Process() {
        this.no = 0;
        this.waitingTime = -1;
    }
    
    public Process(final String ID, final int AT, final int BT, final int no) {
        this.no = 0;
        this.waitingTime = -1;
        this.processID = ID;
        this.arrivalTime = AT;
        this.burstTime = BT;
        this.remainingTime = this.burstTime;
        this.no = no;
    }
    
    public Process(final String ID, final int AT, final int BT, final int no, final int P) {
        this(ID, AT, BT, no);
        this.priority = P;
    }
    
    public String getProcessID() {
        return this.processID;
    }
    
    public int getArrivalTime() {
        return this.arrivalTime;
    }
    
    public int getBurstTime() {
        return this.burstTime;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public int getNo() {
        return this.no;
    }
    
    public int getWaitingTime() {
        return this.waitingTime;
    }
    
    public int getRemainingTime() {
        return this.remainingTime;
    }
    
    public void setProcessID(final String ID) {
        this.processID = ID;
    }
    
    public void setArrivalTime(final int time) {
        this.arrivalTime = time;
    }
    
    public void setBurstTime(final int time) {
        this.burstTime = time;
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    
    public void setNo(final int no) {
        this.no = no;
    }
    
    public void setWaitingTime(final int time) {
        this.waitingTime = time;
    }
    
    public void setRemainingTime(final int time) {
        this.remainingTime = time;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.processID) + "  " + this.arrivalTime + "  " + this.burstTime + "  " + this.priority + "  " + this.waitingTime + "  " + this.no;
    }
}
