'use strict';

/*
 * Controller for displaying course details (title, desc, AOs, etc)
 *
 * Event Handling
 * -- Emits: none
 * -- Broadcasts: "directRegisterForCourse" -- this is caught by
 cart-controller.js and registers the user for the selected reg group
 * -- Receives: "termIdChanged" -- received from the main-
 controller.js, updates cart when term Id is changed
 *             "showSubterm" -- received from course-search-
 formatter.js, opens modal for showing subterm info
 *             "showRequisites" -- received from course-search-
 formatter.js, opens modal for showing requisites info
 *              "toggleAO" -- received from the search list
 directives, select/deselects the given ao
 */
angular.module('regCartApp')
    .controller('CourseDetailsCtrl', ['$scope', '$rootScope', '$state', '$filter', '$timeout', '$modal', 'STATUS',
        'SEARCH_ORIGINS', 'SearchService', 'CartService', 'ScheduleService',
    function CourseDetailsCtrl($scope, $rootScope, $state, $filter, $timeout, $modal, STATUS, SEARCH_ORIGINS,
                               SearchService, CartService, ScheduleService) {
        console.log('>> CourseDetailsCtrl');

        $scope.origins = SEARCH_ORIGINS;
        $scope.statuses = STATUS;
        $scope.stateParams = $state.params; // Expose the state parameters to the scope so they can be used in the back link
        $scope.course = null;          // Handle on the course
        $scope.registered = ScheduleService.getRegisteredCourses();
        $scope.waitlisted = ScheduleService.getWaitlistedCourses();
        $scope.unusedCart = CartService.getCartCourses();

        // Push the user back to the search page when the term is changed
        $scope.$on('termIdChanged', function(event, newValue, oldValue) {
            if (oldValue !== null && $scope.uiState === 'root.search.details') {
                $state.go('root.search.results', $state.params);
            }
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria & course ID from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            $scope.stateParams = toParams;
            $scope.origin = toParams.origin;

            if (angular.isDefined(toParams.id)) {
                loadCourse(toParams.id, toParams.code);
            }
        });

        $scope.loading = false; // Course not yet loading
        var lastCourseId = null;
        function loadCourse(courseId, courseCode) {
            if (courseId === null || ($scope.course !== null && $scope.course.courseOfferingId !== null && courseId === $scope.course.courseOfferingId && $scope.course.courseOfferingCode !== null && courseCode === $scope.course.courseOfferingCode)) {
                // Don't load a null courseId or the same course we already have.
                return;
            }

            $scope.loading = true; // Course is still loading
            console.log('Loading course ' + courseId + ', ' + courseCode);

            lastCourseId = courseId;
            SearchService.getCourse().query({courseOfferingId: courseId, courseCode: courseCode}, function(result) {
                if (lastCourseId === courseId) {
                    // This query matches the last one ran - it's current.

                    var regGroups = {};
                    var aoMap = {};

                    if (angular.isDefined(result.activityOfferingTypes) && angular.isArray(result.activityOfferingTypes)) {
                        angular.forEach(result.activityOfferingTypes, function(aoType) {

                            // Apply aoFormatter filter to the activity offerings for display in the table
                            aoType.formattedOfferings = $filter('aoFormatter')(angular.copy(aoType.activityOfferings));

                            angular.forEach(aoType.activityOfferings, function(ao) {

                                // Give each activity offering a handle on its type.
                                // This facilitates managing the selected offerings.
                                ao.activityOfferingType = aoType.activityOfferingType;

                                // Add the ao to the map
                                aoMap[ao.activityOfferingCode] = ao;

                                // Transform the reg groups to be more easily consumed
                                angular.forEach(ao.regGroupInfos, function(regGroup, id) {
                                    if (angular.isUndefined(regGroups[id])) {
                                        regGroups[id] = regGroup;
                                    }

                                    if (angular.isUndefined(regGroup.activityOfferingIds)) {
                                        regGroup.activityOfferingIds = [];
                                    }

                                    // Give each reg group a handle on the activity offerings that compose it.
                                    regGroups[id].activityOfferingIds.push(ao.activityOfferingId);
                                });

                                // Check to see if there are any additional info icons to display for this course
                                if (!$scope.additionalInfo &&
                                    (ao.subterm !== null ||
                                        (angular.isArray(ao.requisites) && ao.requisites.length > 0)) ||
                                        ao.honors === 'true') {
                                    $scope.additionalInfo = true;
                                }
                            });

                            // initialize flags
                            angular.forEach(aoType.formattedOfferings, function(formattedOffering) {
                                var ao = aoMap[formattedOffering.aoId];
                                ao.flags = {};
                                formattedOffering.flags = ao.flags;
                                formattedOffering.inCartIndicator = false;
                                formattedOffering.inScheduleIndicator = false;
                                formattedOffering.colorIndex = null;
                            });
                        });
                    }

                    $scope.availableRegGroups = regGroups;
                    $scope.aoMap = aoMap;

                    $scope.loading = false;     // Course done loading
                    $scope.course = result;

                    // have to call here because of "details" button
                    setCartIndicator();
                    setScheduleIndicator();

                    $scope.singleRegGroup = singleRegGroup();
                    $scope.updateAOStates();
                } else {
                    $scope.loading = false; // Course loaded
                    console.log('Course load completed but not the most recent, ignoring: "' + courseId + '" !== "' + lastCourseId + '"');
                }
            }, function(error) {
                $scope.loading = false;
                console.log('Error loading course: ', error);
            });
        }

        $scope.availableRegGroups = {};
        $scope.aoMap = {};
        $scope.selectedAOs = [];        // List of selected activity offerings by their type
        $scope.selectedRegGroup = null; // Handle on the selected reg group based on the selected AOs
        $scope.singleRegGroup = false;  // Handle on whether we are displaying a single reg group or several
        $scope.additionalInfo = false;  // Handle on whether there are additional info icons to show for this course
        $scope.singleRegFormatted = [];  // If the course a single reg group, holds the formatted ao details

        $scope.clearSelectedAOs = function() {
            $scope.selectedRegGroup = null;
            $scope.selectedAOs = [];
            $scope.updateAOStates();

            clearActionBlock();
        };

        $scope.hasSelectedAOs = function() {
            return $scope.selectedAOs.length > 0;
        };

        $scope.isAOTypeSelected = function(aoType) {
            var selected = getSelectedAOByType(aoType);
            return selected !== null;
        };


        // Method for checking if the selected reg group is in the cart
        $scope.isSelectedRegGroupInCart = function() {
            return $scope.selectedRegGroup && CartService.isCourseInCart($scope.selectedRegGroup.regGroupId);
        };

        // Method for checking if the selected reg group is in the waitlist
        $scope.isSelectedRegGroupInWaitlist = function() {
            return $scope.selectedRegGroup && ScheduleService.isCourseWaitlisted($scope.selectedRegGroup.regGroupId);
        };


        // Perform the Add to Cart action when that button is clicked
        $scope.addToCart = function() {
            if (!$scope.selectedRegGroup) {
                blockAction();
                return;
            }

            // Re-using "Add to Cart" functionality from cart controller
            performAction('cart', 'addCourseToCart');
        };

        // Perform the Direct Register action when that button is clicked
        $scope.directRegister = function() {
            if (!$scope.selectedRegGroup) {
                blockAction();
                return;
            }

            // Broadcast an directRegisterForCourse event that is caught in the cart-controller.js controller
            performAction('register', 'directRegisterForCourse');
        };

        // Perform the Add to Waitlist action when that button is clicked
        $scope.addToWaitlist = function() {
            // Only allow direct add to waitlist if it is enabled & the waitlist is available for this RG.
            if (!$scope.selectedRegGroup || !$scope.selectedRegGroup.isWaitlistAvailable) {
                blockAction();
                return;
            }

            // Set the allowWaitlist flag = true to bypass the waitlist warnings
            $scope.course.allowWaitlist = true;

            // Broadcast an directRegisterForCourse event that is caught in the cart-controller.js controller
            performAction('waitlist', 'directRegisterForCourse');
        };

        // Helper method for updating the $scope when an action is blocked
        var blockTimeout = null;
        function blockAction() {
            // Cancel the old timer
            if (blockTimeout) {
                $timeout.cancel(blockTimeout);
                blockTimeout = null;
            }

            $scope.actionWarning = true;
            $scope.actionBlocked = true;

            // This is a kluge for managing the transitions. Ideally, this would be done outside of the JS.
            blockTimeout = $timeout(function() {
                $scope.actionBlocked = false;
            }, 2000);
        }

        function clearActionBlock() {
            $scope.actionWarning = false;
            $scope.actionBlocked = false;
        }

        // Helper method for performing the action (addToCart, directRegister, addToWaitlist)
        function performAction(actionType, eventAction) {
            clearActionBlock();

            $scope.actionType = actionType;
            $scope.actionStatus = STATUS.processing;

            var course = angular.copy($scope.course);
            course.courseCode = course.courseOfferingCode;
            course.longName = course.courseOfferingLongName;
            course.regGroupId = $scope.selectedRegGroup.regGroupId;
            course.regGroupCode = $scope.selectedRegGroup.regGroupCode;
            course.sourceAction = actionType; // Put the action type into the course so it can be accessed by the direct register system

            // Pin the selected RG's AOs in the schedule format on the course object
            course.activityOfferings = $scope.selectedAOs; // Selected AOs should be the exhaustive list of AOs for this RG

            // Broadcast the event that is caught and handled elsewhere (cart-controller.js controller)
            // cart-controller.js controller puts the promise from the objects onto the event object.
            var event = $rootScope.$broadcast(eventAction, course);
            event.promise.then(function() {
                $scope.actionStatus = STATUS.success;
            }, function(reason) {
                // Bubble up the error if there was one
                if (reason && reason !== 'cancel' && reason !== 'escape key press') { // 'cancel' comes when the Direct Reg modal is canceled/closed
                    $scope.actionStatus = STATUS.error;
                } else {
                    $scope.removeActionMessage();
                }
            });
        }

        // Clear out the action message when the remove button is clicked (X)
        $scope.removeActionMessage = function() {
            $scope.actionType = null;
            $scope.actionStatus = null;
        };


        $scope.$on('showSubterm', function(event, aoId) {
            var subterm = $scope.aoMap[aoId].subterm;
            $modal.open({
                templateUrl: 'partials/additionalInfo.html',
                controller: 'AdditionalInfoCtrl',
                size: 'sm',
                resolve: {
                    subterm: function() {
                        return subterm;
                    },
                    requisites: undefined
                }
            });
        });

        $scope.$on('showRequisites', function(event, aoId) {
            var requisites = $scope.aoMap[aoId].requisites;
            $modal.open({
                templateUrl: 'partials/additionalInfo.html',
                controller: 'AdditionalInfoCtrl',
                size: 'sm',
                resolve: {
                    requisites: function() {
                        return requisites;
                    },
                    subterm: undefined
                }
            });
        });


        // Event fired from search results table when a row is clicked.
        // Takes care of selecting/unselecting AOs and display depending on Reg Groups for selected AOs.
        // When AO is selected we only want to display AOs that are part of Reg Groups for selected one.
        // When several AOs selected such as they build a Reg Group -> passing ID and Code for this Reg Group on UI
        $scope.$on('toggleAO', function (event, formattedOffering) {

            var ao = $scope.aoMap[formattedOffering.aoId];

            var selected = false;
            if (isAOSelected(ao)) {
                deselectAO(ao);
            } else if (isAOCompatible(ao)) {
                selectAO(ao);
                selected = true;
            } else {
                // Don't continue since nothing was done
                return;
            }

            // Re-filter the AOs
            $scope.updateAOStates();

            if (selected) {
                // Auto-select any eligible AOs only when it was selected.
                // This allows the only AO in a list to be deselected.
                autoSelectEligibleAOs();
            }

            // Check if we have reg group
            $scope.selectedRegGroup = checkForSelectedRegGroup();
        });

        /**
         * Tally the number of eligible activity offerings in a list
         *
         * @param aos list of activity offerings
         * @returns number of eligible activity offerings (based on registration group compatibility)
         */
        $scope.countEligibleAOs = function(aos) {
            var eligible = 0;
            angular.forEach(aos, function(ao) {
                if (!ao.flags.disabled && !ao.flags.selected) {
                    eligible++;
                }
            });

            return eligible;
        };

        /**
         * Method for determining which activity offerings to show in the table
         */
        $scope.updateAOStates = function() {
            if (angular.isArray($scope.course.activityOfferingTypes)) {
                for (var i = 0; i < $scope.course.activityOfferingTypes.length; i++) {
                    var aoType = $scope.course.activityOfferingTypes[i];
                    if (aoType.showAll && $scope.isAOTypeSelected(aoType)) {
                        // Reset the showAll if this AOType is selected
                        aoType.showAll = false;
                    }

                    for (var j = 0; j < aoType.activityOfferings.length; j++) {
                        var ao = aoType.activityOfferings[j];

                        ao.flags.selected = isAOSelected(ao);
                        ao.flags.disabled = !isAOCompatible(ao); // AO is disabled if it is not compatible with the selected AOs

                        if (aoType.showAll || ao.flags.selected) {
                            // Show All || this AO is selected
                            ao.flags.hidden = false;
                        } else if (!$scope.isAOTypeSelected(aoType)) {
                            // This AO Type is not selected, hide if not compatible
                            ao.flags.hidden = ao.flags.disabled;
                        } else {
                            ao.flags.hidden = true;
                        }
                    }
                }
            }
        };

        /*
        If:
        -- the available reg groups has changed
        -- a regGroupId is defined in the url
        -- no other reg group is selected
        Then select the reg group (if it exists and is selectable)
         */
        $scope.$watch('availableRegGroups', function() {
            if (angular.isDefined($scope.availableRegGroups) &&
                angular.isDefined($scope.stateParams.regGroupId) &&
                (angular.isUndefined($scope.selectedRegGroup) || $scope.selectedRegGroup === null)) {

                var regGroupId = $scope.stateParams.regGroupId,
                    selectableRegGroups = getSelectableRegGroups();

                if (angular.isDefined(selectableRegGroups[regGroupId])) {
                    var rg = selectableRegGroups[regGroupId];

                    for (var i = 0; i < rg.activityOfferingIds.length; i++) {
                        for (var aoId in $scope.aoMap) {
                            var ao = $scope.aoMap[aoId];
                            if (ao.activityOfferingId === rg.activityOfferingIds[i]) {
                                selectAO(ao);
                            }
                        }
                    }

                    $scope.updateAOStates();
                    $scope.selectedRegGroup = calculateCapacityForRegGroup(rg);
                }
            }
        });

        /*
        Watch registered and waitlisted courses in addition to the ao map.

        If anything changes, check for time conflict between the activities and your schedule.
         */

        $scope.$watch('registered', function() {
            updateTimeConflicts();
        }, true);

        $scope.$watch('waitlisted', function() {
            updateTimeConflicts();
        }, true);

        $scope.$watch('aoMap', function() {
            updateTimeConflicts();
        });

        var updatingTimeConflicts = false; // time conflict update lock

        function updateTimeConflicts() {
            if (!updatingTimeConflicts) {
                updatingTimeConflicts = true;
                // clear existing conflicts
                for (var aoId in $scope.aoMap) {
                    delete $scope.aoMap[aoId].flags.highlight;
                }
                for (aoId in $scope.aoMap) {
                    var ao = $scope.aoMap[aoId];
                    if (ScheduleService.hasTimeConflict(ao) === true) {
                        ao.flags.highlight = true;
                    }
                }
                updatingTimeConflicts = false;
            }
        }

        /* Watch the cart and registered courses and show/hide the in-cart and in-schedule indicators */
        $scope.$watch('registered', function(newValue, oldValue) {
            if (newValue !== oldValue) {
                setScheduleIndicator();
            }
        }, true);

        $scope.$watchCollection('unusedCart', setCartIndicator);

        function setCartIndicator () {
            if ($scope.course !== null) {
                var aoTypes = $scope.course.activityOfferingTypes;
                for (var i = 0; i < aoTypes.length; i++) {
                    for (var j = 0; j < aoTypes[i].formattedOfferings.length; j++) {
                        var cartIndicator = CartService.isAoInCart(aoTypes[i].formattedOfferings[j].activityOfferingId);
                        if (angular.isDefined(cartIndicator)) {
                            aoTypes[i].formattedOfferings[j].inCartIndicator = cartIndicator.flag;
                            aoTypes[i].formattedOfferings[j].colorIndex = cartIndicator.colorIndex;
                        } else {
                            aoTypes[i].formattedOfferings[j].inCartIndicator = false;
                            if (!aoTypes[i].formattedOfferings[j].inScheduleIndicator) {
                                aoTypes[i].formattedOfferings[j].colorIndex = null;
                            }
                        }
                    }
                }
                $scope.course.activityOfferingTypes = aoTypes;
            }
        }

        function setScheduleIndicator () {
            if ($scope.course !== null) {
                var aoTypes = $scope.course.activityOfferingTypes;
                for (var i = 0; i < aoTypes.length; i++) {
                    for (var j = 0; j < aoTypes[i].formattedOfferings.length; j++) {
                        var scheduleIndicator = ScheduleService.isAoInSchedule(aoTypes[i].formattedOfferings[j].activityOfferingId);
                        if (angular.isDefined(scheduleIndicator)) {
                            aoTypes[i].formattedOfferings[j].inScheduleIndicator = scheduleIndicator.flag;
                            aoTypes[i].formattedOfferings[j].colorIndex = scheduleIndicator.colorIndex;
                        } else {
                            aoTypes[i].formattedOfferings[j].inScheduleIndicator = false;
                            if (!aoTypes[i].formattedOfferings[j].inCartIndicator) {
                                aoTypes[i].formattedOfferings[j].colorIndex = null;
                            }
                        }
                    }
                }
                $scope.course.activityOfferingTypes = aoTypes;
            }
        }

        /**
         * Method for determining how many possible reg groups are available --
         * if only one reg group is available, select all aos automatically and
         * return true. Otherwise, select nothing and return false.
         */
        function singleRegGroup() {
            var keys = Object.keys($scope.availableRegGroups);
            if (keys.length === 1) {
                var singleRegFormatted = [];
                angular.forEach($scope.course.activityOfferingTypes, function(aoType) {
                    angular.forEach(aoType.activityOfferings, function(ao) {
                        selectAO(ao, true);
                    });
                    singleRegFormatted.push.apply(singleRegFormatted, aoType.formattedOfferings);
                });

                $scope.selectedRegGroup = checkForSelectedRegGroup();
                $scope.singleRegFormatted = singleRegFormatted;

                return true;
            }

            return false;
        }

        /**
         * Auto-select the an activity offering when there is only 1 eligible option within its type
         */
        function autoSelectEligibleAOs() {
            angular.forEach($scope.course.activityOfferingTypes, function(aoType) {
                // If there is only 1 eligible AO, auto-select it
                if (!$scope.isAOTypeSelected(aoType) && $scope.countEligibleAOs(aoType.activityOfferings) === 1) {
                    angular.forEach(aoType.activityOfferings, function(ao) {
                        if (!ao.flags.selected && !ao.flags.disabled) {
                            selectAO(ao);
                            $scope.updateAOStates();
                        }
                    });
                }
            });
        }

        /**
         * Calculate whether the reg group is full & the waitlist capacity values for a reg group.
         *
         * @param regGroup
         * @returns regGroup
         */
        function calculateCapacityForRegGroup(regGroup) {
            if (angular.isDefined(regGroup.isFull)) {
                // Already calculated
                return regGroup;
            }

            // A reg group is full if any of its activity offerings has 0 seats available
            regGroup.isFull = false;
            angular.forEach($scope.course.activityOfferingTypes, function(aoType) {
                if (!regGroup.isFull) {
                    angular.forEach(aoType.activityOfferings, function(ao) {
                        if (!regGroup.isFull && regGroup.activityOfferingIds.indexOf(ao.activityOfferingId) !== -1 && ao.seatsOpen <= 0) {
                            regGroup.isFull = true;
                        }
                    });
                }
            });

            // A waitlist is full if: (there is a max && all seats are taken)
            regGroup.isWaitlistFull = (angular.isNumber(regGroup.maxWaitListSize) && regGroup.waitListSize >= regGroup.maxWaitListSize);

            // A waitlist is available if: (it is offered && not full)
            regGroup.isWaitlistAvailable = regGroup.waitListOffered && !regGroup.isWaitlistFull;

            return regGroup;
        }

        /**
         * Method for identifying the selected reg group based on the selected activity offerings.
         *
         * @returns regGroup registration group {id, code}
         */
        function checkForSelectedRegGroup() {
            // A reg group is only capable of being selected when each AO type has a selected AO
            if ($scope.selectedAOs.length === $scope.course.activityOfferingTypes.length) {
                var candidates = getSelectableRegGroups();
                if (candidates.length === 1) {
                    return calculateCapacityForRegGroup(candidates[0]);
                }
            }

            return null;
        }

        /**
         * Helper method to get the selected activity offering matching a given type
         *
         * @param aoType type of activity offering
         */
        function getSelectedAOByType(aoType) {
            // Allow a string value or the object itself to be passed in
            if (angular.isObject(aoType) && angular.isDefined(aoType.activityOfferingType)) {
                aoType = aoType.activityOfferingType;
            }

            var selected = null;
            angular.forEach($scope.selectedAOs, function(ao) {
                if (selected === null && ao.activityOfferingType === aoType) {
                    selected = ao;
                }
            });

            return selected;
        }

        /**
         * Get the list of possible registration groups based on the currently selected registration groups
         *
         * @returns array of registration groups [{id, code}]
         */
        function getSelectableRegGroups() {
            var candidates = $scope.availableRegGroups;
            angular.forEach($scope.selectedAOs, function(ao) {
                // Init the reg group candidates with the first AO's reg groups
                if (candidates === null) {
                    candidates = [];
                    for (var key in ao.regGroupInfos) {
                        candidates.push($scope.availableRegGroups[key]);
                    }
                } else {
                    // Eliminate any reg groups from the candidates that don't exist in the current ao
                    var newCandidates = [];
                    angular.forEach(candidates, function(candidate) {
                        if (angular.isDefined(ao.regGroupInfos[candidate.regGroupId])) {
                            newCandidates.push(candidate); // Candidate is still valid, carry it over.
                        }
                    });
                    candidates = newCandidates;
                }
            });

            return candidates || [];
        }

        /**
         * Helper method for determining if an activity offering is
         * compatible with the reg groups of the already selected offerings
         *
         * @param ao
         * @returns {boolean}
         */
        function isAOCompatible(ao) {
            var compatible = true;

            // Only capable of being incompatible if there are selectedAOs and it's not one of them
            if (!isAOSelected(ao) && $scope.hasSelectedAOs()) {
                compatible = false;

                var regGroups = getSelectableRegGroups();
                for (var i = 0; i < regGroups.length; i++) {
                    if (angular.isDefined(ao.regGroupInfos[regGroups[i].regGroupId])) {
                        compatible = true;
                        break;
                    }
                }
            }

            return compatible;
        }

        /**
         * Determine if an AO is selected or not
         * @param ao
         * @returns {boolean}
         */
        function isAOSelected(ao) {
            return $scope.selectedAOs.indexOf(ao) !== -1;
        }

        /**
         * Deselect an activity offering
         * @param ao
         */
        function deselectAO(ao) {
            $scope.selectedAOs.splice($scope.selectedAOs.indexOf(ao), 1);
            ao.flags.selected = false;
        }

        /**
         * Select an activity offering, removing the previously selected offering of the same type
         * @param ao
         * @param suppressSelection true to suppress setting the AO as selected in the view
         */
        function selectAO(ao, suppressSelection) {
            // Deselect the already selected ao of this type
            var selected = getSelectedAOByType(ao.activityOfferingType);
            if (selected !== null) {
                deselectAO(selected);
            }

            // Select the AO
            $scope.selectedAOs.push(ao);

            if (!(suppressSelection || false)) {
                ao.flags.selected = true;
            }
        }

    }])

    .controller('AdditionalInfoCtrl', ['$scope', 'subterm', 'requisites', function($scope, subterm, requisites) {
        $scope.subterm = subterm;
        $scope.requisites = requisites;

        $scope.startDate = function() {
            return parseDate(subterm.startDate);
        };

        $scope.endDate = function() {
            return parseDate(subterm.endDate);
        };

        /*
        Parses a millisecond date into MM/DD/YYYY
         */
        function parseDate(millis) {
            var date=new Date(millis);
            return date.getMonth()+1 + '/' + date.getDate() + '/' +date.getFullYear();
        }
    }])

;