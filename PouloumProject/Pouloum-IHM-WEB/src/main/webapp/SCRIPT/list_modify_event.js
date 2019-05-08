function noResult() {
    var results = document.getElementById("search_result_list_global_container");
    
    results.innerHTML = "";
}

function addResult(data_id, data_label, data_activities, data_organizer, data_place, data_date, data_duration, data_participants, data_status, data_eventurl, data_grades, data_badges) {
    var results = document.getElementById("search_result_list_global_container");
    
    //TODO: show event label (parameter data_label) somewhere
    //TODO: show event grades (parameter data_grades) somewhere
    
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
                div_event_activity_organisator_place_users_row.className = "row w-100 mx-0";
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
                    
                    div_event_activity_organisator_place_users_row.appendChild(div_event_activity_organisator_place_col);
                    
                    var div_users_col = document.createElement("div");
                    div_users_col.className = "col h-100 w-100 users_col";
                    {
                        var div_users_title_container = document.createElement("div");
                        div_users_title_container.className = "container h-100 w-100";
                        {
                            var div_users_title_row = document.createElement("div");
                            div_users_title_row.className = "row mx-0 px-0 h-100 w-100";
                            {
                                var txt_participants = document.createTextNode("Participant(s) : ");
                                
                                div_users_title_row.appendChild(txt_participants);
                            }
                            
                            div_users_title_container.appendChild(div_users_title_row);
                        }
                        
                        div_users_col.appendChild(div_users_title_container);
                        
                        var div_users_container = document.createElement("div");
                        div_users_container.className = "container";
                        {
                            var div_users_row = document.createElement("div");
                            div_users_row.className = "row mx-0 px-0 h-100 w-100 users_row";
                            {
                                for (var i=0; i<data_participants.length; i++)
                                {
                                    var data_participant = data_participants[i];
                                    
                                    var a_participant = document.createElement("a");
                                    a_participant.href = data_participant.url;
                                    {
                                        var img_participant = document.createElement("img");
                                        img_participant.className = "little_avatar_image rounded";
                                        img_participant.src = data_participant.img;
                                        img_participant.alt = data_participant.alt;
                                        
                                        a_participant.appendChild(img_participant);
                                    }
                                    
                                    div_users_row.appendChild(a_participant);
                                }
                            }
                            
                            div_users_container.appendChild(div_users_row);
                        }
                        
                        div_users_col.appendChild(div_users_container);
                    }
                    
                    div_event_activity_organisator_place_users_row.appendChild(div_users_col);
                }
                
                div_event_activity_organisator_place_users_container.appendChild(div_event_activity_organisator_place_users_row);
            }
            
            div_event_row.appendChild(div_event_activity_organisator_place_users_container);
            
            var div_event_date_container = document.createElement("div");
            div_event_date_container.className = "container";
            {
                var div_event_date_row = document.createElement("div");
                div_event_date_row.className = "row mx-0 px-0";
                {
                    var div_event_date_col = document.createElement("div");
                    div_event_date_col.className = "col-6 mx-0 my-0 px-0 py-0";
                    {
                        var txt_dates = document.createTextNode("Date : ");
                        
                        div_event_date_col.appendChild(txt_dates);
                        
                        var txt_date = document.createTextNode(data_date);
                        
                        div_event_date_col.appendChild(txt_date);
                    }
                    
                    div_event_date_row.appendChild(div_event_date_col);
                }
                
                div_event_date_container.appendChild(div_event_date_row);
            }
            
            div_event_row.appendChild(div_event_date_container);
            
            var div_event_time_container = document.createElement("div");
            div_event_time_container.className = "container";
            {
                var div_event_time_row = document.createElement("div");
                div_event_time_row.className = "row mx-0 px-0";
                {
                    var div_event_time_col = document.createElement("div");
                    div_event_time_col.className = "col-6 mx-0 my-0 px-0 py-0";
                    {
                        var txt_time = document.createTextNode("Durée : ");
                        
                        div_event_time_col.appendChild(txt_time);
                        
                        var txt_time = document.createTextNode(data_duration);
                        
                        div_event_time_col.appendChild(txt_time);
                    }
                    
                    div_event_time_row.appendChild(div_event_time_col);
                }
                
                div_event_time_container.appendChild(div_event_time_row);
            }
            
            div_event_row.appendChild(div_event_time_container);
            
            var div_event_status_know_more_no_more_button_badges_container = document.createElement("div");
            div_event_status_know_more_no_more_button_badges_container.className = "container event_status_know_more_no_more_button_badges_container";
            {
                var div_event_status_know_more_no_more_button_badges_row = document.createElement("div");
                div_event_status_know_more_no_more_button_badges_row.className = "row mx-0 px-0";
                {
                    var div_event_statut_know_more_col = document.createElement("div");
                    div_event_statut_know_more_col.className = "col-6 event_statut_know_more_col mx-0 px-0";
                    {
                        var div_event_status_container = document.createElement("div");
                        div_event_status_container.className = "container";
                        {
                            var div_event_status_row = document.createElement("div");
                            div_event_status_row.className = "row mx-0 px-0";
                            {
                                var txt_statuses = document.createTextNode("Statut : ");
                                
                                div_event_status_row.appendChild(txt_statuses);
                                
                                var txt_status = document.createTextNode(data_status + ".");
                                
                                div_event_status_row.appendChild(txt_status);
                            }
                            
                            div_event_status_container.appendChild(div_event_status_row);
                            
                            var div_event_know_more_container = document.createElement("div");
                            div_event_know_more_container.className = "container";
                            {
                                var div_event_know_more_row = document.createElement("div");
                                div_event_know_more_row.className = "row mx-0 px-0";
                                {
                                    var a_knowmore = document.createElement("a");
                                    a_knowmore.href = data_eventurl;
                                    {
                                        var txt_knowmore = document.createTextNode("En savoir plus");
                                        
                                        a_knowmore.appendChild(txt_knowmore);
                                    }
                                    
                                    div_event_know_more_row.appendChild(a_knowmore);
                                }
                                
                                div_event_know_more_container.appendChild(div_event_know_more_row);
                            }
                            
                            div_event_status_container.appendChild(div_event_know_more_container);
                        }
                        
                        div_event_statut_know_more_col.appendChild(div_event_status_container);
                    }
                    
                    div_event_status_know_more_no_more_button_badges_row.appendChild(div_event_statut_know_more_col);
                    
                    var div_event_modify_button_col = document.createElement("div");
                    div_event_modify_button_col.className = "col-2 mx-0 px-0";
                    {
                        var button_modify = document.createElement("button");
                        button_modify.className = "btn btn-light buttonEvent";
                        button_modify.type = "button";
                        button_modify.onclick = function(){
                            window.location.href = "./modify_event.html?id="+data_id;
                        };
                        
                        {
                            var txt_modify = document.createTextNode("Modifier");
                            
                            button_modify.appendChild(txt_modify);
                        }
                        
                        div_event_modify_button_col.appendChild(button_modify);
                    }
                    
                    div_event_status_know_more_no_more_button_badges_row.appendChild(div_event_modify_button_col);
                    
                    var div_event_delete_button_col = document.createElement("div");
                    div_event_delete_button_col.className = "col-2 mx-0 px-0";
                    {
                        var button_delete = document.createElement("button");
                        button_delete.className = "btn btn-light buttonEvent";
                        button_delete.type = "button";
                        button_delete.onclick = function(){
                            $.ajax({
                                url: './AjaxAction',
                                type: 'POST',
                                data: {
                                    'action': 'cancelEvent',
                                    'eventID': data_id
                                },
                                datatype: 'json'
                            }).done(function (data) {
                                window.location.href = "./list_modify_event.html";
                            });
                        };
                        
                        {
                            var txt_delete = document.createTextNode("Supprimer");
                            
                            button_delete.appendChild(txt_delete);
                        }
                        
                        div_event_delete_button_col.appendChild(button_delete);
                    }
                    
                    div_event_status_know_more_no_more_button_badges_row.appendChild(div_event_delete_button_col);
                    
                    var div_event_badges_col = document.createElement("div");
                    div_event_badges_col.className = "col mx-0 px-0";
                    {
                        var div_event_badges_container = document.createElement("div");
                        div_event_status_container.className = "container";
                        {
                            var div_event_badges_row = document.createElement("div");
                            div_event_badges_row.className = "row mx-0 event_badges_row";
                            {
                                for (var i=0; i<data_badges.length; i++)
                                {
                                    var data_badge = data_badges[i];
                                    
                                    var img_badge = document.createElement("img");
                                    img_badge.className = "little_avatar_image rounded";
                                    img_badge.src = "STYLE/" + data_badge + ".png";
                                    img_badge.alt = data_badge;
                                    
                                    div_event_badges_row.appendChild(img_badge);
                                }
                            }
                            
                            div_event_badges_container.appendChild(div_event_badges_row);
                        }
                        
                        div_event_badges_col.appendChild(div_event_badges_container);
                    }
                    
                    div_event_status_know_more_no_more_button_badges_row.appendChild(div_event_badges_col);
                }
                
                div_event_status_know_more_no_more_button_badges_container.appendChild(div_event_status_know_more_no_more_button_badges_row);
            }
            
            div_event_row.appendChild(div_event_status_know_more_no_more_button_badges_container);
        }
        
        div_event_container.appendChild(div_event_row);
    }
    
    results.appendChild(div_event_container);
    
    //TODO: store event id (parameter data_id) somewhere
    //      to be able to interact with the event later...
    
    return div_event_container;
}