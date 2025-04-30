
import java.util.*;

public class Minesweeper {

    private final int rows;
    private final int cols;
    private final int totalMines;
    private boolean[][] mineField;
    private int[][] mineCountField;
    private boolean isFirstClick = true;

    public Minesweeper(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = Math.min(totalMines, rows * cols - 1); // 確保地雷數不超過可用格子數
        this.mineField = new boolean[rows][cols];
        this.mineCountField = new int[rows][cols];
    }

    public void generateBoard(int firstClickRow, int firstClickCol) {
        // 確保輸入合法
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

            // 確保首次點擊位置不是地雷
            if (!mineField[row][col] && (row != firstClickRow || col != firstClickCol)) {
                mineField[row][col] = true;
                minesPlaced++;
            }
        }

        // 計算每個格子周圍的地雷數
        calculateMineCounts();
    }

    private void calculateMineCounts() {
        // 定義八個方向的偏移量
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mineField[i][j]) {
                    // 如果是地雷，標記為-1
                    mineCountField[i][j] = -1;
                } else {
                    // 計算周圍的地雷數
                    int count = 0;
                    for (int k = 0; k < 8; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];

                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && mineField[ni][nj]) {
                            count++;
                        }
                    }
                    mineCountField[i][j] = count;
                }
            }
        }
    }

    public void printBoard(boolean showMines) {
        System.out.println("  " + String.join(" ", Collections.nCopies(cols, "-")));
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                if (showMines && mineField[i][j]) {
                    System.out.print("* ");
                } else if (showMines) {
                    System.out.print(mineCountField[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("|");
        }
        System.out.println("  " + String.join(" ", Collections.nCopies(cols, "-")));
    }

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

    public int getMineCount(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("座標不在棋盤範圍內");
        }
        return mineCountField[row][col];
    }

    public boolean isMine(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("座標不在棋盤範圍內");
        }
        return mineField[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public static void main(String[] args) {
        // 測試案例1：10x10棋盤，15個地雷，首次點擊(3, 4)
        System.out.println("測試案例1：10x10棋盤，15個地雷，首次點擊(3, 4)");
        Minesweeper game1 = new Minesweeper(10, 10, 15);
        game1.generateBoard(3, 4);
        game1.printBoard(true);

        // 測試案例2：5x5棋盤，5個地雷，首次點擊(0, 0)
        System.out.println("\n測試案例2：5x5棋盤，5個地雷，首次點擊(0, 0)");
        Minesweeper game2 = new Minesweeper(5, 5, 5);
        game2.generateBoard(0, 0);
        game2.printBoard(true);

        // 測試案例3：邊界情況，地雷數接近最大值
        System.out.println("\n測試案例3：3x3棋盤，8個地雷（最大可能值），首次點擊(1, 1)");
        Minesweeper game3 = new Minesweeper(3, 3, 8);
        game3.generateBoard(1, 1);
        game3.printBoard(true);

        // 驗證首次點擊位置沒有地雷
        System.out.println("\n驗證首次點擊位置(1, 1)沒有地雷: " + !game3.isMine(1, 1));
    }
}
