'use strict';

angular.module('regCartApp')
    .service('SearchService', ['ServiceUtilities', 'URLS', '$resource', function SearchService(ServiceUtilities, URLS, $resource) {

        this.searchForCourses = function () {
            // This is the actual query to the server
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
        };

        this.getCourse = function() {
            return ServiceUtilities.getData(URLS.scheduleOfClasses + '/courseOfferingInfo');
        };

    }]);