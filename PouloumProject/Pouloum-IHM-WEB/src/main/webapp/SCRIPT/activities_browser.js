/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getSingleActivityDiv(data_activity) {
    var activity_div = '<div class="card" id="card_elmt_'+data_activity.id+'">\n'
                        +'<div class="card-header" id="head_elmt_'+data_activity.id+'">\n'
                        +'<div class="row w-100">\n'
                        +'<div class="col">\n'
                        +    '<h2 class="mb-0">';
    if (data_activity.children.length===0)
    {
        activity_div +=          '<button class="btn btn-link disabled" type="button" data-toggle="collapse" data-target="#" aria-expanded="true" >';
    } else {
        activity_div +=          '<button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#elmt_'+data_activity.id+'" aria-expanded="false" aria-controls="elmt_'+data_activity.id+'">'
    }
    activity_div +=                data_activity.name
                        +        '</button>'
                        +    '</h2>'
                        +'</div>'
                        +'<div class="col">'
                        +'<a href="activity_details.html?activityID='+data_activity.id+'">'
                        +'<button class="btn btn-link collapsed float-sm-right" type="button" data-toggle="collapse" aria-expanded="false">'
                        +'Détails'
                        +'</button></a>'
                        +'</div>\n'
                        +'</div>\n'
                        +'</div>';
                
    if(data_activity.children.length!==0){
        activity_div += '<div id="elmt_'+data_activity.id+'" class="collapse" aria-labelledby="head_elmt_'+data_activity.id+'" data-parent="#card_elmt_'+data_activity.id+'">'
                        +    '<div class="card-body">';
                
        for (var children_index = 0 ; children_index < data_activity.children.length; children_index++)
        {
            activity_div += getSingleActivityDiv(data_activity.children[children_index]);
        }

        activity_div +=      '</div>'
                       +'</div>';
                        
    }
    activity_div += '</div><!--#card_elmt_'+data_activity.id+'-->';
    return activity_div;
}

function getAllActivityDiv(data_activities) {
    var full_div = '';
    for (var activity_index = 0; activity_index < data_activities.length; activity_index++) {
        var activity_div = getSingleActivityDiv(data_activities[activity_index]);
        full_div = full_div + activity_div;
    }    
    return full_div;
}
