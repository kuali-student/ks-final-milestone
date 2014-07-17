'use strict';

angular.module('regCartApp')
    .filter('creditRangeFormatter', function() {

        /**
         * Format an array of credit options into a range using the min & max values.
         *
         * If only 1 option exists in the array, it is returned (e.g. '1 cr').
         * Otherwise the value returned is a represented as a range (e.g. '1-3 cr').
         *
         * @param array of credit options
         * @return string
         */
        return function(creditOptions) {
            var range = '';

            if (creditOptions && angular.isArray(creditOptions)) {
                if (creditOptions.length === 1) {
                    range = parseFloat(creditOptions[0]);
                } else {
                    var min = parseFloat(Math.min.apply(null, creditOptions)),
                        max = parseFloat(Math.max.apply(null, creditOptions));

                    range = min + '-' + max;
                }

                range += ' cr';
            }

            return range;
        };

    });
