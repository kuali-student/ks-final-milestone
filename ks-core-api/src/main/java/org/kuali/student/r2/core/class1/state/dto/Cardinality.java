package org.kuali.student.r2.core.class1.state.dto;

/**
 * @Author Sri komandur@uw.edu
 *
 * This design, explicitly specifies 'business type' and gives flexibility and avoids pitfalls in
 * the future when a stored enum is renamed.
 */
public enum Cardinality {

    EXISTS("EXISTS"), ALL("ALL"), NONE("NONE");

    private final String name;

    Cardinality(String name) {
        this.name = name;
    }

    public static Cardinality toEnum(String value) {
        if (value != null) {
            for (Cardinality cardinality : values()) {
                if (cardinality.name.equals(value)) {
                    return cardinality;
                }
            }
        }
        return Cardinality.NONE;
    }

    @Override
    public String toString() {
        return name;
    }
}