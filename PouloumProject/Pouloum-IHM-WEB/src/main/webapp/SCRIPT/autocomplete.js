//Source : https://www.w3schools.com/howto/howto_js_autocomplete.asp

function autocomplete(inp, div, arr, rech) {
    /*the autocomplete function takes two arguments,
	  the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;
        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("UL");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "list-group list-autocomplete-items");
        /*for each item in the array...*/
        var text;
        for(var[key,value] of arr){
            /*check if the item starts with the same letters as the text field value:*/
            //if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
            var stringIndex = value.toUpperCase().indexOf(val.toUpperCase());
            if (stringIndex > -1) {
                /*create a DIV element for each matching element:*/
                b = document.createElement("LI");
                b.setAttribute("id", i + "-list-items");
                b.setAttribute("value", key);
                b.setAttribute("class", "list-group-item autocomplete-items");
                /*make the matching letters bold:*/
                text = value.substring(0, stringIndex) ;
                text += "<strong>" + value.substring(stringIndex, stringIndex + val.length) + "</strong>";
                text += value.substring(stringIndex + val.length);
                /*insert a input field that will hold the current array item's value:*/
                text += "<input type='hidden' value='" + value + "'>";
                b.innerHTML = text;
                /*execute a function when someone clicks on the item value (DIV element):*/
                b.addEventListener("click", function(e) {
                    /*insert the value for the autocomplete text field and go to the right page:*/
                    inp.value = this.getElementsByTagName("input")[0].value;
                    rech(this.value);
                    
                    /*close the list of autocompleted values,
	                or any other open lists of autocompleted values:*/
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
        /*append the DIV element as a child of the autocomplete container:*/
        div.appendChild(a);
    });
    
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
	        increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
	        decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x) x[currentFocus].click();
            }
        }
    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
	    except the one passed as an argument:*/
        var x = document.getElementsByClassName("list-autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
	    }
        }
    }
    
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}
