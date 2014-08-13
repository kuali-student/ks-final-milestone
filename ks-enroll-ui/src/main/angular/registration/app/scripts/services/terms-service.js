'use strict';

angular.module('regCartApp')
    .service('TermsService', ['$q', 'ServiceUtilities', 'URLS', function TermsService($q, ServiceUtilities, URLS) {

        var termId = null;   // default value

        this.getTermId = function () {
            return termId;
        };

        this.setTermId = function (value) {
            termId = value;
        };

        this.getTermNameForTermId = function (terms, termId) {
            var retTermName;
            angular.forEach(terms, function (term) {
                if (term.termId === termId) {
                    retTermName = term.termName;
                }
            });
            return retTermName;
        };


        // Cache of terms.
        var terms = null;

        this.getTerms= function () {
            var deferred = $q.defer();

            if (terms !== null) {
                deferred.resolve(terms);
            } else {
                this.getTermsFromServer().query({termCode: null, active: true}, function(result) {
                    // Cache the terms
                    terms = result;
                    deferred.resolve(result);
                }, function(error) {
                    // Report out the error
                    deferred.reject(error);
                });
            }

            return deferred.promise;
        };


        // Server API Methods

        this.checkStudentEligibilityForTerm = function () {
            return ServiceUtilities.getData(URLS.scheduleOfClasses + '/checkStudentEligibilityForTerm');
        };

        this.getTermsFromServer = function () {
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/terms');
        };

    }]);