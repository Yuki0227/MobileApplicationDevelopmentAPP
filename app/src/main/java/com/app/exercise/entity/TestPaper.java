package com.app.exercise.entity;


import com.app.exercise.entity.question.ChoiceQuestion;
import com.app.exercise.entity.question.JudgmentQuestion;
import com.app.exercise.entity.question.ProgrammingQuestion;
import com.app.exercise.entity.question.Question;

import java.util.ArrayList;
import java.util.List;


public class TestPaper {
    private final List<Question> questions = new ArrayList<>();
    boolean added = false;
    private List<ProgrammingQuestion> programmingQuestions;
    private List<ChoiceQuestion> choiceQuestions;
    private List<JudgmentQuestion> judgmentQuestions;

    public void setProgrammingQuestions(List<ProgrammingQuestion> programmingQuestions) {
        this.programmingQuestions = programmingQuestions;
    }

    public void setChoiceQuestions(List<ChoiceQuestion> choiceQuestions) {
        this.choiceQuestions = choiceQuestions;
    }


    public void setJudgmentQuestions(List<JudgmentQuestion> judgmentQuestions) {
        this.judgmentQuestions = judgmentQuestions;
    }

    public void init() {
        if (!added) {
            questions.addAll(choiceQuestions);
            questions.addAll(judgmentQuestions);
            questions.addAll(programmingQuestions);
            added = true;
        }
    }

    public int getCount() {
        init();
        return questions.size();
    }

    public Question getQuestion(int id) {
        init();
        return questions.get(id);
    }

}