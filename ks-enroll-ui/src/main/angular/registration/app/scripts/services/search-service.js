'use strict';

angular.module('regCartApp')
    .service('SearchService', ['ServiceUtilities', 'URLS', function SearchService(ServiceUtilities, URLS) {

        this.searchForCourses = function () {
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
        };

        this.getCourse = function() {
            return ServiceUtilities.getData(URLS.scheduleOfClasses + '/courseOfferingInfo');
        };

    }]);