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
    // Define some of the more commonly used components
    var myCart = {
        templateUrl: 'partials/cart.html',
        controller: 'CartCtrl'
      }, mySchedule = {
        templateUrl: 'partials/schedule.html',
        controller: 'ScheduleCtrl'
      };
    $stateProvider.state('root', {
      abstract: true,
      url: '?term',
      views: {
        root: {
          templateUrl: 'partials/main.html',
          controller: 'MainCtrl'
        }
      }
    }).state('root.schedule', {
      url: '/mySchedule',
      views: {
        '': mySchedule,
        mycart: myCart,
        schedule: mySchedule
      }
    }).state('root.cart', {
      url: '/myCart',
      views: {
        '': mySchedule,
        mycart: myCart,
        schedule: mySchedule
      }
    }).state('root.search', {
      url: '/search?page&displayLimit&predicate&reverse&filters',
      views: {
        '': { templateUrl: 'partials/search.html' },
        mycart: myCart,
        schedule: mySchedule
      }
    }).state('root.search.results', {
      url: '/{searchCriteria}',
      views: {
        '': {
          templateUrl: 'partials/searchResults.html',
          controller: 'SearchCtrl'
        }
      }
    }).state('root.search.details', {
      url: '/{searchCriteria}/{code}/{id}',
      templateUrl: 'partials/searchDetails.html',
      controller: 'SearchDetailsCtrl'
    });
    ;
    //Add the  login interceptor to all service calls
    $httpProvider.interceptors.push('loginInterceptor');
  }
]);
;
'use strict';
angular.module('configuration', []).value('APP_URL', 'ks-with-rice-bundled-dev/services/').value('DEFAULT_TERM', 'kuali.atp.2012Fall').value('FEATURE_TOGGLES', { searchDetails: { directAddToWaitlist: false } });
;
'use strict';
angular.module('regCartApp').constant('URLS', {
  scheduleOfClasses: 'ScheduleOfClassesClientService',
  courseRegistration: 'CourseRegistrationClientService',
  courseRegistrationCart: 'CourseRegistrationCartClientService',
  developmentLogin: 'DevelopmentLoginClientService'
}).constant('STATE', function () {
  // Assigned as a var so they can be used in the groupings
  var lprStates = {
      failed: 'kuali.lpr.trans.state.failed',
      processing: 'kuali.lpr.trans.state.processing',
      succeeded: 'kuali.lpr.trans.state.succeeded',
      item: {
        failed: 'kuali.lpr.trans.item.state.failed',
        processing: 'kuali.lpr.trans.item.state.processing',
        succeeded: 'kuali.lpr.trans.item.state.succeeded',
        waitlist: 'kuali.lpr.trans.item.state.waitlist',
        waitlistActionAvailable: 'kuali.lpr.trans.item.state.waitlistActionAvailable'
      }
    };
  return {
    lpr: lprStates,
    action: [lprStates.item.waitlistActionAvailable],
    error: [
      lprStates.failed,
      lprStates.item.failed
    ],
    processing: [
      lprStates.processing,
      lprStates.item.processing
    ],
    success: [
      lprStates.succeeded,
      lprStates.item.succeeded
    ],
    waitlist: [lprStates.item.waitlist]
  };
}()).constant('STATUS', {
  action: 'action',
  editing: 'editing',
  error: 'error',
  new: 'new',
  processing: 'processing',
  success: 'success',
  waitlist: 'waitlist'
}).constant('COURSE_TYPES', {
  registered: 'registered',
  waitlisted: 'waitlist',
  cart: 'cart'
}).constant('GRADING_OPTION', {
  audit: 'kuali.resultComponent.grade.audit',
  letter: 'kuali.resultComponent.grade.letter',
  passFail: 'kuali.resultComponent.grade.passFail'
}).constant('ACTION_LINK', {
  removeItemFromCart: 'removeItemFromCart',
  undoDeleteCourse: 'undoDeleteCourse'
}).constant('VALIDATION_ERROR_TYPE', {
  maxCredits: 'kuali.lpr.trans.message.credit.load.exceeded',
  timeConflict: 'kuali.lpr.trans.message.time.conflict',
  reggroupNotOffered: 'kuali.lpr.trans.message.reggroup.notoffered',
  waitlistAvailable: 'kuali.lpr.trans.message.waitlist.available',
  waitlistWaitlisted: 'kuali.lpr.trans.message.waitlist.waitlisted',
  waitlistFull: 'kuali.lpr.trans.message.waitlist.full',
  waitlistNotOffered: 'kuali.lpr.trans.message.waitlist.not.offered',
  transactionException: 'kuali.lpr.trans.message.exception',
  transactionItemException: 'kuali.lpr.trans.item.message.exception',
  courseNotOpen: 'kuali.lpr.trans.message.course.not.open',
  courseGradeIncomplete: 'kuali.lpr.trans.message.course.grade.incomplete'
}).constant('GENERAL_ERROR_TYPE', {
  noRegGroup: 'noRegGroup',
  courseNotFound: 'kuali.cr.cart.message.course.code.not.found'
}).constant('VALIDATION_SUCCESS_TYPE', {
  waitlistStudentRemoved: 'kuali.lpr.trans.message.waitlist.student.removed',
  waitlistUpdated: 'kuali.lpr.trans.message.waitlist.options.updated',
  courseUpdated: 'kuali.lpr.trans.message.course.updated',
  courseDropped: 'kuali.lpr.trans.message.course.dropped',
  personRegistered: 'kuali.lpr.trans.message.person.registered'
}).constant('DAY_CONSTANTS', {
  monday: 'M',
  tuesday: 'Tu',
  wednesday: 'W',
  thursday: 'Th',
  friday: 'F',
  saturday: 'Sa',
  sunday: 'Su',
  dayArray: [
    'M',
    'Tu',
    'W',
    'Th',
    'F',
    'Sa',
    'Su'
  ]
});
;
'use strict';
angular.module('regCartApp').constant('COURSE_DETAILS', {
  tabs: [
    {
      id: 'time',
      selected: true,
      name: 'Time',
      fields: [
        'days',
        'time'
      ]
    },
    {
      id: 'instr',
      selected: false,
      name: 'Instr',
      fields: ['instructor']
    },
    {
      id: 'seatsLoc',
      selected: false,
      name: 'Loc/Seats',
      fields: [
        'location',
        'seatsOpen'
      ],
      wide: true
    }
  ],
  selectedTab: 'time',
  selectEvent: 'toggleAO',
  fieldOptions: {
    time: { right: true },
    instructor: { block: true },
    seatsOpen: {
      right: true,
      append: '&nbsp;available'
    }
  },
  icons: 'additionalInfo',
  getSections: function (searchDetails) {
    var sections = [];
    for (var i = 0; i < searchDetails.length; i++) {
      var header = searchDetails[i].activity;
      var newAoType = true;
      for (var j = 0; j < sections.length; j++) {
        if (sections[j].header === header) {
          newAoType = false;
          sections[j].details.push(searchDetails[i]);
          break;
        }
      }
      if (newAoType) {
        var detailContainer = {
            header: header,
            details: []
          };
        detailContainer.details.push(searchDetails[i]);
        sections.push(detailContainer);
      }
    }
    return sections;
  },
  setDefaultField: function (searchDetails) {
    for (var i = 0; i < searchDetails.length; i++) {
      searchDetails[i].defaultField = searchDetails[i].aoId;
    }
  }
});
;
'use strict';
angular.module('regCartApp').constant('COURSE_SEARCH_FACETS', [
  {
    label: 'Seats',
    id: 'seatsAvailable',
    autoCollapse: false,
    optionsProvider: function (results) {
      var itemsWithSeats = 0;
      angular.forEach(results, function (item) {
        if (item.seatsAvailable > 0) {
          itemsWithSeats++;
        }
      });
      var options = [];
      if (itemsWithSeats > 0) {
        options.push({
          label: 'Seats available',
          value: 'seatsAvailable',
          count: itemsWithSeats
        });
      }
      return options;
    },
    filter: function (item) {
      // Since there is only 1 option possible, there is no need to check against any selected facets.
      return item.seatsAvailable > 0;
    }
  },
  {
    label: 'Credits',
    id: 'creditOptions',
    optionsKey: 'creditOptions',
    refine: function (originalOptions) {
      var options = [];
      // Bucket all credit options into integer values where 1 matches 1 <= X < 2.
      // Furthermore, bucket everything larger than 6 into a 6+ option.
      angular.forEach(originalOptions, function (option) {
        var newValue = Math.floor(option.value), newLabel = newValue;
        // Enforce the 6+ limit
        if (newValue > 6) {
          newValue = 6;
          newLabel = '6+';
        }
        // Check to see if this new option has already been handled
        var existingOption = null;
        for (var i = 0; i < options.length; i++) {
          if (options[i].value.indexOf(newValue) !== -1) {
            existingOption = options[i];
            break;
          }
        }
        if (existingOption === null) {
          // The option has not yet been handled. Create it.
          option.label = newLabel;
          option.count = 0;
          // Reset the count since it'll be retallied later.
          // New credit option values are stored as an array since 1 option can match multiple values.
          option.value = [option.value];
          if (option.value.indexOf(newValue) === -1) {
            option.value.push(newValue);
          }
          options.push(option);
        } else {
          // The option has already been handled. Make sure it contains the value of the current option.
          if (existingOption.value.indexOf(option.value) === -1) {
            existingOption.value.push(newValue);
          }
          existingOption.label = newLabel;
        }
      });
      return options;
    }
  },
  {
    label: 'Course Level',
    id: 'courseLevel',
    optionsKey: 'courseLevel'
  },
  {
    label: 'Course Prefix',
    id: 'coursePrefix',
    optionsKey: 'coursePrefix'
  }
]).factory('CourseSearchFacets', [
  'COURSE_SEARCH_FACETS',
  'SearchFacetService',
  function (COURSE_SEARCH_FACETS, SearchFacetService) {
    return SearchFacetService.initFacets(COURSE_SEARCH_FACETS);
  }
]);
;
'use strict';
angular.module('regCartApp').controller('MainCtrl', [
  '$scope',
  '$location',
  '$state',
  '$modal',
  'APP_URL',
  'DEFAULT_TERM',
  'GlobalVarsService',
  'LoginService',
  'TermsService',
  'ScheduleService',
  'CartService',
  'MessageService',
  function MainCtrl($scope, $location, $state, $modal, APP_URL, DEFAULT_TERM, GlobalVarsService, LoginService, TermsService, ScheduleService, CartService, MessageService) {
    console.log('>> MainCtrl');
    $scope.appUrl = APP_URL.replace('/services/', '/');
    $scope.userId = GlobalVarsService.getUserId;
    $scope.terms = [];
    $scope.term = TermsService.getSelectedTerm();
    // Globally used selected term
    $scope.studentIsEligibleForTerm = true;
    // Top-level check whether student is eligible to register for the selected term
    // Load up the available terms
    TermsService.getTerms().then(function (terms) {
      $scope.terms = terms;
      // Make sure the app is sync'd up with the state now that the terms are available
      syncWithState();
    });
    // Persist the new term value in the state when it is changed
    $scope.$watch('term', function (newValue, oldValue) {
      if (newValue && newValue !== oldValue) {
        console.log('Term Changed: ' + newValue.termId + ', was ' + (oldValue ? oldValue.termId : null));
        persistTermInRoute(newValue);
      }
    });
    // Listen for any state changes from ui-router. This is where the termId is persisted from.
    $scope.$on('$stateChangeSuccess', function () {
      syncWithState();
    });
    // Listen for the termIdChanged event that is fired when a term has been changed & processed
    $scope.$on('termIdChanged', function (event, newValue) {
      // Go and get the schedule for the new term
      ScheduleService.getSchedule(newValue).then(function (result) {
        console.log('called rest service to get schedule data - in main.js');
        ScheduleService.updateScheduleCounts(result);
        $scope.cartCredits = CartService.getCartCredits;
        $scope.cartCourseCount = CartService.getCartCourseCount;
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;
        $scope.waitlistedCredits = ScheduleService.getWaitlistedCredits;
        $scope.waitlistedCourseCount = ScheduleService.getWaitlistedCourseCount;
        $scope.userId = GlobalVarsService.getUserId;
      });
    });
    // Load up the messages
    MessageService.getMessages();
    $scope.logout = function () {
      LoginService.logout().query({}, function () {
        //After logging in, reload the page.
        console.log('Logging out');
        location.reload();
      });
    };
    $scope.$on('sessionExpired', function () {
      console.log('Received event sessionExpired');
      $modal.open({
        backdrop: 'static',
        templateUrl: 'sessionExpired.html',
        controller: 'SessionCtrl',
        size: 'sm'
      });
    });
    // Update the UI routing state so it is available in the scope.
    $scope.$parent.uiState = $state.current.name;
    $scope.$on('$stateChangeStart', function (event, toState) {
      $scope.$parent.uiState = toState.name;
    });
    // Determine whether the search form should be shown on this page
    $scope.searchForm = function () {
      var searchForm = true;
      if ($state.current.name === 'root.search.details') {
        searchForm = false;  // hidden from mobile search details
      }
      return searchForm;
    };
    // Push the term value into the state parameters
    function persistTermInRoute(term, replace) {
      if (!term) {
        return;
      }
      if (angular.isUndefined($state.params.term) || $state.params.term !== term.termCode) {
        // The term needs to be persisted in the state.
        console.log('Persisting term in state: ' + term.termCode);
        // Put the new term in the state parameters
        var params = angular.copy($state.params);
        // Copy so it sees it as a new object
        params.term = term.termCode;
        var options = { notify: false };
        if (replace) {
          options.location = 'replace';
        }
        // Push the new term into the route
        $state.go($state.current.name, params, options).then(function () {
          // Sync up with the new term
          syncWithTerm(term);
        });
      }
    }
    function syncWithState() {
      var term = null;
      if (angular.isDefined($state.params.term)) {
        // Try to load the term from the state
        term = TermsService.getTermByCode($state.params.term);
      }
      if (term === null) {
        // The state term is not a known term
        if ($scope.term) {
          // Persist the already selected term (most likely from the user monkeying around with the URL || having an old term bookmarked)
          term = $scope.term;
        } else {
          // Persist the default term
          term = TermsService.getTermById(DEFAULT_TERM);
        }
        persistTermInRoute(term, true);
      } else if ($scope.term !== term) {
        syncWithTerm(term);
      }
    }
    // Sync the app with a term
    function syncWithTerm(term) {
      if (term !== null) {
        var oldTermId = $scope.term ? $scope.term.termId : null;
        // Store off the previous termId
        console.log('Syncing with term: ' + term.termId);
        // Persist the term in the TermsService
        TermsService.setSelectedTerm(term);
        $scope.term = term;
        // Check to see if the term is open or closed for registration
        console.log('- Checking term eligibility');
        TermsService.isStudentEligibleForTerm(term).then(function (isEligible, response) {
          $scope.studentIsEligibleForTerm = isEligible;
          // Broadcast a termIdChanged event notifying any listeners that the new termId is ready to go.
          // Doing it this way prevents unnecessary loading & processing from the term change
          // of things the user won't have access to or see.
          if ($scope.studentIsEligibleForTerm) {
            $scope.$broadcast('termIdChanged', term.termId, oldTermId);
          } else if (response) {
            GlobalVarsService.setUserId(response.userId);
          }
        }, function (error) {
          console.log('Error while checking if term is open for registration', error);
          $scope.studentIsEligibleForTerm = false;
        });
      }
    }
  }
]).controller('SessionCtrl', [
  '$scope',
  'LoginService',
  function SessionCtrl($scope, LoginService) {
    $scope.logout = function () {
      LoginService.logout().query({}, function () {
        //After logging in, reload the page.
        console.log('Session expired...logging out');
        location.reload();
      });
    };
  }
]);
'use strict';
angular.module('regCartApp').controller('CartCtrl', [
  '$scope',
  '$modal',
  '$timeout',
  'STATE',
  'STATUS',
  'GRADING_OPTION',
  'ACTION_LINK',
  'COURSE_TYPES',
  'GENERAL_ERROR_TYPE',
  'GlobalVarsService',
  'MessageService',
  'TermsService',
  'CartService',
  'ScheduleService',
  function ($scope, $modal, $timeout, STATE, STATUS, GRADING_OPTION, ACTION_LINK, COURSE_TYPES, GENERAL_ERROR_TYPE, GlobalVarsService, MessageService, TermsService, CartService, ScheduleService) {
    console.log('>> CartCtrl');
    $scope.states = STATE;
    $scope.statuses = STATUS;
    $scope.courseTypes = COURSE_TYPES;
    $scope.oneAtATime = false;
    $scope.isCollapsed = true;
    $scope.cartResults = { items: [] };
    // Add a listener to the termIdChanged event so that when termId changes, the cart is reloaded with the new termId
    $scope.$on('termIdChanged', function (event, newValue) {
      console.log('term id has changed - cart: ' + newValue);
      $scope.cartResults.items.splice(0, $scope.cartResults.items.length);
      if ($scope.userMessage && $scope.userMessage.txt) {
        $scope.removeUserMessage();
      }
      loadCart(newValue);
    });
    // Watch the cart items to ensure the global vars are up to date
    $scope.$watchCollection('cart.items', function (items) {
      $scope.creditTotal = creditTotal();
      if (items) {
        CartService.setCartCredits($scope.creditTotal);
      }
      CartService.setCartCourses(items);
    });
    // this method loads the cart and kicks off polling if needed
    function loadCart(termId) {
      CartService.getCart(termId).then(function (theCart) {
        $scope.cart = theCart;
        // right now theCart is a mix of processing and cart items
        var cartItems = [], submittedCartId = null;
        // we must assume that the items are all from one cart
        // if there are any processing items in the cart we need to start polling
        angular.forEach($scope.cart.items, function (item) {
          // Standardize the fields between cart & scheduled courses. This should really be done on the REST side.
          standardizeCourseData(item);
          if (GlobalVarsService.getCorrespondingStatusFromState(item.state) === STATUS.processing) {
            item.status = STATUS.processing;
            var newItem = angular.copy(item);
            $scope.cartResults.items.push(newItem);
            // set cart and all items in cart to processing
            $scope.cartResults.state = STATE.lpr.processing;
            $scope.cartResults.status = STATUS.processing;
            // set the overall status to processing
            submittedCartId = item.cartId;
          } else {
            cartItems.push(item);
          }
        });
        $scope.cart.items = cartItems;
        if (submittedCartId !== null) {
          cartPoller(submittedCartId);  // each items has a reference back to the cartId
        }
      });
    }
    // Initialize the cart
    if (TermsService.getTermId()) {
      loadCart(TermsService.getTermId());
    }
    /*
         Returns the course index
         */
    $scope.courseIndex = function (course) {
      if (!angular.isDefined(course.index) || !course.index) {
        course.index = GlobalVarsService.getCourseIndex(course);
      }
      return course.index;
    };
    $scope.getStatusMessageFromStatus = function (status) {
      var retStatus = '';
      if (status === STATUS.success) {
        retStatus = ' - Success!';
      } else if (status === STATUS.error || status === STATUS.action) {
        retStatus = ' - Failed';
      }
      return retStatus;
    };
    $scope.addRegGroupToCart = function () {
      addCourseToCart({
        courseCode: $scope.courseCode,
        regGroupCode: $scope.regCode
      });
    };
    // Listens for the "addCourseToCart" event and adds the course to the waitlist.
    $scope.$on('addCourseToCart', function (event, course, successCallback, errorCallback) {
      addCourseToCart(course, successCallback, errorCallback);
    });
    // Listens for the "directRegister" event and directly registers the regGroupId.
    $scope.$on('registerForCourse', function (event, course, successCallback, errorCallback) {
      registerForCourse(course, successCallback, errorCallback);
    });
    // Allows you to add a cartResultItem back into the cart. useful when a user wants to add a failed item back
    // into their cart.
    $scope.addCartItemToCart = function (cartItem) {
      addCourseToCart(cartItem);
    };
    function addCourseToCart(course, successCallback, errorCallback) {
      $scope.courseAdded = false;
      // reset cursor focus
      if (course.courseCode) {
        course.courseCode = course.courseCode.toUpperCase();
      }
      CartService.addCourseToCart().query({
        cartId: $scope.cart.cartId,
        termId: TermsService.getTermId(),
        courseCode: course.courseCode || null,
        regGroupCode: course.regGroupCode || null,
        regGroupId: course.regGroupId || null,
        gradingOptionId: course.gradingOptionId || null,
        credits: course.credits || null
      }, function (response) {
        console.log('Searched for course: ' + $scope.courseCode + ' ' + $scope.regCode + ', Term: ' + TermsService.getTermId());
        $scope.courseCode = '';
        $scope.regCode = '';
        standardizeCourseData(response);
        $scope.cart.items.unshift(response);
        // This part is responsible for glow effect: when the new item is added we want to highlight it and then fade the highlight away after 2 secs
        console.log('Started to glow...');
        // the highlighting fades in
        response.addingNewCartItem = true;
        // the highlighting stays for 2 secs and fades out
        $timeout(function () {
          response.addingNewCartItem = false;
        }, 2000);
        $scope.courseAdded = true;
        // refocus cursor back to course code
        if (angular.isFunction(successCallback)) {
          successCallback(response);
        }
      }, function (error) {
        var errorText;
        if (error.status === 404) {
          //Reg group was not found
          if (error.data.messageKey) {
            switch (error.data.messageKey) {
            case GENERAL_ERROR_TYPE.courseNotFound:
              errorText = error.data.courseCode + ' does not exist for ' + TermsService.getSelectedTerm().termName;
              break;
            default:
              errorText = MessageService.getMessage(error.data.messageKey);
            }
          } else {
            errorText = error.data;
          }
          $scope.userMessage = {
            txt: errorText,
            messageKey: GENERAL_ERROR_TYPE.noRegGroup,
            type: STATUS.error,
            course: course.courseCode
          };
          $scope.courseAdded = true;
          // refocus cursor back to course code
          if (angular.isFunction(errorCallback)) {
            errorCallback($scope.userMessage);
          }
        } else if (error.status === 400) {
          //Additional options are required
          showAdditionalOptionsModal(error.data, function (newCartItem) {
            addCourseToCart(newCartItem, successCallback, errorCallback);
          });
          $scope.courseAdded = true;  // refocus cursor back to course code
        } else {
          console.log('Error with adding course', error.data.consoleMessage);
          //Reg group is not in offered state
          errorText = error.data.genericMessage + ' for ' + TermsService.getSelectedTerm().termName;
          $scope.userMessage = {
            txt: errorText,
            messageKey: GENERAL_ERROR_TYPE.noRegGroup,
            type: error.data.type,
            detail: error.data.detailedMessage,
            course: course.courseCode + ' (' + course.regGroupCode + ')'
          };
          $scope.courseAdded = true;
          // refocus cursor back to course code
          if (angular.isFunction(errorCallback)) {
            errorCallback($scope.userMessage);
          }
        }
      });
    }
    $scope.cancelNewCartItem = function () {
      $scope.newCartItem = null;
      $scope.showNew = false;
    };
    /*
        Listens for the "deleteCartItem" event and calls the cart service to
        remove the given cart item from the cart.
         */
    $scope.$on('deleteCartItem', function (event, item) {
      var actionLinks = item.actionLinks, deleteUri = null;
      angular.forEach(actionLinks, function (actionLink) {
        if (actionLink.action === ACTION_LINK.removeItemFromCart) {
          deleteUri = actionLink.uri;
        }
      });
      // call the backend service here to persist something
      CartService.removeItemFromCart(deleteUri).query({}, function (response) {
        console.log('Cart item removed', response);
        $scope.cart.items.splice($scope.cart.items.indexOf(item), 1);
        var actionUri = null;
        angular.forEach(response.actionLinks, function (actionLink) {
          if (actionLink.action === ACTION_LINK.undoDeleteCourse) {
            actionUri = actionLink.uri;
          }
        });
        $scope.userMessage = {
          'txt': 'Removed <b>' + item.courseCode + '(' + item.regGroupCode + ')</b>',
          'actionLink': actionUri,
          'linkText': 'Undo',
          'type': STATUS.success
        };
      });
    });
    /*
         Listens for the "updateCourse" event and calls the cart update RESTful service.
         */
    $scope.$on('updateCourse', function (event, type, course, newCourse, successCallback, errorCallback) {
      if (type === COURSE_TYPES.cart) {
        console.log('Updating cart item');
        CartService.updateCartItem().query({
          cartId: $scope.cart.cartId,
          cartItemId: course.cartItemId,
          credits: newCourse.credits,
          gradingOptionId: newCourse.gradingOptionId
        }, function () {
          console.log('- Cart item successfully updated');
          $scope.creditTotal = creditTotal() - Number(course.credits) + Number(newCourse.credits);
          // This perhaps should run through the poller...
          if (angular.isFunction(successCallback)) {
            successCallback();
          }
        }, function (error) {
          console.log('- Error updating cart item', error);
          if (angular.isFunction(errorCallback)) {
            errorCallback(error.genericMessage);
          }
        });
      }
    });
    $scope.invokeActionLink = function (actionLink) {
      // call the backend service here to persist something
      CartService.invokeActionLink(actionLink).query({}, function (response) {
        standardizeCourseData(response);
        $scope.cart.items.unshift(response);
        $scope.removeUserMessage();
      });
    };
    $scope.addCartItemToWaitlist = function (cartItem) {
      console.log('Adding cart item to waitlist... ');
      ScheduleService.registerForRegistrationGroup().query({
        courseCode: cartItem.courseCode,
        regGroupId: cartItem.regGroupId,
        gradingOption: cartItem.gradingOptionId,
        credits: cartItem.credits,
        allowWaitlist: true
      }, function (registrationResponseInfo) {
        cartItem.state = STATE.lpr.item.processing;
        cartItem.status = STATUS.processing;
        cartItem.cartItemId = registrationResponseInfo.registrationRequestItems[0].id;
        $timeout(function () {
          console.log('Just waited 250, now start the polling');
          cartPoller(registrationResponseInfo.id);
        }, 250);
      });
    };
    $scope.removeAlertMessage = function (cartItem) {
      cartItem.alertMessage = null;
    };
    $scope.removeUserMessage = function () {
      if ($scope.userMessage) {
        $scope.userMessage.txt = null;
        $scope.userMessage.linkText = null;
      }
    };
    $scope.removeCartResultItem = function (cartResultItem) {
      $scope.cartResults.items.splice(cartResultItem, 1);
      calculateCartResultCounts();
    };
    $scope.register = function () {
      CartService.submitCart().query({ cartId: $scope.cart.cartId }, function (registrationResponseInfo) {
        console.log('Submitted cart. RegReqId[' + registrationResponseInfo.id + ']');
        $scope.removeUserMessage();
        // Move all of the cart over to the cartResults
        $scope.cartResults = angular.copy($scope.cart);
        $scope.cart.items.splice(0, $scope.cart.items.length);
        // set cart and all items in cart to processing
        $scope.showConfirmation = false;
        $scope.cartResults.state = STATE.lpr.processing;
        $scope.cartResults.status = STATUS.processing;
        // set the overall status to processing
        $scope.creditTotal = 0;
        // your cart will always update to zero upon submit
        angular.forEach($scope.cartResults.items, function (item) {
          item.state = STATE.lpr.item.processing;
          item.status = STATUS.processing;
        });
        $timeout(function () {
          console.log('Just waited 250, now start the polling');
          cartPoller(registrationResponseInfo.id);
        }, 250);
      });
    };
    // This method is used to update the STATE/status of each cart item by polling the server
    function cartPoller(registrationRequestId) {
      ScheduleService.pollRegistrationRequestStatus(registrationRequestId).then(function (response) {
        // Success
        console.log('- Stop polling - Success');
        $scope.cart.state = response.state;
        $scope.cart.status = '';
        // set the overall status to nothing... which is the default i guess
        $scope.cartResults.state = STATE.lpr.item.succeeded;
        // Map the data from the responseItems to the cartResultItems
        angular.forEach(response.responseItemResults, function (responseItem) {
          angular.forEach($scope.cartResults.items, function (cartResultItem) {
            if (cartResultItem.cartItemId === responseItem.registrationRequestItemId) {
              cartResultItem.state = responseItem.state;
              cartResultItem.type = responseItem.type;
              // we need to update the status, which is used to control css
              cartResultItem.status = GlobalVarsService.getCorrespondingStatusFromState(responseItem.state);
              cartResultItem.statusMessages = responseItem.messages;
              // Apply the waitlist message if the result was of that type
              switch (cartResultItem.status) {
              case STATUS.waitlist:
              case STATUS.action:
                //waitlist action available
                cartResultItem.waitlistMessage = GlobalVarsService.getCorrespondingMessageFromStatus(cartResultItem.status);
                break;
              }
            }
          });
        });
        // Calculate the result counts per status (clearing out initially to trigger the view to reset the values)
        calculateCartResultCounts(true);
        // After all the processing is complete, get the final Schedule counts.
        reloadSchedule();
      }, function (response) {
        // Error
        console.log('- Stop polling - Error: ', response);
      }, function (response) {
        // Notify
        console.log('- Continue polling');
        $scope.cart.state = response.state;
      });
    }
    /**
         * Calculate the counts of the cart results
         */
    function calculateCartResultCounts(resetValues) {
      if (resetValues) {
        $scope.cartResults.successCount = 0;
        $scope.cartResults.waitlistCount = 0;
        $scope.cartResults.waitlistedCount = 0;
        $scope.cartResults.errorCount = 0;
      }
      // Store as local variables so the $scope vars don't fire change events on increments
      var success = 0, waitlist = 0, waitlisted = 0, error = 0;
      angular.forEach($scope.cartResults.items, function (item) {
        switch (item.status) {
        case STATUS.success:
          success++;
          break;
        case STATUS.waitlist:
          // Waitlist action successful
          success++;
          // Also increment the successes
          waitlisted++;
          break;
        case STATUS.action:
          // Waitlist action available
          waitlist++;
          break;
        case STATUS.error:
          error++;
          break;
        }
      });
      // Set the counts into the scope
      $scope.cartResults.successCount = success;
      $scope.cartResults.waitlistCount = waitlist;
      $scope.cartResults.waitlistedCount = waitlisted;
      $scope.cartResults.errorCount = error;
    }
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
    // Direct register for a course
    function registerForCourse(course, successCallback, errorCallback) {
      if (!course.credits || !course.gradingOptionId) {
        showAdditionalOptionsModal(course, function (newCourse) {
          course.gradingOptionId = newCourse.gradingOptionId;
          course.credits = newCourse.credits;
          registerForCourse(course, successCallback, errorCallback);
        });
        return;
      }
      ScheduleService.registerForRegistrationGroup().query({
        courseCode: course.courseCode || null,
        regGroupId: course.regGroupId || null,
        gradingOption: course.gradingOptionId || null,
        credits: course.credits || null,
        allowWaitlist: course.allowWaitlist || true,
        allowRepeatedCourses: course.allowRepeatedCourses || false
      }, function (regRequest) {
        ScheduleService.pollRegistrationRequestStatus(regRequest.id).then(function (response) {
          var state = null;
          angular.forEach(response.responseItemResults, function (responseItem) {
            if (state === null) {
              state = responseItem.state;
            }
          });
          if (state === STATE.lpr.item.failed) {
            if (angular.isFunction(errorCallback)) {
              errorCallback(response);
            }
          } else {
            // After all the processing is complete, get the final Schedule counts.
            reloadSchedule();
            if (angular.isFunction(successCallback)) {
              successCallback(response);
            }
          }
        }, errorCallback);
      }, errorCallback);
    }
    // Reload the schedule & update the schedule counts
    function reloadSchedule() {
      ScheduleService.getSchedule(TermsService.getTermId(), true).then(function (result) {
        console.log('Called rest service to get schedule data - in cart.js');
        ScheduleService.updateScheduleCounts(result);
        $scope.registeredCredits = ScheduleService.getRegisteredCredits;
        $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;
      });
    }
    // Show the Additional Options Modal Dialog allowing the user to select the specific credit & grading option they would like.
    function showAdditionalOptionsModal(cartItem, callback) {
      $modal.open({
        backdrop: 'static',
        templateUrl: 'partials/additionalOptions.html',
        size: 'sm',
        resolve: {
          item: function () {
            return cartItem;
          }
        },
        controller: [
          '$scope',
          'item',
          function ($scope, item) {
            console.log('Controller for modal... Item: ', item);
            var defaultGradingOption = null;
            if (angular.isDefined(item.gradingOptions[GRADING_OPTION.letter])) {
              defaultGradingOption = GRADING_OPTION.letter;
            } else {
              angular.forEach(item.gradingOptions, function (v, k) {
                if (defaultGradingOption === null) {
                  defaultGradingOption = k;
                }
              });
            }
            item.gradingOptionId = item.newGrading = defaultGradingOption;
            item.credits = item.newCredits = item.creditOptions[0];
            $scope.newCartItem = item;
            $scope.newCartItem.editing = true;
            $scope.dismissAdditionalOptions = function () {
              console.log('Dismissing credits and grading');
              $scope.$close(true);
            };
            var submitted = false;
            $scope.saveAdditionalOptions = function (course) {
              if (!submitted) {
                // Only let the form be submitted once.
                submitted = true;
                course.editing = false;
                $scope.newCartItem.credits = $scope.newCartItem.newCredits;
                $scope.newCartItem.gradingOptionId = $scope.newCartItem.newGrading;
                callback($scope.newCartItem);
                $scope.$close(true);
              }
            };
          }
        ]
      });
    }
    function standardizeCourseData(course) {
      // Standardize the fields between cart & scheduled courses. This should really be done on the REST side.
      course.longName = course.courseTitle;
      course.gradingOptionId = course.grading;
    }
  }
]);
'use strict';
angular.module('regCartApp').controller('ScheduleCtrl', [
  '$scope',
  '$modal',
  '$timeout',
  'STATUS',
  'GRADING_OPTION',
  'COURSE_TYPES',
  'GlobalVarsService',
  'TermsService',
  'ScheduleService',
  'CartService',
  function ($scope, $modal, $timeout, STATUS, GRADING_OPTION, COURSE_TYPES, GlobalVarsService, TermsService, ScheduleService, CartService) {
    console.log('>> ScheduleCtrl');
    $scope.getSchedules = ScheduleService.getSchedules;
    $scope.registeredCredits = ScheduleService.getRegisteredCredits;
    $scope.registeredCourseCount = ScheduleService.getRegisteredCourseCount;
    $scope.waitlistedCredits = ScheduleService.getWaitlistedCredits;
    $scope.waitlistedCourseCount = ScheduleService.getWaitlistedCourseCount;
    $scope.userId = GlobalVarsService.getUserId;
    $scope.courseTypes = COURSE_TYPES;
    var numberOfDroppedWailistedCourses = 0, numberOfDroppedCourses = 0;
    /*
         Helper method to determine if the waitlist should be shown

         Any of these conditions will return a true:
         -- Student is waitlisted for more than 0 credits
         -- A waitlist message is on the screen
         */
    $scope.showWaitlist = function () {
      return $scope.waitlistedCredits() > 0 || numberOfDroppedWailistedCourses > 0;
    };
    /*
        Helper method to determine if the schedule should be shown

        Any of these conditions will return a true:
        -- The waitlist section is being shown
        -- Student is registered for more than 0 credits
        -- A registration message is on the screen
         */
    $scope.showSchedule = function () {
      return $scope.showWaitlist() || $scope.registeredCredits() > 0 || numberOfDroppedCourses > 0;
    };
    /*
         Helper method to determine if the calendar grid should be shown

         Student needs to have at least one non-TBA course: either in the
         cart, registered, or waitlisted.
         */
    $scope.showGrid = function () {
      var showGrid = false;
      //check the cart
      angular.forEach(CartService.getCartCourses(), function (course) {
        angular.forEach(course.schedule, function (schedule) {
          if (!showGrid) {
            var locationTime = schedule.activityOfferingLocationTime;
            for (var i = 0; i < locationTime.length; i++) {
              if (!locationTime[i].isTBA) {
                showGrid = true;
                break;
              }
            }
          }
        });
      });
      // if no non-TBA courses in the cart, check the schedule
      if (!showGrid) {
        angular.forEach(ScheduleService.getScheduledCourses(), function (course) {
          angular.forEach(course.activityOfferings, function (ao) {
            if (!showGrid) {
              var scheduleComponents = ao.scheduleComponents;
              for (var i = 0; i < scheduleComponents.length; i++) {
                if (!scheduleComponents[i].isTBA) {
                  showGrid = true;
                  break;
                }
              }
            }
          });
        });
      }
      return showGrid;
    };
    /*
         Listens for the "courseStatusMessageRemoved" event and removes the card for the given course.
         */
    $scope.$on('courseStatusMessageRemoved', function (event, type) {
      switch (type) {
      case COURSE_TYPES.waitlisted:
        numberOfDroppedWailistedCourses--;
        break;
      case COURSE_TYPES.registered:
        numberOfDroppedCourses--;
      }
    });
    /*
         Listens for the "dropCourse" event and calls the appropriate RESTful service depending on the course type.
         */
    $scope.$on('dropCourse', function (event, type, course, successCallback, errorCallback) {
      switch (type) {
      case COURSE_TYPES.waitlisted:
        console.log('Drop waitlisted course');
        executeDrop(type, course, successCallback, errorCallback, ScheduleService.dropFromWaitlist().query, { masterLprId: course.masterLprId });
        break;
      case COURSE_TYPES.registered:
        console.log('Drop registered course');
        executeDrop(type, course, successCallback, errorCallback, ScheduleService.dropRegistrationGroup().query, { masterLprId: course.masterLprId });
      }
    });
    /*
         Listens for the "updateCourse" event and calls the appropriate RESTful service depending on the course type.
         */
    $scope.$on('updateCourse', function (event, type, course, newCourse, successCallback, errorCallback) {
      switch (type) {
      case COURSE_TYPES.waitlisted:
        console.log('Update waitlisted course');
        executeUpdate(type, course, newCourse, successCallback, errorCallback, ScheduleService.updateWaitlistItem().query, {
          courseCode: course.courseCode,
          regGroupId: course.regGroupId,
          masterLprId: course.masterLprId,
          termId: TermsService.getTermId(),
          credits: newCourse.credits,
          gradingOptionId: newCourse.gradingOptionId
        });
        break;
      case COURSE_TYPES.registered:
        console.log('Update registered course');
        executeUpdate(type, course, newCourse, successCallback, errorCallback, ScheduleService.updateScheduleItem().query, {
          courseCode: course.courseCode,
          regGroupId: course.regGroupId,
          masterLprId: course.masterLprId,
          termId: TermsService.getTermId(),
          credits: newCourse.credits,
          gradingOptionId: newCourse.gradingOptionId
        });
      }
    });
    /**
         * Execute a drop request against the RESTful service and manage it accordingly.
         * Drops are a registration request that needs to be polled until they return a terminal state.
         *
         * @param type of course
         * @param course being updated
         * @param successCallback to be called when the drop has completely finished
         * @param errorCallback to be called if the drop fails at any point
         * @param request to be executed
         * @param params to be provided to the request
         */
    function executeDrop(type, course, successCallback, errorCallback, request, params) {
      request(params, function (regRequest) {
        console.log('- Drop course registration request submitted - starting to poll for request status');
        ScheduleService.pollRegistrationRequestStatus(regRequest.id).then(function () {
          // Success
          console.log('-- Stop polling - Success');
          switch (type) {
          case COURSE_TYPES.waitlisted:
            // After all the processing is complete, update the new Schedule counts.
            ScheduleService.removeWaitlistedCourse(course);
            // need a count on how many success drop message for WL are opened. So if there are no WL courses
            // when we "X" the last success drop message the "Waitlisted" section name would disappear
            numberOfDroppedWailistedCourses++;
            break;
          case COURSE_TYPES.registered:
            // After all the processing is complete, update the new Schedule counts.
            ScheduleService.removeRegisteredCourse(course);
            // need a count on how many success drop message are opened. So if there are no courses when we "X" the last
            // success drop message the section can be replaced by the splash screen (unless there are waitlisted courses)
            numberOfDroppedCourses++;
          }
          // Call the success callback function
          if (angular.isFunction(successCallback)) {
            successCallback();
          }
          $scope.$emit('courseDropped', course, type);
        }, function (response) {
          // Error
          console.log('-- Stop polling - Error', response);
          // Call the error callback function with the error message
          if (angular.isFunction(errorCallback)) {
            errorCallback(response.responseItemResults[0].messages[0]);
          }
        }, function () {
          // Notify
          console.log('-- Continue polling');
        });
      }, function (error) {
        console.log('- Drop course registration request failed', error);
        // Call the error callback function with the error message
        if (angular.isFunction(errorCallback)) {
          errorCallback(error.data);
        }
      });
    }
    /**
         * Execute an update request against the RESTful service and manage it accordingly.
         * Updates are a registration request that needs to be polled until they return a terminal state.
         *
         * @param type of course
         * @param course is the actual course prior to any updates (original data)
         * @param newCourse is a clone with the updated data applied
         * @param successCallback to be called when the drop has completely finished
         * @param errorCallback to be called if the drop fails at any point
         * @param request to be executed
         * @param params to be provided to the request
         */
    function executeUpdate(type, course, newCourse, successCallback, errorCallback, request, params) {
      request(params, function (regRequest) {
        console.log('- Update course registration request submitted - starting to poll for request status');
        ScheduleService.pollRegistrationRequestStatus(regRequest.id).then(function () {
          // Success
          console.log('-- Stop polling - Success');
          switch (type) {
          case COURSE_TYPES.waitlisted:
            // Update the course counts
            ScheduleService.updateWaitlistedCourse(course, newCourse);
            break;
          case COURSE_TYPES.registered:
            // Update the course counts
            ScheduleService.updateRegisteredCourse(course, newCourse);
          }
          // Call the success callback function
          if (angular.isFunction(successCallback)) {
            successCallback();
          }
        }, function (response) {
          // Error
          console.log('-- Stop polling - Error', response);
          // Call the error callback function with the error message
          if (angular.isFunction(errorCallback)) {
            errorCallback(response.responseItemResults[0].messages[0]);
          }
        }, function () {
          // Notify
          console.log('-- Continue polling');
        });
      }, function (error) {
        console.log('- Update course registration request failed', error);
        // Call the error callback function with the error message
        if (angular.isFunction(errorCallback)) {
          errorCallback(error.data);
        }
      });
    }
  }
]);
'use strict';
angular.module('regCartApp').controller('SearchCtrl', [
  '$scope',
  '$filter',
  '$state',
  'CourseSearchFacets',
  'TermsService',
  'SearchService',
  function SearchCtrl($scope, $filter, $state, CourseSearchFacets, TermsService, SearchService) {
    console.log('>> SearchCtrl');
    $scope.facets = CourseSearchFacets;
    // Facet definitions
    $scope.searchState = $state.params;
    // Expose the state parameters so the search state can be persisted there (e.g. page, facets, etc)
    $scope.searchCriteria = null;
    // Criteria used to generate the search results.
    $scope.searchResults = [];
    // Results from the last search request.
    $scope.filteredResults = [];
    // Results that have been filtered through the facets.
    $scope.$on('termIdChanged', function () {
      var criteria = $scope.searchCriteria;
      // Drop the existing search results when the term changes
      $scope.searchCriteria = '';
      $scope.searchResults = [];
      // Search for the old criteria under the new termId.
      if (criteria) {
        lastSearchCriteria = null;
        doSearch(criteria);
      }
    });
    // Listen for any state changes from ui-router. This is where we get the search criteria from.
    $scope.$on('$stateChangeSuccess', function (event, toState, toParams) {
      if (toState.name === 'root.search.results') {
        syncWithSearchFromState(toParams);
        if (angular.isDefined(toParams.searchCriteria)) {
          doSearch(toParams.searchCriteria);
        }
      }
    });
    // Listen for changes to the 'resultsStateChanged' event that is emitted from the searchList directive when the state changes.
    $scope.$on('resultsStateChanged', function (event, resultsState) {
      var params = angular.copy(resultsState);
      params.searchCriteria = $scope.searchCriteria;
      persistSearchState(params);
    });
    // Persist the search state in the URL query string
    function persistSearchState(params) {
      params = params || angular.copy($state.params);
      // Build out the facet filter values
      var selectedFacets = {};
      angular.forEach($scope.facets, function (facet) {
        if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
          selectedFacets[facet.id] = facet.selectedOptions;
        }
      });
      // Push the selected facets into the URL parameters as a JSON string since ui-router doesn't handle object parameters
      params.filters = Object.keys(selectedFacets).length > 0 ? JSON.stringify(selectedFacets) : null;
      // location: 'replace' = replaces the last history item in the stack preserving the back functionality
      // notify: false = prevents the UI state change event from firing which would reload the view
      $state.go('root.search.results', params, {
        location: 'replace',
        notify: false
      });
    }
    // Sync the state parameters with the values in the search
    function syncWithSearchFromState(params) {
      params = params || $state.params;
      // Put the states into the scope for use by the search list
      $scope.stateParams = params;
      // Sync up the selected facets
      var selectedFacets = {};
      if (angular.isDefined(params.filters) && params.filters !== null) {
        selectedFacets = JSON.parse(params.filters) || {};
      }
      angular.forEach($scope.facets, function (facet) {
        var selection = [];
        if (angular.isDefined(selectedFacets[facet.id])) {
          selection = selectedFacets[facet.id];
        }
        facet.selectedOptions = selection;
      });
    }
    // Apply the facet filter to the results
    function filterResults() {
      var filter = $filter('facetFilter'), filtered = [];
      angular.forEach($scope.searchResults, function (item) {
        if (filter(item, $scope.facets)) {
          filtered.push(item);
        }
      });
      $scope.filteredResults = filtered;
    }
    // Watch for selected facets to change, persist the change & filter the results
    $scope.$watch('facets', function () {
      persistSearchState();
      filterResults();
    }, true);
    var queuedSearchHandle,
      // Handle on the queued up search.
      lastSearchCriteria = null;
    // Criteria used to execute the most recent search request.
    function doSearch(criteria) {
      if (criteria === null || lastSearchCriteria !== null && criteria === lastSearchCriteria) {
        // Nothing to do. Last search still valid.
        return;
      }
      // Cancel out the queued up search if it exists
      if (angular.isFunction(queuedSearchHandle)) {
        queuedSearchHandle();
        // Remove the event subscription
        queuedSearchHandle = null;
      }
      if (!TermsService.getTermId()) {
        // The search cannot occur w/o a termId.
        console.log('Search blocked - no termId exists');
        // Queue the search to be performed when the term is set.
        queuedSearchHandle = $scope.$on('termIdChanged', function () {
          queuedSearchHandle();
          // Remove the event subscription
          queuedSearchHandle = null;
          doSearch(criteria);
        });
        return;
      }
      console.log('Searching for "' + criteria + '"');
      // Store off to prevent a provide a way to reference the search results that come back in case
      // the user runs another search request while this one is still running.
      lastSearchCriteria = criteria;
      SearchService.searchForCourses().query({
        termId: TermsService.getTermId(),
        criteria: criteria
      }, function (results) {
        if (lastSearchCriteria === criteria) {
          // This search matches the last one ran - it's current.
          console.log('Search for "' + criteria + '" complete. Results: ' + results.length);
          $scope.searchResults = results;
          $scope.searchCriteria = criteria;
          filterResults();
        } else {
          console.log('Search completed but not the most recent, ignoring results: "' + criteria + '" !== "' + lastSearchCriteria + '"');
        }
      }, function (error) {
        console.log('Error searching for courses: ', error);
      });
    }
    $scope.$on('viewDetails', function (event, course) {
      // redirects the view to the search details screen
      $state.go('root.search.details', {
        searchCriteria: $scope.searchCriteria,
        id: course.courseId,
        code: course.courseCode
      });
    });
  }
]).filter('startFrom', function () {
  return function (input, start) {
    start = +start;
    //parse to int
    return input.slice(start);
  };
}).filter('facetFilter', function () {
  return function (item, facets) {
    var select = true;
    angular.forEach(facets, function (facet) {
      if (select) {
        // Only filter on the facet if it has selected options
        if (angular.isArray(facet.selectedOptions) && facet.selectedOptions.length > 0) {
          if (!facet.filter(item, facet.selectedOptions)) {
            // The item does not match the facet filter. Exclude it.
            select = false;
          }
        }
      }
    });
    return select;
  };
});
;
'use strict';
/*
 * Controller for the search details functionality
 *
 * Event Handling
 * -- Emits: none
 * -- Broadcasts: "registerForCourse" -- this is caught by cart.js and registers the user for the selected reg group
 *                "addCourseToCart" -- this is caught by cart.js and adds the reg group to the user's cart
 * -- Receives: "toggleAO" -- received from the search list directives, select/deselects the given ao
 */
