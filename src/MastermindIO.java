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

    public static String getHeadOrTale() {
        while (true) {
            String preference = System.console().readLine("Wil je kop of munt? : \u001B[32m");

            preference = preference.toLowerCase();

            if (!preference.equals("kop") && preference.equals("munt")) {
                System.out.println("\u001B[0mfout => '" + preference + "' is geen geldige invoer!");
                continue;
            }
            return preference;
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

            if (!guess.matches("[A-F]+")) {
                String nonLetters = guess.replaceAll("[A-F]", "");

                if (nonLetters.length() > 1) {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' zijn geen geldige letters!");
                } else {
                    System.out.println("\u001B[0mfout => '" + nonLetters + "' is geen geldige letter!");
                }
                continue;
            }

            // TODO: If game.getAllowDuplicates() is true, use every letter once per code

            return guess;
        }
    }

    public static int getPlayerBlackPins(MastermindGame game) {
        while (true) {
            String blackPins = System.console().readLine("\u001B[0mGeef het aantal zwarte pins : \u001B[32m");

            if (!blackPins.matches("[0-" + game.getCodeLength() + "]")) {
                System.out.println(
                        "\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en " + game.getCodeLength());
                continue;
            }

            return Integer.parseInt(blackPins);
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
            } else {
                continue;
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