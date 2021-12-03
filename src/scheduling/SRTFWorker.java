/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class SRTFWorker extends SJFWorker
{
    public SRTFWorker(final List<Process> processes, final JTextField ganttChart, final List<String> output, final JTextField cpuState, final List<String> cpuStates, final JButton again, final JLabel totalExecutionTime, final JProgressBar[] progressBars, final List<Integer> valueProgressList, final List<Process> processProgressList, final List<String> readyQueueList, final JTextField readyQueueTextField, final JLabel[] remainingBurstTimeLabel, final List<Integer> remainingBurstTimeList, final List<Integer> remainingBurstTimePositionList, final List<List<Integer>> waitingTimePositionList, final List<List<Integer>> waitingTimeList, final JLabel[] waitingTimeProcessLabel, final JLabel averageWaitingTimeLabel, final JLabel averageTurnaroundTimeLabel, final JButton pauseButton, final int sleepTime, final int language) {
        super(processes, ganttChart, output, cpuState, cpuStates, again, totalExecutionTime, progressBars, valueProgressList, processProgressList, readyQueueList, readyQueueTextField, remainingBurstTimeLabel, remainingBurstTimeList, remainingBurstTimePositionList, waitingTimePositionList, waitingTimeList, waitingTimeProcessLabel, averageWaitingTimeLabel, averageTurnaroundTimeLabel, pauseButton, sleepTime, language);
        System.out.println("size: " + this.outputStrings.size());
    }
    
    @Override
    public Object doInBackground() {
        int count = 0;
        while (count < this.outputStrings.size()) {
            if (!this.isPaused()) {
                try {
                    Thread.sleep(this.sleepTime);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println(this.outputStrings.get(count));
                this.ganttChartTextField.setText(String.valueOf(this.ganttChartTextField.getText()) + this.outputStrings.get(count) + " ");
                this.cpuStateTextField.setText(this.cpuStateStrings.get(count));
                this.readyQueueTextField.setText(this.readyQueueList.get(count));
                this.progressBars[this.processProgressList.get(count).getNo()].setValue(this.valueProgressList.get(count));
                this.remainingBurstTimeLabel[this.remainingBurstTimePositionList.get(count)].setText(String.format("%d", this.remainingBurstTimeList.get(count)));
                final List<Integer> waitingT = this.waitingTimeList.get(count);
                final List<Integer> position = this.waitingTimePositionList.get(count);
                System.out.println(String.valueOf(waitingT.size()) + " " + position.size());
                if (waitingT.size() != 0 && position.size() != 0) {
                    for (int count2 = 0; count2 < waitingT.size(); ++count2) {
                        final int wt = waitingT.get(count2);
                        final int pos = position.get(count2);
                        this.waitingTimeProcessLabel[pos].setText(String.format("%d", wt));
                        System.out.println(this.waitingTimeProcessLabel[pos].getText());
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
                this.totalExecutionTimeLabel.setText(String.format("%d", count + 1));
                ++count;
            }
            else {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}