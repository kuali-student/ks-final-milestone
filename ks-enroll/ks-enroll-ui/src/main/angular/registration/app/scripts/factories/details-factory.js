'use strict';

angular.module('regCartApp')

    /*
     *  The details factory is used to dynamic inject search details configurations into
     *  the search-details-list-directive.js controller. Any number of configurations can be made
     *  based on the needs of the implementer.
     *
     *  See the 'COURSE_OPTIONS' configuration for a sample implementation and further
     *  documentation on customization.
     */
    .factory('DetailsFactory', ['$injector', function($injector) {
        var configCache = {};

        // DetailsFactory constructor...call with "new DetailsFactory(configName)"
        var DetailsFactory = function(configName) {

            // get the config from off the cache if possible, otherwise, inject it
            var detailsConfig = configCache[configName];
            if (angular.isUndefined(detailsConfig)) {
                detailsConfig = $injector.get(configName);
            }

            // if the config exists, add it to the cache, otherwise log an error
            if (angular.isUndefined(detailsConfig)) {
                console.log('Search Details configuration ' + configName + ' is not defined');
            } else {
                configCache[configName] = detailsConfig;
            }

            return detailsConfig;
        };

        return (DetailsFactory);
    }])
;
