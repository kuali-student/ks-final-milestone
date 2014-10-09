'use strict';

/**
 * Learning Plan Controller
 *
 * This controller is intended to be used as the controller for the learning-plan directive.
 *
 * Events:
 * - broadcast:
 *      - addCoursesToCart - broadcast when the user clicks the Keep in Cart button in the event of a failure
 * - emits: none
 * - catches:
 *      - termIdChanged - loads learning plan for new term
 */
angular.module('regCartApp')
    .controller('LearningPlanCtrl',
        ['$scope', '$rootScope', '$state', '$timeout',
            'STATE', 'SEARCH_ORIGINS', 'VALIDATION_ERROR_TYPE', 'LEARNING_PLAN_CATEGORIES', 'LEARNING_PLAN_ERRORS',
            'TermsService', 'CartService', 'ScheduleService', 'LearningPlanService',
        function ($scope, $rootScope, $state, $timeout,
                  STATE, SEARCH_ORIGINS, VALIDATION_ERROR_TYPE, LEARNING_PLAN_CATEGORIES, LEARNING_PLAN_ERRORS,
                  TermsService, CartService, ScheduleService, LearningPlanService) {
            console.log('>> LearningPlanCtrl');

            $scope.categories = {};
            $scope.selectedCategory = null;
            $scope.isLoaded = false;
            $scope.isCollapsed = LearningPlanService.isCollapsed;

            $scope.message = null;
            $scope.plan = null;

            // Transform the categories into more consumable objects
            for (var key in LEARNING_PLAN_CATEGORIES) {
                $scope.categories[key] = {
                    id: LEARNING_PLAN_CATEGORIES[key],
                    key: key,
                    count: 0
                };
            }



            /**
             * Add all actionable items to the cart
             * Utilizes the 'addCoursesToCart' event to push over to the CartCtrl to manage the adds
             */
            $scope.addAllToCart = function() {
                var actionableItems = $scope.getActionableItems();
                if (actionableItems.length > 0) {
                    $rootScope.$broadcast('addCoursesToCart', actionableItems);
                }
            };

            /**
             * Check a plan item (course) for issues
             *
             * @param item to check
             * @return {Boolean} if there are warnings to show
             */
            $scope.checkPlanItemForIssues = function(item) {
                if (!angular.isDefined(item.warnings)) {
                    var warnings = [];

                    if (item.regGroupId) { // This is a regGroup
                        if (STATE.lui.offered !== item.state) { // Not offered
                            warnings.push({ messageKey: LEARNING_PLAN_ERRORS.regGroupNotOffered, state: item.state });
                        }
                    } else { // This is a Course
                        if (STATE.dto.active !== item.state) {
                            warnings.push({ messageKey: LEARNING_PLAN_ERRORS.courseNotActive, state: item.state });
                        } else if (item.coursesOffered < 1) {
                            warnings.push({ messageKey: LEARNING_PLAN_ERRORS.courseNotOffered, state: item.state });
                        }
                    }

                    item.warnings = warnings;
                }

                return item.warnings.length > 0;
            };

            /**
             * Count of planned items in a given category
             *
             * @param plan list of planned items
             * @param category to check against
             * @returns {number} of plan items that match the category
             */
            $scope.countInCategory = function(plan, category) {
                var count = 0;
                for (var i = 0; i < plan.length; i++) {
                    if (plan[i].category === category.id) {
                        count++;
                    }
                }

                return count;
            };

            /**
             * Get the list of actionable items within the plan
             *  An item is actionable if:
             *  - its category matches the selected category
             *  - it has a regGroupId
             *  - it's not in the cart
             *  - it's not in the schedule (registered or waitlisted)
             *  - it's in an offered state
             *
             * @returns {Array} of actionable items
             */
            $scope.getActionableItems = function() {
                var items = [];

                if (angular.isArray($scope.plan)) {
                    for (var i = 0; i < $scope.plan.length; i++) {
                        var item = $scope.plan[i];
                        if (item.category === $scope.selectedCategory.id &&
                            item.regGroupId &&
                            item.state === STATE.lui.offered &&
                            !$scope.isItemInCart(item) &&
                            !$scope.isItemInSchedule(item)) {

                            items.push(item);
                        }
                    }
                }

                return items;
            };

            /**
             * Is the item in the cart
             *
             * @param course
             * @returns {boolean} true if item is in the cart
             */
            $scope.isItemInCart = function(course) {
                return CartService.isCourseInCart(course);
            };

            /**
             * Is the item in the schedule (registered or waitlisted)
             *
             * @param course
             * @returns {boolean} true if the item is in registered or waitlisted
             */
            $scope.isItemInSchedule = function(course) {
                return ScheduleService.isCourseRegistered(course) || ScheduleService.isCourseWaitlisted(course);
            };

            /**
             * Load the Learning Plan for the currently selected term
             */
            $scope.loadLearningPlan = function() {
                $scope.reset();

                if (!$scope.featureToggles.learningPlan) {
                    return;
                }

                var termId = TermsService.getTermId();
                if (termId) {
                    LearningPlanService.getLearningPlan(termId)
                        .then(function(plan) {
                            $scope.isLoaded = true;
                            if (angular.isArray(plan)) {
                                $scope.plan = plan;
                            } else {
                                $scope.plan = [];
                            }

                            // Calculate the count in each category
                            var hasSelected = false;
                            for (var key in $scope.categories) {
                                $scope.categories[key].count = $scope.countInCategory(plan, $scope.categories[key]);

                                // Select the first category with items in it
                                if (!hasSelected && $scope.categories[key].count > 0) {
                                    $scope.selectCategory($scope.categories[key]);
                                    hasSelected = true;
                                }
                            }
                        }, function(response) {
                            $scope.isLoaded = true;
                            if (angular.isDefined(response.messageKey)) {
                                if (response.messageKey === LEARNING_PLAN_ERRORS.notConfigured) {
                                    // If the learning plan is not configured it should not be visible at all.
                                    $scope.featureToggles.learningPlan = false;
                                } else {
                                    $scope.message = response;
                                }
                            }
                        });
                }
            };

            /**
             * Reset all of the scope variables to their initial state
             */
            $scope.reset = function() {
                $scope.isLoaded = false;
                $scope.plan = null;
                $scope.message = null;
                $scope.selectCategory($scope.categories[Object.keys($scope.categories)[0]]);

                for (var key in $scope.categories) {
                    $scope.categories[key].count = 0;
                }
            };

            /**
             * Set the currently selected category
             *
             * @param category
             */
            $scope.selectCategory = function(category) {
                $scope.selectedCategory = category;
            };

            /**
             * Toggle the collapsed nature of the LP widget.
             * This is done it the service b/c it needs to be persisted between scopes.
             */
            $scope.toggleCollapsed = function() {
                LearningPlanService.toggleCollapsed();
            };

            /**
             * View the details of the plan item
             * There are 2 potential places this will route:
             * 1. course details - if it is a reg group
             * 2. search results for the cluId - if not a reg group && courses are offered
             *
             * @param course
             */
            $scope.viewDetails = function(course) {
                if (course.regGroupId) {
                    // Redirects the view to the course details screen
                    $state.go('root.search.details', {
                        origin: SEARCH_ORIGINS.schedule,
                        searchCriteria: course.courseCode,
                        id: course.courseId,
                        code: course.courseCode,
                        regGroupId: course.regGroupId
                    });
                } else if (course.coursesOffered) { // Don't navigate to the results list if we know there are no offered courses
                    $state.go('root.search.results', {
                        origin: SEARCH_ORIGINS.schedule,
                        searchCriteria: course.courseCode,
                        cluId: course.cluId
                    });
                }
            };



            // Listen for the termIdChanged event that is fired when a term has been changed & processed
            $scope.$on('termIdChanged', function() {
                $scope.loadLearningPlan();
            });

            // We still need to load the learning plan (or init the controller when we navigate b/w pages)
            if (TermsService.getTermId()) {
                $scope.loadLearningPlan();
            }
        }]);