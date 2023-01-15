import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class GameComputer {

    private List<String> possibleCodes;

    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;
    public int turn;

    private void setup() {
        guesses = new HashMap<>();
        turn = 0;

        possibleCodes = new ArrayList<>();

        for (char a = 'A'; a <= 'F'; a++) {
            for (char b = 'A'; b <= 'F'; b++) {
                for (char c = 'A'; c <= 'F'; c++) {
                    for (char d = 'A'; d <= 'F'; d++) {
                        possibleCodes.add("" + a + b + c + d);
                    }
                }
            }
        }
    }

    public void play() {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| de COMPUTER gaat nu raden.                            |\r\n"
                + "| Bedenk een code die uit 4 letters bestaat.            |\r\n"
                + "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
                + "| Geef na iedere poging het resultaat in aantal pins.   |\r\n"
                + "| zwarte pins = letters op juiste positie.              |\r\n"
                + "| witte pins = letters op verkeerde positie.            |\r\n"
                + "---------------------------------------------------------");
        MastermindIO.getEnterToContinue();

        setup();

        while (true) {
            turn++;

            if (possibleCodes.size() == 0) {
                System.out.println(
                        "\u001B[0m  X X X X X X X X X X X X\r\n De computer geeft het op! \r\n Waarschijnlijk heb je ergens een fout getal ingevoerd. \r\n  X X X X X X X X X X X X");
                break;
            }

            if (turn > 9) {
                System.out.println("\u001B[0mDe COMPUTER hebt 9 keer een foute code geraden. De COMPUTER is af!");
                break;
            }

            MastermindIO.printGameBoard(guesses);

            String guess = guessCode();

            System.out.println("    " + turn + ": " + guess);
            System.out.println("------------------------------");
            int black = MastermindIO.getPlayerBlackPins();

            if (black == 4) {
                System.out.println("\u001B[0mgeraden");
                System.out.println("de score van de computer is: " + turn);
                break;
            }

            int white = MastermindIO.getPlayerWhitePins(black);

            removeCodes(guess, black, white);

            List<Integer> values = Arrays.asList(black, white);
            HashMap<String, List<Integer>> round = new HashMap<>();
            round.put(guess, values);
            guesses.put(turn, round);
        }
    }

    private String guessCode() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(possibleCodes.size());
        String randomCode = possibleCodes.get(randomIndex);

        return randomCode;
    }

    private void removeCodes(String guess, int black, int white) {
        for (String code : possibleCodes) {
            int blackPins = MastermindGame.countMatchingChars(code, guess);
            int whitePins = MastermindGame.countCharsWrongPosition(code, guess);

            if (blackPins != black || whitePins != white) {
                possibleCodes.remove(code);
            }
        }
    }
}
