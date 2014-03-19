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
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'PUT', cache: false, isArray: false, transformRequest: function(obj) {
                        var str = [];
                        for(var p in obj){
                            if(obj[p]){
                                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                            }
                        }
                        return str.join("&");
                    }}
            });
        };

        this.dropRegistrationGroup = function () {
            return $resource(APP_URL + 'CourseRegistrationClientService/dropRegistrationGroup', {}, {
                query: {method: 'DELETE', cache: false, isArray: false}
            });
        };


    }]);