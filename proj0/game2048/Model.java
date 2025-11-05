package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Cinnamorollx
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        if (side == Side.NORTH) {
            board.setViewingPerspective(Side.NORTH);
        } else if (side == Side.WEST) {
            board.setViewingPerspective(Side.WEST);
        } else if (side == Side.SOUTH) {
            board.setViewingPerspective(Side.SOUTH);
        } else if (side == Side.EAST) {
            board.setViewingPerspective(Side.EAST);
        }

        for (int col = 0; col < board.size(); col++) {
            int limitedRow = board.size();
            for (int row = board.size() - 1; row >= 0 ; row--) {

                if (row == board.size() - 1) {
                    continue;
                }

                Tile t = board.tile(col, row);

                if (t == null) {
                    continue;
                }

                int value = t.value();

                int mergedRow = findNeareastRowOnTopThatHasSameValue(board, col, row, value, limitedRow);
                if (mergedRow != -1) {
                    boolean merged = board.move(col, mergedRow, t);
                    changed = true;
                    if (merged) {
                        score += 2 * value;
                    }
                    limitedRow = mergedRow;
                    continue;
                }

                // otherwise no same value on top, just move up if there is an empty space
                int emptyRow = findLastAvailableEmptyRowOnTop(board, col, row);
                if (emptyRow != -1) {
                    board.move(col, emptyRow, t);
                    changed = true;
                }

            }
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }

        board.setViewingPerspective(Side.NORTH);

        return changed;
    }

    public static int findNeareastRowOnTopThatHasSameValue(Board b, int col, int row, int value, int limitedRow) {
        for (int i = row + 1; i < limitedRow; i++) {
            Tile t = b.tile(col, i);
            if (t != null) {
                if (t.value() == value) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findLastAvailableEmptyRowOnTop(Board b, int col, int row) {
        if (row + 1 >= b.size() || b.tile(col, row + 1) != null) {
            return -1;
        } else {
            int ans = row + 1;
            int finalAns = findLastAvailableEmptyRowOnTop(b, col, row + 1);
            if (finalAns != -1) {
                ans = finalAns;
            }
            return ans;
        }
    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int rowNumber = b.size(), colNumber = b.size();
        for (int i = 0; i < colNumber; i++) {
            for (int j = 0; j < rowNumber; j++) {
                if (b.tile(i,j) == null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int rowNumber = b.size(), colNumber = b.size();
        for (int i = 0; i < colNumber; i++) {
            for (int j = 0; j < rowNumber; j++) {
                Tile currentTile = b.tile(i, j);
                if (currentTile != null) {
                    if (currentTile.value() == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (Model.emptySpaceExists(b)) {
            return true;
        }

        // board is full at least
        int colNum = b.size(), rowNum = b.size();
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                int val = b.tile(i,j).value();
                boolean canMove = positionNeighborTileHasSameValue(i, j, val, b);
                if (canMove) {
                    return canMove;
                }
            }
        }
        return false;
    }

    private static boolean positionNeighborTileHasSameValue(int col, int row, int value, Board b) {
        // top
        if (isValidPosition(b, col, row - 1)) {
            if (b.tile(col, row - 1).value() == value) {
                return true;
            }
        }
        // left
        if (isValidPosition(b, col - 1, row)) {
            if (b.tile(col - 1, row).value() == value) {
                return true;
            }
        }
        // bottom
        if (isValidPosition(b, col, row + 1)) {
            if (b.tile(col, row + 1).value() == value) {
                return true;
            }
        }
        // right
        if (isValidPosition(b, col + 1, row)) {
            if (b.tile(col + 1, row).value() == value) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidPosition(Board b, int col, int row) {
        if (col < 0 || col >= b.size()) {
            return false;
        } else if (row < 0 || row >= b.size()) {
            return false;
        } else {
            return true;
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
