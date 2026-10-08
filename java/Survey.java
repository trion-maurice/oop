import java.util.ArrayList;

public class Survey {

    private final String title;
    private final ArrayList<Question> questions;

    public Survey(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
