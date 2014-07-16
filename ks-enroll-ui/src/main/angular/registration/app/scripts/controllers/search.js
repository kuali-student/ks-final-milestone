'use strict';

angular.module('regCartApp')
    .controller('SearchCtrl', ['$scope', 'SearchService', 'SEARCH_FACETS', function SearchCtrl($scope, SearchService, SEARCH_FACETS) {

        $scope.facets = SEARCH_FACETS; // Facet definitions

        $scope.searchCriteria = null; // Criteria used to generate the search results.
        $scope.searchResults = []; // Results from the last search request.


        $scope.$on('termIdChanged', function(event, newValue, oldValue) {
            var criteria = $scope.searchCriteria;

            // Drop the existing search results when the term changes
            $scope.searchCriteria = '';
            $scope.searchResults = [];

            // Search for the old criteria under the new termId.
            if (criteria) {
                doSearch(criteria);
            }
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria)) {
                doSearch(toParams.searchCriteria);
            }
        });

        // the choices for limiting display of search results
        $scope.displayLimits = [20, 50, 100];
//        $scope.displayLimits = [2, 3, 5]; //uncomment this for testing pagination with small data sets

        // set the default display limit
        $scope.displayLimit = $scope.displayLimits[0];

        // set the default sort field
        $scope.predicate = 'courseCode';

        // set the default page #
        $scope.page = 1;

        // if the display limit changes, reset the page to 1
        $scope.$watch('displayLimit', function(newValue, oldValue) {
            $scope.page = 1;
        });

        // the range of search results being viewed
        $scope.displayRange = function(length) {
            var rangeMin = (($scope.page - 1) * $scope.displayLimit) + 1;
            var rangeMax = rangeMin + $scope.displayLimit - 1;
            if (rangeMax > length) {
                rangeMax = length;
            }
            if (rangeMin === rangeMax) {
                return (rangeMax + ' of ' + length);
            } else {
                return (rangeMin + '-' + rangeMax + ' of ' + length);
            }
        };

        // formats the credits for display
        $scope.displayCredits = function(creditOptions) {
            if (creditOptions.length === 1) {
                return parseFloat(creditOptions[0])+" cr";
            } else {
                var min = parseFloat(Math.min.apply(null, creditOptions));
                var max = parseFloat(Math.max.apply(null, creditOptions));
                return min + "-" + max + " cr";
            }
        };

        // Filter to apply facet options to the search results
        $scope.facetFilter = function(item) {
            var select = true;
            angular.forEach($scope.facets, function(facet) {
                if (select) {
                    // Only filter on the facet if it has selected options
                    if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
                        if (!facet.filter(item, facet.selectedOptions)) {
                            // The item does not match the facet filter. Exclude it.
                            select = false;
                        }
                    }
                }
            });

            return select;
        };

        var queuedSearchHandle, // Handle on the queued up search.
            lastSearchCriteria = ''; // Criteria used to execute the most recent search request.
        function doSearch(criteria) {
            if (criteria === null || ($scope.searchCriteria !== null && criteria === $scope.searchCriteria)) {
                // Nothing to do. Last search still valid.
                return;
            }

            // Cancel out the queued up search if it exists
            if (angular.isFunction(queuedSearchHandle)) {
                queuedSearchHandle(); // Remove the event subscription
                queuedSearchHandle = null;
            }

            if (!$scope.termId) {
                // The search cannot occur w/o a termId.
                console.log('Search blocked - no termId exists');

                // Queue the search to be performed when the term is set.
                queuedSearchHandle = $scope.$on('termIdChanged', function() {
                    queuedSearchHandle(); // Remove the event subscription
                    queuedSearchHandle = null;
                    doSearch(criteria);
                });

                return;
            }

            console.log('Searching for "' + criteria + '"');

            // Store off to prevent a provide a way to reference the search results that come back in case
            // the user runs another search request while this one is still running.
            lastSearchCriteria = criteria;
            SearchService.searchForCourses().query({termId: $scope.termId, criteria: criteria}, function(results) {
                if (lastSearchCriteria === criteria) {
                    // REMOVE THIS AFTER MOCK IS REMOVED
                    results = SearchService.filterResults(results, criteria);

                    // This search matches the last one ran - it's current.
                    console.log('Search for "' + criteria + '" complete. Results: ' + results.length);
                    $scope.searchResults = results;
                    $scope.searchCriteria = criteria;
                } else {
                    console.log('Search completed but not the most recent, ignoring results: "' + criteria + '" !== "' + lastSearchCriteria + '"');
                }
            }, function(error) {
                console.log('Error searching for courses: ', error);
            });
        }

    }])


    /**
     * Controller for the search form
     */
    .controller('SearchFormCtrl', ['$scope', '$window', function SearchFormCtrl ($scope, $window) {

        $scope.courseSearchCriteria = '';

        // Listen for any state changes from ui-router. This will reinsert the search criteria into the form on a refresh.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria) && $scope.courseSearchCriteria !== toParams.searchCriteria) {
                $scope.courseSearchCriteria = toParams.searchCriteria;
            }
        });

        $scope.submit = function() {
            if ($scope.courseSearchCriteria) {
                console.log('Submitting search form: ' + $scope.courseSearchCriteria);

                // Use the goToPage method to go to the search page with the criteria in the query string
                $scope.goToPage('/search/' + $window.encodeURIComponent($scope.courseSearchCriteria));
            }
        };

    }])

    .filter('startFrom', function() {
        return function(input, start) {
            start = +start; //parse to int
            return input.slice(start);
        }
    })

;
