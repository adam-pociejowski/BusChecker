
busApp.controller('ManageUserController', function($scope, $location, $rootScope, ManageUserService) {
    $scope.selectedDriver = {};

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

    $scope.addNewBus = function() {
        $scope.newElement.sitters = [];
        $scope.buses.push($scope.newElement);
        $scope.clear();
    };

    $scope.choseDriver = function (driver) {
        $scope.chosenDriver = driver;
        $scope.init();
    };

    /**
     * Deleting
     */
    $scope.deleteDriverFromUser = function () {
        var idx = $scope.data.drivers.indexOf($scope.chosenDriver);
        $scope.data.drivers.splice(idx, 1);
        postDrivers();
    };

    $scope.deleteBusFromUser = function () {
        var idx = $scope.chosenDriver.buses.indexOf($scope.selectedBus);
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
        if ($scope.newElement.firstname !== undefined) {
            $scope.selectedBus.sitters.push($scope.newElement);
        }
        $scope.clear();
        postSitters();
    };

    $scope.addNewDriver = function () {
        $scope.newElement.buses = [];
        $scope.otherDrivers.push($scope.newElement);
        $scope.clear();
    };

    $scope.setAddingElement = function (value) {
        $scope.addingElement = value;
    };

    $scope.selectedBus = function (bus) {
        $scope.selectedBus = bus;
    };

    $scope.clear = function () {
        $scope.newElement = {};
        $scope.addingElement = false;
    };

    var chooseSelectedFields = function () {
        if ($scope.data.drivers.length > 0) {
            if (!isChosenDriverInList())
                $scope.chosenDriver = $scope.data.drivers[0];

            if ($scope.chosenDriver.buses.length > 0) {
                $scope.selectedBus = $scope.chosenDriver.buses[0];
                changeDateFormat($scope.selectedBus);
            }
        }
    };

    $scope.init = function () {
        $scope.authenticate(function () {
            $scope.clear();
            var promise = ManageUserService.getUserData($scope.loggedUser);
            promise.success(function (response) {
                $scope.data = response;
                chooseSelectedFields();
            }).error(function () {

            });
        });
    };

    $scope.getOtherDrivers = function() {
        $scope.clear();
        var promise = ManageUserService.getOtherDrivers($scope.loggedUser);
        promise.success(function (response) {
            $scope.otherDrivers = response;
        }).error(function () {

        });
    };

    $scope.getAllBuses = function() {
        var promise = ManageUserService.getOtherBuses($scope.chosenDriver.id);
        promise.success(function (response) {
            $scope.buses = response;
        }).error(function () {

        });
    };

    var postSitters = function () {
        var promise = ManageUserService.saveSitters($scope.selectedBus);
        promise.success(function () {
            $scope.init();
        }).error(function () {

        });
    };

    var postBuses = function () {
        var promise = ManageUserService.saveBuses($scope.chosenDriver);
        promise.success(function () {
            $scope.init();
        }).error(function () {

        });
    };

    var postDrivers = function () {
        var promise = ManageUserService.saveDrivers($scope.data);
        promise.success(function () {
            $scope.init();
        }).error(function () {

        });
    };

    var isChosenDriverInList = function() {
        if ($scope.chosenDriver === undefined)
            return false;

        for (var i = 0; i < $scope.data.drivers.length; i++) {
            var driver = $scope.data.drivers[i];
            if ($scope.chosenDriver.id === driver.id)
                return true;
        }
        return false;
    };
});