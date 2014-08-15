'use strict';

angular.module('regCartApp')
    .service('LoginService', ['URLS', 'ServiceUtilities', function LoginService(URLS, ServiceUtilities) {

        this.logOnAsAdmin = function () {
            return ServiceUtilities.getData(URLS.developmentLogin + '/login');
        };

        this.logout = function () {
            return ServiceUtilities.getData(URLS.developmentLogin + '/logout');
        };

    }]);