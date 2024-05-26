package com.jpozarycki.springaifunctions.services;

import com.jpozarycki.springaifunctions.model.Answer;
import com.jpozarycki.springaifunctions.model.Question;

public interface QuestionService {
    Answer getAnswer(Question question);
}
