package com.example.demo.utils;

public interface Container {
    public static final int MIN_NUMBER_OF_PRESCRIPTIONS = 1;
    public static final int MAX_NUMBER_OF_PRESCRIPTIONS = 3;

    public static final String LOW_DIAGNOSIS = "low";
    public static final String MEDIUM_DIAGNOSIS = "medium";
    public static final String HARD_DIAGNOSIS = "hard";

    public static final String PRESCRIPTION_MEDICINE = "medicine";
    public static final String PRESCRIPTION_PROCEDURE = "procedure";
    public static final String PRESCRIPTION_OPERATION = "operations";

    public static final String REGEX_NAME_ENG="^[A-Z][a-z]{7,29}$";
    public static final String REGEX_EMAIL = "([a-z0-9_-]+\\.)*[a-z0-9_-]+\\@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    public static final String REGEX_PASSWORD = "^[A-Za-z0-9_-]{8,30}$";
}
