'use strict';

angular.module('regCartApp')
    .service('LoginService', function LoginService($resource, APP_URL) {
        this.logOnAsAdmin =
            function () {
                return $resource(APP_URL + 'DevelopmentLoginClientService/login', {}, {
                    query: {method: 'GET', cache: false, isArray: false}
                });
            };
//        this.logout =
//            function () {
//                return $resource(APP_URL.replace("/services/","/") + 'j_spring_security_logout', {}, {
//                    query:{method:'GET', cache:false, isArray:false}
//                });
//            };
    });