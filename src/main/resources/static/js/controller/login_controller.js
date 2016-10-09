
busApp.controller('LoginController', function($scope, $http, $location, loggedUser) {

    $scope.credentials = {};
    $scope.create = function() {
        console.log('Create');
        $location.path('/register');
    };

    $scope.login = function() {
        $http.post('/authenticate', $scope.credentials).
        then(function(response) {
            if (response.data.username) {
                console.log("Logged username: "+response.data.username,'\nRoles: '+response.data.role);
                loggedUser.setUsername(response.data.username);
                loggedUser.setRoles(response.data.role);
                $location.path('/checker');
            }
            else {
                $location.path('/login');
            }
        });
    };
});