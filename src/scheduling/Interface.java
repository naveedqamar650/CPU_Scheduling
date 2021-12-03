/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.UIManager;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Interface extends JFrame
{
    private ImageIcon backGroundImage;
    private String[] algorithmNames;
    private final JComboBox algorithmCombobox;
    private JButton startButton;
    private JButton exitButton;
    private BackgroundInterfacePanel backgroundPanel;
    private JMenuBar menuBar;
    private JMenu contactMenu;
    private JMenuItem firstPersionMenuItem;
    private JMenuItem secondPersionMenuItem;
    
    public Interface() {
        algorithmNames = new String[] { "FCFS", "SJF", "SRTF", "Round Robin", "Priority" };
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
            System.err.println("Error generating UI");
            System.exit(1);
        }
        this.setLayout(null);
        (algorithmCombobox = new JComboBox(algorithmNames)).setMaximumRowCount(5);
        this.algorithmCombobox.setSize(100, 25);
        this.algorithmCombobox.setLocation(320, 370);
        
        this.add(this.algorithmCombobox);
        (this.startButton = new JButton("Start")).setSize(100, 25);
        this.startButton.setLocation(220, 450);
        
        this.add(this.startButton);
        (this.exitButton = new JButton("Exit")).setSize(100, 25);
        this.exitButton.setLocation(220, 490);
        
        this.add(this.exitButton);
        (this.backgroundPanel = new BackgroundInterfacePanel()).setSize(1000, 690);
        this.add(this.backgroundPanel);
        this.setJMenuBar(this.menuBar = new JMenuBar());
        this.contactMenu = new JMenu();
        this.contactMenu.setText("Information");  
        this.firstPersionMenuItem = new JMenuItem("+92 320-4308809");
        this.secondPersionMenuItem = new JMenuItem("Naveed");
        
        this.contactMenu.add(this.firstPersionMenuItem);
        this.contactMenu.add(this.secondPersionMenuItem);
        this.menuBar.add(this.contactMenu);
        
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.setSize(1000, 678);
        this.setResizable(false);
        ActionHandlerForComboBox ActionHandlerForComboBox;
        ActionHandlerForComboBox = new ActionHandlerForComboBox();
        algorithmCombobox.addActionListener(ActionHandlerForComboBox);
        ActionHandler actionHandler = new ActionHandler();
        startButton.addActionListener(actionHandler);
        exitButton.addActionListener(actionHandler);
        firstPersionMenuItem.addActionListener(actionHandler);
        secondPersionMenuItem.addActionListener(actionHandler);
    }
    
    public boolean isFCFSSelected() {
        return this.algorithmCombobox.getSelectedIndex() == 0;
    }
    
    public boolean isSJFSelected() {
        return this.algorithmCombobox.getSelectedIndex() == 1;
    }
    
    public boolean isSRTFSelected() {
        return this.algorithmCombobox.getSelectedIndex() == 2;
    }
    
    public boolean isRoundRobinSelected() {
        return this.algorithmCombobox.getSelectedIndex() == 3;
    }
    
    public boolean isPrioritySelected() {
        return this.algorithmCombobox.getSelectedIndex() == 4;
    }
    
    class ActionHandler implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == exitButton) {
                int choice = 0;
                
                    choice = JOptionPane.showConfirmDialog(Interface.this, "Do you want to exit?", "Select an option", 1);
                
                if (choice == 0) {
                    System.exit(0);
                }
            }
            else if (event.getSource() == startButton) {
                Interface.this.setVisible(false);
                Interface.this.setEnabled(false);
                    final ImportInputPanel importInputPanel = new ImportInputPanel(algorithmCombobox.getSelectedIndex());
                
            }
        }
    }
    class ActionHandlerForComboBox implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object c =(Object) event.getSource();
            if (c == algorithmCombobox){
                JComboBox a = (JComboBox) event.getSource();
                String string = (String) a.getSelectedItem();
                if (string.equals("FCFS")){
                    
                }else if(string.equals("SJF")){
                    
                }else if(string.equals("SRTF")){
                    
                }else if(string.equals("Round Robin")){
                    
                }else if(string.equals("Priority")){
                    
                }
            }
        }
    }
}