import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * TicTacToe game for the console, for two players (X and O) or one player (X) against "AI" (O).
 */
public class TicTacToe {

    // Char Array for printing the gameboard on the console
    private char[][] gameBoard;
    // Number of Players (one or two)
    private int players;
    // List for storing the set fields of player X (human)
    private ArrayList<Integer> playerXFields;
    // List for storing the set fields of player O (human or AI)
    private ArrayList<Integer> playerOFields;

    // Current player ('X' or 'O')
    private char activePlayer;


    public static void main(String[] args) {
        TicTacToe myObj = new TicTacToe();
    }

    public TicTacToe() {
        System.out.println("--- Welcome to TicTacToe ---");
        initialiseNewGame();
    }


    /**
     * Initializes a new game.
     * Selects randomly, which player should begin and sets player one as 'activePlayer'.
     */
    public void initialiseNewGame(){

        // CharArray for printing a new gameboard.
        gameBoard = new char[][]{ {' ', '|', ' ', '|', ' '},
                                  {'-', '+', '-', '+', '-'},
                                  {' ', '|', ' ', '|', ' '},
                                  {'-', '+', '-', '+', '-'},
                                  {' ', '|', ' ', '|', ' '} };

        playerXFields = new ArrayList<Integer>();
        playerOFields = new ArrayList<Integer>();

        // Default pattern for random numbers:
        // Min + (int)(Math.random() * ((Max - Min) + 1))
        int randomInt = 1 + (int)(Math.random() * ((2 - 1) + 1));
        if (randomInt == 1){
            activePlayer = 'X';
        }
        else {
            activePlayer = 'O';
        }

        setPlayers();
        playGame();

    }


    /**
     * Controls the game logic:
     * Prints the gameboard; checks after every move if a player has already won, sets the 'activePlayer'.
     */
    public void playGame(){
        printGameBoard();
        if(players == 2 || (players == 1 && activePlayer == 'X') ) {
            selectField();
        }
        else {
            aiSelectField();
        }
        switch (checkGameStatus()) {
            case 'X':
                printGameBoard();
                System.out.println("Player X wins :) \n");
                playAgain();
                break;
            case 'O':
                printGameBoard();
                if (players == 2) {
                    System.out.println("Player O wins :) \n");
                    playAgain();
                }
                else {
                    System.out.println("Computer wins ;-P \n");
                    playAgain();
                }
                break;
            case 'F':
                printGameBoard();
                System.out.println("Game over! Nobody Wins :-( \n");
                playAgain();
                break;
            case 'C':
                if (activePlayer == 'X') {
                    activePlayer = 'O';
                }
                else {
                    activePlayer = 'X';
                }
                playGame();
                break;
        }
    }

