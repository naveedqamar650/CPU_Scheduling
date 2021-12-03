/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.UIManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class ImportInputPanel extends JFrame
{
    private JTable table;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    private BackGroundImportInputPanel backgroundPanel;
    private JButton backButton;
    private JButton startButton;
    private JButton exitButton;
    private JButton inputButton;
    private JTextField processIDTextField;
    private JTextField arrivalTimeTextField;
    private JTextField burstTimeTextField;
    private JTextField priorityTextField;
    private String[] columnNames;
    private String[] columnNamesInVietNamese;
    private Object[][] data;
    private List<Process> processes;
    private int currentRow;
    private static final int MAXIMUM_ROW = 10;
    private int language;
    private int algorithm;
    private static final int PRIORITY = 4;
    
    public ImportInputPanel(final int algorithm) {
        this.columnNames = new String[] { "Process ID", "Arrival Time", "Burst Time", "Priority" };
        this.columnNamesInVietNamese = new String[] { "Ti\u1ebfn tr\u00ecnh", "T.g \u0111\u1ebfn", "T.g th\u1ef1c thi", "\u0110\u1ed9 \u01b0u ti\u00ean" };
        this.data = new Object[10][4];
        this.processes = new ArrayList<Process>();
        this.currentRow = 0;
        this.language = 0;
        this.algorithm = 0;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setLayout(null);
        this.algorithm = algorithm;
        (this.tablePanel = new JPanel()).setBackground(Color.WHITE);
        this.tablePanel.setSize(400, 200);
        this.tablePanel.setLocation(110, 340);
        this.tablePanel.setLayout(new BorderLayout());
        this.tablePanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.add(this.tablePanel);
        if (language == 0) {
            this.table = new JTable(this.data, this.columnNames);
        }
        else if (language == 1) {
            this.table = new JTable(this.data, this.columnNamesInVietNamese);
        }
        this.table.setEnabled(false);
        final JScrollPane scrollPane = new JScrollPane(this.table);
        this.table.setFillsViewportHeight(true);
        this.tablePanel.add(scrollPane, "Center");
        (this.processIDTextField = new JTextField()).setSize(100, 25);
        this.processIDTextField.setLocation(340, 85);
        this.add(this.processIDTextField);
        (this.arrivalTimeTextField = new JTextField()).setSize(100, 25);
        this.arrivalTimeTextField.setLocation(340, 126);
        this.add(this.arrivalTimeTextField);
        (this.burstTimeTextField = new JTextField()).setSize(100, 25);
        this.burstTimeTextField.setLocation(340, 170);
        this.add(this.burstTimeTextField);
        (this.priorityTextField = new JTextField()).setSize(100, 25);
        this.priorityTextField.setLocation(340, 215);
        this.add(this.priorityTextField);
        if (algorithm != 4) {
            this.priorityTextField.setEditable(false);
        }
            this.processIDTextField.setToolTipText("Input Process ID");
            this.arrivalTimeTextField.setToolTipText("Input Arrival Time");
            this.burstTimeTextField.setToolTipText("Input Burst Time");
            this.priorityTextField.setToolTipText("Input  Priority");
        
        (this.buttonPanel = new JPanel()).setLayout(new GridLayout(1, 4, 0, 0));
        this.buttonPanel.setSize(300, 30);
        this.buttonPanel.setBackground(Color.WHITE);
        this.buttonPanel.setBorder(new TitledBorder(new LineBorder(Color.GRAY)));
        this.buttonPanel.setLocation(163, 285);
        this.add(this.buttonPanel);
        this.backButton = new JButton();
            this.backButton.setText("Back");
        this.exitButton = new JButton();
            this.exitButton.setText("Exit");
        this.startButton = new JButton();
            this.startButton.setText("Start");
        this.inputButton = new JButton();
            this.inputButton.setText("Input");
        this.buttonPanel.add(this.backButton);
        this.buttonPanel.add(this.exitButton);
        this.buttonPanel.add(this.startButton);
        this.buttonPanel.add(this.inputButton);
        (this.backgroundPanel = new BackGroundImportInputPanel(language)).setSize(642, 670);
        this.add(this.backgroundPanel);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.setSize(642, 685);
        this.setResizable(false);
        final ActionHandler actionHandler = new ActionHandler();
        this.inputButton.addActionListener(actionHandler);
        this.exitButton.addActionListener(actionHandler);
        this.backButton.addActionListener(actionHandler);
        this.startButton.addActionListener(actionHandler);
        if (this.currentRow == 10) {
            this.processIDTextField.setEditable(false);
            this.arrivalTimeTextField.setEditable(false);
            this.burstTimeTextField.setEditable(false);
            this.priorityTextField.setEditable(false);
        }
    }
    
    protected boolean checkDataConstraints() {
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
        final int at = list.get(0).getArrivalTime();
        int bound = at + list.get(0).getBurstTime();
        if (list.size() != 1) {
            for (int count = 0; count < list.size() - 1; ++count) {
                if (list.get(count + 1).getArrivalTime() > bound) {
                    return false;
                }
                bound += list.get(count + 1).getBurstTime();
            }
        }
        return true;
    }
    
    protected boolean checkSameName(final String s) {
        for (final Process p : this.processes) {
            if (p.getProcessID().equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    static /* synthetic */ void access$11(final ImportInputPanel importInputPanel, final int currentRow) {
        importInputPanel.currentRow = currentRow;
    }
    
    static /* synthetic */ void access$14(final ImportInputPanel importInputPanel, final List processes) {
        importInputPanel.processes = (List<Process>)processes;
    }
    
    private class ActionHandler implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == ImportInputPanel.this.exitButton) {
                int choice = 0;
                    choice = JOptionPane.showConfirmDialog(ImportInputPanel.this, "Do you want to exit?", "Select an option", 1);
                if (choice == 0) {
                    System.exit(0);
                }
            }
            else if (event.getSource() == ImportInputPanel.this.inputButton) {
                final String processID = ImportInputPanel.this.processIDTextField.getText();
                if (processID.equals("")) {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "ProcessID is empty", "Notification", 2);
                }
                else if (ImportInputPanel.this.checkSameName(processID)) {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "Duplicated Process ID", "Notification", 2);
                }
                int arrivalTime = -1;
                try {
                    arrivalTime = Integer.parseInt(ImportInputPanel.this.arrivalTimeTextField.getText());
                    if (arrivalTime < 0) {
                            JOptionPane.showMessageDialog(ImportInputPanel.this, "Burst Time must be positive integer", "Warning", 2);
                    }
                }
                catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "Arrival Time is empty or invalid type", "Warning", 2);
                }
                int burstTime = -1;
                try {
                    burstTime = Integer.parseInt(burstTimeTextField.getText());
                    if (burstTime <= 0) {
                            JOptionPane.showMessageDialog(ImportInputPanel.this, "Burst Time must be positive integer bigger than zero", "Warning", 2);
                    }
                }
                catch (NumberFormatException ex2) {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "Burst Time is empty or invalid type", "Warning", 2);
                }
                int priority = -1;
                if (ImportInputPanel.this.algorithm == 4) {
                    try {
                        priority = Integer.parseInt(priorityTextField.getText());
                        if (priority < 0) {
                                JOptionPane.showMessageDialog(ImportInputPanel.this, "Priority must be positive integer", "Warning", 2);
                        }
                    }
                    catch (NumberFormatException ex3) {
                            JOptionPane.showMessageDialog(ImportInputPanel.this, "Priority is empty or invalid type", "Warning", 2);
                    }
                }
                if (!processID.equals("") && arrivalTime >= 0 && burstTime > 0 && !ImportInputPanel.this.checkSameName(processID)) {
                    ImportInputPanel.this.table.setValueAt(processID, ImportInputPanel.this.currentRow, 0);
                    ImportInputPanel.this.table.setValueAt(String.format("%d", arrivalTime), ImportInputPanel.this.currentRow, 1);
                    ImportInputPanel.this.table.setValueAt(String.format("%d", burstTime), ImportInputPanel.this.currentRow, 2);
                    if (ImportInputPanel.this.algorithm == 4) {
                        if (priority >= 0) {
                            ImportInputPanel.this.table.setValueAt(String.format("%d", priority), ImportInputPanel.this.currentRow, 3);
                            ImportInputPanel.this.processes.add(new Process(processID, arrivalTime, burstTime, ImportInputPanel.this.currentRow, priority));
                        }
                    }
                    else {
                        ImportInputPanel.this.processes.add(new Process(processID, arrivalTime, burstTime, ImportInputPanel.this.currentRow));
                    }
                    if (ImportInputPanel.this.currentRow < 10) {
                        final ImportInputPanel this$0 = ImportInputPanel.this;
                        ImportInputPanel.access$11(this$0, this$0.currentRow + 1);
                    }
                }
                ImportInputPanel.this.processIDTextField.setText("");
                ImportInputPanel.this.arrivalTimeTextField.setText("");
                ImportInputPanel.this.burstTimeTextField.setText("");
                if (ImportInputPanel.this.algorithm == 4) {
                    ImportInputPanel.this.priorityTextField.setText("");
                }
            }
            else if (event.getSource() == ImportInputPanel.this.backButton) {
                ImportInputPanel.this.setVisible(false);
                ImportInputPanel.this.setEnabled(false);
                final Interface interface1 = new Interface();
            }
            else if (event.getSource() == ImportInputPanel.this.startButton) {
                if (processes.size() == 0) {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "Please input data before starting", "Warning", 2);
                }
                else if (ImportInputPanel.this.checkDataConstraints()) {
                    ImportInputPanel.this.setVisible(false);
                    ImportInputPanel.this.setEnabled(false);
                    MainProgramPanel mainProgramPanel = new MainProgramPanel(ImportInputPanel.this.algorithm, ImportInputPanel.this.language, ImportInputPanel.this.processes, 10);
                }
                else {
                        JOptionPane.showMessageDialog(ImportInputPanel.this, "Error with data's constraints", "WARNING", 2);
                    ImportInputPanel.access$14(ImportInputPanel.this, new ArrayList());
                    ImportInputPanel.access$11(ImportInputPanel.this, 0);
                    for (int count = 0; count < 10; ++count) {
                        table.setValueAt("", count, 0);
                        table.setValueAt("", count, 1);
                        table.setValueAt("", count, 2);
                        table.setValueAt("", count, 3);
                    }
                }
            }
        }
    }
    
}