/**
 * COMP10205 - Lab#3 Starter Code
 *
 * @author C. Mark Yendt
 * @version 1.2 (April 2016, February 2019)
 */
package comp10205_Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Logger;

public class Comp10205_Lab3_Start {

    /*
     * program shows that HashSet is faster than an ArrayList contains and ArrayList binary search.
     * and binary search is faster than using contains.
     */
    // this section for all static members.
    public static Scanner fin = null;
    public static int totalWordsInNovel = 0;
    public static int posistion = 0;
    public static int totalUniqueWordsInNovel = 0;
    public static int wordsNotContainedInDic1 = 0;
    public static int wordsNotContainedInDic2 = 0;
    public static int wordsNotContainedInDic3 = 0;

    /**
     * The starting point of the application
     *
     * @param args the command line arguments - not used
     */
    public static void main(String[] args) {
        // Define Dictionaries
        ArrayList<BookWord> bookWords = new ArrayList<>();
        SimpleHashSet<String> dicWords = new SimpleHashSet<>();
        ArrayList<String> dicWordsArr = new ArrayList<>();
        ArrayList<NovelCharacter> novelCharacters = new ArrayList<>();
        HashSet<Integer> ringPositions = new HashSet<>();

        try {
            // Read dictionary words in the SimpleHashSet and ArrayList.
            readDictionaryFile(Common.dictionaryFile, dicWords, dicWordsArr);
            // Read novel characters from the file. 
            readNovelCharacterFile(Common.novelCharactersFile, novelCharacters);
            // Read novel words.
            handleNovelFile(Common.novelName, bookWords, novelCharacters, ringPositions);
            // Execute Part A.
            RunHashingCountingSpelling(dicWords, dicWordsArr, bookWords);
            // Execute Part B.
            runLordOfRings(novelCharacters, ringPositions);

        } catch (FileNotFoundException ex) {
            Logger.getLogger("Exception is : " + ex);
        }

    }

