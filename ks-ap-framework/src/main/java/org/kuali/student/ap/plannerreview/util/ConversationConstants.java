package org.kuali.student.ap.plannerreview.util;

/**
 * 
 * @author Chris Maurer <chmaurer@iupui.edu>
 *
 */
public class ConversationConstants {

	/**
	 * Type key to indicate an advisor
	 */
	public static final String CONV_ADVISOR_ID_TYPE_ADVISOR = "kuali.ap.advisor.type.advisor";

	/**
	 * Type key to indicate an advising office
	 */
	public static final String CONV_ADVISOR_ID_TYPE_ADVISING_OFFICE = "kuali.ap.advisor.type.advisingOffice";
	
	/**
	 * Type key to indicate a student
	 */
	public static final String CONV_COMMENTER_TYPE_STUDENT = "kuali.ap.advisor.type.student";
	
	/**
	 * Constant to indicate that you are on step 1 of the conversation process
	 */
	public static final int CREATE_CONV_WIZARD_STEP1 = 1;
	
	/**
	 * Constant to indicate that you are on step 2 of the conversation process
	 */
	public static final int CREATE_CONV_WIZARD_STEP2 = 2;
	
	/**
	 * Constant to indicate that you are on step 3 of the conversation process
	 */
	public static final int CREATE_CONV_WIZARD_STEP3 = 3;
	
	/**
	 * Type key to indicate that the topic type is "all", meaning that it is for the entire plan
	 */
	public static final String CONV_TOPIC_TYPE_ALL = "kuali.ap.plan.topic.type.all";
	
	/**
	 * Type key to indicate that the topic type is "terms", meaning that it is for choosing terms
	 */
	public static final String CONV_TOPIC_TYPE_TERMS = "kuali.ap.plan.topic.type.terms";
	
	/**
	 * Type key to indicate that the topic type is "courses", meaning that it is for choosing individual courses
	 */
	public static final String CONV_TOPIC_TYPE_COURSES = "kuali.ap.plan.topic.type.courses";
	
	/**
	 * Number of items that will be displayed in the message title before truncating and adding " and X more"
	 */
	public static final int CONV_MESSAGE_TITLE_ITEM_LENGTH = 3;
	
	/**
	 * Type key to indicate a planned course
	 */
	public static final String CONV_COURSE_TYPE_PLANNED = "kuali.ap.plan.course.type.planned";
	
	/**
	 * Type key to indicate a backup course
	 */
	public static final String CONV_COURSE_TYPE_BACKUP = "kuali.ap.plan.course.type.backup";
	
	/* Message bundle keys */
	
	public static final String CONV_MISSING_ADVISOR_KEY = "review.missing_advisor";
	public static final String CONV_MISSING_TOPICTYPE_KEY = "review.missing_topicType";
	public static final String CONV_MISSING_TERMS_KEY = "review.missing_terms";
	public static final String CONV_MISSING_COURSES_KEY = "review.missing_courses";
	public static final String CONV_MISSING_MESSAGETEXT_KEY = "review.missing_messageText";
	
}
