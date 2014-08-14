'use strict';

angular.module('regCartApp')
    .service('SearchService', ['ServiceUtilities', 'URLS', function SearchService(ServiceUtilities, URLS) {

        var searchCacheTTL = 30; // Seconds to keep search results cached
        var searches = []; // Cache of recent searches


        /**
         * Search for courses - uses a combination of client-side caching and REST call to the server API.
         *
         * @returns {{query: Function}}
         */
        this.searchForCourses = function () {
            var me = this;
            return {
                query: function(params, successCallback, errorCallback) {
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

                        me.searchForCoursesFromServer().query(params, function(results) {
                            // Cache the search results
                            lastSearch.results = results;
                            searches.push(lastSearch);

                            successCallback(results);
                        }, errorCallback);
                    } else {
                        console.log('- Returning Cached Results (valid for ' + (searchCacheTTL - (timestamp - lastSearch.timestamp)) + 's) ');
                        // The cached search results are still good, return them out.
                        successCallback(lastSearch.results);
                    }
                }
            };
        };


        // Server API Methods

        this.searchForCoursesFromServer = function() {
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
        };

        this.getCourse = function() {
            return ServiceUtilities.getData(URLS.scheduleOfClasses + '/courseOfferingDetails');
        };

    }]);