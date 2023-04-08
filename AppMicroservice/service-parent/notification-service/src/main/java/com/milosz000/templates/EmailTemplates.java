package com.milosz000.templates;

public class EmailTemplates {

    public static String getConfirmationMailTemplate(String name, String confirmationLink) {
        return "Hello, " + name + "!\n\n"
                + "You are almost ready to start enjoying MovieService!\n"
                + "Please confirm your account. Just hit the link: " + confirmationLink;
    }
}
