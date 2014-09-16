'use strict';

angular.module('regCartApp')

    /*
    The course-card directive displays a list of courses on your schedule (either registered
    or waitlist).
     */
    .directive('courseCard', function() {
        return {
            transclude: true,
            scope: {
                schedules: '=', // the schedule(s) to evaluate in this section
                credits: '=', // total number of credits in this section
                type: '@' // the course type [registered, waitlist]
            },
            templateUrl: 'partials/courseCard.html',
            controller: 'CardCtrl'
        };
    })

    /*
    The course-accordion directive displays the card for a specific course offering. The
    card can display courses from either the schedule or the cart.

    It is currently implemented in three places: registered courses, waitlisted courses,
    and the registration cart.
     */
    .directive('courseAccordion', function() {
        return {
            restrict: 'E',
            transclude: true,
            scope: {
                course: '=', // the course to display in this section
                type: '@', // the course type [registered, waitlist, cart]
                cardIndex: '=', // the index of the course in the list [0, 1, ...]
                cartId: '=' // for cart operations only
            },
            templateUrl: 'partials/courseAccordion.html',
            controller: 'CardCtrl'
        };
    })

    /*
    The CartCtrl controller handles various operations for both the course-card and
    course-accordion directives.
     */
    .controller('CardCtrl', ['$scope', '$state', '$timeout', 'STATUS', 'GRADING_OPTION', 'COURSE_TYPES', 'SEARCH_CRITERIA', 'GlobalVarsService', 'ScheduleService',
        function($scope, $state, $timeout, STATUS, GRADING_OPTION, COURSE_TYPES, SEARCH_CRITERIA, GlobalVarsService, ScheduleService) {
            /*
             Utility function for providing configuration variables based on
             whether the course in scope is registered, waitlist, or cart.
             */
            function getConfig() {
                var config;
                switch ($scope.type) {
                    case COURSE_TYPES.waitlisted:
                        config = {
                            heading: 'Waitlisted',
                            prefix: 'waitlisted',
                            prefix2: 'waitlist_',
                            prefix3: 'waitlist_',
                            remove: 'Remove'
                        };
                        break;
                    case COURSE_TYPES.cart:
                        config = {
                            heading: 'Cart', // not used
                            prefix: 'cart', // not used
                            prefix2: '',
                            prefix3: 'cart_',
                            remove: 'Remove'
                        };
                        break;
                    default : // 'registered'
                        config = {
                            heading: 'Registered',
                            prefix: 'reg',
                            prefix2: '',
                            prefix3: 'schedule_',
                            remove: 'Drop'
                        };
                }

                return config;
            }


            $scope.config = getConfig();
            $scope.courseTypes = COURSE_TYPES;
            $scope.statuses = STATUS;

            $scope.registeredCourseOfferings = ScheduleService.getRegisteredCourses();
            $scope.waitlistCourseOfferings = ScheduleService.getWaitlistedCourses();

            /*
            Closes the drop confirmation dialog
             */
            $scope.cancelDropConfirmation = function (course) {
                course.dropping = false;
            };

            /*
            Shows the grading badge (unless it is letter -- the default)
             */
            $scope.showBadge = function (course) {
                return course.gradingOptionId !== GRADING_OPTION.letter || course.editGradingOption;
            };

            /*
            Edits the credit and grading options for a course
             */
            $scope.editItem = function (course) {
                course.newCredits = course.credits;
                course.newGrading = course.gradingOptionId;
                course.editing = true;
            };

            /*
             Returns the grading option for the course
             */
            $scope.gradingOption = function (course) {
                return course.gradingOptions[$scope.course.gradingOptionId];
            };

            /*
             Returns the course index
             */
            $scope.courseIndex = function(course) {
                if (!angular.isDefined(course.index) || !course.index) {
                    course.index = GlobalVarsService.getCourseIndex(course);
                }

                return course.index;
            };

            /*
             Closes a status message for the card
             */
            $scope.removeStatusMessage = function (course) {
                var statusMessage = course.statusMessage;
                course.statusMessage = null;

                $scope.$emit('courseStatusMessageRemoved', $scope.type, course, statusMessage);
            };

            /*
             Common "drop" functionality. Can be used to drop a registered course,
             remove a course from the waitlist, or remove a course from the cart
             */
            $scope.dropCourse = function (course) {
                switch ($scope.type) {
                    case COURSE_TYPES.cart:
                        // emit an event to the parent scope (handled in cart.js)
                        $scope.$emit('deleteCartItem', course);
                        break;
                    default : // 'registered', 'waitlist'
                        console.log('Open drop confirmation');
                        course.dropping = true;
                }
            };

            $scope.viewDetails = function(course) {
                // redirects the view to the search details screen
                $state.go('root.search.details', { searchCriteria: SEARCH_CRITERIA.fromSchedule, id: course.courseId, code: course.courseCode, regGroupId: course.regGroupId });
            };

            /*
             Emits a 'dropCourse' event and handles the result of that action accordingly.
             */
            $scope.dropCourseConfirmed = function(course) {
                course.dropping = false; // used to display confirmation popup
                course.dropProcessing = true; // used to display spinner while poll is running

                setCourseStatusMessage('drop', course,
                    '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> drop processing',
                    STATUS.processing);


                $scope.$emit('dropCourse', $scope.type, course, function() {
                    course.dropped = true; // used to display course details vs success to drop message
                    course.dropProcessing = false;

                    var message;
                    switch ($scope.type) {
                        case COURSE_TYPES.registered:
                            message = '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> dropped successfully';
                            break;
                        case COURSE_TYPES.waitlisted:
                            message = 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully';
                            break;
                    }

                    setCourseStatusMessage('drop', course, message, STATUS.success);

                }, function(messages) {
                    course.dropProcessing = false;
                    course.isopen = false; // collapse the card
                    setCourseStatusMessage('drop', course, messages, STATUS.error);
                });
            };

            /*
             Emits an 'updateCourse' event and handles the result of that action accordingly.
             */
            $scope.updateItem = function(course) {
                console.log('Updating ' + $scope.type + ' course. Grading: ' + course.newGrading + ', Credits: ' + course.newCredits);

                course.requestProcessing = true; // used to display spinner while poll is running

                // Create version of the course with the updates applied
                var newCourse = angular.copy(course);
                applyCourseUpdates(newCourse);

                $scope.$emit('updateCourse', $scope.type, course, newCourse, function() {
                    course.requestProcessing = false;
                    updateCard(course);
                }, function(messages) {
                    course.requestProcessing = false;
                    course.editing = false;
                    course.isopen = false; // collapse the card
                    revertCourseUpdates(course);
                    setCourseStatusMessage('update', course, messages, STATUS.error);
                });
            };

            /*
             Parses the course conflicts into user-friendly text
             */
            $scope.parseConflicts = function(conflicts) {
                var message = '';
                if (angular.isArray(conflicts) && conflicts.length > 0) {
                    for (var i=0; i<conflicts.length; i++) {
                        if (i > 0) {
                            message += ', ';
                        }
                        message += conflicts[i].courseCode;
                    }
                }
                return message;
            };

            /*
             Helper method for applying the updates to a course model
             */
            function applyCourseUpdates(course) {
                course.credits = course.newCredits;
                course.gradingOptionId = course.newGrading;

                return course;
            }

            /*
             Helper method for setting the course status messages
             */
            function setCourseStatusMessage(action, course, messages, type) {
                if (!angular.isArray(messages)) {
                    // Push the messages to an array
                    messages = [ messages ];
                }

                course.statusMessage = {
                    action: action,
                    type: type,
                    messages: messages
                };
            }

            /*
             Revert the changes made on a course
             */
            function revertCourseUpdates(course) {
                course.newCredits = course.credits;
                course.newGrading = course.gradingOptionId;
            }

            /*
            Common code for displaying the "glow" on changed items when updating a card.
             */
            function updateCard(course) {
                // Apply the updates
                var oldCourse = angular.copy(course);
                applyCourseUpdates(course);

                course.editing = false;
                course.isopen = false; // collapse the card

                // This part is responsible for glow effect: when the card is updated (whether credit or grading) we want to highlight the change and then fade the highlight away after 2 secs
                if (course.gradingOptionId !== oldCourse.gradingOptionId) {
                    // the highlighting fades in & stays for 2 seconds
                    course.editGradingOption = true;
                    $timeout(function(){
                        course.editGradingOption = false;
                    }, 2000);
                }

                if (course.credits !== oldCourse.credits) {
                    // the highlighting fades in & stays for 2 seconds
                    course.editCredits = true;
                    $timeout(function(){
                        course.editCredits = false;
                    }, 2000);
                }
            }

        }])
;
