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

    public void play() {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| " + name + " jij gaat nu raden."
                + String.format("%0" + (35 - name.length()) + "d", 0).replace("0", " ")
                + "|\r\n"
                + "| Probeer de code zo snel mogelijk te raden.            |\r\n"
                + "| Geef per keer 4 letters in en dan [ENTER]             |\r\n"
                + "| Bij 9 keer een foute code ben je af.                  |\r\n"
                + "| Succes!                                               |\r\n"
                + "---------------------------------------------------------");
        MastermindIO.getEnterToContinue();

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

            MastermindIO.printGameBoard(guesses);

            String guess = MastermindIO.getPlayerGuess();

            int black = MastermindGame.countMatchingChars(code, guess);
            int white = MastermindGame.countCharsWrongPosition(code, guess);

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
}
