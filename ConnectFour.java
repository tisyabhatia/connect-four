import java.util.*;

// This class enables the user to play a game of Connect Four. It includes functionality for users
// to start a new empty game, view the game board, make a move, and automatically checking for 
// a horizontal, vertical, AND diagonal winner.
public class ConnectFour extends AbstractStrategyGame {
    public static final char PLAYER1_TOKEN = 'X';
    public static final char PLAYER2_TOKEN = 'O';
    public static final char EMPTY = '-';
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int TIE = 0;
    public static final int GAME_OVER = -1;
    public static final int GAME_NOT_OVER = -1;

    // Private fields
    private char[][] board;
    private boolean is1Turn;

    // Constructs a new Connect Four game with a new empty gameboard 
    // No exceptions, returns, or parameters
    public ConnectFour() {
        board = new char[][]{{'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'}};

        is1Turn = true;
    }

    /* Details how to play the connect four game
     * Exceptions: none
     * Returns: a String of instructions on how to play the game
     * Parameters: none
     */
    public String instructions() {
        String instructions = "";
        instructions += "Player 1 is X and Player 2 is O; Player 1 goes first\n";
        instructions += "Connect Four is a two player game in which each player takes turns\n";
        instructions += "placing a disk in the vertical game board. Each player chooses which\n";
        instructions += "column to drop their disk into. If a column is not full, the token\n";
        instructions += "down to the to the bottom-most available slot. To win, four of a\n"; 
        instructions += "player's tokens must be aligned vertically, horizontally, diagonally.\n";
        instructions += "If the game board is full, the game will be tied.";
        return instructions;
    }

    /* Describes the current state of the game (i.e. the connect four board)
     * Exceptions: none
     * Returns: String representation of the game
     * Parameters: none
     */
    public String toString() {
        String result = "";

        for (int i = 1; i <= board[0].length; i++) {
            result += "  " + i + " ";
        }

        result += "\n";

        for (int i = 0; i < board.length; i++) {
            result += "|";
            for (int j = 0; j < board[0].length; j++) {
                result += " " + board[i][j] + " |";
            }

            result += "\n";
        }

        return result;
    }

    /* Decides the winner of the game by checking for vertical, horizontal, and diagonal winner
     * Exceptions: none
     * Returns:
     *    - int: 1 or 2 based on which player won (if any)
     *    - int: TIE (0) if there is no current winner and the board is full
     *    - int: GAME_NOT_OVER if there is no winner and the board still has empty spots
     */
    public int getWinner() {
        for (int i = 0; i < board.length; i++) {
            int rowWinner = getRowWinner(i);
            if (rowWinner != GAME_NOT_OVER) {
                return rowWinner;
            }

            int columnWinner = getColumnWinner(i);
            if (columnWinner != GAME_NOT_OVER) {
                return columnWinner;
            }
        }

        int diagWinner = getDiagWinner();
        if (diagWinner != GAME_NOT_OVER) {
            return diagWinner;
        }

        return checkTie();
    }

    /* Checks for a horizontal four in a row winner on the board
     * Exceptions: none
     * Returns:
     *    - int: 1 or 2 depending on which player (1 or 2) got a four in a row
     *    - int: GAME_NOT_OVER (-1) if neither player has a horizontal four in a row
     * Parameters: int row- row to be checked for horizontal win
     */
    private int getRowWinner(int row) {
        int inARow1 = 0;
        int inARow2 = 0;

        for (int i = 0; i < board[row].length; i++) {
            if (board[row][i] == PLAYER1_TOKEN) {
                inARow1++;
                if (inARow1 == 4) {
                    return 1;
                }
            } else {
                inARow1 = 0;
            }

            if (board[row][i] == PLAYER2_TOKEN) {
                inARow2++;
                if (inARow2 == 4) {
                    return 2;
                }
            } else {
                inARow2 = 0;
                if (inARow2 == 4) {
                    return 2;
                }
            }
        }

        if (inARow1 == 4) {
            return 1;
        }

        if (inARow2 == 4) {
            return 2;
        }

        return GAME_NOT_OVER;
    }

    /* Checks if there is a column winner (i.e. vertical win)
     * Exceptions: none
     * Returns:
     *    - int: 1 or 2 depending on whether player 1 or 2 received the four-in-a-row
     *    - int: GAME_NOT_OVER (-1) if neither player has a vertical four in a row
     * Parameters:
     *    - int column: the column to check in for a four in a row
     */
    private int getColumnWinner(int column) {
        int inARow1 = 0;
        int inARow2 = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == PLAYER1_TOKEN) {
                inARow1++;
                if (inARow1 == 4) {
                    return 1;
                }
            } else {
                inARow1 = 0;
            }

