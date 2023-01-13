
public class GameComputer {

    public String[] guesses = new String[9];
    public int turn = 0;
    public String code = "";

    public int play(MastermindIO io) {
        System.out.println("\u001B[0m---------------------------------------------------------\r\n"
                + "| de COMPUTER gaat nu raden.                            |\r\n"
                + "| Bedenk een code die uit 4 letters bestaat.            |\r\n"
                + "| Mogelijke letters zijn: A, B, C, D, E, F              |\r\n"
                + "| Geef na iedere poging het resultaat in aantal pins.   |\r\n"
                + "| zwarte pins = letters op juiste positie.              |\r\n"
                + "| witte pins = letters op verkeerde positie.            |\r\n"
                + "---------------------------------------------------------");
        io.getEnterToContinue();
        return 0;
    }
}
