package com.cactus.disco.http.validation;

import com.cactus.disco.exception.CactusDiscoClientException;
import com.cactus.disco.http.validation.validators.ParamValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.coyote.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class HttpRequestValidator {

    protected void validate(List<ParamValidator> validatorList, Map<String, String> parameterMap) {
        List<String> validationErrors = new ArrayList<>();
        for (ParamValidator validator : validatorList) {
            String errorMsg = validator.validate(parameterMap);
            if (Objects.nonNull(errorMsg)){
                validationErrors.add(errorMsg);
            }
        }

        if (CollectionUtils.isNotEmpty(validationErrors)) {
            handleErrors(String.join("\n", validationErrors));
        }
    }

    private void handleErrors(String errors) {
        throw new CactusDiscoClientException(errors);
    }

}
