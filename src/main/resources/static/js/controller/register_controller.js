
busApp.controller('RegisterController', function($scope, $http, $location) {
    $scope.user = {};

    $scope.register = function() {
        $http.post('registerUser', $scope.user).
        success(function() {
            $location.path("login");
        }).error(function() {

        });
    };

    $scope.backToLoginPage = function () {
        $location.path("login");
    };
});