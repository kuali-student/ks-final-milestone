'use strict';

/**
 * Utility filters for course registration
 */

/*
 * Cleanses a string by removing spaces
 */
angular.module('regCartApp')
    .filter('cleanse', function() {
        return function(value) {
            var cleansed;
            if (angular.isString(value)) {
                cleansed = value.replace(' ', '');
            } else {
                cleansed = value;
            }
            return cleansed;
        };
    })
;
