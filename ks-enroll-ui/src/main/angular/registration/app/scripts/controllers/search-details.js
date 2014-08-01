'use strict';

angular.module('regCartApp')
    .controller('SearchDetailsCtrl', ['$scope', '$rootScope', '$state', '$filter', '$modal', 'SearchService',
    function SearchDetailsCtrl($scope, $rootScope, $state, $filter, $modal, SearchService) {

        $scope.searchCriteria = null; // Criteria used to generate the search results.
        $scope.course = null; // Handle on the course

        // Push the user back to the search page when the term is changed
        $scope.$on('termIdChanged', function(event, newValue, oldValue) {
            if (oldValue !== null && $scope.searchCriteria && $scope.uiState === 'root.search.details') {
                $state.goToPage('root.search.results', { searchCriteria: $scope.searchCriteria });
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

                    // Give each activity offering a handle on its type.
                    // This facilitates managing the selected offerings.
                    if (angular.isDefined(result.activityOfferingTypes) && angular.isArray(result.activityOfferingTypes)) {
                        angular.forEach(result.activityOfferingTypes, function(aoType) {
                            angular.forEach(aoType.activityOfferings, function(ao) {
                                ao.activityOfferingType = aoType.activityOfferingType;
                            });
                        });
                    }

                    $scope.course = result;
                    $scope.updateAOStates();
                } else {
                    console.log('Course load completed but not the most recent, ignoring: "' + courseId + '" !== "' + lastCourseId + '"');
                }
            }, function(error) {
                console.log('Error loading course: ', error);
            });
        }


        $scope.selectedAOs = []; // List of selected activity offerings by their type
        $scope.selectedRegGroup = null; // Handle on the selected reg group based on the selected AOs
        $scope.selectedRegGroupId = null;
        $scope.selectedRegGroupCode = null;

        $scope.clearSelectedAOs = function() {
            $scope.selectedRegGroup = null;
            $scope.selectedRegGroupId = null;
            $scope.selectedRegGroupCode = null;

            $scope.selectedAOs = [];
            $scope.updateAOStates();
        };

        // Re-using "Add to Cart" functionality from cart controller
        $scope.addRegGroupIdToCart = function () {
            $rootScope.$broadcast('addRegGroupIdToCart', $scope.selectedRegGroupId);
        };

        $scope.hasSelectedAOs = function() {
            return $scope.selectedAOs.length > 0;
        };

        $scope.isAOSelected = function(ao) {
            return $scope.selectedAOs.indexOf(ao) !== -1;
        };

        $scope.isAOTypeSelected = function(aoType) {
            var selected = getSelectedAOByType(aoType);
            return selected !== null;
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
            })
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
            })
        });

        $scope.$on('toggleAO', function (event, ao) {
            $scope.toggleAO(ao.activityOfferingType, ao);
        });

        // takes care of selecting/unselecting AOs and display depending on Reg Groups for selected AOs.
        // When AO is selected we only want to display AOs that are part of Reg Groups for selected one.
        // When several AOs selected such as they build a Reg Group -> passing ID and Code for this Reg Group on UI
        $scope.toggleAO = function(aoType, ao) {
            if ($scope.isAOSelected(ao)) {
                // Deselect the AO
                $scope.selectedAOs.splice($scope.selectedAOs.indexOf(ao), 1);
            } else if (isAOCompatible(ao)) {
                if ($scope.isAOTypeSelected(aoType)) {
                    // Deselect the already selected ao of this type
                    var selected = getSelectedAOByType(aoType);
                    $scope.selectedAOs.splice($scope.selectedAOs.indexOf(selected), 1);
                }

                // Select the AO
                $scope.selectedAOs.push(ao);
            }

            // Re-filter the AOs
            $scope.updateAOStates();

            // Check if we have reg group
            $scope.selectedRegGroup = null;
            $scope.selectedRegGroupId = null;
            $scope.selectedRegGroupCode = null;

            var selectedRegGroup = checkForSelectedRegGroup();
            if (selectedRegGroup) {
                $scope.selectedRegGroup = true;
                $scope.selectedRegGroupId = selectedRegGroup.id;
                $scope.selectedRegGroupCode = selectedRegGroup.code;
            }
        };

        $scope.countEligibleAOs = function(aos) {
            var eligible = 0;
            angular.forEach(aos, function(ao) {
                if (!ao.disabled) {
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
                    ao.selected = $scope.isAOSelected(ao);
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
         * Method for identifying the selected reg group based on the selected activity offerings.
         *
         * @returns registration group {id, code}
         */
        function checkForSelectedRegGroup() {
            var candidates = getSelectableRegGroups();
            if (candidates.length === 1) {
                return candidates[0];
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
            var candidates = null;
            angular.forEach($scope.selectedAOs, function(ao) {
                // Init the reg group candidates with the first AO's reg groups
                if (candidates === null) {
                    candidates = [];
                    for (var key in ao.regGroupInfos) {
                        candidates.push({id: key, code: ao.regGroupInfos[key]});
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
            if (!$scope.isAOSelected(ao) && $scope.hasSelectedAOs()) {
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