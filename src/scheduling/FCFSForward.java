/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.util.Iterator;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class FCFSForward extends FCFSWorker
{
    protected int count;
    protected JButton forwardButton;
    
    public FCFSForward(final List<Process> processes, final JTextField ganttChart, final List<String> output, final JTextField cpuState, final List<String> cpuStates, final JButton again, final JLabel totalExecutionTime, final JProgressBar[] progressBars, final List<Integer> valueProgressList, final List<Process> processProgressList, final List<String> readyQueueList, final JTextField readyQueueTextField, final JLabel[] remainingBurstTimeLabel, final List<Integer> remainingBurstTimeList, final List<Integer> remainingBurstTimePositionList, final List<List<Integer>> waitingTimePositionList, final List<List<Integer>> waitingTimeList, final JLabel[] waitingTimeProcessLabel, final JLabel averageWaitingTimeLabel, final JLabel averageTurnaroundTimeLabel, final JButton pauseButton, final JButton forwardButton, final int language) {
        super((List)processes, ganttChart, (List)output, cpuState, (List)cpuStates, again, totalExecutionTime, progressBars, (List)valueProgressList, (List)processProgressList, (List)readyQueueList, readyQueueTextField, remainingBurstTimeLabel, (List)remainingBurstTimeList, (List)remainingBurstTimePositionList, (List)waitingTimePositionList, (List)waitingTimeList, waitingTimeProcessLabel, averageWaitingTimeLabel, averageTurnaroundTimeLabel, pauseButton, 0, language);
        this.count = 0;
        this.forwardButton = forwardButton;
        for (final Process i : processProgressList) {
            System.out.print(String.valueOf(i.getProcessID()) + " ");
        }
        System.out.println();
        for (final Integer j : this.valueProgressList) {
            System.out.print(j + " ");
        }
    }
    
    public void forward() {
        if (this.count < this.outputStrings.size()) {
            //this.publish((Object[])new String[] { this.outputStrings.get(this.count) });
            this.cpuStateTextField.setText(" " + this.cpuStateStrings.get(this.count));
            this.readyQueueTextField.setText(this.readyQueueList.get(this.count));
            this.totalExecutionTimeLabel.setText(String.format("%d", this.count + 1));
            this.remainingBurstTimeLabel[this.remainingBurstTimePositionList.get(this.count)].setText(String.format("%d", this.remainingBurstTimeList.get(this.count)));
            final int progressNumber = this.processProgressList.get(this.count).getNo();
            final int progressValue = this.valueProgressList.get(this.count);
            if (this.count == 0 && progressValue == 1) {
                this.progressBars[progressNumber].setValue(1);
            }
            else {
                this.progressBars[this.processProgressList.get(this.count).getNo()].setValue(this.valueProgressList.get(this.count));
            }
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
            ++this.count;
        }
        else {
            this.againButton.setEnabled(true);
            this.forwardButton.setEnabled(false);
            this.cpuStateTextField.setText("Idle");
            if (this.language == 0) {
                JOptionPane.showMessageDialog(null, "Done!", "Notice", -1);
            }
            else if (this.language == 1) {
                JOptionPane.showMessageDialog(null, "Ho\u00e0n t\u1ea5t!", "Ch\u00fa \u00fd", -1);
            }
        }
    }
}