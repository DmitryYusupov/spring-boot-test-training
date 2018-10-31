package ru.yusdm.training.springboottest.user.exceptions;

public enum Exceptions {
    ERROR_500_WHILE_INSERT("Error while persist record"),
    ERROR_500_WHILE_UPDATE("Error while update record"),
    ERROR_500_WHILE_DELETE("Error while delete record");

    private String errorMessage;

    Exceptions(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
