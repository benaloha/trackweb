var map, bounds;
var bluePoly;

function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		mapTypeId : 'roadmap',
		gestureHandling : 'greedy'
	});
	bounds = new google.maps.LatLngBounds();
	bluePoly = initPolyLine('#0000FF');
	addPoints('route', bluePoly);
	map.fitBounds(bounds);
}

function initPolyLine(colour){
	polyLine = new google.maps.Polyline({
		geodesic : true,
		strokeColor : colour,
		strokeOpacity : 1.0,
		strokeWeight : 2
	});
	polyLine.setMap(map);
	return polyLine;
}

function addPoints(elementIdRouteInfo, polyLine) {
	var info = document.getElementById(elementIdRouteInfo);
	if (info != null && info.value != null) {
		var array = eval(info.value);
		if (typeof (array) !== "undefined" && array != "") {
			var i, len = array.length;
			for (var i = 0; i < len; i++) {
				var point = new google.maps.LatLng(array[i][1], array[i][2]);
				bounds.extend(point);
				polyLine.getPath().push(point);
			}
		}
	}
}