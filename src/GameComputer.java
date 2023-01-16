import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class GameComputer {

    private List<String> possibleCodes;
    private int turn;
    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;

    public int getTurn() {
        return turn;
    }

    private void setup(MastermindGame game) {
        guesses = new HashMap<>();
        turn = 0;

        possibleCodes = new ArrayList<>();

        // TODO: Make dynamic using game.getCodeLength()
        // TODO: If game.getAllowDuplicates() is true, use every letter once per code

        for (char a = 'A'; a <= 65 + game.getAmountOfCharacters(); a++) {
            for (char b = 'A'; b <= 65 + game.getAmountOfCharacters(); b++) {
                for (char c = 'A'; c <= 65 + game.getAmountOfCharacters(); c++) {
                    for (char d = 'A'; d <= 65 + game.getAmountOfCharacters(); d++) {
                        possibleCodes.add("" + a + b + c + d);
                    }
                }
            }
        }

        // the above in a seperate function
        for (char a = 'A'; a <= 65 + game.getAmountOfCharacters(); a++) {
            possibleCodes = addLetter(possibleCodes, a, game.getCodeLength());

        }
    }

    private void addLetter(List<String> codes, char letter, int length) {
        if (length == 1) {
            codes.add("" + letter);
        } else {
            for (String code : codes) {
                if (code.length() == length - 1) {
                    codes.add(code + letter);
                }
            }
        }
    }

    public void play(MastermindGame game) {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| de COMPUTER gaat nu raden.                            |\r\n"
                + "| Bedenk een code die uit " + game.getCodeLength() + " letters bestaat.            |\r\n"
                + "| Mogelijke letters zijn: " +
                "ABCDEFGH".substring(0, game.getAmountOfCharacters())
                + String.format("%0" + (30 - game.getAmountOfCharacters()) + "d", 0).replace("0", " ")
                + "|\r\n"
                + "| Geef na iedere poging het resultaat in aantal pins.   |\r\n"
                + "| zwarte pins = letters op juiste positie.              |\r\n"
                + "| witte pins = letters op verkeerde positie.            |\r\n"
                + "---------------------------------------------------------");
        MastermindIO.getEnterToContinue();

        setup(game);

        while (true) {
            turn++;

            if (possibleCodes.size() == 0) {
                System.out.println(
                        "\u001B[0m  X X X X X X X X X X X X\r\n De computer geeft het op! \r\n Waarschijnlijk heb je ergens een fout getal ingevoerd. \r\n  X X X X X X X X X X X X");
                break;
            }

            if (turn > game.getMaxTurns()) {
                System.out.println("\u001B[0mDe COMPUTER hebt " + game.getMaxTurns()
                        + " keer een foute code geraden. De COMPUTER is af!");
                break;
            }

            MastermindIO.printGameBoard(guesses);

            String guess = guessCode();

            System.out.println("    " + turn + ": " + guess);
            System.out.println("------------------------------");
            int black = MastermindIO.getPlayerBlackPins(game);

            if (black == game.getCodeLength()) {
                System.out.println("\u001B[0mgeraden");
                System.out.println("de score van de computer is: " + turn);
                break;
            }

            int white = MastermindIO.getPlayerWhitePins(game, black);

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
            int blackPins = MastermindGame.countMatchingChars(code, guess, true);
            int whitePins = MastermindGame.countMatchingChars(code, guess, false);

            if (blackPins != black || whitePins != white) {
                possibleCodes.remove(code);
            }
        }
    }
}
