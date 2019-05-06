/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getSingleActivityDiv(data_activity,activity_index) {
    var activity_div = '<div class="card" id="card_elmt_'+activity_index+'">'
                        +'<div class="card-header" id="head_elmt_'+activity_index+'">'
                        +    '<h2 class="mb-0">'
                        +        '<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#elmt_'+activity_index+'" aria-expanded="false" aria-controls="elmt_'+activity_index+'">'
                        +           data_activity
                        +        '</button>'
                        +    '</h2>'
                        +'</div>'
                        +'<div id="elmt_'+activity_index+'" class="collapse show" aria-labelledby="head_elmt_'+activity_index+'" data-parent="#card_elmt_'+activity_index+'">'
                        +    '<div class="card-body">'
                        +        'Such Kek.'
                        +    '</div>'
                        +'</div>'
                    +'</div><!--#card_elmt_'+activity_index+'-->';
    return activity_div;
}

function test(data_activity,activity_index) {
    var activity_div = '<div class="card" id="card_elmt_'+activity_index+'">'
                        +'<div class="card-header" id="head_elmt_'+activity_index+'">'
                        +    '<h2 class="mb-0">'
                        +        '<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#elmt_'+activity_index+'" aria-expanded="false" aria-controls="elmt_'+activity_index+'">'
                        +           data_activity
                        +        '</button>'
                        +    '</h2>'
                        +'</div>'
                        +'<div id="elmt_'+activity_index+'" class="collapse show" aria-labelledby="head_elmt_'+activity_index+'" data-parent="#card_elmt_'+activity_index+'">'
                        +    '<div class="card-body">';
    
    activity_div =          '</div>'
                        +'</div>'
                    +'</div><!--#card_elmt_'+activity_index+'-->';
    return activity_div;
}

function getAllActivityDiv(data_activities) {
    var full_div = '';
    for (var activity_index = 0; activity_index < 80; activity_index++) {
        console.log(data_activities[activity_index]);
        if (data_activities[activity_index].parent==="")
        {
            var activity_div = test(data_activities[activity_index].name,data_activities[activity_index].id);
            full_div = full_div + activity_div;
        }
    }    
    return full_div;
}
