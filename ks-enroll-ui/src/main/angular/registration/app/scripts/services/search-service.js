'use strict';

angular.module('regCartApp')
    .service('SearchService', ['ServiceUtilities', 'URLS', '$resource', function SearchService(ServiceUtilities, URLS, $resource) {

        this.searchForCourses = function () {
            // This is the actual query to the server
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
        };

        this.getCourse = function() {
            return localGetCourse();

            // This is the actual query to the server
            // return ServiceUtilities.getData(URLS.scheduleOfClasses + '/search');
        };

        /**
         * REMOVE AFTER MOCK IS PULLED
         *
         * Mock getting the static course details.
         */
        function localGetCourse () {
            return $resource('json/static-search-details.json', {}, {
                query: { method: 'GET', cache: false, isArray: false }
            });
        }


    }]);