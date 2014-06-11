'use strict';

angular.module('regCartApp')
    .service('LoginService', ['ServiceUtilities', 'URLS', function (ServiceUtilities, URLS) {

        this.logOnAsAdmin = function () {
                return ServiceUtilities.getData(URLS.developmentLogin+'/login');
            };

        this.logout = function () {
                return ServiceUtilities.getData(URLS.developmentLogin+'/logout');
            };

    }]);