import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconManager {
    private ImageIcon tileIcon;
    private ImageIcon mineIcon;
    private ImageIcon flagIcon;
    private ImageIcon questionMarkIcon;
    private ImageIcon plainIcon;
    private ImageIcon winIcon;
    private ImageIcon loseIcon;
    private ImageIcon surprisedIcon;

    private HashMap<Integer, ImageIcon> numberIcons;


    public static ImageIcon getScaledImageIcon(int width, int height, String dir) {
        ImageIcon imageIcon = new ImageIcon(MineField.class.getResource(dir));
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public void loadNumbers(int tileSize) {
        numberIcons = new HashMap<>();
        int i = 0;
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/empty.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/1-blue.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/2-green.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/3-red.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/4-purple.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/5-ruby.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/6-teal.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/7-black.png"));
        numberIcons.put(i++, getScaledImageIcon(tileSize, tileSize, "/images/tiles/8-grey.png"));


        tileIcon = getScaledImageIcon(tileSize, tileSize, "/images/tiles/uncovered.png");
        mineIcon = getScaledImageIcon(tileSize, tileSize, "/images/mine_red.png");

    }

    public void loadSymbols(int tileSize) {
        flagIcon = getScaledImageIcon(tileSize, tileSize, "/images/symbols/flag.png");
        questionMarkIcon = getScaledImageIcon(tileSize, tileSize, "/images/symbols/question_mark.png");
    }

    public void loadSpecialIcons(int restartSize, int tileSize) {
        plainIcon = getScaledImageIcon(restartSize, restartSize, "/images/smile_default1.png");
        winIcon = getScaledImageIcon(restartSize, restartSize, "/images/smile_win.png");
        loseIcon = getScaledImageIcon(restartSize, restartSize, "/images/smile_lose1.png");
        tileIcon = getScaledImageIcon(tileSize, tileSize, "/images/tiles/uncovered.png");
        surprisedIcon = getScaledImageIcon(restartSize, restartSize, "/images/smile_surprised.png");
    }

    public void resizeRestartIcons(int width, int height) {
        plainIcon = getScaledImageIcon(width, height, "/images/smile_default1.png");
        winIcon = getScaledImageIcon(width, height, "/images/smile_win.png");
        loseIcon = getScaledImageIcon(width, height, "/images/smile_lose1.png");
    }

    public void setDisabledIconNumber(TileButton tileButton, int mines) {
        tileButton.setDisabledIcon(numberIcons.get(mines));
    }
}