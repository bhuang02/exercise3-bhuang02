package spw4.connectfour;

public class ConnectFourImpl implements ConnectFour {
    private static final int BOARD_HEIGHT = 6;
    private static final int BOARD_WIDTH = 7;

    private Player playerOnTurn;
    private Player[][] board = new Player[BOARD_HEIGHT][BOARD_WIDTH];
    private boolean gameOver = false;

    public ConnectFourImpl(Player playerOnTurn) throws InvalidStartingPlayer {
        if (playerOnTurn == Player.red) {
            this.playerOnTurn = playerOnTurn;
        } else {
            throw new InvalidStartingPlayer("Error: Connect 4 game rules state that red should start first!");
        }
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = Player.none;
            }
        }
    }

    public Player getPlayerAt(int row, int col) {
        if (row >= 0 && row < BOARD_HEIGHT && col >= 0 && col < BOARD_WIDTH) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException("Error: not a valid position.");
        }
    }

    public Player getPlayerOnTurn() {
        return playerOnTurn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        Player player;

        // Check if array is full
        boolean full = true;

        boardSweep:
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                player = board[row][col];
                if (player == Player.none) {
                    full = false;
                    break boardSweep;
                }
            }
        }

        // Check all rows for a horizontal win
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col <= BOARD_WIDTH - 4; col++) {
                player = board[row][col];
                if (player != Player.none && player == board[row][col + 1] && player == board[row][col + 2] && player == board[row][col + 3]) {
                    gameOver = true;
                    return player;
                }
            }
        }

        // Check all columns for a vertical win
        for (int col = 0; col < BOARD_WIDTH; col++) {
            for (int row = 0; row <= BOARD_HEIGHT - 4; row++) {
                player = board[row][col];
                if (player != Player.none && player == board[row + 1][col] && player == board[row + 2][col] && player == board[row + 3][col]) {
                    gameOver = true;
                    return player;
                }
            }
        }

        // Check for diagonal win
        for (int row = 0; row <= BOARD_HEIGHT - 4; row++) {
            for (int col = 0; col <= BOARD_WIDTH - 4; col++) {
                player = board[row][col];
                if (player != Player.none && player == board[row + 1][col + 1] && player == board[row + 2][col + 2] && player == board[row + 3][col + 3]) {
                    gameOver = true;
                    return player;
                }
            }
        }

        // Check for diagonal win
        for (int row = 3; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col <= BOARD_WIDTH - 4; col++) {
                player = board[row][col];
                if (player != Player.none && player == board[row - 1][col + 1] && player == board[row - 2][col + 2] && player == board[row - 3][col + 3]) {
                    gameOver = true;
                    return player;
                }
            }
        }

        if (full) {
            gameOver = true;
        }
        return Player.none;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player: ").append(playerOnTurn.toString().toUpperCase()).append('\n');
        for (int r = 0; r < BOARD_HEIGHT; r++) {
            sb.append("|");
            for (int c = 0; c < BOARD_WIDTH; c++) {
                if (board[r][c] == Player.none) {
                    sb.append(" . ");
                } else if (board[r][c] == Player.red) {
                    sb.append(" R ");
                } else if (board[r][c] == Player.yellow) {
                    sb.append(" Y ");
                }
            }
            sb.append("|").append('\n');
        }
        return sb.toString();
    }

    public void reset(Player playerOnTurn) {
        if (playerOnTurn != Player.red) {
            throw new InvalidStartingPlayer("Error: Connect 4 game rules state that red should start first!");
        }

        this.playerOnTurn = playerOnTurn;
        this.gameOver = false;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = Player.none;
            }
        }
    }

    public void drop(int col) {
        if (col >= 0 && col < BOARD_WIDTH) {
            int row = 0;

            while (row < BOARD_HEIGHT && board[row][col] == Player.none) {
                row++;
            }

            if (row > 0 && row - 1 < BOARD_HEIGHT && board[row - 1][col] == Player.none) {
                board[row - 1][col] = playerOnTurn;
            } else {
                System.out.println("Error: Piece not dropped, column full.");
                return;
            }

            playerOnTurn = playerOnTurn == Player.red ? Player.yellow : Player.red; // swap players
        } else {
            System.out.println("Error: Invalid column.");
            return;
        }
        getWinner();
    }

    public Player[][] getBoard() {
        return board;
    }
}
