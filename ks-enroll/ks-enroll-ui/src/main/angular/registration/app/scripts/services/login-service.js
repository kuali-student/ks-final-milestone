'use strict';

// Development Login REST Resource Factory
angular.module('regCartApp').factory('Login', ['$resource', 'APP_URL', 'URLS', function($resource, APP_URL, URLS) {
    return $resource(APP_URL + URLS.developmentLogin, {}, {
        login: {
            method: 'GET',
            url: APP_URL + URLS.developmentLogin + '/login'
        },
        logout: {
            method: 'GET',
            url: APP_URL + URLS.developmentLogin + '/logout'
        }
    });
}]);

angular.module('regCartApp')
    .service('LoginService', ['Login', function LoginService(Login) {

        this.login = function (username, password) {
            return Login.login({
                userId: username,
                password: password
            }).$promise;
        };

        this.logout = function () {
            return Login.logout().$promise;
        };

    }]);