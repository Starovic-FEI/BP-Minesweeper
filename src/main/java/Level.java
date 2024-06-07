import javax.swing.JRadioButtonMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Level extends JRadioButtonMenuItem implements ActionListener{
    private int gridRow;
    private int gridColumn;
    private int mineNumber;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Level(String label, int gridRow, int gridColumn, int mineNumber) {
        this.label = label;
        setText(label);
        this.gridRow = gridRow;
        this.gridColumn = gridColumn;
        this.mineNumber = mineNumber;
    };

    public int getGridRow() {
        return gridRow;
    }
    
    public int getGridColumn() {
        return gridColumn;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    public void setGridRow(int gridRow) {
        this.gridRow = gridRow;
    } 

    public void setGridColumn(int gridColumn) {
        this.gridColumn = gridColumn;
    }

    public void setMineNumber(int mineNumber) {
        this.mineNumber = mineNumber;
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
