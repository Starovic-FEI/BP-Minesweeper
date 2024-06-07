
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class MultiCounter extends JPanel {

	private JLabel counterField;

	private Timer timeCount;


	private Font oldCustomFont;

	private Boolean isCounterTimer;
	private Boolean counterActive;


	private Color foregroundColor;
	private Color backgroundColor;

	private int currentVal;
	private int defaultVal;
	private int fontSize;
	private int timerWidth;
	private int timerHeight;

	public Timer getTimeCount() {
		return timeCount;
	}

	public void setCounterActive(Boolean counterActive) {
		this.counterActive = counterActive;
	}
	
	public void setCurrentVal(int currentVal) {
		setCounterFieldValue(currentVal);
		this.currentVal = currentVal;
	}

	public int getCurrentVal() {
		return currentVal;
	} 

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setTimerHeight(int timerHeight) {
		this.timerHeight = timerHeight;
	}

	public void setTimerWidth(int timerWidth) {
		this.timerWidth = timerWidth;
	}

	public Boolean isCounterActive() {
		return counterActive;
	}



	public MultiCounter(int defaultVal, boolean isTimer) throws FontFormatException, IOException {
		this.defaultVal = defaultVal;
		this.fontSize = 30;
		this.timerWidth = 52;
		this.timerHeight = 32;
		this.isCounterTimer = isTimer;
		this.backgroundColor = Color.black;
		this.foregroundColor = Color.red;
		initializeFont(fontSize);
		initializeCounterLabel();
		if(this.isCounterTimer) {
			timeCount = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					increaseValue();
				}
			});
		}
	}

	public void refreshCounter(int fontSize, int timerWidth, int timerHeight) {
		this.setFontSize(fontSize);
		this.setPreferredSize(new Dimension(timerWidth, timerHeight));
		this.setTimerWidth(timerWidth);
		this.setTimerHeight(timerHeight);
		this.counterField.setFont(oldCustomFont.deriveFont(Font.BOLD, fontSize));
		this.revalidate();
		this.repaint();
	}

	public void initializeFont(int fontSize) throws FontFormatException, IOException {

		oldCustomFont = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/oldMinesweeperFont.ttf"));

		oldCustomFont = oldCustomFont.deriveFont(Font.BOLD, fontSize);
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(oldCustomFont);
	}

	public void initializeCounterLabel(){
		String tmpText = String.format("%03d", defaultVal);
		counterField = new JLabel(tmpText);
		counterField.setForeground(foregroundColor);
		counterField.setFont(oldCustomFont);
		counterField.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
		this.setPreferredSize(new Dimension(timerWidth, timerHeight));
		this.setBackground(backgroundColor);
		this.add(counterField);
	
	}

	public void restart() {
		timeCount.stop();
		counterActive = false;
		setCurrentVal(defaultVal);
		setCounterFieldValue(defaultVal);
	}

	private void setCounterFieldValue(int value) {
		String tmpValue = String.format("%03d", value);
		counterField.setText(tmpValue);
	}

	public void increaseValue() {

		if (currentVal != 999) {
			setCounterFieldValue(++currentVal);
		}
		else {
			setCounterFieldValue(currentVal);
		}
	}

	public void decreaseValue() {
			setCounterFieldValue(--currentVal);
	}
}
