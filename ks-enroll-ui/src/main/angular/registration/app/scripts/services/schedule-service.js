'use strict';

angular.module('regCartApp')
    .factory('ScheduleService', ['$resource', 'APP_URL', function ($resource, APP_URL) {
        return $resource(APP_URL + 'CourseRegistrationClientService/personschedule', {}, {
            query:{
                method:'GET',
                cache:false,
                isArray:true
            }
        });
    }]);