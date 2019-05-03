/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openNav() {
  document.getElementById("search_result_map_global_col").style.width = "50%";
  document.getElementById("search_result_list_global_container").style.marginRight = "700px";
  document.getElementById("map_Button").setAttribute("onclick", "closeNav()");
}

function closeNav() {
  document.getElementById("search_result_map_global_col").style.width = "65px";
  document.getElementById("search_result_list_global_container").style.marginRight= "65px";
  document.getElementById("map_Button").setAttribute("onclick", "openNav()");
}


