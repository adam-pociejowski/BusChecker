

var app = angular.module('BusChecker', ['ngResource']);
app.controller('CheckerController', function($scope, $http, $filter) {

    $http.get('/secured/rest/checkerdata').
    then(function(response) {
        $scope.data = response.data;
        changeDateFormat($scope.data);
    });

    var changeDateFormat = function(data) {
        $scope.data.technicalReviewDate = $filter('date')(data.technicalReviewDate,'dd-MM-yyyy');
        $scope.data.liftReviewDate = $filter('date')(data.liftReviewDate,'dd-MM-yyyy');
        $scope.data.extinguisherReviewDate = $filter('date')(data.extinguisherReviewDate,'dd-MM-yyyy');
        $scope.data.tachographReviewDate = $filter('date')(data.tachographReviewDate,'dd-MM-yyyy');
        $scope.data.insuranceDate = $filter('date')(data.insuranceDate,'dd-MM-yyyy');
    };
});