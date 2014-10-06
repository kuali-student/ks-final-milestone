'use strict';

/**
 * Configure the default $http behavior to transform POST & PUT requests to url encoded instead of JSON
 */
angular.module('regCartApp').config(['$httpProvider', function($httpProvider) {

    // Transform the request data to be a serialized string instead of a JSON object
    $httpProvider.defaults.transformRequest = function(data) {
        if (!angular.isObject(data)) {
            return (angular.isUndefined(data) || data === null ? '' : data.toString());
        }

        var buffer = [];
        for (var key in data) {
            if (!data.hasOwnProperty(key)) {
                continue;
            }

            var value = data[key];
            buffer.push(encodeURIComponent(key) + '=' + encodeURIComponent(value === null ? '' : value));
        }

        return buffer
            .join('&')
            .replace(/%20/g, '+');
    };

    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);


/**
 * This is the override section for the FEATURE_TOGGLES
 * It allows you to selectively override the config based on query parameters.
 *
 * Available Overrides:
 * - LearningPlan - ?learningPlan=true
 */
angular.module('regCartApp')
    .run(['$location', 'FEATURE_TOGGLES', function($location, FEATURE_TOGGLES) {
        var queryParameters = $location.search();
        if (angular.isDefined(queryParameters.learningPlan)) {
            FEATURE_TOGGLES.learningPlan = queryParameters.learningPlan ? true : false;
        }
    }]);
