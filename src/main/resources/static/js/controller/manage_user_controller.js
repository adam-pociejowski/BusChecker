

busApp.controller('ManageUserController', function($scope, $http, $location, $routeParams) {
    $scope.loggedUser =  $routeParams.username;


    $scope.deleteDriverFromUser = function () {
        $http.post('deletedriver/'+$scope.loggedUser, $scope.chosenDriver).
        then(function() {
            $scope.init();
        });
    };

    $scope.choseDriver = function (driver) {
        $scope.chosenDriver = driver;
    };

    /**
     * Adding drivers
     */
    $scope.saveDrivers = function () {
        $http.post('savedrivers/'+$scope.loggedUser, $scope.otherDrivers).
        then(function() {
            $scope.init();
        });
    };

    $scope.addNewDriver = function () {
        $http.post('adddriver/', $scope.newDriver).
        then(function() {
            $scope.otherDrivers.push($scope.newDriver);
            $scope.clearNewDriver();
        });
    };

    $scope.setAddingDriver = function (value) {
        $scope.addingNewDriver = value;
    };

    $scope.getOtherDrivers = function() {
        $scope.clearNewDriver();
        $http.get('getotherdrivers/'+$scope.loggedUser).
        then(function(response) {
            $scope.otherDrivers = response.data;
            console.log(response.data);
        });
    };

    $scope.clearNewDriver = function () {
        $scope.newDriver = {};
        $scope.addingNewDriver = false;
    };

    $scope.init = function () {
        $scope.clearNewDriver();
        $http.get('getuserdata/'+$scope.loggedUser).
        then(function(response) {
            console.log(response.data);
            $scope.data = response.data;
            if ($scope.data.drivers.length > 0)
                $scope.chosenDriver = $scope.data.drivers[0];
        });
    };
});