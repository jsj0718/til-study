package me.jsj.thejava.codemanipulation.bytecode.jacoco;

public class Moim {
    private int maxNumberOfAttendees;

    private int numberOfEnrollment;

    public boolean isEnrollmentFull() {
        if (maxNumberOfAttendees == 0) return false;
        if (numberOfEnrollment < maxNumberOfAttendees) return false;
        return true;
    }

    public int getMaxNumberOfAttendees() {
        return maxNumberOfAttendees;
    }

    public void setMaxNumberOfAttendees(int maxNumberOfAttendees) {
        this.maxNumberOfAttendees = maxNumberOfAttendees;
    }

    public int getNumberOfEnrollment() {
        return numberOfEnrollment;
    }

    public void setNumberOfEnrollment(int numberOfEnrollment) {
        this.numberOfEnrollment = numberOfEnrollment;
    }
}
