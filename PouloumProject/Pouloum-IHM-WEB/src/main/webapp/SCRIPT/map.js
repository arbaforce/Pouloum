// source : https://leafletjs.com/examples/quick-start/
var listMarkers = new Map();

function initialiseMap() {
	map = L.map('map');
	map.setView([45.74,4.84], 13);
	var basemap = L.tileLayer('http://{s}.tile.stamen.com/terrain/{z}/{x}/{y}.png', {
	        attribution: '<a href="http://content.stamen.com/dotspotting_toner_cartography_available_for_download">Stamen Toner</a>, <a href="http://www.openstreetmap.org/">OpenStreetMap</a>, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	        maxZoom: 17
	});
	basemap.addTo(map);
        /*document.getElementsByClassName("leaflet-map-pane")[0].style.transform ="translate3d(302px, -4px, 0px)";*/
        
        /*style="transform: translate3d(302px, -4px, 0px);"*/
}

function setViewMap(longDeg, longMin, longDir, latDeg, latMin, latDir) {
	var zoom = 10;
	var coordonnes = degresMinutesToDecimal(longDeg, longMin, longDir, latDeg, latMin, latDir);
	map.setView(coordonnes, zoom);
}

function initializeMarker(id, name, lat, lng) {
	var marker = L.marker([lat,lng]);
        map.addLayer(marker);

	marker.bindPopup(name);
        
        listMarkers.set(id.toString(), marker);
}

function addMarker(id) {
	map.addLayer(listMarkers.get(id.toString()));
}

function clearMarker() {
        for(var object of listMarkers.values()){
            map.removeLayer(object);
        }
}

/*function addMarqeur(nom, url, longDeg, longMin, longDir, latDeg, latMin, latDir) {
	var coordonnes = degresMinutesToDecimal(longDeg, longMin, longDir, latDeg, latMin, latDir);
	var marker = L.marker(coordonnes).addTo(map);

	var pageUrl = window.location.href.split('?')[0].split('/');
	pageUrl.pop();
	pageUrl = pageUrl.join('/');
	var capitalUrl = pageUrl + '/capital.html?capitalUrl=' + url;
	marker.bindPopup('<a href="'+capitalUrl+'">'+nom+'</a>');
}*/

function degresMinutesToDecimal(longDeg, longMin, longDir, latDeg, latMin, latDir) {
	var long = parseInt(longDeg, 10) + parseInt(longMin, 10)/60;
	var lat = parseInt(latDeg, 10) + parseInt(latMin, 10)/60;
	if(longDir == 'W') long *= -1;
	if(latDir == 'S') lat *= -1;
	return [lat, long];
}

