import java.util.ArrayList;
import java.util.List;

public class MastermindGame {

    private String tossResult;

    private int codeLength;
    private int maxTurns;
    private int amountOfCharacters;
    private boolean allowDuplicates;

    private List<Character> possibleCharacters;

    public MastermindGame() {
        codeLength = 4;
        maxTurns = 9;
        amountOfCharacters = 6;
        allowDuplicates = true;
        possibleCharacters = new ArrayList<>();
    }

    public int getCodeLength() {
        return codeLength;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public int getAmountOfCharacters() {
        return amountOfCharacters;
    }

    public boolean getAllowDuplicates() {
        return allowDuplicates;
    }

    public List<Character> getPossibleCharacters() {
        return possibleCharacters;
    }

    public void start() {
        System.out.println("=========================================================\r\n"
                + "| Welkom bij Mastermind!                                |\r\n"
                + "=========================================================\r\n"
                + "| Je gaat tegen de computer spelen.                     |\r\n"
                + "| Het doel is zo snel mogelijk een geheime code raden.  |\r\n"
                + "| De code bestaat uit 4 tot 6 letters.                  |\r\n"
                + "| Mogelijke letters zijn: A, B, C, D, E, F, G, H        |\r\n"
                + "| Een letter wel of niet vaker voorkomen in de code.    |\r\n"
                + "| Jij raadt de code van de computer en andersom.        |\r\n"
                + "| Wie met het minste pogingen de code raadt wint.       |\r\n"
                + "| Bij een gelijkspel spelen we nog een ronde.           |\r\n"
                + "=========================================================");

        GameHuman player = new GameHuman();
        GameComputer computer = new GameComputer();

        player.setName(MastermindIO.getHumanPlayerName());

        codeLength = MastermindIO.getCodeLengthInput();
        maxTurns = MastermindIO.getMaxTurnsInput();
        amountOfCharacters = MastermindIO.getAmountOfCharactersInput();

        if (codeLength < amountOfCharacters) {
            allowDuplicates = MastermindIO.getAllowDuplicatesInput();
        }

        for (char a = 'A'; a <= 64 + getAmountOfCharacters(); a++) {
            possibleCharacters.add(a);
        }

        System.out.println("\u001B[0mEerst doen we een TOSS om wie mag beginnen.");

        player.setPreference(MastermindIO.getHeadsOrTails());

        startMatch(player, computer);
    }

    private void result(GameHuman player, GameComputer computer) {
        System.out
                .println("\u001B[0mEindscore: " + player.getName() + ": "
                        + ((player.getTurn() > this.getMaxTurns()) ? "[NIET]" : player.getTurn())
                        + " - COMPUTER: "
                        + ((computer.getTurn() > this.getMaxTurns()) ? "[NIET]" : computer.getTurn()));

        if (player.getTurn() < computer.getTurn()) {
            System.out.println("\u001B[0mBravo " + player.getName() + " je hebt de computer verslagen!");
        } else if (player.getTurn() > computer.getTurn()) {
            System.out.println("\u001B[0mHelaas " + player.getName() + " je hebt verloren!");
        } else {
            System.out.println("\u001B[0mHet is gelijk spel, we spelen nog een ronde!");

            startMatch(player, computer);
        }
    }

    private void startMatch(GameHuman player, GameComputer computer) {
        tossResult = doToss();

        if (tossResult.equals(player.getPreference())) {
            System.out.println(player.getName() + " jij mag beginnen!");
            player.play(this);
            computer.play(this);
        } else {
            System.out.println("De computer begint!");
            computer.play(this);
            player.play(this);
        }

        result(player, computer);
    }

    private static String doToss() {
        int heads = 0;
        int tails = 0;
        String toss = new String();
        for (int i = 0; i < 5; i++) {
            if (Math.random() < 0.5) {
                heads++;
                toss += "kop|";
            } else {
                tails++;
                toss += "munt|";
            }
        }

        System.out.println("\u001B[0mflipping: " + toss.substring(0, toss.length() - 1));

        if (heads > tails) {
            System.out.println("\u001B[0mhet is geworden: kop (" + heads + " keer gegooid)");
            return "kop";
        } else {
            System.out.println("\u001B[0mhet is geworden: munt (" + tails + " keer gegooid)");
            return "munt";
        }
    }

    public static int countPins(String code, String guess, boolean black) {
        int count = 0;

        for (int i = 0; i < code.length(); i++) {
            if (black) {
                if (code.charAt(i) == guess.charAt(i)) {
                    count++;
                }
            } else {
                if (code.charAt(i) != guess.charAt(i) && code.contains(String.valueOf(guess.charAt(i)))) {
                    count++;
                }
            }
        }
        return count;
    }
}
