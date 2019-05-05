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



