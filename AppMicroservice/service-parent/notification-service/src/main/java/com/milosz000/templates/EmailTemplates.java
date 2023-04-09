package com.milosz000.templates;

public class EmailTemplates {

    public static String getConfirmationMailTemplate(String name, String confirmationLink) {
        return "Hello, " + name + "!\n\n"
                + "You are almost ready to start enjoying MovieService!\n"
                + "Please confirm your account. Just hit the link:\n" + confirmationLink;
    }

    public static String getResetPasswordMailTemplate(String name, String resetPasswordLink) {
        return "Hi " + name + ",\n\n"
                + "Forgot your password?\n"
                + "We received a request to reset the password for your account.\n\n"
                + "To reset your password, hit the link:\n" + resetPasswordLink;
    }
}
