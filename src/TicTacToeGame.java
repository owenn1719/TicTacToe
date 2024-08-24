import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToeGame {

    String[][] gameBoard;
    String activePlayer;
    String userTeamChoice;
    String computerTeam;

    public TicTacToeGame(String userTeamChoice) {

        this.gameBoard = new String[3][3];
        for (byte i=0;i<gameBoard.length;i++) {
            for(byte j=0;j<gameBoard.length;j++) {
                gameBoard[i][j] = "";
            }
        }

        this.userTeamChoice = userTeamChoice;
        this.computerTeam = this.userTeamChoice.equalsIgnoreCase("X") ? "O" : "X";
    }

    public String[][] getGameBoard () {
        return this.gameBoard;
    }

    public String getActivePlayer() {
        return this.activePlayer;
    }

    public void setActivePlayer(String player) {
        this.activePlayer = player;
    }

    public String getStartingPlayer () {
        double coinFlip = Math.random();
        return coinFlip < .5 ? "human" : "computer";
    }

    public void switchPlayer() {
        String activePlayer = this.getActivePlayer();
        String playerToSwitchTo = activePlayer.equals("human") ? "computer" : "human";
        this.setActivePlayer(playerToSwitchTo);
    }

    public void executeHumanTurn(Scanner scanner, String[][] gameBoard) {

        while (true) {

            System.out.print("Row: ");
            byte userRowInput = scanner.nextByte();
            System.out.print("Column: ");
            byte userColumnInput = scanner.nextByte();

            boolean validMove = this.evaluateMove(userRowInput,userColumnInput);

            if (validMove) {
                break;
            }

            System.out.println("That position is occupied, please pick a valid position.");
        }
    }

    public void executeComputerTurn(String[][] gameBoard) throws InterruptedException {

        while (true) {
            //get random row and column values
            int randomRowNum = ThreadLocalRandom.current().nextInt(1, 4);
            int randomColumnNum = ThreadLocalRandom.current().nextInt(1, 4);
            boolean validMove = this.evaluateMove(randomRowNum,randomColumnNum);

            //eventually computer will get a valid move
            if (validMove) {
                break;
            }
        }
    }

    public boolean evaluateMove(int rowCoordinate, int columnCoordinate) {
        String gameBoardCoordinate = this.gameBoard[rowCoordinate-1][columnCoordinate-1];
        if (gameBoardCoordinate.isEmpty()) {
            this.executeMove(rowCoordinate-1, columnCoordinate-1);
            return true;
        } else {
            return false;
        }
    }

    public void executeMove(int rowCoordinate, int columnCoordinate) {
        this.gameBoard[rowCoordinate][columnCoordinate] =
                this.getActivePlayer().equals("human") ? this.userTeamChoice : this.computerTeam;
    }

    public boolean checkForCatsGame() {

        for (byte i=0;i<this.gameBoard.length;i++) {
            for(byte j=0;j<this.gameBoard.length;j++) {
                if (this.gameBoard[i][j].isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkForWinner() {

        String[][] rows = {
                {this.gameBoard[0][0], this.gameBoard[0][1], this.gameBoard[0][2]},
                {this.gameBoard[0][0], this.gameBoard[0][1], this.gameBoard[0][2]},
                {this.gameBoard[0][0], this.gameBoard[0][1], this.gameBoard[0][2]}
        };

        String[][] columns = {
                {this.gameBoard[0][0], this.gameBoard[1][0], this.gameBoard[2][0]},
                {this.gameBoard[0][1], this.gameBoard[1][1], this.gameBoard[2][1]},
                {this.gameBoard[0][2], this.gameBoard[1][2], this.gameBoard[2][2]}
        };

        String[][] diagonals = {
                {this.gameBoard[0][0], this.gameBoard[1][1], this.gameBoard[2][2]},
                {this.gameBoard[0][2], this.gameBoard[1][1], this.gameBoard[2][0]}
        };

        if (this.checkForValidWin(rows)) {
            return true;
        }

        if (this.checkForValidWin(columns)) {
            return true;
        }

        if (this.checkForValidWin(diagonals)) {
            return true;
        }

        return false;
    }

    public boolean checkForValidWin(String[][] tilesToCheck) {
        for (String[] tiles : tilesToCheck) {
            byte xCounter = 0;
            byte oCounter = 0;
            for (String tile : tiles) {
                if (tile.isEmpty()) {
                    //System.out.println("tile.isEmpty() call");
                    break;
                } else if (tile.equals("X")) {
                    xCounter++;
                } else {
                    oCounter++;
                }
                if (xCounter == 3 || oCounter == 3) {
                    System.out.println("Should count as a win");
                    return true;
                }
            }
        }
        return false;
    }
}
