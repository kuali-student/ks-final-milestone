'use strict';

angular.module('regCartApp')
    .controller('MainCtrl', ['$scope', 'TermsService',
    function ($scope, TermsService) {
        $scope.terms = TermsService.getTerms().query({}, function () {
            $scope.termId = $scope.terms[0].termId;
        });

    }]);
