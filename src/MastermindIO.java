import java.util.HashMap;
import java.util.List;

public class MastermindIO {
    public static String getHumanPlayerName() {
        while (true) {
            String playerName = System.console().readLine("Wat is je naam? : \u001B[32m");

            if (playerName.length() < 2) {
                System.out.println("\u001B[0mfout => een naam moet minstens 2 letters zijn!");
                continue;
            }

            if (!playerName.matches("[a-zA-Z]+")) {
                String nonLetters = playerName.replaceAll("[a-zA-Z]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' zijn geen letters!");
                } else {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' is geen letter!");
                }
                continue;
            }

            playerName = playerName.substring(0, 1).toUpperCase() + playerName.substring(1).toLowerCase();

            System.out.println("\u001B[0mWelkom " + playerName + "!");
            return playerName;
        }
    }

    public static int getCodeLengthInput() {
        while (true) {
            String input = System.console().readLine("\u001B[0mLengte van de geheime code : \u001B[32m");

            if (input.matches("[4-6]")) {
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 4 en 6");
        }
    }

    public static int getMaxTurnsInput() {
        while (true) {
            String input = System.console().readLine("\u001B[0mHet maximaal aantal raadpogingen : \u001B[32m");

            if (input.matches("20|9|1[0-9]")) {
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 9 en 20");
        }
    }

    public static int getAmountOfCharactersInput() {
        while (true) {
            String input = System.console().readLine("\u001B[0mAantal mogelijke letters : \u001B[32m");

            if (input.matches("[2-8]")) {
                return Integer.parseInt(input);
            }

            System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 2 en 8");
        }
    }

    public static boolean getAllowDuplicatesInput() {
        while (true) {
            String input = System.console()
                    .readLine("\u001B[0mLetters kunnen vaker gebruikt worden in een code? (y/n) : \u001B[32m");

            input = input.toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }

            System.out.println("\u001B[0mfout => '" + input + "' is geen geldige invoer!");
        }
    }

    public static String getHeadOrTale() {
        while (true) {
            String preference = System.console().readLine("Wil je kop of munt? : \u001B[32m");

            preference = preference.toLowerCase();

            if (preference.equals("kop") || preference.equals("munt")) {
                return preference;
            }

            System.out.println("\u001B[0mfout => '" + preference + "' is geen geldige invoer!");
        }
    }

    public static String getPlayerGuess(MastermindGame game) {
        while (true) {
            String guess = System.console().readLine("raad de code : \u001B[32m");

            guess = guess.toUpperCase();

            if (guess.length() != game.getCodeLength()) {
                System.out.println("\u001B[0mfout => de code bestaat uit " + game.getCodeLength() + " letters!");
                continue;
            }

            List<Character> possibleCharacters = game.getPossibleCharacters();
            Character lastCharacter = possibleCharacters.get(possibleCharacters.size() - 1);

            if (!guess.matches("[A-" + lastCharacter + "]+")) {
                String nonLetters = guess.replaceAll("[A-" + lastCharacter + "]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' zijn geen geldige letters!");
                } else {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' is geen geldige letter!");
                }
                continue;
            }

            if (!game.getAllowDuplicates()) {
                for (int i = 0; i < guess.length(); i++) {
                    String letter = guess.substring(i, i + 1);
                    if (guess.indexOf(letter) != guess.lastIndexOf(letter)) {
                        System.out.println("\u001B[0mfout => '" + letter + "' komt meerdere keren voor in de code!");
                        continue;
                    }
                }
            }

            return guess;
        }
    }

    public static int getPlayerBlackPins(MastermindGame game) {
        while (true) {
            String blackPins = System.console().readLine("\u001B[0mGeef het aantal zwarte pins : \u001B[32m");

            if (blackPins.matches("[0-" + game.getCodeLength() + "]")) {
                return Integer.parseInt(blackPins);
            }

            System.out
                    .println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
        }
    }

    public static int getPlayerWhitePins(MastermindGame game, int blackPins) {
        while (true) {
            String whitePins = System.console().readLine("\u001B[0mGeef het aantal witte pins : \u001B[32m");

            if (!whitePins.matches("[0-" + game.getCodeLength() + "]")) {
                System.out.println(
                        "\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
                continue;
            }

            if ((Integer.parseInt(whitePins) + blackPins) > game.getCodeLength()) {
                System.out.println("\u001B[0mfout => som van pins moet tussen 0 en " + game.getCodeLength() + " zijn!");
                continue;
            }

            return Integer.parseInt(whitePins);
        }
    }

    public static void getEnterToContinue() {
        while (true) {
            String enter = System.console().readLine("\u001B[0m[ENTER] om door te gaan.");
            if (enter.equals("")) {
                break;
            }
        }
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