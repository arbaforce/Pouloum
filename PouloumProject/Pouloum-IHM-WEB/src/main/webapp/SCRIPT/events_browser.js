/* 
 * to toggle the map 
 */
function openNav() {
  document.getElementById("search_result_map_global_col").style.width = "50%";
  document.getElementById("search_result_list_global_col").style.marginRight = "50%";
  document.getElementById("map_Button").setAttribute("onclick", "closeNav()");
}

function closeNav() {
  document.getElementById("search_result_map_global_col").style.width = "65px";
  document.getElementById("search_result_list_global_col").style.marginRight= "65px";
  document.getElementById("map_Button").setAttribute("onclick", "openNav()");
}



