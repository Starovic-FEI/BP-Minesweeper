import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.BorderFactory;
import java.awt.Color;

public class CSPAlgorithm {
    
    private int fieldX;
    private int fieldY;
    private TileButton baseTile;
    private int adjacentMineCount;
    private int adjacentFlagCount;
    private int helperFlags;

    public void analyzeMap(Solver solver, MineField mineField) {
        
        HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet = generateRules(solver, mineField);
        boolean algorithmSuccess = true;
        while (algorithmSuccess) {
            algorithmSuccess = false;
            algorithmSuccess = false;
            SimpleEntry<Integer, ArrayList<Integer>>[] simpleList;
            simpleList = newHashSet.toArray(new SimpleEntry[newHashSet.size()]);
            for (SimpleEntry<Integer, ArrayList<Integer>> simple1 : simpleList) {
                for (SimpleEntry<Integer, ArrayList<Integer>> simple2 : simpleList) {
                    if (!simple1.equals(simple2)) {
                        boolean containsAll = simple2.getValue().containsAll(simple1.getValue());
                        boolean containsMore = simple1.getValue().size() < simple2.getValue().size();
                        if (containsAll && containsMore) {
                            ArrayList<Integer> diffList = getUniqueValues(simple1, simple2);
                            if (updateUniqueValues(newHashSet, simple1, simple2, diffList)) {
                                algorithmSuccess = true;
                            }
                            else {
                                if(patternCheck(newHashSet, simple1, simple2))
                                algorithmSuccess = true;
                       }
                   }
                }
            }

        }
    }
        checkForUpdates(newHashSet, solver, mineField);
    }

    private HashSet<SimpleEntry<Integer, ArrayList<Integer>>> generateRules(Solver solver, MineField mineField) {
        HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet = new HashSet<SimpleEntry<Integer, ArrayList<Integer>>>();
        for (int pos : solver.getPosList()) {
            initializeVariables(mineField, pos);
            adjacentFlagCount = mineField.countAdjacentFlags(baseTile);
            SimpleEntry<Integer, ArrayList<Integer>> tileRules = createTileRulesOrHints(mineField, true);
            if (!tileRules.getValue().isEmpty()) {
                newHashSet.add(tileRules);
            }
        }
        return newHashSet;
    }

    private HashSet<SimpleEntry<Integer, ArrayList<Integer>>> generateHelperRules(Helper helper, MineField mineField) {
        
        HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet = new HashSet<SimpleEntry<Integer, ArrayList<Integer>>>();
        for (int pos : helper.getPosList()) {
            initializeVariables(mineField, pos);
            helperFlags = mineField.countAdjacentHelperFlags(baseTile);

            SimpleEntry<Integer, ArrayList<Integer>> tiles = createTileRulesOrHints(mineField, false);
            if (!tiles.getValue().isEmpty()) {
                newHashSet.add(tiles);
            }
        }
        return newHashSet;
    }

    private SimpleEntry<Integer, ArrayList<Integer>> createTileRulesOrHints(MineField mineField, boolean isRule) {
        int safeTiles = isRule ? adjacentMineCount - adjacentFlagCount : adjacentMineCount - helperFlags;
        SimpleEntry<Integer, ArrayList<Integer>> tileData = new SimpleEntry<Integer, ArrayList<Integer>>(safeTiles, new ArrayList<Integer>());
        for (TileButton adjacentTile : mineField.getAdjacentTiles(baseTile)) {
            if ((isRule && adjacentTile.isEnabled()) || (!isRule && !adjacentTile.isRevealed())) {
                int[] position = adjacentTile.getTilePos();
                int index = position[0] * mineField.getFieldY() + position[1];
                tileData.getValue().add(index);
            }
        }
        return tileData;
    }



    private ArrayList<Integer> getUniqueValues(SimpleEntry<Integer, ArrayList<Integer>> list1, SimpleEntry<Integer, ArrayList<Integer>> list2){
        ArrayList<Integer> uniqueL = new ArrayList<Integer>();
        for (int val : list2.getValue()) {
            if (!list1.getValue().contains(val)) {
                uniqueL.add(val);
            }
        }
        return uniqueL;
    }

    private boolean updateUniqueValues(HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet, SimpleEntry<Integer, ArrayList<Integer>> simple1, SimpleEntry<Integer, ArrayList<Integer>> simple2, ArrayList<Integer> uniqueL) {
        int minesSubstract = simple2.getKey() - simple1.getKey();
        SimpleEntry<Integer, ArrayList<Integer>> uniqueE = new SimpleEntry<Integer, ArrayList<Integer>>(minesSubstract, uniqueL);
        boolean updated = newHashSet.add(uniqueE);
        newHashSet.remove(simple2);
        return updated;
    }

