/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.SwingWorker;

// 
// Decompiled by Procyon v0.5.36
// 

public class FCFSWorker extends SwingWorker<Object, String>
{
    protected List<Process> processes;
    protected JTextField ganttChartTextField;
    protected JTextField cpuStateTextField;
    protected List<String> outputStrings;
    protected List<String> cpuStateStrings;
    protected List<Integer> valueProgressList;
    protected List<Process> processProgressList;
    protected JTextField readyQueueTextField;
    protected List<String> readyQueueList;
    protected List<Integer> remainingBurstTimeList;
    protected List<Integer> remainingBurstTimePositionList;
    protected JLabel[] remainingBurstTimeLabel;
    protected List<List<Integer>> waitingTimePositionList;
    protected List<List<Integer>> waitingTimeList;
    protected JButton pauseButton;
    protected JLabel[] waitingTimeProcessLabel;
    protected JProgressBar[] progressBars;
    protected JLabel averageWaitingTimeLabel;
    protected JLabel averageTurnaroundTimeLabel;
    protected JLabel totalExecutionTimeLabel;
    protected JButton againButton;
    protected int sleepTime;
    protected boolean isPaused;
    protected int language;
    
    public FCFSWorker(final List<Process> processes, final JTextField ganttChart, final List<String> output, final JTextField cpuState, final List<String> cpuStates, final JButton again, final JLabel totalExecutionTime, final JProgressBar[] progressBars, final List<Integer> valueProgressList, final List<Process> processProgressList, final List<String> readyQueueList, final JTextField readyQueueTextField, final JLabel[] remainingBurstTimeLabel, final List<Integer> remainingBurstTimeList, final List<Integer> remainingBurstTimePositionList, final List<List<Integer>> waitingTimePositionList, final List<List<Integer>> waitingTimeList, final JLabel[] waitingTimeProcessLabel, final JLabel averageWaitingTimeLabel, final JLabel averageTurnaroundTimeLabel, final JButton pauseButton, final int sleepTime, final int lanuange) {
        this.sleepTime = 500;
        this.isPaused = false;
        this.language = 0;
        this.ganttChartTextField = ganttChart;
        this.cpuStateTextField = cpuState;
        this.outputStrings = output;
        this.cpuStateStrings = cpuStates;
        this.againButton = again;
        this.totalExecutionTimeLabel = totalExecutionTime;
        this.language = lanuange;
        this.progressBars = progressBars;
        this.valueProgressList = valueProgressList;
        this.processProgressList = processProgressList;
        this.readyQueueList = readyQueueList;
        this.readyQueueTextField = readyQueueTextField;
        this.remainingBurstTimeLabel = remainingBurstTimeLabel;
        this.remainingBurstTimeList = remainingBurstTimeList;
        this.remainingBurstTimePositionList = remainingBurstTimePositionList;
        this.waitingTimePositionList = waitingTimePositionList;
        this.waitingTimeList = waitingTimeList;
        this.waitingTimeProcessLabel = waitingTimeProcessLabel;
        this.averageWaitingTimeLabel = averageWaitingTimeLabel;
        this.processes = processes;
        this.pauseButton = pauseButton;
        this.averageTurnaroundTimeLabel = averageTurnaroundTimeLabel;
        this.sleepTime = sleepTime;
    }
    
    public void setSleepTime(final int time) {
        this.sleepTime = time;
    }
    
    public Object doInBackground() {
        int count = 0;
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while (count < this.outputStrings.size()) {
            if (!this.isPaused) {
                try {
                    Thread.sleep(this.sleepTime);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //((SwingWorker<T, String>).publish(outputStrings.get(count));
                this.cpuStateTextField.setText(" " + this.cpuStateStrings.get(count));
                this.readyQueueTextField.setText(this.readyQueueList.get(count));
                this.totalExecutionTimeLabel.setText(String.format("%d", count + 1));
                this.remainingBurstTimeLabel[remainingBurstTimePositionList.get(count)].setText(String.format("%d", remainingBurstTimeList.get(count)));
                final int progressNumber = this.processProgressList.get(count).getNo();
                final int progressValue = this.valueProgressList.get(count);
                System.out.println(String.valueOf(progressNumber) + " : " + progressValue);
                if (count == 0 && progressValue == 1) {
                    this.progressBars[progressNumber].setValue(1);
                }
                else {
                    this.progressBars[this.processProgressList.get(count).getNo()].setValue(this.valueProgressList.get(count));
                }
                final List<Integer> waitingT = this.waitingTimeList.get(count);
                final List<Integer> position = this.waitingTimePositionList.get(count);
                if (waitingT.size() != 0 && position.size() != 0) {
                    for (int count2 = 0; count2 < waitingT.size(); ++count2) {
                        final int wt = waitingT.get(count2);
                        final int pos = position.get(count2);
                        this.waitingTimeProcessLabel[pos].setText(String.format("%d", wt));
                    }
                }
                int sum = 0;
                for (int count3 = 0; count3 < this.waitingTimeProcessLabel.length; ++count3) {
                    sum += Integer.parseInt(this.waitingTimeProcessLabel[count3].getText());
                }
                int btSum = 0;
                for (int count4 = 0; count4 < this.processes.size(); ++count4) {
                    btSum += this.processes.get(count4).getBurstTime();
                }
                this.averageWaitingTimeLabel.setText(String.format("%.2f", sum / (float)this.processes.size()));
                this.averageTurnaroundTimeLabel.setText(String.format("%.2f", (btSum + sum) / (float)this.processes.size()));
                ++count;
            }
            else {
                try {
                    Thread.sleep(this.sleepTime);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
    
    @Override
    protected void process(final List<String> list) {
        for (int count = 0; count < list.size(); ++count) {
            this.ganttChartTextField.setText(String.valueOf(this.ganttChartTextField.getText()) + " " + list.get(count));
        }
    }
    
    public void done() {
        this.cpuStateTextField.setText(" Idle");
        this.againButton.setEnabled(true);
        this.pauseButton.setEnabled(false);
        System.out.println("Language: " + this.language);
            JOptionPane.showMessageDialog(null, "Done!", "Notice", -1);
    }
    
    public boolean isPaused() {
        return this.isPaused;
    }
    
    public void pause() {
        if (!this.isPaused && !this.isDone()) {
            this.isPaused = true;
            this.firePropertyChange("paused", false, true);
        }
    }
    
    public void resume() {
        if (this.isPaused && !this.isDone()) {
            this.isPaused = false;
            this.firePropertyChange("paused", true, false);
        }
    }
}