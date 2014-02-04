'use strict';

angular.module('regCartApp')
    .service('TermsService', function TermsService($resource) {
        this.getTerms = function () {
            return $resource('json/static-terms.json', {}, {
                query:{method:'GET', cache:false, isArray:true}
            });
        };

    });
