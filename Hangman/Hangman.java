import java.io.*;
import java.util.*;

public class Hangman {
    private static int numFails = 0;
    private static int counter = 0;
    private static String[] word;
    private static ArrayList<String> alreadyGuessed = new ArrayList<String>();;

    public static void main(String args[]) {
        Scanner myScan = new Scanner(System.in);

        System.out.print("Welcome to Hangman, please pick a word length: ");
        int wordLength = myScan.nextInt();

        while ((wordLength > 16) || (wordLength < 2)) {
            System.out.println("Please choose a number between 2 and 16");
            wordLength = myScan.nextInt();
        }

        String gameWord = wordGenerator(wordLength);
        // System.out.println("The word is: " + gameWord);

        System.out.println("A " + wordLength + " letter word has been chosen.");

        word = new String[gameWord.length()];
        for (int x = 0; x < word.length; x++) {
            word[x] = "_";
        }

        while (numFails < 7) {
            System.out.print("Please guess one letter (NOTE: only the first letter will be taken): ");
            String stringGuess = myScan.next();

            for (int x = 0; x < alreadyGuessed.size(); x++) { // to stop user putting in same letter twice
                if (alreadyGuessed.get(x).equals(stringGuess)) {
                    System.out.println(stringGuess + " has already been chosen, please choose a different letter");
                    stringGuess = myScan.next();
                }
            }

            char guess = stringGuess.charAt(0); // converted for isPresent method
            boolean answer = isPresent(guess, gameWord);
            hangDrawing(answer, gameWord);
            wordPrint(gameWord, guess);
        }

    }

    /*
     * This method prints out the word as "_", but when user guesses letter right,
     * it shows the letter in the word. eg. for the word "dog": before guesses
     * "___", user guesses "o", "_o_" is shown. This method also keeps track of the
     * already guessed letters with the alreadyGuessed arraylist.
     */
    public static void wordPrint(String gameWord, char guess) {
        String[] letterArray = new String[gameWord.length()];

        String guessedLetter = String.valueOf(guess);
        alreadyGuessed.add(guessedLetter);

        System.out.print("Letters already guessed: ");
        for (int x = 0; x < alreadyGuessed.size(); x++) {
            System.out.print(" " + alreadyGuessed.get(x) + " ");
        }
        System.out.print("\n");

        System.out.print("The word: ");
        for (int x = 0; x < letterArray.length; x++) {
            String letter = String.valueOf(gameWord.charAt(x));
            letterArray[x] = letter;

            if (letter.equals(guessedLetter)) {
                word[x] = letter;
                counter++; // keeping track of right guesses
            }

            System.out.print(word[x]);
        }
        System.out.print("\n");

        /*
         * End game if/else statement
         */
        if (counter == gameWord.length()) {
            System.out.println("You win!");
            numFails = 7;
        } else if (numFails >= 7) {
            System.out.println("Game Over!");
            System.out.println("The word was: " + gameWord);
        }
    }

    /*
     * Checks if the guessed letter is in the word.
     */
    public static boolean isPresent(char letter, String gameWord) {
        for (int x = 0; x < gameWord.length(); x++) {
            if (gameWord.charAt(x) == letter) {
                System.out.println(letter + " is in this word!");
                return true;
            }
        }
        System.out.println(letter + " is not in this word...");
        return false;
    }

    /*
     * This method finds all words of the specified length and outputs a random one
     */
    public static String wordGenerator(int wordLength) {
        FileIO reader = new FileIO();
        String[] contents = reader.load("dictionary.txt");

        ArrayList<String> wordsToPick = new ArrayList<String>();

        for (int x = 0; x < contents.length; x++) {
            if (contents[x].length() == wordLength) {
                wordsToPick.add(contents[x]);
            }
        }

        Random rand = new Random();

        int randNum = rand.nextInt(wordsToPick.size());

        String pickedWord = wordsToPick.get(randNum);

        return pickedWord;
    }

