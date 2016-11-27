'use strict';

var appController = busApp.controller('AppController', function($rootScope, $scope, $http, $location, loggedUser) {
    $scope.alerts = [];
    $scope.loggedUser = {};

    $scope.getLoggedUser = function () {
        $http.get('loggedUser')
            .then(function(response) {
                console.log(response.data);
            });
    };

    $scope.isLogged = function() {
        return loggedUser.hasRole();
    };

    $scope.logout = function() {
        $http.get('logout')
            .then(function() {
                loggedUser.setUsername(null);
                loggedUser.setRoles(null);
                $location.path('login');
        });
    };

    $scope.addErrorAlert = function(message) {
        console.log('added');
        $scope.alerts.push({
            type: error,
            msg: message
        });
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});