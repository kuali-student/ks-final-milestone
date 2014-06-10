'use strict';

angular.module('regCartApp').filter('formatValidationMessage', ['VALIDATION_ERROR_TYPE', 'MessageService', function(VALIDATION_ERROR_TYPE, MessageService) {

    /**
     * In this method we take a course & validation message object and return a formatted
     * message depending on the validation message key.
     *
     * @example {{ validationMessage | formatValidationMessage:course }}
     *
     * @param validationMessage
     * @param course
     * @returns {string}
     */
    return function(data, course, messages) {

        var message = '';

        if (data) {
            if (typeof(data) === 'string') {
                // Backwards compatibility, allow a straight string to go through
                message = data;
            } else if (data.messageKey) {
                // Validation message w/ messageKey value
                switch (data.messageKey) {

                    case VALIDATION_ERROR_TYPE.timeConflict:
                        message = formatTimeConflict(data, course, messages);
                        break;
                    
                    default:
                        message = getMessage(data.messageKey, messages);
                }
            }
        }

        return message;
    };

    /**
     * Format time credits violation message
     *
     * Cases:
     * 1. courseCode on root level:
     *    { messageKey: '...', courseCode: '...' }
     *
     * 2. conflictingItems array:
     *    { messageKey: '...', conflictingItems: [{ courseCode: '...' }, { courseCode: '...' }]}
     *
     * @param data
     * @param course
     * @param messages
     * @returns {string}
     *
     *
     * Need to update id field to be the actual one coming from the server
     */
    function formatTimeConflict(data, course, messages) {

        // look up the core message text
        var message = getMessage(data.messageKey, messages);

        // Try to include the course codes of the conflicting items
        var conflicts = [];

        // Case 1: Check for a courseCode included at the root level
        if (data.courseCode) {
            conflicts.push({id: data.id, courseCode: data.courseCode});
        }

        // Case 2: Check for an array of conflictingItems
        if (data.conflictingItems) {
            angular.forEach(data.conflictingItems, function(item) {
                conflicts.push(item);
            });
        }

        // Parse the identified codes
        if (conflicts.length) {
            var currentCourse = (course && course.id) ? course.id : null,
                codes = [],
                includedIds = [];

            for (var i = 0; i < conflicts.length; i++) {
                if (conflicts[i].id) {
                    // Don't include courses that match the current course (conflicts w/ itself)
                    // & only include them once
                    if (conflicts[i].id === currentCourse || includedIds.indexOf(conflicts[i].id) >= 0) {
                        continue;
                    }

                    includedIds.push(conflicts[i].id);
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

    function getMessage(messageKey, messages) {
        return MessageService.getMessage(messages, messageKey);
    }

}]);
