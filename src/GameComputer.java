import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class GameComputer {

    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;
    public int turn;

    public void play(MastermindIO io) {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| de COMPUTER gaat nu raden.                            |\r\n"
                + "| Bedenk een code die uit 4 letters bestaat.            |\r\n"
                + "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
                + "| Geef na iedere poging het resultaat in aantal pins.   |\r\n"
                + "| zwarte pins = letters op juiste positie.              |\r\n"
                + "| witte pins = letters op verkeerde positie.            |\r\n"
                + "---------------------------------------------------------");
        io.getEnterToContinue();

        turn = 0;
        guesses = new HashMap<>();

        while (true) {
            turn++;
            if (turn > 9) {
                System.out.println("\u001B[0mDe COMPUTER hebt 9 keer een foute code geraden. De COMPUTER is af!");
                break;
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

            String guess = guessCode();

            System.out.println("    " + turn + ": " + guess);
            System.out.println("------------------------------");
            int black = io.getPlayerBlackPins();

            if (black == 4) {
                System.out.println("\u001B[0mgeraden");
                System.out.println("de score van de computer is: " + turn);
                break;
            }

            int white = io.getPlayerWhitePins(black);

            List<Integer> values = Arrays.asList(black, white);
            HashMap<String, List<Integer>> round = new HashMap<>();
            round.put(guess, values);
            guesses.put(turn, round);
        }
    }

    public String guessCode() {
        // TODO: generate a guess

        for (int key : guesses.keySet()) {
            for (String guess : guesses.get(key).keySet()) {
                List<Integer> values = guesses.get(key).get(guess);
                System.out.println("  " + key + ": " + guess
                        + "  zwart: " + values.get(0)
                        + "  wit: " + values.get(1));
            }
        }

        // for (int i = 0; i < 4; i++) {
        // guess += (char) (65 + (int) (Math.random() * 6));
        // }
        return "AAAA";
    }
}