angular.module('regCartApp').controller('SearchDetailsCtrl', [
  '$scope',
  '$rootScope',
  '$state',
  '$filter',
  '$modal',
  'STATUS',
  'FEATURE_TOGGLES',
  'SearchService',
  'CartService',
  'ScheduleService',
  function SearchDetailsCtrl($scope, $rootScope, $state, $filter, $modal, STATUS, FEATURE_TOGGLES, SearchService, CartService, ScheduleService) {
    console.log('>> SearchDetailsCtrl');
    $scope.stateParams = $state.params;
    // Expose the state parameters to the scope so they can be used in the back link
    $scope.course = null;
    // Handle on the course
    // Push the user back to the search page when the term is changed
    $scope.$on('termIdChanged', function (event, newValue, oldValue) {
      if (oldValue !== null && $scope.uiState === 'root.search.details') {
        $state.go('root.search.results', $state.params);
      }
    });
    // Listen for any state changes from ui-router. This is where we get the search criteria & course ID from.
    $scope.$on('$stateChangeSuccess', function (event, toState, toParams) {
      $scope.stateParams = toParams;
      if (angular.isDefined(toParams.id)) {
        loadCourse(toParams.id, toParams.code);
      }
    });
    $scope.loading = false;
    // Course not yet loading
    var lastCourseId = null;
    function loadCourse(courseId, courseCode) {
      if (courseId === null || $scope.course !== null && $scope.course.courseOfferingId !== null && courseId === $scope.course.courseOfferingId && $scope.course.courseOfferingCode !== null && courseCode === $scope.course.courseOfferingCode) {
        // Don't load a null courseId or the same course we already have.
        return;
      }
      $scope.loading = true;
      // Course is still loading
      console.log('Loading course ' + courseId + ', ' + courseCode);
      lastCourseId = courseId;
      SearchService.getCourse().query({
        courseOfferingId: courseId,
        courseCode: courseCode
      }, function (result) {
        if (lastCourseId === courseId) {
          // This query matches the last one ran - it's current.
          var regGroups = {};
          var aoMap = {};
          if (angular.isDefined(result.activityOfferingTypes) && angular.isArray(result.activityOfferingTypes)) {
            angular.forEach(result.activityOfferingTypes, function (aoType) {
              // Apply aoFormatter filter to the activity offerings for display in the table
              aoType.formattedOfferings = $filter('aoFormatter')(angular.copy(aoType.activityOfferings));
              angular.forEach(aoType.activityOfferings, function (ao) {
                // Give each activity offering a handle on its type.
                // This facilitates managing the selected offerings.
                ao.activityOfferingType = aoType.activityOfferingType;
                // Add the ao to the map
                aoMap[ao.activityOfferingCode] = ao;
                // Transform the reg groups to be more easily consumed
                angular.forEach(ao.regGroupInfos, function (regGroup, id) {
                  if (angular.isUndefined(regGroups[id])) {
                    regGroups[id] = regGroup;
                  }
                  if (angular.isUndefined(regGroup.activityOfferingIds)) {
                    regGroup.activityOfferingIds = [];
                  }
                  // Give each reg group a handle on the activity offerings that compose it.
                  regGroups[id].activityOfferingIds.push(ao.activityOfferingId);
                });
                // Check to see if there are any additional info icons to display for this course
                if (!$scope.additionalInfo && (ao.subterm !== null || angular.isArray(ao.requisites) && ao.requisites.length > 0)) {
                  $scope.additionalInfo = true;
                }
              });
              // initialize flags
              angular.forEach(aoType.formattedOfferings, function (formattedOffering) {
                var ao = aoMap[formattedOffering.aoId];
                ao.flags = {};
                formattedOffering.flags = ao.flags;
              });
            });
          }
          $scope.availableRegGroups = regGroups;
          $scope.aoMap = aoMap;
          $scope.loading = false;
          // Course done loading
          $scope.course = result;
          $scope.updateAOStates();
          $scope.singleRegGroup = singleRegGroup();
        } else {
          $scope.loading = true;
          // Course loaded
          console.log('Course load completed but not the most recent, ignoring: "' + courseId + '" !== "' + lastCourseId + '"');
        }
      }, function (error) {
        console.log('Error loading course: ', error);
      });
    }
    $scope.availableRegGroups = {};
    $scope.aoMap = {};
    $scope.selectedAOs = [];
    // List of selected activity offerings by their type
    $scope.selectedRegGroup = null;
    // Handle on the selected reg group based on the selected AOs
    $scope.singleRegGroup = false;
    // Handle on whether we are displaying a single reg group or several
    $scope.additionalInfo = false;
    // Handle on whether there are additional info icons to show for this course
    $scope.singleRegFormatted = [];
    // If the course a single reg group, holds the formatted ao details
    $scope.clearSelectedAOs = function () {
      $scope.selectedRegGroup = null;
      $scope.selectedAOs = [];
      $scope.updateAOStates();
    };
    $scope.hasSelectedAOs = function () {
      return $scope.selectedAOs.length > 0;
    };
    $scope.isAOTypeSelected = function (aoType) {
      var selected = getSelectedAOByType(aoType);
      return selected !== null;
    };
    // Check whether or not the Direct Add to Waitlist feature is enabled
    $scope.allowDirectAddToWaitlist = function () {
      return FEATURE_TOGGLES.searchDetails.directAddToWaitlist || false;
    };
    // Method for checking if the selected reg group is in the cart
    $scope.isSelectedRegGroupInCart = function () {
      return $scope.selectedRegGroup && CartService.isCourseInCart($scope.selectedRegGroup.regGroupId);
    };
    // Method for checking if the selected reg group is in the waitlist
    $scope.isSelectedRegGroupInWaitlist = function () {
      return $scope.selectedRegGroup && ScheduleService.isCourseWaitlisted($scope.selectedRegGroup.regGroupId);
    };
    // Perform the Add to Cart action when that button is clicked
    $scope.addToCart = function () {
      if (!$scope.selectedRegGroup) {
        return;
      }
      // Re-using "Add to Cart" functionality from cart controller
      performAction('cart', 'addCourseToCart');
    };
    // Perform the Add to Waitlist action when that button is clicked
    $scope.addToWaitlist = function () {
      // Only allow direct add to waitlist if it is enabled & the waitlist is available for this RG.
      if (!$scope.selectedRegGroup || !$scope.selectedRegGroup.isWaitlistAvailable || !$scope.allowDirectAddToWaitlist()) {
        return;
      }
      // Broadcast an directRegisterForCourse event that is caught in the cart.js controller
      performAction('waitlist', 'registerForCourse');
    };
    // Helper method for performing the action (addToCart or addToWaitlist)
    function performAction(actionType, event) {
      $scope.actionType = actionType;
      $scope.actionStatus = null;
      var course = angular.copy($scope.course);
      course.courseCode = course.courseOfferingCode;
      course.regGroupId = $scope.selectedRegGroup.regGroupId;
      course.regGroupCode = $scope.selectedRegGroup.regGroupCode;
      // Broadcast the event that is caught and handled elsewhere (cart.js controller)
      $rootScope.$broadcast(event, course, function () {
        $scope.actionStatus = STATUS.success;
      }, function () {
        $scope.actionStatus = STATUS.error;
      });
    }
    // Clear out the action message when the remove button is clicked (X)
    $scope.removeActionMessage = function () {
      $scope.actionType = null;
      $scope.actionStatus = false;
    };
    $scope.$on('showSubterm', function (event, aoId) {
      var subterm = $scope.aoMap[aoId].subterm;
      $modal.open({
        templateUrl: 'partials/additionalInfo.html',
        controller: 'AdditionalInfoCtrl',
        size: 'sm',
        resolve: {
          subterm: function () {
            return subterm;
          },
          requisites: undefined
        }
      });
    });
    $scope.$on('showRequisites', function (event, aoId) {
      var requisites = $scope.aoMap[aoId].requisites;
      $modal.open({
        templateUrl: 'partials/additionalInfo.html',
        controller: 'AdditionalInfoCtrl',
        size: 'sm',
        resolve: {
          requisites: function () {
            return requisites;
          },
          subterm: undefined
        }
      });
    });
    // Event fired from search results table when a row is clicked.
    // Takes care of selecting/unselecting AOs and display depending on Reg Groups for selected AOs.
    // When AO is selected we only want to display AOs that are part of Reg Groups for selected one.
    // When several AOs selected such as they build a Reg Group -> passing ID and Code for this Reg Group on UI
    $scope.$on('toggleAO', function (event, formattedOffering) {
      var ao = $scope.aoMap[formattedOffering.aoId];
      var selected = false;
      if (isAOSelected(ao)) {
        deselectAO(ao);
      } else if (isAOCompatible(ao)) {
        selectAO(ao);
        selected = true;
      } else {
        // Don't continue since nothing was done
        return;
      }
      // Re-filter the AOs
      $scope.updateAOStates();
      if (selected) {
        // Auto-select any eligible AOs only when it was selected.
        // This allows the only AO in a list to be deselected.
        autoSelectEligibleAOs();
      }
      // Check if we have reg group
      $scope.selectedRegGroup = checkForSelectedRegGroup();
    });
    /**
         * Tally the number of eligible activity offerings in a list
         *
         * @param aos list of activity offerings
         * @returns number of eligible activity offerings (based on registration group compatibility)
         */
    $scope.countEligibleAOs = function (aos) {
      var eligible = 0;
      angular.forEach(aos, function (ao) {
        if (!ao.flags.disabled && !ao.flags.selected) {
          eligible++;
        }
      });
      return eligible;
    };
    /**
         * Method for determining which activity offerings to show in the table
         */
    $scope.updateAOStates = function () {
      angular.forEach($scope.course.activityOfferingTypes, function (aoType) {
        if (aoType.showAll && $scope.isAOTypeSelected(aoType)) {
          // Reset the showAll if this AOType is selected
          aoType.showAll = false;
        }
        angular.forEach(aoType.activityOfferings, function (ao) {
          ao.flags.selected = isAOSelected(ao);
          ao.flags.disabled = !isAOCompatible(ao);
          // AO is disabled if it is not compatible with the selected AOs
          if (aoType.showAll || ao.flags.selected) {
            // Show All || this AO is selected
            ao.flags.hidden = false;
          } else if (!$scope.isAOTypeSelected(aoType)) {
            // This AO Type is not selected, hide if not compatible
            ao.flags.hidden = ao.flags.disabled;
          } else {
            ao.flags.hidden = true;
          }
        });
      });
    };
    /**
         * Method for determining how many possible reg groups are available --
         * if only one reg group is available, select all aos automatically and
         * return true. Otherwise, select nothing and return false.
         */
    function singleRegGroup() {
      var keys = Object.keys($scope.availableRegGroups);
      if (keys.length === 1) {
        var singleRegFormatted = [];
        angular.forEach($scope.course.activityOfferingTypes, function (aoType) {
          angular.forEach(aoType.activityOfferings, function (ao) {
            selectAO(ao, true);
          });
          singleRegFormatted.push.apply(singleRegFormatted, aoType.formattedOfferings);
        });
        $scope.selectedRegGroup = checkForSelectedRegGroup();
        $scope.singleRegFormatted = singleRegFormatted;
        return true;
      }
      return false;
    }
    /**
         * Auto-select the an activity offering when there is only 1 eligible option within its type
         */
    function autoSelectEligibleAOs() {
      angular.forEach($scope.course.activityOfferingTypes, function (aoType) {
        // If there is only 1 eligible AO, auto-select it
        if (!$scope.isAOTypeSelected(aoType) && $scope.countEligibleAOs(aoType.activityOfferings) === 1) {
          angular.forEach(aoType.activityOfferings, function (ao) {
            if (!ao.flags.selected && !ao.flags.disabled) {
              selectAO(ao);
              $scope.updateAOStates();
            }
          });
        }
      });
    }
    /**
         * Calculate whether the reg group is full & the waitlist capacity values for a reg group.
         *
         * @param regGroup
         * @returns regGroup
         */
    function calculateCapacityForRegGroup(regGroup) {
      if (angular.isDefined(regGroup.isFull)) {
        // Already calculated
        return regGroup;
      }
      // A reg group is full if any of its activity offerings has 0 seats available
      regGroup.isFull = false;
      angular.forEach($scope.course.activityOfferingTypes, function (aoType) {
        if (!regGroup.isFull) {
          angular.forEach(aoType.activityOfferings, function (ao) {
            if (!regGroup.isFull && regGroup.activityOfferingIds.indexOf(ao.activityOfferingId) !== -1 && ao.seatsOpen <= 0) {
              regGroup.isFull = true;
            }
          });
        }
      });
      // A waitlist is full if: (there is a max && all seats are taken)
      regGroup.isWaitlistFull = angular.isNumber(regGroup.maxWaitListSize) && regGroup.waitListSize >= regGroup.maxWaitListSize;
      // A waitlist is available if: (it is offered && not full)
      regGroup.isWaitlistAvailable = regGroup.waitListOffered && !regGroup.isWaitlistFull;
      return regGroup;
    }
    /**
         * Method for identifying the selected reg group based on the selected activity offerings.
         *
         * @returns registration group {id, code}
         */
    function checkForSelectedRegGroup() {
      // A reg group is only capable of being selected when each AO type has a selected AO
      if ($scope.selectedAOs.length === $scope.course.activityOfferingTypes.length) {
        var candidates = getSelectableRegGroups();
        if (candidates.length === 1) {
          return calculateCapacityForRegGroup(candidates[0]);
        }
      }
      return null;
    }
    /**
         * Helper method to get the selected activity offering matching a given type
         *
         * @param aoType type of activity offering
         */
    function getSelectedAOByType(aoType) {
      // Allow a string value or the object itself to be passed in
      if (typeof aoType === 'object' && angular.isDefined(aoType.activityOfferingType)) {
        aoType = aoType.activityOfferingType;
      }
      var selected = null;
      angular.forEach($scope.selectedAOs, function (ao) {
        if (selected === null && ao.activityOfferingType === aoType) {
          selected = ao;
        }
      });
      return selected;
    }
    /**
         * Get the list of possible registration groups based on the currently selected registration groups
         *
         * @returns array of registration groups [{id, code}]
         */
    function getSelectableRegGroups() {
      var candidates = $scope.availableRegGroups;
      angular.forEach($scope.selectedAOs, function (ao) {
        // Init the reg group candidates with the first AO's reg groups
        if (candidates === null) {
          candidates = [];
          for (var key in ao.regGroupInfos) {
            candidates.push($scope.availableRegGroups[key]);
          }
        } else {
          // Eliminate any reg groups from the candidates that don't exist in the current ao
          var newCandidates = [];
          angular.forEach(candidates, function (candidate) {
            if (angular.isDefined(ao.regGroupInfos[candidate.regGroupId])) {
              newCandidates.push(candidate);  // Candidate is still valid, carry it over.
            }
          });
          candidates = newCandidates;
        }
      });
      return candidates || [];
    }
    /**
         * Helper method for determining if an activity offering is
         * compatible with the reg groups of the already selected offerings
         *
         * @param ao
         * @returns {boolean}
         */
    function isAOCompatible(ao) {
      var compatible = true;
      // Only capable of being incompatible if there are selectedAOs and it's not one of them
      if (!isAOSelected(ao) && $scope.hasSelectedAOs()) {
        compatible = false;
        var regGroups = getSelectableRegGroups();
        for (var i = 0; i < regGroups.length; i++) {
          if (angular.isDefined(ao.regGroupInfos[regGroups[i].regGroupId])) {
            compatible = true;
            break;
          }
        }
      }
      return compatible;
    }
    /**
         * Determine if an AO is selected or not
         * @param ao
         * @returns {boolean}
         */
    function isAOSelected(ao) {
      return $scope.selectedAOs.indexOf(ao) !== -1;
    }
    /**
         * Deselect an activity offering
         * @param ao
         */
    function deselectAO(ao) {
      $scope.selectedAOs.splice($scope.selectedAOs.indexOf(ao), 1);
      ao.flags.selected = false;
    }
    /**
         * Select an activity offering, removing the previously selected offering of the same type
         * @param ao
         * @param suppressSelection true to suppress setting the AO as selected in the view
         */
    function selectAO(ao, suppressSelection) {
      // Deselect the already selected ao of this type
      var selected = getSelectedAOByType(ao.activityOfferingType);
      if (selected !== null) {
        deselectAO(selected);
      }
      // Select the AO
      $scope.selectedAOs.push(ao);
      if (!suppressSelection) {
        ao.flags.selected = true;
      }
    }
  }
]).controller('AdditionalInfoCtrl', [
  '$scope',
  'subterm',
  'requisites',
  function ($scope, subterm, requisites) {
    $scope.subterm = subterm;
    $scope.requisites = requisites;
    $scope.startDate = function () {
      return parseDate(subterm.startDate);
    };
    $scope.endDate = function () {
      return parseDate(subterm.endDate);
    };
    /*
        Parses a millisecond date into MM/DD/YYYY
         */
    function parseDate(millis) {
      var date = new Date(millis);
      return date.getMonth() + 1 + '/' + date.getDate() + '/' + date.getFullYear();
    }
  }
]);
;
'use strict';
angular.module('regCartApp').factory('DetailsFactory', [
  '$injector',
  function ($injector) {
    var configCache = {};
    // DetailsFactory constructor...call with "new DetailsFactory(configName)"
    var DetailsFactory = function (configName) {
      // get the config from off the cache if possible, otherwise, inject it
      var detailsConfig = configCache[configName];
      if (angular.isUndefined(detailsConfig)) {
        detailsConfig = $injector.get(configName);
      }
      // if the config exists, add it to the cache, otherwise log an error
      if (angular.isUndefined(detailsConfig)) {
        console.log('Search Details configuration ' + configName + ' is not defined');
      } else {
        configCache[configName] = detailsConfig;
      }
      return detailsConfig;
    };
    return DetailsFactory;
  }
]);
;
'use strict';
/**
 * Contains a variety of filters for use as data formatters in course search
 */
