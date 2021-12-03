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

public class SRTFForward extends SJFForward
{
    public SRTFForward(final List<Process> processes, final JTextField ganttChart, final List<String> output, final JTextField cpuState, final List<String> cpuStates, final JButton again, final JLabel totalExecutionTime, final JProgressBar[] progressBars, final List<Integer> valueProgressList, final List<Process> processProgressList, final List<String> readyQueueList, final JTextField readyQueueTextField, final JLabel[] remainingBurstTimeLabel, final List<Integer> remainingBurstTimeList, final List<Integer> remainingBurstTimePositionList, final List<List<Integer>> waitingTimePositionList, final List<List<Integer>> waitingTimeList, final JLabel[] waitingTimeProcessLabel, final JLabel averageWaitingTimeLabel, final JLabel averageTurnaroundTimeLabel, final JButton pauseButton, final JButton forwardButton, final int language) {
        super(processes, ganttChart, output, cpuState, cpuStates, again, totalExecutionTime, progressBars, valueProgressList, processProgressList, readyQueueList, readyQueueTextField, remainingBurstTimeLabel, remainingBurstTimeList, remainingBurstTimePositionList, waitingTimePositionList, waitingTimeList, waitingTimeProcessLabel, averageWaitingTimeLabel, averageTurnaroundTimeLabel, pauseButton, forwardButton, language);
    }
}

