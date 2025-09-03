package com.ferreteriafc.api.backend.domain.utils;

public class Constant {
    public static final int CATEGORIES_NAME_MAX_LENGTH = 50;

    public static final int BRANDS_NAME_MAX_LENGTH = 255;

    public static final int PRODUCTS_DESCRIPTION_MAX_LENGTH = 255;
    public static final int PRODUCTS_CODE_MAX_LENGTH = 255;

    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{" + USERNAME_MIN_LENGTH + "," + USERNAME_MAX_LENGTH + "}$";

    public static final int PASSWORD_MAX_LENGTH = 255;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{" + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}$";

    public static final int EMAIL_MAX_LENGTH = 50;
    public static final int EMAIL_MIN_LENGTH = 2;
    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$";

}
