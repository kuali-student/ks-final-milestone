'use strict';

// Messages Resource Factory
angular.module('regCartApp').factory('Messages', ['$resource', function($resource) {
    // Currently we are getting the messages from a static json file...
    // this will likely be replaced by a RESTful service call...
    return $resource('json/messages.json');
}]);

angular.module('regCartApp')
    .service('MessageService', ['$q', 'Messages', function MessageService($q, Messages) {

        // Cached messages
        var messages = null;

        this.getMessages = function () {
            var deferred = $q.defer(); // Create the promise object

            if (messages !== null) {
                // Return out the cached messages
                deferred.resolve(messages);
            } else {
                // The messages aren't cached, load them
                this.loadMessages().then(function(result) {
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
                    if (angular.isDefined(messages[messageKey])) {
                        deferred.resolve(messages[messageKey]);
                    } else {
                        deferred.reject('message not found: ' + messageKey);
                    }
                }, function(error) {
                    deferred.reject('message not found: ' + messageKey + ' - ' + error);
                });

            return deferred.promise;
        };

        this.loadMessages = function() {
            return Messages.get().$promise;
        };
    }]);
