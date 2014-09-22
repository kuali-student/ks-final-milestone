'use strict';

angular.module('regCartApp')

/**
 * Search facet directive. A facet is a filter over a single piece of data (e.g. Course prefix 'CHEM').
 *
 * @example <div search-facet="facet"></div>
 * @example <search-facet facet="facet"></div>
 * @example <div class="search-facet" facet="facet"></div>
 *
 * Event Handling:
 *  Emits:
 *    - facetSelectionChange (facet) - Emitted when selected options change.
 *  Broadcasts: none
 *  Receives: none
 */
    .directive('searchFacet', ['SearchFacetService', function(SearchFacetService) {
        return {
            restrict: 'ECA',
            scope: {
                facet: '=', // Facet definition
                results: '=' // Search results
            },
            templateUrl: 'partials/searchFacet.html',
            controller: ['$scope', function($scope) {

                // Make sure this facet is properly configured
                SearchFacetService.initFacet($scope.facet);


                /**
                 * Tally the count of results that match each option.
                 * Uses the facet's filter method to check if an option would match a result item.
                 *
                 * @param array of options
                 * @param array of results
                 * @return array of options with updated counts
                 */
                function tallyOptionCounts (options, results) {
                    // First, reset the counts
                    for (var i = 0; i < options.length; i++) {
                        options[i].count = 0;
                    }

                    // Iterate through the results, if the filter method matches the option, increment the count.
                    for (i = 0; i < results.length; i++) {
                        for (var j = 0; j < options.length; j++) {
                            if ($scope.facet.filter(results[i], options[j].value)) {
                                options[j].count++;
                            }
                        }
                    }

                    return options;
                }

                /**
                 * Calculate the array of options from the results list using the facet config.
                 *
                 * @param array of results
                 * @return array of options
                 */
                function calculateOptions(results) {
                    // 1. Call prepare() on the facet if that method is defined
                    if (angular.isFunction($scope.facet.prepare)) {
                        $scope.facet.prepare(results);
                    }

                    // 2. Build out the facet options based on the current search results.
                    var options = $scope.facet.optionsProvider(results);

                    // 3. Call refine() on the facet if that method is defined
                    if (angular.isFunction($scope.facet.refine)) {
                        options = $scope.facet.refine(options, results);

                        // The option counts are now unreliable. Retally them.
                        options = tallyOptionCounts(options, results);
                    }

                    return options;
                }

                /**
                 * Refresh the facet options based on the current search results
                 *
                 * @param array of results
                 */
                function reset (results) {
                    // Clear out the selected options
                    if ($scope.options.length > 0) {
                        $scope.clearSelectedOptions();
                    }

                    // Set the new options into the scope
                    var options = calculateOptions(results);
                    $scope.options = options;

                    // Enforce the autoCollapse option when there is only 1 option.
                    if ((!angular.isDefined($scope.facet.autoCollapse) || $scope.facet.autoCollapse) && options.length === 1) {
                        $scope.isCollapsed = true;
                    }
                }


                $scope.options = []; // Facet options based on the current search results

                if (angular.isUndefined($scope.facet.selectedOptions)) {
                    $scope.facet.selectedOptions = []; // Currently Selected facet options
                }


                // Watch for changes on the results and rebuild if they occur
                $scope.$watch('results', function(results) {
                    // console.log('Search Results changed, updating facets');
                    reset(results);
                });


                // Watch for changes to the selected options & emit a 'facetSelectionChange' event when they occur
                $scope.$watchCollection('facet.selectedOptions', function(newOptions, oldOptions) {
                    if (angular.isArray(newOptions) &&
                        (!angular.isArray(oldOptions) || !(newOptions.length === oldOptions.length && newOptions.length === 0))) {
                        $scope.$emit('facetSelectionChange', $scope.facet, newOptions, oldOptions || []);
                    }
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
                    return getSelectedOptionIndex(option) !== -1;
                };

                /**
                 * Toggle the selected state of a given option
                 *
                 * @param option facet option
                 */
                $scope.toggleOption = function(option) {
                    var index = getSelectedOptionIndex(option);
                    if (index === -1) {
                        $scope.facet.selectedOptions.push(option.value);
                    } else {
                        $scope.facet.selectedOptions.splice(index, 1);
                    }
                };


                /**
                 * Helper method for returning the index of the option within the selected options array
                 *
                 * @param option to locate
                 * @return int index of option in selected options array
                 */
                function getSelectedOptionIndex(option) {
                    var index = -1;
                    if (angular.isArray(option.value)) {
                        // This is an array, do an equality search since indexOf matches on identicality (== vs ===)
                        for (var i = 0; i < $scope.facet.selectedOptions.length; i++) {
                            var selectedValue = $scope.facet.selectedOptions[i],
                                matched = true;

                            for (var j = 0; j < option.value.length; j++) {
                                if (selectedValue.indexOf(option.value[j]) === -1) {
                                    matched = false;
                                    break;
                                }
                            }

                            if (matched) {
                                index = i;
                                break;
                            }
                        }
                    } else {
                        index = $scope.facet.selectedOptions.indexOf(option.value);
                    }

                    return index;
                }
            }]
        };
    }])


/**
 * Search Facet Service
 *
 * Configures the default optionsProvider & filter on the facets. This is generic to the type of facet defined.
 */
    .service('SearchFacetService', function SearchFacetService() {

        this.initFacets = function(facets) {
            // Iterate over the facet definitions and make sure they have an optionsProvider & filter.
            for (var i = 0; i < facets.length; i++) {
                this.initFacet(facets[i]);
            }

            return facets;
        };

        this.initFacet = function(facet) {
            // Ensure an optionsProvider exists.
            if (!angular.isFunction(facet.optionsProvider)) {
                // Default options provider that uses optionsKey value to build the options array.
                facet.optionsProvider = function(results) {
                    var options = [];

                    // The default options provider requires the optionsKey. Don't proceed if it's not set.
                    if (angular.isDefined(facet.optionsKey) && facet.optionsKey !== null) {
                        // Loop through the results and configure an option object for each unique value.
                        for (var i = 0; i < results.length; i++) {
                            var item = results[i];

                            if (angular.isDefined(item[facet.optionsKey])) { // Make sure the item has the optionsKey field
                                // To facilitate processing of arrays, push everything to an array.
                                var values = item[facet.optionsKey];
                                if (!angular.isArray(values)) {
                                    values = [values];
                                }

                                for (var j = 0; j < values.length; j++) {
                                    var value = values[j];

                                    // Try to find an existing option with a matching value.
                                    var option = null;
                                    for (var k = 0; k < options.length; k++) {
                                        if (options[k].value === value) {
                                            option = options[k];
                                            break;
                                        }
                                    }

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
                                }
                            }
                        }
                    } else {
                        console.log('Facet "' + facet.id + '" is missing the required optionsKey value');
                    }

                    // Sort the options by their label
                    options.sort(function(a, b) {
                        return (a.label === b.label ? 0 : (a.label < b.label ? -1 : 1));
                    });

                    return options;
                };
            }

            // Ensure a filter exists.
            if (!angular.isFunction(facet.filter)) {
                facet.filter = function(item, selectedValues) {
                    // Recursive function used to traverse an array to see if the value exists at any
                    // level of the array. This allows a single option to be matched to multiple values.
                    function valueExists(value, array) {
                        var exists = false;
                        if (angular.isArray(value)) {
                            for (var i = 0; i < value.length; i++) {
                                if (valueExists(value[i], array)) {
                                    exists = true;
                                    break;
                                }
                            }
                        } else if (angular.isArray(array)) {
                            for (var j = 0; j < array.length; j++) {
                                if (valueExists(value, array[j])) {
                                    exists = true;
                                    break;
                                }
                            }
                        } else {
                            exists = (value === array);
                        }

                        return exists;
                    }

                    // The default filter requires the optionsKey field to be set.
                    if (angular.isDefined(facet.optionsKey) && facet.optionsKey !== null) {
                        if (angular.isDefined(item[facet.optionsKey])) { // Make sure the item has the optionsKey field
                            var value = item[facet.optionsKey];
                            return valueExists(value, selectedValues);
                        }
                    }

                    return false;
                };
            }

            return facet;
        };
    })
;
