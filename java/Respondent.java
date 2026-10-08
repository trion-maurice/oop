import java.util.ArrayList;

public class Respondent extends User {

    private final ArrayList<Answer> answers;

    public Respondent(String username, String password) {

        super(username, password);

        answers = new ArrayList<>();
    }

    public void addAnswer(Answer answer) {

        answers.add(answer);
    }

    public ArrayList<Answer> getAnswers() {

        return answers;
    }

}
