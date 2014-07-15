'use strict';

angular.module('regCartApp')
    .controller('SearchCtrl', ['$scope', '$interval', 'SearchService', 'SEARCH_FACETS', function SearchCtrl($scope, $interval, SearchService, SEARCH_FACETS) {

        $scope.facets = SEARCH_FACETS; // Facet definitions

        $scope.searchCriteria = ''; // Criteria used to generate the search results.
        $scope.searchResults = []; // Results from the last search request.


        $scope.$on('termIdChanged', function() {
            // Drop the existing search results when the term changes
            $scope.searchCriteria = '';
            $scope.searchResults = [];

            // If the search page is the current page, push the user back to their cart.
            if ($scope.uiState === 'root.search') {
                $scope.goToPage('/myCart');
            }
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria)) {
                doSearch(toParams.searchCriteria);
            }
        });

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


        var queuedSearch, // Promise handle on the queued up search.
            lastSearchCriteria = ''; // Criteria used to execute the most recent search request.
        function doSearch(criteria) {
            if (criteria === null || ($scope.searchCriteria !== null && criteria === $scope.searchCriteria)) {
                // Nothing to do. Last search still valid.
                return;
            }

            // Cancel out the queued up search if it exists
            if (angular.isDefined(queuedSearch) && queuedSearch !== null) {
                $interval.cancel(queuedSearch);
                queuedSearch = null;
            }

            if (!$scope.termId) {
                // The search cannot occur w/o a termId.
                console.log('Search blocked - no termId exists');

                queuedSearch = $interval(function() {
                    if ($scope.termId) {
                        $interval.cancel(queuedSearch); // Cancel any subsequent intervals
                        doSearch(criteria); // Run the search
                    }
                }, 100, 20); // Repeat every 100ms up to 20 times (2s in total)

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

        // Clear out the search criteria when the termId is changed.
        $scope.$on('termIdChanged', function(event, newValue, oldValue) {
            // Don't clear it out if the old termId is null. This occurs on a refresh.
            if (oldValue !== null) {
                $scope.courseSearchCriteria = '';
            }
        });

        // Listen for any state changes from ui-router. This will reinsert the search criteria into the form on a refresh.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria)) {
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

    }]);
