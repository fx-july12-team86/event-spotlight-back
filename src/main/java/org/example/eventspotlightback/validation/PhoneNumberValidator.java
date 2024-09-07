package org.example.eventspotlightback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final String PATTERN_OF_UKRAINE_NUMBER = "^\\+380[3-9][0-9]{8}$";

    @Override
    public boolean isValid(
            String phoneNumber,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return phoneNumber != null
                && Pattern.compile(PATTERN_OF_UKRAINE_NUMBER).matcher(phoneNumber).matches();
    }
}
