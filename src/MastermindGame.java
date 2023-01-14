
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

        MastermindIO io = new MastermindIO();
        GameHuman player = new GameHuman();
        GameComputer computer = new GameComputer();

        player.name = io.getHumanPlayerName();

        System.out.println("Eerst doen we een TOSS om wie mag beginnen.");

        player.preference = io.getHeadOrTale();

        tossResult = doToss();

        if (tossResult.equals(player.preference)) {
            System.out.println(player.name + " jij mag beginnen!");
            player.play(io);
            computer.play(io);
        } else {
            System.out.println("De computer begint!");
            computer.play(io);
            player.play(io);
        }

        result(player, computer, io);
    }

    public void result(GameHuman player, GameComputer computer, MastermindIO io) {

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
                player.play(io);
                computer.play(io);
            } else {
                System.out.println("De computer begint!");
                computer.play(io);
                player.play(io);
            }
            result(player, computer, io);
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
}
