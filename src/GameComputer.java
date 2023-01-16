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

        possibleCodes = AddAllPossibleCodes(game.getPossibleCharacters(),
                game.getCodeLength(),
                game.getAllowDuplicates());
    }

    private List<String> AddAllPossibleCodes(List<Character> letters, int length, boolean allowDuplicates) {
        List<String> codes = new ArrayList<>();

        if (length == 1) {
            for (char letter : letters) {
                codes.add(String.valueOf(letter));
            }
        } else {
            for (char letter : letters) {
                List<Character> newLetters = new ArrayList<>(letters);
                if (!allowDuplicates) {
                    newLetters.remove(Character.valueOf(letter));
                }
                for (String code : AddAllPossibleCodes(newLetters, length - 1, allowDuplicates)) {
                    codes.add(letter + code);
                }
            }
        }

        return codes;
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
            System.out.println(possibleCodes.size());
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
        for (int i = 0; i < possibleCodes.size(); i++) {
            String code = possibleCodes.get(i);

            int blackPins = MastermindGame.countPins(code, guess, true);
            int whitePins = MastermindGame.countPins(code, guess, false);

            if (blackPins != black || whitePins != white) {
                possibleCodes.remove(i);
                i--;
            }
        }
    }
}
