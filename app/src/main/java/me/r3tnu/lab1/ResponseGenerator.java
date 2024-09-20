package me.r3tnu.lab1;

import java.nio.charset.StandardCharsets;

public class ResponseGenerator {
    public String generateResponseString(int responseCode, String contentType, String content) {
        String httpResponse = """
            HTTP/1.1 %d
            Content-Type: %s
            Content-Length: %d

            %s

            """.formatted(responseCode, contentType, content.getBytes(StandardCharsets.UTF_8).length, content);
            return httpResponse;
    }
}
