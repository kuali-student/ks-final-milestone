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
            /*
             Utility function for providing configuration variables based on
             whether the course in scope is registered, waitlist, or cart.
             */
            function getConfig() {
                var config;
                switch ($scope.type) {
                    case 'waitlist':
                        config = {
                            heading: 'Waitlisted',
                            prefix: 'waitlisted',
                            prefix2: 'waitlist_',
                            prefix3: 'waitlist_',
                            remove: 'Remove'
                        };
                        break;
                    case 'cart':
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


            /*
             Standardize the grading option value in the course to make it easier throughout the rest of the directive.
             In the cart, it is course.gradingOptionId, everywhere else it is course.grading.
             */
            if ($scope.type === 'cart') {
                $scope.course.gradingOptionId = $scope.course.grading;
            }



            /*
            Returns either registered or waitlisted course offerings based on the
            type in scope
             */
            $scope.courseOfferings = function(schedule) {
                var offerings;
                switch ($scope.type) {
                    case 'waitlist':
                        offerings = schedule.waitlistCourseOfferings;
                        break;
                    default: // 'registered'
                        offerings = schedule.registeredCourseOfferings;
                }

                return offerings;
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
                return course.gradingOptions[$scope.course.gradingOptionId];
            };

            /*
            Returns the course title
             */
            $scope.courseTitle = function (course) {
                var title;
                switch ($scope.type) {
                    case 'cart':
                        title = course.courseTitle;
                        break;
                    default : // 'registered', 'waitlist'
                        title = course.longName;
                }

                return title;
            };

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
                    console.log('old: ' + course.gradingOptionId + ' To: ' + itemResult.gradingOptionId);
                    updateCard(course, itemResult);
                });
            }

            /*
            Common code for displaying the "glow" on changed items when updating
            a card.
             */
            function updateCard(course, itemResult) {
                console.log(itemResult);
                var oldCredits = course.credits;  // need to compare to see if it was changed and need a glow
                var oldGrading = course.gradingOptionId;  // need to compare to see if it was changed and need a glow

                // Update the course with the new credits & grading option value
                course.credits = itemResult.credits;

                switch ($scope.type) {
                    case 'cart':
                        course.gradingOptionId = itemResult.grading;
                        course.grading = course.gradingOptionId; // Cart items store this value in grading
                        break;
                    default : // 'registered', 'waitlist'
                        course.gradingOptionId = itemResult.gradingOptionId;
                }

                course.editing = false;
                course.isopen = !course.isopen; // collapse the card

                // This part is responsible for glow effect: when the card is updated (whether credit or grading) we want to highlight the change and then fade the highlight away after 2 secs
                if (course.newGrading !== oldGrading) {
                    // the highlighting fades in & stays for 2 seconds
                    course.editGradingOption = true;
                    $timeout(function(){
                        course.editGradingOption = false;
                    }, 2000);
                }

                if (course.newCredits !== oldCredits) {
                    // the highlighting fades in & stays for 2 seconds
                    course.editCredits = true;
                    $timeout(function(){
                        course.editCredits = false;
                    }, 2000);
                }
            }
        }])
;
