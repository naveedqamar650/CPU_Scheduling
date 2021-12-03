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

class BackGroundImportInputPanel extends JPanel
{
    private ImageIcon backgroundImage;
    
    public BackGroundImportInputPanel(final int language) {
        if (language == 0) {
            this.backgroundImage = new ImageIcon(this.getClass().getResource("input_Fotor.jpg"));
        }
        else if (language == 1) {
            this.backgroundImage = new ImageIcon(this.getClass().getResource("ImportInputViet_Fotor.jpg"));
        }
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.backgroundImage.paintIcon(this, g, 0, 0);
    }
}
