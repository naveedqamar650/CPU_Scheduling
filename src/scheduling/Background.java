/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class Background extends JPanel
{
    private ImageIcon backgroundImage;
    private int language;
    
    public Background(final int language) {
        this.backgroundImage = new ImageIcon(this.getClass().getResource("1685510.jpg"));
        this.language = language;
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.backgroundImage.paintIcon(this, g, 0, 0);
        g.setColor(Color.GRAY);
        g.setFont(new Font("Sansserif", 1, 15));
            g.drawString("Simulation Speed: ", 25, 47);
            g.drawString("Quantum Time:", 25, 87);
            g.drawString("Ready Queue:", 25, 360);
            g.drawString("CPU:", 25, 460);
            g.drawString("Algorithm:", 175, 460);
            g.drawString("Average Waiting Time:", 25, 507);
            g.drawString("Average Turnaround Time:", 25, 537);
            g.drawString("Total Execution Time:", 25, 567);
            g.drawString("Processes", 410, 150);
            g.drawString("Status Bar", 560, 150);
            g.drawString("Remaining", 720, 150);
            g.drawString("Burst", 730, 170);
            g.drawString("Time", 730, 190);
            g.drawString("Waiting", 820, 150);
            g.drawString("Time", 825, 170);
        g.setColor(Color.CYAN);
        g.drawRect(14, 20, 350, 80);
        g.drawRect(14, 110, 350, 220);
        g.drawRect(14, 340, 350, 80);
        g.drawRect(14, 430, 350, 45);
        g.drawRect(14, 485, 350, 95);
        g.drawRect(400, 20, 100, 85);
        g.drawRect(510, 20, 260, 85);
        g.drawRect(780, 20, 100, 85);
        g.drawRect(400, 120, 480, 85);
        g.drawRect(400, 220, 480, 360);
        g.drawRect(14, 590, 866, 105);
            g.drawString("Gantt Chart", 25, 610);
            g.drawString("or", 630, 70);
            System.out.println("or");
       
    }
}