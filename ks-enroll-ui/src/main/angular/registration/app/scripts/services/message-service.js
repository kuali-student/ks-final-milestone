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

        this.getMessage = function (messageKey) {
            var deferred = $q.defer();

            this.getMessages()
                .then(function(messages) {
                    for (var i = 0; i < messages.length; i++) {
                        if (messages[i].messageKey === messageKey) {
                            deferred.resolve(messages[i].message);
                            return;
                        }
                    }

                    deferred.reject('message not found: ' + messageKey);
                }, function(error) {
                    deferred.reject('message not found: ' + messageKey + ' - ' + error);
                });

            return deferred.promise;
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
