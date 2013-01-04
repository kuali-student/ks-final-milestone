package org.kuali.student.enrollment.acal;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SqlGenerator {
    private String userId = "admin";
    private static final String ATP_STATE = "kuali.atp.state.Official";
    private static final String MILESTONE_STATE = "kuali.milestone.state.Official";
    private static final String ATPATPRELATION_STATE = "kuali.atp.atp.relation.state.active";

    private Set<String> holiCals = new HashSet<String>();

    public StringBuilder getSqlForAcademicCalendar (StringBuilder builder, Atp acal) {
        if (null == builder) {
            builder = new StringBuilder();
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(acal.getStartDate());
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        Date createDate = cal.getTime();

        // create Acal
        getAtpInsert(builder, acal, createDate);

        for (Atp holiCal : acal.getHolidayCalendars()) {
            if (!holiCals.contains(holiCal.getId())) {
                // create HoliCal
                getAtpInsert(builder, holiCal, createDate);
            }

            // connect HoliCal to Acal
            getAtpAtpRelationSql(builder, acal, holiCal, "kuali.atp.atp.relation.associated", createDate);

            if (!holiCals.contains(holiCal.getId())) {
                for (Milestone holiday : holiCal.getMilestones()) {
                    // create Holiday
                    getMilestoneInsert(builder, holiday, createDate);
                    // connect Holiday to HoliCal
                    getAtpMilestoneRelationSql(builder, holiCal, holiday, createDate);
                }
                holiCals.add(holiCal.getId());
            }
        }

        for (Atp term : acal.getTerms()) {
            // create Term
            getAtpInsert(builder, term, createDate);
            // connect Term to Acal
            getAtpAtpRelationSql(builder, acal, term, "kuali.atp.atp.relation.includes", createDate);

            for (Milestone keydate : term.getMilestones()) {
                // create Keydate
                getMilestoneInsert(builder, keydate, createDate);
                // connect Keydate to Term
                getAtpMilestoneRelationSql(builder, term, keydate, createDate);
            }

            for (Atp subTerm : term.getTerms()) {
                // create SubTerm
                getAtpInsert(builder, subTerm, createDate);
                // connect SubTerm to Term
                getAtpAtpRelationSql(builder, term, subTerm, "kuali.atp.atp.relation.includes", createDate);

                for (Milestone keydate : subTerm.getMilestones()) {
                    // create Keydate
                    getMilestoneInsert(builder, keydate, createDate);
                    // connect Keydate to SubTerm
                    getAtpMilestoneRelationSql(builder, subTerm, keydate, createDate);
                }
            }
        }

        return builder;
    }

    private StringBuilder getAtpInsert(StringBuilder builder, Atp atp, Date created) {
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO KSEN_ATP ");
        statement.append("( ID");
        statement.append(", OBJ_ID");
        statement.append(", ATP_TYPE");
        statement.append(", ATP_STATE");
        statement.append(", NAME");
        statement.append(", DESCR_PLAIN");
        statement.append(", DESCR_FORMATTED");
        statement.append(", ATP_CD");
        statement.append(", END_DT");
        statement.append(", START_DT");
        statement.append(", ADMIN_ORG_ID");
        statement.append(", VER_NBR");
        statement.append(", CREATETIME");
        statement.append(", CREATEID");
        statement.append(", UPDATETIME");
        statement.append(", UPDATEID");
        statement.append(") VALUES (");
        statement.append("  ").append(getInput(atp.getId()));
        statement.append(", ").append(getInput(getUUID()));
        statement.append(", ").append(getInput(atp.getType()));
        statement.append(", ").append(getInput(ATP_STATE));
        statement.append(", ").append(getInput(atp.getName()));
        statement.append(", ").append(getInput(atp.getDescriptionPlain()));
        statement.append(", ").append(getInput(atp.getDescriptionFormatted()));
        statement.append(", ").append(getInput(atp.getAtpCode()));
        statement.append(", ").append(getInput(atp.getEndDate()));
        statement.append(", ").append(getInput(atp.getStartDate()));
        statement.append(", ").append(getInput(atp.getAdminOrgId()));
        statement.append(", 0");
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(")");
        statement.append("\n");
        statement.append("/\n");

        if (builder == null) {
            return statement;
        }
        return builder.append(statement);
    }

    private StringBuilder getMilestoneInsert(StringBuilder builder, Milestone milestone, Date created) {
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO KSEN_MSTONE ");
        statement.append("( ID");
        statement.append(", OBJ_ID");
        statement.append(", MSTONE_TYPE");
        statement.append(", MSTONE_STATE");
        statement.append(", NAME");
        statement.append(", DESCR_PLAIN");
        statement.append(", DESCR_FORMATTED");
        statement.append(", IS_ALL_DAY");
        statement.append(", IS_INSTRCT_DAY");
        statement.append(", IS_RELATIVE");
        statement.append(", RELATIVE_ANCHOR_MSTONE_ID");
        statement.append(", IS_DATE_RANGE");
        statement.append(", START_DT");
        statement.append(", END_DT");
        statement.append(", VER_NBR");
        statement.append(", CREATETIME");
        statement.append(", CREATEID");
        statement.append(", UPDATETIME");
        statement.append(", UPDATEID");
        statement.append(") VALUES (");
        statement.append("  ").append(getInput(milestone.getId()));
        statement.append(", ").append(getInput(getUUID()));
        statement.append(", ").append(getInput(milestone.getType()));
        statement.append(", ").append(getInput(MILESTONE_STATE));
        statement.append(", ").append(getInput(milestone.getName()));
        statement.append(", ").append(getInput(milestone.getDescriptionPlain()));
        statement.append(", ").append(getInput(milestone.getDescriptionFormatted()));
        statement.append(", ").append(getInput(milestone.isAllDay()));
        statement.append(", 0");
        statement.append(", ").append(getInput(milestone.isRelative()));
        statement.append(", ").append(getInput(milestone.getRelativeMilestoneId()));
        statement.append(", ").append(getInput(milestone.isDateRange()));
        statement.append(", ").append(getInput(milestone.getStartDate()));
        statement.append(", ").append(getInput(milestone.getEndDate()));
        statement.append(", 0");
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(")");
        statement.append("\n");
        statement.append("/\n");

        if (builder == null) {
            return statement;
        }
        return builder.append(statement);
    }

    public StringBuilder getAtpAtpRelationSql(StringBuilder builder, Atp atp, Atp relatedAtp, String relationType, Date created) {
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO KSEN_ATPATP_RELTN ");
        statement.append("( ID");
        statement.append(", OBJ_ID");
        statement.append(", ATP_TYPE");
        statement.append(", ATP_STATE");
        statement.append(", ATP_ID");
        statement.append(", RELATED_ATP_ID");
        statement.append(", EFF_DT");
        statement.append(", EXPIR_DT");
        statement.append(", VER_NBR");
        statement.append(", CREATETIME");
        statement.append(", CREATEID");
        statement.append(", UPDATETIME");
        statement.append(", UPDATEID");
        statement.append(") VALUES (");
        statement.append("  ").append(getInput(atp.getId() + relatedAtp.getId() + "RELATION"));
        statement.append(", ").append(getInput(getUUID()));
        statement.append(", ").append(getInput(relationType));
        statement.append(", ").append(getInput(ATPATPRELATION_STATE));
        statement.append(", ").append(getInput(atp.getId()));
        statement.append(", ").append(getInput(relatedAtp.getId()));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput((Date)null));
        statement.append(", 0");
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(")");
        statement.append("\n");
        statement.append("/\n");

        if (builder == null) {
            return statement;
        }
        return builder.append(statement);
    }

    public StringBuilder getAtpMilestoneRelationSql(StringBuilder builder, Atp atp, Milestone milestone, Date created) {
        StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO KSEN_ATPMSTONE_RELTN ");
        statement.append("( ID");
        statement.append(", OBJ_ID");
        statement.append(", VER_NBR");
        statement.append(", CREATEID");
        statement.append(", CREATETIME");
        statement.append(", UPDATEID");
        statement.append(", UPDATETIME");
        statement.append(", ATP_ID");
        statement.append(", MSTONE_ID");
        statement.append(") VALUES (");
        statement.append("  ").append(getInput(atp.getId() + milestone.getId() + "RELATION"));
        statement.append(", ").append(getInput(getUUID()));
        statement.append(", 0");
        statement.append(", ").append(getInput(userId));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(userId));
        statement.append(", ").append(getInput(created));
        statement.append(", ").append(getInput(atp.getId()));
        statement.append(", ").append(getInput(milestone.getId()));
        statement.append(")");
        statement.append("\n");
        statement.append("/\n");

        if (builder == null) {
            return statement;
        }
        return builder.append(statement);
    }

    private String getInput(String string) {
        if (string == null) {
            return "NULL";
        }
        string = string.replaceAll("'", "''");
        return "'"+string+"'";
    }

    private String getInput(Date date) {
        if (date == null) {
            return "NULL";
        }
        SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//        return "{ts '" + timestamp.format(date) + "'}";
        return "TO_TIMESTAMP('" + timestamp.format(date) + "', 'YYYY-MM-DD HH24:MI:SS.FF')";
    }

    private int getInput(boolean value) {
        return (value) ? 1 : 0;
    }

    private String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
