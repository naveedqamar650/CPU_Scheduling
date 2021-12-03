/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class PersionPanel extends JPanel
{
    private ImageIcon personCover;
    
    public PersionPanel(final int persion) {
        if (persion == 1) {
            this.personCover = new ImageIcon(this.getClass().getResource("loc_Fotor.jpg"));
        }
        else if (persion == 2) {
            this.personCover = new ImageIcon(this.getClass().getResource("tuyet_Fotor.jpg"));
        }
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.personCover.paintIcon(this, g, 0, 0);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.personCover.getIconWidth(), this.personCover.getIconHeight());
    }
}