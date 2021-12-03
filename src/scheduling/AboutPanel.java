
package scheduling;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

class AboutPanel extends JPanel
{
    private JTextArea textArea;
    private ImageIcon image;
    private ImageIcon background;
    
    public AboutPanel(final int language) {
        this.setLayout(null);
        (this.textArea = new JTextArea()).setLineWrap(true);
        this.textArea.setEnabled(false);
        this.textArea.setOpaque(true);
        this.textArea.setBackground(Color.BLACK);
        this.textArea.setForeground(Color.GREEN);
        final JScrollPane scrollPane = new JScrollPane(this.textArea, 22, 31);
        scrollPane.setSize(385, 200);
        scrollPane.setBorder(new TitledBorder(new LineBorder(Color.GREEN)));
        scrollPane.setLocation(20, 80);
        scrollPane.setOpaque(true);
        this.add(scrollPane);
        this.setBackground(Color.BLACK);
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        new ImageIcon(this.getClass().getResource("pic4_Fotor.jpg")).paintIcon(this, g, 150, 300);
        g.setFont(new Font("Sansserif", 1, 20));
        g.setColor(Color.GREEN);
            g.drawString("CPU SCHEDULING SIMULATION PROJECT", 10, 20);
        
    }
}
