'use strict';

angular.module('regCartApp').factory('loginInterceptor', function ($q, $injector) {
    return {
        'responseError': function (rejection) {
            if (rejection.status === 0) {
                //For failed connections, try to log in again and refresh the page
                console.log('Failed to execute request - trying to login');
                var LoginService = $injector.get('LoginService');
                //This should be removed for production. In the future, we should handle logouts in a user-friendly way.
//                LoginService.logOnAsAdmin().query({userId: 'admin', password: 'admin'}, function () {
//                    //After logging in, reload the page.
//                    console.log('Logged in, reloading page.');
//                    var $state = $injector.get('$state');
//                    $state.reload();
//                }, function () {
//                    //After logging in, reload the page.
//                    console.log('Not Logged in, reloading page.');
//                    var $state = $injector.get('$state');
//                    $state.reload();
//                });
            }
            return $q.reject(rejection);
        }
    };
});
