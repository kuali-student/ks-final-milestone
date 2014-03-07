'use strict';

angular.module('regCartApp')
    .service('TermsService', ['$resource', 'APP_URL', function TermsService($resource, APP_URL) {


        var termId = 'kuali.atp.2012Spring';   // default value

        this.getTermId = function () {
            return termId;
        };

        this.setTermId = function (value) {
            termId = value;
        };

        this.getTermsFromServer = function () {
            return $resource(APP_URL + 'ScheduleOfClassesService/terms', {}, {
                query: { method: 'GET', cache: true, isArray: true }
            });
        };

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
