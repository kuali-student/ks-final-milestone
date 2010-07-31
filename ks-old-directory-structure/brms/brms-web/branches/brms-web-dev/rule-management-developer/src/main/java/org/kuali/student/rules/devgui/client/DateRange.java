package org.kuali.student.rules.devgui.client;

import java.util.ArrayList;
import java.util.Arrays;

public class DateRange implements java.lang.Comparable, java.io.Serializable {
    private java.util.Date startDate;
    private java.util.Date endDate;

    /**
     * constructor
     * @param startDate
     * @param endDate
     */
    public DateRange(java.util.Date startDate, java.util.Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * default constructor.  startDate and endDate fields will be initialized
     * to nulls.  Note: Methods in this class do not allow null dates.  So
     * ensure setStartDate and setEndDate methods are called prior to usage.
     */
    public DateRange() {
    }

    /**
     * get the value of the startDate field
     * @return
     */
    public java.util.Date getStartDate() {
        return startDate;
    }

    /**
     * sets the value of the startDate field
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    /**
     * gets the value of the endDate field
     * @return
     */
    public java.util.Date getEndDate() {
        return endDate;
    }

    /**
     * sets the value of the endDate field
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * exludes the argrument dateRanges from this date range.  alResult will be
     * populated with the ranges with dateRanges excluded.  alResult should be
     * an empty ArrayList of DateRange but cannot be null
     * @param dateRanges
     * @param alResult
     */
    public void excludeRanges(DateRange[] dateRanges, ArrayList alResult) {
        int numRanges = (dateRanges == null)? 0 : dateRanges.length;
        DateRange runningRange = new DateRange(startDate, endDate);
        if (numRanges > 0) {
            Arrays.sort(dateRanges);
        }
        for (int i = 0; i < numRanges; i++) {
            DateRange[] tempResultRange;
            int numResultRanges = 0;
            if (runningRange != null) {
                tempResultRange = runningRange.excludeRange(dateRanges[i]);
                numResultRanges = (tempResultRange == null) ?
                        0 : tempResultRange.length;
                if (numResultRanges == 2 && i < numRanges - 1) {
                    alResult.add(tempResultRange[0]);
                    runningRange = tempResultRange[1];
                } else if (numResultRanges == 2 && i == numRanges - 1) {
                    alResult.add(tempResultRange[0]);
                    alResult.add(tempResultRange[1]);
                    runningRange = null;
                } else if (numResultRanges == 1 && i < numRanges - 1) {
                    runningRange = tempResultRange[0];
                } else if (numResultRanges == 1 && i == numRanges - 1) {
                    alResult.add(tempResultRange[0]);
                    runningRange = null;
                } else if (numResultRanges == 0) {
                    runningRange = null;
                }
            }
        }
        if (numRanges == 0) {
            alResult.add(this);
        }
    }

    /**
     * excludes the parameter dateRange2 from this dateRange and returns an
     * array of date ranges with dateRange2 excluded.
     * @param dateRange2
     * @return
     */
    public DateRange[] excludeRange(DateRange dateRange2) {
        ArrayList alResultRanges = new ArrayList(7);
        DateRange[] resultRanges = null;
        DateRange overlappingRange = null;
        java.util.Date startDate2 = null;
        java.util.Date endDate2 = null;

        if (dateRange2 == null) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }
        try {
            validateDateRange();
            dateRange2.validateDateRange();
        } catch (Exception e) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }
        if (startDate == null ||
                endDate == null ||
                startDate.compareTo(endDate) > 0 ||
                dateRange2 == null ||
                dateRange2.getStartDate() == null ||
                dateRange2.getEndDate() == null ||
                dateRange2.getStartDate().compareTo(dateRange2.getEndDate()) > 0) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }

        startDate2 = dateRange2.getStartDate();
        endDate2 = dateRange2.getEndDate();
        if (startDate.compareTo(endDate) == 0 &&
                startDate2.compareTo(endDate2) < 0) {
            if (startDate.compareTo(startDate2) >= 0 &&
                    startDate.compareTo(endDate2) <= 0) {
                // cases
                // 1:      |     2:      |  3:  |
                //     |______|     |____|      |______|
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 1");
            } else {
                overlappingRange = null;
                alResultRanges.add(this);
            }
        } else if (startDate2.compareTo(endDate2) == 0 &&
                startDate.compareTo(endDate) < 0) {
            if (startDate2.compareTo(startDate) >= 0 &&
                    startDate2.compareTo(endDate) <= 0) {
                // cases
                // 1:    |______|  2: |____|   3: |______|
                //           |             |      |
                overlappingRange = new DateRange(startDate2, endDate2);
                if (startDate2.compareTo(startDate) > 0 &&
                        startDate2.compareTo(endDate) < 0) {
                    alResultRanges.add(new DateRange(startDate,
                            getRelativeDay(overlappingRange.getStartDate(), - 1)));
                    alResultRanges.add(new DateRange(
                            getRelativeDay(overlappingRange.getStartDate(), 1),
                            endDate));
                } else if (startDate2.compareTo(startDate) > 0 &&
                        startDate2.compareTo(endDate) == 0) {
                    alResultRanges.add(new DateRange(startDate,
                            getRelativeDay(overlappingRange.getStartDate(), -1)));
                } else if (startDate2.compareTo(startDate) == 0 &&
                        startDate2.compareTo(endDate) < 0) {
                    alResultRanges.add(new DateRange(
                            getRelativeDay(overlappingRange.getStartDate(), 1),
                            endDate));
                }
//              logger.debug("case 2");
            } else {
                overlappingRange = null;
                alResultRanges.add(this);
            }
        } else if (startDate2.compareTo(endDate2) == 0 &&
                startDate.compareTo(endDate) == 0) {
            if (startDate.compareTo(startDate2) == 0) {
                // case
                //   |
                //   |
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 3");
            } else {
                overlappingRange = null;
                alResultRanges.add(this);
            }
        } else {
            if (startDate.compareTo(startDate2) > 0 &&
                    startDate.compareTo(endDate2) <= 0 &&
                    endDate.compareTo(endDate2) > 0) {
                // cases
                // 1:    |_______|  2:      |______|
                //    |____|           |____|
                overlappingRange = new DateRange(startDate, endDate2);
                alResultRanges.add(new DateRange(
                        getRelativeDay(overlappingRange.getEndDate(), 1),
                        endDate));
//              logger.debug("case 4");
            } else if (startDate.compareTo(startDate2) <= 0 &&
                    endDate.compareTo(endDate2) >= 0) {
                // cases
                // 1: |__________|    2: |______|   3: |________|
                //      |______|         |____|           |_____|
                overlappingRange = new DateRange(startDate2, endDate2);
                if (startDate.compareTo(startDate2) < 0 &&
                        endDate.compareTo(endDate2) > 0) {
                    alResultRanges.add(new DateRange(startDate,
                            getRelativeDay(overlappingRange.getStartDate(), -1)));
                    alResultRanges.add(new DateRange(
                            getRelativeDay(overlappingRange.getEndDate(), 1),
                            endDate));
                } else if (startDate.compareTo(startDate2) == 0 &&
                        endDate.compareTo(endDate2) > 0) {
                    alResultRanges.add(new DateRange(
                            getRelativeDay(overlappingRange.getEndDate(), 1),
                            endDate));
                } else if (startDate.compareTo(startDate2) < 0 &&
                        endDate.compareTo(endDate2) == 0) {
                    alResultRanges.add(new DateRange(
                            startDate,
                            getRelativeDay(overlappingRange.getStartDate(), -1)));
                }
//              logger.debug("case 5");
            } else if (startDate.compareTo(startDate2) >= 0 &&
                    endDate.compareTo(endDate2) <= 0) {
                // cases
                // 1:   |______|      2: |____|     3:    |_____|
                //    |__________|       |______|      |________|
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 6");
            } else if (startDate.compareTo(startDate2) < 0 &&
                    endDate.compareTo(startDate2) >= 0 &&
                    endDate.compareTo(endDate2) < 0) {
                // cases
                // 1: |_______|    2:      |______|
                //        |______|                |____|
                overlappingRange = new DateRange(startDate2, endDate);
                alResultRanges.add(new DateRange(startDate,
                        getRelativeDay(overlappingRange.getStartDate(), -1)));
//              logger.debug("case 7");
            } else {
                overlappingRange = null;
                alResultRanges.add(this);
            }
        }
        resultRanges = (alResultRanges.size() == 0)? null :
            new DateRange[alResultRanges.size()];
        if (resultRanges != null) {
            resultRanges = (DateRange[])alResultRanges.toArray(resultRanges);
        }
        return resultRanges;
    }

    private static java.util.Date getRelativeDay(java.util.Date date, int offset) {
        java.util.Date result = null;
        GregorianCalendar calendar = null;

        if (date == null) {
          System.out.println("date passed in is null");
          return null;
        }
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_YEAR, offset);
        result = calendar.getTime();
        return result;
    }

    private void validateDateRange() throws Exception {
        if (startDate == null ||
                endDate == null ||
                startDate.compareTo(endDate) > 0) {
            throw new Exception("invaid date range");
        }
    }

    /**
     * returns the range that is overlapped
     * @param dateRange2
     * @return
     */
    public DateRange getOverLappingDateRange(DateRange dateRange2) {
        DateRange overlappingRange = null;
        java.util.Date startDate2 = null;
        java.util.Date endDate2 = null;

        if (dateRange2 == null) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }
        try {
            validateDateRange();
            dateRange2.validateDateRange();
        } catch (Exception e) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }
        if (startDate == null ||
                endDate == null ||
                startDate.compareTo(endDate) > 0 ||
                dateRange2 == null ||
                dateRange2.getStartDate() == null ||
                dateRange2.getEndDate() == null ||
                dateRange2.getStartDate().compareTo(dateRange2.getEndDate()) > 0) {
            String dateRange2Message = (dateRange2 == null)? "dateRange2 is null" :
                dateRange2.toString();
            String errorMessage =
                "Unable to determine overlapping date ranges for:\n" +
                "dateRange1:\n" + this.toString() + "\n" +
                "dateRange2:\n" + dateRange2Message;
            throw new java.lang.RuntimeException(errorMessage);
        }

        startDate2 = dateRange2.getStartDate();
        endDate2 = dateRange2.getEndDate();
        if (startDate.compareTo(endDate) == 0 &&
                startDate2.compareTo(endDate2) < 0) {
            if (startDate.compareTo(startDate2) >= 0 &&
                    startDate.compareTo(endDate2) <= 0) {
                // cases
                // 1:      |     2:      |  3:  |
                //     |______|     |____|      |______|
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 1");
            } else {
                overlappingRange = null;
            }
        } else if (startDate2.compareTo(endDate2) == 0 &&
                startDate.compareTo(endDate) < 0) {
            if (startDate2.compareTo(startDate) >= 0 &&
                    startDate2.compareTo(endDate) <= 0) {
                // cases
                // 1:    |______|  2: |____|   3: |______|
                //           |             |      |
                overlappingRange = new DateRange(startDate2, endDate2);
//              logger.debug("case 2");
            } else {
                overlappingRange = null;
            }
        } else if (startDate2.compareTo(endDate2) == 0 &&
                startDate.compareTo(endDate) == 0) {
            if (startDate.compareTo(startDate2) == 0) {
                // case
                //   |
                //   |
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 3");
            } else {
                overlappingRange = null;
            }
        } else {
            if (startDate.compareTo(startDate2) > 0 &&
                    startDate.compareTo(endDate2) <= 0 &&
                    endDate.compareTo(endDate2) > 0) {
                // cases
                // 1:    |_______|  2:      |______|
                //    |____|           |____|
                overlappingRange = new DateRange(startDate, endDate2);
//              logger.debug("case 4");
            } else if (startDate.compareTo(startDate2) <= 0 &&
                    endDate.compareTo(endDate2) >= 0) {
                // cases
                // 1: |__________|    2: |______|   3: |________|
                //      |______|         |____|           |_____|
                overlappingRange = new DateRange(startDate2, endDate2);
//              logger.debug("case 5");
            } else if (startDate.compareTo(startDate2) >= 0 &&
                    endDate.compareTo(endDate2) <= 0) {
                // cases
                // 1:   |______|      2: |____|     3:    |_____|
                //    |__________|       |______|      |________|
                overlappingRange = new DateRange(startDate, endDate);
//              logger.debug("case 6");
            } else if (startDate.compareTo(startDate2) < 0 &&
                    endDate.compareTo(startDate2) >= 0 &&
                    endDate.compareTo(endDate2) < 0) {
                // cases
                // 1: |_______|    2:      |______|
                //        |______|                |____|
                overlappingRange = new DateRange(startDate2, endDate);
//              logger.debug("case 7");
            }
        }
        return overlappingRange;
    }

    /**
     * compares the start date of the ranges returns -1 if the start date of
     * this range is earlier than that of the argument range
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        DateRange dateRange2 = (DateRange)o;
        return startDate.compareTo(dateRange2.getStartDate());
    }

    /**
     * returns a printable string for this date range
     * @return
     */
    public String toString() {
        StringBuffer sbResult = new StringBuffer(179);
        sbResult.append("");
        sbResult.append("startDate: ").append(startDate).append("\n");
        sbResult.append("endDate: ").append(endDate).append("\n");
        return sbResult.toString();
    }

    public boolean equals(Object obj) {
        DateRange dateRange2 = (DateRange)obj;
        return (startDate.equals(dateRange2.getStartDate()) &&
                endDate.equals(dateRange2.getEndDate()));
    }

    public static void main(String[] args) {
        test();
    }

    //  private static void testDurations() {
