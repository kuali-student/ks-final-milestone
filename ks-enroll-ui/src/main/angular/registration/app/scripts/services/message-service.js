'use strict';

angular.module('regCartApp')
    .service('MessageService', ['$resource', function MessageService($resource) {

        this.getMessages = function () {
            /*
             Currently we are getting the messages from a static json file...
             this will likely be replaced by a RESTful service call...
             */
            return $resource('json/messages.json', {}, {
                query: {method: 'GET', cache: false, isArray: true}
            });
        };

        this.getMessage = function (messages, messageKey) {
            var message = '';
            angular.forEach(messages, function (msgObj) {
                if (msgObj.messageKey === messageKey) {
                    message = msgObj.message;
                }
            });
            return message;
        };
    }]);
