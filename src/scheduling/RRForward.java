/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JSlider;

// 
// Decompiled by Procyon v0.5.36
// 

public class RRForward extends FCFSForward
{
    private JSlider quantiumSlider;
    private int count;
    
    public RRForward(final List<Process> processes, final JTextField ganttChart, final List<String> output, final JTextField cpuState, final List<String> cpuStates, final JButton again, final JLabel totalExecutionTime, final JProgressBar[] progressBars, final List<Integer> valueProgressList, final List<Process> processProgressList, final List<String> readyQueueList, final JTextField readyQueueTextField, final JLabel[] remainingBurstTimeLabel, final List<Integer> remainingBurstTimeList, final List<Integer> remainingBurstTimePositionList, final List<List<Integer>> waitingTimePositionList, final List<List<Integer>> waitingTimeList, final JLabel[] waitingTimeProcessLabel, final JLabel averageWaitingTimeLabel, final JLabel averageTurnaroundTimeLabel, final JButton pauseButton, final JButton forwardButton, final JSlider quantiumSlider, final int language) {
        super(processes, ganttChart, output, cpuState, cpuStates, again, totalExecutionTime, progressBars, valueProgressList, processProgressList, readyQueueList, readyQueueTextField, remainingBurstTimeLabel, remainingBurstTimeList, remainingBurstTimePositionList, waitingTimePositionList, waitingTimeList, waitingTimeProcessLabel, averageWaitingTimeLabel, averageTurnaroundTimeLabel, pauseButton, forwardButton, language);
        this.count = 0;
        this.quantiumSlider = quantiumSlider;
    }
    
    @Override
    public void forward() {
        if (this.count < this.outputStrings.size()) {
            this.ganttChartTextField.setText(String.valueOf(this.ganttChartTextField.getText()) + " " + this.outputStrings.get(this.count));
            System.out.println(String.valueOf(this.cpuStateTextField.getText()) + " " + this.count);
            this.cpuStateTextField.setText(" " + this.cpuStateStrings.get(this.count));
            System.out.println(String.valueOf(this.processProgressList.get(this.count).getNo()) + " - " + this.valueProgressList.get(this.count));
            this.progressBars[this.processProgressList.get(this.count).getNo()].setValue(this.valueProgressList.get(this.count));
            this.remainingBurstTimeLabel[this.remainingBurstTimePositionList.get(this.count)].setText(String.format("%d", this.remainingBurstTimeList.get(this.count)));
            final List<Integer> waitingT = this.waitingTimeList.get(this.count);
            final List<Integer> position = this.waitingTimePositionList.get(this.count);
            if (waitingT.size() != 0 && position.size() != 0) {
                for (int count1 = 0; count1 < waitingT.size(); ++count1) {
                    final int wt = waitingT.get(count1);
                    final int pos = position.get(count1);
                    this.waitingTimeProcessLabel[pos].setText(String.format("%d", wt));
                }
            }
            int sum = 0;
            for (int count2 = 0; count2 < this.waitingTimeProcessLabel.length; ++count2) {
                sum += Integer.parseInt(this.waitingTimeProcessLabel[count2].getText());
            }
            int btSum = 0;
            for (int count3 = 0; count3 < this.processes.size(); ++count3) {
                btSum += this.processes.get(count3).getBurstTime();
            }
            this.averageWaitingTimeLabel.setText(String.format("%.2f", sum / (float)this.processes.size()));
            this.averageTurnaroundTimeLabel.setText(String.format("%.2f", (btSum + sum) / (float)this.processes.size()));
            this.totalExecutionTimeLabel.setText(String.format("%d", this.count + 1));
            this.readyQueueTextField.setText(this.readyQueueList.get(this.count));
            ++this.count;
        }
        else {
            this.againButton.setEnabled(true);
            this.forwardButton.setEnabled(false);
            this.cpuStateTextField.setText("Idle");
            this.quantiumSlider.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Done!", "Notice", -1);
        }
    }
}