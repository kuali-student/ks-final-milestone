'use strict';

// Course Details REST Resource Factory
angular.module('regCartApp').factory('CourseDetails', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.scheduleOfClasses + '/courseOfferingDetails', {}, {
        query: {
            method: 'GET',
            url: APP_URL + URLS.scheduleOfClasses + '/search',
            isArray: true
        }
    });
}]);

angular.module('regCartApp')
    .service('SearchService', ['$q', 'CourseDetails', function SearchService($q, CourseDetails) {

        var searchCacheTTL = 30; // Seconds to keep search results cached
        var searches = []; // Cache of recent searches


        /**
         * Search for courses - uses a combination of client-side caching and REST call to the server API.
         *
         * @returns {promise}
         */
        this.searchForCourses = function (params) {
            var deferred = $q.defer();

            var timestamp = Math.round(+new Date() / 1000),
                lastSearch = null,
                validSearches = [];

            // Iterate through the list of cached searches, keep the not stale ones, & try to match the current search parameters against the valid cache
            angular.forEach(searches, function(search) {
                if (angular.isDefined(search.timestamp) && (timestamp - search.timestamp) < searchCacheTTL) {
                    validSearches.push(search);

                    if (search.criteria === params.criteria && search.termId === params.termId) {
                        lastSearch = search;
                    }
                }
            });

            // Reset the cache to the still-fresh list
            searches = validSearches;


            if (lastSearch === null) {
                // Run the search against the server
                lastSearch = angular.copy(params);
                lastSearch.timestamp = timestamp; // Store the current timestamp (in seconds) on the lastSearch

                return CourseDetails.query(params).$promise
                    .then(function(results) {
                        // Cache the search results
                        lastSearch.results = results;
                        searches.push(lastSearch);
                    });
            } else {
                console.log('- Returning Cached Results (valid for ' + (searchCacheTTL - (timestamp - lastSearch.timestamp)) + 's) ');
                // The cached search results are still good, return them out.
                deferred.resolve(lastSearch.results);
            }

            return deferred.promise;
        };


        // Server API Methods

        this.getCourse = function(courseOfferingId, courseCode) {
            return CourseDetails.get({
                courseCode: courseCode,
                courseOfferingId: courseOfferingId
            }).$promise;
        };

    }]);