package Models;

public class User {
    private String username;
    private String password;
    private String email;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String question;
    private String answer;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    private int score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public User(String username, int score, String email) {
        this.username = username;
        this.score = score;
        this.email = email;
    }
    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String email, String question, String answer, int score) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.score = 0;
    }
}
