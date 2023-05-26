import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static ArrayList<player> players = new ArrayList<>(); // The players in the game
    static boolean winner = false; // Whether a player has won the game
    static int currentplayer = 0; // The current player

    public static void main(String[] args) {
        ArrayList<String> green = filereader.read(new File("C:\\Users\\jtjak\\IdeaProjects\\Apples to Apples\\src\\green.txt")); // The green cards
        ArrayList<String> red = filereader.read(new File("C:\\Users\\jtjak\\IdeaProjects\\Apples to Apples\\src\\red.txt")); // The red cards
        int numplayers = players(); // The number of players


        for (int i = 0; i < numplayers; i++) { // Adds the players to the arraylist
            players.add(new player()); // Adds a new player to the arraylist
            System.out.print("What is player " + (i + 1) + "'s name?\n> "); // Asks for the player's name
            Scanner s = new Scanner(System.in); // Creates a new scanner
            String name = s.nextLine(); // Reads the next line
            players.get(i).setName(name); // Sets the player's name
        }

        deal(players, red); // Deals the cards to the players
        do { // Loops until a player wins
            if (!winner) { // If no one has won
                turn(players, green, red); // Runs a turn
            } else { // If someone has won
                System.out.println("Player " + players.get(winner(players)).getName() + " won the game!"); // Prints the winner
            }
        } while (!winner);

    }

    public static int players() { // Asks for the number of players
        Scanner input = new Scanner(System.in); // Creates a new scanner
        System.out.print("How many players are playing?\n> "); // Asks for the number of players
        int players = input.nextInt(); // Reads the next int
        System.out.print("How many bots are playing?\n> "); // Asks for the number of bots
        makebots(input.nextInt()); // Makes the bots
        return players; // Returns the number of players
    }

    public static void deal(ArrayList<player> players, ArrayList<String> red) { // Deals the cards to the players
        for (int i = 0; i < players.size(); i++) { // Loops through the players
            for (int j = 0; j < 5; j++) {  // Loops through the cards
                int card = (int) (Math.random() * (red.size() - 1)); // Picks a random card
                players.get(i).addCard(red.get(card)); // Adds the card to the player's hand
                red.remove(card); // Removes the card from the deck
            }
        }
    }

    public static void turn(ArrayList<player> players, ArrayList<String> green, ArrayList<String> red) { // Runs a turn
        String greencard = green.get((int) Math.random() * green.size()); // Picks a random green card
        ArrayList<String> played = new ArrayList<>(); // The cards that have been played
        for (int i = 0; i < players.size(); i++) { // Loops through the players
            if (!(currentplayer == i)) {
                if (!(players.get(currentplayer).isComputer())) {
                    earlyturn(players, greencard, i);
                    System.out.print("Which card would you like to play? (1-5)\n> "); // Asks for the card to play
                    Scanner input = new Scanner(System.in); // Creates a new scanner
                    int card = input.nextInt() - 1; // Reads the next int
                    lateturn(players, red, played, i, card);
                } else {
                    earlyturn(players, greencard, i);
                    int card = (players.get(i).pickcard()); // Picks a card for the computer
                    lateturn(players, red, played, i, card);
                }
            }
        }
        System.out.println("\n\nThe cards played are: "); // Prints the cards played
        System.out.print("The green card is: " + greencard + "\n");
        for (int i = 0; i < played.size(); i++) { // Loops through the cards played
            System.out.println((i + 1) + ": " + played.get(i)); // Prints the card
        }
        if (!(players.get(currentplayer).isComputer())) {
            System.out.print("Which card is the best? (1-5)\n> "); // Asks for the best card
            Scanner input = new Scanner(System.in); // Creates a new scanner
            int winner = input.nextInt() - 1; // Reads the next int
            players.get(winner).addgreen(); // Adds a green card to the winner
            System.out.println(players.get(winner).getName() + " won the round and has " + players.get(winner).getgreens() + " green cards!"); // Prints the winner
        } else {
            int winner = players.get(currentplayer).pickwinner(played); // Picks a winner for the computer
            players.get(winner).addgreen(); // Adds a green card to the winner
            System.out.println(players.get(winner).getName() + " won the round and has " + players.get(winner).getgreens() + " green cards!"); // Prints the winner
        }
    }

    private static void earlyturn(ArrayList<player> players, String greencard, int i) {
        System.out.println("\n\n" + players.get(i).getName() + "'s turn"); // Prints the player's name
        System.out.println("You have " + players.get(i).getgreens() + " green cards."); // Prints the number of green cards the player has
        System.out.println("Your cards are: "); // Prints the player's cards
        for (int j = 0; j < players.get(i).getcards().size(); j++) { // Loops through the player's cards
            System.out.println((j + 1) + ": " + players.get(i).getcards().get(j)); // Prints the card
        }
        System.out.println("The green card is: " + greencard); // Prints the green card
    }

    private static void lateturn(ArrayList<player> players, ArrayList<String> red, ArrayList<String> played, int i, int card) {
        System.out.println("You played: " + players.get(i).getcards().get(card)); // Prints the card played
        played.add(players.get(i).getcards().get(card)); // Adds the card to the played cards
        players.get(i).removeCard(card); // Removes the card from the player's hand
        draw(players.get(i), red); // Draws a new card
        if (currentplayer == players.size() - 1) { // If it is the last player's turn
            currentplayer = 0; // Sets the current player to the first player
        } else { // If it is not the last player's turn
            currentplayer++; // Increments the current player
        }
    }

    public static int winner(ArrayList<player> players) { // Checks if a player has won
        for (int i = 0; i < players.size(); i++) { // Loops through the players
            if (players.get(i).getgreens() >= 5) { // If the player has 5 green cards
                winner = true; // Sets winner to true
                return i; // Returns the player's number
            }
        }
        return 0;
    }

    public static void draw (player p, ArrayList<String> red) { // Draws a new card
        int index = (int) Math.random() * red.size(); // Picks a random card
        p.addCard(red.get(index)); // Adds the card to the player's hand
        red.remove(index); // Removes the card from the deck
    }

    public static void makebots(int num) {
        for (int i = 0; i < num; i++) {
            players.add(new player());
            players.get(i).setName("Bot " + (i + 1));
            players.get(i).setComputer(true);
        }
    }

}