package org.cinehub.utils;

public class UserValidationUtils {
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,15}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$";

    /**
     * Checks if the username is valid (contains only letters, numbers, underscores and dashes
     * and has a length between 3 and 15 characters)
     *
     * @param username The username to check
     * @return true if the username is valid, false otherwise
     */
    public static boolean isUsernameValid(String username) {
        return username.matches(USERNAME_REGEX);
    }

    /**
     * Checks if the password is secure (contains at least one lowercase letter, one uppercase
     * letter, one digit and one special character
     *
     * @param password The password to check
     * @return true if the password is secure, false otherwise
     */
    public static boolean isPasswordSecure(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Checks if the password and the confirmation password are equal
     *
     * @param password The password
     * @param confirmPassword The confirmation password
     * @return true if the passwords are equal, false otherwise
     */
    public static boolean arePasswordsEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /**
     * Checks if the email is valid
     *
     * @param email The email to check
     * @return true if the email is valid, false otherwise
     */
    public static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
