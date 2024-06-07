import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Tester {

      private static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
      
      public void startTests(MineField mineField){
        mineField.initializeTiles();
        out.println("\n");
        out.println("------------------------------------------------------------");
        out.println("\u001B[33mSpúšťam testy pre automatizovaného riešiteľa a pomocníka...\u001B[0m");
        out.println("Vyriešiteĺné pozície:");
        // No patterns position test
        int[] firstPos = {0,1};
        int[][] minePositions = {{0,5},{1,5},{2,3},{2,7},{3,4},{4,3},{4,6},{6,3},{6,5},{7,4}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre jednoduché označenie mín boli \u001B[32múspešné.\u001B[0m");
        // Uncover safe tiles test
        firstPos = new int[]{0,2};
        minePositions = new int[][]{{0,4},{2,4},{3,4},{3,6},{5,6},{6,0},{6,5},{7,0},{7,2},{7,7}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre odhalenie bezpečných políčok boli \u001B[32múspešné.\u001B[0m");
        // 1-1-X pattern test
        firstPos = new int[]{6,0};
        minePositions = new int[][]{{0,3},{1,3},{1,5},{3,7},{4,0},{4,3},{5,5},{6,5},{7,2},{7,6}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre 1-1-X vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-X pattern test
        firstPos = new int[]{3,2};
        minePositions = new int[][]{{0,3},{0,5},{2,5},{3,4},{3,7},{4,6},{6,3},{7,3},{7,5},{7,6}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre 1-2-X vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-1 pattern test
        firstPos = new int[]{7,7};
        minePositions = new int[][]{{0,7},{2,6},{3,3},{3,4},{4,3},{4,7},{6,3},{6,5},{7,1},{7,4}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre 1-2-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-2-1 pattern test
        firstPos = new int[]{5,4};
        minePositions = new int[][]{{0,1},{1,5},{1,6},{2,0},{2,4},{3,0},{3,1},{3,2},{3,6},{4,6}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre 1-2-2-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-3-1 pattern test
        firstPos = new int[]{1,6};
        minePositions = new int[][]{{1,4},{2,1},{4,3},{4,6},{5,0},{5,4},{5,7},{6,2},{7,0},{7,5}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre 1-3-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // Mine reduction test
        firstPos = new int[]{3,5};
        minePositions = new int[][]{{1,2},{1,5},{1,7},{4,2},{5,4},{5,6},{5,7},{6,5},{6,6},{7,0}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre redukciu mín boli \u001B[32múspešné.\u001B[0m");
        // Pattern reduction test
        firstPos = new int[]{2,7};
        minePositions = new int[][]{{0,4},{0,7},{1,1},{3,4},{4,2},{5,1},{5,6},{6,6},{7,2},{7,7}};
        assert mineField.generateAndSolveTestField(firstPos, minePositions);
        out.println("Testy pre redukciu vzorov boli \u001B[32múspešné.\u001B[0m");
        // add one more test
        // All tests passed
        out.println("\u001B[32mVšetky testy (10) prebehli úspešne.\u001B[0m");
        out.println("------------------------------------------------------------");
        mineField.initializeTiles();

        //
        out.println("------------------------------------------------------------");
        out.println("\u001B[33mSpúšťam testy pre automatizovaného riešiteľa a pomocníka...\u001B[0m");
        out.println("Nevyriešiteĺné pozície:");
        // No patterns position test
        firstPos = new int[]{1,4};
        minePositions = new int[][]{{0,1},{0,5},{1,5},{2,1},{3,1},{4,2},{4,6},{6,4},{7,3},{7,6}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatné jednoduché označenie mín boli \u001B[32múspešné.\u001B[0m");
        mineField.initializeTiles();
        // Uncover safe tiles test
        firstPos = new int[]{5,5};
        minePositions = new int[][]{{0,0},{0,6},{1,2},{1,5},{2,3},{3,4},{3,6},{4,0},{4,6},{5,6}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatné odhalenie bezpečných políčok boli \u001B[32múspešné.\u001B[0m");
        // 1-1-X pattern test
        firstPos = new int[]{2,3};
        minePositions = new int[][]{{0,3},{0,4},{0,6},{1,2},{3,7},{4,1},{4,2},{6,0},{6,4},{7,0}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatný 1-1-X vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-X pattern test
        mineField.initializeTiles();
        firstPos = new int[]{1,4};
        minePositions = new int[][]{{1,1},{2,5},{3,2},{3,6},{4,2},{5,1},{5,6},{6,6},{7,0},{7,4}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatný 1-2-X vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-1 pattern test
        firstPos = new int[]{3,3};
        minePositions = new int[][]{{1,3},{1,5},{2,0},{3,5},{3,6},{4,2},{5,7},{6,1},{6,6},{7,6}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatný 1-2-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-2-2-1 pattern test
        firstPos = new int[]{4,2};
        minePositions = new int[][]{{0,1},{0,6},{1,2},{1,3},{2,0},{2,4},{3,4},{3,5},{4,7},{5,1}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatný 1-2-2-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // 1-3-1 pattern test
        firstPos = new int[]{3,6};
        minePositions = new int[][]{{0,2},{1,0},{1,1},{1,3},{2,7},{4,1},{6,1},{6,5},{6,6},{7,1}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatný 1-3-1 vzor boli \u001B[32múspešné.\u001B[0m");
        // Mine reduction test
        firstPos = new int[]{7,4};
        minePositions = new int[][]{{0,3},{2,0},{2,2},{3,0},{3,5},{4,5},{6,5},{6,7},{7,1},{7,2}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatnú redukciu mín boli \u001B[32múspešné.\u001B[0m");
        // Pattern reduction test
        firstPos = new int[]{7,0};
        minePositions = new int[][]{{0,0},{0,5},{2,7},{3,5},{3,6},{4,4},{5,3},{6,1},{6,7},{7,4}};
        assert !(mineField.generateAndSolveTestField(firstPos, minePositions));
        out.println("Testy pre neplatnú redukciu vzorov boli \u001B[32múspešné.\u001B[0m");
        // add one more test
        // All tests passed
        out.println("\u001B[32mVšetky testy (10) prebehli úspešne.\u001B[0m");
        out.println("------------------------------------------------------------");
        mineField.initializeTiles();
    }
}