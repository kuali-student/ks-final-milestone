'use strict';

angular.module('regCartApp')
    .controller('MainCtrl',
    function ($scope, TermsService, APP_URL) {
        console.log('In Main Controller');

        $scope.appUrl = APP_URL.replace('/services/', '/');

        $scope.terms = TermsService.query({active:true}, function () {
            if(!$scope.termId){ // if the termId isn't set, defualt to spring 2012
                $scope.termId= 'kuali.atp.2012Spring';
            }
            // default to the current term
            /* Commenting this out for now. In real world use below
            angular.forEach($scope.terms, function (term) {
                if (term.currentTerm) {
                    $scope.termId = term.termId;
                }
            });
            */
        });
    });
