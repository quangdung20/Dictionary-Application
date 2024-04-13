package Models;

public enum Ranking {
    BRONZE("BRONZE"),
    SILVER("SILVER"),
    GOLD("GOLD"),
    PLATINUM("PLATINUM"),
    DIAMOND("DIAMOND"),
    MASTER("MASTER"),
    GRANDMASTER("GRANDMASTER"),
    CHALLENGER(" CHALLENGER");

    private final String value;

    private Ranking(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Ranking getRanking(int totalScore) {
        if (totalScore < 100) {
            return BRONZE;
        } else if (totalScore < 300) {
            return SILVER;
        } else if (totalScore < 600) {
            return GOLD;
        } else if (totalScore < 1000) {
            return PLATINUM;
        } else if (totalScore < 1500) {
            return DIAMOND;
        } else if (totalScore < 2000) {
            return MASTER;
        } else if (totalScore < 2500) {
            return GRANDMASTER;
        } else {
            return CHALLENGER;
        }
    }
}
