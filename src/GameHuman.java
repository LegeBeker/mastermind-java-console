import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class GameHuman {

    private String name;
    private String preference;
    private String code;
    private int turn;
    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;
    static boolean CHEATMODE = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public int getTurn() {
        return turn;
    }

    private void setup(MastermindGame game) {
        guesses = new HashMap<>();
        turn = 0;

        code = new String();

        Random rand = new Random();
        if (game.getAllowDuplicates()) {
            for (int i = 0; i < game.getCodeLength(); i++) {
                code += (char) (64 + rand.nextInt(game.getAmountOfCharacters()));
            }
        } else {
            List<Character> possibleCharacters = game.getPossibleCharacters();
            for (int i = 0; i < game.getCodeLength(); i++) {
                int randomIndex = rand.nextInt(possibleCharacters.size() - 1);

                code += possibleCharacters.get(randomIndex);
                possibleCharacters.remove(randomIndex);
            }
        }
    }

    public void play(MastermindGame game) {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| " + name + " jij gaat nu raden."
                + String.format("%0" + (35 - name.length()) + "d", 0).replace("0", " ")
                + "|\r\n"
                + "| Probeer de code zo snel mogelijk te raden.            |\r\n"
                + "| Geef per keer " + game.getCodeLength() + " letters in en dan [ENTER]             |\r\n"
                + "| Bij " + game.getMaxTurns() + " keer een foute code ben je af.                  |\r\n"
                + "| Succes!                                               |\r\n"
                + "---------------------------------------------------------");
        MastermindIO.getEnterToContinue();

        setup(game);

        while (true) {
            turn++;

            if (turn > game.getMaxTurns()) {
                System.out.println(
                        "\u001B[0mJe hebt " + game.getMaxTurns() + " keer een foute code geraden. Je bent af!");
                break;
            }

            if (CHEATMODE) {
                System.out.println("\u001B[0m>>> CHEAT [" + code + "] <<<");
            }

            MastermindIO.printGameBoard(guesses);

            String guess = MastermindIO.getPlayerGuess(game);

            if (guess.equals(code)) {
                System.out.println("\u001B[0m" + name + " je hebt de code geraden!");
                System.out.println("jouw score is: " + turn);
                break;
            }

            int black = MastermindGame.countPins(code, guess, true);
            int white = MastermindGame.countPins(code, guess, false);

            List<Integer> values = Arrays.asList(black, white);
            HashMap<String, List<Integer>> round = new HashMap<>();
            round.put(guess, values);
            guesses.put(turn, round);
        }
    }
}
