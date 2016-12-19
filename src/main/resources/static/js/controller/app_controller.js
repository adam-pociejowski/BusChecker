'use strict';

var appController = busApp.controller('AppController', function($rootScope, $scope, $http, $location, AuthService) {
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
        var promise = AuthService.authenticate();
        promise.success(function (response) {
            $scope.isAuthenticated = true;
            $scope.loggedUser = response.name;
            callback();
        }).error(function () {
            $scope.isAuthenticated = false;
            $scope.loggedUser = null;
            $location.path('login');
        });
    };

    $scope.logout = function () {
        var promise = AuthService.logout();
        promise.success(function () {
            $location.path('login');
            $scope.addAlert();
        }).error(function () {

        });
    };

    $scope.addAlert = function () {
        var alert = {
            msg: "alter",
            type: "SUCCESS"
        };
        console.log('added');
        $scope.alerts.push(alert);
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});