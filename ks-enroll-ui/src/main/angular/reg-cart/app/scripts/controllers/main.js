'use strict';

angular.module('regCartApp')
    .controller('MainCtrl', ['$scope', 'TermsService',
    function ($scope, TermsService) {
        $scope.terms = TermsService.query({active:true}, function () {
            // default to the current term
            angular.forEach($scope.terms, function(term){
               if(term.currentTerm){
                   $scope.termId = term.termId;
               }
            });
        });
    }]);
