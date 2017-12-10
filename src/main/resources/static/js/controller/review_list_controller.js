
busApp.controller('ReviewListController', function($scope, ManageUserService) {
    $scope.reviewList = [];

    $scope.init = function () {
        $scope.authenticate(function () {
            var promise = ManageUserService.getReviewList($scope.loggedUser);
            promise.success(function (data) {
                $scope.reviewList = data;
            }).error(function () {});
        });
    };
});