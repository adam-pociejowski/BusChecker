
var busApp = angular.module('BusChecker', ['ngResource', 'ngRoute'])
    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/login', {
            templateUrl : 'login.html',
            controller : 'LoginController'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'RegisterController'
        }).when('/checker', {
            templateUrl : 'checker.html',
            controller : 'CheckerController'
        }).otherwise(
            { redirectTo: '/login'}
        );

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .service('loggedUser', function() {
        var username = '';

        return {
            getUsername: function () {
                return username;
            },
            setUsername: function(value) {
                username = value;
            }
        };
    });