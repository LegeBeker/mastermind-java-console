import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MastermindIO {

    private static Scanner scanner = new Scanner(System.in);

    public static String getHumanPlayerName() {
        while (true) {
            System.out.print("Wat is je naam? : ");
            String input = scanner.nextLine();

            if (input.length() < 2) {
                System.out.println("fout => een naam moet minstens 2 letters zijn!");
                continue;
            }

            if (!input.matches("[a-zA-Z]+")) {
                String nonLetters = input.replaceAll("[a-zA-Z]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("fout => '" + nonLetters + "' zijn geen letters!");
                } else {
                    System.out.println("fout => '" + nonLetters + "' is geen letter!");
                }
                continue;
            }

            String playerName = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

            System.out.println("Welkom " + playerName + "!");
            return playerName;
        }
    }

    public static int getCodeLengthInput() {
        while (true) {
            System.out.print("Lengte van de geheime code : ");
            String input = scanner.nextLine();

            if (input.matches("[4-6]")) {
                return Integer.parseInt(input);
            }

            System.out.println("fout => invoer is ongeldig! Geef een getal tussen 4 en 6");
        }
    }

    public static int getMaxTurnsInput() {
        while (true) {
            System.out.print("Het maximaal aantal raadpogingen : ");
            String input = scanner.nextLine();

            if (input.matches("20|9|1[0-9]")) {
                return Integer.parseInt(input);
            }

            System.out.println("fout => invoer is ongeldig! Geef een getal tussen 9 en 20");
        }
    }

    public static int getAmountOfCharactersInput() {
        while (true) {
            System.out.print("Aantal mogelijke letters : ");
            String input = scanner.nextLine();

            if (input.matches("[2-8]")) {
                return Integer.parseInt(input);
            }

            System.out.println("fout => invoer is ongeldig! Geef een getal tussen 2 en 8");
        }
    }

    public static boolean getAllowDuplicatesInput() {
        while (true) {
            System.out.print("Letters kunnen vaker gebruikt worden in een code? (y/n) : \u001B[32");
            String input = scanner.nextLine();

            input = input.toLowerCase();

            if (input.equals("y") || input.equals("n")) {
                return input.equals("y") ? true : false;
            }

            System.out.println("fout => '" + input + "' is geen geldige invoer!");
        }
    }

    public static String getHeadsOrTails() {
        while (true) {
            System.out.print("Wil je kop of munt? : ");
            String input = scanner.nextLine();

            input = input.toLowerCase();

            if (input.equals("kop") || input.equals("munt")) {
                return input;
            }

            System.out.println("fout => '" + input + "' is geen geldige invoer!");
        }
    }

    public static String getPlayerGuess(MastermindGame game) {
        while (true) {
            System.out.print("raad de code : ");
            String input = scanner.nextLine();

            input = input.toUpperCase();

            if (input.length() != game.getCodeLength()) {
                System.out.println("fout => de code bestaat uit " + game.getCodeLength() + " letters!");
                continue;
            }

            List<Character> possibleCharacters = game.getPossibleCharacters();
            Character lastCharacter = possibleCharacters.get(possibleCharacters.size() - 1);

            if (!input.matches("[A-" + lastCharacter + "]+")) {
                String nonLetters = input.replaceAll("[A-" + lastCharacter + "]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("fout => '" + nonLetters + "' zijn geen geldige letters!");
                } else {
                    System.out.println("fout => '" + nonLetters + "' is geen geldige letter!");
                }
                continue;
            }

            if (!game.getAllowDuplicates()) {
                boolean duplicate = false;
                for (int i = 0; i < input.length(); i++) {
                    String letter = input.substring(i, i + 1);
                    if (input.indexOf(letter) != input.lastIndexOf(letter)) {
                        System.out.println("fout => '" + letter + "' komt meerdere keren voor in de code!");
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    continue;
                }
            }

            return input;
        }
    }

    public static int getPlayerBlackPins(MastermindGame game) {
        while (true) {
            System.out.print("Geef het aantal zwarte pins : ");
            String blackPins = scanner.nextLine();

            if (blackPins.matches("[0-" + game.getCodeLength() + "]")) {
                return Integer.parseInt(blackPins);
            }

            System.out
                    .println("fout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
        }
    }

    public static int getPlayerWhitePins(MastermindGame game, int blackPins) {
        while (true) {
            System.out.print("Geef het aantal witte pins : ");
            String whitePins = scanner.nextLine();

            if (!whitePins.matches("[0-" + game.getCodeLength() + "]")) {
                System.out.println(
                        "fout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
                continue;
            }

            if ((Integer.parseInt(whitePins) + blackPins) > game.getCodeLength()) {
                System.out.println("fout => som van pins moet tussen 0 en " + game.getCodeLength() + " zijn!");
                continue;
            }

            return Integer.parseInt(whitePins);
        }
    }

    public static void getEnterToContinue() {
        while (true) {
            System.out.print("[ENTER] om door te gaan.");
            String input = scanner.nextLine();

            if (input.equals("")) {
                break;
            }
        }
    }

    public static void printGameBoard(HashMap<Integer, HashMap<String, List<Integer>>> guesses) {

        System.out.println("------------------------------\r\n"
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