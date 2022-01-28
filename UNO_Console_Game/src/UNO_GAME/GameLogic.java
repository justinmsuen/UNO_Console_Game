package UNO_GAME;

import UNO_GAME.DeckOfCards;
import UNO_GAME.Player;

import java.util.Scanner;

public class GameLogic {

    private Scanner in = new Scanner(System.in);
    private DeckOfCards deck;
    private Player player1;
    private Player player2;
    private UNO_GAME.Card currentCard;
    private UNO_GAME.Card prevCard;
    private boolean p1Turn;
    private boolean p2Turn;
    private boolean p1Uno;
    private boolean p2Uno;
    private boolean validMove;


    /**
     * Constructor - initializes instance variables: 2 Players, 1 Deck of cards, 1 card to act as current in-play card, 1 card to act as previous card, and several boolean variables to determine turns,
     *  if a player has "UNO", if a move is valid, and if victory has been achieved
     */
    public GameLogic() {

        deck = new DeckOfCards();
        player1 = new Player(7, deck);
        player2 = new Player(7, deck);
        currentCard = null;
        prevCard = null;
        p1Turn = false;
        p2Turn = false;
        p1Uno = false;
        p2Uno = false;
        validMove = false;

    }

    /**
     * Asks players to enter their names, deals their first hand (7 cards) and starts the game (Player 1 has first move)
     * All methods in this class are private except for startGame() and endGame() since there is no reason for anything outside the class to call these private methods
     */
    public void startGame() {
        System.out.println("Please enter name of player 1: ");
        String name1 = in.nextLine();
        player1.setPlayerName(name1);
        System.out.println("Please enter name of player 2: ");
        String name2 = in.nextLine();
        player2.setPlayerName(name2);

        System.out.println("Starting Game. Good luck, and have fun! ");
        p1Turn = true;
        prevCard = deck.deal(); //<---need a way to flip the first card from the deck to start the game
        System.out.println("The card to start the game is " + prevCard);
        startTurn();

    }

    /**
     * Starts turn for each player by giving them the option to play a card from their hand, or draw a card from the deck
     * From there, it will validate this move by calling the isValidMove() method
     */

