
var busApp = angular.module('BusChecker', ['ngAnimate', 'ui.bootstrap', 'ngResource', 'ngRoute',
    'ngMaterial'])
    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/login', {
            templateUrl : 'login.html',
            controller : 'LoginController'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'RegisterController'
        }).when('/manage_user', {
            templateUrl : 'manage_user.html',
            controller : 'ManageUserController'
        }).when('/review_list', {
            templateUrl : 'review_list.html',
            controller : 'ReviewListController'
        }).otherwise(
            { redirectTo: '/login'}
        );

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    });