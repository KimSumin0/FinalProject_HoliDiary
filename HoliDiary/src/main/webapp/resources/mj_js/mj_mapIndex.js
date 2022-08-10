let latLng = { lat: 37.5662952, lng: 126.9779451 };
let europe = { lat: 56.0789635, lng: 0.9901297 };
let asia = { lat: 34.047863, lng: 100.6196553 };
let america = { lat: 11.863562, lng: -107.4353043 };
let oceania = { lat: -11.1692535, lng: 130.6697515 };
let africa = { lat: 4.364132, lng: 23.025819 };
const bounds = {
		north: 84.860203,
		south: -84.618493,
		west: -180.0,
		east: 180.0
};

const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
let labelIndex = 0;
let map, infoWindow;
let arr = [];






function initMap() {
	
	map = new google.maps.Map(document.getElementById("map"), {
	    zoom: 13,
	    center: latLng,
	    restriction: {
	           latLngBounds: bounds,
	           strictBounds: false,
	       },
	  });
	
  infoWindow = new google.maps.InfoWindow();
  const locationButton = document.createElement("button");
  locationButton.textContent = "현재위치";
  locationButton.classList.add("custom-map-control-button");
  map.controls[google.maps.ControlPosition.BOTTOM_LEFT].push(locationButton);
  locationButton.addEventListener("click", () => {
      // Try HTML5 geolocation.
      if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition((position) => {
              const pos = {
                  lat: position.coords.latitude,
                  lng: position.coords.longitude,
                  zoom: 14,
              };
              infoWindow.setPosition(pos);
              infoWindow.setContent("현재위치를 찾았습니다!" + "<br>" + "(사용자님의 위치와 대략 500m정도 차이가 날 수 있습니다)");
              infoWindow.open(map);
              map.setCenter(pos);
          }, () => {
              handleLocationError(true, infoWindow, map.getCenter());
          });
      }
      else {
          // Browser doesn't support Geolocation
          handleLocationError(false, infoWindow, map.getCenter());
      }
  });
	  
  $('.europeGo').on('click', function() {
		map.panTo(europe);
		map.setZoom(4);
	});
  $('.asiaGo').on('click', function() {
	  map.panTo(asia);
	  map.setZoom(3);
  });
  $('.americaGo').on('click', function() {
	  map.panTo(america);
	  map.setZoom(3);
  });
  $('.oceaniaGo').on('click', function() {
	  map.panTo(oceania);
	  map.setZoom(4);
  });
  $('.africaGo').on('click', function() {
	  map.panTo(africa);
	  map.setZoom(3);
  });
		
	  
  const drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.MARKER,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [
                google.maps.drawing.OverlayType.MARKER,
                google.maps.drawing.OverlayType.CIRCLE,
                google.maps.drawing.OverlayType.POLYGON,
                google.maps.drawing.OverlayType.POLYLINE,
                google.maps.drawing.OverlayType.RECTANGLE,
            ],
        },
        
		
        circleOptions: {
            fillColor: "#ffff00",
            fillOpacity: 0.2,
            strokeWeight: 2,
            clickable: true,
            editable: true,
            zIndex: 1,
        },
    });
    drawingManager.setMap(map);
   
    
 
 google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
	 if (e.type == google.maps.drawing.OverlayType.MARKER) {
		// arr.push(e.overlay);
		// console.log(arr[0]);
		// drawingManager.setDrawingMode(null);
		google.maps.event.addListener(e.overlay, 'click', function(event) {
			e.overlay.setMap(null);
		});
		
		
		 // drawingManager.setMap(null);
   } else if (e.type == google.maps.drawing.OverlayType.CIRCLE) {
	   	// arr.push(e.overlay);
		// console.log(arr[0]);
		// drawingManager.setDrawingMode(null);
		google.maps.event.addListener(e.overlay, 'click', function overlayClickListner(event) {
			e.overlay.setRadius(0);
			e.overlay.setMap(null);
		});
		
   } else if (e.type == google.maps.drawing.OverlayType.POLYGON) {
		// arr.push(e.overlay);
		// console.log(arr[0]);
		drawingManager.setDrawingMode(null);
		google.maps.event.addListener(e.overlay, 'click', function(event) {
			e.overlay.setMap(null);
		});
		
   } else if (e.type == google.maps.drawing.OverlayType.POLYLINE) {
		// arr.push(e.overlay);
		// console.log(arr[0]);
		drawingManager.setDrawingMode(null);
		google.maps.event.addListener(e.overlay, 'click', function(event) {
			e.overlay.setMap(null);
		});
		
  } else {
	// arr.push(e.overlay);
		// console.log(arr[0]);
		drawingManager.setDrawingMode(null);
		google.maps.event.addListener(e.overlay, 'click', function(event) {
			e.overlay.setMap(null);
		});
		
  }
	 
	 
	 
  });
    
 google.maps.event.addListener(drawingManager, 'click', function(e) {
	 if (e.overlay == arr[0]) {
	 }
})
	   

 // This event listener calls addMarker() when the map is clicked.
google.maps.event.addListener(map, "click", (event) => {
        addMarker(event.latLng, map);
    });
    
    

function addMarker(location, map) {
    new google.maps.Marker({
        position: location,
        label: labels[labelIndex++ % labels.length],
        map: map,
    });
    
}

google.maps.event.addListener(addMarker, "click", (event) => {
	map.removeOverlay(marker);
});

/*
 * google.maps.event.addListener(addMarker, 'click', function(m) { alert(11);
 * m.setLabel(null); m.setMap(null); });
 */

/*
 * (function(addMarker,l){
 * google.maps.event.addListener(addMarker,'click',function(){ alert(11);
 * l.setMap(this.getMap()); }); })(addMarker)
 */

  
  

}  // initMap






function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation
        ? "Error: The Geolocation service failed."
        : "Error: Your browser doesn't support geolocation.");
    infoWindow.open(map);
}




window.initMap = initMap;
//window.initAutocomplete = initAutocomplete;

// window.initialize = initialize;
export {};




