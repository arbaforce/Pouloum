/* 
 * to toggle the map 
 */
function openNav() {
  document.getElementById("search_result_map_global_col").style.width = "49%";
  document.getElementById("search_result_list_global_col").style.marginRight= "0px";
    
  var events_statut_know_more_col = document.getElementsByClassName("event_statut_know_more_col");
  var events_status_know_more_no_more_button_badges_container = document.getElementsByClassName("event_status_know_more_no_more_button_badges_container");
  for (var i = 0; i < events_statut_know_more_col.length; i++){
      events_statut_know_more_col[i].setAttribute("class", "col event_statut_know_more_col mx-0 px-0");
  }
  for(var i = 0; i < events_status_know_more_no_more_button_badges_container.length; i++){
      events_status_know_more_no_more_button_badges_container[i].style.marginRight= "50%";
  }
  setTimeout(function(){map.invalidateSize(); map.setView([45.74,4.84], 13);},500);
  
  document.getElementById("map_Button").setAttribute("onclick", "closeNav()");
}

function closeNav() {
  document.getElementById("search_result_map_global_col").style.width = "65px";
  document.getElementById("search_result_list_global_col").style.marginRight= "50px";
  
  var events_statut_know_more_col = document.getElementsByClassName("event_statut_know_more_col");
  var events_status_know_more_no_more_button_badges_container = document.getElementsByClassName("event_status_know_more_no_more_button_badges_container");
  for (var i = 0; i < events_statut_know_more_col.length; i++){
      events_statut_know_more_col[i].setAttribute("class", "col-6 event_statut_know_more_col mx-0 px-0");
  }
  for(var i = 0; i < events_status_know_more_no_more_button_badges_container.length; i++){
      events_status_know_more_no_more_button_badges_container[i].style.marginRight= "0px";
  }
  
  
  document.getElementById("map_Button").setAttribute("onclick", "openNav()");
}

// source : https://leafletjs.com/examples/quick-start/
function initialiseMap() {
	map = L.map('map');
	var basemap = L.tileLayer('http://{s}.tile.stamen.com/terrain/{z}/{x}/{y}.png', {
	        attribution: '<a href="http://content.stamen.com/dotspotting_toner_cartography_available_for_download">Stamen Toner</a>, <a href="http://www.openstreetmap.org/">OpenStreetMap</a>, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	        maxZoom: 17,
                accessToken: 'sk.eyJ1IjoiYXJiYWZvcmNlIiwiYSI6ImNqdmM1OG9pbDAwMDI0ZG82NXl4YWliZm0ifQ.UiSlHm8LYj8QEPSX6EpCyw'
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

function addMarqeur(nom, url, longDeg, longMin, longDir, latDeg, latMin, latDir) {
	var coordonnes = degresMinutesToDecimal(longDeg, longMin, longDir, latDeg, latMin, latDir);
	var marker = L.marker(coordonnes).addTo(map);

	var pageUrl = window.location.href.split('?')[0].split('/');
	pageUrl.pop();
	pageUrl = pageUrl.join('/');
	var capitalUrl = pageUrl + '/capital.html?capitalUrl=' + url;
	marker.bindPopup('<a href="'+capitalUrl+'">'+nom+'</a>');
}

function degresMinutesToDecimal(longDeg, longMin, longDir, latDeg, latMin, latDir) {
	var long = parseInt(longDeg, 10) + parseInt(longMin, 10)/60;
	var lat = parseInt(latDeg, 10) + parseInt(latMin, 10)/60;
	if(longDir == 'W') long *= -1;
	if(latDir == 'S') lat *= -1;
	return [lat, long];
}

