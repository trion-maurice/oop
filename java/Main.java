import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Main {

    private JFrame frame;

    // Stores all surveys created during the current program run
    private final ArrayList<Survey> surveys = new ArrayList<>();

    // Current respondent
    private Respondent respondent;

    // Used when taking a survey
    private static class QuestionInput {
        Question question;
        JTextField textField;
        ButtonGroup radioGroup;
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.createWindow();
            app.showLogin();
        });
    }

    // ---------------------------------------------------------
    // WINDOW
    // ---------------------------------------------------------

    private void createWindow() {
        frame = new JFrame("Online Survey System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------

    private void showLogin() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Online Survey System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JLabel info = new JLabel(
                "<html><center>Creator: creator / 1234<br>Student: student / 1234</center></html>",
                SwingConstants.CENTER
        );

        gbc.gridy = 4;
        panel.add(info, gbc);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals("creator") && password.equals("1234")) {

                // Demonstrates the SurveyCreator class
                SurveyCreator creator = new SurveyCreator(username, password);

                JOptionPane.showMessageDialog(
                        frame,
                        "Welcome, " + creator.getUsername() + "!"
                );

                showCreatorDashboard();

            } else if (username.equals("student") && password.equals("1234")) {

                respondent = new Respondent(username, password);

                JOptionPane.showMessageDialog(
                        frame,
                        "Welcome, " + respondent.getUsername() + "!"
                );

                showRespondentDashboard();

            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid username or password.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    // ---------------------------------------------------------
    // CREATOR DASHBOARD
    // ---------------------------------------------------------

    private void showCreatorDashboard() {
    frame.getContentPane().removeAll();

    JPanel panel = new JPanel(new BorderLayout(10, 10));

    JLabel title = new JLabel(
            "Survey Creator Dashboard",
            SwingConstants.CENTER
    );

    title.setFont(new Font("Arial", Font.BOLD, 24));

    panel.add(title, BorderLayout.NORTH);

    // Smaller buttons
    JPanel buttons = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 15, 15)
    );

    JButton createButton = new JButton("Create Survey");
    JButton viewButton = new JButton("View Surveys");
    JButton resultsButton = new JButton("View Results");
    JButton logoutButton = new JButton("Logout");

    Dimension buttonSize = new Dimension(160, 40);

    createButton.setPreferredSize(buttonSize);
    viewButton.setPreferredSize(buttonSize);
    resultsButton.setPreferredSize(buttonSize);
    logoutButton.setPreferredSize(buttonSize);

    buttons.add(createButton);
    buttons.add(viewButton);
    buttons.add(resultsButton);
    buttons.add(logoutButton);

    panel.add(buttons, BorderLayout.CENTER);

    createButton.addActionListener(e -> showCreateSurvey());

    viewButton.addActionListener(e -> showSurveys());

    resultsButton.addActionListener(e -> showResultsSelection());

    logoutButton.addActionListener(e -> showLogin());

    frame.add(panel);
    frame.revalidate();
    frame.repaint();
}

    // ---------------------------------------------------------
    // CREATE SURVEY
    // ---------------------------------------------------------

    private void showCreateSurvey() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
                "Create New Survey",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel surveyTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel surveyTitleLabel = new JLabel("Survey Title:");

        JTextField surveyTitleField = new JTextField(30);

        surveyTitlePanel.add(surveyTitleLabel);
        surveyTitlePanel.add(surveyTitleField);

        formPanel.add(surveyTitlePanel);

        formPanel.add(Box.createVerticalStrut(10));

        // We are creating exactly 3 questions
        ArrayList<JTextField> questionFields = new ArrayList<>();
        ArrayList<JComboBox<QuestionType>> typeBoxes = new ArrayList<>();
        ArrayList<JTextField> optionFields = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            JPanel questionPanel = new JPanel();
            questionPanel.setBorder(
                    BorderFactory.createTitledBorder(
                            "Question " + (i + 1)
                    )
            );

            questionPanel.setLayout(
                    new BoxLayout(questionPanel, BoxLayout.Y_AXIS)
            );

            JPanel questionTextPanel = new JPanel(
                    new FlowLayout(FlowLayout.LEFT)
            );

            JLabel questionLabel = new JLabel("Question:");

            JTextField questionField = new JTextField(35);

            questionTextPanel.add(questionLabel);
            questionTextPanel.add(questionField);

            questionPanel.add(questionTextPanel);

            JPanel typePanel = new JPanel(
                    new FlowLayout(FlowLayout.LEFT)
            );

            JLabel typeLabel = new JLabel("Question Type:");

            JComboBox<QuestionType> typeBox =
                    new JComboBox<>(QuestionType.values());

            typePanel.add(typeLabel);
            typePanel.add(typeBox);

            questionPanel.add(typePanel);

            JPanel optionsPanel = new JPanel(
                    new FlowLayout(FlowLayout.LEFT)
            );

            JLabel optionsLabel = new JLabel(
                    "Options (separate with commas):"
            );

            JTextField optionsField = new JTextField(30);

            optionsPanel.add(optionsLabel);
            optionsPanel.add(optionsField);

            questionPanel.add(optionsPanel);

            questionFields.add(questionField);
            typeBoxes.add(typeBox);
            optionFields.add(optionsField);

            // Show/hide options field depending on question type
            typeBox.addActionListener(e -> {

                QuestionType selectedType =
                        (QuestionType) typeBox.getSelectedItem();

                boolean needsOptions =
                        selectedType == QuestionType.MULTIPLE_CHOICE
                                || selectedType == QuestionType.MULTIPLE_ANSWER;

                optionsField.setEnabled(needsOptions);

                if (!needsOptions) {
                    optionsField.setText("");
                }
            });

            // Set initial state
            optionsField.setEnabled(false);

            formPanel.add(questionPanel);
            formPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(formPanel);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save Survey");
        JButton backButton = new JButton("Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // SAVE SURVEY
        saveButton.addActionListener(e -> {

            String surveyTitle = surveyTitleField.getText().trim();

            if (surveyTitle.isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please enter a survey title."
                );
                return;
            }

            Survey survey = new Survey(surveyTitle);

            for (int i = 0; i < 3; i++) {

                String questionText =
                        questionFields.get(i).getText().trim();

                if (questionText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please enter Question " + (i + 1) + "."
                    );
                    return;
                }

                QuestionType type =
                        (QuestionType)
                                typeBoxes.get(i).getSelectedItem();

                Question question =
                        new Question(questionText, type);

                // Add options for objective questions
                if (type == QuestionType.MULTIPLE_CHOICE
                        || type == QuestionType.MULTIPLE_ANSWER) {

                    String optionsText =
                            optionFields.get(i).getText().trim();

                    if (optionsText.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please enter options for Question "
                                        + (i + 1) + "."
                        );
                        return;
                    }

                    String[] options =
                            optionsText.split(",");

                    for (String option : options) {
                        question.addOption(option.trim());
                    }

                    if (question.getOptions().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please provide valid options for Question "
                                        + (i + 1) + "."
                        );
                        return;
                    }
                }

                survey.addQuestion(question);
            }

            surveys.add(survey);

            JOptionPane.showMessageDialog(
                    frame,
                    "Survey created successfully!"
            );

            showCreatorDashboard();
        });

        backButton.addActionListener(e -> showCreatorDashboard());

        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // VIEW SURVEYS
    // ---------------------------------------------------------

    private void showSurveys() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Available Surveys",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        if (surveys.isEmpty()) {

            JLabel emptyLabel = new JLabel(
                    "No surveys have been created yet.",
                    SwingConstants.CENTER
            );

            panel.add(emptyLabel, BorderLayout.CENTER);

        } else {

            DefaultListModel<String> listModel =
                    new DefaultListModel<>();

            for (Survey survey : surveys) {
                listModel.addElement(survey.getTitle());
            }

            JList<String> surveyList =
                    new JList<>(listModel);

            panel.add(
                    new JScrollPane(surveyList),
                    BorderLayout.CENTER
            );

            JButton detailsButton =
                    new JButton("View Survey Details");

            detailsButton.addActionListener(e -> {

                int selectedIndex =
                        surveyList.getSelectedIndex();

                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select a survey."
                    );
                    return;
                }

                showSurveyDetails(
                        surveys.get(selectedIndex),
                        false
                );
            });

            panel.add(detailsButton, BorderLayout.SOUTH);
        }

        JButton backButton = new JButton("Back");

        backButton.addActionListener(
                e -> showCreatorDashboard()
        );

        panel.add(backButton, BorderLayout.WEST);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // SURVEY DETAILS
    // ---------------------------------------------------------

    private void showSurveyDetails(
            Survey survey,
            boolean respondentView
    ) {

        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                survey.getTitle(),
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        JPanel questionsPanel = new JPanel();

        questionsPanel.setLayout(
                new BoxLayout(
                        questionsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        int number = 1;

        for (Question question : survey.getQuestions()) {

            StringBuilder text = new StringBuilder();

            text.append(number)
                    .append(". ")
                    .append(question.getQuestionText())
                    .append(" [")
                    .append(question.getType())
                    .append("]");

            if (!question.getOptions().isEmpty()) {

                text.append("\nOptions: ")
                        .append(
                                String.join(
                                        ", ",
                                        question.getOptions()
                                )
                        );
            }

            JTextArea questionArea =
                    new JTextArea(text.toString());

            questionArea.setEditable(false);
            questionArea.setLineWrap(true);
            questionArea.setWrapStyleWord(true);

            questionsPanel.add(questionArea);
            questionsPanel.add(
                    Box.createVerticalStrut(10)
            );

            number++;
        }

        panel.add(
                new JScrollPane(questionsPanel),
                BorderLayout.CENTER
        );

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {

            if (respondentView) {
                showRespondentDashboard();
            } else {
                showSurveys();
            }
        });

        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // RESULTS SELECTION
    // ---------------------------------------------------------

    private void showResultsSelection() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "View Survey Results",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        if (surveys.isEmpty()) {

            JLabel emptyLabel = new JLabel(
                    "No surveys available.",
                    SwingConstants.CENTER
            );

            panel.add(emptyLabel, BorderLayout.CENTER);

        } else {

            DefaultListModel<String> listModel =
                    new DefaultListModel<>();

            for (Survey survey : surveys) {
                listModel.addElement(survey.getTitle());
            }

            JList<String> surveyList =
                    new JList<>(listModel);

            panel.add(
                    new JScrollPane(surveyList),
                    BorderLayout.CENTER
            );

            JButton viewButton =
                    new JButton("View Results");

            viewButton.addActionListener(e -> {

                int selectedIndex =
                        surveyList.getSelectedIndex();

                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select a survey."
                    );
                    return;
                }

                showResults(
                        surveys.get(selectedIndex)
                );
            });

            panel.add(viewButton, BorderLayout.SOUTH);
        }

        JButton backButton = new JButton("Back");

        backButton.addActionListener(
                e -> showCreatorDashboard()
        );

        panel.add(backButton, BorderLayout.WEST);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // RESULTS
    // ---------------------------------------------------------

    private void showResults(Survey survey) {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Results: " + survey.getTitle(),
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        JTextArea resultsArea = new JTextArea();

        resultsArea.setEditable(false);
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);

        StringBuilder results = new StringBuilder();

        results.append(
                "Survey: "
        ).append(survey.getTitle()).append("\n\n");

        if (respondent == null
                || respondent.getAnswers().isEmpty()) {

            results.append(
                    "No responses have been submitted yet."
            );

        } else {

            boolean foundAnswer = false;

            int questionNumber = 1;

            for (Question question : survey.getQuestions()) {

                results.append(questionNumber)
                        .append(". ")
                        .append(question.getQuestionText())
                        .append("\n");

                boolean questionAnswered = false;

                for (Answer answer :
                        respondent.getAnswers()) {

                    if (answer.getSurvey() == survey
                            && answer.getQuestion() == question) {

                        results.append("Answer: ")
                                .append(answer.getAnswerText())
                                .append("\n");

                        questionAnswered = true;
                        foundAnswer = true;
                    }
                }

                if (!questionAnswered) {
                    results.append("Answer: No response\n");
                }

                results.append("\n");

                questionNumber++;
            }

            if (!foundAnswer) {
                results.setLength(0);

                results.append(
                        "No responses have been submitted for this survey."
                );
            }
        }

        resultsArea.setText(results.toString());

        panel.add(
                new JScrollPane(resultsArea),
                BorderLayout.CENTER
        );

        JButton backButton = new JButton("Back");

        backButton.addActionListener(
                e -> showResultsSelection()
        );

        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // RESPONDENT DASHBOARD
    // ---------------------------------------------------------

    private void showRespondentDashboard() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Student Dashboard",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        JButton takeSurveyButton =
                new JButton("Take Survey");

        JButton viewSurveysButton =
                new JButton("View Surveys");

        JButton logoutButton =
                new JButton("Logout");

        buttons.add(takeSurveyButton);
        buttons.add(viewSurveysButton);
        buttons.add(logoutButton);

        panel.add(buttons, BorderLayout.CENTER);

        takeSurveyButton.addActionListener(
                e -> showTakeSurveySelection()
        );

        viewSurveysButton.addActionListener(
                e -> showRespondentSurveyList()
        );

        logoutButton.addActionListener(
                e -> showLogin()
        );

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // RESPONDENT SURVEY LIST
    // ---------------------------------------------------------

    private void showRespondentSurveyList() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Available Surveys",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        if (surveys.isEmpty()) {

            JLabel emptyLabel = new JLabel(
                    "No surveys are currently available.",
                    SwingConstants.CENTER
            );

            panel.add(emptyLabel, BorderLayout.CENTER);

        } else {

            DefaultListModel<String> listModel =
                    new DefaultListModel<>();

            for (Survey survey : surveys) {
                listModel.addElement(survey.getTitle());
            }

            JList<String> surveyList =
                    new JList<>(listModel);

            panel.add(
                    new JScrollPane(surveyList),
                    BorderLayout.CENTER
            );

            JButton detailsButton =
                    new JButton("View Survey");

            detailsButton.addActionListener(e -> {

                int selectedIndex =
                        surveyList.getSelectedIndex();

                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select a survey."
                    );
                    return;
                }

                showSurveyDetails(
                        surveys.get(selectedIndex),
                        true
                );
            });

            panel.add(detailsButton, BorderLayout.SOUTH);
        }

        JButton backButton = new JButton("Back");

        backButton.addActionListener(
                e -> showRespondentDashboard()
        );

        panel.add(backButton, BorderLayout.WEST);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // SELECT SURVEY TO TAKE
    // ---------------------------------------------------------

    private void showTakeSurveySelection() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                "Select a Survey",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        if (surveys.isEmpty()) {

            JLabel emptyLabel = new JLabel(
                    "No surveys are currently available.",
                    SwingConstants.CENTER
            );

            panel.add(emptyLabel, BorderLayout.CENTER);

        } else {

            DefaultListModel<String> listModel =
                    new DefaultListModel<>();

            for (Survey survey : surveys) {
                listModel.addElement(survey.getTitle());
            }

            JList<String> surveyList =
                    new JList<>(listModel);

            panel.add(
                    new JScrollPane(surveyList),
                    BorderLayout.CENTER
            );

            JButton startButton =
                    new JButton("Start Survey");

            startButton.addActionListener(e -> {

                int selectedIndex =
                        surveyList.getSelectedIndex();

                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select a survey."
                    );
                    return;
                }

                showTakeSurvey(
                        surveys.get(selectedIndex)
                );
            });

            panel.add(startButton, BorderLayout.SOUTH);
        }

        JButton backButton = new JButton("Back");

        backButton.addActionListener(
                e -> showRespondentDashboard()
        );

        panel.add(backButton, BorderLayout.WEST);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // TAKE SURVEY
    // ---------------------------------------------------------

    private void showTakeSurvey(Survey survey) {
        frame.getContentPane().removeAll();

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel(
                survey.getTitle(),
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel questionsPanel = new JPanel();

        questionsPanel.setLayout(
                new BoxLayout(
                        questionsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        ArrayList<QuestionInput> inputs =
                new ArrayList<>();

        int questionNumber = 1;

        for (Question question :
                survey.getQuestions()) {

            JPanel questionPanel =
                    new JPanel();

            questionPanel.setBorder(
                    BorderFactory.createTitledBorder(
                            "Question " + questionNumber
                    )
            );

            questionPanel.setLayout(
                    new BoxLayout(
                            questionPanel,
                            BoxLayout.Y_AXIS
                    )
            );

            JLabel questionLabel =
                    new JLabel(
                            question.getQuestionText()
                    );

            questionPanel.add(questionLabel);

            questionPanel.add(
                    Box.createVerticalStrut(10)
            );

            QuestionInput input =
                    new QuestionInput();

            input.question = question;

            // TEXT QUESTION
            if (question.getType()
                    == QuestionType.TEXT) {

                input.textField =
                        new JTextField(40);

                questionPanel.add(
                        input.textField
                );
            }

            // MULTIPLE CHOICE
            else if (question.getType()
                    == QuestionType.MULTIPLE_CHOICE) {

                input.radioGroup =
                        new ButtonGroup();

                for (String option :
                        question.getOptions()) {

                    JRadioButton radioButton =
                            new JRadioButton(option);

                    input.radioGroup.add(
                            radioButton
                    );

                    questionPanel.add(
                            radioButton
                    );
                }
            }

            // MULTIPLE ANSWER
            else if (question.getType()
                    == QuestionType.MULTIPLE_ANSWER) {

                for (String option :
                        question.getOptions()) {

                    JCheckBox checkBox =
                            new JCheckBox(option);

                    input.checkBoxes.add(
                            checkBox
                    );

                    questionPanel.add(
                            checkBox
                    );
                }
            }

            inputs.add(input);

            questionsPanel.add(
                    questionPanel
            );

            questionsPanel.add(
                    Box.createVerticalStrut(10)
            );

            questionNumber++;
        }

        mainPanel.add(
                new JScrollPane(questionsPanel),
                BorderLayout.CENTER
        );

        JPanel buttonPanel = new JPanel();

        JButton submitButton =
                new JButton("Submit Answers");

        JButton cancelButton =
                new JButton("Cancel");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // SUBMIT
        submitButton.addActionListener(e -> {

            ArrayList<String> answers =
                    new ArrayList<>();

            for (QuestionInput input : inputs) {

                String answerText = "";

                // TEXT
                if (input.question.getType()
                        == QuestionType.TEXT) {

                    answerText =
                            input.textField
                                    .getText()
                                    .trim();

                    if (answerText.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please answer all questions."
                        );
                        return;
                    }
                }

                // MULTIPLE CHOICE
                else if (input.question.getType()
                        == QuestionType.MULTIPLE_CHOICE) {

                    answerText =
                            getSelectedRadioButtonText(
                                    input.radioGroup
                            );

                    if (answerText.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please answer all questions."
                        );
                        return;
                    }
                }

                // MULTIPLE ANSWER
                else if (input.question.getType()
                        == QuestionType.MULTIPLE_ANSWER) {

                    ArrayList<String> selected =
                            new ArrayList<>();

                    for (JCheckBox checkBox :
                            input.checkBoxes) {

                        if (checkBox.isSelected()) {
                            selected.add(
                                    checkBox.getText()
                            );
                        }
                    }

                    if (selected.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Please answer all questions."
                        );
                        return;
                    }

                    answerText =
                            String.join(
                                    ", ",
                                    selected
                            );
                }

                answers.add(answerText);
            }

            // Remove previous answers for this survey
            respondent.getAnswers().removeIf(
                    answer -> answer.getSurvey() == survey
            );

            // Save each answer
            for (int i = 0;
                 i < inputs.size();
                 i++) {

                Answer answer =
                        new Answer(
                                survey,
                                inputs.get(i).question,
                                answers.get(i)
                        );

                respondent.addAnswer(answer);
            }

            JOptionPane.showMessageDialog(
                    frame,
                    "Your answers have been submitted successfully!"
            );

            showRespondentDashboard();
        });

        cancelButton.addActionListener(
                e -> showRespondentDashboard()
        );

        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // ---------------------------------------------------------
    // GET SELECTED RADIO BUTTON
    // ---------------------------------------------------------

    private String getSelectedRadioButtonText(
            ButtonGroup group
    ) {

        Enumeration<AbstractButton> buttons =
                group.getElements();

        while (buttons.hasMoreElements()) {

            AbstractButton button =
                    buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return "";
    }
}