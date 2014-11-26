package com.impetus.bookstore.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.impetus.bookstore.model.UserDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterFormValidator.
 */
public class RegisterFormValidator implements Validator {

    /** The confirm password. */
    private String confirmPassword;

    /** The pattern. */
    private Pattern pattern;

    /** The matcher. */
    private Matcher matcher;

    /** The Constant EMAIL_PATTERN. */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /** The string pattern. */
    String STRING_PATTERN = "[a-zA-Z]+";

    /** The mobile pattern. */
    String MOBILE_PATTERN = "[0-9]{10}";

    /**
     * Instantiates a new form validator.
     * 
     * @param confirmPassword
     *            the confirm password
     */
    public RegisterFormValidator(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> arg0) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {

        UserDetails ud = (UserDetails) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "required.name",
                "Name is required.");
        if (!(ud.getName() != null && ud.getName().isEmpty())) {
            pattern = Pattern.compile(STRING_PATTERN);
            matcher = pattern.matcher(ud.getName());
            if (!matcher.matches()) {
                errors.rejectValue("name", "name.containNonChar",
                        "Enter a valid name");
            }
            if ((ud.getName().length() < 3) || (ud.getName().length() > 25)) {
                errors.rejectValue("name", "name.length",
                        "Name must be between 3 - 25 characters");
            }
        }

        ValidationUtils.rejectIfEmpty(errors, "username", "required.username",
                "Username is required.");
        if (!(ud.getUsername() != null && ud.getUsername().isEmpty())) {
            pattern = Pattern.compile(STRING_PATTERN);
            matcher = pattern.matcher(ud.getName());
            if (!matcher.matches()) {
                errors.rejectValue("username", "username.containNonChar",
                        "Enter a valid username");
            }
            if ((ud.getUsername().length() < 3)
                    || (ud.getUsername().length() > 10)) {
                errors.rejectValue("username", "username.length",
                        "Username must be between 3 - 10 characters");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Password is required.");
        if (!ud.getPassword().equals(confirmPassword)) {
            errors.rejectValue("password", "password.mismatch",
                    "Password does not match");
            if ((ud.getPassword().length() < 3)
                    || (ud.getPassword().length() > 15)) {
                errors.rejectValue("password", "password.length",
                        "Password must be between 3 - 15 characters");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eid",
                "required.eid", "Email is required.");
        if (!(ud.getEid() != null && ud.getEid().isEmpty())) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(ud.getEid());
            if (!matcher.matches()) {
                errors.rejectValue("eid", "eid.incorrect",
                        "Enter a correct email");
            }
        }

        ValidationUtils.rejectIfEmpty(errors, "address", "required.address",
                "Address is required.");
        if (!(ud.getAddress() != null && ud.getAddress().isEmpty())) {
            if (ud.getAddress().length() < 5) {
                errors.rejectValue("address", "address.length",
                        "Address too short");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact",
                "required.contact", "Contact is required.");
        if (!(ud.getContact() != null && ud.getContact().isEmpty())) {
            pattern = Pattern.compile(MOBILE_PATTERN);
            matcher = pattern.matcher(ud.getContact());
            if (!matcher.matches()) {
                errors.rejectValue("contact", "contact.incorrect",
                        "Enter a correct phone number");
            }
        }
    }
}
