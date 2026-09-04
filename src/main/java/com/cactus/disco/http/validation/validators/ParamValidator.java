package com.cactus.disco.http.validation.validators;

import java.util.Map;

public interface ParamValidator {

    String validate(Map<String, String> input);

    default String generateErrorMessage(String msg, Object... args) {
        return String.format(msg, args);
    }
}
