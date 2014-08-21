'use strict';

angular.module('regCartApp').filter('formatValidationMessage', ['VALIDATION_ERROR_TYPE', 'GENERAL_ERROR_TYPE', 'MessageService', function(VALIDATION_ERROR_TYPE, GENERAL_ERROR_TYPE, MessageService) {

    /**
     * In this method we take a course & validation message object and return a formatted
     * message depending on the validation message key.
     *
     * @example {{ validationMessage | formatValidationMessage:course }}
     *
     * @param data
     * @param course
     * @returns {string}
     */
    return function(data, course) {

        var message = '';

        if (data) {
            if (typeof(data) === 'string') {
                // Backwards compatibility, allow a straight string to go through
                message = data;
            } else if (data.txt) {
                message = data.txt;
            } else if (data.messageKey) {
                // Validation message w/ messageKey value
                switch (data.messageKey) {

                    case VALIDATION_ERROR_TYPE.timeConflict:
                        message = formatTimeConflict(data, course);
                        break;

                    case VALIDATION_ERROR_TYPE.maxCredits:
                        message = formatMaxCredits(data);
                        break;

                    case GENERAL_ERROR_TYPE.noRegGroup:
                        message = formatCourse(data.txt, data.course);
                        break;

                    default:
                        message = getMessage(data.messageKey);
                }
            }
        }

        return formatCourse(message, course);
    };

    /**
     * Format time credits violation message
     *
     * Cases:
     * 1. courseCode on root level:
     *    { messageKey: '...', courseCode: '...' }
     *
     * 2. conflictingCourses array:
     *    { messageKey: '...', conflictingCourses: [{ courseCode: '...' }, { courseCode: '...' }]}
     *
     * @param data
     * @param course
     * @returns {string}
     *
     *
     * Need to update id field to be the actual one coming from the server
     */
    function formatTimeConflict(data, course) {

        // look up the core message text
        var message = getMessage(data.messageKey);

        // Try to include the course codes of the conflicting items
        var conflicts = [];

        // Case 1: Check for a courseCode included at the root level
        if (data.courseCode) {
            conflicts.push({masterLprId: data.masterLprId, courseCode: data.courseCode});
        }

        // Case 2: Check for an array of conflictingCourses
        if (data.conflictingCourses) {
            angular.forEach(data.conflictingCourses, function(item) {
                conflicts.push(item);
            });
        }

        // Parse the identified codes
        if (conflicts.length) {
            var currentCourseId = null,
                codes = [],
                includedIds = [];

            if (course) {
                // Try to get the id of the current course so we can can avoid including it in the conflicting list
                if (angular.isDefined(course.cartItemId)) {
                    currentCourseId = course.cartItemId; // In cart
                } else if (angular.isDefined(course.masterLprId)) {
                    currentCourseId = course.masterLprId;
                }
            }

            for (var i = 0; i < conflicts.length; i++) {
                if (conflicts[i].masterLprId) {
                    // Don't include courses that match the current course (conflicts w/ itself)
                    // & only include them once
                    if (conflicts[i].masterLprId === currentCourseId || includedIds.indexOf(conflicts[i].masterLprId) >= 0) {
                        continue;
                    }

                    includedIds.push(conflicts[i].masterLprId);
                }

                if (conflicts[i].courseCode) {
                    codes.push('<strong>' + conflicts[i].courseCode + '</strong>');
                }
            }

            if (codes.length) {
                message += ' (' + codes.join(', ') + ')';
            }
        }

        return message;
    }

    function formatMaxCredits(data) {
        // look up the core message text
        var message = getMessage(data.messageKey);

        // if max credits are sent back with the message key, include them in the message back to the user
        if (data.maxCredits) {
            var maxCredits = parseFloat(data.maxCredits); // convert to a float to eliminate unnecessary decimals
            message += ' (<strong>' + maxCredits + ' credits</strong>)';
        }

        return message;
    }

    function formatCourse(message, course) {
        var courseCode = '';
        if (angular.isString(course)) {
            courseCode = course;
        } else if (course && angular.isDefined(course.courseCode)) {
            courseCode = course.courseCode;
        } else {
            // No course code found.
            return message;
        }

        // Replace any {{courseCode}} parameters with the courseCode value
        if (message.indexOf('{{courseCode}}') !== -1) {
            message = message.replace('{{courseCode}}', courseCode);
        }

        // Wrap the course code with <strong></strong>
        if (message.indexOf(courseCode) !== -1) {
            message = message.replace(courseCode, '<strong>' + courseCode + '</strong>');
        }

        return message;
    }

    function getMessage(messageKey) {
        return MessageService.getMessage(messageKey);
    }

}]);
