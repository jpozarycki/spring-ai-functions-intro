package com.jpozarycki.springaifunctions.services;

import com.jpozarycki.springaifunctions.model.Answer;
import com.jpozarycki.springaifunctions.model.Question;
import com.jpozarycki.springaifunctions.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final OpenAiChatClient chatClient;
    @Value("${api-ninjas.key}")
    public String apiKey;

    @Override
    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(apiKey))
                        .withName("currentWeather")
                        .withDescription("Get the current weather for a location")
                                .withResponseConverter((response) -> {
                                    String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                                    String json = ModelOptionsUtils.toJsonString(response);
                                    return schema + "\n" + json;
                                })
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();

        Message systemMessage = new SystemPromptTemplate("You are a weather sevice. You receive weather information from a service which gives you the information in metrics systems. You should convert epoch GMT time into HH:MM GMT format").createMessage();

        var chatResponse = chatClient.call(new Prompt(List.of(systemMessage, userMessage), promptOptions));
        return new Answer(chatResponse.getResult().getOutput().getContent());
    }
}
