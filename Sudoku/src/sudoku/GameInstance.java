package sudoku;

public class GameInstance {

    public int row;
    public int column;
    public Cell cell;
    public Cell[][] board;

    public GameInstance(int row, int column, Cell cell, Cell[][] board) {
        this.row = row;
        this.column = column;
        this.cell = cell.getDefensiveCopy();
        this.board = getBoardDefensiveCopy(board);
    }

    private Cell[][] getBoardDefensiveCopy(Cell[][] board) {
        Cell[][] newBoard = new Cell[GameGen.GAMESIZE][GameGen.GAMESIZE];
        for(int r = 0; r < GameGen.GAMESIZE; r++) {
            for(int c = 0; c < GameGen.GAMESIZE; c++) {
                newBoard[r][c] = board[r][c].getDefensiveCopy();
            }
        }
        return newBoard;
    }

    public boolean isBoardFilled() {
        int count = 0;
        for(int r = 0; r < GameGen.GAMESIZE; r++) {
            for(int c = 0; c < GameGen.GAMESIZE; c++) {
                if (board[r][c].value != 0) {
                    count++;
                }
            }
        }
        return count == (GameGen.GAMESIZE * GameGen.GAMESIZE);
    }

    public GameInstance getDefensiveCopy() {
        return new GameInstance(row, column, cell, board);
    }
}
