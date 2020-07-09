package com.example.demo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputCheckData {
    private static final Logger logger = LogManager.getLogger(InputCheckData.class);

    public static boolean inputCheck(String input, String regex)  {
        if (input.matches(regex))
            return true;
        else
            return false;
    }

    public static boolean validInputData (String name, String surname, String email, String password) {
        boolean isValid;
        isValid = inputCheck(name, Container.REGEX_NAME_ENG);
        if (!isValid) {
            logger.debug("Non-valid name entered");
            return false;
        }
        isValid = inputCheck(surname, Container.REGEX_NAME_ENG);
        if (!isValid) {
            logger.debug("Non-valid surname entered");
            return false;
        }
        isValid = inputCheck(email, Container.REGEX_EMAIL);
        if (!isValid) {
            logger.debug("Non-valid email entered");
            return false;
        }
        isValid = inputCheck(password, Container.REGEX_PASSWORD);
        if (!isValid) {
            logger.debug("Non-valid password entered");
            return false;
        }
        return true;
    }
}
