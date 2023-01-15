import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class GameBase {

    private HashMap<Integer, HashMap<String, List<Integer>>> guesses;
    public int turn;

    public void setGuess(String guess, int black, int white) {
        List<Integer> values = Arrays.asList(black, white);
        HashMap<String, List<Integer>> round = new HashMap<>();
        round.put(guess, values);
        guesses.put(turn, round);
    }
}
