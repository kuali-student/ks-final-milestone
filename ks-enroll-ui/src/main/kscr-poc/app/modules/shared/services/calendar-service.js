'use strict';

angular.module('kscrPocApp')
    .factory('calendarService', function ($resource, apiService) {
        return $resource(apiService.get('personschedulecalendar'), {}, {
            query: {
                method: 'GET',
                cache: false,
                isArray: true
            }
        });
    });