    /**
     * Read all words from novel and assign it to book words.
     *
     * @param filePath that contain novel words.
     * @param bookWords list of bookWords from novel file.
     * @param novelCharacters list of novel characters.
     * @param ringPositions list that contains all positions of rings.
     * @throws java.io.FileNotFoundException
     */
    public static void handleNovelFile(String filePath, ArrayList<BookWord> bookWords, ArrayList<NovelCharacter> novelCharacters, HashSet<Integer> ringPositions) throws FileNotFoundException {

        try {
            fin = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
            throw ex;
        }
        fin.useDelimiter("\\s|\"|\\(|\\)|\\.|\\,|\\?|\\!|\\_|\\-|\\:|\\;|\\n");  // Filter - DO NOT CHANGE 
        while (fin.hasNext()) {
            String fileWord = fin.next().toLowerCase();
            if (fileWord.length() > 0) {
                totalWordsInNovel++;
                BookWord newBookWord = new BookWord(fileWord);
                boolean isFound = false;
                for (BookWord bookWord : bookWords) {
                    if (bookWord.equals(newBookWord)) {
                        bookWord.incrementalCount();
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    totalUniqueWordsInNovel++;
                    newBookWord.incrementalCount();
                    bookWords.add(newBookWord);
                }

                posistion++;
                if (fileWord.equalsIgnoreCase("Ring")) {
                    ringPositions.add(posistion);
                    continue;
                }
                for (NovelCharacter character : novelCharacters) {

                    if (character.getCharacterName().trim().equalsIgnoreCase(fileWord)) {
                        character.incrementNumberofOccurrences();
                        character.AddNewCharacterPosition(posistion);
                    }
                }

                // TODO : Need to create an instance of a BookWord object here and add to ArrayList
            }
        }

    }

    /**
     * Read all words from dictionary.
     *
     * @param filePath that contain dictionary.
     * @param dicWords list of words from dictionary file and assign it to
     * Simple HashSet..
     * @param dicWordsArr list of words from dictionary file and assign it to
     * Array List..
     *
     * @return void
     * @throws java.io.FileNotFoundException
     */
    public static void readDictionaryFile(String filePath, SimpleHashSet<String> dicWords, ArrayList<String> dicWordsArr) throws FileNotFoundException {
        try {
            fin = new Scanner(new File(filePath), "UTF-8");
            while (fin.hasNext()) {
                String dicWord = fin.next();
                if (dicWord.length() > 0) {
                    dicWords.insert(dicWord);
                    dicWordsArr.add(dicWord);
                }
            }
            fin.close();
        } catch (FileNotFoundException e) {
            throw e;
        }

    }

    /**
     * Read all novel character from file.
     *
     * @param filePath that contain dictionary.
     * @param novelCharacters list of novel characters from novel characters
     * file and assign it to Simple Array List.
     *
     * @return void
     * @throws java.io.FileNotFoundException
     */
    public static void readNovelCharacterFile(String filePath, ArrayList<NovelCharacter> novelCharacters) throws FileNotFoundException {
        try {
            fin = new Scanner(new File(filePath), "UTF-8");
            while (fin.hasNext()) {
                String charachter = fin.next();
                if (charachter.length() > 0) {
                    NovelCharacter novelCharacter = new NovelCharacter(charachter);
                    novelCharacters.add(novelCharacter);
                }
                // TODO : Add code to store dictionary words into an appropriate data structure
            }
            fin.close();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    /**
     * This method is responsible to Run Part A.
     *
     * @param dicWords list of simple hash set contains dictionary words from
     * dictionary file.
     * @param dicWordsArr list of array list contains dictionary words from
     * dictionary file..
     * @param bookWords list of novel characters.
     *
     * @return void
     */
    public static void RunHashingCountingSpelling(SimpleHashSet<String> dicWords, ArrayList<String> dicWordsArr, ArrayList<BookWord> bookWords) {
        Collections.sort(bookWords, new SortByBookWord().reversed());
        long startTime = System.nanoTime();
        countWordsUsingContains(dicWordsArr, bookWords);
        long totalTime = System.nanoTime() - startTime;
        //  System.out.println("contains:" + totalTime / 1000000 + "ms");
        startTime = System.nanoTime();
        countWordsUsingBSearch(dicWordsArr, bookWords);
        totalTime = System.nanoTime() - startTime;
        // System.out.println("binary:" + totalTime / 1000000 + "ms");
        startTime = System.nanoTime();
        countWordsUsingSimpleHash(dicWords, bookWords);
        totalTime = System.nanoTime() - startTime;
        //   System.out.println("SimpleHash:" + totalTime / 1000000 + "ms");
        displayTotalWords();
        displayTotalUniqueWords();
        displayTop10Words(bookWords);
        displayWords64(bookWords);
        displayWordsNotContainedInDic();
    }

    /**
     * This method is responsible to count the words not contains in dictionary
     * using contain.
     *
     * @param dicWordsArr contains array list of dictionary words.
     * @param bookWords contains array list of book words.
     * @return void
     */
    public static void countWordsUsingContains(ArrayList<String> dicWordsArr, ArrayList<BookWord> bookWords) {
        for (BookWord word : bookWords) {
            if (!dicWordsArr.contains(word.getText())) {
                wordsNotContainedInDic1++;
            }
        }
    }

    /**
     * This method is responsible to count the words not contain in dictionary
     * using binary search.
     *
     * @param dicWordsArr contains array list of dictionary words.
     * @param bookWords contains array list of book words.
     * @return void
     */
    public static void countWordsUsingBSearch(ArrayList<String> dicWordsArr, ArrayList<BookWord> bookWords) {
        Collections.sort(dicWordsArr);
        int result = 0;
        for (BookWord word : bookWords) {
            result = Collections.binarySearch(dicWordsArr, word.getText());
            if (result < 0) {
                wordsNotContainedInDic2++;
            }
        }
    }

    /**
     * This method is responsible for counting the words not contain in dictionary
     * using simple hash set[ contain.
     *
     * @param dictoinaryWords contains simple hash set of dictionary words.
     * @param bookWords contains array list of book words.
     * @return void
     */
    public static void countWordsUsingSimpleHash(SimpleHashSet<String> dictoinaryWords, ArrayList<BookWord> bookWords) {
        for (BookWord word : bookWords) {
            if (!dictoinaryWords.contains(word.getText())) {
                wordsNotContainedInDic3++;
            }
        }
    }

    /**
     * This method is responsible to print list of the 10 most frequent words and
     * counts . times
     *
     * @param bookWords display top 10 words.
     */
    public static void displayTop10Words(ArrayList<BookWord> bookWords) {
        System.out.printf(" # The list of the 10 most frequent words and counts : \n");

        for (int i = 0; i < 10; i++) {
            System.out.printf("\t\t\t\t\t\t@%s\n", bookWords.get(i));
        }
    }

    /**
     * This method is responsible to print list of words that occur exactly 64
     * times
     *
     * @param bookWords display 64 time words.
     */
    public static void displayWords64(ArrayList<BookWord> bookWords) {
        System.out.printf(" # The list of words that occur exactly 64 times : \n");
        for (BookWord word : bookWords) {
            if (word.getCount() == 64) {
                System.out.printf("\t\t\t\t\t\t@%s\n", word.toString());
            }
        }
    }

    /**
     * This method is responsible to print Total Words not in dictionary.
     *
     * @return void
     */
    public static void displayWordsNotContainedInDic() {
        System.out.printf(" # Words not in dictionary using Contains : %d\n", wordsNotContainedInDic1);
        System.out.printf(" # Words not in dictionary using Binary Search : %d\n", wordsNotContainedInDic2);
        System.out.printf(" # Words not in dictionary using SimpleHashSet : %d\n", wordsNotContainedInDic3);
    }

    /**
     * This method is responsible for printing Total Words In The Novel.
     *
     * @return void
     */
    public static void displayTotalWords() {
        System.out.println("PART A - HASHING / COUNTING AND SPELLING\n__________________________________________");
        System.out.printf(" # Total Words In The Novel : %d\n", totalWordsInNovel);

    }

    /**
     * This method responsible for print Total Unique Words In The Novel.
     *
     * @return void
     */
    public static void displayTotalUniqueWords() {
        System.out.printf(" # Total Unique Words In The Novel : %d\n", totalUniqueWordsInNovel);
    }

    /**
     * This method is responsible to Run Part B.
     *
     * @param novelCharacters list contains dictionary novel Characters.
     * @param ringPositions list contains Positions of rings word.
     *
     * @return void
     */
    public static void runLordOfRings(ArrayList<NovelCharacter> novelCharacters, HashSet<Integer> ringPositions) {
        CalculateProximityDistance(novelCharacters, ringPositions);
        Collections.sort(novelCharacters, new SortByClosenessFactor().reversed());
        displayClosenessFactors(novelCharacters);
    }

    /**
     * This method is responsible to calculate proximity distance.
     *
     * @param novelCharacters list contains dictionary novel Characters.
     * @param ringPositions list contains Positions of rings word.
     *
     * @return void
     */
    public static void CalculateProximityDistance(ArrayList<NovelCharacter> novelCharacters, HashSet<Integer> ringPositions) {
        for (NovelCharacter novelCharacter : novelCharacters) {
            for (Integer characterPosition : novelCharacter.getCharacterPositions()) {
                for (Integer ringPosition : ringPositions) {
                    if (Math.abs(ringPosition - characterPosition) <= 42) {
                        novelCharacter.incrementClosenessCount();
                    }
                }
            }
            novelCharacter.setClosenessFactor((float) novelCharacter.getClosenessCount() / (float) novelCharacter.getNumberofOccurrences());
        }
    }

    /**
     * This method is responsible for Printing.
     *
     * @param novelCharacters list contains dictionary novel Characters.
     *
     * @return void
     */
    public static void displayClosenessFactors(ArrayList<NovelCharacter> novelCharacters) {
        System.out.println("\n\nPART B - THE LORD OF THE RINGS\n___________________________________");
        for (NovelCharacter novelCharacter : novelCharacters) {
            System.out.format(" # [%s,%d] Close to Ring %d Closeness Factor %.4f\n\n", novelCharacter.getCharacterName(),
                    novelCharacter.getNumberofOccurrences(), novelCharacter.getClosenessCount(), novelCharacter.getClosenessFactor());
        }
    }
}
