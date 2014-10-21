'use strict';

angular.module('regCartApp')
    /*
     * This service contains utility methods used throughout the registration application.
     *
     * No persistent variables should be used, only stand-alone methods should be in this service.
     */
    .service('RegUtil', [function RegUtil() {
        /*
         * Converts a time string to an integer representing the minutes since
         * start of day.
         */
        this.convertTimeStringToTime = function(str) {
            var colonIndex = str.indexOf(':'),
                h = parseInt(str.substring(0, colonIndex)),
                m = parseInt(str.substring(colonIndex + 1, colonIndex + 3)),
                isPm = str.toLowerCase().indexOf('pm') !== -1;

            if (!isPm && h === 12) {
                // 12am should be reset to 0 hours
                h = 0;
            }

            var time = (h * 60) + m;

            if (isPm && h !== 12) {
                time += (60 * 12);
            }

            return time;
        };

        /*
         * Converts a date string in format mm/dd/yyyy to a Date object
         */
        this.convertStringToDate = function(str) {
            var parts = str.split('/');
            var date;
            if (parts.length === 3) {
                date = new Date(parts[2],parts[0]-1,parts[1]);
            } else {
                console.log('Invalid date string: '+str);
            }
            return date;
        };


        /**
         * Standardize the fields between cart & scheduled courses.
         * This should really be done on the REST side.
         *
         * @param course standardized
         * @returns course
         */
        this.standardizeCartCourse = function(course) {
            // Schedule has the courseTitle as longName
            course.longName = course.courseTitle;

            // Schedule uses gradingOptionId to denote the selected grading option
            course.gradingOptionId = course.grading;

            // Schedule AO's are structured quite differently than the cart
            if (angular.isArray(course.schedule)) {
                var activityOfferings = [];
                for (var i = 0; i < course.schedule.length; i++) {
                    var ao = course.schedule[i];
                    ao.scheduleComponents = [];

                    for (var j = 0; j < ao.activityOfferingLocationTime.length; j++) {
                        var component = {},
                            locationTime = ao.activityOfferingLocationTime[j];

                        // Flatten the location, time, & isTBA
                        component.buildingCode = locationTime.location.building;
                        component.roomCode = locationTime.location.room;
                        angular.extend(component, locationTime.time);
                        component.isTBA = locationTime.isTBA;

                        ao.scheduleComponents.push(component);
                    }

                    // Delete the old data structure
                    delete ao.activityOfferingLocationTime;

                    activityOfferings.push(ao);
                }

                // Delete the old data structure
                delete course.schedule;

                course.activityOfferings = activityOfferings;
            }

            return course;
        };


        /**
         * Check to see if any course in the list has an AO with a scheduled time (!isTBA).
         *
         * @param courses list of courses
         * @returns {boolean}
         */
        this.coursesHaveScheduledTimes = function(courses) {
            for (var i = 0; i < courses.length; i++) {
                if (!courses[i].dropped) {
                    for (var j = 0; j < courses[i].activityOfferings.length; j++) {
                        var scheduleComponents = courses[i].activityOfferings[j].scheduleComponents;
                        for (var k = 0; k < scheduleComponents.length; k++) {
                            if (!scheduleComponents[k].isTBA) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        };

        /**
         * Locate and return a course from a list of courses
         *
         * @param c course to match on
         * @param list of courses
         * @returns {course}
         */
        this.getCourseFromList = function(c, list) {
            var course = c;
            if (angular.isString(c)) {
                course = {
                    masterLprId: c,
                    regGroupId: c
                };
            }

            var listCourse = null;
            for (var i = 0; i < list.length; i++) {
                if ((angular.isDefined(list[i].regGroupId) && list[i].regGroupId === course.regGroupId) ||
                    (angular.isDefined(list[i].masterLprId) && list[i].masterLprId === course.masterLprId)) {

                    listCourse = list[i];
                    break;
                }
            }

            return listCourse;
        };

        /**
         * Global method for seeing if a course is present in a list of courses
         *
         * @param c course to match on
         * @param list of courses
         * @returns {boolean}
         */
        this.isCourseInList = function (c, list) {
            return this.getCourseFromList(c, list) !== null;
        };

        /**
         * Sort a course list by a specified order
         *
         * @param list of courses
         * @param order to sort by [ 'latestFirst' || 'latestLast' ]
         */
        this.sortCoursesByCreateTime = function(list, order) {
            if (list.length > 1) {
                switch (order) {
                    case 'latestFirst':
                        list.sort(function(course1, course2) {
                            if( course1.createTime < course2.createTime){
                                return 1;
                            }else if( course1.createTime > course2.createTime){
                                return -1;
                            }else{
                                return 0;
                            }

                        });
                        break;
                    default:
                        list.sort(function(course1, course2) {
                            if( course1.createTime < course2.createTime){
                                return -1;
                            }else if( course1.createTime > course2.createTime){
                                return 1;
                            }else{
                                return 0;
                            }
                        });
                }
            }

            return list;
        };

        /*
         A course conflicts if its time range overlaps at all with another course
         E.g. Course:                [-----]
         Conflicts with both:      [--] [---]
         */
        this.coursesConflict = function(c1, c2) {
            return (c1.startTime <= c2.endTime && c1.endTime >= c2.startTime);
        };
    }])
;