angular.module('regCartApp').filter('creditRangeFormatter', function () {
  /**
         * Format an array of credit options into a range using the min & max values.
         *
         * If only 1 option exists in the array, it is returned (e.g. '1 cr').
         * Otherwise the value returned is a represented as a range (e.g. '1-3 cr').
         *
         * @param array of credit options
         * @return string
         */
  return function (creditOptions) {
    var range = '';
    if (creditOptions && angular.isArray(creditOptions)) {
      if (creditOptions.length === 1) {
        range = parseFloat(creditOptions[0]);
      } else {
        var min = parseFloat(Math.min.apply(null, creditOptions)), max = parseFloat(Math.max.apply(null, creditOptions));
        range = min + '-' + max;
      }
      range += ' cr';
    }
    return range;
  };
}).filter('aoFormatter', [
  'DAY_CONSTANTS',
  'RegUtil',
  function (DAY_CONSTANTS, RegUtil) {
    /**
         * Format an array of activity offerings for display using the search-list directive
         *
         * @param array of schedule components
         * @return string
         */
    return function (activityOfferings) {
      var formattedOfferings = [];
      for (var index = 0; index < activityOfferings.length; index++) {
        var ao = activityOfferings[index];
        var scheduleComponents = ao.scheduleComponents;
        var instructors = ao.instructors;
        var days = '';
        // days column
        var time = '';
        // time column
        var location = '';
        // location column
        var instructorList = '';
        // instructors column
        var seatsOpen = '';
        // seats open column
        var additionalInfo;
        // additional info column
        var indicator = false;
        // determines if we show the row indicator on the left
        if (scheduleComponents && angular.isArray(scheduleComponents) && scheduleComponents.length > 0) {
          for (var i = 0; i < scheduleComponents.length; i++) {
            days += denullify(scheduleComponents[i].days);
            time += denullify(scheduleComponents[i].displayTime);
            location += denullify(scheduleComponents[i].buildingCode) + ' ' + denullify(scheduleComponents[i].roomCode);
            if (i < scheduleComponents.length - 1) {
              days += '<br />';
              time += '<br />';
              location += '<br />';
            }
          }
          days = addSortField(days, getNumericDays(scheduleComponents[0].days));
          time = addSortField(time, getActualTime(scheduleComponents[0].startTime));
        }
        if (instructors && angular.isArray(instructors) && instructors.length > 0) {
          for (var j = 0; j < instructors.length; j++) {
            instructorList += denullify(instructors[j].firstName) + ' ' + denullify(instructors[j].lastName);
            if (j < instructors.length - 1) {
              instructorList += '<br />';
            }
          }
          instructorList = addSortField(instructorList, instructors[0].lastName + instructors[0].firstName);
        }
        seatsOpen += ao.seatsOpen + '/' + ao.seatsAvailable;
        if (ao.seatsOpen === 0) {
          seatsOpen = '<span class="kscr-Search-results-no-seats">' + seatsOpen + '</span>';
          indicator = true;
        }
        seatsOpen = addSortField(seatsOpen, zeroPad(ao.seatsOpen, 3) + zeroPad(ao.seatsAvailable, 3));
        var subterm = false, requisites = false;
        if (ao.subterm !== null) {
          subterm = true;
        }
        if (angular.isArray(ao.requisites) && ao.requisites.length > 0) {
          requisites = true;
        }
        if (subterm || requisites) {
          additionalInfo = '';
        }
        if (subterm) {
          additionalInfo += '<span class="kscr-SearchDetails-icon--subterm" ng-click="$emit(\'showSubterm\', searchResult.aoId); $event.stopPropagation();"></span>';
        }
        if (requisites) {
          if (!subterm) {
            additionalInfo += '<span class="kscr-SearchDetails-icon">&nbsp;</span>';
          }
          additionalInfo += '<span class="kscr-SearchDetails-icon--requisites" ng-click="$emit(\'showRequisites\', searchResult.aoId); $event.stopPropagation();"></span>';
        }
        var aoId = ao.activityOfferingCode;
        var formattedOffering = {
            activity: ao.activityOfferingTypeName,
            days: days,
            time: time,
            instructor: instructorList,
            location: location,
            seatsOpen: seatsOpen,
            additionalInfo: additionalInfo,
            aoId: aoId,
            indicator: indicator
          };
        formattedOfferings.push(formattedOffering);
      }
      return formattedOfferings;
    };
    function denullify(value) {
      if (value === null) {
        return '';
      } else {
        return value;
      }
    }
    function zeroPad(value, len) {
      value = '' + value;
      // convert to string
      while (value.length < len) {
        value = '0' + value;
      }
      return value;
    }
    function addSortField(field, sortField) {
      field = '<span class="kscr-Search-result-sort">' + sortField + '</span>' + field;
      return field;
    }
    // Converts the day of the week into a number for sorting purposes
    function getNumericDays(days) {
      var dayNumArray = [];
      for (var i = 0; i < DAY_CONSTANTS.dayArray.length; i++) {
        if (days.indexOf(DAY_CONSTANTS.dayArray[i]) > -1) {
          dayNumArray.push(i);
        }
      }
      return dayNumArray.sort().toString();
    }
    // Converts display time into the actual time for sorting purposes
    function getActualTime(time) {
      var actualTime = RegUtil.convertTimeStringToTime(time);
      if (isNaN(actualTime)) {
        actualTime = 9999;
      }
      return zeroPad(actualTime, 4);
    }
  }
]);
;
'use strict';
/**
 * Utility filters for course registration
 */
