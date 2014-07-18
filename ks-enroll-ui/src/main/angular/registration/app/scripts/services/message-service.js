'use strict';

angular.module('regCartApp')
    .service('MessageService', ['$q', '$resource', function MessageService($q, $resource) {

        // Cached messages
        var messages = null;

        this.getMessages = function () {
            var deferred = $q.defer(); // Create the promise object

            if (messages !== null) {
                // Return out the cached messages
                deferred.resolve(messages);
            } else {
                // The messages aren't cached, load them
                this.loadMessages().query({}, function(result) {
                    // Cache the messages
                    messages = result;
                    deferred.resolve(messages);
                }, function(error) {
                    // Report out the error
                    deferred.reject(error);
                });
            }

            // Return the handle on the promise
            return deferred.promise;
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

        this.loadMessages = function() {
            /*
             Currently we are getting the messages from a static json file...
             this will likely be replaced by a RESTful service call...
             */
            return $resource('json/messages.json', {}, {
                query: { method: 'GET', cache: false, isArray: true }
            });
        };
    }]);
