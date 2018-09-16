angular.module('fpwApp')
.controller('camerasCtrl', function($scope, $http, $location, $timeout, SocketService ){
	var originalCameraList;
	$scope.cameralist = [];
	
	SocketService.callbacks.notifyUpdateCamera = function(cameraUpdated, imageID){
			var index = 0;
			var x = 0;
			for (x = 0;x < $scope.cameralist.length; x++){
				if($scope.cameralist[x].id === cameraUpdated){
					index = x;
				}
			}
		
			fadeUpdate(index, imageID);
		};

		
	$http.get('/fpw/camera/info').then(function(goodResponse) {
		$scope.cameralist = goodResponse.data;
		originalCameraList = goodResponse.data;
		var timestamp = (new Date()).getTime();
		var count = 0;
		for (count = 0; count < originalCameraList.length; count++) {
			$scope.cameralist[count].url = '/fpw/camera/' + originalCameraList[count].id +
			'/image?' + timestamp;
			$scope.cameralist[count].style='trasition-in';
		}
	}, function(badResponse) {
		$location.path('/login');
	});
	
	
	
	var fadeUpdate = function(index, imageID) {
		if (document.hidden){
			return ;
		}
		
		$scope.cameralist[index].style='trasition-out';
		var timestamp = (new Date()).getTime();
		$scope.cameralist[index].url = '/fpw/camera/' + originalCameraList[index].id + '/image/' + imageID;

			$timeout(function(){
				$scope.cameralist[index].style='trasition-in';
			}, 500);
			
	}

	  
	
});

angular.module('fpwApp')
.controller('cameraCtrl', function($scope, $http, $location, $timeout, $routeParams){
	$scope.cameraId = $routeParams.cameraId;
	$scope.imagelist = [];
	
	$http.get('/fpw/camera/' + $routeParams.cameraId + '/idlist').then(function(goodResponse) {
		var idlist = goodResponse.data;
		for (var key in idlist){
			$scope.imagelist.push('camera/' + $routeParams.cameraId + '/image/' + idlist[key])
		}
	}, function(badResponse) {
		$location.path('/login');
	});
});