'use strict';

/*
 * Controller for the search functionality
 *
 * Event Handling
 * -- Emits: none
 * -- Broadcasts: none
 * -- Receives: "termIdChanged" -- received from the main-controller.js, updates search when term Id is changed
 *              "resultsStateChanged" -- received from the searchList directive, updates search results as needed
 *              "facetSelectionChange" -- received from the searchFacet directive, updates when facet selection is changed
 *              "viewDetails" -- received from the courseCard directive, performs search when view details button is clicked
 */
angular.module('regCartApp')
    .controller('SearchCtrl', ['$scope', '$filter', '$state', '$timeout', 'CourseSearchFacets', 'TermsService', 'SearchService',
    function SearchCtrl($scope, $filter, $state, $timeout, CourseSearchFacets, TermsService, SearchService) {
        console.log('>> SearchCtrl');

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
                lastSearchHash = null;
                doSearch(criteria, $scope.searchState.cluId);
            }
        });

        // Listen for any state changes from ui-router. This is where we get the search criteria from.
        $scope.$on('$stateChangeSuccess', function(event, toState, toParams) {
            if (toState.name === 'root.search.results') {
                syncWithSearchFromState(toParams);

                if (angular.isDefined(toParams.searchCriteria) || angular.isDefined(toParams.cluId)) {
                    doSearch(toParams.searchCriteria, toParams.cluId);
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
            params = params || angular.copy($state.params);

            // Build out the facet filter values
            var selectedFacets = {};
            for (var i = 0; i < $scope.facets.length; i++) {
                var facet = $scope.facets[i];
                if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
                    selectedFacets[facet.id] = facet.selectedOptions;
                }
            }

            // Push the selected facets into the URL parameters as a JSON string since ui-router doesn't handle object parameters
            params.filters = Object.keys(selectedFacets).length > 0 ? JSON.stringify(selectedFacets) : null;

            // location: 'replace' = replaces the last history item in the stack preserving the back functionality
            // notify: false = prevents the UI state change event from firing which would reload the view
            $state.go('root.search.results', params, { location: 'replace', notify: false });
        }

        // Sync the state parameters with the values in the search
        function syncWithSearchFromState(params) {
            params = params || $state.params;

            // Put the states into the scope for use by the search list
            $scope.stateParams = params;

            // Sync up the selected facets
            var selectedFacets = {};
            if (angular.isDefined(params.filters) && params.filters !== null) {
                selectedFacets = JSON.parse(params.filters) || {};
            }

            for (var i = 0; i < $scope.facets.length; i++) {
                var selection = [],
                    facet = $scope.facets[i];

                if (angular.isDefined(selectedFacets[facet.id])) {
                    selection = selectedFacets[facet.id];
                }

                facet.selectedOptions = selection;
            }
        }


        // Apply the facet filter to the results
        function filterResults() {
            var filter = $filter('facetFilter'),
                filtered = [];

            for (var i = 0; i < $scope.searchResults.length; i++) {
                if (filter($scope.searchResults[i], $scope.facets)) {
                    if (angular.isUndefined($scope.searchResults[i].title)) {
                        $scope.searchResults[i].title = getResultsTitle($scope.searchResults[i]);
                    }
                    filtered.push($scope.searchResults[i]);
                }
            }

            $scope.filteredResults = filtered;

            if (filtered.length === 1) {
                if ($scope.searchState.cluId) {
                    viewDetails(filtered[0]);
                }
            }
        }

        // Watch for selected facets to change, persist the change & filter the results
        var facetTimeoutPromise;
        $scope.$on('facetSelectionChange', function() {

            if (facetTimeoutPromise) {
                // Cancel out any previous timer that's still running (if they click before the timeout fires).
                $timeout.cancel(facetTimeoutPromise);
            }

            facetTimeoutPromise = $timeout(function() {
                filterResults();
                persistSearchState();
            }, 20, false);
        });


        var queuedSearchHandle, // Handle on the queued up search.
            lastSearchHash = null; // Criteria used to execute the most recent search request.
        function doSearch(criteria, cluId) {
            var searchHash = criteria + cluId;
            if (!searchHash || (lastSearchHash !== null && searchHash === lastSearchHash)) {
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
                // console.log('Search blocked - no termId exists');

                // Queue the search to be performed when the term is set.
                queuedSearchHandle = $scope.$on('termIdChanged', function() {
                    queuedSearchHandle(); // Remove the event subscription
                    queuedSearchHandle = null;
                    doSearch(criteria);
                });

                return;
            }

            // console.log('Searching for "' + criteria + '"');

            // Store off to prevent a provide a way to reference the search results that come back in case
            // the user runs another search request while this one is still running.
            lastSearchHash = searchHash;
            SearchService.searchForCourses({
                termId: TermsService.getTermId(),
                criteria: criteria || null,
                cluId: cluId || null
            }).then(function(results) {
                if (lastSearchHash === searchHash) {
                    // This search matches the last one ran - it's current.
                    console.log('Search for "' + criteria + '" complete. Results: ' + results.length);
                    $scope.searchResults = results;
                    $scope.searchCriteria = criteria;
                    filterResults();
                } else {
                    console.log('Search completed but not the most recent, ignoring results: "' + searchHash + '" !== "' + lastSearchHash + '"');
                }
            }, function(error) {
                console.log('Error searching for courses: ', error);
            });
        }

        function getResultsTitle(searchResult) {
            var title = searchResult.longName;

            if (searchResult.honors) {
                title += '<span class="kscr-SearchDetails-icon--honors" title="Honors Course"></span>';
            }

            return title;
        }

        // redirects the view to the course details screen
        function viewDetails(course) {
            $state.go('root.search.details', {
                searchCriteria: $scope.searchCriteria,
                id: course.courseId,
                code: course.courseCode,
                origin: $scope.searchState.origin
            });
        }

        $scope.$on('viewDetails', function(event, course) {
            viewDetails(course);
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
            for (var i = 0; i < facets.length; i++) {
                var facet = facets[i];

                // Only filter on the facet if it has selected options
                if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
                    if (!facet.filter(item, facet.selectedOptions)) {
                        // The item does not match the facet filter. Exclude it.
                        return false;
                    }
                }
            }

            return true;
        };
    })

;
