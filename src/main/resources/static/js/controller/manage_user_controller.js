
busApp.controller('ManageUserController', function($scope, $http, $location, $routeParams) {
    $scope.loggedUser =  $routeParams.username;

    $scope.saveUserData = function() {
        console.log($scope.data);
        $http.post('saveuser', $scope.data);
    };

    $scope.saveSitter = function () {
        $scope.selectedBus.sitters.push($scope.newElement);
        $scope.clear();
    };

    var changeDateFormat = function(bus) {
        bus.technicalReviewDate = new Date(bus.technicalReviewDate);
        bus.liftReviewDate = new Date(bus.liftReviewDate);
        bus.extinguisherReviewDate = new Date(bus.extinguisherReviewDate);
        bus.insuranceDate = new Date(bus.insuranceDate);
        bus.tachographReviewDate = new Date(bus.tachographReviewDate);
    };

    $scope.selectBus = function (bus) {
        $scope.selectedBus = bus;
        changeDateFormat($scope.selectedBus);
    };

    var addChosenBusesToDriver = function() {
        for (var i = 0; i < $scope.buses.length; i++) {
            var bus = $scope.buses[i];
            if (bus.chosen)
                $scope.chosenDriver.buses.push(bus);
        }
    };

    $scope.saveChosenDriver = function () {
        addChosenBusesToDriver();
    };

    $scope.addNewBus = function() {
        $scope.buses.push($scope.newElement);
        $scope.clear();
    };

    $scope.setAddingNewBus = function (value) {
        $scope.addingNewBus = value;
    };

    $scope.getAllBuses = function() {
        $http.get('getotherbuses/'+$scope.chosenDriver.id).
        then(function(response) {
            $scope.buses = response.data;
        });
    };

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
        // $http.post('savedrivers/'+$scope.loggedUser, $scope.otherDrivers).
        // then(function() {
        //     $scope.init();
        // });
        for (var i = 0; i < $scope.otherDrivers.length; i++) {
            var driver = $scope.otherDrivers[i];
            if (driver.chosen)
                $scope.data.drivers.push(driver);
        }
    };

    $scope.addNewDriver = function () {
        $http.post('savedriver', $scope.newElement).
        then(function() {
            $scope.otherDrivers.push($scope.newElement);
            $scope.clear();
        });
    };

    $scope.setAddingElement = function (value) {
        $scope.addingElement = value;
    };

    $scope.getOtherDrivers = function() {
        $scope.clear();
        $http.get('getotherdrivers/'+$scope.loggedUser).
        then(function(response) {
            $scope.otherDrivers = response.data;
        });
    };

    $scope.clear = function () {
        $scope.newElement = {};
        $scope.addingElement = false;
    };

    $scope.init = function () {
        $scope.clear();
        $http.get('getuserdata/'+$scope.loggedUser).
        then(function(response) {
            $scope.data = response.data;
            if ($scope.data.drivers.length > 0) {
                $scope.chosenDriver = $scope.data.drivers[0];
                console.log($scope.chosenDriver);
                if ($scope.chosenDriver.buses.length > 0) {
                    $scope.selectedBus = $scope.chosenDriver.buses[0];
                    changeDateFormat($scope.selectedBus);
                }
            }
        });
    };
});