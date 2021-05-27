package edu.ntnu.bsc020backend.models;

import java.util.ArrayList;
import java.util.List;

public class SolutionMessage {
    private final String source;
    private final List<String> message;

    public SolutionMessage(String source){
        this.source = source;
        message = new ArrayList<>();
    }

    public boolean addMessage(String message){
        return this.message.add(message);
    }

    public String getSource() {
        return source;
    }

    public String getMessage(int index) {
        return message.get(index);
    }

    public List<String> getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "\n{" +
                "source='" + source + '\'' +
                ", message=" + message +
                '}';
    }
}
