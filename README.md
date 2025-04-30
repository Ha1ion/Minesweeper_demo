# 踩地雷模組

這是一個踩地雷遊戲的核心邏輯模組，提供生成棋盤與處理第一次點擊的功能。此模組專注於地雷的放置與確保第一次點擊的安全規則，可作為踩地雷遊戲的基礎組件。

## 功能特點

- 創建指定大小和地雷數量的棋盤
- 確保第一次點擊位置不會是地雷
- 計算每個格子周圍的地雷數
- 提供棋盤資訊的存取方法

## 環境需求

- Java 8 或更高版本
- 或 Docker (用於容器化運行)

## 如何使用

### 方法一：直接使用 Java 運行

#### 編譯及執行

```bash
# 編譯
javac Minesweeper.java

# 執行
java Minesweeper
```

### 方法二：使用 Docker 運行

如果您的系統上沒有安裝 Java 環境，可以使用 Docker 來運行此模組：

```bash
# 構建 Docker 映像
docker build -t minesweeper-game .

# 運行 Docker 容器
docker run --rm minesweeper-game
```

或者使用 docker-compose：

```bash
# 使用 docker-compose 構建和運行
docker-compose up --build
```

### 將模組整合到您的專案

1. 將 `Minesweeper.java` 複製到您的專案目錄
2. 創建 `Minesweeper` 物件，設定行數、列數和地雷數量
3. 呼叫 `generateBoard` 方法，傳入第一次點擊的座標
4. 使用提供的方法來獲取棋盤資訊

### 使用範例

```java
// 創建一個 10x10 的棋盤，包含 15 個地雷
Minesweeper game = new Minesweeper(10, 10, 15);

// 生成棋盤，第一次點擊位置為 (3, 4)
game.generateBoard(3, 4);

// 獲取地雷位置列表
List<int[]> minePositions = game.getMinePositions();

// 檢查特定位置是否為地雷
boolean isMine = game.isMine(5, 6);

// 獲取特定位置周圍的地雷數量
int mineCount = game.getMineCount(2, 2);

// 打印棋盤
game.printBoard(true); // 顯示地雷
```

## API 說明

### 建構函數

- `Minesweeper(int rows, int cols, int totalMines)` - 創建新的踩地雷遊戲

### 主要方法

- `void generateBoard(int firstClickRow, int firstClickCol)` - 生成棋盤，確保首次點擊位置不是地雷
- `List<int[]> getMinePositions()` - 獲取所有地雷的位置列表
- `boolean isMine(int row, int col)` - 檢查指定位置是否為地雷
- `int getMineCount(int row, int col)` - 獲取指定位置周圍的地雷數量
- `void printBoard(boolean showMines)` - 打印棋盤，可選擇是否顯示地雷

### 其他方法

- `int getRows()` - 獲取棋盤行數
- `int getCols()` - 獲取棋盤列數
- `int getTotalMines()` - 獲取總地雷數

## 測試

模組包含多個測試案例，運行 `main` 方法可以查看不同情境下的棋盤生成結果，並驗證第一次點擊的安全性。

## Docker 容器說明

- `Dockerfile` - 定義了建構 Docker 映像的步驟
- `entrypoint.sh` - 容器啟動時執行的腳本
- `docker-compose.yml` - 簡化 Docker 容器的構建和運行 