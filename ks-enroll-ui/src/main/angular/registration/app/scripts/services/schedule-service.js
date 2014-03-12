'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$resource', 'APP_URL', function ScheduleService($resource, APP_URL) {

        this.getScheduleFromServer = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/personschedule', {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };
        this.updateSchedule = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };

        this.updateScheduleItem = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.dropRegistrationGroup = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/dropRegistrationGroup', {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };


    }]);