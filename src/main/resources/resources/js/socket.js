angular.module('fpwApp').factory('SocketService', function($q, $rootScope) {
	var Service = {};
	Service.callbacks = {};
	
	var socket = new SockJS('/fpw/communications');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {

		console.log('Connected: ' + frame);
		stompClient.subscribe('/queue/comms', function(comms) {
			console.log(JSON.parse(comms.body).content);
			
			var messageData = JSON.parse(comms.body);
			if (messageData.messageType === "1"){
				var cameraUpdated = messageData.updateCamera;
				var imageID = messageData.imageID;
				console.log("Update from " + cameraUpdated + " : " + imageID );
				Service.callbacks.notifyUpdateCamera(cameraUpdated, imageID);
			}

		});
	});

	Service.socket = socket;
	
	return Service;
});
