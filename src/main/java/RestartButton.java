import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class RestartButton extends JButton implements ActionListener {
    private ImageIcon plainIcon;
    private Runnable onRestart;

    public RestartButton(ImageIcon plainIcon, Runnable onRestart) {
        this.plainIcon = plainIcon;
        this.setPreferredSize(new Dimension(33, 33));
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setIcon(plainIcon);
        // 
        this.onRestart = onRestart;
        this.addActionListener(this);
        this.setFocusPainted(false);
        this.setFocusable(false);
    }

    public void setIconToPlain() {
        this.setIcon(plainIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.onRestart.run();
    }
}