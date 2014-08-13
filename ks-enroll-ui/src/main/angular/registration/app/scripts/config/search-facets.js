'use strict';

angular.module('regCartApp')

    /**
     * Course facet definitions
     *
     * Facets are 'smart' objects that are used to filter search results.
     * A facet is a filter over a single piece of data (e.g. Course prefix 'CHEM').
     *
     * Facet properties are:
     * {
     *     label: Required string label
     *     id: Required string ID of facet - used to create a unique ID in the DOM.
     *
     *     autoCollapse: Optional boolean to collapse the facet if only 1 option is discovered - Default = True.
     *
     *     optionsKey: Optional string key corresponding to the field that the facet value in the search result items
     *                 Required if using the default filter.
     *
     *     optionsProvider: Optional function to parse the search results & return a list of option objects
     *         @param array of search results
     *         @return array of facet options [{ label, value, count }]
     *
     *     filter: Optional function used to enforce the selected facets on the displayed search results
     *         @param object search result item
     *         @param array of selected facet options values (values only)
     *         @return boolean of whether or not to display the item based on the selected facet options
     *
     *     prepare: Optional function called prior to checking the optionsProvider/optionsKey.
     *              Useful for doing some initial processing of the search results.
     *         @param array of search results
     *
     *     refine: Optional function called after the options have been determined.
     *             Useful for performing some refinement or aggregation of the facet options.
     *         @param array of options
     *         @param array of search results
     *         @return array of options
     * }
     *
     * When not explicitly set, optionsProvider & filter default to showing a 1-1 list of the values detected on the optionsField.
     * One of optionsKey or optionsProvider is required. If both are defined, preference is given to optionsKey.
     * - If using the default filter, optionsKey is required.
     *
     * Facets will be displayed in the order they are configured here.
     */

    .constant('COURSE_SEARCH_FACETS', [
        {
            label: 'Seats',
            id: 'seatsAvailable',
            autoCollapse: false, // Don't auto-collapse Seats Available
            optionsProvider: function(results) {
                var itemsWithSeats = 0;
                angular.forEach(results, function(item) {
                    if (item.seatsAvailable > 0) {
                        itemsWithSeats++;
                    }
                });

                var options = [];
                if (itemsWithSeats > 0) {
                    options.push({
                        label: 'Seats available',
                        value: 'seatsAvailable',
                        count: itemsWithSeats
                    });
                }

                return options;
            },
            filter: function(item) {
                // Since there is only 1 option possible, there is no need to check against any selected facets.
                return item.seatsAvailable > 0;
            }
        },

        {
            label: 'Credits',
            id: 'creditOptions',
            optionsKey: 'creditOptions', // The default optionsProvider & filter can handle arrays
            refine: function(originalOptions) {
                var options = [];

                // Bucket all credit options into integer values where 1 matches 1 <= X < 2.
                // Furthermore, bucket everything larger than 6 into a 6+ option.
                angular.forEach(originalOptions, function(option) {
                    var newValue = Math.floor(option.value),
                        newLabel = newValue;


                    // Enforce the 6+ limit
                    if (newValue > 6) {
                        newValue = 6;
                        newLabel = '6+';
                    }

                    // Check to see if this new option has already been handled
                    var existingOption = null;
                    for (var i = 0; i < options.length; i++) {
                        if (options[i].value.indexOf(newValue) !== -1) {
                            existingOption = options[i];
                            break;
                        }
                    }

                    if (existingOption === null) {
                        // The option has not yet been handled. Create it.
                        option.label = newLabel;
                        option.count = 0; // Reset the count since it'll be retallied later.

                        // New credit option values are stored as an array since 1 option can match multiple values.
                        option.value = [option.value];
                        if (option.value.indexOf(newValue) === -1) {
                            option.value.push(newValue);
                        }

                        options.push(option);
                    } else {
                        // The option has already been handled. Make sure it contains the value of the current option.
                        if (existingOption.value.indexOf(option.value) === -1) {
                            existingOption.value.push(newValue);
                        }

                        existingOption.label = newLabel;
                    }
                });

                return options;
            }
        },

        {
            label: 'Course Level',
            id: 'courseLevel',
            optionsKey: 'courseLevel'
        },

        {
            label: 'Course Prefix',
            id: 'coursePrefix',
            optionsKey: 'coursePrefix'
        }
    ])

    /**
     * Factory to provide an initialized set of the COURSE_SEARCH_FACETS (with the default optionsProvider & filters configured if necessary)
     * This is what should be used within the code. COURSE_SEARCH_FACETS is extensible and should be overwritten with custom facet definitions.
     */
    .factory('CourseSearchFacets', ['COURSE_SEARCH_FACETS', 'SearchFacetService', function(COURSE_SEARCH_FACETS, SearchFacetService) {
        return SearchFacetService.initFacets(COURSE_SEARCH_FACETS);
    }])
;