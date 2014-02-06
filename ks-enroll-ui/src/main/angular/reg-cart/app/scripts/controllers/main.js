'use strict';

angular.module('regCartApp')
    .controller('MainCtrl', ['$scope', 'TermsService',
    function ($scope, TermsService) {
        $scope.terms = TermsService.query({active:true}, function () {
            $scope.termId = $scope.terms[0].termId;
        });

    }]);
