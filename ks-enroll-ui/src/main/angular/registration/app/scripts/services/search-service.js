'use strict';

angular.module('regCartApp')
    .service('SearchService', ['ServiceUtilities', 'URLS', '$resource', function SearchService(ServiceUtilities, URLS, $resource) {

        this.searchForCourses = function () {
            return localSearch();

            // This is the actual query to the server
            // return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
        };

        this.getCourse = function() {
            return localGetCourse();

            // This is the actual query to the server
            // return ServiceUtilities.getData(URLS.scheduleOfClasses + '/search');
        };


        /**
         * REMOVE AFTER MOCK IS PULLED
         *
         * Mock searching of static results list. Applies search on courseCode & longName.
         */
        function localSearch () {
            return $resource('json/static-search-results.json', {}, {
                query: { method: 'GET', cache: false, isArray: true }
            });
        }

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

        /**
         * REMOVE AFTER MOCK IS PULLED
         *
         * Local filter of results on criteria
         */
        this.filterResults = function(results, criteria) {
            var filtered = [];
            criteria = criteria.toLowerCase();
            angular.forEach(results, function(item) {
                if (item.courseCode.toLowerCase().indexOf(criteria) > -1 || item.longName.toLowerCase().indexOf(criteria) > -1) {
                    filtered.push(item);
                }
            });

            return filtered;
        };

    }]);