
busApp.controller('ManageUserController', function($scope, $http, $location, $routeParams) {
    $scope.loggedUser =  $routeParams.username;

    var changeDateFormat = function(bus) {
        bus.technicalReviewDate = new Date(bus.technicalReviewDate);
        bus.liftReviewDate = new Date(bus.liftReviewDate);
        bus.extinguisherReviewDate = new Date(bus.extinguisherReviewDate);
        bus.insuranceDate = new Date(bus.insuranceDate);
        bus.tachographReviewDate = new Date(bus.tachographReviewDate);
    };

    var addChosenBusesToDriver = function() {
        for (var i = 0; i < $scope.buses.length; i++) {
            var bus = $scope.buses[i];
            if (bus.chosen)
                $scope.chosenDriver.buses.push(bus);
        }
    };

    $scope.selectBus = function (bus) {
        $scope.selectedBus = bus;
        changeDateFormat($scope.selectedBus);
    };

    $scope.saveChosenDriver = function () {
        addChosenBusesToDriver();
    };

    $scope.addNewBus = function() {
        $scope.newElement.sitters = [];
        $scope.buses.push($scope.newElement);
        $scope.clear();
    };

    $scope.choseDriver = function (driver) {
        $scope.chosenDriver = driver;
    };

    /**
     * Deleting
     */
    $scope.deleteDriverFromUser = function () {
        var idx = $scope.data.drivers.indexOf($scope.chosenDriver);
        $scope.data.drivers.splice(idx, 1);
        postDrivers();
    };

    $scope.deleteBusFromUser = function (bus) {
        var idx = $scope.chosenDriver.buses.indexOf(bus);
        $scope.chosenDriver.buses.splice(idx, 1);
        postBuses();
    };

    $scope.deleteSitterFromBus = function (sitter) {
        var idx = $scope.selectedBus.sitters.indexOf(sitter);
        $scope.selectedBus.sitters.splice(idx, 1);
        postSitters();
    };

    /**
     * Adding
     */
    $scope.saveDrivers = function () {
        for (var i = 0; i < $scope.otherDrivers.length; i++) {
            var driver = $scope.otherDrivers[i];
            if (driver.chosen)
                $scope.data.drivers.push(driver);
        }
        postDrivers();
    };

    $scope.saveNewBuses = function () {
        addChosenBusesToDriver();
        $scope.saveBuses();
    };

    $scope.saveBuses = function () {
        postBuses();
    };

    $scope.saveSitters = function () {
        $scope.selectedBus.sitters.push($scope.newElement);
        $scope.clear();
        postSitters();
    };

    $scope.addNewDriver = function () {
        $scope.newElement.buses = [];
        $scope.otherDrivers.push($scope.newElement);
        $scope.clear();
    };

    /**
     * Getting information
     */
    $scope.getOtherDrivers = function() {
        $scope.clear();
        $http.get('getotherdrivers/'+$scope.loggedUser).
        then(function(response) {
            $scope.otherDrivers = response.data;
            console.log('dd');
        });
    };

    $scope.getAllBuses = function() {
        $http.get('getotherbuses/'+$scope.chosenDriver.id).
        then(function(response) {
            $scope.buses = response.data;
        });
    };

    $scope.setAddingElement = function (value) {
        $scope.addingElement = value;
    };

    $scope.clear = function () {
        $scope.newElement = {};
        $scope.addingElement = false;
    };

    var chooseSelectedFields = function () {
        if ($scope.data.drivers.length > 0) {
            $scope.chosenDriver = $scope.data.drivers[0];
            console.log($scope.chosenDriver);
            if ($scope.chosenDriver.buses.length > 0) {
                $scope.selectedBus = $scope.chosenDriver.buses[0];
                changeDateFormat($scope.selectedBus);
            }
        }
    };

    $scope.init = function () {
        $scope.clear();
        $http.get('getuserdata/'+$scope.loggedUser).
        then(function(response) {
            $scope.data = response.data;
            chooseSelectedFields();
        });
    };

    var postSitters = function () {
        $http.post('savesitters', $scope.selectedBus).
        then(function() {
            $scope.init();
        });
    };

    var postBuses = function () {
        $http.post('savebuses', $scope.chosenDriver).
        then(function() {
            $scope.init();
        });
    };

    var postDrivers = function () {
        $http.post('savedrivers', $scope.data).
        then(function() {
            $scope.init();
        });
    };
});