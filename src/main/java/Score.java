

@lombok.Setter
@lombok.Getter
public class Score {
    private String playerName;
    private int time;

    public Score() {

    }

    public Score(String playerName, int time) {
        this.playerName = playerName;
        this.time = time;
    }
}
