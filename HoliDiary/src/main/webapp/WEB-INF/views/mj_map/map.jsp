<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>

<html>
<head>
<title>The places you traveled to</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>

<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<link rel="stylesheet" type="text/css" href="resources/mj_css/mj_map.css" />
<script type="module" src="resources/mj_js/mj_mapIndex.js"></script>
<script type="text/javascript" src="resources/mj_js/map_help.js"></script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
</head>
<body>

<div id="whole">
	<div class="dropdown">
		<button onclick="dp_menu()" class="worldGoButton">국가 바로가기</button>
		<div style="display: none;" id="drop-content">
			<button id="worldButton" class="europeGo">유럽</button>
			<button id="worldButton" class="asiaGo">아시아</button>
			<button id="worldButton" class="americaGo">아메리카</button>
			<button id="worldButton" class="oceaniaGo">오세아니아</button>
			<button id="worldButton" class="africaGo">아프리카</button>
		</div>
	</div>

	<img class="mapHelp" alt="도움말" 
		onclick="helpOpen()" src="resources/sm_img/png-clipart-question-mark-question-mark-removebg-preview.png">
	
	<div id="imageDownload">
		<button class="imageDownloadButton">이미지로 저장하기</button>
	</div>
	

	<script>
		function dp_menu() {
			let click = document.getElementById("drop-content");
			if (click.style.display === "none") {
				click.style.display = "block";

			} else {
				click.style.display = "none";
			}
		}
	</script>

	<div class="pac-card" id="pac-card">
		<div id="pac-container">
			<input id="pac-input" type="text" placeholder="위치 검색" />
		</div>
	</div>
	<div id="map"></div>
	<div id="infowindow-content">
		<span id="place-name" class="title"></span><br /> <span
			id="place-address"></span>
	</div>

	<!-- <div id="map"></div> -->

	<p>
</div>
<script type="text/javascript">
	
	
	
	$(".imageDownloadButton").on("click", function() {
		ignoreElements: (node) => {
        	return node.nodeName === 'IFRAME';
      	}
		
		html2canvas(document.querySelector("#whole"), {
	      useCORS: true,
    	  allowTaint: false,
	      ignoreElements: (node) => {
    	  return node.nodeName === 'IFRAME';
      	}
	    }).then(canvas => {
    	  const url = canvas.toDataURL('image/png');
	      saveAs(url, 'map.png');
    	  window.URL.revokeObjectURL(url);
	    });
		function saveAs(uri, filename) {
			var link = document.createElement('a');
			if(typeof link.download === 'string'){
				link.href = uri;
				link.download = filename;
				document.body.appendChild(link);
				link.click();
				document.body.removeChild(link);
			} else {
				window.open(uri);
			}
		}
    });
	
	
	
	</script>

		<script
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDb4vTBtJ5eI4DOAJXj4ov7YSdM066-PQ0&callback=initMap&libraries=places,drawing&v=weekly"
			defer></script>
</body>
</html>
