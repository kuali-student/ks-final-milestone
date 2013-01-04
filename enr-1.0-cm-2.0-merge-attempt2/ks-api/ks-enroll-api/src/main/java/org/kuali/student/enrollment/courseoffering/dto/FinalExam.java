package org.kuali.student.enrollment.courseoffering.dto;

/**
 * @Author Sri komandur@uw.edu
 *
 * Our use case involves jpa persistence, storing enum in attributes
 * as 'string' and conversion back to enum This design, explicitly
 * specifying 'business type' gives flexibility and avoids pitfalls in
 * the future when a stored enum is renamed, for example.
 */
public enum FinalExam {

    STANDARD("STANDARD"), ALTERNATE("ALTERNATE"), NONE("NONE");
    
    private final String name;

    FinalExam(String name) {
        this.name = name;
    }

    public static FinalExam toEnum(String value) {
        if (value != null) {
            for (FinalExam finalExam : values()) {
                if (finalExam.name.equals(value)) {
                    return finalExam;
                }
            }
        }
        return FinalExam.NONE;
    }

    @Override
    public String toString() {
        return name;
    }
}