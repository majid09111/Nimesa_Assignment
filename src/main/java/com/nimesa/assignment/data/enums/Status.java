package com.nimesa.assignment.data.enums;

public enum Status {

    COMPLETED("Completed"),
    IN_PROGRESS("In Progress"),
    FAILED("Failed");

    String status;

    Status(String status){
        this.status = status;
    }

}
