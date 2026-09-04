package com.cactus.disco.http.validation.validators;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DateValidator implements ParamValidator {

    private final String INVALID_DATE_PROVIDED = "Invalid date format provided %s, please provide a date in the format of yyyy-mm-dd";
    private final String DATE_PROVIDED_IS_IN_THE_FUTURE = "Invalid date provided %s, please provide a date not in the future time travel is not possible.";
    private final String NO_DATE_PROVIDED = "No %s date was provided please provided a date in the format yyyymmdd";
    @Override
    public String validate(Map<String, String> input) {

        String fromDate = input.getOrDefault("from", null);

        if(Objects.isNull(fromDate)) {
            return generateErrorMessage(NO_DATE_PROVIDED, "from");
        }

        String toDate = input.getOrDefault("to", null);

        if(Objects.isNull(toDate)) {
            return generateErrorMessage(NO_DATE_PROVIDED, "to");
        }

        List<String> validationErrors = new ArrayList<>();

        validateDate(fromDate, validationErrors);
        validateDate(toDate, validationErrors);

        if (CollectionUtils.isNotEmpty(validationErrors)) {
            return String.join("\n", validationErrors);
        }

        return null;

    }

    private void validateDate(String dateInStringFormat, List<String> validationErrors) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateInStringFormat);
        } catch (DateTimeParseException exception) {
            validationErrors.add(generateErrorMessage(INVALID_DATE_PROVIDED, dateInStringFormat));
            return;
        }
        if (date.isAfter(LocalDate.now())) {
            validationErrors.add(generateErrorMessage(DATE_PROVIDED_IS_IN_THE_FUTURE, dateInStringFormat));
        }
    }
}
