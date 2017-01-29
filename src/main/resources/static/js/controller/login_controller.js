
busApp.controller('LoginController', function($scope, $http, $location, AuthService) {
    $scope.credentials = {};

    $scope.create = function() {
        $location.path('register');
    };

    $scope.login = function() {
        var promise = AuthService.login($scope.credentials);
        promise.success(function () {
            $location.path('manage_user');
            $scope.addAlert();
        }).error(function() {
            $scope.login.error = true;
        });
    };
});