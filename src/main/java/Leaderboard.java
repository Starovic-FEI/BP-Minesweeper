import java.util.prefs.Preferences;

public class Leaderboard {
    private Preferences prefs;

    public Leaderboard() {
        prefs = Preferences.userNodeForPackage(Leaderboard.class);
    }

    public void saveScore(String level, Score score) {
        String scoreData = score.getPlayerName() + "," + score.getTime();
        prefs.put(level, scoreData);
    }

    public Score loadScore(String level) {
        String scoreData = prefs.get(level, "");
        String[] parts = scoreData.split(",");
        if (parts.length == 2) {
            Score score = new Score();
            score.setPlayerName(parts[0]);
            score.setTime(Integer.parseInt(parts[1]));
            return score;
        } else {
            return new Score("Anonymous", 999);
        }
    }
}