package org.kuali.student.ap.schedulebuilder.util;

import org.kuali.student.ap.schedulebuilder.dto.ActivityOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.CourseOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.PossibleScheduleOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.SecondaryActivityOptionsInfo;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.ClassMeetingTime;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.ap.schedulebuilder.infc.ScheduleBuildEvent;
import org.kuali.student.ap.schedulebuilder.infc.SecondaryActivityOptions;
import org.kuali.student.r2.core.acal.infc.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class ScheduleBuilder implements Serializable {

	private static final long serialVersionUID = 4792345964542902431L;

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleBuilder.class);

	private static long[][][] BLOCK_CACHE = new long[288][288][];

	private static long[] block(int fromSlot, int toSlot) {
		long[] rv = BLOCK_CACHE[fromSlot][toSlot];
		if (rv != null)
			return rv;
		rv = new long[5];
		for (int i = fromSlot; i <= toSlot; i++)
			rv[i / 64] |= 1L << i % 64;
		return BLOCK_CACHE[fromSlot][toSlot] = rv;
	}

	private static boolean intersects(long[] day, int fromSlot, int toSlot) {
		boolean rv = false;
		long[] block = block(fromSlot, toSlot);
		for (int i = 0; !rv && i < 5; i++)
			rv |= 0L != (day[i] & block[i]);
		return rv;
	}

	private static void union(long[] day, int fromSlot, int toSlot) {
		long[] block = block(fromSlot, toSlot);
		for (int i = 0; i < 5; i++)
			day[i] |= block[i];
	}

	private static boolean isEpoch(Calendar c) {
		return c.get(Calendar.YEAR) == 1970
				&& c.get(Calendar.MONDAY) == Calendar.JANUARY
				&& c.get(Calendar.DATE) == 1;
	}

	private static boolean checkForConflicts(ScheduleBuildEvent event,
			Date[] sundays, long[][][] days, Calendar c) {
		if (event.isAllDay())
			return true;

		for (int i = 0; i < sundays.length; i++) {

			c.setTime(event.getStartDate());
			int fromSlot = c.get(Calendar.HOUR_OF_DAY) * 12
					+ (c.get(Calendar.MINUTE) / 5);
			if (!isEpoch(c)) {
				c.add(Calendar.DATE, -7);
				if (!c.getTime().before(sundays[i]))
					continue;
			}

			c.setTime(event.getUntilDate());
			int toSlot = c.get(Calendar.HOUR_OF_DAY) * 12
					+ (c.get(Calendar.MINUTE) / 5);
			if (!isEpoch(c) && !c.getTime().after(sundays[i]))
				continue;

			if (event.isSunday()) {
				if (intersects(days[i][0], fromSlot, toSlot))
					return false;
				else
					union(days[i][0], fromSlot, toSlot);
			}
			if (event.isMonday()) {
				if (intersects(days[i][1], fromSlot, toSlot))
					return false;
				else
					union(days[i][1], fromSlot, toSlot);
			}
			if (event.isTuesday()) {
				if (intersects(days[i][2], fromSlot, toSlot))
					return false;
				else
					union(days[i][2], fromSlot, toSlot);
			}
			if (event.isWednesday()) {
				if (intersects(days[i][3], fromSlot, toSlot))
					return false;
				else
					union(days[i][3], fromSlot, toSlot);
			}
			if (event.isThursday()) {
				if (intersects(days[i][4], fromSlot, toSlot))
					return false;
				else
					union(days[i][4], fromSlot, toSlot);
			}
			if (event.isFriday()) {
				if (intersects(days[i][5], fromSlot, toSlot))
					return false;
				else
					union(days[i][5], fromSlot, toSlot);
			}
			if (event.isSaturday()) {
				if (intersects(days[i][6], fromSlot, toSlot))
					return false;
				else
					union(days[i][6], fromSlot, toSlot);
			}
		}
		return true;
	}

	private static boolean checkForConflicts(ActivityOption ao, Date[] sundays,
			long[][][] days, Calendar c) {
		for (ClassMeetingTime meetingTime : ao.getClassMeetingTimes())
			if (!checkForConflicts(meetingTime, sundays, days, c))
				return false;
		return true;
	}

	private static Date[] getSundays(Term term, Calendar c) {
		List<Date> sundayList = new ArrayList<Date>(16);
		c.setTime(term.getStartDate());
		c.add(Calendar.DATE, -(c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY));
		while (c.getTime().before(term.getEndDate())) {
			sundayList.add(c.getTime());
			c.add(Calendar.DATE, 7);
		}
		return sundayList.toArray(new Date[sundayList.size()]);
	}

	private final Term term;
	private final List<CourseOption> courseOptions;
	private final List<ReservedTime> reservedTimes;
	private final Set<Long> primaryConflicts = new HashSet<Long>();
	private final boolean empty;

	private int currentCourseIndex;
	private final int[] currentPrimaryActivityIndex;
	private final int[][] currentSecondaryOptionIndex;
	private final int[][][] currentSecondaryActivityIndex;
	private final int[][][] limitSecondaryOption;

	private int scheduleNumber;
	private boolean hasMore;
	private Set<PossibleScheduleOption> generated;

	@Override
	public String toString() {
        StringBuilder sb=new StringBuilder("ScheduleBuilder\n  currentCourse ");
        sb.append(currentCourseIndex).append("\n  currentPrimary ");
        sb.append(Arrays.toString(currentPrimaryActivityIndex)).append("\n  currentSecondary ");
		for (int[] cs : currentSecondaryOptionIndex)  {
			sb.append("\n      ").append(Arrays.toString(cs));
        }
		sb.append("\n  currentSecondaryOption\n    ");
		for (int[][] cso : currentSecondaryActivityIndex) {
			for (int[] cs : cso){
				sb.append(",").append(Arrays.toString(cs));
            }
        }
		sb.append("\n  limitSecondaryOption\n    ");
		for (int[][] cso : limitSecondaryOption){
			for (int[] cs : cso){
				sb.append(",").append(Arrays.toString(cs));
            }
        }
		return sb.toString();
	}

	public ScheduleBuilder(Term term, List<CourseOption> courseOptions,
                           List<ReservedTime> reservedTimes) {
		this.term = term;
		this.reservedTimes = reservedTimes;

		boolean empty = false;
		List<CourseOption> co = new ArrayList<CourseOption>(
				courseOptions == null ? 0 : courseOptions.size());
		if (courseOptions != null)
			course: for (CourseOption c : courseOptions)
				if (c.isSelected()) {
					List<ActivityOption> oaol = c.getActivityOptions();
					List<ActivityOption> aol = new ArrayList<ActivityOption>();
					for (ActivityOption ao : oaol)
						if (ao.isSelected() || ao.isLockedIn()) {
							List<SecondaryActivityOptions> osol = ao
									.getSecondaryOptions();
							List<SecondaryActivityOptions> sol = new ArrayList<SecondaryActivityOptions>(
									osol.size());
							for (SecondaryActivityOptions so : osol) {
								List<ActivityOption> osaol = so
										.getActivityOptions();
								List<ActivityOption> saol = new ArrayList<ActivityOption>(
										osaol.size());
								for (ActivityOption sao : osaol)
									if (sao.isSelected() || sao.isLockedIn())
										saol.add(sao);
								if (saol.isEmpty()) {
									LOG.warn("Course option is selected, but has no selected activities for {} {} {} {}",
                                            ao.getRegistrationCode(), so.getActivityTypeDescription(), c.getCourseId(), c.getCourseCode());
									empty = true;
									break course;
								}
								SecondaryActivityOptionsInfo soi = new SecondaryActivityOptionsInfo(
										so);
								soi.setActivityOptions(saol);
								sol.add(soi);
							}
							ActivityOptionInfo aoi = new ActivityOptionInfo(ao);
							aoi.setSecondaryOptions(sol);
							aol.add(aoi);
						}
					if (aol.isEmpty()) {
						LOG.warn("Course option is selected, but has no selected activities {} {}", c.getCourseId(), c.getCourseCode());
						empty = true;
						break course;
					}
					CourseOptionInfo ci = new CourseOptionInfo(c);
					ci.setActivityOptions(aol);
					co.add(ci);
				}
		this.empty = empty;
		this.courseOptions = Collections.unmodifiableList(co);

		currentPrimaryActivityIndex = new int[co.size()];
		currentSecondaryOptionIndex = new int[co.size()][];
		currentSecondaryActivityIndex = new int[co.size()][][];
		limitSecondaryOption = new int[co.size()][][];

		for (int i = 0; i < co.size(); i++) {
			List<ActivityOption> primaryActivityOptions = co.get(i)
					.getActivityOptions();
			currentSecondaryOptionIndex[i] = new int[primaryActivityOptions
					.size()];
			currentSecondaryActivityIndex[i] = new int[primaryActivityOptions
					.size()][];
			limitSecondaryOption[i] = new int[primaryActivityOptions.size()][];
			for (int j = 0; j < primaryActivityOptions.size(); j++) {
				List<SecondaryActivityOptions> secondaryActivityOptions = primaryActivityOptions
						.get(j).getSecondaryOptions();
				currentSecondaryActivityIndex[i][j] = new int[secondaryActivityOptions
						.size()];
				limitSecondaryOption[i][j] = new int[secondaryActivityOptions
						.size()];
				for (int k = 0; k < secondaryActivityOptions.size(); k++) {
					SecondaryActivityOptions secondaryActivityOption = secondaryActivityOptions
							.get(k);
					if (secondaryActivityOption.isEnrollmentGroup()) {
						// enrollment groups must include all secondary options
						limitSecondaryOption[i][j][k] = 0;
					} else {
						// for non-enrollment secondary options, select one at a
						// time
						limitSecondaryOption[i][j][k] = secondaryActivityOptions
								.get(k).getActivityOptions().size();
					}
				}
			}
		}
	}

	/**
	 * Queue course indexes for the current run, considering locked-in courses
	 * first followed by selected course.
	 *
	 * @param courseQueue
	 */
	private void prepareCourseQueue(Queue<Integer> courseQueue) {
		courseQueue.clear();
		int courseOptionsLength = courseOptions.size();
		for (int i = 0; i < courseOptionsLength; i++) {
			int courseIndex = currentCourseIndex + i;
			if (courseIndex >= courseOptionsLength)
				courseIndex -= courseOptionsLength;
			CourseOption courseOption = courseOptions.get(courseIndex);

			if (courseOption.isLockedIn())
				courseQueue.offer(courseIndex);
		}
		for (int i = 0; i < courseOptionsLength; i++) {
			int courseIndex = currentCourseIndex + i;
			if (courseIndex >= courseOptionsLength)
				courseIndex -= courseOptionsLength;
			CourseOption courseOption = courseOptions.get(courseIndex);

			if (!courseOption.isLockedIn() && courseOption.isSelected())
				courseQueue.offer(courseIndex);
		}
	}

	/**
	 * Step forward to the next course and activity option combination.
	 *
	 * @return True if this step has rolled the options over to the initial
	 *         state, false if the initial state has not reached.
	 */
	private boolean step(Queue<Integer> courseQueue) {
		if (currentPrimaryActivityIndex.length < 1)
			return true;

		boolean conflict;

		do {
			increment: {
				// Operate on the next course option, stepping backward
				int lastCourseIndex = courseOptions.size() - 1;
				currentCourseIndex = currentCourseIndex == 0 ? lastCourseIndex
						: currentCourseIndex - 1;

				int primaryActivityIndex = currentPrimaryActivityIndex[currentCourseIndex];
				int[] secondaryActivityIndex = currentSecondaryActivityIndex[currentCourseIndex][primaryActivityIndex];
				if (secondaryActivityIndex.length > 0) {
					// increment current secondary option activity index,
					// stepping
					// forward.
					int secondaryOptionIndex = currentSecondaryOptionIndex[currentCourseIndex][primaryActivityIndex];
					int secondaryOptionActivityIndex = secondaryActivityIndex[secondaryOptionIndex] + 1;
					if (secondaryOptionActivityIndex >= limitSecondaryOption[currentCourseIndex][primaryActivityIndex][secondaryOptionIndex])
						secondaryOptionActivityIndex = 0;
					secondaryActivityIndex[secondaryOptionIndex] = secondaryOptionActivityIndex;

					// increment current secondary option index, stepping
					// forward.
					secondaryOptionIndex += 1;
					if (secondaryOptionIndex >= secondaryActivityIndex.length)
						secondaryOptionIndex = 0;
					currentSecondaryOptionIndex[currentCourseIndex][primaryActivityIndex] = secondaryOptionIndex;

					// Not the last secondary option for the current primary,
					// return
					// without rollover
					if (secondaryOptionActivityIndex > 0
							|| secondaryOptionIndex > 0)
						break increment;
				}

				// increment current primary activity index, stepping forward.
				primaryActivityIndex += 1;
				if (primaryActivityIndex >= currentSecondaryActivityIndex[currentCourseIndex].length)
					primaryActivityIndex = 0;
				currentPrimaryActivityIndex[currentCourseIndex] = primaryActivityIndex;

				// Determine whether or not rollover has taken place, and return
				if (currentCourseIndex == 0) {
					assert currentPrimaryActivityIndex.length == currentSecondaryActivityIndex.length;
					for (int i = 0; i < currentSecondaryActivityIndex.length; i++) {
						if (currentPrimaryActivityIndex[i] > 0)
							break increment;
						for (int j = 0; j < currentSecondaryActivityIndex[i].length; j++)
							for (int k = 0; k < currentSecondaryActivityIndex[i][j].length; k++)
								if (currentSecondaryActivityIndex[i][j][k] > 0)
									break increment;
					}
					primaryConflicts.clear();
					return true;
				}
			}

			conflict = false;
			long hash = (long) currentCourseIndex;
			prepareCourseQueue(courseQueue);
			conflict: while (!conflict && !courseQueue.isEmpty()) {

				int courseIndex = courseQueue.poll();
				int activityOptionIndex = currentPrimaryActivityIndex[courseIndex];
				hash = hash * 127L + (long) activityOptionIndex;
				if (conflict = primaryConflicts.contains(hash))
					break conflict;

				int[] secondaryActivityIndex = currentSecondaryActivityIndex[courseIndex][activityOptionIndex];
				long shash = hash;
				for (int j = 0; j < secondaryActivityIndex.length; j++) {
					int sidx = secondaryActivityIndex[j];
					shash = shash * 31L + (long) sidx;
					if (conflict = primaryConflicts.contains(shash))
						break conflict;
				}
			}

		} while (conflict);

		return false;
	}

	public List<PossibleScheduleOption> getNext(int count,
			Set<PossibleScheduleOption> current) {
		if (empty) {
			if (LOG.isDebugEnabled())
				LOG.debug("Schedule builder is marked as empty, at least one course "
						+ "option has not associated activity options");
			return Collections.emptyList();
		}

		boolean rolled = false;
		boolean done = false;
		Calendar tempCalendar = Calendar.getInstance();
		Date[] sundays = getSundays(term, tempCalendar);
		long[][][] days = new long[sundays.length][7][5];
		int iterationCount = 0;
		int iterationsSinceLast = 0;
		if (generated == null) {
			assert !hasMore;
			generated = new HashSet<PossibleScheduleOption>();
		}

		Queue<Integer> courseQueue = new LinkedList<Integer>();
		List<PossibleScheduleOption> rv = new ArrayList<PossibleScheduleOption>(count);
		List<ActivityOption> possibleActivityOptions = new LinkedList<ActivityOption>();

		StringBuilder msg = null;
		if (LOG.isDebugEnabled()) {
			msg = new StringBuilder("Schedule build run\nCourses:");
			for (CourseOption co : courseOptions) {
				msg.append("\n  ").append(co.getCourseCode());
				if (co.isSelected())
					msg.append(" selected");
				if (co.isLockedIn())
					msg.append(" locked-in");
				for (ActivityOption pao : co.getActivityOptions())
					if (pao.isSelected() || pao.isLockedIn()) {
						msg.append("\n    Primary ").append(
								pao.getRegistrationCode());
						if (pao.isSelected())
							msg.append(" selected");
						if (pao.isLockedIn())
							msg.append(" locked-in");
						for (SecondaryActivityOptions so : pao
								.getSecondaryOptions()) {
							for (ActivityOption sao : so.getActivityOptions()) {
								if (sao.isSelected() || sao.isLockedIn()) {
									msg.append("\n    ")
											.append(so
													.getActivityTypeDescription())
											.append(" ")
											.append(pao.getRegistrationCode());
									if (sao.isSelected())
										msg.append(" selected");
									if (sao.isLockedIn())
										msg.append(" locked-in");
								}
							}
						}
					}
			}
		}

		do {
			possibleActivityOptions.clear();
			for (int i = 0; i < sundays.length; i++)
				for (int j = 0; j < 7; j++)
					for (int k = 0; k < 5; k++)
						days[i][j][k] = 0L;
			for (ReservedTime reservedTime : reservedTimes)
				if (reservedTime.isSelected())
					checkForConflicts(reservedTime, sundays, days, tempCalendar);

			boolean allCourses = true;
			if (msg != null)
				msg.append("\nIteration ").append(++iterationCount);
			iterationsSinceLast++;

			long hash = (long) currentCourseIndex;
			prepareCourseQueue(courseQueue);
			course: while (!courseQueue.isEmpty()) {
				int courseIndex = courseQueue.poll();
				CourseOption courseOption = courseOptions.get(courseIndex);
				if (msg != null)
					msg.append(" co ").append(courseOption.getCourseCode());

				int activityOptionIndex = currentPrimaryActivityIndex[courseIndex];
				hash = hash * 127L + (long) activityOptionIndex;

				ActivityOption primary = courseOption.getActivityOptions().get(
						activityOptionIndex);
				if (msg != null)
					msg.append(" pri ").append(primary.getRegistrationCode());

				if (!checkForConflicts(primary, sundays, days, tempCalendar)
						&& !courseOption.isLockedIn()) {
					if (msg != null)
						msg.append(" conflict");
					primaryConflicts.add(hash);
					allCourses = false;
					break course;
				}

				int[] secondaryActivityIndex = currentSecondaryActivityIndex[courseIndex][activityOptionIndex];
				if (primary.isEnrollmentGroup()) {
					if (msg != null)
						msg.append(" block");
					for (int j = 0; j < secondaryActivityIndex.length; j++) {
						List<ActivityOption> secondaryActivityOptions = primary
								.getSecondaryOptions().get(j)
								.getActivityOptions();
						if (msg != null)
							msg.append(" sec ").append(
									secondaryActivityOptions.size());
						for (ActivityOption secondary : secondaryActivityOptions) {
							if (msg != null)
								msg.append(" ").append(
										secondary.getRegistrationCode());
							if (!checkForConflicts(secondary, sundays, days,
									tempCalendar)) {
								if (msg != null)
									msg.append(" conflict");
								allCourses = false;
								break course;
							}
						}
					}
				} else {
					long shash = hash;
					for (int j = 0; j < secondaryActivityIndex.length; j++) {
						int sidx = secondaryActivityIndex[j];
						ActivityOption secondary = primary
								.getSecondaryOptions().get(j)
								.getActivityOptions().get(sidx);
						shash = shash * 31L + (long) sidx;
						if (msg != null)
							msg.append(" sec ").append(
									secondary.getRegistrationCode());
						if ((!secondary.isSelected() && !secondary.isLockedIn())
								|| !checkForConflicts(secondary, sundays, days,
										tempCalendar)) {
							primaryConflicts.add(shash);
							if (msg != null)
								msg.append(" conflict");
							allCourses = false;
							break course;
						}
					}
				}

				possibleActivityOptions.add(primary);
				for (int j = 0; j < secondaryActivityIndex.length; j++) {
					SecondaryActivityOptions secondaryActivityOptions = primary
							.getSecondaryOptions().get(j);
					if (secondaryActivityOptions.isEnrollmentGroup()) {
						for (ActivityOption secondary : secondaryActivityOptions
								.getActivityOptions())
							possibleActivityOptions.add(secondary);
					} else
						possibleActivityOptions.add(secondaryActivityOptions
								.getActivityOptions().get(
										secondaryActivityIndex[j]));
				}
			}

			if (allCourses) {
				PossibleScheduleOptionInfo pso = new PossibleScheduleOptionInfo();
				pso.setUniqueId(UUID.randomUUID().toString());
				Collections.sort(possibleActivityOptions,
						new Comparator<ActivityOption>() {
							@Override
							public int compare(ActivityOption o1,
									ActivityOption o2) {
								String cc1 = o1.getCourseOfferingCode();
								String cc2 = o2.getCourseOfferingCode();
								if (cc1 == null && cc2 != null)
									return 1;
								if (cc1 != null && cc2 == null)
									return -1;
								if (cc1 != null && cc2 != null) {
									int ccc = cc1.compareTo(cc2);
									if (ccc != 0)
										return ccc;
								}
								return o1.compareTo(o2);
							}
						});
				pso.setActivityOptions(possibleActivityOptions);
				if (!generated.add(pso)) {
					if (msg != null)
						msg.append(" dup-rolled");
				} else if (current.contains(pso)) {
					scheduleNumber++;
					if (msg != null)
						msg.append(" dup-current");
				} else {
					iterationsSinceLast = 0;
					String descr = "Schedule " + (++scheduleNumber);
					pso.setTermId(term.getId());
					pso.setDescription(descr);
					rv.add(pso);
					if (msg != null) {
						msg.append("\nPossible option #").append(rv.size());
						msg.append(" ").append(descr).append(" ");
						for (ActivityOption ao : pso.getActivityOptions())
							msg.append(" ").append(ao.getCourseOfferingCode())
									.append(" ")
									.append(ao.getRegistrationCode());
					}
				}
			}

			boolean justRolled = step(courseQueue);
			if (justRolled) {
				scheduleNumber = 0;
				rolled = true;
				hasMore = generated.size() > count;
				if (msg != null)
					msg.append("\nJust rolled. Count: ").append(count)
							.append(" Current: ").append(current.size())
							.append(" Generated: ").append(generated.size())
							.append(" More? ").append(hasMore);
				generated = new HashSet<PossibleScheduleOption>();
			}
			done = rv.size() >= count || (justRolled && !hasMore);
			if (iterationsSinceLast > 50000) {
				done = true;
				if (msg != null)
					msg.append(" !loop");
			}
			if (msg != null) {
				if (justRolled)
					msg.append(" just");
				if (rolled)
					msg.append(" rolled");
				if (done)
					msg.append(" done");
			}
		} while (!done);
		if (msg != null) {
			LOG.debug(msg.toString());
		}
		hasMore = hasMore || (rv.size() >= count && !rolled);
		return rv;
	}

	public boolean hasMore() {
		return hasMore;
	}

	public List<CourseOption> getCourseOptions() {
		return courseOptions;
	}

}