//  Logger.setLogImpl(Logger.LOGGINGTYPE_SYSOUT);
//  Logger logger = Logger.getInstance("main");
//  DateRange testRange = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2004, 11, 31, 1, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2004, 11, 31, 23, 0, 0).getTime()
//  .getTime()
//  ));
//  for (int i = 0; i < 50; i++) {
//  logger.debug("Duration in days of: " + testRange +
//  String.valueOf(testRange.getDurationInDays()));
//  testRange.setEndDate(new java.util.Date(DateUtility.getRelativeDay(testRange.getEndDate(), 1)
//  .getTime()));
//  }
    //  }

    //  private static void testExcusions() {
//  Logger.setLogImpl(Logger.LOGGINGTYPE_SYSOUT);
//  Logger logger = Logger.getInstance("main");
//  DateRange testRange = null;
//  DateRange[] testRanges = new DateRange[4];
//  ArrayList alResultRanges = null;
    //
//  testRange = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2004, 11, 31, 0, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 14, 0, 0, 0).getTime()
//  .getTime()
//  ));
    //
//  testRanges[0] = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2004, 11, 31, 0, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 2, 0, 0, 0).getTime()
//  .getTime()
//  ));
//  testRanges[1] = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 1, 0, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 3, 0, 0, 0).getTime()
//  .getTime()
//  ));
//  testRanges[2] = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 2, 0, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 4, 0, 0, 0).getTime()
//  .getTime()
//  ));
//  testRanges[3] = new DateRange(
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 7, 0, 0, 0).getTime()
//  .getTime()),
//  new java.util.Date(
//  new java.util.GregorianCalendar(2005, 0, 10, 0, 0, 0).getTime()
//  .getTime()
//  ));
    //
