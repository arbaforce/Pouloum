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
  
  document.getElementById("map_Button").setAttribute("onclick", "closeNav()");
}

function closeNav() {
  document.getElementById("search_result_map_global_col").style.width = "65px";
  document.getElementById("search_result_list_global_col").style.marginRight= "65px";
  
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

function addResultDemo() {
    addResult([{name:"Basketball", url:"", level:"débutant"}], {name:"Claude", url:""}, "Tête d'Or");
}

function addResult(data_activities, data_organizer, data_place) {
    var results = document.getElementById("search_result_list_global_container");
    
    var div_event_container = document.createElement("div");
    div_event_container.className = "event_container container w-100 border";
    {
        var div_event_row = document.createElement("div");
        div_event_row.className = "row mx-0 px-0";
        {
            var div_event_activity_organisator_place_users_container = document.createElement("div");
            div_event_activity_organisator_place_users_container.className = "container";
            {
                var div_event_activity_organisator_place_users_row = document.createElement("div");
                event_activity_organisator_place_users_row.className = "row w-100 mx-0";
                {
                    var div_event_activity_organisator_place_col = document.createElement("div");
                    div_event_activity_organisator_place_col.className = "col mx-0 px-0";
                    {
                        var div_event_activity_container = document.createElement("div");
                        div_event_activity_container.className = "container";
                        {
                            var div_event_activity_row = document.createElement("div");
                            div_event_activity_row.className = "row mx-0 px-0";
                            {
                                var txt_activities = document.createTextNode("Activité(s) : ");
                                
                                div_event_activity_row.appendChild(txt_activities);
                                
                                for (var i=0; i<data_activities.length; i++)
                                {
                                    var data_activity = data_activities[i];
                                    
                                    var a_activity = document.createElement("a");
                                    a_activity.href = data_activity.url;
                                    {
                                        var txt_activity = document.createTextNode(data_activity.name);
                                        
                                        a_activity.appendChild(txt_activity);
                                    }
                                    
                                    div_event_activity_row.appendChild(a_activity);
                                    
                                    if (data_activity.level.length > 0)
                                    {
                                        var txt_activity = document.createTextNode(" (" + data_activity.level + ")");
                                        
                                        div_event_activity_row.appendChild(txt_activity);
                                    }
                                }
                            }
                            
                            div_event_activity_container.appendChild(div_event_activity_row);
                        }
                        
                        div_event_activity_organisator_place_col.appendChild(div_event_activity_container);
                        
                        var div_event_organisator_container = document.createElement("div");
                        div_event_organisator_container.className = "container";
                        {
                            var div_event_organisator_row = document.createElement("div");
                            div_event_organisator_row.className = "row mx-0 px-0";
                            {
                                var txt_organizers = document.createTextNode("Organisateur : ");
                                
                                div_event_organisator_row.appendChild(txt_organizers);
                                
                                var a_organizer = document.createElement("a");
                                a_organizer.href = data_organizer.url;
                                {
                                    var txt_organizer = document.createTextNode(data_organizer.name);
                                    
                                    a_organizer.appendChild(txt_organizer);
                                }
                                
                                div_event_organisator_row.appendChild(a_organizer);
                            }
                            
                            div_event_organisator_container.appendChild(div_event_organisator_row);
                        }
                        
                        div_event_activity_organisator_place_col.appendChild(div_event_organisator_container);
                        
                        var div_event_place_container = document.createElement("div");
                        div_event_place_container.className = "container";
                        {
                            var div_event_place_row = document.createElement("div");
                            div_event_place_row.className = "row mx-0 px-0";
                            {
                                var txt_places = document.createTextNode("Lieu : ");
                                
                                div_event_place_row.appendChild(txt_places);
                                
                                var txt_place = document.createTextNode(data_place);
                                    
                                div_event_place_row.appendChild(txt_place);
                            }
                            
                            div_event_place_container.appendChild(div_event_place_row);
                        }
                        
                        div_event_activity_organisator_place_col.appendChild(div_event_place_container);
                    }
                    
                    /*
                    <div id="users_col" class="col h-100 w-100 users_col">
                        <div id="users_title_container" class="container h-100 w-100">
                            <div id="users_title_row" class="row mx-0 px-0 h-100 w-100">
                                Participant(s):
                            </div><!--#users_title_row -->
                        </div><!--#users_title_container -->
                        <div id="users_container" class="container">
                            <div id="users_row" class="row mx-0 px-0 h-100 w-100 users_row">
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                <a href="http://localhost:8080/Pouloum-IHM-WEB/my_profile.html">
                                    <img src="https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg" alt="image" class="little_avatar_image rounded " /> 
                                </a>
                                
                            </div><!--#users_title_row -->
                        </div><!--#users_title_container -->
                    </div><!--#event_activity_organisator_place_col -->
                    */
                    
                    div_event_activity_organisator_place_users_row.appendChild(div_event_activity_organisator_place_col);
                }
                
                div_event_activity_organisator_place_users_container.appendChild(div_event_activity_organisator_place_users_row);
            }
            
            /*
            <div id="event_date_container" class="container">
                <div id="event_date_row" class="row mx-0 px-0">
                    <div id="event_date_col" class="col-6 mx-0 my-0 px-0 py-0">
                        Date
                    </div><!--#event_date_col -->
                </div><!--#event_date_row -->
            </div><!--#event_date_container -->
            <div id="event_time_container" class="container">
                <div id="event_time_row" class="row mx-0 px-0">
                    <div id="event_time_col" class="col-6 mx-0 my-0 px-0 py-0">
                        Durée
                    </div><!--#event_time_col -->
                </div><!--#event_time_row -->
            </div><!--#event_time_container -->
            <div id="event_status_know_more_no_more_button_badges_container" class="container event_status_know_more_no_more_button_badges_container">
                <div id="event_status_know_more_no_more_button_badges_row" class="row mx-0 px-0">
                    
                    <div id="event_statut_know_more_col" class="col-6 event_statut_know_more_col mx-0 px-0">
                        <div id="event_status_container" class="container">
                            <div id="event_status_row" class="row mx-0 px-0">
                                Statut : remplie.
                            </div>  <!--#event_status_row -->
                        </div><!--#event_status_container -->
                        <div id="event_know_more_container" class="container">
                            <div id="event_know_more_row" class="row mx-0 px-0">
                                <a href="">En savoir plus</a>
                            </div>  <!--#event_know_more_row -->
                        </div><!--#event_know_more_container -->
                    </div><!--#event_status_know_more_col -->
                    <div id="event_no_more_button_col" class="col mx-0 px-0">
                        <button id="buttonLeaveEvent" type="button" class="btn btn-light buttonEvent" >
                            Je ne participe plus.
                        </button><!--#buttonLeaveEvent -->
                    </div><!--#event_no_more_button_col -->
                    <div id="event_badges_col" class="col mx-0 px-0" >
                        
                        <div id="event_badges_container" class="container" >
                            
                            <div id="event_badges_row" class="row mx-0 event_badges_row">
                                <img src="STYLE/Wind.png" alt="image" class="little_avatar_image rounded " /> 
                                <img src="STYLE/Fire.png" alt="image" class="little_avatar_image rounded " /> 
                            </div><!--#event_badges_row -->
                        </div><!--#event_badges_container -->
                                
                    </div><!--#event_badges_col -->
                        
                    
                    
                    
                </div><!--#event_status_know_more_no_more_button_badges_row -->
            </div><!--#event_status_know_more_no_more_button_badges_container -->
            */
            
            div_event_row.appendChild(div_event_activity_organisator_place_users_container);
        }
        
        div_event_container.appendChild(div_event_row);
    }
    
    results.appendChild(div_event_container);
    
    //EXAMPLE:
    /*
    var container = document.createElement("div");
    consoles.appendChild(container);
    
    var checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = resname;
    checkbox.id = "c_" + resname;
    checkbox.value = name;
    checkbox.onchange = filterresults;
    container.appendChild(checkbox);
    
    var label = document.createElement("label");
    label.htmlFor = "c_" + resname;
    label.innerText = name;
    container.appendChild(label);
    */
    
    return div_event_container;
}

