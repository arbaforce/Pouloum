
function page_clear_events() {
    noResult();
}

function page_add_events(events, limit) {
    var count = 0;
    for (var ei=0; ei<events.length; ei++) {
        if (events[ei].event == null)
            var event = events[ei];
        else
            var event = events[ei].event;
        
        var data_id = event.id;
        var data_label = event.label;
        /// data_description = event.description;
        /// data_p_min = event.participants_min;
        /// data_p_max = event.participants_max;
        
        // var data_cancelled = event.cancelled;
        // console.log(data_cancelled);
        // if (data_cancelled) continue;
        
        var tmp_a = event.activity;
        var data_activities = [{
            name: tmp_a.name,
            url: "./activity_details.html?activityID=" + tmp_a.id,
            level: "débutant" //FIXME
        }];
        var tmp_bs = tmp_a.badges;
        var data_badges = [];
        for (var i=0; i<tmp_bs.length; i++) {
            var tmp_b = tmp_bs[i];
            var name = tmp_b.type + " ";
            name = name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
            data_badges.push(name.trim());
        }
        
        var tmp_ps = event.participants;
        var data_participants = [];
        for (var i=0; i<tmp_ps.length; i++) {
            var tmp_p = tmp_ps[i];
            data_participants.push({
                url: "./consult_other_user_profile.html?id=" + tmp_p.id,
                img: "https://static.lexpress.fr/medias_11568/w_2048,h_1146,c_crop,x_0,y_160/w_480,h_270,c_fill,g_north/v1509975901/panda-chine_5923268.jpg", //FIXME
                alt: "Profil de " + tmp_p.nickname
            });
        }
        
        var tmp_o = event.pouloumer;
        var data_organizer = {
            name: tmp_o.nickname,
            url: "./consult_other_user_profile.html?id=" + tmp_o.id
        };
        
		var tmp_a = event.address;
        var data_place = tmp_a.number.trim() + " " + tmp_a.street.trim() + " " + tmp_a.city.trim();
		
        var data_start = event.startDate;
        var data_duration = event.duration;
        var data_status = event.statut;
        // cancelled | finished | started  | full  | ready | organized
        // annulé    | terminé  | commencé | plein | prêt  | proposé
        var data_eventurl = "";
        
        /*
        var tmp_cs = event.comments;
        console.log("=== COMMENTS ===");
        console.log(tmp_cs);
        */
        
        var data_grades = {}; //FIXME
        
        addResult(data_id, data_label, data_activities, data_organizer, data_place, data_start, data_duration, data_participants, data_status, data_eventurl, data_grades, data_badges);
        
        count++;
        if (count >= 100) break;
    }
}
