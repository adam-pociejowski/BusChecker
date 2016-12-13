'use strict';

var appController = busApp.controller('AppController', function($rootScope, $scope, $http, $location) {
    $scope.alerts = [];
    $scope.isAuthenticated = false;
    $scope.loggedUser = {};

    $scope.addErrorAlert = function(message) {
        $scope.alerts.push({
            type: error,
            msg: message
        });
    };

    $scope.authenticate = function (callback) {
        $http.get('user').
        success(function (response) {
            $scope.isAuthenticated = true;
            $scope.loggedUser = response.name;
            callback();
        }).
        error(function () {
            $scope.isAuthenticated = false;
            $scope.loggedUser = null;
            $location.path('login');
        });
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});