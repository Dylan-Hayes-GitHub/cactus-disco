package com.cactus.disco.http.validation;

import com.cactus.disco.http.validation.validators.ParamValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetRequestValidator extends HttpRequestValidator {
    private final List<ParamValidator> getSensorByIdValidators;
    private final List<ParamValidator> getAllSensorValidators;

    public GetRequestValidator(List<ParamValidator> getSensorByIdValidators, List<ParamValidator> getAllSensorValidators) {
        this.getSensorByIdValidators = getSensorByIdValidators;
        this.getAllSensorValidators = getAllSensorValidators;
    }

    public void validateGetSensorById(Map<String, String> paramMap) {
        validate(getSensorByIdValidators, paramMap);
    }

    public void validateGetAllSensors(Map<String, String> paramMap) {
        validate(getAllSensorValidators, paramMap);
    }
}
