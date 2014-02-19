'use strict';

var cartServiceModule = angular.module('regCartApp');

cartServiceModule.controller('ScheduleCtrl', ['$scope', 'ScheduleService',
    function ($scope, ScheduleService) {
        $scope.schedules = ScheduleService.getCart().query({ person: 'admin' }, function (result){
            console.log('got schedule data back');
            console.log(result);
        })
    }]);