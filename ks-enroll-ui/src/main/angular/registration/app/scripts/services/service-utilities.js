'use strict';

angular.module('regCartApp')
    .service('ServiceUtilities', ['$resource', 'APP_URL', function ServiceUtilities($resource, APP_URL) {

        this.getData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'GET', cache: false, isArray: false}
            });
        };

        this.deleteData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'DELETE', cache: false, isArray: false}
            });
        };

        this.postData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'POST', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.putData = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'PUT', cache: false, isArray: false,
                    transformRequest: function (obj) {
                        return transformRequest(obj);
                    }}
            });
        };

        this.getArray = function (url) {
            return $resource(APP_URL + url, {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };

        /*
         Global method for seeing if a course is present in a list of courses
         */
        this.isCourseInList = function (c, list) {
            var course = c;
            if (angular.isString(c)) {
                course = {
                    masterLprId: c,
                    regGroupId: c
                };
            }

            var inList = false;
            for (var i = 0; i < list.length; i++) {
                if ((angular.isDefined(list[i].regGroupId) && list[i].regGroupId === course.regGroupId) ||
                    (angular.isDefined(list[i].masterLprId) && list[i].masterLprId === course.masterLprId)) {

                    inList = true;
                    break;
                }
            }

            return inList;
        };

        function transformRequest(obj) {
            var str = [];
            for (var p in obj) {
                if (obj[p]) {
                    str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
                }
            }
            return str.join('&');
        }
    }]
    );