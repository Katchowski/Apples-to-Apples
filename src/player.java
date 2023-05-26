import java.util.ArrayList;

public class player {

    ArrayList<String> cards = new ArrayList<>(); // The cards in the player's hand
    private int greens = 0; // The number of green cards the player has
    private String name; // The player's name
    boolean computer = false; // If the player is a computer
    public  player () {

    }

    public void addCard(String card) { // Adds a card to the player's hand
        cards.add(card);
    }

    public ArrayList<String> getcards() { // Returns the player's hand
        return cards;
    }

    public int getgreens() { // Returns the number of green cards the player has
        return greens;
    }

    public void removeCard(int card) { // Removes a card from the player's hand
        cards.remove(card);
    }

    public void addgreen() { // Adds a green card to the player's hand
        greens++;
    }

    public void setName(String name) { // Sets the player's name
        this.name = name;
    }

    public String getName() { // Returns the player's name
        return name;
    }

    public boolean isComputer() { // Returns if the player is a computer
        return computer;
    }

    public void setComputer(boolean computer) { // Sets if the player is a computer
        this.computer = computer;
    }

    public int pickcard() {
        return (int) (Math.random() * (cards.size() - 1));
    }

    public int pickwinner(ArrayList<String> played) {
        return (int) (Math.random() * (played.size() - 1));
    }
}
