angular.module('jobPortalApp', [])
    .controller('HeaderController', function ($scope, $http, $window, $location, $q) {

        $scope.searchJobs = function() {
            $http.get("http://" + $location.host() + ":" + $location.port() + "/job-portal/search/"+$scope.searchKey).
            then(function successCallback(response) {
                alert("Success");
            }),function errorCallback(response) {
                alert("There is an error while adding DL data with duplicate parameters ");
            }
        }
        $scope.getChartDetails();
    });

