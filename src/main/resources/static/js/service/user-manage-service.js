
busApp.factory('ManageUserService', ['$http', function ($http) {

    return {
        getUserData: function (username) {
            return $http.get('getuserdata/'+username);
        },
        saveDrivers: function (data) {
            return $http.post('savedrivers', data);
        },
        saveBuses: function (data) {
            return $http.post('savebuses', data);
        },
        saveSitters: function (data) {
            return $http.post('savesitters', data);
        },
        getOtherDrivers: function(username) {
            return $http.get('getotherdrivers/'+username);
        },
        getOtherBuses: function(driverId) {
            return $http.get('getotherbuses/'+driverId);
        },
        getReviewList: function (username) {
            return $http.get('getreviewlist/'+username);
        }
    };
}]);