            if (board[i][column] == PLAYER2_TOKEN) {
                inARow2++;
            if (inARow2 == 4) {
                return 2;
            }
            } else {
                inARow2 = 0;
            }
        }

        if (inARow1 == 4) {
            return 1;
        }

        if (inARow2 == 4) {
            return 2;
        }

        return GAME_NOT_OVER;
    }

    /* Checks if there is a winner who won diagonally (four in a row diagonally)
     * Exceptions: none
     * Returns: 
     *    - int: 1 or 2 depending on if player 1 or player 2 won the diagonal
     *    - int: GAME_NOT_OVER (-1) if there was no diagonal
     * Parameters: none
     */
    private int getDiagWinner() {   
        char token = ' ';

        // check right diagonal (next coin is up and to the right)

        for (int i = board.length - 1; i > 2; i--) {
            for (int j = 0; j < 4; j++) { // only first 4 slots can get four in a row
                if (board[i][j]==board[i-1][j+1] && board[i-1][j+1]==board[i-2][j+2] && 
                    board[i-2][j+2] == board[i - 3][j + 3]) {
                    
                    token = board[i][j];
                }
                
                if (token == PLAYER1_TOKEN) {
                    return 1;
                } else if (token == PLAYER2_TOKEN) {
                    return 2;
                }
            }
        }

        // check left diagonal (next coin is up and to the left)

        token = ' ';

        for (int i = board.length - 1; i > 2; i--) {
            for (int j = board[i].length - 1; j > 2; j--) { // only last 4 slots can get four in a row
                if (board[i][j]==board[i-1][j-1] && board[i-1][j-1]==board[i-2][j-2] && 
                    board[i-2][j-2] == board[i - 3][j - 3]) {

                    token = board[i][j];
                }

                if (token == PLAYER1_TOKEN) {
                    return 1;
                } else if (token == PLAYER2_TOKEN) {
                    return 2;
                }
            }
        }

        return GAME_NOT_OVER;
    }

    /* Checks if the two players are tied (i.e. the gameboard is full)
     * Exceptions: none
     * Returns: 
     *    - int: TIE (0) if there is no more space on the gameboard
     *    - int: GAME_NOT_OVER (-1) if there is still unused space on the baord
     * Parameters: none
     */
    private int checkTie() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; i++) {
                if (isEmpty(i, j)) {
                    return GAME_NOT_OVER;
                }
            }
        }

        return TIE;
    }

    /* Checks whether a specific cell in the board is empty
     * Exceptions: IllegalArgumentException if column is outside of the board's range
     * Returns: boolean true if the cell is empty, false if not
     * Parameters: 
     *    - int row: the row of the cell to check
     *    - int col: the column of the cell to check
     */
    private boolean isEmpty(int row, int col) {
        if (col > board[0].length || col < 0) {
            throw new IllegalArgumentException("Given column is out of range: " + col);
        }

        return board[row][col] == EMPTY;
    }

    /* Determie which player is next to take a turn
     * Exceptions: none
     * Return:
     *    - int: a player number to indicate which player's turn is next
     *    - int: game over (-1) if the game is over and a player has won
     * Parameters: none
     */
    public int getNextPlayer() {
        if (isGameOver()) {
            return GAME_OVER;
        }

        return is1Turn ? PLAYER1 : PLAYER2;
    }

    /* Determines user's next move and make the next move
     * Exceptions: IllegalArgumentException 
     *    - if the input is null
     *    - if the specified column is outside the available column range
     *    - if the column of choice is full
     * Returns: none
     * Parameters: Scanner input used to read the user's intend move
     */
    public void makeMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Input is null :(");
        }

        char currChip = is1Turn ? PLAYER1_TOKEN : PLAYER2_TOKEN;

        System.out.print("Which column do you want to drop the tile into? ");
        int col = input.nextInt() - 1;

        makeMove(col, currChip);
        is1Turn = !is1Turn;
    }

    /* Places a token into a specific column of the board if column requirements are met
     * Exceptions: IllegalArgumentException
     *    - if the specified column is outside the available column range
     *    - if the column of choice is full
     * Returns: none
     * Parameters: 
     *    - int col: the column that the user wants to put the token in
     *    - char chip: the user's chip ('X' or 'O')
     */
    private void makeMove(int col, char chip) {
        if (col > board[0].length || col < 0) {
            throw new IllegalArgumentException("Given column is out of the range: " + col);
        }

        if (!isEmpty(0, col)) {
            throw new IllegalArgumentException("This column is occupied: " + col);
        }

        boolean tokenPlaced = false;
        int height = board.length - 1; 

        while (tokenPlaced == false) {
            if(isEmpty(height, col)) {
                board[height][col] = chip;
                tokenPlaced = true;
            }

            height--;
        }

    }
}
