'use strict';

describe('Service: RegUtil', function () {

    // load the service's module
    beforeEach(module('regCartApp', 'mockData'));

    var RegUtil;

    // instantiate service
    beforeEach(inject(function(_RegUtil_) {
        RegUtil = _RegUtil_;
    }));


    describe('convertTimeStringToTime', function() {
        it('should convert a time string to minutes', function() {
            var data = {
                0: '12:00am', // midnight
                30: '12:30am', // base
                60: '1:00am', // no leading 0, no space b/w am
                145: '02:25am', // leading 0, no space b/w am
                180: '3:00 am', // no leading 0, space b/w am
                200: '03:20 am', // leading 0, space b/w am
                720: '12:00pm', // noon
                800: '1:20pm', // no leading 0, no space b/w pm
                820: '01:40pm', // leading 0, no space b/w pm
                860: '2:20 pm', // no leading 0, space b/w pm
                885: '2:45 pm' // leading 0, space b/w pm
            };

            for (var min in data) {
                expect(RegUtil.convertTimeStringToTime(data[min])).toEqual(parseInt(min));
            }
        });
    });

    describe('convertStringToDate', function() {
        it('should convert a date string to a date', function() {
            // JS Date constructor takes the month as an int 0-11, not 1-12.
            expect(RegUtil.convertStringToDate('9/30/1986')).toEqual(new Date(1986, 8, 30));
            expect(RegUtil.convertStringToDate('09/17/2014')).toEqual(new Date(2014, 8, 17));
            expect(RegUtil.convertStringToDate('1/1/2012')).toEqual(new Date(2012, 0, 1));
            expect(RegUtil.convertStringToDate('1/01/2012')).toEqual(new Date(2012, 0, 1));
            expect(RegUtil.convertStringToDate('01/1/2012')).toEqual(new Date(2012, 0, 1));
            expect(RegUtil.convertStringToDate('01/01/2012')).toEqual(new Date(2012, 0, 1));
            expect(RegUtil.convertStringToDate('12/25/2013')).toEqual(new Date(2013, 11, 25));
        });
    });

    describe('coursesHaveScheduledTimes', function() {
        it('should be able to determine if any course in a list has scheduled times', inject(function(_studentScheduleTermResult_) {
            // Base case - empty list
            var list = [];
            expect(RegUtil.coursesHaveScheduledTimes(list)).toBeFalsy();

            // Mock registered courses, there are scheduled times in there
            list = _studentScheduleTermResult_.registeredCourseOfferings;
            expect(RegUtil.coursesHaveScheduledTimes(list)).toBeTruthy();

            // Mock waitlisted courses, there are NOT scheduled times in there
            list = _studentScheduleTermResult_.waitlistCourseOfferings;
            expect(RegUtil.coursesHaveScheduledTimes(list)).toBeFalsy();
        }));
    });

    describe('getCourseFromList', function() {
        it('should be to retrieve a course based on its ID from a list', inject(function(_studentScheduleTermResult_) {
            var list = angular.copy(_studentScheduleTermResult_.registeredCourseOfferings),
                course = list[0],
                id = course.masterLprId;

            // Base cases - empty data
            expect(RegUtil.getCourseFromList('abc', list)).toBeNull();
            expect(RegUtil.getCourseFromList({}, list)).toBeNull();
            expect(RegUtil.getCourseFromList({ masterLprId: 'abc' }, list)).toBeNull();
            expect(RegUtil.getCourseFromList({ regGroupId: 'abc' }, list)).toBeNull();

            // Match on the ID as a straight string
            expect(RegUtil.getCourseFromList(id, list)).toBe(course);

            // Match on object.masterLprId
            expect(RegUtil.getCourseFromList({ masterLprId: id }, list)).toBe(course);

            // Match on regGroupId
            course.masterLprId = null;
            course.regGroupId = id;
            expect(RegUtil.getCourseFromList({ masterLprId: id }, list)).toBeNull(); // Prove we're matching on regGroupId
            expect(RegUtil.getCourseFromList({ regGroupId: id }, list)).toBe(course);
        }));
    });

    describe('isCourseInList', function() {
        it('should be able to determine if a course is in a list', inject(function(_studentScheduleTermResult_) {
            var list = angular.copy(_studentScheduleTermResult_.registeredCourseOfferings),
                course = list[0],
                id = course.masterLprId;

            // Base cases - empty data
            expect(RegUtil.isCourseInList('abc', list)).toBeFalsy();
            expect(RegUtil.isCourseInList({}, list)).toBeFalsy();
            expect(RegUtil.isCourseInList({ masterLprId: 'abc' }, list)).toBeFalsy();
            expect(RegUtil.isCourseInList({ regGroupId: 'abc' }, list)).toBeFalsy();

            // Match on the ID as a straight string
            expect(RegUtil.isCourseInList(id, list)).toBeTruthy();

            // Match on object.masterLprId
            expect(RegUtil.isCourseInList({ masterLprId: id }, list)).toBeTruthy();

            // Match on regGroupId
            course.masterLprId = null;
            course.regGroupId = id;
            expect(RegUtil.isCourseInList({ masterLprId: id }, list)).toBeFalsy(); // Prove we're matching on regGroupId
            expect(RegUtil.isCourseInList({ regGroupId: id }, list)).toBeTruthy();
        }));
    });

    describe('sortCoursesByCreateTime', function() {
        it('should sort the courses by their createTime', inject(function(_studentScheduleTermResult_) {
            var list = angular.copy(_studentScheduleTermResult_.registeredCourseOfferings);

            // 1. Sort 'latestLast' / ASC
            RegUtil.sortCoursesByCreateTime(list, 'latestLast');

            // Check to see that all of the times are after the one before them
            var lastTime = null;
            for (var i = 0; i < list.length; i++) {
                if (lastTime !== null) {
                    expect(list[i].createTime).toBeGreaterThan(lastTime);
                }
                lastTime = list[i].createTime;
            }


            // 2. Sort 'latestFirst' / DESC
            RegUtil.sortCoursesByCreateTime(list, 'latestFirst');

            // Check to see that all of the times are after the one before them
            lastTime = null;
            for (i = 0; i < list.length; i++) {
                if (lastTime !== null) {
                    expect(list[i].createTime).toBeLessThan(lastTime);
                }
                lastTime = list[i].createTime;
            }
        }));
    });

});