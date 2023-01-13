
public class GameHuman {

    public String name;
    public String preference;
    public String[] guesses = new String[9];
    public int turn = 0;
    public String code = "";
    static boolean CHEATMODE = true;

    public void play(MastermindIO io) {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| " + name + " jij gaat nu raden."
                + String.format("%0" + (35 - name.length()) + "d", 0).replace("0", " ")
                + "|\r\n"
                + "| Probeer de code zo snel mogelijk te raden.            |\r\n"
                + "| Geef per keer 4 letters in en dan [ENTER]             |\r\n"
                + "| Bij 9 keer een foute code ben je af.                  |\r\n"
                + "| Succes!                                               |\r\n"
                + "---------------------------------------------------------");
        io.getEnterToContinue();

        for (int i = 0; i < 4; i++) {
            code += (char) (Math.random() * 4 + 65);
        }

        System.out.println(code);
        while (true) {
            turn++;
            if (turn == 9) {
                System.out.println("\u001B[0mJe hebt 9 keer een foute code geraden. Je bent af!");
                break;
            }

            if (CHEATMODE) {
                System.out.println("\u001B[0m>>> CHEAT [" + code + "] <<<");
            }

            System.out.println("\u001B[0m------------------------------\r\n"
                    + "|    Mastermind speelbord    |\r\n"
                    + "------------------------------");

            for (int i = 0; i < guesses.length; i++) {
                if (guesses[i] != null) {
                    int black = countMatchingChars(code, guesses[i]);
                    int white = countCharsWrongPosition(code, guesses[i]);
                    System.out.println("    " + (i + 1) + ": " + guesses[i] + "  zwart: " + black + "  wit: " + white);
                }
            }

            System.out.println("------------------------------");
            String guess = io.getPlayerGuess();

            guesses[turn - 1] = guess;

            if (guess.equals(code)) {
                System.out.println("\u001B[0m" + name + " je hebt de code geraden!");
                System.out.println("jouw score is: " + turn);
                break;
            }
        }
    }

    public static int countMatchingChars(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static int countCharsWrongPosition(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && s2.contains(String.valueOf(s1.charAt(i)))) {
                count++;
            }
        }
        return count;
    }
}
