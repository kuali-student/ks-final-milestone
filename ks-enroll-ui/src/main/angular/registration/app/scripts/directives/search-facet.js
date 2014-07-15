'use strict';

angular.module('regCartApp')

    /**
     * Search facet directive. A facet is a filter over a single piece of data (e.g. Course prefix 'CHEM').
     *
     * @example <div search-facet="facet"></div>
     * @example <search-facet facet="facet"></div>
     * @example <div class="search-facet" facet="facet"></div>
     */
    .directive('searchFacet', [function() {
        return {
            restrict: 'ECA',
            scope: {
                facet: '=', // Facet definition
                results: '=' // Search results
            },
            templateUrl: 'partials/searchFacet.html',
            controller: ['$scope', function($scope) {

                // Ensure an optionsProvider exists.
                if (!angular.isFunction($scope.facet.optionsProvider)) {
                    // Default options provider that uses optionsKey value to build the options array.
                    $scope.facet.optionsProvider = function(results) {
                        var options = [];

                        // The default options provider requiress the optionsKey. Don't proceed if it's not set.
                        if (angular.isDefined($scope.facet.optionsKey) && $scope.facet.optionsKey !== null) {
                            // Loop through the results and configure an option object for each unique value.
                            angular.forEach(results, function(item) {
                                if (angular.isDefined(item[$scope.facet.optionsKey])) { // Make sure the item has the optionsKey field
                                    // To facilitate processing of arrays, push everything to an array.
                                    var values = item[$scope.facet.optionsKey];
                                    if (!angular.isArray(values)) {
                                        values = [values];
                                    }

                                    angular.forEach(values, function(value) {
                                        // Try to find an existing option with a matching value.
                                        var option = null;
                                        angular.forEach(options, function(o) {
                                            if (option === null && o.value === value) {
                                                option = o;
                                            }
                                        });

                                        if (option === null) {
                                            // The existing option doesn't exist. Create it anew.
                                            option = {
                                                label: value,
                                                value: value,
                                                count: 0
                                            };
                                            options.push(option);
                                        }

                                        // Increment the count of results identified with this option.
                                        option.count++;
                                    });
                                }
                            });
                        } else {
                            console.log('Facet "' + facet.id + '" is missing the required optionsKey value');
                        }

                        return options;
                    };
                }

                // Ensure a filter exists.
                if (!angular.isFunction($scope.facet.filter)) {
                    $scope.facet.filter = function(item, selectedValues) {
                        // The default filter requires the optionsKey field to be set.
                        if (angular.isDefined($scope.facet.optionsKey) && $scope.facet.optionsKey !== null) {
                            if (angular.isDefined(item[$scope.facet.optionsKey])) { // Make sure the item has the optionsKey field
                                var value = item[$scope.facet.optionsKey];

                                if (!angular.isArray(value)) {
                                    return selectedValues.indexOf(value) !== -1;
                                } else {
                                    var selected = false;
                                    angular.forEach(value, function(v) {
                                        if (!selected && selectedValues.indexOf(v) !== -1) {
                                            selected = true;
                                        }
                                    });

                                    return selected;
                                }
                            }
                        }

                        return false;
                    };
                }


                /**
                 * Refresh the facet options based on the current search results
                 */
                function populateOptions(results) {
                    // 1. Call prepare() on the facet if that method is defined
                    if (angular.isFunction($scope.facet.prepare)) {
                        $scope.facet.prepare(results);
                    }

                    // 2. Build out the facet options based on the current search results.
                    $scope.options = $scope.facet.optionsProvider(results);
                    $scope.facet.selectedOptions = [];
                }


                $scope.options = []; // Facet options based on the current search results
                $scope.facet.selectedOptions = []; // Currently Selected facet options


                // Watch for changes on the results and rebuild if they occur
                $scope.$watch('results', function(results) {
                    console.log('Search Results changed, updating facets');
                    populateOptions(results);
                });



                /**
                 * Clear selected options
                 */
                $scope.clearSelectedOptions = function() {
                    $scope.facet.selectedOptions = [];
                };

                /**
                 * Check whether or not any options have been selected
                 *
                 * @return {boolean}
                 */
                $scope.hasSelected = function() {
                    return $scope.facet.selectedOptions.length > 0;
                };

                /**
                 * Is an option selected.
                 *
                 * @param option facet option
                 * @returns {boolean}
                 */
                $scope.isSelected = function(option) {
                    return $scope.facet.selectedOptions.indexOf(option.value) !== -1;
                };

                /**
                 * Toggle the selected state of a given option
                 *
                 * @param option facet option
                 */
                $scope.toggleOption = function(option) {
                    var index = $scope.facet.selectedOptions.indexOf(option.value);
                    if (index === -1) {
                        $scope.facet.selectedOptions.push(option.value);
                    } else {
                        $scope.facet.selectedOptions.splice(index, 1);
                    }
                };
            }]
        };
    }]);
