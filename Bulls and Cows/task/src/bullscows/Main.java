package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean valid = false;
        int length = 0;
        int symbols = 0;
        String code = "initialized";
        String[] allSymbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        while (!valid) {
            System.out.println("Input the length of the secret code:\n");
            String s = sc.nextLine();
            try {
                length = Integer.parseInt(s);
                // length = sc.nextInt();
            } catch (RuntimeException e) {
                System.out.println("Error: \"" + s + "\" isn't a valid number.");
                System.exit(0);
            }

            if (length >= 37) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            } else if (length == 0) {
                System.out.println("Error: number of possible symbols must be greater than 0.");
                System.exit(0);
            } else {
                System.out.println("Input the number of possible symbols in the code:\n");
                symbols = sc.nextInt();
                if (symbols < length) {
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, symbols);
                    System.exit(0);
                } else if (symbols >= 37) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    System.exit(0);
                }
                String[] possibleSymbols = new String[symbols];
                for (int i = 0; i < symbols; i++) {
                    possibleSymbols[i] = allSymbols[i];
                    // System.out.println(possibleSymbols[i]);
                }
                Random r = new Random();
                int rNum;
                StringBuilder sbCode = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    rNum = r.nextInt(possibleSymbols.length);
                    boolean validElement = false;
                    while (!validElement) {
                        if (possibleSymbols[rNum] != null) {
                            // System.out.print(possibleSymbols[rNum]);
                            sbCode.append(possibleSymbols[rNum]);
                            possibleSymbols[rNum] = null;
                            validElement = true;
                        } else {
                            rNum = r.nextInt(possibleSymbols.length);
                        }
                    }
                }

                /*
                int[] nums = ThreadLocalRandom.current().ints(0, 10).distinct().limit(length).toArray();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    sb.append(nums[i]);
                }
                code = sb.toString();

                 */
                code = sbCode.toString();
                valid = true;
            }
        }
        System.out.print("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        System.out.print(" (0-9, a-" + allSymbols[symbols - 1] + ").\n");

        System.out.println("Okay, let's start a game!");

        int count = 1;
        boolean end = false;
        while (!end) {
            System.out.printf("Turn %d:", count);
            System.out.println("");
            end = grader(code);
            count++;
        }
    }

    public static boolean grader(String secretString) {
        int bulls = 0;
        int cows = 0;
        Scanner sc = new Scanner(System.in);
        String userString = sc.next();
        for (int i = 0; i < secretString.length(); i++) {
            if (secretString.charAt(i) == userString.charAt(i)) {
                bulls += 1;
            } else {
                for (int j = 0; j < secretString.length(); j++) {
                    if (secretString.charAt(i) == userString.charAt(j)) {
                        cows += 1;
                    }
                }
            }
        }

        if (bulls != 0 && cows != 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s)", bulls, cows);
        } else if (bulls == 0 && cows != 0) {
            System.out.printf("Grade: %d cow(s)", cows);
        } else if (bulls != 0 && cows == 0) {
            System.out.printf("Grade: %d bull(s)", bulls);
        } else {
            System.out.print("Grade: None");
        }
        System.out.println("");

        boolean end = false;
        if (bulls == secretString.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            end = true;
        }
        return end;
    }
}