    /*
     * This method creates the hangman drawing, adding to the man if an answer is
     * wrong A 2D array is used to create the drawing
     */
    public static void hangDrawing(boolean answer, String gameWord) {
        int count = 0;

        char[][] drawing = new char[7][6];
        drawing[0][0] = ' ';
        drawing[0][1] = '-';
        drawing[0][2] = '-';
        drawing[0][3] = '-';
        drawing[0][4] = '¬';
        drawing[0][5] = ' ';

        drawing[1][0] = ' ';
        drawing[1][1] = '|';
        drawing[1][2] = ' ';
        drawing[1][3] = ' ';
        drawing[1][4] = ' ';
        drawing[1][5] = ' ';

        drawing[2][0] = ' ';
        drawing[2][1] = '|';
        drawing[2][2] = ' ';
        drawing[2][3] = ' ';
        drawing[2][4] = ' ';
        drawing[2][5] = ' ';

        drawing[3][0] = ' ';
        drawing[3][1] = '|';
        drawing[3][2] = ' ';
        drawing[3][3] = ' ';
        drawing[3][4] = ' ';
        drawing[3][5] = ' ';

        drawing[4][0] = ' ';
        drawing[4][1] = '|';
        drawing[4][2] = ' ';
        drawing[4][3] = ' ';
        drawing[4][4] = ' ';
        drawing[4][5] = ' ';

        drawing[5][0] = ' ';
        drawing[5][1] = '|';
        drawing[5][2] = ' ';
        drawing[5][3] = ' ';
        drawing[5][4] = ' ';
        drawing[5][5] = ' ';

        drawing[6][0] = '-';
        drawing[6][1] = '-';
        drawing[6][2] = '-';
        drawing[6][3] = '-';
        drawing[6][4] = ' ';
        drawing[6][5] = ' ';

        if (answer == false) {
            numFails++;
            if (numFails >= 1) {
                drawing[1][4] = 'O';
            }
            if (numFails >= 2) {
                drawing[2][4] = '|';
            }
            if (numFails >= 3) {
                drawing[2][3] = '/';
            }
            if (numFails >= 4) {
                drawing[2][5] = '\\';
            }
            if (numFails >= 5) {
                drawing[3][4] = '|';
            }
            if (numFails >= 6) {
                drawing[4][3] = '/';
            }
            if (numFails >= 7) {
                drawing[4][5] = '\\';
            }
        }

        else {
            if (numFails >= 1) {
                drawing[1][4] = 'O';
            }
            if (numFails >= 2) {
                drawing[2][4] = '|';
            }
            if (numFails >= 3) {
                drawing[2][3] = '/';
            }
            if (numFails >= 4) {
                drawing[2][5] = '\\';
            }
            if (numFails >= 5) {
                drawing[3][4] = '|';
            }
            if (numFails >= 6) {
                drawing[4][3] = '/';
            }
            if (numFails >= 7) {
                drawing[4][5] = '\\';
            }
        }

        /*
         * Prints out the Hangman
         */
        for (int rows = 0; rows < 7; rows++) {
            for (int columns = 0; columns < 6; columns++) {
                System.out.print(drawing[rows][columns]);
            }
            System.out.println(" ");
        }

    }
}

/*
 * This class reads in the dictionary.txt file, so that the words in the file
 * can be used in the game
 */
class FileIO {

    public String[] load(String file) {
        File aFile = new File(file);
        StringBuffer contents = new StringBuffer();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(aFile));
            String line = null;
            int i = 0;
            while ((line = input.readLine()) != null) {
                contents.append(line);
                i++;
                contents.append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find the file - are you sure the file is in this location: " + file);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Input output exception while processing file");
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            }
        }
        String[] array = contents.toString().split("\n");
        for (String s : array) {
            s.trim();
        }
        return array;
    }

    public void save(String file, String[] array) throws FileNotFoundException, IOException {

        File aFile = new File(file);
        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(aFile));
            for (int i = 0; i < array.length; i++) {
                output.write(array[i]);
                output.write(System.getProperty("line.separator"));
            }
        } finally {
            if (output != null)
                output.close();
        }
    }
}