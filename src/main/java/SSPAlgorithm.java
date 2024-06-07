import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import java.awt.Color;
// modified SSPAlgorithm, incorporating double set single point strategy
public class SSPAlgorithm {
    
    private int fieldX;
    private int fieldY;
    private TileButton baseTile;
    private int adjacentUnexploredCount;
    private int adjacentMineCount;
    private int adjacentFlagCount;
    private boolean hintFound;
    private int unrevealedTiles;
    private int helperFlags;

    public void analyzeMap(Solver solver, MineField mineField) {
        List<Integer> keysToRemove = new ArrayList<>();
        for (int pos : solver.getPosList()) {
            initializeVariables(mineField, pos);
            boolean amn = adjacentMineCount == adjacentUnexploredCount + adjacentFlagCount;

            boolean afn = adjacentMineCount == adjacentFlagCount;
            if (amn || afn) {
                    processTile(baseTile, solver, mineField);
                    keysToRemove.add(pos);
                    solver.setSuccessful(true);
                    break;
                }
            }
        
        solver.getLastCheckedOuterTiles().removeAll(keysToRemove);
    }

    private void processTile(TileButton tile,  Solver solver, MineField mineField) {
        for (TileButton adjacentTile : mineField.getAdjacentTiles(tile)) {
            if (adjacentTile.isEnabled()) {    
                if (!flagging(adjacentTile, solver)){
                    explore(adjacentTile, solver);
                }
            }
        }
    }

    private void processHelperTile(TileButton tile,  Helper helper, MineField mineField, int pos) {
        for (TileButton adjacentTile : mineField.getAdjacentTiles(tile)){
            if (adjacentTile.isRevealed() == false){
                adjacentTile.setRevealed(true);
                if (!helperFlagging(adjacentTile, helper, pos)) {
                    reveal(adjacentTile, helper, pos);
                }
            }
        }
    }

    private boolean flagging(TileButton adjacentTile, Solver solver) {
        boolean state = adjacentMineCount != adjacentFlagCount;
        if (state) {
            adjacentTile.setEnabled(false);
            adjacentTile.setFlag(true);
            solver.setTotalFlagCount(solver.getTotalFlagCount() + 1);
        }
        return state;
    }

    private boolean helperFlagging(TileButton adjacentTile, Helper helper, int pos) {
        boolean state = adjacentMineCount != helperFlags;
        if (state) {
            if (adjacentTile.isFlag()) {
                adjacentTile.setHelperFlag(true);
            } 
            else {
                hintFound = true;
                adjacentTile.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                adjacentTile.setRevealed(false);
            }
        }
        return state;
    }

    private void explore(TileButton tile, Solver solver) {

        tile.setEnabled(false);
        int pos = tile.getTilePos()[0] * solver.getFieldY() + tile.getTilePos()[1];
        solver.getLastCheckedOuterTiles().add(pos);
        solver.setTotalExploredTilesCount(solver.getTotalExploredTilesCount() + 1);
    }

    private void reveal(TileButton adjacentTile, Helper helper, int pos) {
        if (adjacentTile.isEnabled() || adjacentTile.isFlag() || adjacentTile.isQuestionMark()) {
            hintFound = true;
            adjacentTile.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            adjacentTile.setRevealed(false);
        }
        else {
            helper.getLastCheckedOuterTiles().add(adjacentTile.getTilePos()[0] * helper.getFieldY() + adjacentTile.getTilePos()[1]);
            adjacentTile.setRevealed(true);
        }
    }
    
    private void initializeVariables(MineField mineField, int pos) {
        
        fieldX = (int) (pos / mineField.getFieldY());
        fieldY = pos %  mineField.getFieldY();
        baseTile = mineField.getTiles()[fieldX][fieldY];
        adjacentUnexploredCount = mineField.countUnexploredAdjacentTiles(baseTile);
        adjacentMineCount = mineField.countAdjacentMines(baseTile);
        adjacentFlagCount = mineField.countAdjacentFlags(baseTile);
    }


    public boolean getHint(Helper helper, MineField mineField) {
        //List<Integer> posToRemove = new ArrayList<>();
        hintFound = false;
        for (int pos : helper.getPosList()) {
            initializeVariables(mineField, pos);
            unrevealedTiles = mineField.countUnrevealedAdjacentTiles(baseTile);
            helperFlags = mineField.countAdjacentHelperFlags(baseTile);

            boolean amn = adjacentMineCount == unrevealedTiles + helperFlags;

            boolean afn = adjacentMineCount == helperFlags;
            if (amn || afn) {
                helper.getLastCheckedOuterTiles().remove(pos);
                processHelperTile(baseTile, helper, mineField, pos);
                if (hintFound) {
                    helper.getLastCheckedOuterTiles().add(pos);
                    // helper.getLastCheckedOuterTiles().removeAll(posToRemove);
                    baseTile.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                    return true;
                }
                helper.setSuccessful(true);
                break;
            }
        }
        
        //helper.getLastCheckedOuterTiles().removeAll(posToRemove);
    
        return false;
    }
}

