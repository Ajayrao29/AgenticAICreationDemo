package org.hartford.agenticaidemo.controller;

import org.hartford.agenticaidemo.service.QAAgentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Allow for local development frontend
public class ChatController {

    private final QAAgentService qaAgentService;

    public ChatController(QAAgentService qaAgentService) {
        this.qaAgentService = qaAgentService;
    }

    @PostMapping
    public String chat(@RequestParam String chatId, @RequestBody String question) {
        return qaAgentService.ask(chatId, question);
    }

    @GetMapping("/test")
    public String test() {
        return "Agentic AI Demo is running!";
    }
}
