package org.kuali.student.r2.common.util;

/**
 * Used to help format the time
 */
public enum TimeOfDayFormattingEnum {
    USE_TWO_DIGITS_FOR_HOURS, // Use a leading zero if this exists (By default, use one digit)
    USE_MILITARY_TIME, // Use military time 0-23 (By default, use 12 hour time)
    USE_ALL_CAPS_AM_PM, // Use AM/PM. (By default, use lowercase am/pm).  Not used in military time.  Takes precedence
                        // over CAPITALIZE_AM_PM
    CAPITALIZE_AM_PM, // Use Am/Pm.  (By default, use lowercase am/pm). Not used in military time
    USE_ONLY_FIRST_LETTER_AM_PM // Either A/P depending on capitalization of previous (By default, use two letters
                                // unless military time
}
