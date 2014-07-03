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
    .controller('CardCtrl', ['$scope', '$timeout', 'GlobalVarsService', 'CartService', 'ScheduleService', 'STATUS', 'GRADING_OPTION',
        function($scope, $timeout, GlobalVarsService, CartService, ScheduleService, STATUS, GRADING_OPTION) {
            $scope.config = getConfig();

            /*
            Returns either registered or waitlisted course offerings based on the
            type in scope
             */
            $scope.courseOfferings = function(schedule) {
                switch ($scope.type) {
                    case 'waitlist':
                        return schedule.waitlistCourseOfferings;
                        break;
                    default: // 'registered'
                        return schedule.registeredCourseOfferings;
                }
            };

            /*
            Common "drop" functionality. Can be used to drop a registered course,
            remove a course from the waitlist, or remove a course from the cart
             */
            $scope.dropCourse = function (index, course) {
                console.log('course-card index: '+index);
                switch ($scope.type) {
                    case 'cart':
                        // emit an event to the parentt scope (handled in cart.js)
                        $scope.$emit('deleteCartItem', index);
                        break;
                    default : // 'registered', 'waitlist'
                        console.log('Open drop confirmation');
                        course.dropping = true;
                        $scope.index = index;
                        $scope.course = course;
                }
            };

            /*
            Closes the drop confirmation dialog
             */
            $scope.cancelDropConfirmation = function (course) {
                course.dropping = false;
            };

            /*
            Closes a status message card
             */
            $scope.removeStatusMessage = function (course) {
                course.statusMessage = null;
            };

            /*
            Shows the grading badge (unless it is letter -- the default)
             */
            $scope.showBadge = function (course) {
                switch ($scope.type) {
                    case 'cart':
                        return course.grading !== GRADING_OPTION.letter || course.editGradingOptionLetter;
                        break;
                    default : // 'registered', 'waitlist'
                        return course.gradingOptionId !== GRADING_OPTION.letter || course.editGradingOptionLetter;
                }
            };

            /*
            Edits the credit and grading options for a course
             */
            $scope.editItem = function (course) {
                course.newCredits = course.credits;
                course.editing = true;

                switch ($scope.type) {
                    case 'cart':
                        course.newGrading = course.grading;
                        break;
                    default : // 'registered', 'waitlist'
                        course.newGrading = course.gradingOptionId;
                }
            };

            /*
            Calls the appropriate function for updating a course offering
            based on the scope type.
             */
            $scope.updateItem = function(course) {
                switch ($scope.type) {
                    case 'registered':
                        updateScheduleItem(course);
                        break;
                    case 'waitlist':
                        updateWaitlistItem(course);
                        break;
                    case 'cart':
                        updateCartItem(course);
                }
            };

            /*
            Removes the waitlist status message from the view
             */
            $scope.removeWaitlistStatusMessage = function (course) {
                $scope.$emit('removeWaitlistStatusMessage', course);
            };

            /*
            Emits an event to the parent scope to drop the registration
            group. Actual drop is handled in schedule.js.
             */
            $scope.dropRegistrationGroup = function (index, course) {
                $scope.$emit('dropRegistered', index, course);
            };

            /*
            Emits an event to the parent scope to remove the user from
            the wait list for a course. Actual removal is handled in
             schedule.js.
             */
            $scope.dropFromWaitlist = function (index, course) {
                $scope.$emit('dropWaitlist', index, course);
            };

            /*
            Returns the grading option for the course
             */
            $scope.gradingOption = function (course) {
                switch ($scope.type) {
                    case 'cart':
                        return course.gradingOptions[$scope.course.grading];
                        break;
                    default : // 'registered', 'waitlist'
                        return course.gradingOptions[$scope.course.gradingOptionId];
                }
            };

            /*
            Returns the course title
             */
            $scope.courseTitle = function (course) {
                switch ($scope.type) {
                    case 'cart':
                        return course.courseTitle;
                        break;
                    default : // 'registered', 'waitlist'
                        return course.longName;
                }
            };

            /*
            Utility function for providing configuration variables based on
            whether the course in scope is registered, waitlist, or cart.
             */
            function getConfig() {
                switch ($scope.type) {
                    case 'waitlist':
                        return {
                            heading: 'Waitlisted',
                            prefix: 'waitlisted',
                            prefix2: 'waitlist_',
                            prefix3: 'waitlist_',
                            remove: 'Remove'
                        };
                        break;
                    case 'cart':
                        return {
                            heading: 'Cart', // not used
                            prefix: 'cart', // not used
                            prefix2: '',
                            prefix3: 'cart_',
                            remove: 'Remove'
                        };
                        break;
                    default : // 'registered'
                        return {
                            heading: 'Registered',
                            prefix: 'reg',
                            prefix2: '',
                            prefix3: 'schedule_',
                            remove: 'Drop'
                        };
                }
            }

            /*
            Calls the RESTful service to update a registered course on the schedule.
             */
            function updateScheduleItem(course) {
                console.log('Updating registered course:');
                console.log(course.newCredits);
                console.log(course.newGrading);
                ScheduleService.updateScheduleItem().query({
                    courseCode: course.courseCode,
                    regGroupCode: course.regGroupCode,
                    masterLprId: course.masterLprId,
                    termId: $scope.termId,
                    credits: course.newCredits,
                    gradingOptionId: course.newGrading
                }, function (itemResult) {
                    GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(itemResult.credits));
                    updateCard(course, itemResult);
                }, function (error) {
                    course.statusMessage = {txt: error.data, type: STATUS.error};
                });
            }

            /*
            Calls the RESTful service to update a waitlisted course on the schedule.
             */
            function updateWaitlistItem(course) {
                console.log('Updating waitlisted course:');
                console.log(course.newCredits);
                console.log(course.newGrading);
                ScheduleService.updateWaitlistItem().query({
                    courseCode: course.courseCode,
                    regGroupCode: course.regGroupCode,
                    masterLprId: course.masterLprId,
                    termId: $scope.termId,
                    credits: course.newCredits,
                    gradingOptionId: course.newGrading
                }, function (itemResult) {
                    GlobalVarsService.setWaitlistedCredits(parseFloat(GlobalVarsService.getWaitlistedCredits()) - parseFloat(course.credits) + parseFloat(itemResult.credits));
                    updateCard(course, itemResult);
                }, function (error) {
                    course.statusMessage = {txt: error.data, type: STATUS.error};
                });
            }

            /*
            Calls the RESTful service to update the cart item.
             */
            function updateCartItem(course) {
                console.log('Updating cart item. Grading: ' + course.newGrading + ', credits: ' + course.newCredits);
                CartService.updateCartItem().query({
                    cartId: $scope.cartId,
                    cartItemId: course.cartItemId,
                    credits: course.newCredits,
                    gradingOptionId: course.newGrading
                }, function (itemResult) {
                    console.log('old: ' + course.credits + ' To: ' + itemResult.credits);
                    console.log('old: ' + course.grading + ' To: ' + itemResult.grading);
                    updateCard(course, itemResult);
                });
            }

            /*
            Common code for displaying the "glow" on changed items when updating
            a card.
             */
            function updateCard(course, itemResult) {
                console.log(itemResult);
                var oldCredits=course.credits;  // need to compare to see if it was changed and need a glow
                var oldGrading=course.gradingOptionId;  // need to compare to see if it was changed and need a glow
                course.credits = itemResult.credits;
                var gradingOptionId;
                switch ($scope.type) {
                    case 'cart':
                        course.grading = itemResult.grading;
                        gradingOptionId = course.grading;
                        break;
                    default : // 'registered', 'waitlist'
                        course.gradingOptionId = itemResult.gradingOptionId;
                        gradingOptionId = course.grading;
                }
                course.editing = false;
                course.isopen = !course.isopen; // collapse the card
                // This part is responsible for glow effect: when the card is updated (whether credit or grading) we want to highlight the change and then fade the highlight away after 2 secs
                console.log('Started to glow...');
                if (course.newGrading !== oldGrading) {
                    // the highlighting fades in
                    course.editGradingOption = true;
                    // The diffeence with the grading option LETTER is that we don't display it permanently.
                    // So when we change the grading option to LETTER we display and highlight it, then it fades out and disappears completely.
                    if (gradingOptionId === GRADING_OPTION.letter) {
                        course.editGradingOptionLetter = true;
                    }
                    // the highlighting stays for 2 secs
                    $timeout(function(){
                        course.editGradingOption = false;
                        course.editGradingOptionDone = true;
                    }, 2000);
                    // the highlighting fades out
                    $timeout(function(){
                        course.editGradingOptionDone = false;
                        if (gradingOptionId === GRADING_OPTION.letter) {
                            course.editGradingOptionLetter = false;
                        }
                    }, 4000);
                }
                if (course.newCredits !== oldCredits) {
                    // the highlighting fades in
                    course.editCredits = true;
                    // the highlighting stays for 2 secs
                    $timeout(function(){
                        course.editCredits = false;
                        course.editCreditsDone = true;
                    }, 2000);
                    // the highlighting fades out
                    $timeout(function(){
                        course.editCreditsDone = false;
                    }, 4000);
                }
                // End of glow effect
            }
        }])
;
