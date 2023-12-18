package com.eeze.techinterview.controller.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ControllerExceptionDto {

    private List<String> messages = new ArrayList<>();

    private ControllerExceptionDto(Iterable<String> messages) { messages.forEach(this.messages::add); }

    public List<String> getMessages() { return Collections.unmodifiableList(messages); }

    public static ControllerExceptionDto of (String messages) {
        return new ControllerExceptionDto(Arrays.asList(messages));
    }

    public static ControllerExceptionDto of (String... messages) {
        return new ControllerExceptionDto(Arrays.asList(messages));
    }

    public static ControllerExceptionDto of (Iterable<String> messages){ return new ControllerExceptionDto(messages); }
}
