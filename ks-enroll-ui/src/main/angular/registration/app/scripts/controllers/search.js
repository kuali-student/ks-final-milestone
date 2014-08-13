'use strict';

angular.module('regCartApp')
    .controller('SearchCtrl', ['$scope', '$rootScope', '$filter', '$state', 'TermsService', 'SearchService', 'CourseSearchFacets',
    function SearchCtrl($scope, $rootScope, $filter, $state, TermsService, SearchService, CourseSearchFacets) {

        $scope.facets = CourseSearchFacets; // Facet definitions

        $scope.searchState = $state.params; // Expose the state parameters so the search state can be persisted there (e.g. page, facets, etc)

        $scope.searchCriteria = null; // Criteria used to generate the search results.
        $scope.searchResults = [];    // Results from the last search request.
        $scope.filteredResults = [];  // Results that have been filtered through the facets.

        $scope.$on('termIdChanged', function() {
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
            if (toState.name === 'root.search.results') {
                syncSearchWithState(toParams);

                if (angular.isDefined(toParams.searchCriteria)) {
                    doSearch(toParams.searchCriteria);
                }
            }
        });

        // Listen for changes to the 'resultsStateChanged' event that is emitted from the searchList directive when the state changes.
        $scope.$on('resultsStateChanged', function(event, resultsState) {
            var params = angular.copy(resultsState);
            params.searchCriteria = $scope.searchCriteria;

            persistSearchState(params);
        });

        // Persist the search state in the URL query string
        function persistSearchState(params) {
            params = params || $state.params;

            // Build out the facet filter values
            var selectedFacets = {};
            angular.forEach($scope.facets, function(facet) {
                if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
                    selectedFacets[facet.id] = facet.selectedOptions;
                }
            });

            // Push the selected facets into the URL parameters as a JSON string since ui-router doesn't handle object parameters
            params.filters = Object.keys(selectedFacets).length > 0 ? JSON.stringify(selectedFacets) : null;

            // location: 'replace' = replaces the last history item in the stack preserving the back functionality
            // notify: false = prevents the UI state change event from firing which would reload the view
            // reload: true = force the state to reload even if it thinks it is the same (doesn't recognize filter changes for some reason)
            $state.go('root.search.results', params, { location: 'replace', notify: false, reload: true });
        }

        // Sync the state parameters with the values in the search
        function syncSearchWithState(params) {
            params = params || $state.params;

            // Put the states into the scope for use by the search list
            $scope.stateParams = params;

            // Sync up the selected facets
            var selectedFacets = {};
            if (angular.isDefined(params.filters) && params.filters !== null) {
                selectedFacets = JSON.parse(params.filters) || {};
            }

            angular.forEach($scope.facets, function(facet) {
                var selection = [];
                if (angular.isDefined(selectedFacets[facet.id])) {
                    selection = selectedFacets[facet.id];
                }

                facet.selectedOptions = selection;
            });
        }


        // Apply the facet filter to the results
        function filterResults() {
            var filter = $filter('facetFilter'),
                filtered = [];

            angular.forEach($scope.searchResults, function(item) {
                if (filter(item, $scope.facets)) {
                    filtered.push(item);
                }
            });

            $scope.filteredResults = filtered;
        }

        // Watch for selected facets to change, persist the change & filter the results
        $scope.$watch('facets', function() {
            persistSearchState();
            filterResults();
        }, true);


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

            if (!TermsService.getTermId()) {
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
            SearchService.searchForCourses().query({termId: TermsService.getTermId(), criteria: criteria}, function(results) {
                if (lastSearchCriteria === criteria) {
                    // This search matches the last one ran - it's current.
                    console.log('Search for "' + criteria + '" complete. Results: ' + results.length);
                    $scope.searchResults = results;
                    $scope.searchCriteria = criteria;
                    filterResults();
                } else {
                    console.log('Search completed but not the most recent, ignoring results: "' + criteria + '" !== "' + lastSearchCriteria + '"');
                }
            }, function(error) {
                console.log('Error searching for courses: ', error);
            });
        }


        $scope.$on('viewDetails', function(event, course) {
            // redirects the view to the search details screen
            $state.go('root.search.details', { searchCriteria: $scope.searchCriteria, id: course.courseId });
        });

    }])

    .filter('startFrom', function() {
        return function(input, start) {
            start = +start; //parse to int
            return input.slice(start);
        };
    })

    .filter('facetFilter', function() {
        return function(item, facets) {
            var select = true;
            angular.forEach(facets, function(facet) {
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
    })

;
