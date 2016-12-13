
busApp.controller('LoginController', function($scope, $http, $location) {
    $scope.credentials = {};

    $scope.create = function() {
        $location.path('register');
    };

    $scope.login = function() {
        $http.post('login', $.param($scope.credentials), {
            headers : {
                "content-type" : "application/x-www-form-urlencoded"
            }
        }).success(function() {
            $location.path("/manage_user");
        }).error(function() {
            $location.path("/login");
            $scope.error = true;
        })
    };
});