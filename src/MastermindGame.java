
public class MastermindGame {

    public String tossResult;

    public void start() {
        System.out.println("=========================================================\r\n"
                + "| Welkom bij Mastermind!                                |\r\n"
                + "=========================================================\r\n"
                + "| Je gaat tegen de computer spelen.                     |\r\n"
                + "| Het doel is zo snel mogelijk een geheime code raden.  |\r\n"
                + "| De code bestaat uit 4 letters.                        |\r\n"
                + "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
                + "| Een letter kan 0 t/m 4 keer voorkomen in de code.     |\r\n"
                + "| Jij raadt de code van de computer en andersom.        |\r\n"
                + "| Wie met het minste pogingen de code raadt wint.       |\r\n"
                + "| Bij een gelijkspel spelen we nog een ronde.           |\r\n"
                + "=========================================================");

        GameHuman player = new GameHuman();
        GameComputer computer = new GameComputer();

        player.name = MastermindIO.getHumanPlayerName();

        System.out.println("Eerst doen we een TOSS om wie mag beginnen.");

        player.preference = MastermindIO.getHeadOrTale();

        tossResult = doToss();

        if (tossResult.equals(player.preference)) {
            System.out.println(player.name + " jij mag beginnen!");
            player.play();
            computer.play();
        } else {
            System.out.println("De computer begint!");
            computer.play();
            player.play();
        }

        result(player, computer);
    }

    public void result(GameHuman player, GameComputer computer) {

        System.out.println("\u001B[0mEindscore: " + player.name + ": " + ((player.turn > 9) ? "[NIET]" : player.turn)
                + " - COMPUTER: " + ((computer.turn > 9) ? "[NIET]" : computer.turn));

        if (player.turn < computer.turn) {
            System.out.println("\u001B[0mBravo " + player.name + " je hebt de computer verslagen!");
        } else if (player.turn > computer.turn) {
            System.out.println("\u001B[0mHelaas " + player.name + " je hebt verloren!");
        } else {
            System.out.println("\u001B[0mHet is gelijk spel, we spelen nog een ronde!");

            tossResult = doToss();

            if (tossResult.equals(player.preference)) {
                System.out.println(player.name + " jij mag beginnen!");
                player.play();
                computer.play();
            } else {
                System.out.println("De computer begint!");
                computer.play();
                player.play();
            }
            result(player, computer);
        }
    }

    public String doToss() {
        int head = 0;
        int tale = 5;
        String toss = "";
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
