package edu.cnm.deepdive.finalexam;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Partial implementation of a simple Keno game, with a payout table specified in
 * resources/payouts.txt
 */
public class Keno {

  /**
   * Classpath-relative location of payouts file.
   */
  public static final String PAYOUTS_RESOURCE = "resources/payouts.txt";

  private PayoutTable payoutTable;
  private static int kenoCatch;
  private static int wins;
  private static int losses;
  private static int[] userInput;
  private static List<Integer> housePicks = new ArrayList<>();
  private static String[] strings;
  private int[][] matrix;
  private int size = -1;
  private static int totalMatches = 0;

  /**
   * Entry point for Keno application.
   */
  public static void main(String[] args) throws IOException, URISyntaxException {

    ClassLoader loader = Keno.class.getClassLoader();
    new Keno(Paths.get(loader.getResource(PAYOUTS_RESOURCE).toURI()));

    try {
      BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));

      String line = br.readLine();
      strings = line.trim().split(" ");
      if (line.equals("")) {
        System.out.println("You must enter a number to play.");
      } else if (line.equals("-?") || line.equals("-help")) {
        System.out.println(
            "1. Choose a number from 1 to 80. \n" +
                "2. Pick no more 15 numbers. \n" +
                "3. You start with $100 in your account. \n");


      } else {
        userInput = new int[strings.length];
        for (int i = 0; i < userInput.length; i++) {
          userInput[i] = Integer.parseInt(strings[i]); //parsing from string to int
        }
        if (userInput.length > 15) {
          System.out.println("You may not select more than 15 numbers");//terminate

        } else {
          for (int i = 0; i < strings.length; i++) {
            for (int j = i + 1; j < strings.length; j++) {
              if (strings[i].equals(strings[j])) {
                System.out.print("duplicate number entered, unable to continue ");
              }
            }
          }
        }
        for (int x = 0; x < userInput.length;
            x++) { //checks user input of numbers to make sure not greater than 80 and not 0
          if (userInput[x] > 80 || userInput[x] == 0) {
            System.out.println("Numbers selected cannot be 0 or more than 80");
          }
        }
      }
      ArrayList<Integer> draw = getRandomNonRepeatingIntegers(20, 1, 80);
      matches();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * compares user entered numbers with house picked numbers
   */
  public static void matches() {

    for (int i = 0; i < userInput.length; i++) {
      if (housePicks.contains(userInput[i]))

      {
        totalMatches++;
      }
    }
    System.out.println("you matched: " + totalMatches);

  }

  /**
   *
   * @param min
   * @param max
   * @return
   */
  public static int getRandomInt(int min, int max) {
    Random random = new Random();

    return random.nextInt((max - min) + 1) + min;
  }

  /**
   * creates a random Array List of Integers not allowing for repeated numbers
   *
   * @param size the size of the array list
   * @param min the minimum integer value to pick from
   * @param max the maximum integer value to pick from
   * @return the Array List of Integers
   */
  public static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min, int max) {
    ArrayList<Integer> houseNumbers = new ArrayList<Integer>();

    while (houseNumbers.size() < size) {
      int random = getRandomInt(min, max);

      if (!houseNumbers.contains(random)) {
        houseNumbers.add(random);
      }

    }
    return houseNumbers;
  }

  /**
   * Initializes Keno instance by creating the payout table and (eventually) creating a number pool,
   * which will be shuffled and drawn from in each play.
   *
   * @param payoutsPath location of payout file.
   * @throws IOException if payout file cannot be found or read.
   */
  public Keno(Path payoutsPath) throws IOException {
    payoutTable = new PayoutTable(payoutsPath);

  }


}









