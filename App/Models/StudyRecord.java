package Models;


public class StudyRecord {
    private int score;
    private int timesAttend;
    private int totalQuestion;
    private int correctQuestions;
    private int incorrectQuestions;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimesAttend() {
        return timesAttend;
    }

    public void setTimesAttend(int timesAttend) {
        this.timesAttend = timesAttend;
    }
    public int getTotalQuestion() {
        return totalQuestion;
    }
    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
    public int getCorrectQuestions() {
        return correctQuestions;
    }
    public void setCorrectQuestions(int correctQuestions) {
        this.correctQuestions = correctQuestions;
    }
    public int getIncorrectQuestions() {
        return incorrectQuestions;
    }
    public void setIncorrectQuestions(int incorrectQuestions) {
        this.incorrectQuestions = incorrectQuestions;
    }

    public StudyRecord(int score, int timesAttend, int totalQuestion, int correctQuestions, int incorrectQuestions) {
        this.score = score;
        this.timesAttend = timesAttend;
        this.totalQuestion = totalQuestion;
        this.correctQuestions = correctQuestions;
        this.incorrectQuestions = incorrectQuestions;
    }
}
