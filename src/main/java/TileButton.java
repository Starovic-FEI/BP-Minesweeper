import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TileButton extends JToggleButton {
    private boolean mine;
    private boolean flag;
    private boolean helperFlag;
    private boolean revealed;
    private boolean questionMark;

    private int[] tilePos = new int[2];

    public TileButton(ImageIcon tileIcon) {
        this.mine = false;
        this.flag = false;
        this.helperFlag = false;
        this.questionMark = false;
        this.revealed = false;
        this.setIcon(tileIcon);
        this.setPreferredSize(new Dimension(23, 23));
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setFocusPainted(false);
        this.setFocusable(false);
    }

    public boolean isQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(boolean q) {
        this.questionMark = q;
        this.setEnabled(!questionMark);
    }

    public void clear() {
        this.mine = false;
        this.flag = false;
        this.helperFlag = false;
        this.questionMark = false;
        this.revealed = false;
        this.setEnabled(true);
        this.setSelected(false);

    }
}
