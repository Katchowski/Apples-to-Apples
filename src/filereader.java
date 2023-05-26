import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class filereader {

    public filereader() {

    }

    public static  ArrayList<String> read (File file) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner greenread = new Scanner(file); // Reads the green cards from the file
            while (greenread.hasNextLine()) { // Adds the green cards to the arraylist
                String line = greenread.nextLine(); // Reads the next line
                lines.add(line); // Adds the line to the arraylist
            }
        } catch (FileNotFoundException e) { // If the file is not found
            System.out.println("File not found"); // Prints "File not found"
        }
        return lines;
    }
}
