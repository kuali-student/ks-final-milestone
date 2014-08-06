'use strict';

angular.module('regCartApp')
    .controller('SearchDetailsCtrl', ['$scope', '$rootScope', '$state', '$filter', '$modal', 'STATUS', 'FEATURE_TOGGLES', 'SearchService', 'GlobalVarsService',
    function SearchDetailsCtrl($scope, $rootScope, $state, $filter, $modal, STATUS, FEATURE_TOGGLES, SearchService, GlobalVarsService) {

        $scope.searchCriteria = null; // Criteria used to generate the search results.
        $scope.course = null;         // Handle on the course
        $rootScope.hideForm = true;   // Hides the search form in mobile view

        // Push the user back to the search page when the term is changed
        $scope.$on('termIdChanged', function(event, newValue, oldValue) {
            if (oldValue !== null && $scope.searchCriteria && $scope.uiState === 'root.search.details') {
                $state.go('root.search.results', { searchCriteria: $scope.searchCriteria });
            }
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria & course ID from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria)) {
                $scope.searchCriteria = toParams.searchCriteria;
            }

            if (angular.isDefined(toParams.id)) {
                loadCourse(toParams.id);
            }
        });


        var lastCourseId = null;
        function loadCourse(courseId) {
            if (courseId === null || ($scope.course !== null && $scope.course.courseId !== null && courseId === $scope.course.courseId)) {
                // Don't load a null courseId or the same course we already have.
                return;
            }


            console.log('Loading course "' + courseId + '"');

            lastCourseId = courseId;
            SearchService.getCourse().query({courseOfferingId: courseId}, function(result) {
                if (lastCourseId === courseId) {
                    // This query matches the last one ran - it's current.

                    var regGroups = {};
                    if (angular.isDefined(result.activityOfferingTypes) && angular.isArray(result.activityOfferingTypes)) {
                        angular.forEach(result.activityOfferingTypes, function(aoType) {

                            // Apply aoFormatter filter to the activity offerings for display in the table
                            $filter('aoFormatter')(aoType.activityOfferings);

                            angular.forEach(aoType.activityOfferings, function(ao) {

                                // Give each activity offering a handle on its type.
                                // This facilitates managing the selected offerings.
                                ao.activityOfferingType = aoType.activityOfferingType;

                                // Transform the reg groups to be more easily consumed
                                angular.forEach(ao.regGroupInfos, function(code, id) {
                                    if (angular.isUndefined(regGroups[id])) {
                                        regGroups[id] = {
                                            id: id,
                                            code: code,
                                            aos: []
                                        };
                                    }

                                    // Give each reg group a handle on the activity offerings that compose it.
                                    // This facilitates calculating if the waitlist is available.
                                    regGroups[id].aos.push(ao);
                                });

                                // Check to see if there are any additional info icons to display for this course
                                if (!$scope.additionalInfo && (ao.subterm != null || (angular.isArray(ao.requisites) && ao.requisites.length > 0))) {
                                    $scope.additionalInfo = true;
                                }
                            });
                        });
                    }

                    $scope.availableRegGroups = regGroups;
                    $scope.course = result;

                    $scope.updateAOStates();
                    $scope.singleRegGroup = singleRegGroup();
                } else {
                    console.log('Course load completed but not the most recent, ignoring: "' + courseId + '" !== "' + lastCourseId + '"');
                }
            }, function(error) {
                console.log('Error loading course: ', error);
            });
        }


        $scope.availableRegGroups = {};
        $scope.selectedAOs = [];        // List of selected activity offerings by their type
        $scope.selectedRegGroup = null; // Handle on the selected reg group based on the selected AOs
        $scope.singleRegGroup = false;  // Handle on whether we are displaying a single reg group or several
        $scope.additionalInfo = false;  // Handle on whether there are additional info icons to show for this course

        $scope.clearSelectedAOs = function() {
            $scope.selectedRegGroup = null;
            $scope.selectedAOs = [];
            $scope.updateAOStates();
        };

        $scope.hasSelectedAOs = function() {
            return $scope.selectedAOs.length > 0;
        };

        $scope.isAOTypeSelected = function(aoType) {
            var selected = getSelectedAOByType(aoType);
            return selected !== null;
        };


        // Check whether or not the Direct Add to Waitlist feature is enabled
        $scope.allowDirectAddToWaitlist = function() {
            return FEATURE_TOGGLES.searchDetails.directAddToWaitlist || false;
        };


        // Method for checking if the selected reg group is in the cart
        $scope.isSelectedRegGroupInCart = function() {
            return $scope.selectedRegGroup && GlobalVarsService.isCourseInCart($scope.selectedRegGroup.id);
        };

        // Method for checking if the selected reg group is in the waitlist
        $scope.isSelectedRegGroupInWaitlist = function() {
            return $scope.selectedRegGroup && GlobalVarsService.isCourseWaitlisted($scope.selectedRegGroup.id);
        };


        // Perform the Add to Cart action when that button is clicked
        $scope.addToCart = function() {
            // Re-using "Add to Cart" functionality from cart controller
            performAction('cart', 'addCourseToCart');
        };

        // Perform the Add to Waitlist action when that button is clicked
        $scope.addToWaitlist = function() {
            // Only allow direct add to waitlist if it is enabled & the waitlist is available for this RG.
            if (!$scope.selectedRegGroup || !$scope.selectedRegGroup.isWaitlistAvailable || !$scope.allowDirectAddToWaitlist()) {
                return;
            }

            // Broadcast an directRegisterForCourse event that is caught in the cart.js controller
            performAction('waitlist', 'registerForCourse');
        };

        // Helper method for performing the action (addToCart or addToWaitlist)
        function performAction(actionType, event) {
            if (!$scope.selectedRegGroup) {
                return;
            }

            $scope.actionType = actionType;
            $scope.actionStatus = null;

            var course = angular.copy($scope.course);
            course.courseCode = course.courseOfferingCode;
            course.regGroupId = $scope.selectedRegGroup.id;
            course.regGroupCode = $scope.selectedRegGroup.code;

            // Broadcast the event that is caught and handled elsewhere (cart.js controller)
            $rootScope.$broadcast(event, course, function() {
                $scope.actionStatus = STATUS.success;
            }, function() {
                $scope.actionStatus = STATUS.error;
            });
        }

        // Clear out the action message when the remove button is clicked (X)
        $scope.removeActionMessage = function() {
            $scope.actionType = null;
            $scope.actionStatus = false;
        };


        $scope.$on('showSubterm', function(event, subterm) {
            $modal.open({
                templateUrl: 'partials/additionalInfo.html',
                controller: 'AdditionalInfoCtrl',
                size: 'sm',
                resolve: {subterm: function() {
                    return subterm;
                },
                requisites: undefined
                }
            });
        });

        $scope.$on('showRequisites', function(event, requisites) {
            $modal.open({
                templateUrl: 'partials/additionalInfo.html',
                controller: 'AdditionalInfoCtrl',
                size: 'sm',
                resolve: {requisites: function() {
                    return requisites;
                },
                subterm: undefined
                }
            });
        });


        // Event fired from search results table when a row is selected.
        $scope.$on('toggleAO', function (event, ao) {
            $scope.toggleAO(ao.activityOfferingType, ao);
        });

        // Takes care of selecting/unselecting AOs and display depending on Reg Groups for selected AOs.
        // When AO is selected we only want to display AOs that are part of Reg Groups for selected one.
        // When several AOs selected such as they build a Reg Group -> passing ID and Code for this Reg Group on UI
        $scope.toggleAO = function(aoType, ao) {
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
        };

        /**
         * Tally the number of eligible activity offerings in a list
         *
         * @param aos list of activity offerings
         * @returns number of eligible activity offerings (based on registration group compatibility)
         */
        $scope.countEligibleAOs = function(aos) {
            var eligible = 0;
            angular.forEach(aos, function(ao) {
                if (!ao.disabled && !ao.selected) {
                    eligible++;
                }
            });

            return eligible;
        };

        /**
         * Method for determining which activity offerings to show in the table
         */
        $scope.updateAOStates = function() {
            angular.forEach($scope.course.activityOfferingTypes, function(aoType) {
                if (aoType.showAll && $scope.isAOTypeSelected(aoType)) {
                    // Reset the showAll if this AOType is selected
                    aoType.showAll = false;
                }

                angular.forEach(aoType.activityOfferings, function(ao) {
                    ao.selected = isAOSelected(ao);
                    ao.disabled = !isAOCompatible(ao); // AO is disabled if it is not compatible with the selected AOs

                    if (aoType.showAll || ao.selected) {
                        // Show All || this AO is selected
                        ao.hidden = false;
                    } else if (!$scope.isAOTypeSelected(aoType)) {
                        // This AO Type is not selected, hide if not compatible
                        ao.hidden = ao.disabled;
                    } else {
                        ao.hidden = true;
                    }
                });
            });
        };

        /**
         * Method for determining how many possible reg groups are available --
         * if only one reg group is available, select all aos automatically and
         * return true. Otherwise, select nothing and return false.
         */
        function singleRegGroup() {
            var keys = Object.keys($scope.availableRegGroups);
            if (keys.length === 1) {
                angular.forEach($scope.availableRegGroups[keys[0]].aos, function(ao) {
                    selectAO(ao, true);
                });

                $scope.selectedRegGroup = checkForSelectedRegGroup();
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
                        if (!ao.selected && !ao.disabled) {
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

            regGroup.isFull = false; // Any AO in the RG is full (no seats open)
            regGroup.isWaitlistOffered = true; // Waitlist is offered    TODO: implement actual logic for calculating whether a waitlist is offered, probably get from CO
            regGroup.isWaitlistAvailable = false; // Aggregate check whether waitlist is offered and not full
            regGroup.isWaitlistFull = false; // Waitlist is full (seats taken >= seats available)
            regGroup.waitlistSeatsAvailable = null; // Max size of waitlist
            regGroup.waitlistSeatsTaken = 0; // # seats taken already on waitlist

            // A reg group is full if any of its activity offerings has 0 seats available
            angular.forEach(regGroup.aos, function(ao) {
                if (!regGroup.isFull && ao.seatsOpen <= 0) {
                    regGroup.isFull = true;
                }


                // Calculate Seats Available on this RG's waitlist
                if (ao.seatsOpen <= 0 && angular.isNumber(ao.maxWaitListSize)) { // This AO's max waitlist size is only relevant if it has no seats open
                    if (angular.isNumber(regGroup.waitlistSeatsAvailable) && regGroup.waitlistSeatsAvailable > 0) {
                        // The seats available is equal to the smallest max waitlist size of the AOs in this RG
                        regGroup.waitlistSeatsAvailable = Math.min(regGroup.waitlistSeatsAvailable, ao.maxWaitListSize);
                    } else {
                        // No max has been identified yet, default to this AOs.
                        regGroup.waitlistSeatsAvailable = ao.maxWaitListSize;
                    }
                }

                // Calculate Seats Taken on this RG's waitlist
                // The seats taken is equal to the max waitlist size of the AOs in this RG
                regGroup.waitlistSeatsTaken = Math.max(regGroup.waitlistSeatsTaken, (ao.waitListSize || 0));
            });

            // A waitlist is full if: (there is a max && all seats are taken)
            regGroup.isWaitlistFull = (angular.isNumber(regGroup.waitlistSeatsAvailable) && regGroup.waitlistSeatsTaken >= regGroup.waitlistSeatsAvailable);

            // A waitlist is available if: (it is offered && not full)
            regGroup.isWaitlistAvailable = regGroup.isWaitlistOffered && !regGroup.isWaitlistFull;

            return regGroup;
        }

        /**
         * Method for identifying the selected reg group based on the selected activity offerings.
         *
         * @returns registration group {id, code}
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
            if (typeof aoType === 'object' && angular.isDefined(aoType.activityOfferingType)) {
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
                        if (angular.isDefined(ao.regGroupInfos[candidate.id])) {
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
                    if (angular.isDefined(ao.regGroupInfos[regGroups[i].id])) {
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
            ao.selected = false;
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

            if (!suppressSelection) {
                ao.selected = true;
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

    .directive('searchDetailsList', ['$timeout', function($timeout) {
        return {
            restrict: 'E',
            templateUrl: 'partials/searchDetailsList.html',
            scope: {
                searchDetails: '='
            },
            link:function(scope,element,attrs) {
                scope.time = true;
                scope.instr = false;
                scope.seatsLoc = false;
                scope.tab = 'time';

                scope.select = function(tab) {
                    scope.tab = tab;
                    switch(tab) {
                        case 'time':
                            scope.time = true;
                            scope.instr = false;
                            scope.seatsLoc = false;
                            scope.all = false;
                            break;
                        case 'instr':
                            scope.time = false;
                            scope.instr = true;
                            scope.seatsLoc = false;
                            scope.all = false;
                            break;
                        case 'seatsLoc':
                            scope.time = false;
                            scope.instr = false;
                            scope.seatsLoc = true;
                            scope.all = false;
                            break;
                        case 'all':
                            scope.time = true;
                            scope.instr = true;
                            scope.seatsLoc = true;
                            scope.all = true;
                    }
                };

                // Displays the table in batches for performance
                var stagger = 5;
                scope.limit = stagger;
                scope.$watch('limit', function() {
                    if (scope.limit < scope.searchDetails.length) {
                        $timeout(function() {
                            scope.limit += stagger;
                        }, 100)
                    }
                });
            }
        }
    }])
;