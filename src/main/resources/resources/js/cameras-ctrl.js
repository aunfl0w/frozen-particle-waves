angular.module('fpwApp')
.controller('camerasCtrl', function($scope, $http, $location, $timeout){
	var originalCameraList;
	$scope.cameralist = [];
	
	$http.get('/fpw/camera/info').then(function(goodResponse) {
		$scope.cameralist = goodResponse.data;
		originalCameraList = goodResponse.data;
		var timestamp = (new Date()).getTime();
		var count = 0;
		for (count = 0; count < originalCameraList.length; count++) {
			$scope.cameralist[count].url = '/fpw/camera/' + originalCameraList[count].id +
			'/image?' + timestamp;
			$scope.cameralist[count].style='';
		}
		intervalFunction();
		fadeUpdate();
	}, function(badResponse) {
		$location.path('/login');
	});
	
	var index =  0;
	
	var fadeUpdate = function() {

		$scope.cameralist[index].style='trasition-out';
		var timestamp = (new Date()).getTime();
		$scope.cameralist[index].url = '/fpw/camera/' + originalCameraList[index].id +
		'/image?' + timestamp;

			$timeout(function(){
				
				$scope.cameralist[index].style='trasition-in';

				index++;

				if (index < originalCameraList.length){
					fadeUpdate();
				} 
				$scope.index = index;
				
			}, 500);
			
	}
	var intervalFunction = function() {
		$timeout(function() {
			index = 0;
			fadeUpdate();
			intervalFunction();
		}, 31000);
	};

	  
	
});

angular.module('fpwApp')
.controller('cameraCtrl', function($scope, $http, $location, $timeout, $routeParams){
	$scope.cameraId = $routeParams.cameraId;
	$scope.imagelist = [];
	//assume there will be 10 images
	var step;
	for (step = 0; step < 10; step++){
		$scope.imagelist.push('/fpw/camera/'+$routeParams.cameraId+'/image/'+step);
	}
					
	
});