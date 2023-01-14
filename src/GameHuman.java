import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class GameHuman {

    public String name;
    public String preference;
    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;
    public int turn;
    private String code;
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

        turn = 0;
        guesses = new HashMap<>();
        code = new String();

        for (int i = 0; i < 4; i++) {
            code += (char) (65 + (int) (Math.random() * 6));
        }

        while (true) {
            turn++;
            if (turn > 9) {
                System.out.println("\u001B[0mJe hebt 9 keer een foute code geraden. Je bent af!");
                break;
            }

            if (CHEATMODE) {
                System.out.println("\u001B[0m>>> CHEAT [" + code + "] <<<");
            }

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
            String guess = io.getPlayerGuess();

            int black = countMatchingChars(code, guess);
            int white = countCharsWrongPosition(code, guess);

            List<Integer> values = Arrays.asList(black, white);
            HashMap<String, List<Integer>> round = new HashMap<>();
            round.put(guess, values);
            guesses.put(turn, round);

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
