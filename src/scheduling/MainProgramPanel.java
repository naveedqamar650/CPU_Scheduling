/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import javax.swing.event.ChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.UIManager;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class MainProgramPanel extends JFrame
{
    private Background backgroundPanel;
    private JSlider simulationSpeedSlider;
    private JSlider quantiumTimeSlider;
    private JTextField speedTextField;
    private JTextField quantiumTimeTextField;
    private JTextField cpuStateTextField;
    private JTextField algorithmTextField;
    private JPanel tablePanel;
    private JTable table;
    private JTextField readyQueueTextField;
    private JButton simulateButton;
    private JButton forwardButton;
    private JButton pauseButton;
    private JButton exitButton;
    private JButton againButton;
    private JButton backButton;
    private JProgressBar[] progressBars;
    private JLabel[] waitingTimeOfProcessesLabel;
    private JLabel[] processNumberLabel;
    private JLabel[] remainingBurstTimeLabel;
    private JLabel[] processesIDLabel;
    private JLabel averageWaitingTimeLabel;
    private JLabel averageTurnaroundTimeLabel;
    private JLabel totalExecutionTimeLabel;
    private JTextField ganttChartTextField;
    private JButton printToFileButton;
    private int algorithm;
    private int language;
    private int speed;
    private int quantiumTime;
    private double simulationSpeed;
    private List<Process> processes;
    private List<Process> copyProcesses;
    private Object[][] data;
    private String[] columnNames;
    private String[] algorithmNames;
    private boolean isPaused;
    private static final int FCFS = 0;
    private static final int SJF = 1;
    private static final int SRTF = 2;
    private static final int ROUND_ROBIN = 3;
    private static final int PRIORITY = 4;
    private static final int PRIORITY_PREEMPTIVE = 5;
    private static final int IMPORT_INPUT_PANEL = 10;
    private static final int FROM_FILE_INPUT_PANEL = 11;
    private static final int RANDOM_INPUT_PANEL = 12;
    private int previousPanel;
    private boolean firstClickOnForward;
    private FCFSWorker fcfsWorker;
    private FCFSForward fcfsForward;
    private SJFWorker sjfWorker;
    private SJFForward sjfForward;
    private PriorityWorker priorityWorker;
    private PriorityForward priorityForward;
    private RRWorker rrWorker;
    private RRForward rrForward;
    private SRTFWorker srtfWorker;
    private SRTFForward srtfForward;
    
    public MainProgramPanel(final int algorithm, final int language, final List<Process> processes, final int previousPanel) {
        this.data = new Object[10][4];
        this.columnNames = new String[] { "Process ID", "Arrival Time", "Burst Time", "Priority" };
        this.algorithmNames = new String[] { "FCFS", "SJF", "SRFT", "Round Robin", "Priority", "Priority Preemptive" };
        this.isPaused = false;
        this.firstClickOnForward = true;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.algorithm = algorithm;
        this.language = language;
        this.processes = processes;
        this.copyProcesses = new ArrayList<Process>();
        for (final Process p : processes) {
            this.copyProcesses.add(p);
        }
        this.previousPanel = previousPanel;
        this.setLayout(null);
        (this.simulationSpeedSlider = new JSlider(0)).setPaintTicks(false);
        this.simulationSpeedSlider.setMajorTickSpacing(10);
        this.simulationSpeedSlider.setMaximum(500);
        this.simulationSpeedSlider.setMinimum(10);
        this.simulationSpeedSlider.setSize(150, 30);
        this.simulationSpeedSlider.setLocation(155, 30);
        this.simulationSpeedSlider.setOpaque(false);
        this.simulationSpeedSlider.setValue(10);
        this.simulationSpeedSlider.setFocusable(false);
        this.add(this.simulationSpeedSlider);
        (this.speedTextField = new JTextField()).setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.speedTextField.setEnabled(false);
        this.speedTextField.setFont(new Font("Sansserif", 1, 15));
        this.speedTextField.setSize(40, 25);
        this.speedTextField.setLocation(315, 30);
        this.speedTextField.setText(String.format(" %.2f", this.simulationSpeedSlider.getValue() * 5.0f / 500.0f));
        this.add(this.speedTextField);
        (this.quantiumTimeSlider = new JSlider(0)).setMinimum(1);
        this.quantiumTimeSlider.setMaximum(10);
        this.quantiumTimeSlider.setPaintTicks(false);
        this.quantiumTimeSlider.setMajorTickSpacing(1);
        this.quantiumTimeSlider.setSize(150, 30);
        this.quantiumTimeSlider.setLocation(155, 70);
        this.quantiumTimeSlider.setOpaque(false);
        this.quantiumTimeSlider.setValue(3);
        this.quantiumTimeSlider.setFocusable(false);
        this.add(this.quantiumTimeSlider);
        if (algorithm != 3) {
            this.quantiumTimeSlider.setEnabled(false);
        }
        if (algorithm == 3) {
            this.quantiumTimeSlider.setMinimum(this.getMinValueForQuantium());
        }
        (this.quantiumTimeTextField = new JTextField()).setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.quantiumTimeTextField.setEnabled(false);
        this.quantiumTimeTextField.setFont(new Font("Sansserif", 1, 15));
        this.quantiumTimeTextField.setText("   " + this.quantiumTimeSlider.getValue());
        this.quantiumTimeTextField.setSize(40, 25);
        this.quantiumTimeTextField.setLocation(315, 70);
        this.add(this.quantiumTimeTextField);
        this.tablePanel = new JPanel();
        String s1 = "Input";
        String s2 = "\u0110\u1ea7u v\u00e0o";
        if (language == 0) {
            this.tablePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), s1));
        }
        else {
            this.tablePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), s2));
        }
        this.tablePanel.setBackground(Color.GRAY);
        this.tablePanel.setSize(330, 200);
        this.tablePanel.setLocation(25, 120);
        this.tablePanel.setLayout(new BorderLayout());
        this.add(this.tablePanel);
        (this.table = new JTable(this.data, this.columnNames)).setEnabled(false);
        this.table.setBackground(Color.LIGHT_GRAY);
        final JScrollPane scrollPane = new JScrollPane(this.table);
        this.table.setFillsViewportHeight(true);
        this.tablePanel.add(scrollPane, "Center");
        int sum = 0;
        for (int count = 0; count < processes.size(); ++count) {
            this.table.setValueAt(this.processes.get(count).getProcessID(), count, 0);
            this.table.setValueAt(this.processes.get(count).getArrivalTime(), count, 1);
            this.table.setValueAt(this.processes.get(count).getBurstTime(), count, 2);
            this.table.setValueAt(this.processes.get(count).getPriority(), count, 3);
            sum += this.processes.get(count).getBurstTime();
        }
        System.out.println(sum);
        (this.readyQueueTextField = new JTextField()).setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY)));
        this.readyQueueTextField.setEnabled(false);
        this.readyQueueTextField.setBackground(Color.LIGHT_GRAY);
        this.readyQueueTextField.setFont(new Font("Sansserif", 0, 15));
        this.readyQueueTextField.setDisabledTextColor(Color.BLACK);
        final JScrollPane readyQueueScroJScrollPane = new JScrollPane(this.readyQueueTextField, 21, 32);
        readyQueueScroJScrollPane.setSize(330, 40);
        readyQueueScroJScrollPane.setLocation(25, 370);
        this.add(readyQueueScroJScrollPane);
        (this.cpuStateTextField = new JTextField()).setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.cpuStateTextField.setEnabled(false);
        this.cpuStateTextField.setBackground(Color.LIGHT_GRAY);
        this.cpuStateTextField.setText(" Idle");
        this.cpuStateTextField.setSize(80, 25);
        this.cpuStateTextField.setLocation(70, 440);
        this.cpuStateTextField.setDisabledTextColor(Color.BLACK);
        this.add(this.cpuStateTextField);
        (this.algorithmTextField = new JTextField()).setEnabled(false);
        this.algorithmTextField.setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.algorithmTextField.setDisabledTextColor(Color.BLACK);
        this.algorithmTextField.setFont(new Font("Sansserif", 1, 15));
        this.algorithmTextField.setText(this.algorithmNames[this.algorithm]);
        this.algorithmTextField.setBackground(Color.LIGHT_GRAY);
        this.algorithmTextField.setSize(105, 25);
        this.algorithmTextField.setLocation(250, 440);
        this.add(this.algorithmTextField);
        s1 = "Pause";
        s2 = "D\u1eebng";
        this.pauseButton = new JButton();
        if (language == 0) {
            this.pauseButton.setText("Pause");
        }
        else if (language == 1) {
            this.pauseButton.setText("D\u1eebng");
        }
        this.pauseButton.setSize(80, 25);
        this.pauseButton.setLocation(410, 30);
        this.pauseButton.setFocusable(false);
        this.pauseButton.setOpaque(false);
        this.pauseButton.setEnabled(false);
        this.add(this.pauseButton);
        this.backButton = new JButton();
        if (language == 0) {
            this.backButton.setText("Back");
        }
        else {
            this.backButton.setText("Quay l\u1ea1i");
        }
        this.backButton.setSize(80, 25);
        this.backButton.setFocusable(false);
        this.backButton.setOpaque(false);
        this.backButton.setLocation(410, 70);
        this.add(this.backButton);
        this.simulateButton = new JButton();
        if (language == 0) {
            this.simulateButton.setText("Simulate");
        }
        else {
            this.simulateButton.setText("M\u00f4 ph\u1ecfng");
        }
        this.simulateButton.setSize(100, 65);
        this.simulateButton.setLocation(520, 30);
        this.simulateButton.setOpaque(false);
        this.simulateButton.setFocusable(false);
        this.add(this.simulateButton);
        this.forwardButton = new JButton();
        if (language == 0) {
            this.forwardButton.setText("Forward");
        }
        else {
            this.forwardButton.setText("T\u1edbi");
        }
        this.forwardButton.setSize(100, 65);
        this.forwardButton.setLocation(660, 30);
        this.forwardButton.setOpaque(false);
        this.forwardButton.setFocusable(false);
        this.add(this.forwardButton);
        this.exitButton = new JButton();
        if (language == 0) {
            this.exitButton.setText("Exit");
        }
        else {
            this.exitButton.setText("Tho\u00e1t");
        }
        this.exitButton.setSize(80, 25);
        this.exitButton.setLocation(790, 30);
        this.exitButton.setFocusable(false);
        this.exitButton.setOpaque(false);
        this.add(this.exitButton);
        this.againButton = new JButton();
        if (language == 0) {
            this.againButton.setText("Again");
        }
        else if (language == 1) {
            this.againButton.setText("L\u1eb7p l\u1ea1i");
        }
        this.againButton.setSize(80, 25);
        this.againButton.setLocation(790, 70);
        this.againButton.setFocusable(false);
        this.againButton.setOpaque(false);
        this.againButton.setEnabled(false);
        this.add(this.againButton);
        this.progressBars = new JProgressBar[10];
        for (int count2 = 0; count2 < this.progressBars.length; ++count2) {
            (this.progressBars[count2] = new JProgressBar(0)).setSize(200, 20);
            this.progressBars[count2].setOpaque(false);
            this.progressBars[count2].setLocation(500, 232 + count2 * 35);
            this.progressBars[count2].setStringPainted(true);
            this.progressBars[count2].setBackground(Color.CYAN);
            this.add(this.progressBars[count2]);
        }
        this.waitingTimeOfProcessesLabel = new JLabel[this.progressBars.length];
        for (int count2 = 0; count2 < this.waitingTimeOfProcessesLabel.length; ++count2) {
            (this.waitingTimeOfProcessesLabel[count2] = new JLabel()).setSize(30, 25);
            this.waitingTimeOfProcessesLabel[count2].setFont(new Font("Sansserif", 1, 15));
            this.waitingTimeOfProcessesLabel[count2].setText("0");
            this.waitingTimeOfProcessesLabel[count2].setForeground(Color.GRAY);
            this.waitingTimeOfProcessesLabel[count2].setLocation(840, 227 + count2 * 35);
            this.add(this.waitingTimeOfProcessesLabel[count2]);
        }
        this.processNumberLabel = new JLabel[this.progressBars.length];
        for (int count2 = 0; count2 < this.processNumberLabel.length; ++count2) {
            (this.processNumberLabel[count2] = new JLabel()).setSize(30, 25);
            this.processNumberLabel[count2].setFont(new Font("Sansserif", 1, 15));
            this.processNumberLabel[count2].setText(count2 + 1 + ".");
            this.processNumberLabel[count2].setForeground(Color.GRAY);
            this.processNumberLabel[count2].setLocation(405, 227 + count2 * 35);
            this.add(this.processNumberLabel[count2]);
        }
        (this.averageWaitingTimeLabel = new JLabel()).setFont(new Font("Sansserif", 1, 15));
        this.averageWaitingTimeLabel.setSize(70, 25);
        this.averageWaitingTimeLabel.setText("0.00");
        this.averageWaitingTimeLabel.setForeground(Color.CYAN);
        this.averageWaitingTimeLabel.setLocation(310, 487);
        this.add(this.averageWaitingTimeLabel);
        (this.averageTurnaroundTimeLabel = new JLabel()).setFont(new Font("Sansserif", 1, 15));
        this.averageTurnaroundTimeLabel.setSize(70, 25);
        this.averageTurnaroundTimeLabel.setText("0.00");
        this.averageTurnaroundTimeLabel.setForeground(Color.CYAN);
        this.averageTurnaroundTimeLabel.setLocation(310, 517);
        this.add(this.averageTurnaroundTimeLabel);
        (this.totalExecutionTimeLabel = new JLabel()).setFont(new Font("Sansserif", 1, 15));
        this.totalExecutionTimeLabel.setForeground(Color.CYAN);
        this.totalExecutionTimeLabel.setSize(70, 25);
        this.totalExecutionTimeLabel.setText("0");
        this.totalExecutionTimeLabel.setLocation(310, 547);
        this.add(this.totalExecutionTimeLabel);
        (this.ganttChartTextField = new JTextField()).setFont(new Font("Sansserif", 1, 15));
        this.ganttChartTextField.setBackground(Color.CYAN);
        this.ganttChartTextField.setEnabled(false);
        final JScrollPane ganttChartScrollPane = new JScrollPane(this.ganttChartTextField, 21, 32);
        ganttChartScrollPane.setSize(710, 60);
        ganttChartScrollPane.setLocation(25, 625);
        ganttChartScrollPane.setAutoscrolls(true);
        this.add(ganttChartScrollPane);
        this.printToFileButton = new JButton();
        if (language == 0) {
            this.printToFileButton.setText("Print To File");
        }
        else if (language == 1) {
            this.printToFileButton.setText("In ra file");
        }
        this.printToFileButton.setFocusable(false);
        this.printToFileButton.setOpaque(false);
        this.printToFileButton.setSize(100, 80);
        this.printToFileButton.setLocation(770, 605);
        this.add(this.printToFileButton);
        this.remainingBurstTimeLabel = new JLabel[this.progressBars.length];
        for (int count3 = 0; count3 < this.remainingBurstTimeLabel.length; ++count3) {
            (this.remainingBurstTimeLabel[count3] = new JLabel()).setSize(30, 25);
            this.remainingBurstTimeLabel[count3].setFont(new Font("Sansserif", 1, 15));
            if (count3 < this.processes.size()) {
                this.remainingBurstTimeLabel[count3].setText(String.format("%d", this.processes.get(count3).getBurstTime()));
            }
            else {
                this.remainingBurstTimeLabel[count3].setText("0");
            }
            this.remainingBurstTimeLabel[count3].setForeground(Color.GRAY);
            this.remainingBurstTimeLabel[count3].setLocation(750, 227 + count3 * 35);
            this.add(this.remainingBurstTimeLabel[count3]);
        }
        this.processesIDLabel = new JLabel[this.progressBars.length];
        for (int count3 = 0; count3 < this.remainingBurstTimeLabel.length; ++count3) {
            (this.processesIDLabel[count3] = new JLabel()).setSize(50, 25);
            this.processesIDLabel[count3].setFont(new Font("Sansserif", 1, 15));
            if (count3 < this.processes.size()) {
                this.processesIDLabel[count3].setText(processes.get(count3).getProcessID());
            }
            else {
                this.remainingBurstTimeLabel[count3].setText("");
            }
            this.processesIDLabel[count3].setForeground(Color.GRAY);
            this.processesIDLabel[count3].setLocation(435, 227 + count3 * 35);
            this.add(this.processesIDLabel[count3]);
        }
        (this.backgroundPanel = new Background(language)).setSize(1000, 750);
        this.add(this.backgroundPanel);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.setSize(903, 740);
        this.setResizable(false);
        final ActionHandler handler = new ActionHandler();
        this.simulateButton.addActionListener(handler);
        this.exitButton.addActionListener(handler);
        this.pauseButton.addActionListener(handler);
        this.backButton.addActionListener(handler);
        this.againButton.addActionListener(handler);
        this.forwardButton.addActionListener(handler);
        this.printToFileButton.addActionListener(handler);
        final ChangeHandler changeHandler = new ChangeHandler();
        this.simulationSpeedSlider.addChangeListener(changeHandler);
        this.quantiumTimeSlider.addChangeListener(changeHandler);
    }
    
    public void setProcess(final List<Process> p) {
        this.processes = p;
    }
    
    private int getMinValueForQuantium() {
        int min = 0;
        if (this.checkAllATIsTheSame()) {
            return 1;
        }
        final List<Process> list = new ArrayList<Process>();
        for (final Process p : this.processes) {
            list.add(p);
        }
        Collections.sort(list, new Comparator<Process>() {
            @Override
            public int compare(final Process p1, final Process p2) {
                return p1.getArrivalTime() - p2.getArrivalTime();
            }
        });
        min = list.get(1).getArrivalTime() - list.get(0).getArrivalTime();
        if (min == 0) {
            min = 1;
        }
        return min;
    }
    
    private boolean checkAllATIsTheSame() {
        final int at = this.processes.get(0).getArrivalTime();
        for (final Process p : this.processes) {
            if (p.getArrivalTime() != at) {
                return false;
            }
        }
        return true;
    }
    
    static /* synthetic */ void access$18(final MainProgramPanel mainProgramPanel, final FCFSWorker fcfsWorker) {
        mainProgramPanel.fcfsWorker = fcfsWorker;
    }
    
    static /* synthetic */ void access$20(final MainProgramPanel mainProgramPanel, final SJFWorker sjfWorker) {
        mainProgramPanel.sjfWorker = sjfWorker;
    }
    
    static /* synthetic */ void access$22(final MainProgramPanel mainProgramPanel, final SRTFWorker srtfWorker) {
        mainProgramPanel.srtfWorker = srtfWorker;
    }
    
    static /* synthetic */ void access$25(final MainProgramPanel mainProgramPanel, final RRWorker rrWorker) {
        mainProgramPanel.rrWorker = rrWorker;
    }
    
    static /* synthetic */ void access$27(final MainProgramPanel mainProgramPanel, final PriorityWorker priorityWorker) {
        mainProgramPanel.priorityWorker = priorityWorker;
    }
    
    static /* synthetic */ void access$31(final MainProgramPanel mainProgramPanel, final boolean isPaused) {
        mainProgramPanel.isPaused = isPaused;
    }
    
    static /* synthetic */ void access$32(final MainProgramPanel mainProgramPanel, final List processes) {
        mainProgramPanel.processes = (List<Process>)processes;
    }
    
    static /* synthetic */ void access$33(final MainProgramPanel mainProgramPanel, final boolean firstClickOnForward) {
        mainProgramPanel.firstClickOnForward = firstClickOnForward;
    }
    
    static /* synthetic */ void access$37(final MainProgramPanel mainProgramPanel, final FCFSForward fcfsForward) {
        mainProgramPanel.fcfsForward = fcfsForward;
    }
    
    static /* synthetic */ void access$39(final MainProgramPanel mainProgramPanel, final SJFForward sjfForward) {
        mainProgramPanel.sjfForward = sjfForward;
    }
    
    static /* synthetic */ void access$41(final MainProgramPanel mainProgramPanel, final PriorityForward priorityForward) {
        mainProgramPanel.priorityForward = priorityForward;
    }
    
    static /* synthetic */ void access$43(final MainProgramPanel mainProgramPanel, final RRForward rrForward) {
        mainProgramPanel.rrForward = rrForward;
    }
    
    static /* synthetic */ void access$45(final MainProgramPanel mainProgramPanel, final SRTFForward srtfForward) {
        mainProgramPanel.srtfForward = srtfForward;
    }
    
    private class ActionHandler implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == MainProgramPanel.this.simulateButton) {
                MainProgramPanel.this.simulateButton.setEnabled(false);
                MainProgramPanel.this.forwardButton.setEnabled(false);
                MainProgramPanel.this.againButton.setEnabled(false);
                MainProgramPanel.this.pauseButton.setEnabled(true);
                for (int count = 0; count < MainProgramPanel.this.copyProcesses.size(); ++count) {
                    MainProgramPanel.this.progressBars[count].setMaximum(MainProgramPanel.this.copyProcesses.get(count).getBurstTime());
                }
                if (MainProgramPanel.this.algorithm == 0) {
                    final SolveFCFS fcfsSolver = new SolveFCFS(MainProgramPanel.this.processes, 0);
                    fcfsSolver.solve();
                    MainProgramPanel.access$18(MainProgramPanel.this, new FCFSWorker(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, fcfsSolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, fcfsSolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, fcfsSolver.getValueProgressList(), fcfsSolver.getProcessProgressList(), fcfsSolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, fcfsSolver.getRemainingBurstTimeList(), fcfsSolver.getRemainingBurstTimePositionList(), fcfsSolver.getWaitingTimePositionList(), fcfsSolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.simulationSpeedSlider.getValue() * 10, MainProgramPanel.this.language));
                    MainProgramPanel.this.fcfsWorker.execute();
                }
                else if (MainProgramPanel.this.algorithm == 1) {
                    final SolveSJF sjfSolver = new SolveSJF(MainProgramPanel.this.processes);
                    sjfSolver.solve();
                    MainProgramPanel.access$20(MainProgramPanel.this, new SJFWorker(sjfSolver.getProcesses(), MainProgramPanel.this.ganttChartTextField, sjfSolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, sjfSolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, sjfSolver.getValueProgressList(), sjfSolver.getProcessProgressList(), sjfSolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, sjfSolver.getRemainingBurstTimeList(), sjfSolver.getRemainingBurstTimePositionList(), sjfSolver.getWaitingTimePositionList(), sjfSolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.simulationSpeedSlider.getValue() * 10, MainProgramPanel.this.language));
                    MainProgramPanel.this.sjfWorker.execute();
                }
                else if (MainProgramPanel.this.algorithm == 2) {
                    final SolveSRTF srtfSolver = new SolveSRTF(MainProgramPanel.this.processes);
                    srtfSolver.solve();
                    MainProgramPanel.access$22(MainProgramPanel.this, new SRTFWorker(srtfSolver.getProcesses(), MainProgramPanel.this.ganttChartTextField, srtfSolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, srtfSolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, srtfSolver.getValueProgressList(), srtfSolver.getProcessProgressList(), srtfSolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, srtfSolver.getRemainingBurstTimeList(), srtfSolver.getRemainingBurstTimePositionList(), srtfSolver.getWaitingTimePositionList(), srtfSolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.simulationSpeedSlider.getValue() * 10, MainProgramPanel.this.language));
                    MainProgramPanel.this.srtfWorker.execute();
                }
                else if (MainProgramPanel.this.algorithm == 3) {
                    for (int count = 0; count < MainProgramPanel.this.progressBars.length; ++count) {
                        System.out.println(String.valueOf(count) + " : " + MainProgramPanel.this.progressBars[count].getMaximum());
                    }
                    MainProgramPanel.this.quantiumTimeSlider.setEnabled(false);
                    final SolveRR rrSolver = new SolveRR(MainProgramPanel.this.processes, MainProgramPanel.this.quantiumTimeSlider.getValue());
                    rrSolver.solve();
                    MainProgramPanel.access$25(MainProgramPanel.this, new RRWorker(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, rrSolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, rrSolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, rrSolver.getValueProgressList(), rrSolver.getProcessProgressList(), rrSolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, rrSolver.getRemainingBurstTimeList(), rrSolver.getRemainingBurstTimePositionList(), rrSolver.getWaitingTimePositionList(), rrSolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.simulationSpeedSlider.getValue() * 10, MainProgramPanel.this.quantiumTimeSlider, MainProgramPanel.this.language));
                    MainProgramPanel.this.rrWorker.execute();
                }
                else if (MainProgramPanel.this.algorithm == 4) {
                    final SolvePriority prioritySolver = new SolvePriority(MainProgramPanel.this.processes);
                    prioritySolver.solve();
                    MainProgramPanel.access$27(MainProgramPanel.this, new PriorityWorker(prioritySolver.getProcesses(), MainProgramPanel.this.ganttChartTextField, prioritySolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, prioritySolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, prioritySolver.getValueProgressList(), prioritySolver.getProcessProgressList(), prioritySolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, prioritySolver.getRemainingBurstTimeList(), prioritySolver.getRemainingBurstTimePositionList(), prioritySolver.getWaitingTimePositionList(), prioritySolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.simulationSpeedSlider.getValue() * 10, MainProgramPanel.this.language));
                    MainProgramPanel.this.priorityWorker.execute();
                }
            }
            else if (event.getSource() == MainProgramPanel.this.exitButton) {
                int choice = 0;
                if (MainProgramPanel.this.language == 0) {
                    choice = JOptionPane.showConfirmDialog(MainProgramPanel.this, "Do you want to exit?", "Select an option", 1);
                }
                else if (MainProgramPanel.this.language == 1) {
                    choice = JOptionPane.showConfirmDialog(MainProgramPanel.this, "B\u1ea1n c\u00f3 mu\u1ed1n tho\u00e1t?", "T\u00f9y ch\u1ecdn", 1);
                }
                if (choice == 0) {
                    System.exit(0);
                }
            }
            else if (event.getSource() == MainProgramPanel.this.pauseButton) {
                if (!MainProgramPanel.this.isPaused) {
                    if (MainProgramPanel.this.algorithm == 0) {
                        MainProgramPanel.this.fcfsWorker.pause();
                    }
                    else if (MainProgramPanel.this.algorithm == 1) {
                        MainProgramPanel.this.sjfWorker.pause();
                    }
                    else if (MainProgramPanel.this.algorithm == 4) {
                        MainProgramPanel.this.priorityWorker.pause();
                    }
                    else if (MainProgramPanel.this.algorithm == 3) {
                        MainProgramPanel.this.rrWorker.pause();
                    }
                    else if (MainProgramPanel.this.algorithm == 2) {
                        MainProgramPanel.this.srtfWorker.pause();
                    }
                    if (MainProgramPanel.this.language == 0) {
                        MainProgramPanel.this.pauseButton.setText("Resume");
                    }
                    else if (MainProgramPanel.this.language == 1) {
                        MainProgramPanel.this.pauseButton.setText("Kh\u00f4i ph\u1ee5c");
                    }
                    MainProgramPanel.access$31(MainProgramPanel.this, true);
                }
                else if (MainProgramPanel.this.isPaused) {
                    if (MainProgramPanel.this.algorithm == 0) {
                        MainProgramPanel.this.fcfsWorker.resume();
                    }
                    else if (MainProgramPanel.this.algorithm == 1) {
                        MainProgramPanel.this.sjfWorker.resume();
                    }
                    else if (MainProgramPanel.this.algorithm == 4) {
                        MainProgramPanel.this.priorityWorker.resume();
                    }
                    else if (MainProgramPanel.this.algorithm == 3) {
                        MainProgramPanel.this.rrWorker.resume();
                    }
                    else if (MainProgramPanel.this.algorithm == 2) {
                        MainProgramPanel.this.srtfWorker.resume();
                    }
                    if (MainProgramPanel.this.language == 0) {
                        MainProgramPanel.this.pauseButton.setText("Pause");
                    }
                    else if (MainProgramPanel.this.language == 1) {
                        MainProgramPanel.this.pauseButton.setText("D\u1eebng");
                    }
                    MainProgramPanel.access$31(MainProgramPanel.this, false);
                }
            }
            else if (event.getSource() == MainProgramPanel.this.againButton) {
                for (int count = 0; count < MainProgramPanel.this.waitingTimeOfProcessesLabel.length; ++count) {
                    MainProgramPanel.this.waitingTimeOfProcessesLabel[count].setText("0");
                }
                MainProgramPanel.access$32(MainProgramPanel.this, new ArrayList());
                for (int count = 0; count < MainProgramPanel.this.copyProcesses.size(); ++count) {
                    MainProgramPanel.this.processes.add(MainProgramPanel.this.copyProcesses.get(count));
                    MainProgramPanel.this.processes.get(count).setWaitingTime(-1);
                    MainProgramPanel.this.processes.get(count).setRemainingTime(MainProgramPanel.this.copyProcesses.get(count).getBurstTime());
                }
                for (int count = 0; count < MainProgramPanel.this.remainingBurstTimeLabel.length; ++count) {
                    if (count < MainProgramPanel.this.processes.size()) {
                        MainProgramPanel.this.remainingBurstTimeLabel[count].setText(String.format("%d", MainProgramPanel.this.processes.get(count).getBurstTime()));
                    }
                    else {
                        MainProgramPanel.this.remainingBurstTimeLabel[count].setText("");
                    }
                }
                for (int count = 0; count < MainProgramPanel.this.progressBars.length; ++count) {
                    MainProgramPanel.this.progressBars[count].setValue(MainProgramPanel.this.progressBars[count].getMinimum());
                }
                MainProgramPanel.this.ganttChartTextField.setText("");
                MainProgramPanel.this.averageTurnaroundTimeLabel.setText("0.00");
                MainProgramPanel.this.averageWaitingTimeLabel.setText("0.00");
                MainProgramPanel.this.totalExecutionTimeLabel.setText("0");
                MainProgramPanel.this.simulateButton.setEnabled(true);
                MainProgramPanel.this.forwardButton.setEnabled(true);
                MainProgramPanel.access$33(MainProgramPanel.this, true);
            }
            else if (event.getSource() == MainProgramPanel.this.backButton) {
                MainProgramPanel.this.setVisible(false);
                MainProgramPanel.this.setEnabled(false);
                switch (MainProgramPanel.this.previousPanel) {
                    case 10: {
                        final ImportInputPanel inputPanel = new ImportInputPanel(MainProgramPanel.this.algorithm);
                        break;
                    }
                }
            }
            else if (event.getSource() == MainProgramPanel.this.forwardButton) {
                if (MainProgramPanel.this.firstClickOnForward) {
                    MainProgramPanel.this.simulateButton.setEnabled(false);
                    MainProgramPanel.this.pauseButton.setEnabled(false);
                    MainProgramPanel.access$33(MainProgramPanel.this, false);
                    for (int count = 0; count < MainProgramPanel.this.copyProcesses.size(); ++count) {
                        MainProgramPanel.this.progressBars[count].setMaximum(MainProgramPanel.this.copyProcesses.get(count).getBurstTime());
                    }
                    switch (MainProgramPanel.this.algorithm) {
                        case 0: {
                            final SolveFCFS fcfsSolver = new SolveFCFS(MainProgramPanel.this.processes, 0);
                            fcfsSolver.solve();
                            MainProgramPanel.access$37(MainProgramPanel.this, new FCFSForward(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, fcfsSolver.getGanttChart(), MainProgramPanel.this.cpuStateTextField, fcfsSolver.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, fcfsSolver.getValueProgressList(), fcfsSolver.getProcessProgressList(), fcfsSolver.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, fcfsSolver.getRemainingBurstTimeList(), fcfsSolver.getRemainingBurstTimePositionList(), fcfsSolver.getWaitingTimePositionList(), fcfsSolver.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.forwardButton, MainProgramPanel.this.language));
                            MainProgramPanel.this.fcfsForward.forward();
                            break;
                        }
                        case 1: {
                            final SolveSJF sjfSolver2 = new SolveSJF(MainProgramPanel.this.processes);
                            sjfSolver2.solve();
                            MainProgramPanel.access$39(MainProgramPanel.this, new SJFForward(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, sjfSolver2.getGanttChart(), MainProgramPanel.this.cpuStateTextField, sjfSolver2.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, sjfSolver2.getValueProgressList(), sjfSolver2.getProcessProgressList(), sjfSolver2.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, sjfSolver2.getRemainingBurstTimeList(), sjfSolver2.getRemainingBurstTimePositionList(), sjfSolver2.getWaitingTimePositionList(), sjfSolver2.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.forwardButton, MainProgramPanel.this.language));
                            MainProgramPanel.this.sjfForward.forward();
                            break;
                        }
                        case 4: {
                            final SolvePriority prioritySolver2 = new SolvePriority(MainProgramPanel.this.processes);
                            prioritySolver2.solve();
                            MainProgramPanel.access$41(MainProgramPanel.this, new PriorityForward(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, prioritySolver2.getGanttChart(), MainProgramPanel.this.cpuStateTextField, prioritySolver2.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, prioritySolver2.getValueProgressList(), prioritySolver2.getProcessProgressList(), prioritySolver2.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, prioritySolver2.getRemainingBurstTimeList(), prioritySolver2.getRemainingBurstTimePositionList(), prioritySolver2.getWaitingTimePositionList(), prioritySolver2.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.forwardButton, MainProgramPanel.this.language));
                            MainProgramPanel.this.priorityForward.forward();
                            break;
                        }
                        case 3: {
                            MainProgramPanel.this.quantiumTimeSlider.setEnabled(false);
                            final SolveRR rrSolver2 = new SolveRR(MainProgramPanel.this.processes, MainProgramPanel.this.quantiumTimeSlider.getValue());
                            rrSolver2.solve();
                            MainProgramPanel.access$43(MainProgramPanel.this, new RRForward(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, rrSolver2.getGanttChart(), MainProgramPanel.this.cpuStateTextField, rrSolver2.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, rrSolver2.getValueProgressList(), rrSolver2.getProcessProgressList(), rrSolver2.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, rrSolver2.getRemainingBurstTimeList(), rrSolver2.getRemainingBurstTimePositionList(), rrSolver2.getWaitingTimePositionList(), rrSolver2.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.forwardButton, MainProgramPanel.this.quantiumTimeSlider, MainProgramPanel.this.language));
                            MainProgramPanel.this.rrForward.forward();
                            break;
                        }
                        case 2: {
                            final SolveSRTF srtfSolver2 = new SolveSRTF(MainProgramPanel.this.processes);
                            srtfSolver2.solve();
                            MainProgramPanel.access$45(MainProgramPanel.this, new SRTFForward(MainProgramPanel.this.processes, MainProgramPanel.this.ganttChartTextField, srtfSolver2.getGanttChart(), MainProgramPanel.this.cpuStateTextField, srtfSolver2.getCPUStates(), MainProgramPanel.this.againButton, MainProgramPanel.this.totalExecutionTimeLabel, MainProgramPanel.this.progressBars, srtfSolver2.getValueProgressList(), srtfSolver2.getProcessProgressList(), srtfSolver2.getReadyQueueList(), MainProgramPanel.this.readyQueueTextField, MainProgramPanel.this.remainingBurstTimeLabel, srtfSolver2.getRemainingBurstTimeList(), srtfSolver2.getRemainingBurstTimePositionList(), srtfSolver2.getWaitingTimePositionList(), srtfSolver2.getWaitingTimeList(), MainProgramPanel.this.waitingTimeOfProcessesLabel, MainProgramPanel.this.averageWaitingTimeLabel, MainProgramPanel.this.averageTurnaroundTimeLabel, MainProgramPanel.this.pauseButton, MainProgramPanel.this.forwardButton, MainProgramPanel.this.language));
                            MainProgramPanel.this.srtfForward.forward();
                            break;
                        }
                    }
                }
                else {
                    switch (MainProgramPanel.this.algorithm) {
                        case 0: {
                            MainProgramPanel.this.fcfsForward.forward();
                            break;
                        }
                        case 1: {
                            MainProgramPanel.this.sjfForward.forward();
                            break;
                        }
                        case 4: {
                            MainProgramPanel.this.priorityForward.forward();
                            break;
                        }
                        case 3: {
                            MainProgramPanel.this.rrForward.forward();
                            break;
                        }
                        case 2: {
                            MainProgramPanel.this.srtfForward.forward();
                            break;
                        }
                    }
                }
            }
            else if (event.getSource() == MainProgramPanel.this.printToFileButton) {
                final JFileChooser fileChooser = new JFileChooser();
                final int choice2 = fileChooser.showSaveDialog(MainProgramPanel.this);
                if (choice2 == 0) {
                    final File file = fileChooser.getSelectedFile();
                    if (file.exists()) {
                        if (file.isFile()) {
                            final String fileName = file.getName();
                            if (fileName.matches(".*txt")) {
                                Formatter output = null;
                                try {
                                    output = new Formatter(file);
                                    final String text = MainProgramPanel.this.ganttChartTextField.getText();
                                    output.format("%s", text);
                                }
                                catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(MainProgramPanel.this, "Sorry, file not found.\nPlease try again.", "Error", 0);
                                    return;
                                }
                                finally {
                                    output.close();
                                }
                                output.close();
                            }
                            else {
                                JOptionPane.showMessageDialog(MainProgramPanel.this, "Only support file .txt", "Error", 0);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(MainProgramPanel.this, "Only support file .txt", "Error", 0);
                        }
                    }
                    else if (MainProgramPanel.this.language == 0) {
                        JOptionPane.showMessageDialog(MainProgramPanel.this, "File does not exist", "Error", 0);
                    }
                    else if (MainProgramPanel.this.language == 1) {
                        JOptionPane.showMessageDialog(MainProgramPanel.this, "File kh\u00f4ng t\u1ed3n t\u1ea1i", "L\u1ed7i", 0);
                    }
                }
            }
        }
    }
    
    private class ChangeHandler implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            MainProgramPanel.this.speedTextField.setText(String.format("%.2f", MainProgramPanel.this.simulationSpeedSlider.getValue() / 100.0f));
            if (MainProgramPanel.this.pauseButton.isEnabled()) {
                if (MainProgramPanel.this.algorithm == 0) {
                    MainProgramPanel.this.fcfsWorker.setSleepTime(MainProgramPanel.this.simulationSpeedSlider.getValue() * 10);
                }
                else if (MainProgramPanel.this.algorithm == 1) {
                    MainProgramPanel.this.sjfWorker.setSleepTime(MainProgramPanel.this.simulationSpeedSlider.getValue() * 10);
                }
                else if (MainProgramPanel.this.algorithm == 4) {
                    MainProgramPanel.this.priorityWorker.setSleepTime(MainProgramPanel.this.simulationSpeedSlider.getValue() * 10);
                }
                else if (MainProgramPanel.this.algorithm == 3) {
                    MainProgramPanel.this.rrWorker.setSleepTime(MainProgramPanel.this.simulationSpeedSlider.getValue() * 10);
                }
                else if (MainProgramPanel.this.algorithm == 2) {
                    MainProgramPanel.this.srtfWorker.setSleepTime(MainProgramPanel.this.simulationSpeedSlider.getValue() * 10);
                }
            }
            if (event.getSource() == MainProgramPanel.this.quantiumTimeSlider) {
                MainProgramPanel.this.quantiumTimeTextField.setText(String.format("%d", MainProgramPanel.this.quantiumTimeSlider.getValue()));
            }
        }
    }
}