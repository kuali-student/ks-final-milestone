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
                h = str.substring(0, colonIndex),
                m = str.substring(colonIndex + 1, str.indexOf(' ')),
                isPm = str.toLowerCase().indexOf('pm') !== -1;

            var time = parseInt(h) * 60;
            time += parseInt(m);

            if (isPm && h !== '12') {
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
            var activityOfferings = [];
            if (angular.isArray(course.schedule)) {
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
            }

            course.activityOfferings = activityOfferings;

            return course;
        };
    }])
;