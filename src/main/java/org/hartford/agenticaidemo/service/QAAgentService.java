package org.hartford.agenticaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QAAgentService {

    private final ChatClient chatClient;

    public QAAgentService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are a helpful and professional insurance agent assistant for 'Hartford Life'. " +
                        "Use the provided tools to lookup policies, calculate premiums, or answer FAQs. " +
                        "If you don't know the answer, say you don't know and suggest contacting support.")
                .defaultFunctions("policy_lookup", "calculate_premium", "get_policy_faq")
                .build();
    }

    public String ask(String chatId, String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
