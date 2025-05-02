
import java.util.*;

public class Minesweeper {

    private final int rows;
    private final int cols;
    private final int totalMines;
    private boolean[][] mineField;      // 儲存地雷位置
    private int[][] mineCountField;     // 每格周圍的地雷數 (-1 表示該格是地雷)
    private boolean isFirstClick = true;

    public Minesweeper(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = Math.min(totalMines, rows * cols - 1); // 限制地雷數量不能覆蓋全部格子
        this.mineField = new boolean[rows][cols];
        this.mineCountField = new int[rows][cols];
    }

    // 產生地雷盤，排除首次點擊位置
    public void generateBoard(int firstClickRow, int firstClickCol) {
        if (firstClickRow < 0 || firstClickRow >= rows || firstClickCol < 0 || firstClickCol >= cols) {
            throw new IllegalArgumentException("首次點擊座標不在棋盤範圍內");
        }

        // 重設棋盤
        mineField = new boolean[rows][cols];
        mineCountField = new int[rows][cols];

        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // 避免放在首次點擊格與重複放置地雷
            if (!mineField[row][col] && (row != firstClickRow || col != firstClickCol)) {
                mineField[row][col] = true;
                minesPlaced++;
            }
        }

        // 計算每格的地雷數
        calculateMineCounts();
    }

    // 計算每格周圍的地雷數量
    private void calculateMineCounts() {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1}; // 8個方向的 x 位移
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1}; // 8個方向的 y 位移

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mineField[i][j]) {
                    mineCountField[i][j] = -1; // 該格為地雷
                } else {
                    int count = 0;
                    for (int k = 0; k < 8; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];

                        // 判斷邊界與鄰近地雷
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && mineField[ni][nj]) {
                            count++;
                        }
                    }
                    mineCountField[i][j] = count;
                }
            }
        }
    }

    // 列印棋盤
    public void printBoard(boolean showMines) {
        System.out.println("  " + String.join(" ", Collections.nCopies(cols, "-")));
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                if (showMines && mineField[i][j]) {
                    System.out.print("* "); // 顯示地雷
                } else if (showMines) {
                    System.out.print(mineCountField[i][j] + " "); // 顯示地雷數
                } else {
                    System.out.print(". "); // 顯示未揭示狀態
                }
            }
            System.out.println("|");
        }
        System.out.println("  " + String.join(" ", Collections.nCopies(cols, "-")));
    }

    // 取得所有地雷的位置
    public List<int[]> getMinePositions() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mineField[i][j]) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    // 取得某格的周圍地雷數量
    public int getMineCount(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("座標不在棋盤範圍內");
        }
        return mineCountField[row][col];
    }

    // 判斷某格是否是地雷
    public boolean isMine(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("座標不在棋盤範圍內");
        }
        return mineField[row][col];
    }

    // Getter 方法
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    // 測試主程式
    public static void main(String[] args) {
        System.out.println("測試案例1：10x10棋盤，15個地雷，首次點擊(3, 4)");
        Minesweeper game1 = new Minesweeper(10, 10, 15);
        game1.generateBoard(3, 4);
        game1.printBoard(true);

        System.out.println("\n測試案例2：5x5棋盤，5個地雷，首次點擊(0, 0)");
        Minesweeper game2 = new Minesweeper(5, 5, 5);
        game2.generateBoard(0, 0);
        game2.printBoard(true);

        System.out.println("\n測試案例3：3x3棋盤，8個地雷（最大可能值），首次點擊(1, 1)");
        Minesweeper game3 = new Minesweeper(3, 3, 8);
        game3.generateBoard(1, 1);
        game3.printBoard(true);

        System.out.println("\n驗證首次點擊位置(1, 1)沒有地雷: " + !game3.isMine(1, 1));
    }
}
