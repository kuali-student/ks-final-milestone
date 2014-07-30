'use strict';

angular.module('regCartApp')
    .controller('SearchDetailsCtrl', ['$scope', '$state', '$filter', 'SearchService', function SearchDetailsCtrl($scope, $state, $filter, SearchService) {

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

        $scope.$watchCollection('selectedAOs', function(aos) {
            // TODO: build out selected reg group
        });


        $scope.clearSelectedAOs = function() {
            $scope.selectedRegGroup = null;
            $scope.selectedRegGroupId = null;
            $scope.selectedRegGroupCode = null;
            angular.forEach($scope.selectedAOs, function (ao) {
                ao.selected = false;
            });
            $scope.selectedAOs = [];
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

        $scope.$on('toggleAO', function (event, searchResult) {
            $scope.toggleAO(searchResult.ao.activityOfferingType, searchResult.ao, $scope.course);
        });

        $scope.toggleAO = function(aoType, ao, course) {
            $scope.selectedRegGroup = null;
            $scope.selectedRegGroupId = null;
            $scope.selectedRegGroupCode = null;

            if ($scope.isAOSelected(ao)) {
                // Deselect the AO
                $scope.selectedAOs.splice($scope.selectedAOs.indexOf(ao), 1);
            } else {
                if ($scope.isAOTypeSelected(aoType)) {
                    // Deselect the already selected ao of this type
                    var selected = getSelectedAOByType(aoType);
                    $scope.selectedAOs.splice($scope.selectedAOs.indexOf(selected), 1);
                }

                // Select the AO
                $scope.selectedAOs.push(ao);
            }

            // only want to display AOs that form reg groups with the selected AOs
            angular.forEach(course.activityOfferingTypes, function(aoType) {
                if (!$scope.isAOTypeSelected(aoType)) {
                    angular.forEach(aoType.activityOfferings, function(aoDiff) {
                            aoDiff.isSameRegGroup == false;
                            for (var keyDiff in aoDiff.regGroupInfos) {
                                var cnt = 0;
                                angular.forEach($scope.selectedAOs, function(selectedAO) {
                                    for (var key in selectedAO.regGroupInfos) {
                                        if ((keyDiff == key) && ((cnt == 0) || ((cnt > 0) && aoDiff.isSameRegGroup == true))) {
                                            aoDiff.isSameRegGroup = true;
                                            break;
                                        } else {
                                            aoDiff.isSameRegGroup = false;
                                        }
                                    }
                                    cnt++;
                                });
                                if (aoDiff.isSameRegGroup == true) {
                                    break;
                                }
                            }
                        }
                    );
                }
            });

            // Check if we have reg group
            if ($scope.selectedAOs.length == course.activityOfferingTypes.length) {
                var regGroups = {};
                var cnt = 0;
                angular.forEach($scope.selectedAOs, function(selectedAO) {
                    var selectedAORGcnt = 0;
                    for (var key in selectedAO.regGroupInfos) selectedAORGcnt++;

                    if ((selectedAORGcnt == 1) && ((cnt == 0) || ($scope.selectedRegGroup !== true))) {
                        for (var key in selectedAO.regGroupInfos) {
                            $scope.selectedRegGroup = true;
                            $scope.selectedRegGroupId = key;
                            $scope.selectedRegGroupCode = selectedAO.regGroupInfos[key];
                        }
                    } else if ((selectedAORGcnt !== 1) && ($scope.selectedRegGroup !== true)) {
                        if (cnt == 0) {
                            for (var key in selectedAO.regGroupInfos) {
                                regGroups[key] = selectedAO.regGroupInfos[key];
                            }
                        } else {
                            for (var rgKey in regGroups) {
                                var checkRgKey = false;
                                for (var key in selectedAO.regGroupInfos) {
                                    if (rgKey == key) {
                                        checkRgKey = true;
                                        break;
                                    }
                                }
                                if (!checkRgKey) {
                                    delete regGroups[rgKey];
                                }
                            }
                        }

                        var regGroupsCnt = 0;
                        for (var key in regGroups) regGroupsCnt++;
                        if (regGroupsCnt == 1) {
                            for (var rgKey in regGroups) {
                                $scope.selectedRegGroup = true;
                                $scope.selectedRegGroupId = rgKey;
                                $scope.selectedRegGroupCode = regGroups[rgKey];
                            }
                        }
                    }
                    cnt++;
                });
            }
        };

        /**
         * Filter for determining which activity offerings to show in the table
         * @param ao the activity offering
         * @param showAll the showAll value scoped to this particular ao's aoType
         */
        $scope.aoFilter = function(ao, showAll) {
            if (showAll || $scope.isAOSelected(ao)) {
                return true;
            }

            // This would be where the reg-group eligibility check would go for this ao

            // Otherwise, hide everything only the activity offering type is selected
            return !$scope.isAOTypeSelected(ao.activityOfferingType);
        };

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

    }]);