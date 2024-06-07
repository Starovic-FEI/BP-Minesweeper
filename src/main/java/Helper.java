import java.util.*;

import lombok.*;

@Getter
@Setter
public class Helper {

    private int fieldX;
    private int fieldY;
    private boolean isSuccessful;
    private boolean hintFound;
    private HashSet<Integer> lastCheckedOuterTiles;
    private int[] firstPosition;
    private Integer[] posList;
    private SSPAlgorithm sspAlgorithm;
    private CSPAlgorithm cspAlgorithm;

    public Helper() {
        lastCheckedOuterTiles = null;
        this.sspAlgorithm = new SSPAlgorithm();
        this.cspAlgorithm = new CSPAlgorithm();
    }

    public boolean requestHint(MineField mineField, TileButton firstClickedTile) {

        fieldX = mineField.getFieldX();
        fieldY = mineField.getFieldY();

        isSuccessful = false;
        hintFound = false;

        if (lastCheckedOuterTiles == null) {
            lastCheckedOuterTiles = new HashSet<Integer>(2 * (fieldX + fieldY));
            firstPosition = firstClickedTile.getTilePos();
            int firstTileIndex = firstPosition[0] * fieldY + firstPosition[1];
            lastCheckedOuterTiles.add(firstTileIndex);
        }


        while(true) {
            isSuccessful = false;
            posList = lastCheckedOuterTiles.toArray(new Integer[0]);
            if (sspAlgorithm.getHint(this, mineField)) {
                return true;
            }
            else if (isSuccessful) {
                continue;
            }
            else if (cspAlgorithm.getHint(this, mineField)) {
                return true;
            }
            else if (!isSuccessful) {
                return false;
            }
        }
    }
}