//  alResultRanges = new ArrayList(7);
//  testRange.excludeRanges(testRanges, alResultRanges);
//  logger.debug("Result Ranges");
//  if (alResultRanges != null) {
//  for (int i = 0; i < alResultRanges.size(); i++) {
//  DateRange resultRange = (DateRange)alResultRanges.get(i);
//  logger.debug(resultRange);
//  }
//  }
    //  }

    private static void test() {
        DateRange testRange = null;
        DateRange testRange2 = null;
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        java.util.Date startDate2 = null;
        java.util.Date endDate2 = null;

        // test case 1
        startDate =
            new java.util.Date(
                    2005, 0, 4, 0, 0, 0);
        endDate =
            new java.util.Date(2005, 0, 4, 0, 0, 0);
        startDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);
        endDate2 =
            new java.util.Date(2005, 0, 2, 0, 0, 0);

        testRange = new DateRange(startDate, endDate);
        testRange2 = new DateRange(startDate2, endDate2);

        System.out.println("\n\n\ntest case 1");
        doTestExclusion(testRange, testRange2);

        // test case 2
        startDate =
            new java.util.Date(2005, 0, 4, 0, 0, 0);
        endDate =
            new java.util.Date(2005, 0, 6, 0, 0, 0);
        startDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);
        endDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);

        testRange = new DateRange(startDate, endDate);
        testRange2 = new DateRange(startDate2, endDate2);

        System.out.println("\n\n\ntest case 2");
        doTestExclusion(testRange, testRange2);

        // test case 3
        startDate =
            new java.util.Date(2005, 0, 4, 0, 0, 0);
        endDate =
            new java.util.Date(2005, 0, 4, 0, 0, 0);
        startDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);
        endDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);

        testRange = new DateRange(startDate, endDate);
        testRange2 = new DateRange(startDate2, endDate2);

        System.out.println("\n\n\ntest case 3");
        doTestExclusion(testRange, testRange2);

        // test cases 4, 5, 7
        startDate =
            new java.util.Date(2005, 0, 4, 0, 0, 0);
        endDate =
            new java.util.Date(2005, 0, 8, 0, 0, 0);
        startDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);
        endDate2 =
            new java.util.Date(2005, 0, 2, 0, 0, 0);

        testRange = new DateRange(startDate, endDate);
        testRange2 = new DateRange(startDate2, endDate2);

        System.out.println("\n\n\ntest cases 4, 5, 7");
        doTestExclusion(testRange, testRange2);

        // tests cases 4, 6, 7
        startDate =
            new java.util.Date(2005, 0, 5, 0, 0, 0);
        endDate =
            new java.util.Date(2005, 0, 7, 0, 0, 0);
        startDate2 =
            new java.util.Date(2004, 11, 31, 0, 0, 0);
        endDate2 =
            new java.util.Date(2005, 0, 4, 0, 0, 0);

        testRange = new DateRange(startDate, endDate);
        testRange2 = new DateRange(startDate2, endDate2);

        System.out.println("\n\n\ntest cases 4, 6, 7");
        doTestExclusion(testRange, testRange2);
    }

    //  private static void doTestOverlap(DateRange testRange, DateRange testRange2) {
