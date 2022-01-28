import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        UNO_GAME.GameLogic game = new UNO_GAME.GameLogic();
        while(true) {
            System.out.println("Welcome to UNO!"); //move to main
            System.out.println("This will be a game between 2 players. "); //main
            System.out.println("The player who has no cards in their hand first wins."); //main
            System.out.println("Player 1 will start first."); //main
            System.out.println("Are you ready to play? (Y/N)"); //main - if Y, starting game, if N, re-loops to beginning
            String line = in.nextLine();
            if (line.equalsIgnoreCase("Y")) {
                game.startGame();
            }
            else if (line.equalsIgnoreCase("N")) {
                break;
            }
            else{
                System.out.println("Invalid Input!!");
            }
        }
    }
}
