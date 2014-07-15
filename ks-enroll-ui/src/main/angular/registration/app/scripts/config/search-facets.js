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
     *     optionsField: Optional string key corresponding to the field that the facet value in the search result items
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
     *     prepare: Optional function called prior to checking the optionsProvider/optionsKey. Useful for doing some initial processing of the search results.
     *         @param array of search results
     * }
     *
     * When not explicitly set, optionsProvider & filter default to showing a 1-1 list of the values detected on the optionsField.
     * Either optionsKey or optionsProvider must be set.
     *
     * Facets will be displayed in the order they are configured here.
     */

    .constant('SEARCH_FACETS', [
        {
            label: 'Seats',
            id: 'seatsAvailable',
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
            optionsKey: 'creditOptions' // The default optionsProvider & filter can handle arrays
        },

        {
            label: 'Course Level',
            id: 'courseLevel',
            optionsKey: 'courseLevel',
            prepare: function(results) {
                // Create the courseLevel field on the result item.
                console.log(results);
                angular.forEach(results, function(item) {
                    if (angular.isUndefined(item.courseLevel)) {
                        var level = item.courseNumber.substring(0, 1) + '00';
                        item.courseLevel = level;
                    }
                });
            }
        },

        {
            label: 'Course Prefix',
            id: 'coursePrefix',
            optionsKey: 'coursePrefix'
        }
    ]);