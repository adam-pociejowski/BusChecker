
var app = angular.module('BusChecker', ['ngResource']);
app.controller('CheckerController', function($scope, $http, $filter) {
    data = [];
    dates = [];
    infos = [];
    var editingObj = null;

    $http.get('/secured/rest/checkerdata').
    then(function(response) {
        setData(response.data);
    });

    $scope.edit = function(obj) {
        $scope.modalInput = obj.value;
        editingObj = obj;
    };

    $scope.save = function() {
        editingObj.value = $scope.modalInput;
    };

    var setData = function(responseData) {
        $scope.driver = responseData.firstname+' '+responseData.lastname;
        infos.push(createObject('Nr boczny', responseData.sideNumber));
        infos.push(createObject('Nr rejestracyjny', responseData.rejestrNumber));
        infos.push(createObject('Liczba miejsc', responseData.numberOfSeats));
        infos.push(createObject('Powiadomienie na telefon', responseData.notificationBetweenEventDays+' dni wcześniej'));
        dates.push(createObject('Przegląd techniczny', dateFormat(responseData.technicalReviewDate)));
        dates.push(createObject('Przegląd windy', dateFormat(responseData.liftReviewDate)));
        dates.push(createObject('Przegląd gaśnicy', dateFormat(responseData.technicalReviewDate)));
        dates.push(createObject('Przegląd tachografu', dateFormat(responseData.tachographReviewDate)));
        dates.push(createObject('Ubezpieczenie wozu', dateFormat(responseData.insuranceDate)));
        $scope.infos = infos;
        $scope.dates = dates;
    };

    var dateFormat = function(date) {
        return $filter('date')(date,'dd-MM-yyyy');
    };

    var createObject = function(description, val) {
        return {
            label : description,
            value : val
        };
    };
});