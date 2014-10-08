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
            controller: ['$scope', '$q', 'VALIDATION_ERROR_TYPE', 'GENERAL_ERROR_TYPE', 'STATE', 'MessageService', 'TermsService',
            function($scope, $q, VALIDATION_ERROR_TYPE, GENERAL_ERROR_TYPE, STATE, MessageService, TermsService) {

                $scope.states = STATE;

                /**
                 * In this method we take a course & validation message object and return a formatted
                 * message depending on the validation message key.
                 *
                 * @returns {promise}
                 */
                $scope.formatMessage = function() {
                    var deferred = $q.defer();

                    var message = '',
                        data = $scope.data,
                        course = $scope.course;

                    if (data) {
                        if (angular.isString(data)) {
                            // Backwards compatibility, allow a straight string to go through
                            message = data;
                        } else if (data.messageKey) {
                            // We need to load config the message from the MessageService
                            MessageService.getMessage(data.messageKey)
                                .then(function(msg) {
                                    // Validation message w/ messageKey value
                                    switch (data.messageKey) {
                                        case VALIDATION_ERROR_TYPE.timeConflict:
                                            msg = formatTimeConflict(msg, data, course);
                                            break;
                                    }

                                    deferred.resolve(parseMessage(msg));
                                });

                            // Return out to give the MessageService time to load the messages
                            return deferred.promise;
                        } else if (data.txt) {
                            message = data.txt;
                        }
                    }

                    deferred.resolve(parseMessage(message));

                    return deferred.promise;
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
                 * @param message
                 * @param data
                 * @param course
                 * @returns {string}
                 *
                 *
                 * Need to update id field to be the actual one coming from the server
                 */
                function formatTimeConflict(message, data, course) {

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
                 * Parse the message and inject any additional parameters that are found
                 *
                 * @param message
                 * @param course
                 * @returns parsed message
                 */
                function parseMessage(message) {

                    // Wrap any instance of the course code with <strong></strong>
                    if (angular.isString(message)) {
                        var checks = [
                            '{{courseCode}} ({{regGroupCode}})',
                            '{{courseCode}} {{regGroupCode}}',
                            '{{courseCode}}',
                            '({{regGroupCode}})',
                            '{{regGroupCode}}',
                            '{{cluCode}}'
                        ];

                        for (var i = 0; i < checks.length; i++) {
                            var check = checks[i];
                            if (message.indexOf(check) !== -1) {
                                var target = '<strong>' + check + '</strong>';
                                if (message.indexOf(target) === -1) { // Don't rebold something that's already bolded
                                    message = message.replace(check, target, 'g'); // 'g' to do a global match
                                }

                                break;
                            }
                        }
                    }

                    return message;
                }

                $scope.getDataForMessage = function() {
                    if (angular.isUndefined($scope.data)) {
                        $scope.data = {};
                    }

                    if (angular.isUndefined($scope.data._messageData)) {
                        var data = {};
                        data.states = STATE;


                        // Make the data object available at the root level to the formatted messages
                        if (angular.isObject($scope.data)) {
                            angular.extend(data, $scope.data);
                        }

                        var selectedTerm = TermsService.getSelectedTerm();
                        if (selectedTerm) {
                            data.termName = TermsService.getSelectedTerm().termName;
                        }


                        // Create a copy of the data object so we don't mess with any base data
                        // Try to pull out the courseCode && regGroupCode and provide them as a standard parameters for the messages
                        if (angular.isString($scope.course)) { // Allow a straight string to come through
                            data.courseCode = $scope.course;
                        } else if (angular.isObject($scope.course)) { // Course object
                            if (angular.isDefined($scope.course.courseCode)) {
                                data.courseCode = $scope.course.courseCode;
                            }
                        }

                        // Remove the suffix from the course code (if any)
                        if (angular.isString(data.courseCode)) {
                            data.cluCode = data.courseCode.replace(/\D+$/, '');
                        }

                        if (angular.isObject($scope.course) && angular.isDefined($scope.course.regGroupCode)) {
                            data.regGroupCode = $scope.course.regGroupCode;
                        }

                        $scope.data._messageData = data;
                    }

                    return $scope.data._messageData; // Pin the message data into the data object that lives outside of this scope so that it's not regenerated multiple times for the same message
                };

            }],
            link: function(scope, element) {

                /**
                 * Update the element contents with the formatted message value
                 */
                function updateMessage() {
                    scope.formatMessage().then(function(message) {
                        element.html(message);

                        // Compile the element using a new isolated scope
                        $compile(element.contents())(angular.extend(scope.$new(true), scope.getDataForMessage()));
                    });
                }

                // Update the message when the data || course objects change
                scope.$watch('data', updateMessage);
                if (scope.course) {
                    scope.$watch('course', updateMessage);
                }
            }
        };

    }]);
