package sudoku;

import java.util.Stack;

public class GameGen{

    public static final int GAMESIZE = 9;

    private Cell[][] board;

    public GameGen(){
        board = new Cell[GAMESIZE][GAMESIZE];
        initializeCells();
    }

    public void generate(){
        board = backtrackingSearch();
    }

    private void initializeCells() {
        for(int r = 0; r < GAMESIZE; r++) {
            for(int c = 0; c < GAMESIZE; c++) {
                board[r][c] = new Cell();
            }
        }
    }

    private Cell[][] backtrackingSearch() {
        Stack<GameInstance> history = new Stack();
        int curRow = 0;
        int curColumn = 0;

        Cell cell = board[curRow][curColumn].getDefensiveCopy();
        history.push(new GameInstance(curRow, curColumn, cell, board));

        while(!history.peek().isBoardFilled()) {
            GameInstance curGI = history.peek().getDefensiveCopy();
            Cell cellToTry = curGI.board[curRow][curColumn];
            // Backtrack
            if(cellToTry.domain.size() == 0) {
                GameInstance valueToRemoveGI = history.pop();
                GameInstance domainToReduceGI = history.pop();
                domainToReduceGI.board[valueToRemoveGI.row][valueToRemoveGI.column].domain.remove(new Integer(valueToRemoveGI.cell.value));
                history.push(domainToReduceGI);
                curRow = valueToRemoveGI.row;
                curColumn = valueToRemoveGI.column;
            }
            // Try a value
            else {
                cellToTry.tryFill();
                maintainArcConsistency(curRow, curColumn, cellToTry, curGI);
                GameInstance newGI = new GameInstance(curRow, curColumn, cellToTry, curGI.board);
                history.push(newGI);

                // Move to next cell
                curRow++;
                if (curRow >= GAMESIZE) {
                    curRow = 0;
                    curColumn++;
                    if (curColumn >= GAMESIZE) {
                        break;
                    }
                }
            }
        }
        return history.peek().board;
    }

    private void maintainArcConsistency(int row, int column, Cell cell, GameInstance GI) {
        // Maintain row
        for(int c = 0; c < GAMESIZE; c++){
            GI.board[row][c].domain.remove(new Integer(cell.value));
        }
        // Maintain column
        for(int r = 0; r < GAMESIZE; r++) {
            GI.board[r][column].domain.remove(new Integer(cell.value));
        }
        // Maintain square
        int rSquare = row / 3 * 3;
        int cSquare = column / 3 * 3;
        for(int r = 0; r < (int)Math.sqrt(GAMESIZE); r++) {
            for(int c = 0; c < (int)Math.sqrt(GAMESIZE); c++) {
                GI.board[rSquare + r][cSquare + c].domain.remove(new Integer(cell.value));
            }
        }
    }

    public void display(){
        for(int r = 0; r < GAMESIZE; r++){
            for(int c = 0; c < GAMESIZE; c++){
                System.out.print(board[r][c].value + " ");
            }
            System.out.println();
        }
    }
}