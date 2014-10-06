'use strict';

// Term REST Resource Factory
angular.module('regCartApp').factory('Term', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.scheduleOfClasses + '/terms', {}, {
        checkEligibility: {
            method: 'GET',
            url: APP_URL + URLS.scheduleOfClasses + '/termEligibility'
        }
    });
}]);

angular.module('regCartApp')
    .service('TermsService', ['$q', 'Term', 'URLS', 'DEFAULT_TERM', function TermsService($q, Term, URLS, DEFAULT_TERM) {

        var selectedTerm = null;

        this.getSelectedTerm = function() {
            return selectedTerm;
        };

        this.setSelectedTerm = function(term) {
            selectedTerm = term;
        };

        this.getTermId = function() {
            var term = this.getSelectedTerm();
            if (term !== null) {
                return term.termId;
            }

            return null;
        };

        this.getTermByCode = function(code) {
            var retTerm = null;
            if (angular.isArray(terms)) {
                angular.forEach(terms, function(term) {
                    if (term.termCode === code) {
                        retTerm = term;
                    }
                });
            }

            return retTerm;
        };

        this.getTermById = function(id) {
            var retTerm = null;
            if (angular.isArray(terms)) {
                angular.forEach(terms, function(term) {
                    if (term.termId === id) {
                        retTerm = term;
                    }
                });
            }

            return retTerm;
        };


        // Cache of terms.
        var terms = null;

        this.getTerms = function () {
            var deferred = $q.defer();

            if (terms !== null) {
                deferred.resolve(terms);
            } else {
                this.getTermsFromServer().then(function(result) {
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

        // Cache of student eligibility for term
        var termEligibility = {};

        this.isStudentEligibleForTerm = function(term) {
            var deferred = $q.defer();

            if (term.termId !== DEFAULT_TERM) {
                console.log('-- Term eligibility check bypassed - term != default term');
                termEligibility[term.termId] = { isEligible: true };
            }

            if (angular.isDefined(termEligibility[term.termId])) {
                deferred.resolve(termEligibility[term.termId]);
            } else {
                this.checkStudentEligibilityForTerm(term).then(function (response) {
                    response.isEligible = response.isEligible || false;
                    termEligibility[term.termId] = response;

                    term.eligibilityMessages = [];
                    if (angular.isDefined(response.messages) && angular.isArray(response.messages)) {
                        term.eligibilityMessages = response.messages;
                    }

                    deferred.resolve(response);
                }, function(error) {
                    deferred.reject(error);
                });
            }

            return deferred.promise;
        };


        // Server API Methods

        this.checkStudentEligibilityForTerm = function (term) {
            return Term.checkEligibility({
                termId: term.termId
            }).$promise;
        };

        this.getTermsFromServer = function () {
            return Term.query({
                termId: null,
                termCode: null,
                active: true
            }).$promise;
        };

    }]);