
var busApp = angular.module('BusChecker', ['ngAnimate', 'ui.bootstrap', 'ngResource', 'ngRoute',
    'ngMaterial'])
    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/login', {
            templateUrl : 'login.html',
            controller : 'LoginController'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'RegisterController'
        }).when('/manage_user/:username', {
            templateUrl : 'manage_user.html',
            controller : 'ManageUserController'
        }).otherwise(
            { redirectTo: '/login'}
        );

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .service('loggedUser', function() {
        var username = '';
        var roles = '';

        return {
            getUsername: function () {
                return username;
            },
            setUsername: function(value) {
                username = value;
            },
            getRoles: function() {
                return roles;
            },
            setRoles: function(value) {
                roles = value;
            },
            hasRole: function() {
                if (roles === 'ROLE_USER') return true;
                else return false;
            }
        };
    });