'use strict';

angular.module('regCartApp')
    .controller('SearchCtrl', ['$scope', '$interval', 'SearchService', function SearchCtrl($scope, $interval, SearchService) {

        $scope.searchCriteria = ''; // Criteria used to generate the search results.
        $scope.searchResults = []; // Results from the last search request.

        $scope.$watch('termId', function() {
            // Drop the existing search results when the term changes
            $scope.searchCriteria = '';
            $scope.searchResults = [];
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (angular.isDefined(toParams.searchCriteria)) {
                doSearch(toParams.searchCriteria);
            }
        });



        var queuedSearch, // Promise handle on the queued up search.
            lastSearchCriteria = ''; // Criteria used to execute the most recent search request.
        function doSearch(criteria) {
            if (criteria === $scope.searchCriteria && criteria !== null) {
                // Nothing to do. Last search still valid.
                return;
            }

            // Cancel out the queued up search if it exists
            if (angular.isDefined(queuedSearch) && queuedSearch !== null) {
                $interval.cancel(queuedSearch);
                queuedSearch = null;
            }

            var termId = $scope.termId;
            if (!termId) {
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
            SearchService.searchForCourses().query({termId: termId, criteria: criteria}, function(results) {
                if (lastSearchCriteria === criteria) {
                    // This search matches the last one ran - it's current.
                    console.log('Search for "' + criteria + '" complete. Results: ' + results.length);

                    // REMOVE THIS AFTER MOCK IS REMOVED
                    $scope.searchResults = SearchService.filterResults(results, criteria);

                    // $scope.searchResults = results;
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
            if (angular.isDefined(toParams.searchCriteria)) {
                $scope.courseSearchCriteria = toParams.searchCriteria;
            }
        });

        $scope.submit = function() {
            if ($scope.courseSearchCriteria) {
                console.log('Submitting search form: ' + $scope.courseSearchCriteria);

                // Use the goToPage method to go to the search page with the criteria in the query string
                $scope.goToPage('/search/' + $window.encodeURIComponent($scope.courseSearchCriteria));
                $scope.searchSubmitted = true; // refocus cursor back to search input field
            }
        };

    }]);
