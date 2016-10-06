
busApp.controller('LoginController', function($scope, $http, $location, loggedUser) {
    console.log('LoginController ');

    $scope.credentials = {};
    $scope.login = function() {
        console.log($scope.credentials);
        $http.post('/authenticate', $scope.credentials).
        then(function(response) {
            if (response.data.username) {
                console.log("Logged username: "+response.data.username);
                loggedUser.setUsername(response.data.username);
                $location.path('/checker');
            }
            else {
                console.log("Login error");
            }
        });
    };
});