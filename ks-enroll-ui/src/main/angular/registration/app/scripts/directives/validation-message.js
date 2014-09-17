'use strict';

/**
 * Validation Messages Directive
 *
 * This directive is used to format and display messages that are pulled from the MessageService using a messageKey.
 *
 * @example <validation-message message="messageObject"></validation-message>
 * @example <validation-message message="messageObject" course="courseObject"></validation-message>
 * @example <span validation-message message="messageObject"></span>
 * @example <span class="validation-message" message="messageObject"></span>
 *
 * @param message Message object to be used
 * @param course Course object that the message pertains to (optional)
 */
angular.module('regCartApp')
    .directive('validationMessage', ['$compile', function($compile) {

        return {
            restrict: 'CAE',
            scope: {
                data: '=message',
                course: '=?' // optional handle on the course
            },
            controller: ['$scope', 'VALIDATION_ERROR_TYPE', 'GENERAL_ERROR_TYPE', 'MessageService',
            function($scope, VALIDATION_ERROR_TYPE, GENERAL_ERROR_TYPE, MessageService) {

                // Make the data object available at the root level to the formatted messages
                if (angular.isObject($scope.data)) {
                    angular.extend($scope, $scope.data);
                }


                /**
                 * In this method we take a course & validation message object and return a formatted
                 * message depending on the validation message key.
                 *
                 * @returns {string}
                 */
                $scope.getFormattedMessage = function() {
                    var message = '',
                        data = $scope.data,
                        course = $scope.course;

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

                                case GENERAL_ERROR_TYPE.noRegGroup:
                                    message = parseMessage(data.txt, data.course);
                                    break;

                                default:
                                    message = getBaseMessage(data.messageKey);
                            }
                        }
                    }

                    message = parseMessage(message, course);
                    return message;
                };


                /* ---------- Begin MessageKey-Specific Message Formatting Methods ---------- */

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
                    var message = getBaseMessage(data.messageKey);

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
                function getBaseMessage(messageKey) {
                    return MessageService.getMessage(messageKey);
                }

                /**
                 * Parse the message and inject any additional parameters that are found
                 *
                 * @param message
                 * @param course
                 * @returns parsed message
                 */
                function parseMessage(message, course) {
                    // Create a copy of the data object so we don't mess with any base data
                    if (!$scope.courseCode) {
                        // Try to pull out the courseCode && regGroupCode and provide them as a standard parameters for the messages
                        if (angular.isString(course)) { // Allow a straight string to come through
                            $scope.courseCode = course;
                        } else if (angular.isObject(course)) { // Course object
                            if (angular.isDefined(course.courseCode)) {
                                $scope.courseCode = course.courseCode;
                            }
                        }
                    }

                    if (!$scope.cluCode) {
                        // Remove the suffix from the course code (if any)
                        if (angular.isString($scope.courseCode)) {
                            $scope.cluCode = $scope.courseCode.replace(/\D+$/, '');
                        }
                    }

                    if (!$scope.regGroupCode) {
                        if (angular.isObject(course) && angular.isDefined(course.regGroupCode)) {
                            $scope.regGroupCode = course.regGroupCode;
                        }
                    }




                    // Wrap any instance of the course code with <strong></strong>
                    var checks = [
                        '{{courseCode}} ({{regGroupCode}})',
                        '{{courseCode}} {{regGroupCode}}',
                        '{{courseCode}}', '{{cluCode}}'
                    ];

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

                    return message;
                }

            }],
            link: function(scope, element) {

                /**
                 * Update the element contents with the formatted message value
                 */
                function updateMessage() {
                    var message = scope.getFormattedMessage();
                    element.html(message);
                    $compile(element.contents())(scope);
                }

                // Update the message when the data || course objects change
                scope.$watch('data', updateMessage);
                if (scope.course) {
                    scope.$watch('course', updateMessage);
                }
            }
        };

    }]);