//  DateRange overlappedRange = null;
//  Logger logger = Logger.getInstance("main");
//  java.util.Date startDate2 = testRange2.getStartDate();
//  java.util.Date endDate2 = testRange2.getEndDate();
//  for (int i = 0; i < 10; i++) {
//  logger.debug("------------------------------");
//  logger.debug("first range: \n" + testRange);
//  logger.debug("second range: \n" + testRange2);
//  overlappedRange = testRange.getOverLappingDateRange(testRange2);
//  logger.debug("overlapped range: \n" + overlappedRange);
//  // get next test date range
//  startDate2 = new java.util.Date(ubc.ems.util.DateUtility.getRelativeDay(startDate2, 1).getTime());
//  endDate2 =  new java.util.Date(ubc.ems.util.DateUtility.getRelativeDay(endDate2, 1).getTime());
//  testRange2 = new DateRange(startDate2, endDate2);
//  logger.debug("==============================");
//  }
    //  }

    private static void doTestExclusion(DateRange testRange, DateRange testRange2) {
        DateRange[] resultRanges = null;
        java.util.Date startDate2 = testRange2.getStartDate();
        java.util.Date endDate2 = testRange2.getEndDate();
        for (int i = 0; i < 10; i++) {
            System.out.println("------------------------------");
            System.out.println("first range: \n" + testRange);
            System.out.println("second range: \n" + testRange2);
            resultRanges = testRange.excludeRange(testRange2);
            System.out.println("result ranges:");
            if (resultRanges != null) {
                for (int rangeIndex = 0; rangeIndex < resultRanges.length;
                rangeIndex++) {
                    System.out.println(resultRanges[rangeIndex]);
                }
            }
            // get next test date range
            startDate2 = new java.util.Date(getRelativeDay(startDate2, 1).getTime());
            endDate2 =  new java.util.Date(getRelativeDay(endDate2, 1).getTime());
            testRange2 = new DateRange(startDate2, endDate2);
            System.out.println("==============================");
        }
    }
    
}
