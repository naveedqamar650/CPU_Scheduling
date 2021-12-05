/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class BackgroundInterfacePanel extends JPanel
{
    private ImageIcon backgroundImage;
    
    public BackgroundInterfacePanel() {
        this.backgroundImage = new ImageIcon(this.getClass().getResource("BackGround1.jpg"));
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.backgroundImage.paintIcon(this, g, 0, 0);
    }
    
    public void setBackground(final int selectedIndex) {
        if (selectedIndex == 0) {
            this.backgroundImage = new ImageIcon(this.getClass().getResource("BackGround1.jpg"));
        }
        else if (selectedIndex == 1) {
            this.backgroundImage = new ImageIcon(this.getClass().getResource("BackGround1.jpg"));
        }
    }
}
