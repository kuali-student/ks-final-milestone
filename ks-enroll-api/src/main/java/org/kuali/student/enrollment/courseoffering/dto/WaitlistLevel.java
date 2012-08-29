package org.kuali.student.enrollment.courseoffering.dto;

/**
 * @Author Charles cclin@umd.edu (Modeled after Sri's FinalExam enum
 *
 * Our use case involves jpa persistence, storing enum in attributes as 'string' and conversion back to enum
 * This design, explicitly specifying 'business type'  gives flexibility and avoids pitfalls in the future when a stored enum
 * is renamed, for example.
 */
public enum WaitlistLevel {
    // Waitlist can be at the course offering or activity offering level
    COURSE_OFFERING("COURSE_OFFERING"), ACTIVITY_OFFERING("ACTIVITY_OFFERING");

    private final String name;

    private WaitlistLevel(String name) {
        this.name = name;
    }

    public static WaitlistLevel toEnum(String value) {
        if (value != null) {
            for (WaitlistLevel waitlistLevel : values()) {
                if (waitlistLevel.name.equals(value)) {
                    return waitlistLevel;
                }
            }
        }
        return WaitlistLevel.ACTIVITY_OFFERING;
    }

    @Override
    public String toString() {
        return name;
    }
}
