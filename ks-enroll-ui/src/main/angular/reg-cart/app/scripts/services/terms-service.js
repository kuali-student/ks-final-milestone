'use strict';
angular.module('regCartApp')
    .factory('TermsService', function ($resource) {
        return $resource('http://localhost:8081/ks-with-rice-embedded-dev/services/ScheduleOfClassesService/terms', {}, {
            query:{
                method:'GET',
                cache:true,
                isArray:true
            }
        });
    });
//angular.module('regCartApp')
//    .service('TermsService', function TermsService($resource) {
//        this.getTerms = function () {
//            return $resource('json/static-terms.json', {}, {
//                query:{method:'GET', cache:false, isArray:true}
//            });
//        };
//
//    });
