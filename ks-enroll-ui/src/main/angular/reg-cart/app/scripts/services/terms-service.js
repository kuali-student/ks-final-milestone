'use strict';
angular.module('regCartApp')
    .factory('TermsService', ['$resource', 'APP_URL', function ($resource, APP_URL) {
    return $resource(APP_URL + 'ScheduleOfClassesService/terms', {}, {
        query:{
            method:'GET',
            cache:true,
            isArray:true
        }
    });
}]);
//angular.module('regCartApp')
//    .service('TermsService', function TermsService($resource) {
//        this.getTerms = function () {
//            return $resource('json/static-terms.json', {}, {
//                query:{method:'GET', cache:false, isArray:true}
//            });
//        };
//
//    });
