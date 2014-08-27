'use strict';

angular.module('regCartApp').filter('formatValidationMessage', ['$filter', 'VALIDATION_ERROR_TYPE', 'GENERAL_ERROR_TYPE', 'MessageService', function($filter, VALIDATION_ERROR_TYPE, GENERAL_ERROR_TYPE, MessageService) {

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

                    case VALIDATION_ERROR_TYPE.courseAlreadyTaken:
                        // Humanize the # attempts & # max attempts
                        data.attempts = $filter('multiplicativeAdverb')(data.attempts, ' times');
                        data.maxRepeats = $filter('multiplicativeAdverb')(data.maxRepeats, ' times');

                        message = getMessage(data.messageKey);
                        break;

                    case VALIDATION_ERROR_TYPE.repeatabilityWarning:
                        // Humanize the # attempts & # max attempts
                        data.attempts = $filter('ordinal')(data.attempts);
                        data.maxRepeats = $filter('multiplicativeAdverb')(data.maxRepeats, ' times');

                        message = getMessage(data.messageKey);
                        break;

                    case GENERAL_ERROR_TYPE.noRegGroup:
                        message = parseMessage(data.txt, data, data.course);
                        break;

                    default:
                        message = getMessage(data.messageKey);
                }
            }
        }

        return parseMessage(message, data, course);
    };

    /* ---------- Begin MessageKey-Specific Message Formatting Methods ---------- */

    /**
     * Message - Max Credits Violation
     *
     * @param data
     * @returns {string}
     */
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

    /**
     * Message - Time Credits Violation
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
            for (var j = 0; j < data.conflictingCourses.length; j++) {
                conflicts.push(data.conflictingCourses[j]);
            }
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

    /* ---------- Begin General Use Methods ---------- */

    /**
     * Get the base message from the MessageService
     *
     * @param messageKey
     * @returns string
     */
    function getMessage(messageKey) {
        return MessageService.getMessage(messageKey);
    }

    /**
     * Parse the message and inject any additional parameters that are found
     *
     * @param message
     * @param data
     * @param course
     * @returns parsed message
     */
    function parseMessage(message, data, course) {
        // Create a copy of the data object so we don't mess with any base data
        var params = angular.isObject(data) ? angular.copy(data) : {};
        params.courseCode = '';
        params.regGroupCode = '';

        // Try to pull out the courseCode && regGroupCode and provide them as a standard parameters for the messages
        if (angular.isString(course)) { // Allow a straight string to come through
            params.courseCode = course;
        } else if (angular.isObject(course)) { // Course object
            if (angular.isDefined(course.courseCode)) {
                params.courseCode = course.courseCode;
            }

            if (angular.isDefined(course.regGroupCode)) {
                params.regGroupCode = course.regGroupCode;
            }
        }


        // Iterate over the keys in the data object & replace the message parameters if they exist
        if (angular.isObject(params)) {
            for (var key in params) {
                // Replace any {{[key]}} parameters with their value
                if (message.indexOf('{{' + key + '}}') !== -1) {
                    message = message.replace('{{' + key + '}}', params[key]);
                }
            }
        }


        // Wrap any instance of the course code with <strong></strong>
        if (angular.isDefined(params.courseCode) && params.courseCode !== '') {
            var checks = [];
            if (angular.isDefined(params.regGroupCode) && params.regGroupCode !== '') {
                checks.push(params.courseCode + ' (' + params.regGroupCode + ')');
                checks.push(params.courseCode + ' ' + params.regGroupCode);
            }

            checks.push(params.courseCode);

            for (var i = 0; i < checks.length; i++) {
                var check = checks[i];
                if (message.indexOf(check) !== -1) {
                    var target = '<strong>' + check + '</strong>';
                    if (message.indexOf(target) === -1) { // Don't rebold something that's already bolded
                        message = message.replace(check, target);
                    }

                    break;
                }
            }
        }

        return message;
    }

}]);
