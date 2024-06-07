
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MineField {

    private IconManager iconManager;
    
    private int algorithmicDifficulty;
    private int minesTotal;
    private int fieldX;
    private int fieldY;
    private int exploredTileNumber;

    private TileButton[][] tiles;

    private boolean fieldFailed;
    private boolean fieldWon;

    private Border originalBorder;

    
    public MineField(int height, int width, int minesTotal, int algorithmicDifficulty) {
        this.iconManager = new IconManager();

        this.exploredTileNumber = 0;
        this.fieldX = height;
        this.fieldY = width;
        this.fieldFailed = false;
        this.fieldWon = false;
        this.minesTotal = minesTotal;
        this.tiles = new TileButton[fieldX][fieldY];
        this.iconManager.loadNumbers(23);
        this.iconManager.loadSymbols(23);
        this.algorithmicDifficulty = algorithmicDifficulty;
    }


    public void initializeTiles() {

        originalBorder = tiles[0][0].getBorder();
        int rowIndex = 0;
        for (TileButton[] tileRow : tiles) {
            int colIndex = 0;
            for (TileButton tile : tileRow) {
                tile.clear();
                tile.setTilePos(new int[]{rowIndex, colIndex});
                tile.setDisabledIcon(iconManager.getTileIcon());
                tile.setBorder(originalBorder);
                colIndex++;
            }
            rowIndex++;
        }
    }

    public void generateField(TileButton clickedTile) {
        if (exploredTileNumber > 0) {
            exploredTileNumber++;
            return;
        }
        exploredTileNumber++;
        while(true) {
            initializeTiles();
            Solver solver = new Solver(algorithmicDifficulty);
            int[] startPosition = clickedTile.getTilePos();
            clickedTile.setSelected(true);
            clickedTile.setEnabled(false);

            if (algorithmicDifficulty == -1) {
                // classic random start
                generateAnyStart(startPosition);
            }
            else {
                generateZeroStart(startPosition);
            }
            if (solver.solve(this, startPosition[0] * fieldY + startPosition[1])){
                cleanAllTiles();
                tiles[startPosition[0]][startPosition[1]].setEnabled(false);
                String minePositions = "";
                for (TileButton tile[] : tiles) {
                    for (TileButton onetile : tile) {
                        if (onetile.isMine()) {
                            minePositions += "{" + onetile.getTilePos()[0] + "," + onetile.getTilePos()[1] + "},";
                        }
                    }
                }
                System.out.println("{" + startPosition[0] + "," + startPosition[1] + "}");
                System.out.println(minePositions);
                break;
            }
        }

    }

    public boolean generateAndSolveTestField(int[] firstTilePos, int[][] minePositions) {
        
        exploredTileNumber = 1;
        // adjustable difficulty
        algorithmicDifficulty = -2;
        while(true) {
            initializeTiles();
            Solver solver = new Solver(algorithmicDifficulty);
            for (TileButton tile[] : tiles) {
                for (TileButton onetile : tile) {
                    if (onetile.getTilePos()[0] == firstTilePos[0] && onetile.getTilePos()[1] == firstTilePos[1]){
                        onetile.setSelected(true);
                        onetile.setEnabled(false);
                    }
                }
            }
            generateCustomField(minePositions);

            return solver.solve(this, firstTilePos[0] * fieldY + firstTilePos[1]);
        }

    }
    private void generateCustomField(int[][] minePositions){
        for (int[] pos : minePositions) {
            tiles[pos[0]][pos[1]].setMine(true);
        }
    }
    
    private void generateAnyStart(int[] startPosition) {
        Random random = new Random();
        int currentMinesTotal = 0;
    	while (true) {
            TileButton randTile = tiles[random.nextInt(fieldX)][random.nextInt(fieldY)];

            boolean isStartPosition = randTile == tiles[startPosition[0]][startPosition[1]];
            boolean isAlreadyMine = randTile.isMine();
            
            if (!isStartPosition && !isAlreadyMine) {
                randTile.setMine(true);
                currentMinesTotal++;
            }
            if (currentMinesTotal == minesTotal) {
                break;
            }
        }
	}

    private void generateZeroStart(int[] startPosition) {

        Random random = new Random();
        int currentMinesTotal = 0;
        while (true) {

            int[] randPosition = new int[]{random.nextInt(fieldX), random.nextInt(fieldY)};

            TileButton randTile = tiles[randPosition[0]][randPosition[1]];
                
            boolean isZeroStartPosition = Math.abs(randPosition[0] - startPosition[0]) <= 1 
                                        && Math.abs(randPosition[1] - startPosition[1]) <= 1;
            
            if (!isZeroStartPosition && !randTile.isMine()) {
                randTile.setMine(true);
                currentMinesTotal++;
            }
       
            if (currentMinesTotal == minesTotal) {
                break;
            }
        }
    }

	public void undoLastMove() {
        for (TileButton[] tileRow : tiles) {
            for (TileButton tile : tileRow) {
                if (tile.isMine() && !tile.isEnabled() && !tile.isFlag() && !tile.isQuestionMark()) {
                    tile.setEnabled(true);
                    tile.setSelected(false);
                    tile.setFlag(false);
                    tile.setQuestionMark(false);
                    tile.setDisabledIcon(iconManager.getTileIcon());
                }
            }
        }
        fieldFailed = false;
        exploredTileNumber--;
    }
    

    public void disableField() {
        for (TileButton[] tileRow : tiles) {
            for (TileButton tile : tileRow) {
                tile.setEnabled(false);
                tile.setSelected(true);
            }
        }
    }


    public void revealField() {
        ImageIcon icon;
        if (fieldWon) {
            icon = iconManager.getFlagIcon();
        }
        else {
            icon = iconManager.getMineIcon();
        }
        for (TileButton[] tileRow : tiles) {
            for (TileButton tile : tileRow) {
                if (tile.isMine() && tile.isEnabled() && !tile.isQuestionMark()){
                    tile.setDisabledIcon(icon);
                }
                if (!tile.isMine()) {
                    if (tile.isEnabled() || tile.isFlag() || tile.isQuestionMark()){
                        int mineCount = countAdjacentMines(tile);
                        iconManager.setDisabledIconNumber(tile, mineCount);
                    }
                }
            }
        }
    }


    public ArrayList<TileButton> getAdjacentTiles(TileButton tile) {
        ArrayList<TileButton> adjacentTiles = new ArrayList<>();
        int tileX = tile.getTilePos()[0];
        int tileY = tile.getTilePos()[1];
        
        for (TileButton[] tileRow : tiles) {
            for (TileButton adjacentTile : tileRow) {
                int[] adjacentTilePosition = adjacentTile.getTilePos();
                Boolean isSameTile = tile == adjacentTile;
                Boolean isAdjacentTile = Math.abs(adjacentTilePosition[0] - tileX) <= 1 && Math.abs(adjacentTilePosition[1] - tileY) <= 1;
                if (isAdjacentTile && !isSameTile) {
                    adjacentTiles.add(adjacentTile);
                }
            }
        }
        return adjacentTiles;
    }

    public int countAdjacentMines(TileButton tile) {
        int adjacentMines = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if (adjacentTile.isMine()) {
                adjacentMines++;
            }
        }
        return adjacentMines;
    }

    public int countUnexploredAdjacentTiles(TileButton tile) {
        int unexplored = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if (adjacentTile.isEnabled()) {
                unexplored++;
            }
        }
        return unexplored;
    }

    public int countUnrevealedAdjacentTiles(TileButton tile) {
        int unrevealed = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if (!adjacentTile.isRevealed()) {
                unrevealed++;
            }
        }
        return unrevealed;
    }

    public int countAdjacentFlags(TileButton tile) {
        int flags = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if (adjacentTile.isFlag()) {
                flags += 1;
            }
        }
        return flags;
    }

    public int countAdjacentHelperFlags(TileButton tile) {
        int helperFlags = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if (adjacentTile.isHelperFlag()) {
                helperFlags++;
            }
        }
        return helperFlags;
    }

    public int countAdjacentTilesForFlagging(TileButton tile) {
        int adjacentTilesFlags = 0;
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
            if ((adjacentTile.isEnabled() || adjacentTile.isFlag() || adjacentTile.isQuestionMark())){
                adjacentTilesFlags++;
            }
        }
        return adjacentTilesFlags;
    }

    public void explore(TileButton tile) {

        generateField(tile);

        if (!tile.isMine()) {
            successfulTurn(tile);
        }
        else {
            failedTurn(tile);
        } 
    }

    public void successfulTurn(TileButton tile) {
        int mineCount = countAdjacentMines(tile);
        iconManager.setDisabledIconNumber(tile, mineCount);
        
        if (!gameWon() && mineCount == 0) {
            exploreAdjacentTiles(tile);
        }
    }

    private boolean gameWon() {
        return fieldWon = exploredTileNumber + minesTotal == fieldX * fieldY;
    }

    public void failedTurn(TileButton tile) {
        tile.setDisabledIcon(iconManager.getMineIcon());
        fieldFailed = true;
    }

    public void questionMark(TileButton tile) {
        tile.setQuestionMark(true);
        tile.setDisabledIcon(iconManager.getQuestionMarkIcon());
    }
    public void unQuestionMark(TileButton tile) {
        tile.setQuestionMark(false);
    }
    

    public void exploreAdjacentTiles(TileButton tile) {
        if (countAdjacentMines(tile) == countAdjacentFlags(tile)) {
            ArrayList<TileButton> adjacentTiles = getAdjacentTiles(tile);
            for (int i = 0; i < adjacentTiles.size(); i++) {
                if (adjacentTiles.get(i).isEnabled()) {
                    adjacentTiles.get(i).setEnabled(false);
                    adjacentTiles.get(i).setSelected(true);
                    explore(adjacentTiles.get(i));
                }
            }
        }
    }
    public int flagAdjacentTiles(TileButton tile) {
        int flaggedCount = 0;
        if (countAdjacentMines(tile) == countAdjacentTilesForFlagging(tile)) {
            for (TileButton adjacentTile : getAdjacentTiles(tile)) {
                if ((adjacentTile.isEnabled() && !adjacentTile.isSelected()) || adjacentTile.isQuestionMark()) {
                    if (!adjacentTile.isFlag()) {
                        if (adjacentTile.isQuestionMark()) {
                            unQuestionMark(adjacentTile);
                        }
                        adjacentTile.setFlag(true);
                        adjacentTile.setEnabled(false);
                        adjacentTile.setDisabledIcon(iconManager.getFlagIcon());
                        checkForExcessiveFlags(adjacentTile);
                        flaggedCount--;
                    }
                }
            }
        }
        return flaggedCount;
    }
    public void checkForExcessiveFlags(TileButton tile) {
        for (TileButton adjacentTile : getAdjacentTiles(tile)) {
                if (!adjacentTile.isEnabled() && !adjacentTile.isFlag() && !adjacentTile.isQuestionMark()) {
                    int mineCount = countAdjacentMines(adjacentTile);
                    int flagCount = countAdjacentFlags(adjacentTile);
                    if (mineCount < flagCount) {
                        
                        adjacentTile.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                    else if (mineCount == flagCount) {
                        adjacentTile.setBorder(originalBorder);
                    }
                }
        }
    }
    public void resetBorders() {
        for (int row = 0; row < fieldX; row++) {
            for (int col = 0; col < fieldY; col++) {
                tiles[row][col].setBorder(originalBorder);
            }
        }
    }
    private void cleanAllTiles() {
        for (TileButton[] tileRow : tiles) {
            for (TileButton tile : tileRow) {
                tile.setEnabled(true);
                tile.setFlag(false);
            }
        }
    }
}
