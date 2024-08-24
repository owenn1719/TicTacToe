import javax.xml.transform.Source;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        String userTeamChoice;

        while (true) {
            System.out.print("Do you want X's or O's? : ");
            userTeamChoice = scanner.nextLine();

            if (userTeamChoice.trim().equalsIgnoreCase("X") ||
                    userTeamChoice.trim().equalsIgnoreCase("O")) {
                break;
            }

            System.out.println("Please type X, or O, as valid team choices.");
        }

        TicTacToeGame ticTacToeGame = new TicTacToeGame(userTeamChoice);
        String startingPlayer = ticTacToeGame.getStartingPlayer();
        ticTacToeGame.setActivePlayer(startingPlayer);
        System.out.println(startingPlayer + " goes first.");

        //Game Loop
        while (true) {

            System.out.println("Current Game Board: ");

            String[][] gameBoard = ticTacToeGame.getGameBoard();
            for (int i = 0; i < ticTacToeGame.getGameBoard().length; i++) {
                System.out.println(Arrays.toString(gameBoard[i]));
            }

            //Get user input for players turn
            if (ticTacToeGame.getActivePlayer().equals("human")) {
                ticTacToeGame.executeHumanTurn(scanner, gameBoard);
            } else {
                ticTacToeGame.executeComputerTurn(gameBoard);
            }

            System.out.println("\n");

            boolean isCatsGame = ticTacToeGame.checkForCatsGame();
            if (isCatsGame) {
                System.out.println("Cat's Game!");
                break;
            }

            boolean gameOver = ticTacToeGame.checkForWinner();
            if (gameOver) {
                System.out.println(ticTacToeGame.getActivePlayer() + " wins!");
                break;
            }

            ticTacToeGame.switchPlayer();
        }
    }
}