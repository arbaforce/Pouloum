
function addResultDemo() {
    data_badges = ["Wind", "Fire"];
    data_grades = {
        sad : "STYLE/sad.png",
        neutral : "STYLE/pokerface.png",
        happy : "STYLE/happy.png"
    };
    
    
    data_participants = [];
    for (var i=0; i<14; i++) {
        data_participants.push({
            url:"http://localhost:8080/Pouloum-IHM-WEB/my_profile.html",
            img:"https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg",
            alt:"Profil"
        });
    }
    
    addResult(0,"",[{name:"Basketball", url:"", level:"débutant"}], {name:"Claude", url:""}, "Tête d'Or", "aujourd'hui", "une semaine", data_participants, "rempli", "", data_grades, data_badges);
    addResult(0,"",[{name:"Basketball", url:"", level:"débutant"}], {name:"Claude", url:""}, "Tête d'Or", "aujourd'hui", "une semaine", data_participants, "rempli", "", data_grades, data_badges);
    addResult(0,"",[{name:"Basketball", url:"", level:"débutant"}], {name:"Claude", url:""}, "Tête d'Or", "aujourd'hui", "une semaine", data_participants, "rempli", "", data_grades, data_badges);
}

function addResult(data_id, data_label, data_activities, data_organizer, data_place, data_date, data_duration, data_participants, data_status, data_eventurl, data_grades, data_badges) {
    var results = document.getElementById("search_result_list_global_container");
    
    //TODO: show event label (parameter data_label) somewhere
    
    var div_event_container = document.createElement("div");
    div_event_container.className = "container w-100 border";
    {
        var div_event_row = document.createElement("div");
        div_event_row.className = "row mx-1 px-1";
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

                                    var img_participant = document.createElement("img");
                                    img_participant.className = "little_avatar_image rounded";
                                    img_participant.src = data_participant.img;
                                    img_participant.alt = data_participant.alt;
                                      
                                    div_users_row.appendChild(img_participant);
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
            
            var div_event_time_evaluation_title_container = document.createElement("div");
            div_event_time_evaluation_title_container.className = "container";
            {
                var div_event_time_evaluation_title_row = document.createElement("div");
                div_event_time_evaluation_title_row.className = "row mx-0 px-0";
                {
                    var div_event_time_col = document.createElement("div");
                    div_event_time_col.className = "col-6 mx-0 my-0 px-0 py-0";
                    {
                        var txt_duration = document.createTextNode("Durée : ");
                        div_event_time_col.appendChild(txt_duration);
                        
                        var value_duration = document.createTextNode(data_duration);
                        div_event_time_col.appendChild(value_duration);
                        
                    }
                    
                    div_event_time_evaluation_title_row.appendChild(div_event_time_col);
                    
					/*
                    var var_event_evaluation_title_col = document.createElement("div");
                    var_event_evaluation_title_col.className ="col-6 mx-0 my-0 px-0 py-0";
                    {
                        var txt_evaluation = document.createTextNode("Note : ");
                        var_event_evaluation_title_col.appendChild(txt_evaluation);
                    }
                    div_event_time_evaluation_title_row.appendChild(var_event_evaluation_title_col);
					*/
                }
                div_event_time_evaluation_title_container.appendChild(div_event_time_evaluation_title_row);
                
            }
            div_event_row.appendChild(div_event_time_evaluation_title_container);
            
            var div_event_status_know_more_evaluation_button_badges_container = document.createElement("div");
            div_event_status_know_more_evaluation_button_badges_container.className = "container event_status_know_more_evaluation_button_badges_container";
            {
                var div_event_status_know_more_evaluation_button_badges_row = document.createElement("div");
                div_event_status_know_more_evaluation_button_badges_row.className = "row mx-0 px-0";
                {
                    var div_event_status_know_more_col = document.createElement("div");
                    div_event_status_know_more_col.className = "col-6 event_statut_know_more_col mx-0 px-0";
                    {
                        var div_event_status_know_more_container = document.createElement("div");
                        div_event_status_know_more_container.className = "container";
                        {
                            var div_event_status_row = document.createElement("div");
                            div_event_status_row.className = "row mx-0 px-0";
                            {
                                var txt_statuses = document.createTextNode("Statut : ");
                                
                                div_event_status_row.appendChild(txt_statuses);
                                
                                var txt_status = document.createTextNode(data_status + ".");
                                
                                div_event_status_row.appendChild(txt_status);
                            }
                            
                            div_event_status_know_more_container.appendChild(div_event_status_row);
                            
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

                            div_event_status_know_more_container.appendChild(div_event_know_more_row);
                        }
                        
                        div_event_status_know_more_col.appendChild(div_event_status_know_more_container);
                    }
                    
                    div_event_status_know_more_evaluation_button_badges_row.appendChild(div_event_status_know_more_col);
                    
					/*
                    var div_event_evaluation_button_col = document.createElement("div");
                    div_event_evaluation_button_col.className = "col mx-0 px-0";
                    {
                        var radio_button_sad_label = document.createElement("label");
                        {
                           var input_sad = document.createElement("input");
                           input_sad.setAttribute("type", "radio");
                           input_sad.setAttribute("name", "test");
                           input_sad.setAttribute("value", "sad");
                           
                           radio_button_sad_label.appendChild(input_sad);
                           
                           var img_sad = document.createElement("img");
                           img_sad.className = "radio_button_image";
                           img_sad.src = data_grades.sad;
                           img_sad.alt = ":(";
                           
                           radio_button_sad_label.appendChild(img_sad);
                        }
                        
                        div_event_evaluation_button_col.appendChild(radio_button_sad_label);
                        
                        var radio_button_neutral_label = document.createElement("label");
                        {
                           var input_neutral = document.createElement("input");
                           input_neutral.setAttribute("type", "radio");
                           input_neutral.setAttribute("name", "test");
                           input_neutral.setAttribute("value", "neutral");
                           
                           radio_button_neutral_label.appendChild(input_neutral);
                           
                           var img_neutral = document.createElement("img");
                           img_neutral.className = "radio_button_image";
                           img_neutral.src = data_grades.neutral;
                           img_neutral.alt = ":|";
                           
                           radio_button_neutral_label.appendChild(img_neutral);
                        }
                        
                        div_event_evaluation_button_col.appendChild(radio_button_neutral_label);
                        var radio_button_happy_label = document.createElement("label");
                        {
                           var input_happy = document.createElement("input");
                           input_happy.setAttribute("type", "radio");
                           input_happy.setAttribute("name", "test");
                           input_happy.setAttribute("value", "happy");
                           
                           radio_button_happy_label.appendChild(input_happy);
                           
                           var img_happy = document.createElement("img");
                           img_happy.className = "radio_button_image";
                           img_happy.src = data_grades.happy;
                           img_happy.alt = ":(";
                           
                           radio_button_happy_label.appendChild(img_happy);
                        }
                        
                        div_event_evaluation_button_col.appendChild(radio_button_happy_label);
                    }
                    
                    div_event_status_know_more_evaluation_button_badges_row.appendChild(div_event_evaluation_button_col);
                  	*/
                    
                    var div_event_badges_col = document.createElement("div");
                    div_event_badges_col.className = "col mx-0 px-0";
                    {
                        var div_event_badges_container = document.createElement("div");
                        div_event_badges_container.className = "container";
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
                    
                    div_event_status_know_more_evaluation_button_badges_row.appendChild(div_event_badges_col);
                }
                
                div_event_status_know_more_evaluation_button_badges_container.appendChild(div_event_status_know_more_evaluation_button_badges_row);
            }
            
            div_event_row.appendChild(div_event_status_know_more_evaluation_button_badges_container);
        }
        
        div_event_container.appendChild(div_event_row);
    }
    
    results.appendChild(div_event_container);
    
    //TODO: store event id (parameter data_id) somewhere
    //      to be able to interact with the event later...
    
    return div_event_container;
}




