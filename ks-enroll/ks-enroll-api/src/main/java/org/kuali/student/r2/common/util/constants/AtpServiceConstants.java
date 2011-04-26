/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpMilestoneRelationInfo;

/**
 * This class holds the constants used by the ATP service
 *
 * @author nwright
 */
public class AtpServiceConstants {

    /**
     * Reference Object URI's
     */

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "atp";
    public static final String REF_OBJECT_URI_ATP = NAMESPACE + "/" + AtpInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MILESTONE = NAMESPACE + "/" + MilestoneInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ATP_MILESTONE_RELATION = NAMESPACE + "/" + AtpMilestoneRelationInfo.class.getSimpleName();
    /**
     * ATP Types
     */
    public static final String ATP_ACADEMIC_CALENDAR_TYPE_KEY = "kuali.atp.type.AcademicCalendar";
    public static final String ATP_CAMPUS_CALENDAR_TYPE_KEY = "kuali.atp.type.CampusCalendar";
    // reporting groups
    public static final String ATP_AY_TYPE_KEY = "kuali.atp.type.AY";
    public static final String ATP_FY_TYPE_KEY = "kuali.atp.type.FY";
    // terms
    public static final String ATP_FALL_TYPE_KEY = "kuali.atp.type.Fall";
    public static final String ATP_HALF_FALL_1_TYPE_KEY = "kuali.atp.type.HalfFall1";
    public static final String ATP_HALF_FALL_2_TYPE_KEY = "kuali.atp.type.HalfFall2";
    public static final String ATP_HALF_SPRING_1_TYPE_KEY = "kuali.atp.type.HalfSpring1";
    public static final String ATP_HALF_SPRING_2_TYPE_KEY = "kuali.atp.type.HalfSpring2";
    public static final String ATP_MINI_MESTER_1_A_TYPE_KEY = "kuali.atp.type.Mini-mester1A";
    public static final String ATP_MINI_MESTER_1_B_TYPE_KEY = "kuali.atp.type.Mini-mester1B";
    public static final String ATP_MINI_MESTER_2_C_TYPE_KEY = "kuali.atp.type.Mini-mester2C";
    public static final String ATP_MINI_MESTER_2_D_TYPE_KEY = "kuali.atp.type.Mini-mester2D";
    public static final String ATP_SESSION_1_TYPE_KEY = "kuali.atp.type.Session1";
    public static final String ATP_SESSION_2_TYPE_KEY = "kuali.atp.type.Session2";
    public static final String ATP_SESSION_G1_TYPE_KEY = "kuali.atp.type.SessionG1";
    public static final String ATP_SESSION_G2_TYPE_KEY = "kuali.atp.type.SessionG2";
    public static final String ATP_SPRING_TYPE_KEY = "kuali.atp.type.Spring";
    public static final String ATP_SPRING_BREAK_TYPE_KEY = "kuali.atp.type.SpringBreak";
    public static final String ATP_SUMMER_TYPE_KEY = "kuali.atp.type.Summer";
    public static final String ATP_SUMMER_EVE_TYPE_KEY = "kuali.atp.type.SummerEve";
    public static final String ATP_WINTER_TYPE_KEY = "kuali.atp.type.Winter";
    // adhoc
    public static final String ATP_ADHOC_TYPE_KEY = "kuali.atp.type.Adhoc";
    // program
    public static final String ATP_UNDERGRAD_PROGRAM_TYPE_KEY = "kuali.atp.type.UndergradProgram";
    public static final String ATP_FRESHMAN_YEAR_TYPE_KEY = "kuali.atp.type.FreshmanYear";
    public static final String ATP_FRESHMAN_YEAR_TERM_1_TYPE_KEY = "kuali.atp.type.FreshmanYearTerm1";
    public static final String ATP_FRESHMAN_YEAR_TERM_2_TYPE_KEY = "kuali.atp.type.FreshmanYearTerm2";
    public static final String ATP_SOPHOMORE_YEAR_TYPE_KEY = "kuali.atp.type.SophomoreYear";
    public static final String ATP_SOPHOMORE_YEAR_TERM_1_TYPE_KEY = "kuali.atp.type.SophomoreYearTerm1";
    public static final String ATP_SOPHOMORE_YEAR_TERM_2_TYPE_KEY = "kuali.atp.type.SophomoreYearTerm2";
    public static final String ATP_JUNIOR_YEAR_TYPE_KEY = "kuali.atp.type.JuniorYear";
    public static final String ATP_JUNIOR_YEAR_TERM_1_TYPE_KEY = "kuali.atp.type.JuniorYearTerm1";
    public static final String ATP_JUNIOR_YEAR_TERM_2_TYPE_KEY = "kuali.atp.type.JuniorYearTerm2";
    public static final String ATP_SENIOR_YEAR_TYPE_KEY = "kuali.atp.type.SeniorYear";
    public static final String ATP_SENIOR_YEAR_TERM_1_TYPE_KEY = "kuali.atp.type.SeniorYearTerm1";
    public static final String ATP_SENIOR_YEAR_TERM_2_TYPE_KEY = "kuali.atp.type.SeniorYearTerm2";
    // course offering options
    public static final String ATP_EVEN_YEARS_TYPE_KEY = "kuali.atp.type.EvenYears";
    public static final String ATP_FALL_EVEN_YEARS_TYPE_KEY = "kuali.atp.type.FallEvenYears";
    public static final String ATP_SPRING_EVEN_YEARS_TYPE_KEY = "kuali.atp.type.SpringEvenYears";
    public static final String ATP_ODD_YEARS_TYPE_KEY = "kuali.atp.type.OddYears";
    public static final String ATP_FALL_ODD_YEARS_TYPE_KEY = "kuali.atp.type.FallOddYears";
    public static final String ATP_SPRING_ODD_YEARS_TYPE_KEY = "kuali.atp.type.SpringOddYears";
    /**
     * Milstone types
     */
    // registration
    public static final String MILESTONE_ADVANCE_REGISTRATION_PERIOD_TYPE_KEY = "kuali.atp.milestone.AdvanceRegistrationPeriod";
    public static final String MILESTONE_REGISTRATION_PERIOD_TYPE_KEY = "kuali.atp.milestone.RegistrationPeriod";
    public static final String MILESTONE_REGISTRATION_BEGINS_FOR_MBA_TYPE_KEY = "kuali.atp.milestone.RegistrationBeginsforMBA";
    public static final String MILESTONE_REGISTRATION_BEGINS_NON_DEGREE_TYPE_KEY = "kuali.atp.milestone.RegistrationBeginsNonDegree";
    public static final String MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY = "kuali.atp.milestone.RegistrationBeginsTransfer";
    public static final String MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY = "kuali.atp.milestone.InstructionalPeriod";
    public static final String MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY = "kuali.atp.milestone.CourseSelectionPeriodEnd";
    public static final String MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY = "kuali.atp.milestone.DropDeadlineWithoutRecord";
    public static final String MILESTONE_DROP_DATE_TYPE_KEY = "kuali.atp.milestone.DropDate";
    public static final String MILESTONE_POST_GRADES_MIDTERM_TYPE_KEY = "kuali.atp.milestone.PostGradesMidterm";
    public static final String MILESTONE_MAIL_PROGRESS_REPORTS_TYPE_KEY = "kuali.atp.milestone.MailProgressReports";
    public static final String MILESTONE_READING_PERIOD_TYPE_KEY = "kuali.atp.milestone.ReadingPeriod";
    public static final String MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY = "kuali.atp.milestone.FinalExamPeriod";
    public static final String MILESTONE_GRADES_DUE_TYPE_KEY = "kuali.atp.milestone.GradesDue";
    public static final String MILESTONE_POST_GRADES_TYPE_KEY = "kuali.atp.milestone.PostGrades";
    // holidays
    public static final String MILESTONE_LABOR_DAY_TYPE_KEY = "kuali.atp.milestone.LaborDay";
    public static final String MILESTONE_FALL_BREAK_TYPE_KEY = "kuali.atp.milestone.FallBreak";
    public static final String MILESTONE_THANKSGIVING_BREAK_TYPE_KEY = "kuali.atp.milestone.ThanksgivingBreak";
    public static final String MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY = "kuali.atp.milestone.MLKDayObserved";
    public static final String MILESTONE_SPRING_BREAK_TYPE_KEY = "kuali.atp.milestone.SpringBreak";
    public static final String MILESTONE_MEMORIAL_DAY_OBSERVED_TYPE_KEY = "kuali.atp.milestone.MemorialDayObserved";
    public static final String MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY = "kuali.atp.milestone.IndependenceDayObserved";
    // events
    public static final String MILESTONE_HOMECOMING_TYPE_KEY = "kuali.atp.milestone.Homecoming";
    public static final String MILESTONE_FAMILY_WEEKEND_TYPE_KEY = "kuali.atp.milestone.FamilyWeekend";
    // orientation
    public static final String MILESTONE_MOVE_IN_DATE_TYPE_KEY = "kuali.atp.milestone.Move-inDate";
    public static final String MILESTONE_NEW_STUDENT_CONVOCATION_TYPE_KEY = "kuali.atp.milestone.NewStudentConvocation";
    public static final String MILESTONE_NEW_STUDENT_ORIENTATION_TYPE_KEY = "kuali.atp.milestone.NewStudentOrientation";
    // admissions
    public static final String MILESTONE_ADMISSIONS_APPLICATION_DUE_EARLY_CYCLE_TYPE_KEY = "kuali.atp.milestone.AdmissionsApplicationDueEarlyCycle";
    // billing
    public static final String MILESTONE_GENERATE_BILLS_TYPE_KEY = "kuali.atp.milestone.GenerateBills";
    public static final String MILESTONE_DISBURSE_FUNDS_TYPE_KEY = "kuali.atp.milestone.DisburseFunds";
    public static final String MILESTONE_PAYMENT_DUE_TYPE_KEY = "kuali.atp.milestone.PaymentDue";
    public static final String MILESTONE_PROCESS_REFUNDS_TYPE_KEY = "kuali.atp.milestone.ProcessRefunds";
    // fin aid
    public static final String MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY = "kuali.atp.milestone.FinancialAidCensus";
    public static final String MILESTONE_BEGIN_PACKAGING_TYPE_KEY = "kuali.atp.milestone.BeginPackaging";
    // curriculumn
    public static final String MILESTONE_COORDINATORS_KICKOFF_MEETING_TYPE_KEY = "kuali.atp.milestone.CoordinatorsKickoffMeeting";
    public static final String MILESTONE_CURRICULUM_COMMITTEE_MEETING_TYPE_KEY = "kuali.atp.milestone.CurriculumCommitteeMeeting";
    public static final String MILESTONE_MAJOR_CHANGES_DEADLINE_TYPE_KEY = "kuali.atp.milestone.MajorChangesDeadline";
    public static final String MILESTONE_MINOR_CHANGES_DEADLINE_TYPE_KEY = "kuali.atp.milestone.MinorChangesDeadline";
    public static final String MILESTONE_PROPOSAL_PERIOD_TYPE_KEY = "kuali.atp.milestone.ProposalPeriod";
    public static final String MILESTONE_REVIEW_PERIOD_TYPE_KEY = "kuali.atp.milestone.ReviewPeriod";
    public static final String MILESTONE_LAST_MINUTE_PROPOSALS_DEADLINE_TYPE_KEY = "kuali.atp.milestone.LastMinuteProposalsDeadline";
    public static final String MILESTONE_PUBLISH_CHANGES_ON_LINE_TYPE_KEY = "kuali.atp.milestone.PublishChangesOnLine";
    // graduation
    public static final String MILESTONE_GRADUATION_APPLICATION_DEADLINE_TYPE_KEY = "kuali.atp.milestone.GraduationApplicationDeadline";
    public static final String MILESTONE_ALUMNI_DAY_TYPE_KEY = "kuali.atp.milestone.AlumniDay";
    public static final String MILESTONE_BACCALAUREATE_TYPE_KEY = "kuali.atp.milestone.Baccalaureate";
    public static final String MILESTONE_COMMENCEMENT_TYPE_KEY = "kuali.atp.milestone.Commencement";
    public static final String MILESTONE_LEAVE_OF_ABSENSE_BEGIN_TYPE_KEY = "kuali.atp.milestone.LeaveofAbsenseBegin";
    // Room scheduling
    public static final String MILESTONE_ROOM_SCHEDULING_BEGIN_TYPE_KEY = "kuali.atp.milestone.RoomSchedulingBegin";
    // tuition calc
    public static final String MILESTONE_REFUND_100_TYPE_KEY = "kuali.atp.milestone.Refund100";
    public static final String MILESTONE_REFUND_80_TYPE_KEY = "kuali.atp.milestone.Refund80";
    public static final String MILESTONE_REFUND_60_TYPE_KEY = "kuali.atp.milestone.Refund60";
    public static final String MILESTONE_REFUND_50_TYPE_KEY = "kuali.atp.milestone.Refund50";
    public static final String MILESTONE_REFUND_40_TYPE_KEY = "kuali.atp.milestone.Refund40";
    public static final String MILESTONE_REFUND_20_TYPE_KEY = "kuali.atp.milestone.Refund20";
    /**
     * Duration Types
     */
    public static final String DURATION_FOUR_YEARS_TYPE_KEY = "kuali.atp.duration.FourYears";
    public static final String DURATION_TWO_YEARS_TYPE_KEY = "kuali.atp.duration.TwoYears";
    public static final String DURATION_YEAR_TYPE_KEY = "kuali.atp.duration.Year";
    public static final String DURATION_MONTH_TYPE_KEY = "kuali.atp.duration.Month";
    public static final String DURATION_WEEK_TYPE_KEY = "kuali.atp.duration.Week";
    public static final String DURATION_SEMESTER_TYPE_KEY = "kuali.atp.duration.Semester";
    public static final String DURATION_TERM_TYPE_KEY = "kuali.atp.duration.Term";
    public static final String DURATION_HALF_SEMESTER_TYPE_KEY = "kuali.atp.duration.HalfSemester";
    public static final String DURATION_SESSION_TYPE_KEY = "kuali.atp.duration.Session";
    public static final String DURATION_PERIOD_TYPE_KEY = "kuali.atp.duration.Period";
    public static final String DURATION_MINI_MESTER_TYPE_KEY = "kuali.atp.duration.Mini-mester";
    /**
     * Season Types
     */
    public static final String SEASON_FALL_TYPE_KEY = "kuali.atp.season.Fall";
    public static final String SEASON_FALL_1_TYPE_KEY = "kuali.atp.season.Fall1";
    public static final String SEASON_FALL_2_TYPE_KEY = "kuali.atp.season.Fall2";
    public static final String SEASON_FALL_SPRING_TYPE_KEY = "kuali.atp.season.Fall-Spring";
    public static final String SEASON_SPRING_TYPE_KEY = "kuali.atp.season.Spring";
    public static final String SEASON_SPRING_1_TYPE_KEY = "kuali.atp.season.Spring1";
    public static final String SEASON_SPRING_2_TYPE_KEY = "kuali.atp.season.Spring2";
    public static final String SEASON_SPRING_BREAK_TYPE_KEY = "kuali.atp.season.SpringBreak";
    public static final String SEASON_SUMMER_TYPE_KEY = "kuali.atp.season.Summer";
    public static final String SEASON_SUMMER_1_TYPE_KEY = "kuali.atp.season.Summer1";
    public static final String SEASON_SUMMER_1_A_TYPE_KEY = "kuali.atp.season.Summer1A";
    public static final String SEASON_SUMMER_1_B_TYPE_KEY = "kuali.atp.season.Summer1B";
    public static final String SEASON_SUMMER_2_TYPE_KEY = "kuali.atp.season.Summer2";
    public static final String SEASON_SUMMER_2_C_TYPE_KEY = "kuali.atp.season.Summer2C";
    public static final String SEASON_SUMMER_2_D_TYPE_KEY = "kuali.atp.season.Summer2D";
    public static final String SEASON_WINTER_TYPE_KEY = "kuali.atp.season.Winter";
    public static final String SEASON_AY_TYPE_KEY = "kuali.atp.season.AY";
    public static final String SEASON_FY_TYPE_KEY = "kuali.atp.season.FY";
    public static final String SEASON_FOUR_YEAR_CYCLE_TYPE_KEY = "kuali.atp.season.FourYearCycle";
    public static final String SEASON_TERM_1_TYPE_KEY = "kuali.atp.season.Term1";
    public static final String SEASON_TERM_2_TYPE_KEY = "kuali.atp.season.Term2";
    public static final String SEASON_YEAR_1_TYPE_KEY = "kuali.atp.season.Year1";
    public static final String SEASON_YEAR_2_TYPE_KEY = "kuali.atp.season.Year2";
    public static final String SEASON_YEAR_3_TYPE_KEY = "kuali.atp.season.Year3";
    public static final String SEASON_YEAR_4_TYPE_KEY = "kuali.atp.season.Year4";
    public static final String SEASON_ALTERNATE_YEARS_CYCLE_TYPE_KEY = "kuali.atp.season.AlternateYearsCycle";
    public static final String SEASON_EVEN_YEARS_TYPE_KEY = "kuali.atp.season.EvenYears";
    public static final String SEASON_ODD_YEARS_TYPE_KEY = "kuali.atp.season.OddYears";
    /**
     * Milestone States
     */
    public static final String MILESTONE_OFFICIAL_STATE_KEY = "kuali.milestone.state.Official";
    public static final String MILESTONE_DRAFT_STATE_KEY = "kuali.milestone.state.Draft";
    /**
     * ATP States
     */
    public static final String ATP_OFFICIAL_STATE_KEY = "kuali.atp.state.Official";
    public static final String ATP_DRAFT_STATE_KEY = "kuali.atp.state.Draft";
}
