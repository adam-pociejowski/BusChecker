
busApp.controller('RegisterController', function($scope, $http, $location) {
    $scope.sitters = [];
    $scope.acc = {};

    $.fn.datepicker.defaults.format = "dd/mm/yyyy";
    $('.datepicker').datepicker({
        startDate: '-3d'
    });

    $scope.addSitter = function() {
        var sitter = {
            firstname : '',
            lastname : ''
        };
        $scope.sitters.push(sitter);
    };

    $scope.register = function() {
        var data = {
            username : getValue($scope.acc.username),
            password : getValue($scope.acc.password),
            busName : getValue($scope.acc.busName),
            sideNumber : getValue($scope.acc.sideNumber),
            rejestrNumber : getValue($scope.acc.rejestrNumber),
            firstname : getValue($scope.acc.firstname),
            lastname : getValue($scope.acc.lastname),
            phoneNumber : getValue($scope.acc.phoneNumber)+"",
            numberOfSeats : parseInt($scope.acc.numberOfSeats),
            technicalReviewDate : getValue($scope.acc.technicalReviewDate),
            liftReviewDate : getValue($scope.acc.liftReviewDate),
            extinguisherReviewDate : getValue($scope.acc.extinguisherReviewDate),
            tachographReviewDate : getValue($scope.acc.tachographReviewDate),
            insuranceDate : getValue($scope.acc.insuranceDate),
            notificationBetweenEventDays : 14,
            sitters : $scope.sitters
        };

        // console.log(data);
        $http.post('registerUser', data).
        then(function() {
            $location.path("login");
        });
    };

    var getValue = function(value) {
        if (value) return value;
        else return '';
    };
});