/*
 * Cleanses a string by removing spaces
 */
angular.module('regCartApp').filter('cleanse', function () {
  return function (value) {
    var cleansed;
    if (angular.isString(value)) {
      cleansed = value.replace(' ', '');
    } else {
      cleansed = value;
    }
    return cleansed;
  };
});
;
'use strict';
angular.module('regCartApp').filter('formatValidationMessage', [
  'VALIDATION_ERROR_TYPE',
  'GENERAL_ERROR_TYPE',
  'MessageService',
  function (VALIDATION_ERROR_TYPE, GENERAL_ERROR_TYPE, MessageService) {
    /**
     * In this method we take a course & validation message object and return a formatted
     * message depending on the validation message key.
     *
     * @example {{ validationMessage | formatValidationMessage:course }}
     *
     * @param data
     * @param course
     * @returns {string}
     */
    return function (data, course) {
      var message = '';
      if (data) {
        if (typeof data === 'string') {
          // Backwards compatibility, allow a straight string to go through
          message = data;
        } else if (data.txt) {
          message = data.txt;
        } else if (data.messageKey) {
          // Validation message w/ messageKey value
          switch (data.messageKey) {
          case VALIDATION_ERROR_TYPE.timeConflict:
            message = formatTimeConflict(data, course);
            break;
          case VALIDATION_ERROR_TYPE.maxCredits:
            message = formatMaxCredits(data);
            break;
          case VALIDATION_ERROR_TYPE.reggroupNotOffered:
            message = formatReggroupNotOffered(data, course);
            break;
          case GENERAL_ERROR_TYPE.noRegGroup:
            message = formatCourse(data.txt, data.course);
            break;
          default:
            message = getMessage(data.messageKey);
          }
        }
      }
      return formatCourse(message, course);
    };
    /**
     * Format time credits violation message
     *
     * Cases:
     * 1. courseCode on root level:
     *    { messageKey: '...', courseCode: '...' }
     *
     * 2. conflictingCourses array:
     *    { messageKey: '...', conflictingCourses: [{ courseCode: '...' }, { courseCode: '...' }]}
     *
     * @param data
     * @param course
     * @returns {string}
     *
     *
     * Need to update id field to be the actual one coming from the server
     */
    function formatTimeConflict(data, course) {
      // look up the core message text
      var message = getMessage(data.messageKey);
      // Try to include the course codes of the conflicting items
      var conflicts = [];
      // Case 1: Check for a courseCode included at the root level
      if (data.courseCode) {
        conflicts.push({
          masterLprId: data.masterLprId,
          courseCode: data.courseCode
        });
      }
      // Case 2: Check for an array of conflictingCourses
      if (data.conflictingCourses) {
        angular.forEach(data.conflictingCourses, function (item) {
          conflicts.push(item);
        });
      }
      // Parse the identified codes
      if (conflicts.length) {
        var currentCourseId = null, codes = [], includedIds = [];
        if (course) {
          // Try to get the id of the current course so we can can avoid including it in the conflicting list
          if (angular.isDefined(course.cartItemId)) {
            currentCourseId = course.cartItemId;  // In cart
          } else if (angular.isDefined(course.masterLprId)) {
            currentCourseId = course.masterLprId;
          }
        }
        for (var i = 0; i < conflicts.length; i++) {
          if (conflicts[i].masterLprId) {
            // Don't include courses that match the current course (conflicts w/ itself)
            // & only include them once
            if (conflicts[i].masterLprId === currentCourseId || includedIds.indexOf(conflicts[i].masterLprId) >= 0) {
              continue;
            }
            includedIds.push(conflicts[i].masterLprId);
          }
          if (conflicts[i].courseCode) {
            codes.push('<strong>' + conflicts[i].courseCode + '</strong>');
          }
        }
        if (codes.length) {
          message += ' (' + codes.join(', ') + ')';
        }
      }
      return message;
    }
    function formatMaxCredits(data) {
      // look up the core message text
      var message = getMessage(data.messageKey);
      // if max credits are sent back with the message key, include them in the message back to the user
      if (data.maxCredits) {
        var maxCredits = parseFloat(data.maxCredits);
        // convert to a float to eliminate unnecessary decimals
        message += ' (<strong>' + maxCredits + ' credits</strong>)';
      }
      return message;
    }
    function formatReggroupNotOffered(data, course) {
      // look up the core message text
      var message = getMessage(data.messageKey);
      message = message.replace('XXXX (RG)', course.courseCode + ' (' + course.regGroupCode + ') ');
      return message;
    }
    function formatCourse(message, course) {
      var courseCode = '';
      if (angular.isString(course)) {
        courseCode = course;
      } else if (course && angular.isDefined(course.courseCode)) {
        courseCode = course.courseCode;
      } else {
        // No course code found.
        return message;
      }
      // Replace any {{courseCode}} parameters with the courseCode value
      if (message.indexOf('{{courseCode}}') !== -1) {
        message = message.replace('{{courseCode}}', courseCode);
      }
      // Wrap the course code with <strong></strong>
      if (message.indexOf(courseCode) !== -1) {
        message = message.replace(courseCode, '<strong>' + courseCode + '</strong>');
      }
      return message;
    }
    function getMessage(messageKey) {
      return MessageService.getMessage(messageKey);
    }
  }
]);
'use strict';
angular.module('regCartApp').service('CartService', [
  '$q',
  'URLS',
  'ServiceUtilities',
  function CartService($q, URLS, ServiceUtilities) {
    var cartCredits = 0;
    var cartCourseCount = 0;
    var cartCourses = [];
    // Cache of carts per term
    var carts = {};
    this.getCart = function (termId) {
      var deferred = $q.defer();
      if (angular.isDefined(carts[termId])) {
        // Return the cached cart
        deferred.resolve(carts[termId]);
      } else {
        this.getCartFromServer().query({ termId: termId }, function (cart) {
          // Cache the cart
          carts[termId] = cart;
          deferred.resolve(cart);
        }, function (error) {
          // Report out the error
          deferred.reject(error);
        });
      }
      return deferred.promise;
    };
    this.getCartCredits = function () {
      return cartCredits;
    };
    this.setCartCredits = function (value) {
      cartCredits = value;
    };
    this.getCartCourseCount = function () {
      return cartCourseCount;
    };
    this.setCartCourseCount = function (value) {
      cartCourseCount = value;
    };
    this.getCartCourses = function () {
      return cartCourses;
    };
    this.setCartCourses = function (courses) {
      cartCourses.splice(0, cartCourses.length);
      if (courses) {
        angular.forEach(courses, function (course) {
          cartCourses.push(course);
        });
        this.setCartCourseCount(courses.length);
      }
    };
    this.isCourseInCart = function (course) {
      return ServiceUtilities.isCourseInList(course, this.getCartCourses());
    };
    // Server API Methods
    this.getCartFromServer = function () {
      return ServiceUtilities.getData(URLS.courseRegistrationCart + '/searchForCart');
    };
    this.addCourseToCart = function () {
      return ServiceUtilities.postData(URLS.courseRegistrationCart + '/addCourseToCart');
    };
    this.removeItemFromCart = function ($actionLink) {
      return ServiceUtilities.deleteData($actionLink);
    };
    this.invokeActionLink = function ($actionLink) {
      return ServiceUtilities.getData($actionLink);
    };
    this.updateCartItem = function () {
      return ServiceUtilities.putData(URLS.courseRegistrationCart + '/updateCartItem');
    };
    this.submitCart = function () {
      return ServiceUtilities.getData(URLS.courseRegistrationCart + '/submitCart');
    };
    this.undoDeleteCourse = function () {
      return ServiceUtilities.getData(URLS.courseRegistrationCart + '/undoDeleteCourse');
    };
  }
]);
'use strict';
angular.module('regCartApp').service('GlobalVarsService', [
  'STATE',
  'STATUS',
  function GlobalVarsService(STATE, STATUS) {
    var userId;
    var conflictMap = {};
    var courseIndexes = {};
    var courseIndexPointer = 1;
    this.getUserId = function () {
      return userId;
    };
    this.setUserId = function (value) {
      userId = value;
    };
    this.getConflictMap = function () {
      return conflictMap;
    };
    this.setConflictMap = function (value) {
      if (!angular.equals(value, conflictMap)) {
        conflictMap = value;
      }
    };
    // In this method we pass in a state and it returns a status
    this.getCorrespondingStatusFromState = function (state) {
      var retStatus = STATUS.new;
      if (STATE.processing.indexOf(state) >= 0) {
        retStatus = STATUS.processing;
      } else if (STATE.success.indexOf(state) >= 0) {
        retStatus = STATUS.success;
      } else if (STATE.error.indexOf(state) >= 0) {
        retStatus = STATUS.error;
      } else if (STATE.waitlist.indexOf(state) >= 0) {
        retStatus = STATUS.waitlist;
      } else if (STATE.action.indexOf(state) >= 0) {
        retStatus = STATUS.action;
      }
      return retStatus;
    };
    // In this method we pass in a status and it returns a message to display
    this.getCorrespondingMessageFromStatus = function (status) {
      var statusMessage = '';
      if (status === STATUS.waitlist) {
        statusMessage = 'If a seat becomes available you will be registered automatically';
      }
      return statusMessage;
    };
    /*
        Return the course index based on the course details position in the array.

        If no course index is found, add the course details to the array and
        return the new index.
         */
    this.getCourseIndex = function (courseDetails) {
      var courseDetailsString = courseDetails.courseCode + (courseDetails.regGroup || courseDetails.regGroupCode);
      // regGroupCode in schedule
      var index = courseIndexes[courseDetailsString];
      if (isNaN(index)) {
        index = courseIndexPointer++;
        courseIndexes[courseDetailsString] = index;
      }
      return index;
    };
    /*
        Updates an array of offerings with any conflicts for each one
         */
    this.updateConflicts = function (courseList, type) {
      var conflictMap = this.getConflictMap();
      for (var i = 0; i < courseList.length; i++) {
        var course = courseList[i];
        var conflicts = conflictMap[course.regGroupId];
        if (!angular.equals(course.conflicts, conflicts)) {
          switch (type) {
          case 'REG':
            // registered courses only conflict with other registered courses
            if (conflicts.type === 'REG') {
              course.conflicts = conflicts;
            }
            break;
          case 'WAIT':
            // waitlisted courses can conflict with registered or waitlisted
            if (conflicts.type === 'REG' || conflicts.type === 'WAIT') {
              course.conflicts = conflicts;
            }
            break;
          default:
            course.conflicts = conflicts;
          }
        }
      }
    };
  }
]);
'use strict';
angular.module('regCartApp').factory('loginInterceptor', [
  '$q',
  '$injector',
  '$window',
  '$rootScope',
  function ($q, $injector, $window, $rootScope) {
    return {
      'response': function (response) {
        /*
            Currently, when the session expires any http request (including REST calls) is being redirected
            to the login page which responds with an HTTP status of 200 (making it unrecognizable as an
            error. To account for this, we will look for the login page to come back in the response...
            if it does we will broadcast the sessionExpired event for the controller to handle.
             */
        var responseData = response.data;
        if (typeof responseData !== 'object' && responseData.indexOf('Kuali Student Login') > -1) {
          console.log('Informing user that session has expired...');
          $rootScope.$broadcast('sessionExpired');
          return $q.reject(response);
        } else {
          return response;
        }
      },
      'responseError': function (rejection) {
        if (rejection.status === 0) {
          //For failed connections, try to log in again and refresh the page
          console.log('Failed to execute request - trying to login');
          var LoginService = $injector.get('LoginService');
          //This should be removed for production. In the future, we should handle logouts in a user-friendly way.
          LoginService.logOnAsAdmin().query({
            userId: 'student1',
            password: 'student1'
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
angular.module('regCartApp').service('LoginService', [
  'URLS',
  'ServiceUtilities',
  function LoginService(URLS, ServiceUtilities) {
    this.logOnAsAdmin = function () {
      return ServiceUtilities.getData(URLS.developmentLogin + '/login');
    };
    this.logout = function () {
      return ServiceUtilities.getData(URLS.developmentLogin + '/logout');
    };
  }
]);
'use strict';
angular.module('regCartApp').service('MessageService', [
  '$q',
  '$resource',
  function MessageService($q, $resource) {
    // Cached messages
    var messages = null;
    this.getMessages = function () {
      var deferred = $q.defer();
      // Create the promise object
      if (messages !== null) {
        // Return out the cached messages
        deferred.resolve(messages);
      } else {
        // The messages aren't cached, load them
        this.loadMessages().query({}, function (result) {
          // Cache the messages
          messages = result;
          deferred.resolve(messages);
        }, function (error) {
          // Report out the error
          deferred.reject(error);
        });
      }
      // Return the handle on the promise
      return deferred.promise;
    };
    this.getMessage = function (messageKey) {
      var message = '';
      for (var i = 0; i < messages.length; i++) {
        if (messages[i].messageKey === messageKey) {
          message = messages[i].message;
          break;
        }
      }
      return message;
    };
    this.loadMessages = function () {
      /*
             Currently we are getting the messages from a static json file...
             this will likely be replaced by a RESTful service call...
             */
      return $resource('json/messages.json', {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: true
        }
      });
    };
  }
]);
'use strict';
angular.module('regCartApp').service('RegUtil', [function RegUtil() {
    /*
         * Converts a time string to an integer representing the minutes since
         * start of day.
         */
    this.convertTimeStringToTime = function (str) {
      var colonIndex = str.indexOf(':'), h = str.substring(0, colonIndex), m = str.substring(colonIndex + 1, str.indexOf(' ')), isPm = str.toLowerCase().indexOf('pm') !== -1;
      var time = parseInt(h) * 60;
      time += parseInt(m);
      if (isPm && h !== '12') {
        time += 60 * 12;
      }
      return time;
    };
  }]);
;
'use strict';
angular.module('regCartApp').service('ScheduleService', [
  '$q',
  '$timeout',
  'URLS',
  'STATUS',
  'ServiceUtilities',
  'GlobalVarsService',
  function ScheduleService($q, $timeout, URLS, STATUS, ServiceUtilities, GlobalVarsService) {
    var registeredCredits;
    var registeredCourseCount = 0;
    var waitlistedCredits = 0;
    var waitlistedCourseCount = 0;
    var scheduleArray;
    var registeredCourses = [];
    var waitlistedCourses = [];
    // Cache of schedules per term
    var scheduleMap = {};
    this.getSchedule = function (termId, forceLoad) {
      var deferred = $q.defer();
      if (!forceLoad) {
        // Make sure forceLoad is set
        forceLoad = false;
      }
      if (angular.isDefined(scheduleMap[termId]) && !forceLoad) {
        // Return the cached cart
        deferred.resolve(scheduleMap[termId]);
      } else {
        this.getScheduleFromServer().query({ termId: termId }, function (schedule) {
          // Cache the schedule
          scheduleMap[termId] = schedule;
          deferred.resolve(schedule);
        }, function (error) {
          // Report out the error
          deferred.reject(error);
        });
      }
      return deferred.promise;
    };
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
    this.getRegisteredCourses = function () {
      return registeredCourses;
    };
    this.getWaitlistedCourses = function () {
      return waitlistedCourses;
    };
    /*
         Helper method to concatenate all the courses on the schedule
         (both registered and waitlisted) into one array
         */
    this.getScheduledCourses = function () {
      return registeredCourses.concat(waitlistedCourses);
    };
    this.removeRegisteredCourse = function (course) {
      this.getRegisteredCourses().splice(this.getRegisteredCourses().indexOf(course), 1);
      this.setRegisteredCredits(parseFloat(this.getRegisteredCredits()) - parseFloat(course.credits));
      this.setRegisteredCourseCount(parseInt(this.getRegisteredCourseCount()) - 1);
    };
    this.removeWaitlistedCourse = function (course) {
      this.getWaitlistedCourses().splice(this.getWaitlistedCourses().indexOf(course), 1);
      this.setWaitlistedCredits(parseFloat(this.getWaitlistedCredits()) - parseFloat(course.credits));
      this.setWaitlistedCourseCount(this.getWaitlistedCourses().length);
    };
    this.updateRegisteredCourse = function (oldCourse, newCourse) {
      var credits = parseFloat(this.getRegisteredCredits()) - parseFloat(oldCourse.credits) + parseFloat(newCourse.credits);
      console.log('registeredCredits = ' + credits);
      this.setRegisteredCredits(credits);
    };
    this.updateWaitlistedCourse = function (oldCourse, newCourse) {
      var credits = parseFloat(this.getWaitlistedCredits()) - parseFloat(oldCourse.credits) + parseFloat(newCourse.credits);
      console.log('waitlistedCredits = ' + credits);
      this.setWaitlistedCredits(credits);
    };
    this.isCourseRegistered = function (course) {
      return ServiceUtilities.isCourseInList(course, this.getRegisteredCourses());
    };
    this.isCourseWaitlisted = function (course) {
      return ServiceUtilities.isCourseInList(course, this.getWaitlistedCourses());
    };
    this.getSchedules = function () {
      return scheduleArray;
    };
    this.setSchedules = function (value) {
      scheduleArray = value;
    };
    /**
         * This method takes the schedule list returned from the schedule service and updates the global counts.
         *
         * @param personSchedule
         */
    this.updateScheduleCounts = function (personSchedule) {
      var scheduleList = personSchedule.studentScheduleTermResults;
      var userId = personSchedule.userId;
      //Calculate credit count, course count and grading option count
      var creditCount = 0;
      var waitlistCreditCount = 0;
      registeredCourses.splice(0, registeredCourses.length);
      waitlistedCourses.splice(0, waitlistedCourses.length);
      this.setSchedules(scheduleList);
      angular.forEach(scheduleList, function (schedule) {
        angular.forEach(schedule.registeredCourseOfferings, function (course) {
          creditCount += parseFloat(course.credits);
          registeredCourses.push(course);
          var gradingOptionCount = 0;
          //grading options are an object (map) so there's no easy way to get the object size without this code
          angular.forEach(course.gradingOptions, function () {
            gradingOptionCount++;
          });
          course.gradingOptionCount = gradingOptionCount;
        });
        angular.forEach(schedule.waitlistCourseOfferings, function (course) {
          waitlistCreditCount += parseFloat(course.credits);
          waitlistedCourses.push(course);
          var gradingOptionCount = 0;
          //grading options are an object (map) so there's no easy way to get the object size without this code
          angular.forEach(course.gradingOptions, function () {
            gradingOptionCount++;
          });
          course.gradingOptionCount = gradingOptionCount;
        });
      });
      this.setRegisteredCourseCount(registeredCourses.length);
      this.setRegisteredCredits(creditCount);
      this.setWaitlistedCredits(waitlistCreditCount);
      this.setWaitlistedCourseCount(waitlistedCourses.length);
      GlobalVarsService.setUserId(userId);
    };
    // Schedule Poller
    this.pollRegistrationRequestStatus = function (registrationRequestId, interval, deferred) {
      // Make sure the interval is defined
      if (!angular.isNumber(interval)) {
        interval = 1000;
      }
      // Make sure the promise is set up.
      if (!deferred) {
        deferred = $q.defer();
      }
      var me = this;
      // Get a handle on the service so we can refer to it within the $timeout.
      $timeout(function () {
        // Query for the registration status
        me.getRegistrationStatus().query({ regReqId: registrationRequestId }, function (result) {
          var status = GlobalVarsService.getCorrespondingStatusFromState(result.state);
          switch (status) {
          case STATUS.new:
          case STATUS.processing:
            // The request is still new or processing, first make sure at least 1 of the items are still processing.
            var processing = false;
            angular.forEach(result.responseItemResults, function (item) {
              if (processing) {
                return;
              }
              var itemStatus = GlobalVarsService.getCorrespondingStatusFromState(item.state);
              switch (itemStatus) {
              case STATUS.new:
              case STATUS.processing:
                processing = true;
                // At least 1 item is still processing.
                break;
              }
            });
            deferred.notify(result);
            // Notify out as well to give any itemized processors full coverage of all items.
            if (processing) {
              // The request is still new or processing, reschedule the poller
              me.pollRegistrationRequestStatus(registrationRequestId, interval, deferred);
            } else {
              // A state mismatch exists on the request, all items have finished
              // successfully even though the request is still marked as new or processing.
              deferred.resolve(result);
            }
            break;
          case STATUS.success:
            // The request has finished successfully
            deferred.notify(result);
            // Notify out as well to give any itemized processors full coverage of all items.
            deferred.resolve(result);
            break;
          case STATUS.error:
            // The request has finished with an error state
            deferred.reject(result);
            break;
          }
        }, function (error) {
          // Return out the error
          deferred.reject(error);
        });
      }, interval);
      return deferred.promise;
    };
    // Server API Methods
    this.getScheduleFromServer = function () {
      return ServiceUtilities.getData(URLS.courseRegistration + '/studentSchedule');
    };
    this.dropRegistrationGroup = function () {
      return ServiceUtilities.deleteData(URLS.courseRegistration + '/registrationRequest');
    };
    this.registerForRegistrationGroup = function () {
      return ServiceUtilities.postData(URLS.courseRegistration + '/registrationRequest');
    };
    this.updateScheduleItem = function () {
      return ServiceUtilities.putData(URLS.courseRegistration + '/registrationRequest');
    };
    this.dropFromWaitlist = function () {
      return ServiceUtilities.deleteData(URLS.courseRegistration + '/waitlistRegistrationRequest');
    };
    this.updateWaitlistItem = function () {
      return ServiceUtilities.putData(URLS.courseRegistration + '/waitlistRegistrationRequest');
    };
    this.getRegistrationStatus = function () {
      return ServiceUtilities.getData(URLS.courseRegistration + '/registrationStatus');
    };
  }
]);
'use strict';
angular.module('regCartApp').service('SearchService', [
  'URLS',
  'ServiceUtilities',
  function SearchService(URLS, ServiceUtilities) {
    var searchCacheTTL = 30;
    // Seconds to keep search results cached
    var searches = [];
    // Cache of recent searches
    /**
         * Search for courses - uses a combination of client-side caching and REST call to the server API.
         *
         * @returns {{query: Function}}
         */
    this.searchForCourses = function () {
      var me = this;
      return {
        query: function (params, successCallback, errorCallback) {
          var timestamp = Math.round(+new Date() / 1000), lastSearch = null, validSearches = [];
          // Iterate through the list of cached searches, keep the not stale ones, & try to match the current search parameters against the valid cache
          angular.forEach(searches, function (search) {
            if (angular.isDefined(search.timestamp) && timestamp - search.timestamp < searchCacheTTL) {
              validSearches.push(search);
              if (search.criteria === params.criteria && search.termId === params.termId) {
                lastSearch = search;
              }
            }
          });
          // Reset the cache to the still-fresh list
          searches = validSearches;
          if (lastSearch === null) {
            // Run the search against the server
            lastSearch = angular.copy(params);
            lastSearch.timestamp = timestamp;
            // Store the current timestamp (in seconds) on the lastSearch
            me.searchForCoursesFromServer().query(params, function (results) {
              // Cache the search results
              lastSearch.results = results;
              searches.push(lastSearch);
              successCallback(results);
            }, errorCallback);
          } else {
            console.log('- Returning Cached Results (valid for ' + (searchCacheTTL - (timestamp - lastSearch.timestamp)) + 's) ');
            // The cached search results are still good, return them out.
            successCallback(lastSearch.results);
          }
        }
      };
    };
    // Server API Methods
    this.searchForCoursesFromServer = function () {
      return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/search');
    };
    this.getCourse = function () {
      return ServiceUtilities.getData(URLS.scheduleOfClasses + '/courseOfferingDetails');
    };
  }
]);
'use strict';
angular.module('regCartApp').service('ServiceUtilities', [
  '$resource',
  'APP_URL',
  function ServiceUtilities($resource, APP_URL) {
    this.getData = function (url) {
      return $resource(APP_URL + url, {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: false
        }
      });
    };
    this.deleteData = function (url) {
      return $resource(APP_URL + url, {}, {
        query: {
          method: 'DELETE',
          cache: false,
          isArray: false
        }
      });
    };
    this.postData = function (url) {
      return $resource(APP_URL + url, {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'POST',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            return transformRequest(obj);
          }
        }
      });
    };
    this.putData = function (url) {
      return $resource(APP_URL + url, {}, {
        query: {
          headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
          method: 'PUT',
          cache: false,
          isArray: false,
          transformRequest: function (obj) {
            return transformRequest(obj);
          }
        }
      });
    };
    this.getArray = function (url) {
      return $resource(APP_URL + url, {}, {
        query: {
          method: 'GET',
          cache: false,
          isArray: true
        }
      });
    };
    /*
         Global method for seeing if a course is present in a list of courses
         */
    this.isCourseInList = function (course, list) {
      if (angular.isString(course)) {
        course = { regGroupId: course };
      }
      var inList = false;
      angular.forEach(list, function (listCourse) {
        if (!inList && listCourse.regGroupId === course.regGroupId) {
          // Courses match on regGroupId
          inList = true;
        }
      });
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
  }
]);
'use strict';
angular.module('regCartApp').service('TermsService', [
  '$q',
  'ServiceUtilities',
  'URLS',
  'DEFAULT_TERM',
  function TermsService($q, ServiceUtilities, URLS, DEFAULT_TERM) {
    var selectedTerm = null;
    this.getSelectedTerm = function () {
      return selectedTerm;
    };
    this.setSelectedTerm = function (term) {
      selectedTerm = term;
    };
    this.getTermId = function () {
      var term = this.getSelectedTerm();
      if (term !== null) {
        return term.termId;
      }
      return null;
    };
    this.getTermByCode = function (code) {
      var retTerm = null;
      if (angular.isArray(terms)) {
        angular.forEach(terms, function (term) {
          if (term.termCode === code) {
            retTerm = term;
          }
        });
      }
      return retTerm;
    };
    this.getTermById = function (id) {
      var retTerm = null;
      if (angular.isArray(terms)) {
        angular.forEach(terms, function (term) {
          if (term.termId === id) {
            retTerm = term;
          }
        });
      }
      return retTerm;
    };
    // Cache of terms.
    var terms = null;
    this.getTerms = function () {
      var deferred = $q.defer();
      if (terms !== null) {
        deferred.resolve(terms);
      } else {
        this.getTermsFromServer().query({
          termCode: null,
          active: true
        }, function (result) {
          // Cache the terms
          terms = result;
          deferred.resolve(result);
        }, function (error) {
          // Report out the error
          deferred.reject(error);
        });
      }
      return deferred.promise;
    };
    // Cache of student eligibility for term
    var termEligibility = {};
    this.isStudentEligibleForTerm = function (term) {
      var deferred = $q.defer();
      if (term.termId !== DEFAULT_TERM) {
        console.log('-- Term eligibility check bypassed - term != default term');
        termEligibility[term.termId] = true;
      }
      if (angular.isDefined(termEligibility[term.termId])) {
        deferred.resolve(termEligibility[term.termId]);
      } else {
        this.checkStudentEligibilityForTerm().query({ termId: term.termId }, function (response) {
          var isEligible = angular.isDefined(response.isEligible) && response.isEligible || false;
          termEligibility[term.termId] = isEligible;
          deferred.resolve(isEligible, response);
        }, function (error) {
          deferred.reject(error);
        });
      }
      return deferred.promise;
    };
    // Server API Methods
    this.checkStudentEligibilityForTerm = function () {
      return ServiceUtilities.getData(URLS.scheduleOfClasses + '/checkStudentEligibilityForTerm');
    };
    this.getTermsFromServer = function () {
      return ServiceUtilities.getArray(URLS.scheduleOfClasses + '/terms');
    };
  }
]);
'use strict';
angular.module('regCartApp').service('CourseCalendarDataParser', [
  'DAY_CONSTANTS',
  'RegUtil',
  'GlobalVarsService',
  function (DAY_CONSTANTS, RegUtil, GlobalVarsService) {
    var conflictMap;
    /*
         Returns formatted data for display in the course calendar
         */
    this.buildCalendar = function (registered, waitlisted, cart) {
      conflictMap = {};
      // First, we split the data up by days
      var dayMap = initDayMap();
      if (registered) {
        // iterate over registered courses
        dayMap = iterateCourseList(dayMap, registered, 'REG');
      }
      if (waitlisted) {
        // iterate over waitlisted courses
        dayMap = iterateCourseList(dayMap, waitlisted, 'WAIT');
      }
      if (cart) {
        // iterate over cart
        dayMap = iterateCourseList(dayMap, cart, 'CART');
      }
      // Finally, convert all of the day data to the calendar format
      var calendar = convertMapToCalendar(dayMap);
      // Update the schedule with conflicts so they can be flagged
      // with an icon in the card. Cart courses are not flagged.
      GlobalVarsService.setConflictMap(conflictMap);
      GlobalVarsService.updateConflicts(registered, 'REG');
      GlobalVarsService.updateConflicts(waitlisted, 'WAIT');
      return calendar;
    };
    /*
         Monday through Friday are always shown, so they should be initialized
         with an empty array
         */
    function initDayMap() {
      var dayMap = {};
      dayMap[DAY_CONSTANTS.monday] = [];
      dayMap[DAY_CONSTANTS.tuesday] = [];
      dayMap[DAY_CONSTANTS.wednesday] = [];
      dayMap[DAY_CONSTANTS.thursday] = [];
      dayMap[DAY_CONSTANTS.friday] = [];
      return dayMap;
    }
    /*
         Iterate over each course in the list and group the AOs by day
         */
    function iterateCourseList(dayMap, courseList, type) {
      angular.forEach(courseList, function (course) {
        // do not show dropped courses on the calendar
        if (!course.dropped) {
          var courseDetails = {
              courseCode: course.courseCode,
              regGroup: course.regGroupCode
            };
          // Standardize the activity offering data structure between the cart & the other types
          if (type === 'CART' && angular.isUndefined(course.activityOfferings)) {
            course.activityOfferings = [];
            angular.forEach(course.schedule, function (ao) {
              if (angular.isUndefined(ao.scheduleComponents)) {
                ao.scheduleComponents = [];
                angular.forEach(ao.activityOfferingLocationTime, function (locationTime) {
                  ao.scheduleComponents.push(locationTime.time);
                });
              }
              if (angular.isUndefined(ao.activityOfferingTypeName)) {
                ao.activityOfferingTypeName = ao.activityOfferingType;
              }
              course.activityOfferings.push(ao);
            });
          }
          // iterate over activity offerings
          angular.forEach(course.activityOfferings, function (ao) {
            angular.forEach(ao.scheduleComponents, function (scheduleComponent) {
              dayMap = updateDayMap(dayMap, courseDetails, scheduleComponent, type, course);
            });
          });
        }
      });
      return dayMap;
    }
    /*
         Add the course to the map for for appropriate day(s). TBA courses will not be
         shown on the grid.

         We are using the days String to determine this as it is more reliable than
         the booleans.
         */
    function updateDayMap(dayMap, courseDetails, scheduleComponent, type, course) {
      if (!scheduleComponent.isTBA) {
        angular.forEach(DAY_CONSTANTS.dayArray, function (day) {
          if (scheduleComponent.days.indexOf(day) > -1) {
            dayMap = addToDayMap(dayMap, courseDetails, scheduleComponent, day, type, course);
          }
        });
      }
      return dayMap;
    }
    /*
         Add the AO to the day map
         */
    function addToDayMap(dayMap, courseDetails, scheduleComponent, day, type, fullCourse) {
      if (!scheduleComponent.startTime || !scheduleComponent.endTime) {
        // Can't do anything without a start or end time
        return dayMap;
      }
      if (!angular.isArray(dayMap[day])) {
        dayMap[day] = [];
        /*
                 If the student has any classes on saturday or sunday,
                 add both days to the calendar
                 */
        if (day === DAY_CONSTANTS.saturday && !angular.isArray(dayMap[DAY_CONSTANTS.sunday])) {
          dayMap[DAY_CONSTANTS.sunday] = [];
        }
        if (day === DAY_CONSTANTS.sunday && !angular.isArray(dayMap[DAY_CONSTANTS.saturday])) {
          dayMap[DAY_CONSTANTS.saturday] = [];
        }
      }
      var startTime = RegUtil.convertTimeStringToTime(scheduleComponent.startTime);
      var endTime = RegUtil.convertTimeStringToTime(scheduleComponent.endTime);
      /*
             Course started before midnight, and ended after midnight, so
             we need to extend the end time so that they can display on the
             same day.
             */
      if (endTime < startTime) {
        endTime += 1440;  // 24 hours
      }
      var course = {
          index: getCourseIndex(courseDetails),
          courseCode: courseDetails.courseCode,
          startTime: startTime,
          endTime: endTime,
          type: type,
          key: courseDetails.courseCode + '.' + day + '.' + startTime + '.' + endTime,
          details: fullCourse
        };
      dayMap[day].push(course);
      return dayMap;
    }
    /*
         Take the full day map and convert to the full calendar format,
         looking for conflicts along the way.
         */
    function convertMapToCalendar(dayMap) {
      var typeArray = [
          'REG',
          'WAIT',
          'CART'
        ], days = [], startTime = null, endTime = null, buffer = 60;
      // the amount of buffer (in minutes) to give to either side of the calendar.
      angular.forEach(DAY_CONSTANTS.dayArray, function (dayString) {
        var courses = dayMap[dayString];
        if (angular.isArray(courses)) {
          // Iterate through all of the courses and bucket them by their type.
          var courseMap = {};
          angular.forEach(courses, function (course) {
            var type = course.type;
            if (!angular.isArray(courseMap[type])) {
              courseMap[type] = [];
            }
            courseMap[type].push(course);
            // Determine the earliest start time (- buffer)
            if (startTime === null || course.startTime - buffer < startTime) {
              startTime = course.startTime - buffer;
            }
            // Determine the latest end time (+ buffer)
            if (endTime === null || course.endTime + buffer > endTime) {
              endTime = course.endTime + buffer;
            }
          });
          // In the order defined in the typeArray, iterate through the courses again and add them to the day.
          var dayCourses = [];
          angular.forEach(typeArray, function (type) {
            angular.forEach(courseMap[type], function (course) {
              dayCourses.push(course);
            });
          });
          // Update the courses with the conflict data
          updateCoursesWithConflictData(dayCourses);
          // Sort the day's courses by their start time
          sortCourses(dayCourses);
          // Add the day to the days array
          days.push({
            day: dayString,
            courses: dayCourses
          });
        }
      });
      if (startTime < 0) {
        startTime = 0;  // midnight
      }
      return {
        timeRange: [
          startTime,
          endTime
        ],
        days: days
      };
    }
    /*
         See if the course conflicts with any courses already slotted
         in this day. If it does, add a conflict for both courses.
         */
    function updateCoursesWithConflictData(courses) {
      // Initialize the lanes with an initial lane, and initialize the conflict map
      var lanes = [[]], laneMap = {};
      // Slot the course in the appropriate lane
      angular.forEach(courses, function (course) {
        course.conflicts = [];
        // The schedule data for the courses that this course conflicts with
        course.lane = 0;
        // The lane this course is in relative to its conflicts (each new conflicting course gets a higher value).
        var slotted = false;
        angular.forEach(lanes, function (lane) {
          var conflicting = false;
          angular.forEach(lane, function (slottedCourse) {
            if (coursesConflict(course, slottedCourse)) {
              // add both courses to the conflict map
              addConflictToMap(course, slottedCourse);
              addConflictToMap(slottedCourse, course);
              conflicting = true;
              // Make sure both the course & the conflicting course know about the courses they conflict with.
              course.conflicts.push(slottedCourse.key);
              slottedCourse.conflicts.push(course.key);
            }
          });
          // Check to see if this course conflicts with any course in this lane.
          if (!conflicting && !slotted) {
            // Slot this course in this lane
            course.lane = lanes.indexOf(lane);
            laneMap[course.key] = course.lane;
            // Add the course to the lane & sort the lane by startTime
            lane.push(course);
            sortCourses(lane);
            slotted = true;
          }
        });
        if (!slotted) {
          // The course conflicted with every lane, we need to add a new lane for it.
          var position = lanes.push([course]);
          // Slot this course in this lane
          course.lane = position - 1;
          laneMap[course.key] = course.lane;
        }
      });
      // Update the course to know how many lanes are around it so that it can be laid out correctly in the UI.
      angular.forEach(courses, function (course) {
        var maxConflictLane = 0;
        angular.forEach(course.conflicts, function (key) {
          if (laneMap[key] > maxConflictLane) {
            maxConflictLane = laneMap[key];
          }
        });
        course.conflictCount = Math.max(maxConflictLane, course.lane);
      });
      return courses;
    }
    /*
         A course conflicts if its time range overlaps at all with another course
         E.g. Course:                    [-----]
         Conflicts with both:      [--] [---]
         */
    function coursesConflict(c1, c2) {
      return c1.startTime <= c2.endTime && c1.endTime >= c2.startTime;
    }
    /*
         Sort a list of courses by their start time
         */
    function sortCourses(courses) {
      courses.sort(function (a, b) {
        return a.startTime === b.startTime ? 0 : a.startTime < b.startTime ? -1 : 1;  // Sort by startTime
      });
      return courses;
    }
    /*
         Use the course code + the reg group code to retrieve an index
         for color-coding and conflict matching.

         All indexes will be stored in the GlobalVarsService, so that
         if a student drops or re-adds a course the colors will remain
         consistent (at least until they refresh, at which time the
         indexes will be reset).
         */
    function getCourseIndex(courseDetails) {
      return GlobalVarsService.getCourseIndex(courseDetails);
    }
    /*
         Add the indicated course to the conflict map (indexed by regGroupId)
         */
    function addConflictToMap(course, conflict) {
      var regGroupId = course.details.regGroupId;
      var conflictOffering = {
          courseCode: conflict.details.courseCode,
          regGroupCode: conflict.details.regGroupCode,
          type: conflict.type
        };
      if (!angular.isArray(conflictMap[regGroupId])) {
        conflictMap[regGroupId] = [];
      }
      var matchFound = false;
      for (var i = 0; i < conflictMap[regGroupId].length; i++) {
        if (angular.equals(conflictMap[regGroupId][i], conflictOffering)) {
          matchFound = true;
          break;
        }
      }
      if (!matchFound) {
        conflictMap[regGroupId].push(conflictOffering);
      }
    }
  }
]);
angular.module('regCartApp').directive('courseCalendar', function () {
  return {
    restrict: 'E',
    scope: { size: '@' },
    templateUrl: 'partials/courseCalendar.html',
    controller: [
      '$scope',
      'CourseCalendarDataParser',
      'CartService',
      'ScheduleService',
      function ($scope, CourseCalendarDataParser, CartService, ScheduleService) {
        /*
                 Filter used by view to show courses based on the toggles in the legend
                 */
        $scope.courseFilter = function (course) {
          var select = true;
          switch (course.type) {
          case 'REG':
            select = !$scope.hideRegistered;
            break;
          case 'WAIT':
            select = !$scope.hideWaitlisted;
            break;
          case 'CART':
            select = !$scope.hideCart;
            break;
          }
          return select;
        };
        /*
                 Filter used by the view to restrict the visible time range to the data being shown
                 */
        $scope.timeFilter = function (time) {
          var h = time.getHours();
          return $scope.visibleTimeRange[0] / 60 <= h && h <= $scope.visibleTimeRange[1] / 60;
        };
        /*
                 Handle the course-clicked event that is emitted from the child course-calendar-item directive
                 */
        $scope.selectedCourse = null;
        $scope.$on('course-clicked', function (event, course) {
          if ($scope.selectedCourse && $scope.selectedCourse.index === course.index) {
            $scope.selectedCourse = null;
          } else {
            $scope.selectedCourse = course;
          }
          $scope.$apply();
        });
        $scope.clearSelectedCourse = function () {
          $scope.selectedCourse = null;
        };
        var size = $scope.size;
        if (size !== 'small') {
          size = 'large';
        }
        $scope.calendarSize = size;
        $scope.hideRegistered = false;
        $scope.hideWaitlisted = false;
        $scope.hideCart = false;
        $scope.visibleTimeRange = [];
        // Create the list of times (date objects) to be shown in the view
        $scope.times = [];
        for (var i = 0; i < 24; i++) {
          $scope.times.push(new Date(0, 0, 0, i, 0, 0, 0));
        }
        $scope.registered = ScheduleService.getRegisteredCourses();
        $scope.waitlisted = ScheduleService.getWaitlistedCourses();
        $scope.cart = CartService.getCartCourses();
        var init = function (list) {
          if (list) {
            var calendar = CourseCalendarDataParser.buildCalendar($scope.registered, $scope.waitlisted, $scope.cart);
            $scope.visibleTimeRange = [
              Math.floor(calendar.timeRange[0] / 60) * 60,
              Math.ceil(calendar.timeRange[1] / 60) * 60
            ];
            $scope.days = calendar.days;
          }
        };
        init(true);
        $scope.$watchCollection('registered', init);
        $scope.$watchCollection('waitlisted', init);
        $scope.$watchCollection('cart', init);
      }
    ]
  };
});
;
/**
 * Course Calendar - Course Lane Directive
 */
angular.module('regCartApp').directive('courseCalendarLane', [
  '$timeout',
  '$window',
  function ($timeout, $window) {
    return {
      restrict: 'CAE',
      link: function (scope, element, attr) {
        scope.scrollAfter = attr.scrollAfter || null;
        /**
                 * Update the lane's height to be accurate to the # of visible elements in it
                 */
        scope.updateLaneHeight = function () {
          var lanes = 0;
          angular.forEach(scope.day.courses, function (course) {
            if (lanes < course.lane + 1) {
              lanes = course.lane + 1;
            }
          });
          var items = element.children();
          if (items.length > 0) {
            var itemHeight = element.children()[0].offsetHeight, targetHeight = itemHeight * lanes;
            if (itemHeight > 0 && targetHeight > 0) {
              element.height(targetHeight);
            }
          }
        };
        $timeout(scope.updateLaneHeight);
        // When the window is resized, update the parent element's height
        angular.element($window).on('resize', function () {
          $timeout(scope.updateLaneHeight);
        });
        // Update the lane height when the 'updateLaneHeight' event is received
        scope.$on('updateLaneHeight', function () {
          $timeout(scope.updateLaneHeight);
        });
      }
    };
  }
]);
/**
 * Course Calendar - Course Item Directive
 */
angular.module('regCartApp').directive('courseCalendarItem', [
  '$timeout',
  '$window',
  function ($timeout, $window) {
    return {
      restrict: 'CAE',
      link: function (scope, element) {
        /**
                 * Layout the element within its parent based on the course duration and position
                 */
        function layout() {
          var timeRange = scope.visibleTimeRange, totalRange = timeRange[1] - timeRange[0], duration = scope.course.endTime - scope.course.startTime;
          // Calculate the proportion of the total size & the position as a percentage so
          // it doesn't have to be updated when the window is resized.
          var proportion = duration * 100 / totalRange,
            // Proportion of total width (%)
            position = (scope.course.startTime - timeRange[0]) * 100 / totalRange;
          // Position within the parent element (%);
          // Persist the calculated position and proportion on the course
          scope.course.layout = {
            position: position,
            proportion: proportion
          };
          // Update the element to be position correctly relative to the parent container
          element.css({
            left: position + '%',
            top: position + '%',
            height: proportion + '%',
            width: proportion + '%'
          });
          // Offset the course for conflicts
          offsetForConflicts();
          // Update the lane height now that we have a new course element
          scope.$emit('updateLaneHeight');
        }
        /**
                 * Offset the course based on the conflict position & window layout
                 */
        function offsetForConflicts() {
          if (angular.isUndefined(scope.course.layout)) {
            layout();
            return;
          }
          var css = {
              left: scope.course.layout.position + '%',
              top: scope.course.layout.position + '%',
              height: scope.course.layout.proportion + '%',
              width: scope.course.layout.proportion + '%'
            };
          if (scope.course.conflictCount > 0) {
            var parent = element.parent();
            if (parent.width() > parent.height()) {
              // Landscape / large-format layout - courses laid out horizontally
              css.top = scope.course.lane * element[0].offsetHeight + 'px';  // Offset this block in px from the top according to its conflict position
            } else {
              // Portrait / small-format / mobile layout - courses laid out vertically
              var width = 100;
              // Width of the element based on the # of lanes around it
              if (scope.course.conflictCount > 0) {
                width = 100 / (scope.course.conflictCount + 1);  // Lane width
              }
              css.left = width * scope.course.lane + '%';
              // Offset this block a % from the left according to its conflict position
              css.width = width + '%';  // Portrait layout conflicts get a proportion of the total width.
            }
          }
          element.css(css);
        }
        $timeout(layout);
        // When the window is resized, update the element's offset for conflicts
        angular.element($window).on('resize', function () {
          $timeout(offsetForConflicts);
        });
        // Fire a 'course-clicked' event when the course is clicked. This is caught and handled by the CourseCalendar directive.
        element.bind('click', function () {
          scope.$emit('course-clicked', scope.course);
        });
      }
    };
  }
]);
'use strict';
angular.module('regCartApp').directive('courseCard', function () {
  return {
    transclude: true,
    scope: {
      schedules: '=',
      credits: '=',
      type: '@'
    },
    templateUrl: 'partials/courseCard.html',
    controller: 'CardCtrl'
  };
}).directive('courseAccordion', function () {
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      course: '=',
      type: '@',
      cardIndex: '=',
      cartId: '='
    },
    templateUrl: 'partials/courseAccordion.html',
    controller: 'CardCtrl'
  };
}).controller('CardCtrl', [
  '$scope',
  '$timeout',
  'STATUS',
  'GRADING_OPTION',
  'COURSE_TYPES',
  'GlobalVarsService',
  function ($scope, $timeout, STATUS, GRADING_OPTION, COURSE_TYPES, GlobalVarsService) {
    /*
             Utility function for providing configuration variables based on
             whether the course in scope is registered, waitlist, or cart.
             */
    function getConfig() {
      var config;
      switch ($scope.type) {
      case COURSE_TYPES.waitlisted:
        config = {
          heading: 'Waitlisted',
          prefix: 'waitlisted',
          prefix2: 'waitlist_',
          prefix3: 'waitlist_',
          remove: 'Remove'
        };
        break;
      case COURSE_TYPES.cart:
        config = {
          heading: 'Cart',
          prefix: 'cart',
          prefix2: '',
          prefix3: 'cart_',
          remove: 'Remove'
        };
        break;
      default:
        // 'registered'
        config = {
          heading: 'Registered',
          prefix: 'reg',
          prefix2: '',
          prefix3: 'schedule_',
          remove: 'Drop'
        };
      }
      return config;
    }
    $scope.config = getConfig();
    $scope.courseTypes = COURSE_TYPES;
    /*
            Returns either registered or waitlisted course offerings based on the
            type in scope
             */
    $scope.courseOfferings = function (schedule) {
      var offerings;
      switch ($scope.type) {
      case COURSE_TYPES.waitlisted:
        offerings = schedule.waitlistCourseOfferings;
        break;
      default:
        // 'registered'
        offerings = schedule.registeredCourseOfferings;
      }
      return offerings;
    };
    /*
            Closes the drop confirmation dialog
             */
    $scope.cancelDropConfirmation = function (course) {
      course.dropping = false;
    };
    /*
            Shows the grading badge (unless it is letter -- the default)
             */
    $scope.showBadge = function (course) {
      return course.gradingOptionId !== GRADING_OPTION.letter || course.editGradingOption;
    };
    /*
            Edits the credit and grading options for a course
             */
    $scope.editItem = function (course) {
      course.newCredits = course.credits;
      course.newGrading = course.gradingOptionId;
      course.editing = true;
    };
    /*
             Returns the grading option for the course
             */
    $scope.gradingOption = function (course) {
      return course.gradingOptions[$scope.course.gradingOptionId];
    };
    /*
             Returns the course index
             */
    $scope.courseIndex = function (course) {
      if (!angular.isDefined(course.index) || !course.index) {
        course.index = GlobalVarsService.getCourseIndex(course);
      }
      return course.index;
    };
    /*
             Closes a status message card
             */
    $scope.removeStatusMessage = function (course) {
      course.statusMessage = null;
      $scope.$emit('courseStatusMessageRemoved', $scope.type, course);
    };
    /*
             Common "drop" functionality. Can be used to drop a registered course,
             remove a course from the waitlist, or remove a course from the cart
             */
    $scope.dropCourse = function (course) {
      switch ($scope.type) {
      case COURSE_TYPES.cart:
        // emit an event to the parent scope (handled in cart.js)
        $scope.$emit('deleteCartItem', course);
        break;
      default:
        // 'registered', 'waitlist'
        console.log('Open drop confirmation');
        course.dropping = true;
      }
    };
    /*
             Emits a 'dropCourse' event and handles the result of that action accordingly.
             */
    $scope.dropCourseConfirmed = function (course) {
      course.dropping = false;
      // used to display confirmation popup
      course.dropProcessing = true;
      // used to display spinner while poll is running
      course.statusMessage = {
        txt: '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> drop processing',
        type: STATUS.processing
      };
      $scope.$emit('dropCourse', $scope.type, course, function () {
        course.dropped = true;
        // used to display course details vs success to drop message
        course.dropProcessing = false;
        var message;
        switch ($scope.type) {
        case COURSE_TYPES.registered:
          message = '<strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> dropped successfully';
          break;
        case COURSE_TYPES.waitlisted:
          message = 'Removed from waitlist for <strong>' + course.courseCode + ' (' + course.regGroupCode + ')</strong> successfully';
          break;
        }
        course.statusMessage = {
          txt: message,
          type: STATUS.success
        };
      }, function (message) {
        course.dropProcessing = false;
        course.statusMessage = {
          txt: message,
          type: STATUS.error
        };
      });
    };
    /*
             Emits an 'updateCourse' event and handles the result of that action accordingly.
             */
    $scope.updateItem = function (course) {
      console.log('Updating ' + $scope.type + ' course. Grading: ' + course.newGrading + ', Credits: ' + course.newCredits);
      course.requestProcessing = true;
      // used to display spinner while poll is running
      // Create version of the course with the updates applied
      var newCourse = angular.copy(course);
      applyCourseUpdates(newCourse);
      $scope.$emit('updateCourse', $scope.type, course, newCourse, function () {
        course.requestProcessing = false;
        updateCard(course);
      }, function (message) {
        course.requestProcessing = false;
        course.statusMessage = {
          txt: message,
          type: STATUS.error
        };
      });
    };
    /*
             Parses the course conflicts into user-friendly text
             */
    $scope.parseConflicts = function (conflicts) {
      var message = '';
      if (angular.isArray(conflicts) && conflicts.length > 0) {
        for (var i = 0; i < conflicts.length; i++) {
          if (i > 0) {
            message += ', ';
          }
          message += conflicts[i].courseCode;
        }
      }
      return message;
    };
    /*
             Helper method for applying the updates to a course model
             */
    function applyCourseUpdates(course) {
      course.credits = course.newCredits;
      course.gradingOptionId = course.newGrading;
      return course;
    }
    /*
            Common code for displaying the "glow" on changed items when updating a card.
             */
    function updateCard(course) {
      // Apply the updates
      var oldCourse = angular.copy(course);
      applyCourseUpdates(course);
      course.editing = false;
      course.isopen = false;
      // collapse the card
      // This part is responsible for glow effect: when the card is updated (whether credit or grading) we want to highlight the change and then fade the highlight away after 2 secs
      if (course.gradingOptionId !== oldCourse.gradingOptionId) {
        // the highlighting fades in & stays for 2 seconds
        course.editGradingOption = true;
        $timeout(function () {
          course.editGradingOption = false;
        }, 2000);
      }
      if (course.credits !== oldCourse.credits) {
        // the highlighting fades in & stays for 2 seconds
        course.editCredits = true;
        $timeout(function () {
          course.editCredits = false;
        }, 2000);
      }
    }
  }
]);
;
'use strict';
angular.module('regCartApp').directive('courseOptions', function () {
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      course: '=',
      maxOptions: '@max',
      prefix: '@',
      showAll: '@',
      moreButtonSelectBehavior: '@moreBehavior',
      cancelFn: '&onCancel',
      submitFn: '&onSubmit'
    },
    templateUrl: 'partials/courseOptions.html',
    controller: [
      '$scope',
      '$modal',
      function ($scope, $modal) {
        var course = $scope.course, maxOptions = $scope.maxOptions || 4, showAll = $scope.showAll ? true : false, moreButtonSelectBehavior = $scope.moreButtonSelectBehavior || 'expand';
        $scope.showAllCreditOptions = showAll;
        $scope.showAllGradingOptions = showAll;
        // Transpose the grading options object into a more consumable format for the view
        $scope.gradingOptions = [];
        if (course && course.gradingOptions) {
          angular.forEach(course.gradingOptions, function (v, k) {
            this.push({
              key: k,
              label: v
            });
          }, $scope.gradingOptions);
        }
        // Sort the credit options as numbers rather than strings
        course.creditOptions.sort(function (o1, o2) {
          var f1 = parseFloat(o1), f2 = parseFloat(o2);
          return f1 === f2 ? 0 : f1 > f2 ? 1 : -1;
        });
        $scope.creditOptionsFilter = function (option) {
          if (!course || $scope.showAllCreditOptions) {
            return true;
          }
          return shouldShow(course.creditOptions, course.credits, option);
        };
        $scope.gradingOptionsFilter = function (option) {
          if (!course || $scope.showAllGradingOptions) {
            return true;
          }
          return shouldShow(Object.keys(course.gradingOptions), course.grading, option.key);
        };
        $scope.showMoreCreditOptions = function () {
          if (moreButtonSelectBehavior === 'expand') {
            $scope.showAllCreditOptions = true;
          } else {
            showOptionsDialog();
          }
        };
        $scope.showMoreGradingOptions = function () {
          if (moreButtonSelectBehavior === 'expand') {
            $scope.showAllGradingOptions = true;
          } else {
            showOptionsDialog();
          }
        };
        $scope.shouldShowMoreCreditOptionsToggle = function () {
          return !$scope.showAllCreditOptions && course.creditOptions.length > maxOptions;
        };
        $scope.shouldShowMoreGradingOptionsToggle = function () {
          return !$scope.showAllGradingOptions && Object.keys(course.gradingOptions).length > maxOptions;
        };
        $scope.cancel = function () {
          console.log('Canceling options changes');
          // Reset the edit state on the course & form
          course.newCredits = course.credits;
          course.newGrading = course.grading || course.gradingOptionId;
          // grading in cart, gradingOptionId in schedule
          course.status = '';
          // From cart
          course.editing = false;
          // From schedule
          if ($scope.cancelFn) {
            $scope.cancelFn({ course: course });
          }
          // Reset the state of the options form
          reset();
        };
        $scope.submit = function () {
          console.log('Submitting options form');
          if ($scope.submitFn) {
            $scope.submitFn({ course: course });
          }
          // Reset the state of the options form
          reset();
        };
        $scope.showGradingHelp = function () {
          $modal.open({ templateUrl: 'partials/gradingOptionsHelp.html' });
        };
        function shouldShow(options, selectedItem, currentItem) {
          if (options.length <= maxOptions) {
            return true;
          }
          var selectedItemIndex = options.indexOf(selectedItem), currentItemIndex = options.indexOf(currentItem), padding = 2, min = Math.max(0, Math.min(selectedItemIndex - padding, options.length - maxOptions)), max = Math.min(min + maxOptions, options.length) - 1;
          return min <= currentItemIndex && currentItemIndex <= max;
        }
        function showOptionsDialog() {
          // Create a sanitized copy of the scope for the modal dialog
          var modalScope = $scope.$new();
          modalScope.course = angular.copy(course);
          modalScope.cancel = function () {
          };
          modalScope.submit = function () {
          };
          course.editing = false;
          var dialog = $modal.open({
              backdrop: 'static',
              template: '<div class="kscr-AdditionalOptions">' + '<course-options course="course" show-all="true" max="' + maxOptions + '" prefix="modal_' + ($scope.prefix ? $scope.prefix : '') + '" on-submit="modalSubmit()" on-cancel="modalCancel()"></course-options>' + '</div>',
              scope: modalScope,
              controller: [
                '$scope',
                function ($modalScope) {
                  // Show all options within the modal dialog
                  $modalScope.showAllCreditOptions = true;
                  $modalScope.showAllGradingOptions = true;
                  $modalScope.modalCancel = function () {
                    $modalScope.$dismiss('cancel');
                  };
                  $modalScope.modalSubmit = function () {
                    $modalScope.$close($modalScope.course);
                  };
                }
              ]
            });
          dialog.result.then(function (modalCourse) {
            course.newGrading = modalCourse.newGrading;
            course.newCredits = modalCourse.newCredits;
            $scope.submit();
          }, function () {
            $scope.cancel();
          });
        }
        function reset() {
          // Reset the option visibility based on the showAll parameter.
          $scope.showAllCreditOptions = showAll;
          $scope.showAllGradingOptions = showAll;
        }
      }
    ]
  };
});
;
'use strict';
/*
This is a collection of smaller directives used for course registration
 */
angular.module('regCartApp').directive('focusMe', [
  '$timeout',
  '$parse',
  function ($timeout, $parse) {
    return {
      link: function (scope, element, attrs) {
        var model = $parse(attrs.focusMe);
        scope.$watch(model, function (value) {
          if (value === true) {
            $timeout(function () {
              element[0].focus();
            });
          }
        });
        element.bind('blur', function () {
          $timeout(function () {
            element[0].focus();
          });
        });
      }
    };
  }
]).directive('focusOnce', [
  '$timeout',
  '$parse',
  function ($timeout, $parse) {
    return {
      link: function (scope, element, attrs) {
        var model = $parse(attrs.focusOnce);
        scope.$watch(model, function (value) {
          if (value === true) {
            $timeout(function () {
              element[0].focus();
            });
          }
        });
      }
    };
  }
]).directive('dropMenu', [
  '$window',
  function ($window) {
    return {
      controller: [
        '$scope',
        function ($scope) {
          return angular.element($window).bind('resize', function () {
            // if the menu is open while the window is resized, close it
            if ($scope.dropMenu === true) {
              $scope.dropMenu = false;
            }
          });
        }
      ],
      templateUrl: 'dropMenu.html'
    };
  }
]).directive('compile', [
  '$compile',
  function ($compile) {
    return function (scope, element, attrs) {
      scope.$watch(function (scope) {
        return scope.$eval(attrs.compile);
      }, function (value) {
        element.html(value);
        $compile(element.contents())(scope);
      });
    };
  }
]);
;
// Common mask functionality
var maskUtils = {
    mask: function (targetElement, maskElement) {
      if (maskElement) {
        // Remove the mask element from its current parent
        maskElement.remove();
      } else {
        // Make sure the mask element has been created
        maskElement = angular.element('<div class="util-Mask"/>');
      }
      // Append the maskElement to the body
      targetElement.append(maskElement);
      targetElement.addClass('util-Mask--masked');
      return maskElement;
    },
    unmask: function (targetElement, maskElement) {
      if (!maskElement) {
        // Not masked
        return;
      }
      maskElement.remove();
      maskElement = null;
      targetElement.removeClass('util-Mask--masked');
    },
    resetZIndex: function (element, zIndex) {
      element.css('zIndex', zIndex);
    },
    updateZIndex: function (element, maskElement) {
      // Update the element's z-index to be higher than the mask. This assumes that the mask has a set zIndex.
      var zIndex = maskElement.css('zIndex'), initialZIndex = element.css('zIndex');
      element.css('zIndex', zIndex + 1);
      return initialZIndex;
    }
  };
/**
 * Mask screen directive that masks the screen when the mask-screen value is true.
 * The parameter should be a boolean value.
 *
 * @example <div mask-screen="maskWhenTrue"></div>
 */
angular.module('regCartApp').directive('maskScreen', [
  '$timeout',
  '$document',
  function ($timeout, $document) {
    return {
      restrict: 'A',
      scope: { maskScreen: '=' },
      link: function (scope, elem) {
        var targetElement = $document.find('body'), maskElement = null, isMasked = false, initialZIndex;
        // Mask the screen
        function mask() {
          maskElement = maskUtils.mask(targetElement);
          // Bind up to unmask when the mask is clicked
          maskElement.bind('click', function () {
            unmask();
          });
          initialZIndex = maskUtils.updateZIndex(elem, maskElement);
          isMasked = true;
        }
        // Unmask the screen
        function unmask() {
          maskUtils.unmask(targetElement, maskElement);
          maskUtils.resetZIndex(elem, initialZIndex);
          isMasked = false;
          // Update the scope.maskScreen value to be accurate.
          $timeout(function () {
            // This needs to be in a timeout in order for the parent scope to see the change.
            scope.maskScreen = false;
          });
        }
        // Toggle the mask
        function toggleMask(shouldMask) {
          if (shouldMask && !isMasked) {
            mask();
          } else if (!shouldMask && isMasked) {
            unmask();
          }
        }
        // Listen for the maskScreen value to change and handle it accordingly
        scope.$watch('maskScreen', toggleMask);
        toggleMask(scope.maskScreen);
        // Cleanup when the element is being destroyed
        elem.bind('$destroy', unmask);
      }
    };
  }
]);
/**
 * Mask when directive that masks an element when the mask-when value is true.
 * The parameter should be a boolean value.
 *
 * @example <div mask-when="maskWhenTrue"></div>
 */
angular.module('regCartApp').directive('maskWhen', [
  '$timeout',
  function ($timeout) {
    return {
      restrict: 'A',
      scope: { maskWhen: '=' },
      link: function (scope, elem) {
        var maskElement = null, isMasked = false;
        // Mask the screen
        function mask() {
          maskElement = maskUtils.mask(elem);
          // Bind up to unmask when the mask is clicked
          maskElement.bind('click', function () {
            unmask();
          });
          isMasked = true;
        }
        // Unmask the screen
        function unmask() {
          maskUtils.unmask(elem, maskElement);
          isMasked = false;
          // Update the scope.maskWhen value to be accurate.
          $timeout(function () {
            // This needs to be in a timeout in order for the parent scope to see the change.
            scope.maskWhen = false;
          });
        }
        // Toggle the mask
        function toggleMask(shouldMask) {
          if (shouldMask && !isMasked) {
            mask();
          } else if (!shouldMask && isMasked) {
            unmask();
          }
        }
        // Listen for the maskScreen value to change and handle it accordingly
        scope.$watch('maskWhen', toggleMask);
        toggleMask(scope.maskWhen);
        // Cleanup when the element is being destroyed
        elem.bind('$destroy', unmask);
      }
    };
  }
]);
'use strict';
angular.module('regCartApp').directive('searchDetailsList', [
  '$timeout',
  '$animate',
  'DetailsFactory',
  function ($timeout, $animate, DetailsFactory) {
    return {
      restrict: 'E',
      templateUrl: 'partials/searchDetailsList.html',
      scope: {
        searchDetails: '=',
        selectable: '=',
        config: '@'
      },
      link: function (scope) {
        var detailsConfig = new DetailsFactory(scope.config);
        // reorganize the data into sections
        scope.sections = detailsConfig.getSections(scope.searchDetails);
        // set the default field for each row
        if (angular.isFunction(detailsConfig.setDefaultField)) {
          detailsConfig.setDefaultField(scope.searchDetails);
        }
        // turn off ng-repeat animations for better performance
        $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-details-grid')));
        // initialize the tabs
        for (var i = 0; i < scope.sections.length; i++) {
          scope.sections[i].tabs = angular.copy(detailsConfig.tabs);
          scope.sections[i].tab = angular.copy(detailsConfig.selectedTab);
        }
        // set the field options
        scope.fieldOptions = detailsConfig.fieldOptions;
        scope.icons = detailsConfig.icons;
        // this method selects a tab and sets the display flags accordingly
        scope.select = function (section, tab) {
          section.tab = tab;
          for (var i = 0; i < section.tabs.length; i++) {
            section.tabs[i].selected = section.tabs[i].id === tab || tab === 'all';
          }
        };
        // if a row is selected, emit an event to the search details controller
        scope.selectRow = function (searchDetail) {
          if (scope.selectable && !angular.isUndefined(detailsConfig.selectEvent)) {
            scope.$emit(detailsConfig.selectEvent, searchDetail);
          }
        };
        // Displays the details in batches for performance
        var stagger = 20;
        scope.limit = stagger;
        scope.$watch('limit', function () {
          if (scope.limit < scope.searchDetails.length) {
            $timeout(function () {
              scope.limit += stagger;
            }, 250);
          }
        });
      }
    };
  }
]);
;
'use strict';
angular.module('regCartApp').directive('searchFacet', [
  'SearchFacetService',
  function (SearchFacetService) {
    return {
      restrict: 'ECA',
      scope: {
        facet: '=',
        results: '='
      },
      templateUrl: 'partials/searchFacet.html',
      controller: [
        '$scope',
        function ($scope) {
          // Make sure this facet is properly configured
          SearchFacetService.initFacet($scope.facet);
          /**
                 * Tally the count of results that match each option.
                 * Uses the facet's filter method to check if an option would match a result item.
                 *
                 * @param array of options
                 * @param array of results
                 * @return array of options with updated counts
                 */
          function tallyOptionCounts(options, results) {
            // First, reset the counts
            angular.forEach(options, function (option) {
              option.count = 0;
            });
            // Iterate through the results, if the filter method matches the option, increment the count.
            angular.forEach(results, function (item) {
              angular.forEach(options, function (option) {
                if ($scope.facet.filter(item, option.value)) {
                  option.count++;
                }
              });
            });
            return options;
          }
          /**
                 * Calculate the array of options from the results list using the facet config.
                 *
                 * @param array of results
                 * @return array of options
                 */
          function calculateOptions(results) {
            // 1. Call prepare() on the facet if that method is defined
            if (angular.isFunction($scope.facet.prepare)) {
              $scope.facet.prepare(results);
            }
            // 2. Build out the facet options based on the current search results.
            var options = $scope.facet.optionsProvider(results);
            // 3. Call refine() on the facet if that method is defined
            if (angular.isFunction($scope.facet.refine)) {
              options = $scope.facet.refine(options, results);
              // The option counts are now unreliable. Retally them.
              options = tallyOptionCounts(options, results);
            }
            return options;
          }
          /**
                 * Refresh the facet options based on the current search results
                 *
                 * @param array of results
                 */
          function reset(results) {
            // Clear out the selected options
            if ($scope.options.length > 0) {
              $scope.clearSelectedOptions();
            }
            // Set the new options into the scope
            var options = calculateOptions(results);
            $scope.options = options;
            // Enforce the autoCollapse option when there is only 1 option.
            if ((!angular.isDefined($scope.facet.autoCollapse) || $scope.facet.autoCollapse) && options.length === 1) {
              $scope.isCollapsed = true;
            }
          }
          $scope.options = [];
          // Facet options based on the current search results
          if (angular.isUndefined($scope.facet.selectedOptions)) {
            $scope.facet.selectedOptions = [];  // Currently Selected facet options
          }
          // Watch for changes on the results and rebuild if they occur
          $scope.$watch('results', function (results) {
            // console.log('Search Results changed, updating facets');
            reset(results);
          });
          /**
                 * Clear selected options
                 */
          $scope.clearSelectedOptions = function () {
            $scope.facet.selectedOptions = [];
          };
          /**
                 * Check whether or not any options have been selected
                 *
                 * @return {boolean}
                 */
          $scope.hasSelected = function () {
            return $scope.facet.selectedOptions.length > 0;
          };
          /**
                 * Is an option selected.
                 *
                 * @param option facet option
                 * @returns {boolean}
                 */
          $scope.isSelected = function (option) {
            return getSelectedOptionIndex(option) !== -1;
          };
          /**
                 * Toggle the selected state of a given option
                 *
                 * @param option facet option
                 */
          $scope.toggleOption = function (option) {
            var index = getSelectedOptionIndex(option);
            if (index === -1) {
              $scope.facet.selectedOptions.push(option.value);
            } else {
              $scope.facet.selectedOptions.splice(index, 1);
            }
          };
          /**
                 * Helper method for returning the index of the option within the selected options array
                 *
                 * @param option to locate
                 * @return int index of option in selected options array
                 */
          function getSelectedOptionIndex(option) {
            var index = -1;
            if (angular.isArray(option.value)) {
              // This is an array, do an equality search since indexOf matches on identicality (== vs ===)
              for (var i = 0; i < $scope.facet.selectedOptions.length; i++) {
                var selectedValue = $scope.facet.selectedOptions[i], matched = true;
                for (var j = 0; j < option.value.length; j++) {
                  if (selectedValue.indexOf(option.value[j]) === -1) {
                    matched = false;
                    break;
                  }
                }
                if (matched) {
                  index = i;
                  break;
                }
              }
            } else {
              index = $scope.facet.selectedOptions.indexOf(option.value);
            }
            return index;
          }
        }
      ]
    };
  }
]).service('SearchFacetService', function SearchFacetService() {
  this.initFacets = function (facets) {
    // Iterate over the facet definitions and make sure they have an optionsProvider & filter.
    angular.forEach(facets, function (facet) {
      this.initFacet(facet);
    }, this);
    return facets;
  };
  this.initFacet = function (facet) {
    // Ensure an optionsProvider exists.
    if (!angular.isFunction(facet.optionsProvider)) {
      // Default options provider that uses optionsKey value to build the options array.
      facet.optionsProvider = function (results) {
        var options = [];
        // The default options provider requires the optionsKey. Don't proceed if it's not set.
        if (angular.isDefined(facet.optionsKey) && facet.optionsKey !== null) {
          // Loop through the results and configure an option object for each unique value.
          angular.forEach(results, function (item) {
            if (angular.isDefined(item[facet.optionsKey])) {
              // Make sure the item has the optionsKey field
              // To facilitate processing of arrays, push everything to an array.
              var values = item[facet.optionsKey];
              if (!angular.isArray(values)) {
                values = [values];
              }
              angular.forEach(values, function (value) {
                // Try to find an existing option with a matching value.
                var option = null;
                angular.forEach(options, function (o) {
                  if (option === null && o.value === value) {
                    option = o;
                  }
                });
                if (option === null) {
                  // The existing option doesn't exist. Create it anew.
                  option = {
                    label: value,
                    value: value,
                    count: 0
                  };
                  options.push(option);
                }
                // Increment the count of results identified with this option.
                option.count++;
              });
            }
          });
        } else {
          console.log('Facet "' + facet.id + '" is missing the required optionsKey value');
        }
        // Sort the options by their label
        options.sort(function (a, b) {
          return a.label === b.label ? 0 : a.label < b.label ? -1 : 1;
        });
        return options;
      };
    }
    // Ensure a filter exists.
    if (!angular.isFunction(facet.filter)) {
      facet.filter = function (item, selectedValues) {
        // Recursive function used to traverse an array to see if the value exists at any
        // level of the array. This allows a single option to be matched to multiple values.
        function valueExists(value, array) {
          var exists = false;
          if (angular.isArray(value)) {
            angular.forEach(value, function (v) {
              if (!exists) {
                exists = valueExists(v, array);
              }
            });
          } else {
            if (angular.isArray(array)) {
              angular.forEach(array, function (item) {
                if (!exists) {
                  exists = valueExists(value, item);
                }
              });
            } else {
              exists = value === array;
            }
          }
          return exists;
        }
        // The default filter requires the optionsKey field to be set.
        if (angular.isDefined(facet.optionsKey) && facet.optionsKey !== null) {
          if (angular.isDefined(item[facet.optionsKey])) {
            // Make sure the item has the optionsKey field
            var value = item[facet.optionsKey];
            return valueExists(value, selectedValues);
          }
        }
        return false;
      };
    }
    return facet;
  };
});
;
'use strict';
angular.module('regCartApp').directive('searchForm', [function () {
    return {
      restrict: 'ECA',
      templateUrl: 'partials/searchForm.html',
      controller: [
        '$scope',
        '$state',
        function ($scope, $state) {
          $scope.courseSearchCriteria = '';
          function getCriteriaFromState() {
            var criteria = null;
            if (angular.isDefined($state.params) && angular.isDefined($state.params.searchCriteria)) {
              criteria = $state.params.searchCriteria;
            }
            return criteria;
          }
          function syncSearchCriteriaWithState() {
            var stateCriteria = getCriteriaFromState();
            if (stateCriteria !== null) {
              // Only update the criteria if it exists in the $state and it doesn't match the current criteria
              if (stateCriteria !== $scope.courseSearchCriteria) {
                $scope.courseSearchCriteria = $state.params.searchCriteria;
              }
            } else {
              // Criteria doesn't exist in $state, set local criteria to an empty string.
              $scope.courseSearchCriteria = '';
            }
          }
          syncSearchCriteriaWithState();
          // Listen for any state changes from ui-router.
          // This will reinsert the search criteria into the form on a refresh.
          $scope.$on('$stateChangeSuccess', function () {
            syncSearchCriteriaWithState();
          });
          $scope.submit = function () {
            // Clear out the state parameters that we don't want to persist
            var params = angular.copy($state.params);
            angular.forEach(params, function (value, key) {
              switch (key) {
              case 'term':
              case 'searchCriteria':
                break;
              default:
                params[key] = null;
              }
            });
            params.searchCriteria = $scope.courseSearchCriteria;
            // Push the criteria into the $state
            $state.go('root.search.results', params);
          };
        }
      ]
    };
  }]);
'use strict';
angular.module('regCartApp').directive('searchList', [
  '$filter',
  '$animate',
  '$timeout',
  function ($filter, $animate, $timeout) {
    return {
      restrict: 'E',
      require: '^searchResults',
      templateUrl: 'partials/searchList.html',
      scope: {
        searchResults: '=?',
        searchColumns: '=',
        searchData: '=?',
        displayMax: '@displayLimit',
        state: '=?',
        searchCriteria: '@',
        termName: '@',
        detailsId: '@',
        defaultField: '@',
        preprocessor: '@',
        onClick: '@',
        prefix: '@',
        showMobile: '@',
        last: '=?',
        noResults: '@'
      },
      link: function (scope) {
        $animate.enabled(false, angular.element(document.querySelector('.kscr-Search-row')));
        if (angular.isUndefined(scope.searchResults) && scope.preprocessor) {
          scope.searchResults = $filter(scope.preprocessor)(scope.searchData);
        }
        // Displays the table in batches for performance
        var stagger = 20;
        scope.limit = 0;
        scope.mobileLimit = 0;
        // limit is for large format, and looks at the set display limit
        scope.$watch('limit', function () {
          if (scope.limit > 0 && scope.limit < scope.displayLimit) {
            $timeout(function () {
              scope.limit += stagger;
            }, 250);
          } else {
            if (scope.limit > scope.displayLimit) {
              scope.limit = scope.displayLimit;
            }
          }
        });
        // mobile limit is for small format and looks at the entire array of search results
        scope.$watch('mobileLimit', function () {
          if (scope.mobileLimit > 0 && scope.mobileLimit < scope.searchResults.length) {
            $timeout(function () {
              scope.mobileLimit += stagger;
            }, 250);
          } else {
            if (scope.mobileLimit > scope.searchResults.length) {
              scope.mobileLimit = scope.searchResults.length;
            }
          }
        });
        // the choices for limiting display of search results
        scope.displayLimits = [
          20,
          50,
          100
        ];
        if (angular.isDefined(scope.state)) {
          // set the default values from the state object
          scope.displayLimit = parseInt(scope.state.displayLimit);
          scope.page = parseInt(scope.state.page);
          scope.predicate = scope.state.predicate;
          scope.reverse = scope.state.reverse || undefined;
        }
        // make sure the page & display limit is set to a valid value
        scope.page = scope.page || 1;
        if (scope.displayLimits.indexOf(scope.displayLimit) === -1) {
          scope.displayLimit = scope.displayLimits[0];
        }
        if (angular.isDefined(scope.displayMax) && scope.displayMax) {
          scope.displayLimit = scope.displayMax;
        }
        // holds the customizable id for linking to search details
        scope.detailsId = {};
        // holds the reverse for each column (true = reverse sort)
        scope.reverseMap = {};
        // returns the last page
        scope.lastPage = function () {
          return Math.ceil(scope.searchResults.length / scope.displayLimit);
        };
        // returns a the full range of pages
        scope.pageRange = function () {
          var pageRange = [], lastPage = scope.lastPage();
          for (var i = 1; i <= lastPage; i++) {
            pageRange.push(i);
          }
          return pageRange;
        };
        /*
                 * if the display limit changes:
                 * -- reset the page to 1
                 * -- increment the limit
                 */
        scope.$watch('displayLimit', function (limit) {
          scope.page = 1;
          scope.limit += stagger;
        });
        /*
                 * if the search results changes:
                 * -- if current page is outside of the page range, reset the page to 1
                 * -- increment the mobile limit
                 */
        scope.$watch('searchResults', function () {
          if (scope.lastPage() === 0) {
            // First init
            // Take them to the state's page if it is defined, otherwise page 1
            scope.page = scope.state.page || 1;
          } else if (scope.page > scope.lastPage()) {
            scope.page = 1;
          }
          scope.mobileLimit += stagger;
        });
        // returns the index of the first record being displayed
        scope.displayRangeMin = function () {
          return (scope.page - 1) * scope.displayLimit + 1;
        };
        // returns the index of the last record being displayed
        scope.displayRangeMax = function () {
          var min = scope.displayRangeMin(), max = min + scope.displayLimit - 1;
          if (max > scope.searchResults.length) {
            max = scope.searchResults.length;
          }
          return max;
        };
        // apply the given filter name to the value (if it's an array)
        scope.applyFilter = function (row, field, filterName) {
          var value = null;
          if (field && angular.isUndefined(row[field]) && field.indexOf('.') !== -1) {
            var workingValue = row, parts = field.split('.'), part;
            for (var i = 0; part = parts[i], i < parts.length; i++) {
              if (angular.isDefined(workingValue[part])) {
                workingValue = workingValue[part];
              } else {
                // Part doesn't exist. Exit out.
                workingValue = null;
                break;
              }
            }
            if (workingValue !== null) {
              value = workingValue;
            }
          } else {
            value = row[field];
          }
          if (angular.isArray(value) && filterName) {
            return $filter(filterName)(value);
          } else {
            return value;
          }
        };
        // returns the customizable id for the given search result
        scope.getId = function (searchResult) {
          return searchResult[scope.detailsId];
        };
        // column sort method called when the column header is clicked
        scope.sortColumn = function (column) {
          if (column.sortable) {
            scope.predicate = column.field;
            scope.reverse = scope.sortResults(column.field);
          }
        };
        // switches the reverse order of the specified column
        scope.sortResults = function (column) {
          var reverse;
          if (angular.isUndefined(scope.reverseMap[column])) {
            // initialize
            scope.reverseMap[column] = 0;
          }
          switch (scope.reverseMap[column]) {
          case 0:
            // first click
            scope.reverseMap[column] = 1;
            reverse = false;
            // descending order
            break;
          case 1:
            // second click
            scope.reverseMap[column] = 2;
            reverse = true;
            // ascending order
            break;
          case 2:
            scope.reverseMap[column] = 0;
            scope.predicate = undefined;
            //undoes the search
            break;
          }
          return reverse;
        };
        // helper method to emit an event with the search result
        scope.emitEvent = function (eventToEmit, searchResult) {
          if (eventToEmit) {
            scope.$emit(eventToEmit, searchResult);
          }
        };
        //checks to see if the given column has data
        scope.hasData = function (field) {
          var hasData = false;
          for (var i = 0; i < scope.searchResults.length; i++) {
            var data = scope.applyFilter(scope.searchResults[i], field);
            if (data) {
              hasData = true;
              break;
            }
          }
          return hasData;
        };
        /*
                 Watch and emit an event anytime the list state values change (page, displayLimit, & sorting)
                 */
        function emitResultsStateChangedEvent(value, oldValue) {
          if (value !== oldValue) {
            var resultsState = {
                displayLimit: scope.displayLimit,
                page: scope.page,
                predicate: scope.predicate || null,
                reverse: scope.predicate ? scope.reverse : null
              };
            scope.$emit('resultsStateChanged', resultsState);
          }
        }
        scope.$watch('displayLimit', emitResultsStateChangedEvent);
        scope.$watch('page', emitResultsStateChangedEvent);
        scope.$watch('predicate', emitResultsStateChangedEvent);
        scope.$watch('reverse', emitResultsStateChangedEvent);
        /*
                If a clearSelected event is received from the parent, clear all the selected search
                results and unhide.
                 */
        scope.$on('clearSelected', function () {
          angular.forEach(scope.searchResults, function (searchResult) {
            searchResult.selected = false;
            searchResult.hidden = false;
          });
        });
        // hides the row with the given id
        scope.$on('hideRow', function (event, id) {
          angular.forEach(scope.searchResults, function (searchResult) {
            if (searchResult[scope.defaultField] === id) {
              searchResult.hidden = true;
            }
          });
        });
        // shows the row with the given id
        scope.$on('showRow', function (event, id) {
          angular.forEach(scope.searchResults, function (searchResult) {
            if (searchResult[scope.defaultField] === id) {
              searchResult.hidden = false;
            }
          });
        });
        // shows all rows
        scope.$on('showAllRows', function () {
          angular.forEach(scope.searchResults, function (searchResult) {
            if (searchResult.hidden) {
              searchResult.hidden = false;
            }
          });
        });
      }
    };
  }
]).directive('searchResults', function () {
  return {
    restrict: 'E',
    controller: 'SearchResultsCtrl'
  };
}).directive('searchColumn', function () {
  return {
    restrict: 'E',
    require: '^searchResults',
    link: function (scope, element, attrs) {
      if (attrs.name || attrs.field || attrs.filter) {
        if (!angular.isArray(scope.searchColumns)) {
          scope.searchColumns = [];
        }
        var searchColumn = {
            name: attrs.name,
            field: attrs.field,
            filter: attrs.filter,
            order: attrs.order,
            url: attrs.url,
            sortable: attrs.sortField,
            optional: attrs.optional,
            checkbox: attrs.checkbox
          };
        searchColumn.sortable = searchColumn.sortable !== 'false';
        // defaults to true
        searchColumn.optional = searchColumn.optional === 'true';
        // defaults to false
        scope.searchColumns.push(searchColumn);
        scope.sortSearchColumns();
      }
    }
  };
}).controller('SearchResultsCtrl', [
  '$scope',
  '$filter',
  function SearchResultsCtrl($scope, $filter) {
    $scope.sortSearchColumns = function () {
      $scope.searchColumns = $filter('orderBy')($scope.searchColumns, 'order');
    };
  }
]);
;
'use strict';
angular.module('regCartApp').directive('sticky', [
  '$timeout',
  '$window',
  '$document',
  function ($timeout, $window, $document) {
    return {
      restrict: 'CA',
      link: function (scope, elem, attrs) {
        $timeout(function () {
          var w = angular.element($window), d = angular.element($document), stickTo = attrs.sticky === 'bottom' ? 'bottom' : 'top', elementTop = elem.offset().top, stickyLine,
            // Scroll position to stick at
            usePlaceholder = !angular.isDefined(attrs.usePlaceholder) || attrs.usePlaceholder ? true : false, placeholder, isStuck = false;
          // Add the base classes to the element
          elem.addClass('util-sticky');
          elem.addClass('util-sticky-' + stickTo);
          /**
                     * Check and sticky/unstick the element depending on the element & scroll position.
                     */
          function check() {
            if (!isStuck) {
              elementTop = elem.offset().top;
            }
            var elemLargerThanBrowser = d.height() <= elem.height(), scrollTop = ($window.pageYOffset || d.scrollTop()) - (d.clientTop || 0),
              // Calculate the top of the screen,
              shouldStick = elemLargerThanBrowser;
            if (!shouldStick) {
              if (stickTo === 'bottom') {
                var elementBottom = elementTop + elem.outerHeight(), visibleBottom = $window.innerHeight + scrollTop;
                if (elementTop) {
                  if (!stickyLine || !elementTop) {
                    stickyLine = elementBottom;
                  }
                  shouldStick = visibleBottom < stickyLine;
                  if (shouldStick && !isStuck) {
                    stickyLine = elementBottom;
                  }
                }
              } else {
                stickyLine = elementTop;
                shouldStick = scrollTop >= stickyLine;  // The element should be stuck if its top is below the 'stickyLine'.
              }
            }
            if (!isStuck && shouldStick) {
              // Element is not stuck but should be.
              // console.log('Stick it!', scrollTop, stickyLine);
              stick();
            } else if (isStuck && !shouldStick) {
              // Element is stuck but shouldn't be.
              // console.log('Unstick it!', scrollTop, stickyLine);
              unstick();
            } else if (isStuck && shouldStick) {
            } else {
            }
            if (isStuck && elemLargerThanBrowser) {
              // If the element is larger than the browser, we need to check back to see if it has resized.
              // This is the best way I could think to handle a resize of the sticky element.
              $timeout(check, 500);
            }
          }
          /**
                     * Make the element sticky on the page.
                     */
          function stick() {
            if (usePlaceholder) {
              // Insert a placeholder element before the sticky element.
              // The placeholder prevents the page height from shifting when we pop it out of the DOM flow.
              if (!placeholder) {
                placeholder = angular.element('<div class="util-sticky-placeholder"/>');
              }
              placeholder.css('height', elem.outerHeight() + 'px');
              placeholder.insertBefore(elem);
            }
            // Add the stuck class
            elem.addClass('util-sticky--stuck');
            isStuck = true;
          }
          /**
                     * Return the element to its pre-sticky state.
                     */
          function unstick() {
            // The placeholder is no longer needed
            if (placeholder) {
              placeholder.remove();
              placeholder = null;
            }
            // Remove the stuck class
            elem.removeClass('util-sticky--stuck');
            isStuck = false;
          }
          // Recheck the stickiness when the user scrolls or resizes the window
          w.on('scroll', check);
          w.on('resize', function () {
            $timeout(check);
          });
          check();
          // Cleanup when the element is being destroyed
          elem.bind('$destroy', function () {
            if (placeholder) {
              placeholder.remove();
              placeholder = null;
            }
          });
        });
      }
    };
  }
]);