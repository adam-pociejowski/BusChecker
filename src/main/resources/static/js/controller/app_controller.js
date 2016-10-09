
busApp.controller('AppController', function($scope, $http, $location, loggedUser) {

    // $scope.$on('$routeChangeSuccess', function() {
    //     $http.get('/loggeduser').then(function(response) {
    //         console.log('changed',response.data);
    //         if (response.data.username) {
    //             console.log('changed logged');
    //             loggedUser.setUsername(response.data.username);
    //             loggedUser.setRoles(response.data.role);
    //         }
    //         else {
    //             console.log('changed not logged');
    //             $location.path('/login');
    //         }
    //     });
    // });


    $scope.logout = function() {
        $http.get('/logout')
            .then(function() {
                loggedUser.setUsername(null);
                loggedUser.setRoles(null);
                $location.path('/login');
        });
    };
});