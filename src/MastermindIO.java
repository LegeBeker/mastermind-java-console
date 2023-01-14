
public class MastermindIO {
    public String getHumanPlayerName() {
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

    public String getHeadOrTale() {
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

    public String getPlayerGuess() {
        while (true) {
            String guess = System.console().readLine("raad de code : \u001B[32m");

            guess = guess.toUpperCase();

            if (guess.length() != 4) {
                System.out.println("\u001B[0mfout => de code bestaat uit 4 letters!");
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

            return guess;
        }
    }

    public int getPlayerBlackPins() {
        while (true) {
            String blackPins = System.console().readLine("\u001B[0mGeef het aantal zwarte pins : \u001B[32m");

            if (!blackPins.matches("[0-4]")) {
                System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en 4");
                continue;
            }

            return Integer.parseInt(blackPins);
        }
    }

    public int getPlayerWhitePins(int blackPins) {
        while (true) {
            String whitePins = System.console().readLine("\u001B[0mGeef het aantal witte pins : \u001B[32m");

            if (!whitePins.matches("[0-4]")) {
                System.out.println("\u001B[0mfout => invoer is ongeldig! Geef een getal tussen 0 en 4");
                continue;
            }

            if ((Integer.parseInt(whitePins) + blackPins) > 4) {
                System.out.println("\u001B[0mfout => som van pins moet tussen 0 en 4 zijn!");
                continue;
            }

            return Integer.parseInt(whitePins);
        }
    }

    public void getEnterToContinue() {
        while (true) {
            String enter = System.console().readLine("\u001B[0m[ENTER] om door te gaan.");
            if (enter.equals("")) {
                break;
            } else {
                continue;
            }
        }
    }
}