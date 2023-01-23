import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MastermindIO {
    public static String getHumanPlayerName() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Wat is je naam? : \u001B[32m");
            String input = scanner.nextLine();

            if (input.length() < 2) {
                System.out.println("\u001B[0mfout => een naam moet minstens 2 letters zijn!");
                continue;
            }

            if (!input.matches("[a-zA-Z]+")) {
                String nonLetters = input.replaceAll("[a-zA-Z]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' zijn geen letters!");
                } else {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' is geen letter!");
                }
                continue;
            }

            String playerName = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

            System.out.println("\u001B[0mWelkom " + playerName + "!");
            scanner.close();
            return playerName;
        }
    }

    public static int getCodeLengthInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mLengte van de geheime code : \u001B[32m");
            String input = scanner.nextLine();

            if (input.matches("[4-6]")) {
                scanner.close();
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 4 en 6");
        }
    }

    public static int getMaxTurnsInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mHet maximaal aantal raadpogingen : \u001B[32m");
            String input = scanner.nextLine();

            if (input.matches("20|9|1[0-9]")) {
                scanner.close();
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 9 en 20");
        }
    }

    public static int getAmountOfCharactersInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mAantal mogelijke letters : \u001B[32m");
            String input = scanner.nextLine();

            if (input.matches("[2-8]")) {
                scanner.close();
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 2 en 8");
        }
    }

    public static boolean getAllowDuplicatesInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mLetters kunnen vaker gebruikt worden in een code? (y/n) : \u001B[32");
            String input = scanner.nextLine();

            input = input.toLowerCase();

            if (input.equals("y") || input.equals("n")) {
                scanner.close();
                return input.equals("y") ? true : false;
            }

            System.out.println("\u001B[0mfout => '" + input + "' is geen geldige invoer!");
        }
    }

    public static String getHeadsOrTails() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mWil je kop of munt? : \u001B[32m");
            String input = scanner.nextLine();

            input = input.toLowerCase();

            if (input.equals("kop") || input.equals("munt")) {
                scanner.close();
                return input;
            }

            System.out.println("\u001B[0mfout => '" + input + "' is geen geldige invoer!");
        }
    }

    public static String getPlayerGuess(MastermindGame game) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mraad de code : \u001B[32m");
            String input = scanner.nextLine();

            input = input.toUpperCase();

            if (input.length() != game.getCodeLength()) {
                System.out.println("\u001B[0mfout => de code bestaat uit " + game.getCodeLength() + " letters!");
                continue;
            }

            List<Character> possibleCharacters = game.getPossibleCharacters();
            Character lastCharacter = possibleCharacters.get(possibleCharacters.size() - 1);

            if (!input.matches("[A-" + lastCharacter + "]+")) {
                String nonLetters = input.replaceAll("[A-" + lastCharacter + "]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' zijn geen geldige letters!");
                } else {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' is geen geldige letter!");
                }
                continue;
            }

            if (!game.getAllowDuplicates()) {
                boolean duplicate = false;
                for (int i = 0; i < input.length(); i++) {
                    String letter = input.substring(i, i + 1);
                    if (input.indexOf(letter) != input.lastIndexOf(letter)) {
                        System.out.println("\u001B[0mfout => '" + letter + "' komt meerdere keren voor in de code!");
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    continue;
                }
            }

            scanner.close();
            return input;
        }
    }

    public static int getPlayerBlackPins(MastermindGame game) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mGeef het aantal zwarte pins : \u001B[32m");
            String blackPins = scanner.nextLine();

            if (blackPins.matches("[0-" + game.getCodeLength() + "]")) {
                scanner.close();
                return Integer.parseInt(blackPins);
            }

            System.out
                    .println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
        }
    }

    public static int getPlayerWhitePins(MastermindGame game, int blackPins) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0mGeef het aantal witte pins : \u001B[32m");
            String whitePins = scanner.nextLine();

            if (!whitePins.matches("[0-" + game.getCodeLength() + "]")) {
                System.out.println(
                        "\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
                continue;
            }

            if ((Integer.parseInt(whitePins) + blackPins) > game.getCodeLength()) {
                System.out.println("\u001B[0mfout => som van pins moet tussen 0 en " + game.getCodeLength() + " zijn!");
                continue;
            }

            scanner.close();
            return Integer.parseInt(whitePins);
        }
    }

    public static void getEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\u001B[0m[ENTER] om door te gaan.");
            String input = scanner.nextLine();

            if (input.equals("")) {
                break;
            }
        }
        scanner.close();
    }

    public static void printGameBoard(HashMap<Integer, HashMap<String, List<Integer>>> guesses) {

        System.out.println("\u001B[0m------------------------------\r\n"
                + "|    Mastermind speelbord    |\r\n"
                + "------------------------------");

        for (int key : guesses.keySet()) {
            for (String guess : guesses.get(key).keySet()) {
                List<Integer> values = guesses.get(key).get(guess);
                System.out.println("  " + key + ": " + guess
                        + "  zwart: " + values.get(0)
                        + "  wit: " + values.get(1));
            }
        }

        System.out.println("------------------------------");
    }
}