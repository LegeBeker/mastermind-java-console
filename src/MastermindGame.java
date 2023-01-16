import java.util.ArrayList;
import java.util.List;

public class MastermindGame {

    private String tossResult;

    private int codeLength;
    private int maxTurns;
    private int amountOfCharacters;
    private boolean allowDuplicates;

    private List<Character> possibleLetters;

    public MastermindGame() {
        codeLength = 4;
        maxTurns = 9;
        amountOfCharacters = 6;
        allowDuplicates = true;
        possibleLetters = new ArrayList<>();
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

    public List<Character> getPossibleLetters() {
        return possibleLetters;
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

        for (char a = 'A'; a <= 65 + getAmountOfCharacters(); a++) {
            possibleLetters.add(a);
        }

        player.setName(MastermindIO.getHumanPlayerName());

        System.out.println("Eerst doen we een TOSS om wie mag beginnen.");

        player.setPreference(MastermindIO.getHeadOrTale());

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
        int head = 0;
        int tale = 5;
        String toss = new String();
        for (int i = 0; i < 5; i++) {
            if (Math.random() < 0.5) {
                head++;
                toss += "kop|";
            } else {
                tale++;
                toss += "munt|";
            }
        }

        System.out.println("\u001B[0mflipping: " + toss.substring(0, toss.length() - 1));

        if (head > tale) {
            System.out.println("\u001B[0mhet is geworden: kop (" + head + " keer gegooid)");
            return "kop";
        } else {
            System.out.println("\u001B[0mhet is geworden: munt (" + tale + " keer gegooid)");
            return "munt";
        }
    }

    public static int countMatchingChars(String s1, String s2, boolean samePosition) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i) && (samePosition || s2.contains(String.valueOf(s1.charAt(i))))) {
                count++;
            }
        }
        return count;
    }
}
