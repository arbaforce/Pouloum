var csscode =  `

.newnavbar {
    display:flex;
    flex-wrap: wrap;
    justify-content: space-around;
    align-items: center;
    background-image : url('STYLE/sky-8.png');
    /*background-color: #f8f9fa;*/
}

.newnavbar a {
    color: black;
}

`;

var htmlcode = `
            <div class="newnavbar w-100" id="navbar">
                <div id="navbar_brand_column" class="nav-link font-weight-bold text-center">
                    <img href="inde.html" src="STYLE/logo-1.png" style="max-width: 2em; max-height: 2em;"/>
                </div> <!--#navbar_brand_column-->
                <div>
                    <a class="nav-link font-weight-bold text-center" href="home_page.html">
                        Accueil
                    </a>
                </div>
                <div>
                    <a class="nav-link dropdown-toggle font-weight-bold text-center" href="#" id="navbar_profile_dropdown_links" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Profil
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbar_profile_dropdown_links">
                        <a class="dropdown-item font-weight-bold text-center" href="my_profile.html">
                            Mon Profil
                        </a>
                        <a class="dropdown-item font-weight-bold text-center" href="profile_history.html">
                            Historique
                        </a>
                        <a class="dropdown-item font-weight-bold text-center" href="profile_relations.html">
                            Relations  
                        </a>
                    </div>
                </div>
                <div>
                    <a class="nav-link font-weight-bold text-center" href="events_browser.html">
                        Chercher
                    </a>
                </div>
                <div>
                    <a class="nav-link dropdown-toggle font-weight-bold text-center" href="#" id="navbar_organize_dropdown_links" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Organiser
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbar_organize_dropdown_links">
                        <a class="dropdown-item font-weight-bold text-center" href="modify_event.html?create=1&idEvent=null">
                            Nouvel Evènement
                        </a>
                        <a class="dropdown-item font-weight-bold text-center" href="list_modify_event.html">
                            Modifier
                        </a>
                    </div>
                </div>
                <div>
                    <a class="nav-link font-weight-bold text-center" href="activities_browser.html">
                        Activité
                    </a>
                </div>
                <div>
                    <div class="nav-link font-weight-bold text-center">
                        <a href="index.html"><img href="inde.html" src="IMG/navbar_logout_icon.png" style="max-width: 1.5em; max-height: 1.5em;"/></a>
                    </div>
                </div>
            </div> <!--#navbar-->
`;



var node = document.currentScript;
var parent = node.parentNode;

var css = document.createElement('style');
css.innerHTML = csscode;
parent.insertBefore(css, node);

var html = document.createElement('div');
html.innerHTML = htmlcode;
parent.insertBefore(html, node);

for (var innerElem; innerElem = html.firstChild;) {
    parent.insertBefore(innerElem, html);
}
parent.removeChild(html);

parent.removeChild(node);
