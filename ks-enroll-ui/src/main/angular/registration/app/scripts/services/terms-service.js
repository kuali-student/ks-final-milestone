'use strict';

angular.module('regCartApp')
    .service('TermsService', ['ServiceUtilities', 'URLS', 'DEFAULT_TERM', function TermsService(ServiceUtilities, URLS, DEFAULT_TERM) {

        var termId = DEFAULT_TERM;   // default value

        this.checkStudentEligibilityForTerm = function () {
            return ServiceUtilities.getData(URLS.scheduleOfClasses + '/checkStudentEligibilityForTerm');
        };

        this.getTermId = function () {
            return termId;
        };

        this.setTermId = function (value) {
            termId = value;
        };

        this.getTermsFromServer = function () {
            return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/terms');
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

    }]);