    private void startTurn(){
        //determines and starts turn for either player
        while (p1Turn == true) {
            if (player1.playerHand.size() > 1) {
                p1Uno = false;  //resets Uno to false if player has more than 1 card
            }

            System.out.println("***********************************************");
            player1.displayPlayerHand();
            System.out.println("It is " + player1.getPlayerName() + "'s turn. These are your options (press A or B): ");
            System.out.println("A.) Play a card from your hand ");
            System.out.println("B.) Draw a card from the deck ");
            System.out.println("Active Card is = " + prevCard);
            String choice = in.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                try {
                    player1.displayPlayerHand();
                    String choice2 = in.nextLine();
                    currentCard = player1.playCard(Integer.parseInt(choice2));  //sets currentCard to what the playCard() returns (method should return a UNO_GAME.Card object). Also once card played, card will be removed from player's hand
                    if (isValidMove(choice2)) {
                        player1.removeCardFromHand(Integer.parseInt(choice2));
                        checkUno();
                        checkVictory();
                    }
                    endTurn();
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input. Please enter the correct number for the card you wish to play");
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println("Incorrect input. Please enter the correct number for the card you wish to play");
                }

            } else if (choice.equalsIgnoreCase("B")) {
                player1.addCardToHand(1);
                endTurn();
            } else {
                System.out.println("Invalid choice. Try again.");
            }

        }
        while (p2Turn == true) {
            if (player2.playerHand.size() > 1) {
                p2Uno = false;   //resets Uno to false if player has more than 1 card
            }
            System.out.println("***********************************************");
            player2.displayPlayerHand();
            System.out.println("It is " + player2.getPlayerName() + "'s turn. These are your options (press A or B): ");
            System.out.println("A.) Play a card from your hand ");
            System.out.println("B.) Draw a card from the deck ");
            System.out.println("Active Card is = " + prevCard);
            String choice = in.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                try {
                    player2.displayPlayerHand();//should list player's cards and give him options to select a card to play
                    String choice2 = in.nextLine();
                    currentCard = player2.playCard(Integer.parseInt(choice2));  //sets currentCard to what the playCard() returns (method should return a UNO_GAME.Card object). Also once card played, card will be removed from player's hand
                    if (isValidMove(choice2)) {
                        player2.removeCardFromHand(Integer.parseInt(choice2));
                        checkUno();
                        checkVictory();
                    }
                    endTurn();
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input. Please enter the correct number for the card you wish to play.");
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println("Incorrect input. Please enter the correct number for the card you wish to play");
                }

            } else if (choice.equalsIgnoreCase("B")) {
                player2.addCardToHand(1);
                endTurn();
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * checks if valid move (card played needs to be of same color/suit or both)
     */
    private boolean isValidMove(String choice){

        if (prevCard.rank() == 13 || prevCard.rank() == 14) {
            prevCard = currentCard;
            validMove = true;
        }
        else if (currentCard.rank() == 13 || currentCard.rank() == 14) {
            checkWild(choice);
            validMove = true;
        }
        else if(currentCard.rank() == prevCard.rank()){
            prevCard = currentCard; //sets previous card to current card played if move is valid
            checkSpecial(choice);
            validMove = true;
        }
        else if (currentCard.suit() == prevCard.suit()){
            prevCard = currentCard; //sets previous card to current card played if move is valid
            checkSpecial(choice);
            validMove = true;
        }
        else {
            System.out.println("Invalid move. Please try again. ");
            startTurn();
        }
        return validMove;
    }

    /**
     * ends turn of current player and switches turn to next player
     */
    private void endTurn(){

        if (p1Turn == true) {
            p1Turn = false;
            p2Turn = true;
        }
        else if (p2Turn == true) {
            p2Turn = false;
            p1Turn = true;
        }

        startTurn();
    }

    /**
     * This method checks if a Draw +2, Reverse, or Skip card is played
     */
    private void checkSpecial(String choice) {
        while(p1Turn == true) {
            if (currentCard.rank() == 10) {

                player2.addCardToHand(2);
                System.out.println(player2.getPlayerName() + " will draw 2 cards ");
                player1.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            } else if (currentCard.rank() == 11) {

                player1.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            } else if (currentCard.rank() == 12) {

                player1.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            }
            else {
                break;
            }
        }

        while (p2Turn == true) {
            if (currentCard.rank() == 10) {

                player1.addCardToHand(2);
                System.out.println(player1.getPlayerName() + " will draw 2 cards ");
                player2.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            } else if (currentCard.rank() == 11) {

                player2.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            } else if (currentCard.rank() == 12) {

                player2.removeCardFromHand(Integer.parseInt(choice));
                checkUno();
                checkVictory();
                startTurn();
            }
            else {
                break;
            }
        }
    }

    /**
     * Method to check if card is Wild/Wild +4
     * @param choice
     */
    private void checkWild(String choice) {
        while (p1Turn == true) {
            if (currentCard.rank() == 13) {
                if (chooseColor() == true) {

                    player1.removeCardFromHand(Integer.parseInt(choice));
                    checkUno();
                    checkVictory();
                    endTurn();
                }
            }
            else if (currentCard.rank() == 14) {

                if (chooseColor() == true) {

                    player2.addCardToHand(4);
                    System.out.println(player2.getPlayerName() + " will draw 4 cards ");
                    player1.removeCardFromHand(Integer.parseInt(choice));
                    checkUno();
                    checkVictory();
                    endTurn();
                }
            }
        }
        while (p2Turn == true) {
            if(currentCard.rank() == 13) {

                if (chooseColor() == true) {

                    player2.removeCardFromHand(Integer.parseInt(choice));
                    checkUno();
                    checkVictory();
                    endTurn();
                }
             }
            else if(currentCard.rank() == 14) {

                if (chooseColor() == true) {

                    player1.addCardToHand(4);
                    System.out.println(player1.getPlayerName() + " will draw 4 cards ");
                    player2.removeCardFromHand(Integer.parseInt(choice));
                    checkUno();
                    checkVictory();
                    endTurn();
                }
            }
        }
    }

    /**
     * Asks player to choose what color next card should be if they play a Wild/Wild +4
     */
    private boolean chooseColor() {
        boolean colorFlag = false;
        System.out.println("You played a Wild Card. Please enter the color you would like to change it to.");
        String colorChoice = in.nextLine();

        if (colorChoice.equalsIgnoreCase("RED")) {
            prevCard = new Card(3, 15);
            colorFlag = true;

        } else if (colorChoice.equalsIgnoreCase("BLUE")) {
            prevCard = new Card(2, 15);
            colorFlag = true;

        } else if (colorChoice.equalsIgnoreCase("GREEN")) {
            prevCard = new Card(1, 15);
            colorFlag = true;

        } else if (colorChoice.equalsIgnoreCase("YELLOW")) {
            prevCard = new Card(0, 15);
            colorFlag = true;

        } else {
            System.out.println("Invalid color. Please type red/blue/green/yellow. ");

        }

        return colorFlag;
    }

    /**
     * Checks whether player still has Cards in Hand
     * Also checks if player has 1 card left, then gives them the option to say UNO
     */
    private void checkUno(){

        while(p1Uno == false) {
            if (player1.playerHand.size() == 1) {
                System.out.println("Type the magic word. ");
                String uno = in.nextLine();
                if (uno.equalsIgnoreCase("UNO")) {
                    System.out.println("Nice! Almost to victory");
                    p1Uno = true;
                } else {
                    System.out.println("Sorry, you didn't say UNO. Draw 2 cards. ");
                    player1.addCardToHand(2);
                }
            }
            else{
                break;
            }
        }
        while(p2Uno == false) {
            if (player2.playerHand.size() == 1) {
                System.out.println("Type the magic word. ");
                String uno = in.nextLine();
                if (uno.equalsIgnoreCase("UNO")) {
                    System.out.println("Nice! Almost to victory");
                    p2Uno = true;
                } else {
                    System.out.println("Sorry, you didn't say UNO. Draw 2 cards. ");
                    player2.addCardToHand(2);
                }
            }
            else {
                break;
            }
        }

    }

    private void checkVictory() {
        if (player1.playerHand.size() == 0 || player2.playerHand.size() == 0) {
            System.out.println("Congratulations, you win! This is the end of the game. ");
            endGame();
        }
    }

    public void endGame(){
        //Ends program
        System.exit(0);
    }
}