    private void initializeVariables(MineField mineField, int pos) {
        fieldX = pos / mineField.getFieldY();
        fieldY = pos % mineField.getFieldY();
        baseTile = mineField.getTiles()[fieldX][fieldY];
        adjacentMineCount = mineField.countAdjacentMines(baseTile);
    }
    public boolean getHint(Helper helper, MineField mineField) {
   
            HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet = generateHelperRules(helper, mineField);
            boolean algorithmHelperSuccess = true;
            while (algorithmHelperSuccess) {
                algorithmHelperSuccess = false;
                SimpleEntry<Integer, ArrayList<Integer>>[] simpleList;
                simpleList = newHashSet.toArray(new SimpleEntry[newHashSet.size()]);
                for (SimpleEntry<Integer, ArrayList<Integer>> simple1 : simpleList) {
                    for (SimpleEntry<Integer, ArrayList<Integer>> simple2 : simpleList) {
                        if (!simple1.equals(simple2)) {
                            boolean containsAll = simple2.getValue().containsAll(simple1.getValue());
                            boolean containsMore = simple1.getValue().size() < simple2.getValue().size();
                            if (containsAll && containsMore) {
                                ArrayList<Integer> diffList = getUniqueValues(simple1, simple2);
                                if (updateUniqueValues(newHashSet, simple1, simple2, diffList)) {
                                    algorithmHelperSuccess = true;
                                }
                            }
                            else {
                                if (patternCheck(newHashSet, simple1, simple2)) {
                                    algorithmHelperSuccess = true;
                                }
                            }
                        }   
                    }
                }
            }
            return checkForHelperUpdates(newHashSet, helper, mineField);
        }
    



    private void checkForUpdates(HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet, Solver solver, MineField mineField) {
        
        for (SimpleEntry<Integer, ArrayList<Integer>> simples : newHashSet) {
            int mines = simples.getKey();
            if (mines == 0 || mines == simples.getValue().size()) {
                for (int tileIndex : simples.getValue()) {    
                    int fieldX = tileIndex / mineField.getFieldY();
                    TileButton tile = mineField.getTiles()[fieldX][tileIndex % mineField.getFieldY()];
                    if (tile.isEnabled()){
                        tile.setEnabled(false);
                        if (mines == 0) {
                            solver.getLastCheckedOuterTiles().add(tileIndex);
                            solver.setTotalExploredTilesCount(solver.getTotalExploredTilesCount() + 1);
                        } else {
                            tile.setFlag(true);
                            solver.setTotalFlagCount(solver.getTotalFlagCount() + 1);
                        }
                    }
                }
                solver.setSuccessful(true);
            }
        }
    }
    private void cleanSet(HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet) {
        SimpleEntry<Integer, ArrayList<Integer>>[] simpleList = newHashSet.toArray(new SimpleEntry[newHashSet.size()]);
        for (SimpleEntry<Integer, ArrayList<Integer>> simples : simpleList) {
            if (simples.getValue().isEmpty()) {
                newHashSet.remove(simples);
            }
        }
    }
    private boolean patternCheck(HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet, SimpleEntry<Integer, ArrayList<Integer>> simple1, SimpleEntry<Integer, ArrayList<Integer>> simple2){
        int size = newHashSet.size();
        ArrayList<Integer> list1 = simple1.getValue();
        ArrayList<Integer> list2 = simple2.getValue();
        ArrayList<Integer> diffList = new ArrayList<>(list2);
        diffList.removeAll(list1);
        if (diffList.size() == 1 && simple2.getKey() - simple1.getKey() == 1) {
            newHashSet.add(new SimpleEntry<>(simple2.getKey() - simple1.getKey(), diffList));
        }

        return newHashSet.size() > size;
    }

    private boolean checkForHelperUpdates(HashSet<SimpleEntry<Integer, ArrayList<Integer>>> newHashSet, Helper helper, MineField mineField) {
        // cleanSet(newHashSet);
        for (SimpleEntry<Integer, ArrayList<Integer>> simples : newHashSet) {
            int mines = simples.getKey();
            if (mines == 0 || mines == simples.getValue().size()) {
                List<TileButton> helperTiles = new ArrayList<>();
                for (int tileIndex : simples.getValue()) {    
                    int fieldX = tileIndex / mineField.getFieldY();
                    TileButton tile = mineField.getTiles()[fieldX][tileIndex % mineField.getFieldY()];
                    if (!tile.isRevealed()){
                        tile.setRevealed(true);
                        if (mines == 0) {
                            if (tile.isEnabled() || tile.isFlag() || tile.isQuestionMark()){
                                helper.setHintFound(true);
                                tile.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                                tile.setRevealed(false);
                            }
                            else {
                                helper.getLastCheckedOuterTiles().add(tileIndex);         
                            }                        
                        } else {
                            if (tile.isFlag()) {
                                tile.setHelperFlag(true);
                            }
                            else {
                                helper.setHintFound(true);
                                tile.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                                tile.setRevealed(false);
                            }
                        }
                    }
                    else {
                        helperTiles.add(tile);
                    }
                }
                if (helper.isHintFound()) {       
                    for (TileButton tile : helperTiles) {
                        //tile.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                    } 
                    return true;
                }
                helper.setSuccessful(true);
            }
        }
        return false;
    }

}
    