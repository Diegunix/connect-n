package com.darwinex.connectn.game;

import com.darwinex.connectn.Chip;
import com.darwinex.connectn.ChipPosition;
import com.darwinex.connectn.GameResult;

public class GameImpl implements Game {
    
    private Chip[][] board;
    private int connectedChips;
    private int columns;
    private int rows;
    
    /**
     * @param rows
     *            the number of rows in the board
     * @param columns
     *            the number of columns in the board
     * @param n
     *            the number of connected chips required to win the game
     */
    public GameImpl(final int rows, final int columns, final int n) {
        if (rows > 0 && columns > 0 && n > 0) {
            board = new Chip[rows][columns];
            connectedChips = n;
            this.rows = rows;
            this.columns = columns;
        } else {
            throw new UnsupportedOperationException("Error in data");
        }
    }
    
    @Override
    public void putChip(final Chip chip, final int column) {
        if(column > columns) {
            throw new UnsupportedOperationException("Error in data");
        }
        for (int i = 0; i < rows; ++i) {
            if (board[i][column] == null) {
                board[i][column] = chip;
                i = rows;
            }
        }
    }
    
    @Override
    public GameResult getGameResult() {
        GameResult result = null;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                result = validatePosition(row, column);
                if (result != null) {
                    row = rows;
                    column = columns;
                }
            }
        }
        return result;
    }
    
    private GameResult validatePosition(int row, int column) {
        Chip chip = board[row][column];
        ChipPosition[] position = null;
        if (chip != null) {
            position = validateHorizontal(chip, row, column);
            if (position == null) {
                position = validateVertical(chip, row, column);
            }
            if (position == null) {
                position = validateDiagonal(chip, row, column);
            }
            if (position == null) {
                position = validateDiagonalInversa(chip, row, column);
            }
            if (position != null) {
                return new GameResult(chip, position);
            }
        }
        
        return null;
    }
    
    private ChipPosition[] validateHorizontal(Chip chip, int row, int column) {
        ChipPosition[] position = new ChipPosition[connectedChips];
        int chipsInPosition = 0;
        for (int i = column; i < columns; ++i) {
            if (board[row][i] == chip) {
                ChipPosition pos = new ChipPosition(row, i);
                position[chipsInPosition] = pos;
                chipsInPosition++;
                if (position.length == chipsInPosition) {
                    return position;
                }
            } else {
                i = columns;
            }
        }
        return null;
    }
    
    private ChipPosition[] validateVertical(Chip chip, int row, int column) {
        ChipPosition[] position = new ChipPosition[connectedChips];
        int chipsInPosition = 0;
        for (int i = row; i < rows; ++i) {
            if (board[i][column] == chip) {
                ChipPosition pos = new ChipPosition(i, column);
                position[chipsInPosition] = pos;
                chipsInPosition++;
                if (position.length == chipsInPosition) {
                    return position;
                }
            } else {
                i = rows;
            }
        }
        return null;
    }
    
    private ChipPosition[] validateDiagonal(Chip chip, int row, int column) {
        ChipPosition[] position = new ChipPosition[connectedChips];
        int chipsInPosition = 0;
        for (int i = 0; i < Math.min(rows, columns); ++i) {
            if (row + i < rows && column + i < columns) {
                if (board[row + i][column + i] == chip) {
                    ChipPosition pos = new ChipPosition(row + i, column + i);
                    position[chipsInPosition] = pos;
                    chipsInPosition++;
                    if (position.length == chipsInPosition) {
                        return position;
                    }
                } else {
                    i = Math.min(rows, columns);
                }
            }
        }
        return null;
    }
    
    private ChipPosition[] validateDiagonalInversa(Chip chip, int row, int column) {
        ChipPosition[] position = new ChipPosition[connectedChips];
        int chipsInPosition = 0;
        for (int i = 0; i < Math.min(rows, columns); ++i) {
            if (row + i >= 0 && column - i >= 0) {
                if (board[row + i][column - i] == chip) {
                    ChipPosition pos = new ChipPosition(row + i, column - i);
                    position[chipsInPosition] = pos;
                    chipsInPosition++;
                    if (position.length == chipsInPosition) {
                        return position;
                    }
                } else {
                    i = Math.min(rows, columns);
                }
            }
        }
        return null;
    }
    
}
