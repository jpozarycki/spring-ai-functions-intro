package com.jpozarycki.springaifunctions.services;

import com.jpozarycki.springaifunctions.model.Answer;
import com.jpozarycki.springaifunctions.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final ChatClient chatClient;

    @Override
    public Answer getAnswer(Question question) {
        return new Answer("Not implemented yet!");
    }
}
