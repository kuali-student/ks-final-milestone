'use strict';

angular.module('regCartApp')
    .service('GlobalVarsService', ['STATE', 'STATUS',
    function GlobalVarsService(STATE, STATUS) {

        var userId;
        var conflictMap = {};
        var courseIndexes = {};
        var courseIndexPointer = 1;

        this.getUserId = function () {
            return userId;
        };

        this.setUserId = function (value) {
            userId = value;
        };

        this.getConflictMap = function () {
            return conflictMap;
        };

        this.setConflictMap = function(value) {
            if (!angular.equals(value, conflictMap)) {
                conflictMap = value;
            }
        };

        // In this method we pass in a state and it returns a status
        this.getCorrespondingStatusFromState = function (state) {
            var retStatus = STATUS.new;
            if (STATE.processing.indexOf(state) >= 0) {
                retStatus = STATUS.processing;
            } else if (STATE.success.indexOf(state) >= 0) {
                retStatus = STATUS.success;
            } else if (STATE.error.indexOf(state) >= 0) {
                retStatus = STATUS.error;
            } else if (STATE.waitlist.indexOf(state) >= 0) {
                retStatus = STATUS.waitlist;
            } else if (STATE.action.indexOf(state) >= 0) {
                retStatus = STATUS.action;
            }

            return retStatus;
        };

        // In this method we pass in a status and it returns a message to display
        this.getCorrespondingMessageFromStatus = function (status) {
            var statusMessage = '';
            if (status === STATUS.waitlist) {
                statusMessage = 'If a seat becomes available you will be registered automatically';
            }

            return statusMessage;
        };

        /*
        Return the course index based on the course details position in the array.

        If no course index is found, add the course details to the array and
        return the new index.
         */
        this.getCourseIndex = function (courseDetails) {
            var courseDetailsString = courseDetails.courseCode + (courseDetails.regGroup || courseDetails.regGroupCode); // regGroupCode in schedule
            var index = courseIndexes[courseDetailsString];

            if (isNaN(index)) {
                index = courseIndexPointer++;
                courseIndexes[courseDetailsString] = index;
            }

            return index;
        };

        /*
        Updates an array of offerings with any conflicts for each one
         */
        this.updateConflicts = function(courseList, type) {
            if (!angular.isArray(courseList)) {
                return;
            }

            var conflictMap = this.getConflictMap();
            for (var i = 0; i < courseList.length; i++) {
                var course = courseList[i];
                var conflicts = conflictMap[course.regGroupId];
                if (!angular.equals(course.conflicts, conflicts)) {
                    switch (type) {
                        case 'REG':
                            // registered courses only conflict with other registered courses
                            if (conflicts.type === 'REG') {
                                course.conflicts = conflicts;
                            }
                            break;
                        case 'WAIT':
                            // waitlisted courses can conflict with registered or waitlisted
                            if (conflicts.type === 'REG' || conflicts.type === 'WAIT') {
                                course.conflicts = conflicts;
                            }
                            break;
                        default:
                            course.conflicts = conflicts;
                    }
                }
            }
        };

    }]);
