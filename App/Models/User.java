package Models;

public class User {

    private int userID;
    private String username;
    private String password;
    private String email;
    private String question;
    private String answer;

    private StudyRecord studyRecord;

    public StudyRecord getStudyRecord() {
        return studyRecord;
    }

    public void setStudyRecord(StudyRecord studyRecord) {
        this.studyRecord = studyRecord;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, StudyRecord score){
        this.username = username;
        this.studyRecord = score;
    }

    public User(int userID, String username, String password, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
