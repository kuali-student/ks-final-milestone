'use strict';

angular.module('regCartApp')
    .service('ScheduleService', ['$resource', 'APP_URL', function ScheduleService($resource, APP_URL) {
    this.getCart = function () {
        return $resource(APP_URL + 'CourseRegistrationClientService/personschedule', {}, {
            query:{method:'GET', cache:false, isArray:true}
        });
    };
    this.updateCartItem = function () {
        return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
            query:{method:'GET', cache:false, isArray:true}
        });
    };

}]);