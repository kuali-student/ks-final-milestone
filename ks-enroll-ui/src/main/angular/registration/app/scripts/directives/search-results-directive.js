'use strict';

angular.module('regCartApp')

    /*
     The search-list directive displays a generic list of search results based on the
     columns set by the search-column directive.
     */
    .directive('searchList', ['$filter', '$animate', '$timeout', function($filter, $animate, $timeout) {
        return {
            restrict: 'E',
            require: '^searchResults',
            templateUrl: 'partials/searchList.html',
            scope: {
                searchResults: '=?', //optional
                searchColumns: '=',
                searchData: '=?',    // optional
                displayMax: '@displayLimit',
                state: '=?', // optional
                searchCriteria: '@',
                termName: '@',
                detailsId: '@',
                defaultField: '@',
                preprocessor: '@',
                onClick: '@',
                prefix: '@',
                showMobile: '@',
                last: '=?',     // optional
                noResults: '@'  //optional, template to show if there are no results
            },
            link: function(scope) {
                $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-row'))); // Large-format table rows
                $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-result'))); // Mobile cards

                if (angular.isUndefined(scope.searchResults) && scope.preprocessor) {
                    scope.searchResults = $filter(scope.preprocessor)(scope.searchData);
                }

                // Displays the table in batches for performance
                var stagger = 20;
                scope.limit = 0;
                scope.mobileLimit = 0;

                // limit is for large format, and looks at the set display limit
                scope.$watch('limit', function() {
                    if (scope.limit > 0 && scope.limit < scope.displayLimit) {
                        $timeout(function() {
                            scope.limit += stagger;
                        }, 250);
                    } else {
                        if (scope.limit > scope.displayLimit) {
                            scope.limit = scope.displayLimit;
                        }
                    }
                });

                // mobile limit is for small format and looks at the entire array of search results
                scope.$watch('mobileLimit', function() {
                    if (scope.mobileLimit > 0 && scope.mobileLimit < scope.searchResults.length) {
                        $timeout(function() {
                            scope.mobileLimit += stagger;
                        }, 250);
                    } else {
                        if (scope.mobileLimit > scope.searchResults.length) {
                            scope.mobileLimit = scope.searchResults.length;
                        }
                    }
                });

                // the choices for limiting display of search results
                scope.displayLimits = [20, 50, 100];

                if (angular.isDefined(scope.state)) {
                    // set the default values from the state object
                    scope.displayLimit = parseInt(scope.state.displayLimit);
                    scope.page = parseInt(scope.state.page);
                    scope.predicate = scope.state.predicate;
                    scope.reverse = scope.state.reverse || undefined;
                }

                // make sure the page & display limit is set to a valid value
                scope.page = scope.page || 1;
                if (scope.displayLimits.indexOf(scope.displayLimit) === -1) {
                    scope.displayLimit = scope.displayLimits[0];
                }

                if (angular.isDefined(scope.displayMax) && scope.displayMax) {
                    scope.displayLimit = scope.displayMax;
                }

                // holds the customizable id for linking to search details
                scope.detailsId = {};

                // holds the reverse for each column (true = reverse sort)
                scope.reverseMap = {};

                // returns the last page
                scope.lastPage = function() {
                    return Math.ceil(scope.searchResults.length / scope.displayLimit);
                };

                // returns a the full range of pages
                scope.pageRange = function() {
                    var pageRange = [],
                        lastPage = scope.lastPage();
                    for (var i = 1; i <= lastPage; i++) {
                        pageRange.push(i);
                    }
                    return pageRange;
                };

                /*
                 * if the display limit changes:
                 * -- reset the page to 1
                 * -- increment the limit
                 */
                scope.$watch('displayLimit', function() {
                    scope.page = 1;
                    scope.limit += stagger;
                });

                /*
                 * if the search results changes:
                 * -- if current page is outside of the page range, reset the page to 1
                 * -- increment the mobile limit
                 */
                scope.$watch('searchResults', function() {
                    if (scope.lastPage() === 0) { // First init
                        // Take them to the state's page if it is defined, otherwise page 1
                        scope.page = scope.state.page || 1;
                    } else if (scope.page > scope.lastPage()) {
                        scope.page = 1;
                    }
                    scope.mobileLimit += stagger;
                });

                // returns the index of the first record being displayed
                scope.displayRangeMin = function() {
                    return ((scope.page - 1) * scope.displayLimit) + 1;
                };

                // returns the index of the last record being displayed
                scope.displayRangeMax = function() {
                    var min = scope.displayRangeMin(),
                        max = min + scope.displayLimit - 1;

                    if (max > scope.searchResults.length) {
                        max = scope.searchResults.length;
                    }

                    return max;
                };

                // apply the given filter name to the value (if it's an array)
                scope.applyFilter = function(row, field, filterName) {
                    // Check for the cached value
                    if (angular.isDefined(row._cachedValues) && angular.isDefined(row._cachedValues[field])) {
                        return row._cachedValues[field];
                    }

                    var value = null;
                    if (field && angular.isUndefined(row[field]) && field.indexOf('.') !== -1) {
                        var workingValue = row,
                            parts = field.split('.'),
                            part;

                        for (var i = 0; part = parts[i], i < parts.length; i++) {
                            if (angular.isDefined(workingValue[part])) {
                                workingValue = workingValue[part];
                            } else {
                                // Part doesn't exist. Exit out.
                                workingValue = null;
                                break;
                            }
                        }

                        if (workingValue !== null) {
                            value = workingValue;
                        }
                    } else {
                        value = row[field];
                    }

                    // Apply the filter if one was provided
                    if (angular.isArray(value) && filterName) {
                        value = $filter(filterName)(value);
                    }

                    // Make sure the cache is defined
                    if (angular.isUndefined(row._cachedValues)) {
                        row._cachedValues = {};
                    }

                    // Cache the filtered value
                    row._cachedValues[field] = value;

                    return value;
                };

                // returns the customizable id for the given search result
                scope.getId = function(searchResult) {
                    return searchResult[scope.detailsId];
                };

                // column sort method called when the column header is clicked
                scope.sortColumn = function(column) {
                    if (column.sortable) {
                        scope.predicate = column.field;
                        scope.reverse = scope.sortResults(column.field);
                    }
                };

                // switches the reverse order of the specified column
                scope.sortResults = function(column) {
                    var reverse;
                    if (angular.isUndefined(scope.reverseMap[column])) {
                        // initialize
                        scope.reverseMap[column] = 0;
                    }
                    switch (scope.reverseMap[column]) {
                        case 0: // first click
                            scope.reverseMap[column] = 1;
                            reverse = false; // descending order
                            break;
                        case 1: // second click
                            scope.reverseMap[column] = 2;
                            reverse = true; // ascending order
                            break;
                        case 2:
                            scope.reverseMap[column] = 0;
                            scope.predicate = undefined; //undoes the search
                            break;
                    }
                    return reverse;
                };

                // helper method to emit an event with the search result
                scope.emitEvent = function(eventToEmit, searchResult) {
                    if (eventToEmit) {
                        scope.$emit(eventToEmit, searchResult);
                    }
                };

                //checks to see if the given column has data
                scope.hasData = function(field) {
                    var hasData = false;
                    for (var i=0; i < scope.searchResults.length; i++) {
                        var data = scope.applyFilter(scope.searchResults[i], field);
                        if (data) {
                            hasData = true;
                            break;
                        }
                    }
                    return hasData;
                };


                /*
                 Watch and emit an event anytime the list state values change (page, displayLimit, & sorting)
                 */
                function emitResultsStateChangedEvent(value, oldValue) {
                    if (value !== oldValue) {
                        var resultsState = {
                            displayLimit: scope.displayLimit,
                            page: scope.page,
                            predicate: scope.predicate || null,
                            reverse: (scope.predicate ? scope.reverse : null) // Only consider reverse if predicate is defined
                        };

                        scope.$emit('resultsStateChanged', resultsState);
                    }
                }

                scope.$watch('displayLimit', emitResultsStateChangedEvent);
                scope.$watch('page', emitResultsStateChangedEvent);
                scope.$watch('predicate', emitResultsStateChangedEvent);
                scope.$watch('reverse', emitResultsStateChangedEvent);


                /*
                If a clearSelected event is received from the parent, clear all the selected search
                results and unhide.
                 */
                scope.$on('clearSelected', function () {
                    angular.forEach(scope.searchResults, function(searchResult) {
                        searchResult.selected = false;
                        searchResult.hidden = false;
                    });
                });

                // hides the row with the given id
                scope.$on('hideRow', function (event, id) {
                    angular.forEach(scope.searchResults, function(searchResult) {
                        if (searchResult[scope.defaultField] === id) {
                            searchResult.hidden = true;
                        }
                    });
                });

                // shows the row with the given id
                scope.$on('showRow', function (event, id) {
                    angular.forEach(scope.searchResults, function(searchResult) {
                        if (searchResult[scope.defaultField] === id) {
                            searchResult.hidden = false;
                        }
                    });
                });

                // shows all rows
                scope.$on('showAllRows', function () {
                    angular.forEach(scope.searchResults, function(searchResult) {
                        if (searchResult.hidden) {
                            searchResult.hidden = false;
                        }
                    });
                });
            }
        };
    }])

    /*
    The parent directive for search results. Can contain:

    -- search-column(s) -- defines the column(s) for display in the results
    -- search-list -- the actual list of results
     */
    .directive('searchResults', function() {
        return {
            restrict: 'E',
            controller: 'SearchResultsCtrl'
        };
    })

    /*
     The search-column directive sets the columns to be displayed by search results with
     the following options:

     name (required) -- the user-friendly column name to be displayed
     field (required) -- the field name on the searchResult object to display
     filter (optional) -- a filter to be applied to the field
     order (optional) -- the order in which the column should display
     url (optional) -- converts the field into a link to the given url
     */
    .directive('searchColumn', function() {
        return {
            restrict: 'E',
            require: '^searchResults',
            link:function(scope,element,attrs) {
                if (attrs.name || attrs.field || attrs.filter) {
                    if (!angular.isArray(scope.searchColumns)) {
                        scope.searchColumns = [];
                    }
                    var searchColumn = {name: attrs.name,
                        field: attrs.field,
                        filter: attrs.filter,
                        order: attrs.order,
                        url: attrs.url,
                        sortable: attrs.sortField,
                        optional: attrs.optional,
                        checkbox: attrs.checkbox,
                        highlight: attrs.highlight};
                    searchColumn.sortable = searchColumn.sortable !== 'false'; // defaults to true
                    searchColumn.optional = searchColumn.optional === 'true';  // defaults to false
                    searchColumn.highlight = searchColumn.highlight === 'true';        // defaults to false
                    scope.searchColumns.push(searchColumn);
                    scope.sortSearchColumns();
                }
            }
        };
    })

    .controller('SearchResultsCtrl', ['$scope', '$filter', function SearchResultsCtrl ($scope, $filter) {
        $scope.sortSearchColumns = function() {
            $scope.searchColumns = $filter('orderBy')($scope.searchColumns, 'order');
        };
    }])

;
