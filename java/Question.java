import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String questionText;
    private final QuestionType type;
    private final ArrayList<String> options = new ArrayList<>();

    public Question(String questionText, QuestionType type) {
        this.questionText = questionText;
        this.type = type;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getType() {
        return type;
    }

    public void addOption(String option) {
        String trimmedOption = option.trim();
        if (!trimmedOption.isEmpty()) {
            options.add(trimmedOption);
        }
    }

    public List<String> getOptions() {
        return List.copyOf(options);
    }
}
