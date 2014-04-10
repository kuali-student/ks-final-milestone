'use strict';
angular.module('regCartApp', [
  'configuration',
  'ngAnimate',
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router',
  'ui.bootstrap'
]).config([
  '$stateProvider',
  '$urlRouterProvider',
  '$httpProvider',
  function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('/myCart');
    $stateProvider.state('root', {
      templateUrl: 'partials/main.html',
      controller: 'MainCtrl'
    }).state('root.cart', {
      url: '/myCart',
      templateUrl: 'partials/cart.html',
      controller: 'CartCtrl'
    }).state('root.schedule', {
      url: '/mySchedule',
      templateUrl: 'partials/schedule.html',
      controller: 'ScheduleCtrl'
    }).state('root.additionalOptions', {
      url: '/options',
      templateUrl: 'partials/additionalOptions.html'
    });
    //Add the  login interceptor to all service calls
    $httpProvider.interceptors.push('loginInterceptor');
  }
]);
'use strict';
angular.module('configuration', []).value('APP_URL', 'ks-with-rice-bundled-dev/services/');
'use strict';
angular.module('regCartApp').controller('MainCtrl', [
  '$scope',
  'TermsService',
  'ScheduleService',
  'GlobalVarsService',
  'APP_URL',
  function ($scope, TermsService, ScheduleService, GlobalVarsService, APP_URL) {
    console.log('In Main Controller');
    $scope.appUrl = APP_URL.replace('/services/', '/');
    // update the term name if the termId changes
    $scope.$watch('termId', function (newValue) {
      if (newValue) {
        $scope.termName = TermsService.getTermNameForTermId($scope.terms, newValue);
        ScheduleService.getScheduleFromServer().query({ termId: newValue }, function (result) {
          console.log('called rest service to get schedule data - in main.js');
          GlobalVarsService.updateScheduleCounts(result);
          $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
          // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
          $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
          // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
          $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
          // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
          $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
          // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
          $scope.showWaitlistedSection = GlobalVarsService.getShowWaitlistedSection;
        });
      }
    });
    $scope.terms = TermsService.getTermsFromServer().query(({ active: true }, function (result) {
      $scope.termId = 'kuali.atp.2012Spring';
      TermsService.setTermId($scope.termId);
      //FIXME Term service is just a service handle, don't put business logic in it!!!
      $scope.termName = TermsService.getTermNameForTermId(result, $scope.termId);
    }));  /**
        ScheduleService.getScheduleFromServer().query({termId: $scope.termId }, function (result) {
            console.log('called rest service to get schedule data - in main.js');
            GlobalVarsService.updateScheduleCounts(result);
            $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;   // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
            $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount; // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
        });
         **/
  }
]);
'use strict';
angular.module('regCartApp').controller('CartCtrl', [
  '$scope',
  '$modal',
  'CartService',
  'ScheduleService',
  'GlobalVarsService',
  '$timeout',
  function ($scope, $modal, CartService, ScheduleService, GlobalVarsService, $timeout) {
    $scope.oneAtATime = false;
    var hasCartBeenLoaded = false;
    $scope.cartResults = { items: [] };
    //Add a watch so that when termId changes, the cart is reloaded with the new termId
    $scope.$watch('termId', function (newValue) {
      console.log('term id has changed');
      $scope.cartResults.items.splice(0, $scope.cartResults.items.length);
      if ($scope.userMessage) {
        if ($scope.userMessage.txt) {
          $scope.removeUserMessage();
        }
      }
      if (newValue) {
        hasCartBeenLoaded = true;
        loadCart(newValue);
      }
    });
    // this method loads the cart and kicks off polling if needed
    function loadCart(termId) {
      CartService.getCart().query({ termId: termId }, function (theCart) {
        $scope.cart = theCart;
        // right now theCart is a mix of processing and cart items
        var cartItems = [];
        var startPolling = false;
        // prime to false
        var submittedCartId;
        // we must assume that the items are all from one cart
        // if there are any processing items in the cart we need to start polling
        for (var i = 0; i < $scope.cart.items.length; i++) {
          var item = $scope.cart.items[i];
          if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === 'processing') {
            item.status = 'processing';
            var newItem = $.extend(true, {}, item);
            $scope.cartResults.items.push(newItem);
            // set cart and all items in cart to processing
            $scope.cartResults.state = 'kuali.lpr.trans.state.processing';
            $scope.cartResults.status = 'processing';
            // set the overall status to processing
            startPolling = true;
            submittedCartId = item.cartId;
          } else {
            cartItems.push(item);
          }
        }
        $scope.cart.items = cartItems;
        if (startPolling) {
          cartPoller(submittedCartId);  // each items has a reference back to the cartId
        }
      });
    }
    $scope.getStatusMessageFromStatus = function (status) {
      var retStatus = '';
      if (status === 'success') {
        retStatus = ' - Success!';
      }
      if (status === 'error' || status === 'action') {
        retStatus = ' - Failed!';
      }
      return retStatus;
    };
    $scope.addRegGroupToCart = function () {
      $scope.courseCode = $scope.courseCode.toUpperCase();
      addCourseToCart($scope.cart.cartId, $scope.courseCode, $scope.termId, $scope.regCode, null, null, null, null);
    };
    $scope.$on('addCourseToCart', function (event, cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
      console.log('Received event addCourseToCart ', event);
      addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits);
    });
    function addCourseToCart(cartId, courseCode, termId, regGroupCode, regGroupId, gradingOptionId, credits) {
      CartService.addCourseToCart().query({
        cartId: cartId,
        courseCode: courseCode,
        termId: termId,
        regGroupCode: regGroupCode,
        regGroupId: regGroupId,
        gradingOptionId: gradingOptionId,
        credits: credits
      }, function (response) {
        console.log('Searched for course: ' + $scope.courseCode + ', Term: ' + $scope.termId);
        $scope.userMessage = {
          txt: 'Course Added Successfully',
          type: 'success'
        };
        $scope.courseCode = '';
        $scope.regCode = '';
        $scope.cart.items.unshift(response);
      }, function (error) {
        console.log('CartId:', cartId);
        if (error.status === 404) {
          //Reg group was not found
          $scope.userMessage = {
            txt: error.data,
            type: 'error'
          };
        } else if (error.status === 400) {
          console.log('CartId: ', cartId);
          //Additional options are required
          $modal.open({
            backdrop: 'static',
            templateUrl: 'partials/additionalOptions.html',
            resolve: {
              item: function () {
                return error.data;
              },
              cartId: function () {
                return cartId;
              }
            },
            controller: [
              '$rootScope',
              '$scope',
              'item',
              'cartId',
              function ($rootScope, $scope, item, cartId) {
                console.log('Controller for modal... Item: ', item);
                $scope.newCartItem = item;
                $scope.newCartItem.credits = $scope.newCartItem.creditOptions[0];
                $scope.newCartItem.grading = 'kuali.resultComponent.grade.letter';
                $scope.dismissAdditionalOptions = function () {
                  console.log('Dismissing credits and grading');
                  $scope.$close(true);
                };
                $scope.saveAdditionalOptions = function () {
                  console.log('Save credits and grading for cartId:', cartId);
                  $rootScope.$broadcast('addCourseToCart', cartId, $scope.newCartItem.courseCode, $scope.newCartItem.termId, $scope.newCartItem.regGroupCode, $scope.newCartItem.regGroupId, $scope.newCartItem.grading, $scope.newCartItem.credits);
                  $scope.$close(true);
                };
              }
            ]
          });
        } else {
          console.log('Error with adding course', error.data.consoleMessage);
          $scope.userMessage = {
            txt: error.data.genericMessage,
            type: error.data.type,
            detail: error.data.detailedMessage
          };
        }
      });
    }
    $scope.cancelNewCartItem = function () {
      $scope.newCartItem = null;
      $scope.showNew = false;
    };
    $scope.deleteCartItem = function (index) {
      var item = $scope.cart.items[index];
      var actionLinks = item.actionLinks;
      var deleteUri;
      angular.forEach(actionLinks, function (actionLink) {
        if (actionLink.action === 'removeItemFromCart') {
          deleteUri = actionLink.uri;
        }
      });
      // call the backend service here to persist something
      CartService.removeItemFromCart(deleteUri).query({}, function (response) {
        $scope.cart.items.splice(index, 1);
        var actionUri;
        angular.forEach(response.actionLinks, function (actionLink) {
          if (actionLink.action === 'undoDeleteCourse') {
            actionUri = actionLink.uri;
          }
        });
        $scope.userMessage = {
          'txt': item.courseCode + '(' + item.regGroupCode + ') ' + 'has been successfully removed from your cart.',
          'actionLink': actionUri,
          'linkText': 'Undo',
          'type': 'success'
        };
        $scope.userActionSuccessful = true;
      });
    };
    $scope.invokeActionLink = function (actionLink) {
      $scope.userActionSuccessful = false;
      // call the backend service here to persist something
      CartService.invokeActionLink(actionLink).query({}, function (response) {
        $scope.cart.items.unshift(response);
        $scope.userMessage = { txt: '' };
      });
    };
    $scope.editCartItem = function (cartItem) {
      $scope.newCredits = cartItem.credits;
      $scope.newGrading = cartItem.grading;
      cartItem.status = 'editing';
    };
    $scope.cancelEditItem = function (cartItem) {
      cartItem.status = '';
    };
    $scope.updateCartItem = function (cartItem, newCredits, newGrading) {
      console.log('Updating cart item. Grading: ' + newGrading + ', credits: ' + newCredits);
      CartService.updateCartItem().query({
        cartId: $scope.cart.cartId,
        cartItemId: cartItem.cartItemId,
        credits: newCredits,
        gradingOptionId: newGrading
      }, function (newCartItem) {
        cartItem.credits = newCartItem.credits;
        console.log('old: ' + cartItem.grading + ' To: ' + newCartItem.grading);
        cartItem.grading = newCartItem.grading;
        console.log('old: ' + cartItem.grading + ' To: ' + newCartItem.grading);
        cartItem.status = '';
        cartItem.actionLinks = newCartItem.actionLinks;
        $scope.creditTotal = creditTotal();
        cartItem.alertMessage = {
          txt: 'Changes saved successfully',
          type: 'success'
        };
      });
    };
    $scope.addCartItemToWaitlist = function (cartItem) {
      console.log('Adding cart item to waitlist... ');
      ScheduleService.registerForRegistrationGroup().query({
        courseCode: cartItem.courseCode,
        regGroupId: cartItem.regGroupId,
        gradingOption: cartItem.grading,
        credits: cartItem.credits,
        allowWaitlist: true
      }, function (registrationResponseInfo) {
        cartItem.state = 'kuali.lpr.trans.item.state.processing';
        cartItem.status = 'processing';
        cartItem.cartItemId = registrationResponseInfo.registrationResponseItems[0].registrationRequestItemId;
        //cartItem.waitlistedStatus = true;
        //cartItem.statusMessage = GlobalVarsService.getCorrespondingMessageFromStatus('waitlisted');
        $timeout(function () {
        }, 250);
        // delay for 250 milliseconds
        console.log('Just waited 250, now start the polling');
        cartPoller(registrationResponseInfo.registrationRequestId);
      });
    };
    $scope.removeAlertMessage = function (cartItem) {
      cartItem.alertMessage = null;
    };
    $scope.removeUserMessage = function () {
      $scope.userMessage.txt = null;
      $scope.userMessage.linkText = null;
    };
    $scope.register = function () {
      CartService.submitCart().query({ cartId: $scope.cart.cartId }, function (registrationResponseInfo) {
        $scope.userMessage = { txt: '' };
        console.log('Submitted cart. RegReqId[' + registrationResponseInfo.registrationRequestId + ']');
        $scope.cartResults = $.extend(true, {}, $scope.cart);
        $scope.cart.items.splice(0, $scope.cart.items.length);
        // set cart and all items in cart to processing
        $scope.cartResults.state = 'kuali.lpr.trans.state.processing';
        $scope.cartResults.status = 'processing';
        // set the overall status to processing
        $scope.creditTotal = 0;
        // your cart will always update to zero upon submit
        angular.forEach($scope.cartResults.items, function (item) {
          item.state = 'kuali.lpr.trans.item.state.processing';
          item.status = 'processing';
        });
        $timeout(function () {
        }, 250);
        // delay for 250 milliseconds
        console.log('Just waited 250, now start the polling');
        cartPoller(registrationResponseInfo.registrationRequestId);
      });
    };
    // This method is used to update the states/status of each cart item by polling the server
    var cartPoller = function (registrationRequestId) {
      $scope.pollingCart = false;
      // prime to false
      $timeout(function () {
        CartService.getRegistrationStatus().query({ regReqId: registrationRequestId }, function (regResponseResult) {
          $scope.cart.state = regResponseResult.state;
          var locCart = $scope.cart;
          // for debug only
          angular.forEach(regResponseResult.responseItemResults, function (responseItem) {
            angular.forEach($scope.cartResults.items, function (item) {
              if (item.cartItemId === responseItem.registrationRequestItemId) {
                item.state = responseItem.state;
                item.type = responseItem.type;
                // we need to update the status, which is used to controll css
                item.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
                item.statusMessage = responseItem.message;
              }
              // If anything is still processing, continue polling
              if (responseItem.state === 'kuali.lpr.trans.item.state.processing') {
                $scope.pollingCart = true;
              }
            });
          });
          if ($scope.pollingCart) {
            console.log('Continue polling');
            cartPoller(registrationRequestId);
          } else {
            console.log('Stop polling');
            $scope.cart.status = '';
            // set the overall status to nothing... which is the default i guess
            $scope.cartResults.state = 'kuali.lpr.trans.state.succeeded';
            $scope.cartResults.successCount = 0;
            $scope.cartResults.waitlistCount = 0;
            $scope.cartResults.errorCount = 0;
            angular.forEach($scope.cartResults.items, function (item) {
              switch (item.status) {
              case 'success':
                $scope.cartResults.successCount++;
                break;
              case 'waitlist':
                $scope.cartResults.waitlistCount++;
                item.statusMessage = GlobalVarsService.getCorrespondingMessageFromStatus(item.status);
                break;
              case 'error':
                $scope.cartResults.errorCount++;
                break;
              }
            });
            // After all the processing is complete, get the final Schedule counts.
            ScheduleService.getScheduleFromServer().query({ termId: $scope.termId }, function (result) {
              console.log('called rest service to get schedule data - in main.js');
              GlobalVarsService.updateScheduleCounts(result);
              $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
              // notice that i didn't put the (). in the ui call: {{registeredCredits()}}
              $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;  // notice that i didn't put the (). in the ui call: {{registeredCourseCount()}}
            });
          }
        });
      }, 1000);  // right now we're going to wait 1 second per poll
    };
    $scope.removeCartResultItem = function (cartResultItem) {
      $scope.cartResults.items.splice(cartResultItem, 1);
    };
    function creditTotal() {
      if (!$scope.cart) {
        return 0;
      }
      var totalNumber = 0;
      for (var i = 0; i < $scope.cart.items.length; i++) {
        totalNumber = totalNumber + Number($scope.cart.items[i].credits);
      }
      return totalNumber;
    }
    $scope.$watchCollection('cart.items', function () {
      $scope.creditTotal = creditTotal();
    });
    $scope.showBadge = function (cartItem) {
      //console.log("Cart Item Grading: " + JSON.stringify(cartItem));
      return cartItem.gradingOptions[cartItem.grading] != 'Letter';
    };
    $scope.editing = function (cartItem) {
      return cartItem.status == 'editing';
    };
  }
]);
'use strict';
var cartServiceModule = angular.module('regCartApp');
cartServiceModule.controller('ScheduleCtrl', [
  '$scope',
  '$modal',
  'ScheduleService',
  'GlobalVarsService',
  function ($scope, $modal, ScheduleService, GlobalVarsService) {
    $scope.getSchedules = GlobalVarsService.getSchedule;
    $scope.registeredCredits = GlobalVarsService.getRegisteredCredits;
    $scope.registeredCourseCount = GlobalVarsService.getRegisteredCourseCount;
    $scope.waitlistedCredits = GlobalVarsService.getWaitlistedCredits;
    $scope.waitlistedCourseCount = GlobalVarsService.getWaitlistedCourseCount;
    $scope.numberOfDroppedWailistedCourses = 0;
    $scope.$watch('termId', function (newValue) {
      console.log('term id has changed');
      if ($scope.userMessage) {
        if ($scope.userMessage.txt) {
          $scope.removeUserMessage();
        }
      }
      if ($scope.waitlistUserMessage) {
        if ($scope.waitlistUserMessage.txt) {
          $scope.removeWaitlistUserMessage();
        }
      }
      ScheduleService.getScheduleFromServer().query({ termId: newValue }, function (result) {
        console.log('called rest service to get schedule data - in schedule.js');
        GlobalVarsService.updateScheduleCounts(result);
      });
    });
    $scope.openDropConfirmation = function (index, course) {
      console.log('Open drop confirmation');
      course.dropping = true;
      $scope.index = index;
      $scope.course = course;
    };
    $scope.cancelDropConfirmation = function (course) {
      course.dropping = false;
    };
    $scope.dropRegistrationGroup = function (index, course) {
      console.log('Open drop confirmation for registered course');
      ScheduleService.dropRegistrationGroup().query({ masterLprId: course.masterLprId }, function () {
        course.dropping = false;
        course.dropped = true;
        //                $scope.getSchedules()[0].registeredCourseOfferings.splice(index, 1);
        //                GlobalVarsService.updateScheduleCounts($scope.getSchedules());
        GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits));
        GlobalVarsService.setRegisteredCourseCount(parseInt(GlobalVarsService.getRegisteredCourseCount()) - 1);
        course.statusMessage = {
          txt: '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> dropped successfully',
          type: 'success'
        };
      }, function (error) {
        //course.dropping = false;
        $scope.userMessage = {
          txt: error.data,
          type: 'error'
        };
      });
    };
    $scope.dropFromWaitlist = function (index, course) {
      console.log('Open drop confirmation for waitlist course');
      ScheduleService.dropFromWaitlist().query({ masterLprId: course.masterLprId }, function () {
        $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses + 1;
        $scope.showWaitlistMessages = true;
        console.log('TEST ' + $scope.numberOfDroppedWailistedCourses);
        course.dropping = false;
        course.dropped = true;
        //                $scope.getSchedules()[0].waitlistCourseOfferings.splice(index, 1);
        //                GlobalVarsService.updateScheduleCounts($scope.getSchedules());
        GlobalVarsService.setWaitlistedCredits(parseFloat(GlobalVarsService.getWaitlistedCredits()) - parseFloat(course.credits));
        GlobalVarsService.setWaitlistedCourseCount(parseInt(GlobalVarsService.getWaitlistedCourseCount()) - 1);
        course.statusMessage = {
          txt: 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully',
          type: 'success'
        };
      }, function (error) {
        course.statusMessage = {
          txt: error.data,
          type: 'error'
        };
      });
    };
    $scope.editScheduleItem = function (course) {
      $scope.newCredits = course.credits;
      $scope.newGrading = course.gradingOptionId;
      course.editing = true;
    };
    $scope.cancelEditScheduleItem = function (course) {
      course.editing = false;
    };
    $scope.updateScheduleItem = function (course, newCredits, newGrading) {
      console.log('Updating registered course:');
      console.log(newCredits);
      console.log(newGrading);
      ScheduleService.updateScheduleItem().query({
        courseCode: course.courseCode,
        regGroupCode: course.regGroupCode,
        masterLprId: course.masterLprId,
        credits: newCredits,
        gradingOptionId: newGrading
      }, function (scheduleItemResult) {
        console.log(scheduleItemResult);
        GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
        course.credits = scheduleItemResult.credits;
        course.gradingOptionId = scheduleItemResult.gradingOptionId;
        //                GlobalVarsService.updateScheduleCounts($scope.getSchedules());
        course.editing = false;
        course.statusMessage = {
          txt: 'Changes saved successfully',
          type: 'success'
        };
      }, function (error) {
        //course.editing = false;
        course.statusMessage = {
          txt: error.data,
          type: 'error'
        };
      });
    };
    $scope.updateWaitlistItem = function (course, newCredits, newGrading) {
      console.log('Updating waitlisted course:');
      console.log(newCredits);
      console.log(newGrading);
      ScheduleService.updateWaitlistItem().query({
        courseCode: course.courseCode,
        regGroupCode: course.regGroupCode,
        masterLprId: course.masterLprId,
        credits: newCredits,
        gradingOptionId: newGrading
      }, function (scheduleItemResult) {
        console.log(scheduleItemResult);
        GlobalVarsService.setRegisteredCredits(parseFloat(GlobalVarsService.getRegisteredCredits()) - parseFloat(course.credits) + parseFloat(scheduleItemResult.credits));
        course.credits = scheduleItemResult.credits;
        course.gradingOptionId = scheduleItemResult.gradingOptionId;
        //                GlobalVarsService.updateScheduleCounts($scope.getSchedules());
        course.editing = false;
        course.statusMessage = {
          txt: 'Changes saved successfully',
          type: 'success'
        };
      }, function (error) {
        //course.editing = false;
        course.statusMessage = {
          txt: error.data,
          type: 'error'
        };
      });
    };
    $scope.removeStatusMessage = function (course) {
      course.statusMessage = null;
    };
    $scope.removeUserMessage = function () {
      $scope.userMessage.txt = null;
      $scope.userMessage.linkText = null;
    };
    $scope.removeWaitlistStatusMessage = function (course) {
      course.statusMessage = null;
      $scope.numberOfDroppedWailistedCourses = $scope.numberOfDroppedWailistedCourses - 1;
      if ($scope.numberOfDroppedWailistedCourses == 0) {
        $scope.showWaitlistMessages = false;
      }
    };
    $scope.showBadge = function (course) {
      return course.gradingOptions[course.gradingOptionId] != 'Letter';
    };
  }
]);
'use strict';
angular.module('regCartApp').service('CartService', [
  '$resource',
  'APP_URL',
  function CartService($resource, APP_URL) {
    this.getCart = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/searchForCart', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.getGradingOptions = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/getStudentRegistrationOptions', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.addCourseToCart = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/addCourseToCart', {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'POST',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            var str = [];
            for (var p in obj) {
              if (obj[p]) {
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
              }
            }
            return str.join('&');
          }
        }
      });
    };
    this.removeItemFromCart = function ($actionLink) {
      return $resource(APP_URL + $actionLink, {}, {
        query: {
          method: 'DELETE',
          cache: false,
          isArray: false
        }
      });
    };
    this.invokeActionLink = function ($actionLink) {
      return $resource(APP_URL + $actionLink, {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.updateCartItem = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/updateCartItem', {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'PUT',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            var str = [];
            for (var p in obj) {
              if (obj[p]) {
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
              }
            }
            return str.join('&');
          }
        }
      });
    };
    this.submitCart = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/submitCart', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.getRegistrationStatus = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/getRegistrationStatus', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.undoDeleteCourse = function () {
      return $resource(APP_URL + 'CourseRegistrationCartClientService/undoDeleteCourse', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
  }
]);
'use strict';
angular.module('regCartApp').service('TermsService', [
  '$resource',
  'APP_URL',
  function TermsService($resource, APP_URL) {
    var termId = 'kuali.atp.2012Spring';
    // default value
    this.getTermId = function () {
      return termId;
    };
    this.setTermId = function (value) {
      termId = value;
    };
    this.getTermsFromServer = function () {
      return $resource(APP_URL + 'ScheduleOfClassesService/terms', {}, {
        query: {
          method: 'GET',
          cache: true,
          isArray: true
        }
      });
    };
    this.getTermNameForTermId = function (terms, termId) {
      var retTermName;
      angular.forEach(terms, function (term) {
        if (term.termId === termId) {
          retTermName = term.termName;
        }
      });
      return retTermName;
    };
  }
]);
//angular.module('regCartApp')
//    .service('TermsService', function TermsService($resource) {
//        this.getTerms = function () {
//            return $resource('json/static-terms.json', {}, {
//                query:{method:'GET', cache:false, isArray:true}
//            });
//        };
//
//    });
'use strict';
angular.module('regCartApp').service('ScheduleService', [
  '$resource',
  'APP_URL',
  function ScheduleService($resource, APP_URL) {
    this.getScheduleFromServer = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/personschedule', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: true
        }
      });
    };
    this.updateSchedule = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: true
        }
      });
    };
    this.updateScheduleItem = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/updateScheduleItem', {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'PUT',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            var str = [];
            for (var p in obj) {
              if (obj[p]) {
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
              }
            }
            return str.join('&');
          }
        }
      });
    };
    this.updateWaitlistItem = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/updateWaitlistEntry', {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'PUT',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            var str = [];
            for (var p in obj) {
              if (obj[p]) {
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
              }
            }
            return str.join('&');
          }
        }
      });
    };
    this.dropRegistrationGroup = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/dropRegistrationGroup', {}, {
        query: {
          method: 'DELETE',
          cache: false,
          isArray: false
        }
      });
    };
    this.dropFromWaitlist = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/dropFromWaitlistEntry', {}, {
        query: {
          method: 'DELETE',
          cache: false,
          isArray: false
        }
      });
    };
    this.registerForRegistrationGroup = function () {
      return $resource(APP_URL + 'CourseRegistrationClientService/registerreggroup', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
  }
]);
'use strict';
angular.module('regCartApp').service('LoginService', [
  '$resource',
  'APP_URL',
  function LoginService($resource, APP_URL) {
    this.logOnAsAdmin = function () {
      return $resource(APP_URL + 'DevelopmentLoginClientService/login', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };  //        this.logout =
        //            function () {
        //                return $resource(APP_URL.replace("/services/","/") + 'j_spring_security_logout', {}, {
        //                    query:{method:'GET', cache:false, isArray:false}
        //                });
        //            };
  }
]);
'use strict';
angular.module('regCartApp').factory('loginInterceptor', [
  '$q',
  '$injector',
  '$window',
  function ($q, $injector, $window) {
    return {
      'responseError': function (rejection) {
        if (rejection.status === 0) {
          //For failed connections, try to log in again and refresh the page
          console.log('Failed to execute request - trying to login');
          var LoginService = $injector.get('LoginService');
          //This should be removed for production. In the future, we should handle logouts in a user-friendly way.
          LoginService.logOnAsAdmin().query({
            userId: 'admin',
            password: 'admin'
          }, function () {
            //After logging in, reload the page.
            console.log('Logged in, reloading page.');
            $window.location.reload();
          }, function () {
            //After logging in, reload the page.
            console.log('Not Logged in, reloading page.');
            $window.location.reload();
          });
        }
        return $q.reject(rejection);
      }
    };
  }
]);
'use strict';
angular.module('regCartApp').service('GlobalVarsService', function GlobalVarsService() {
  var registeredCredits = 0;
  var registeredCourseCount = 0;
  var waitlistedCredits = 0;
  var waitlistedCourseCount = 0;
  var schedule;
  var processingStates = [
      'kuali.lpr.trans.item.state.processing',
      'kuali.lpr.trans.state.processing'
    ];
  var successStates = [
      'kuali.lpr.trans.state.succeeded',
      'kuali.lpr.trans.item.state.succeeded'
    ];
  var waitlistStates = ['kuali.lpr.trans.item.state.waitlist'];
  var errorStates = [
      'kuali.lpr.trans.state.failed',
      'kuali.lpr.trans.item.state.failed'
    ];
  var actionStates = ['kuali.lpr.trans.item.state.waitlistActionAvailable'];
  this.getRegisteredCredits = function () {
    return registeredCredits;
  };
  this.setRegisteredCredits = function (value) {
    registeredCredits = value;
  };
  this.getRegisteredCourseCount = function () {
    return registeredCourseCount;
  };
  this.setRegisteredCourseCount = function (value) {
    registeredCourseCount = value;
  };
  this.getWaitlistedCredits = function () {
    return waitlistedCredits;
  };
  this.setWaitlistedCredits = function (value) {
    waitlistedCredits = value;
  };
  this.getWaitlistedCourseCount = function () {
    return waitlistedCourseCount;
  };
  this.setWaitlistedCourseCount = function (value) {
    waitlistedCourseCount = value;
  };
  this.getSchedule = function () {
    return schedule;
  };
  this.setSchedule = function (value) {
    schedule = value;
  };
  // In this method we pass in a state and it returns a status
  this.getCorrespondingStatusFromState = function (state) {
    var retStatus = 'new';
    if (processingStates.indexOf(state) >= 0) {
      retStatus = 'processing';
    } else if (successStates.indexOf(state) >= 0) {
      retStatus = 'success';
    } else if (errorStates.indexOf(state) >= 0) {
      retStatus = 'error';
    } else if (waitlistStates.indexOf(state) >= 0) {
      retStatus = 'waitlist';
    } else if (actionStates.indexOf(state) >= 0) {
      retStatus = 'action';
    }
    return retStatus;
  };
  /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param scheduleList
         */
  this.updateScheduleCounts = function (scheduleList) {
    //Calculate credit count, course count and grading option count
    var creditCount = 0;
    var courses = 0;
    var waitlistCreditCount = 0;
    var waitlistCourses = 0;
    this.setSchedule(scheduleList);
    angular.forEach(scheduleList, function (schedule) {
      angular.forEach(schedule.registeredCourseOfferings, function (course) {
        creditCount += parseFloat(course.credits);
        courses++;
        var gradingOptionCount = 0;
        //grading options are an object (map) so there's no easy way to get the object size without this code
        angular.forEach(course.gradingOptions, function () {
          gradingOptionCount++;
        });
        course.gradingOptionCount = gradingOptionCount;
      });
      angular.forEach(schedule.waitlistCourseOfferings, function (course) {
        waitlistCreditCount += parseFloat(course.credits);
        waitlistCourses++;
        var gradingOptionCount = 0;
        //grading options are an object (map) so there's no easy way to get the object size without this code
        angular.forEach(course.gradingOptions, function () {
          gradingOptionCount++;
        });
        course.gradingOptionCount = gradingOptionCount;
      });
    });
    this.setRegisteredCourseCount(courses);
    this.setRegisteredCredits(creditCount);
    this.setWaitlistedCredits(waitlistCreditCount);
    this.setWaitlistedCourseCount(waitlistCourses);
  };
  // In this method we pass in a status and it returns a message to display
  this.getCorrespondingMessageFromStatus = function (status) {
    var statusMessage = '';
    if (status == 'waitlist') {
      statusMessage = 'If a seat becomes available you will be registered automatically';
    }
    return statusMessage;
  };
});