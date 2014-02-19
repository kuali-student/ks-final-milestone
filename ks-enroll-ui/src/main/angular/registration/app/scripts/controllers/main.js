'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, TermsService, APP_URL) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.terms = TermsService.query({active:true}, function () {
            // default to the current term
            angular.forEach($scope.terms, function (term) {
                if (term.currentTerm) {
                    $scope.termId = term.termId;
                }
            });
        });
    });