    /**
     * UI for setting the amount of human players (1 or 2).
     */
    public void setPlayers(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nHow many (human) players are we today? Please type '1' or '2': ");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    players = 1;
                    break;
                case 2:
                    players = 2;
                    break;
                default:
                    System.out.println("This is not a valid number of players.");
                    setPlayers();
            }
        }
        catch (Exception e) {
            // java.util.InputMismatchException e
            System.out.println("This is not a number!");
            scanner.nextLine();
            setPlayers();
        }

    }

    /**
     * Prints the updated gameboard with the set fields (X's and O's) of both players.
     */
    public void printGameBoard() {
        for (char[] ca: gameBoard) {
            for (char c : ca) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();


    }

    /**
     * UI for selecting the field.
     */
    public void selectField() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print( "Player " + activePlayer + ", please select your field from 1 to 9: ");
            int input = scanner.nextInt();
            // If field is still empty it can be set.
            if (returnCharAtPosition(input) == ' ') {
                placePiece(input, activePlayer);
            }
            // Returns a message if field is already set.
            else if (returnCharAtPosition(input) == 'X' || returnCharAtPosition(input) == 'O'){
                System.out.println("This Field is already taken!");
                selectField();
            }
            else {
                System.out.println("This is not a valid field position!");
                selectField();
            }
        }
        catch (Exception e) {
            // java.util.InputMismatchException e
            System.out.println("This ist not a number!");
            scanner.nextLine();
            selectField();
        }
    }

    /**
     * Simple 'AI' for randomly selecting a field.
     */
    public void aiSelectField(){
        System.out.println("Computer selected field:");
        int randomInt;
        do{
            // Default pattern for random numbers:
            // Min + (int)(Math.random() * ((Max - Min) + 1))
            randomInt = 1 + (int)(Math.random() * ((9 - 1) + 1));
        }
        while (playerXFields.contains(randomInt) || playerOFields.contains(randomInt));
        placePiece(randomInt, activePlayer);
    }

    /**
     * Returns the actual marks at field position 1 to 9.
     * @param pos Field position from 1 to 9.
     * @return char ' ' if field is still empty,
     *              'X' if field ist set by player X,
     *              'O' if field ist set by player O.
     */
    public char returnCharAtPosition(int pos){
        char c;
        switch(pos) {
            case 1:
                c = gameBoard[0][0];
                break;
            case 2:
                c = gameBoard[0][2];
                break;
            case 3:
                c = gameBoard[0][4];
                break;
            case 4:
                c = gameBoard[2][0];
                break;
            case 5:
                c = gameBoard[2][2];
                break;
            case 6:
                c = gameBoard[2][4];
                break;
            case 7:
                c = gameBoard[4][0];
                break;
            case 8:
                c = gameBoard[4][2];
                break;
            case 9:
                c = gameBoard[4][4];
                break;
            default:
                c = '!';
        }
        return c;
    }

    /**
     * Sets a field mark for a player.
     * @param pos Field position from 1 to 9.
     * @param player Player 'X' or 'O'.
     */
    public void placePiece(int pos, char player) {
        switch (pos) {
            case 1:
                gameBoard[0][0] = player;
                break;
            case 2:
                gameBoard[0][2] = player;
                break;
            case 3:
                gameBoard[0][4] = player;
                break;
            case 4:
                gameBoard[2][0] = player;
                break;
            case 5:
                gameBoard[2][2] = player;
                break;
            case 6:
                gameBoard[2][4] = player;
                break;
            case 7:
                gameBoard[4][0] = player;
                break;
            case 8:
                gameBoard[4][2] = player;
                break;
            case 9:
                gameBoard[4][4] = player;
                break;
            default:
                System.out.println("This ist not a valid Field!");
                selectField();
        }
        switch (player) {
            case 'X':
                playerXFields.add(pos);
                break;
            case 'O':
                playerOFields.add(pos);
                break;
        }
    }

    /**
     * Return the actual game status.
     * @return  'X' -> Player X wins.
     *          'O' -> Player O wins.
     *          'F' -> GameBoard full, pat.
     *          'C' -> Game continues.
     */
    public char checkGameStatus(){
        ArrayList<Integer> currentPlayerFields;
        int[][] winnerPos = { {1,2,3}, {4,5,6}, {7,8,9}, {1,4,7}, {2,5,8}, {3,6,9}, {1,5,9}, {3,5,7} };

        if (activePlayer == 'X') {
            currentPlayerFields = playerXFields;
        }
        else {
            currentPlayerFields = playerOFields;
        }


        for (int[] intArray: winnerPos) {
            int counter = 0;
            for (int i: intArray) {
                if (currentPlayerFields.contains(i)) {
                    counter++;
                }
                else {
                    break;
                }
            }
            if (counter >= 3){
                return activePlayer;
            }
        }
        if (playerOFields.size() + playerXFields.size() >= 9) {
            return 'F';
        }
        else {
            return 'C';
        }
    }

    /**
     * Asks the player if he wants to play again or quit the game.
     */
    public void playAgain(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Wanna play again? Type 'y' for yes and 'n' for no: ");
            String input = scanner.nextLine();
            switch (input) {
                case "y":
                    initialiseNewGame();
                    break;
                case "n":
                    System.out.println("Goodbye, see you next time :)");
                    TimeUnit.SECONDS.sleep(3);
                    System.exit(0);
                    break;
                default:
                    System.out.println("This is not a valid input.");
                    playAgain();
            }
        }
        catch (Exception e) {
            scanner.nextLine();
            playAgain();
        }
    }

}
