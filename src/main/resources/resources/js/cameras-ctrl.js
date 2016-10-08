angular.module('fpwApp')
.controller('camerasCtrl', function($scope, $http, $location, $timeout){
	
	
	$scope.timestamp = (new Date()).getTime();
	
	$http.get('/fpw/camera/info')
	.then(function(goodResponse){
		$scope.cameralist = goodResponse.data;
	}, function(badResponse){
			$location.path('/login');
	});
	
	  $scope.intervalFunction = function(){
	    $timeout(function() {
	      $scope.timestamp = (new Date()).getTime();
	      $scope.intervalFunction();
	    }, 20000)
	  };

	  $scope.intervalFunction();
	
});