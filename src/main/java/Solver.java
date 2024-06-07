import java.util.*;

import lombok.*;

@Getter
@Setter
public class Solver {

    int algorithmicDifficulty;
    SSPAlgorithm sspAlgorithm;
    CSPAlgorithm cspAlgorithm;
    int fieldX;
    int fieldY;
    int mineNumber;
    boolean isSuccessful;
    int totalFlagCount;
    int totalExploredTilesCount;
    Integer[] posList;
    HashSet<Integer> lastCheckedOuterTiles;
    int cspUsed;


    public Solver(int algorithmicDifficulty) {
        this.algorithmicDifficulty = algorithmicDifficulty;
        this.sspAlgorithm = new SSPAlgorithm();
        this.cspAlgorithm = new CSPAlgorithm();
    }

    public boolean solve(MineField mineField, int clickedtileIndex) {
        if (algorithmicDifficulty == -1) {
            return true;
        }
        if (algorithmicDifficulty == 5 && mineField.getMinesTotal() != 99) {
            algorithmicDifficulty = 4;
        }
        initializeVariables(mineField);
        lastCheckedOuterTiles = new HashSet<Integer>(2 * (fieldX + fieldY));
        lastCheckedOuterTiles.add(clickedtileIndex);
        while (!(mineNumber == totalFlagCount 
                || fieldY * fieldX - totalExploredTilesCount == mineNumber)) {
            isSuccessful = false;
            posList = lastCheckedOuterTiles.toArray(new Integer[0]);
            sspAlgorithm.analyzeMap(this, mineField);
            if (isSuccessful) {
                continue;
            }
            else if (algorithmicDifficulty == 0) {
                return false;
            }
            cspUsed++;
            
            cspAlgorithm.analyzeMap(this, mineField);    
            
            if (!isSuccessful) {
                return false;
            }
        }
        if (cspUsed >= algorithmicDifficulty) {
            return true;
        }
        return false;
    }


    private void initializeVariables(MineField mineField) {
        fieldX = mineField.getFieldX();
        fieldY = mineField.getFieldY();
        mineNumber = mineField.getMinesTotal();
        isSuccessful = false;
        totalFlagCount = 0;
        totalExploredTilesCount = 1;
        cspUsed = 0;
    }
}


