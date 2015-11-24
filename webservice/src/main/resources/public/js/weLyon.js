function WeLyon(){

	var self = this;
	var category = new Category();
	var mapManager = new MapManager();
	var pin = new Pin();
	var user = new User();
	var messageView = new MessageView();
	var idUser;
	var nameUser;
	var pinTest=new Pin();
	var dernier_id = 1;
	var levelofSearch=0;
	var pos = null;

//TODO initialisation par rapport aux droits d'utilisateur

//------------Les setups des pages/panels et ses boutons------------------
	self.setup = function(){
		localStorage.clear();
		self.setUser();		
		self.fillCategories();
		self.initialiserCarte();		

		$('#newEventButton').on('click', function(){
			self.ajouterEvenemment($(this));	
		});

		$('#onFireButton').on('click', function(){
			self.gererVisibilite($(this));
		});

		$('#allButton').on('click', function(){
			self.gererVisibilite($(this));
		});

		$('#freshButton').on('click', function(){
			self.gererVisibilite($(this));
		});

		$('#searchButton').on('click', function(){
			self.gererVisibilite($(this));
		});

		$('#sendSearch').on('click', function(){
			var search = $('#searchInput').find('input').val();
			mapManager.getPinBySearch(search);
		});

		$('#dateFilterButton').on('click', function(){
			self.remplirFiltreDate();
			$('#dateFilterForm').toggle();
			$('#categoryFilter').toggle();
		});

		$('#sendDateFilter').on('click', function(){			
			mapManager.filterByDate()	
		});

		$('#categoryFilterButton').on('click', function(){
			$('#categoryFilter').toggleClass('open-filter');
			$('#categoryFilterTree').toggle();
		})

		$('#signinButton').on('click', function(){
			self.ouvrirPanelAuthentification($(this));
		});

		$('#connectButton').on('click', function(){
			self.ouvrirPanelAuthentification($(this));
		});

		$('#disconnectButton').on('click', function(){
			self.setUser();
		});

		$('#filterByDateButton').on('click', function(){
			mapManager.filterByDate();
		});


		$(".finalInput").keypress(function(event) {
			if (event.which == 13) {
				event.preventDefault();
				$(this).parent().find('.valider').click();
			}
		});


		$('#map-container').css('height', (window.innerHeight-50)+'px');
		$(window).resize(function() {
			$('#map-container').css('height', (window.innerHeight-50)+'px');
		});

		//setInterval(self.chargerNews,4000);
	};


	self.setupAuthentificationPanel = function(bouton){

		$('#okInscription').on('click', function(){
			self.signInUser($(this));
			bouton.toggleClass('active');
			$('#inscriptionPanel').hide()
		});

		$('#okConnexion').on('click', function(){
			self.signUpUser($(this));
			bouton.toggleClass('active');
			$('#inscriptionPanel').hide();
		});

		$('.annuler').on('click', function(){
			bouton.toggleClass('active');
			$('#inscriptionPanel').hide();
		});

		$(".finalInput").keypress(function(event) {
			if (event.which == 13) {
				event.preventDefault();
				$(this).closest('form').find('.valider').click();
			}
		});

		$(document).keyup(function(event) {
			if (event.which == 27) {
				event.preventDefault();
				if ($('#inscriptionPanel').is(":visible")){
					$('#inscriptionPanel').find('.annuler').click();
				}				
			}
		});
	};

	self.setupNewPointForm = function(buttonData){

		$(".annuler").on('click', function(){
			$('#formulaireAjoutPoint').modal('hide');
		});		

		$(".retour").on('click', function(){
			self.fillNewPointForm('new-point');			
		});

		switch	(buttonData){
			case "new-point":
				$(".pointType").on('click', function(){
					$(this).parent().find('.active').toggleClass('active');
					$(this).toggleClass('active');			
				});

				$(".pointType").dblclick(function(){
					$(this).click();
					$("#validePointType").click();
				});

				$("#validePointType").on('click', function(){
					buttonData = $('#formulaireAjoutPoint').find('.active').data('form-type');
					self.fillNewPointForm(buttonData);			
				});

				break;

			case "place":			
				self.getCategories(self.cbFillCatLieu);

				$('#valideAjoutLieu').on('click', function(){
					self.ajouterEvent();
					messageView.append(Messages.Point.NEW_PLACE_SUCCESS,$('#placeName').val());					
					$('#formulaireAjoutPoint').modal('hide');
					messageView.show();
				});			
				break;

			case "event":
				self.getCategories(self.cbFillCatEvent);
				self.remplirFormEventDate();

				$('#valideAjoutEvent').on('click', function(){
					self.ajouterLieu();
					messageView.append(Messages.Point.NEW_EVENT_SUCCESS,$('#eventName').val());					
					$('#formulaireAjoutPoint').modal('hide');
					messageView.show();	
				});
				break;

		}

	};

	self.cbAddPin=function(data){
		if (data['error']==null){
			aPin = data.pin;
			mapManager.addMarker(aPin);

			//mapManager.markers.get(aPin.id).marker.setAnimation(google.maps.Animation.BOUNCE);
			// setTimeout(function () {
			// 	markers.get(aPin.id).marker.setAnimation(null);
			// },2250);
		}
	};


	self.initialiserCarte = function(){			
		google.maps.event.addDomListener(window, 'load', mapManager.initMap());	
		$('[data-toggle="tooltip"]').tooltip();	
		self.gererVisibilite($('#onFireButton'));
		$('#optionsCarte').show();
		messageView.install('appAlert');
	};

//--------------Remplissage des formulaires----------------------
	self.fillAuthentificationForm = function(bouton){
		$("#inscriptionPanel").find(".panel-body").find('form').remove();
		
		var form = '';
		if(bouton.get(0) === $('#signinButton').get(0)){
			form+='	   <form>';
	        form+='        <div class="form-group">';
	        form+='            <label for="inscrirePseudo">Pseudo</label>';
	        form+='            <input type="text" class="form-control" id="inscrirePseudo1" placeholder="Pseudo">';
	        form+='        </div>';
	        form+='        <div class="form-group">';
	        form+='            <label for="inscrireEmail">Email</label>';
	        form+='            <input type="email" class="form-control" id="inscrireEmail1" placeholder="Entrez votre email">';
	        form+='        </div>';
	        form+='        <div class="form-group">';
	        form+='            <label for="inscrireMdP">Mot de Passe</label>';
	        form+='           <input type="password" class="form-control" id="inscrireMdP1" placeholder="Mot de Passe">';
	        form+='        </div>';
	        form+='        <div class="form-group">';
	        form+='            <label for="confirmerMdP">Confirmer mot de passe</label>';
	        form+='            <input type="password" class="form-control finalInput" id="confirmerMdP1" placeholder="Confirmez votre mot de passe">';
	        form+='        </div>';
	        form+='        <button id="annulerInscription" type="button" class="btn btn-danger pull-left annuler">Annuler</button>';
	        form+='        <button  id="okInscription" type="button" class="btn btn-default pull-right valider">S\'inscrire</button>';
	        form+='    </form>';
		}
		else if(bouton.get(0) === $('#connectButton').get(0)){
			form+='	   <form>';
	        form+='        <div class="form-group">';
	        form+='            <label for="inscrirePseudo">Pseudo</label>';
	        form+='            <input type="text" class="form-control" id="inscrirePseudo2" placeholder="Pseudo">';
	        form+='        </div>';
	        form+='        <div class="form-group">';
	        form+='            <label for="inscrireMdP">Mot de Passe</label>';
	        form+='           <input type="password" class="form-control finalInput" id="inscrireMdP2" placeholder="Mot de Passe">';
	        form+='        </div>';
	        form+='        <button id="annulerConnexion" type="button" class="btn btn-danger pull-left annuler">Annuler</button>';
	        form+='        <button  id="okConnexion" type="button" class="btn btn-default pull-right valider">Connexion</button>';
	        form+='    </form>';
		}

		$("#inscriptionPanel").find(".panel-body").append(form);
		self.setupAuthentificationPanel(bouton);
	};

	self.fillNewPointForm = function(buttonData){
		$('#formulaireAjoutPoint').find('.modal-content').html("");
		var form = "";

		switch	(buttonData){
			case "new-point":
				form+=' <div class="modal-header">';
                form+='            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                form+='           <h3 class="modal-title">Choisissez un type</h3>';
                form+='        </div>';
                form+='        <div class="modal-body"> ';                            
                form+='            <div id="pointTypeButtons" class="row col-md-offset-1 text-center">';                             
                form+='                <button id="pointTypeEventButton" type="button" data-form-type="event" class="btn btn-default col-md-5 pointType active">Événement</button>';
                form+='                <button id="pointTypePlaceButton"  type="button" data-form-type="place" class="btn btn-default col-md-5  pointType">Lieu</button>';
                form+='            </div>';
                form+='        </div>';
                form+='        <div class="modal-footer">';
                form+='            <button type="button" class="btn btn-default pull-left annuler" data-dismiss="modal">Close</button>';
                form+='            <button type="button" class="btn btn-primary valider" id="validePointType"> Suivant</button>';
                form+='        </div>';
				break;

			case "place":
					form+='	<div class="modal-content">';
	                form+='        <div class="modal-header">';
	                form+='           <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	                form+='            <h3 class="modal-title">Ajout d\'un lieu</h3>';
	                form+='        </div>';
	                form+='        <div class="modal-body">';
	                form+='            <form class="form-horizontal">';
	                form+='                <div class="form-group">';
	                form+='                    <label for="placeName" class="col-sm-2 control-label">Nom</label>';
	                form+='                    <div class="col-sm-10">';
	                form+='                        <input type="text" class="form-control" id="placeName" placeholder="Nom du lieu">';
	                form+='                    </div>';
	                form+='                </div>';
	                form+='                <div class="form-group">';
	                form+='                    <label for="placeDescription" class="col-sm-2 control-label">Description</label>';
	                form+='                    <div class="col-sm-10">';
	                form+='                        <textarea id="placeDescription" class="form-control" rows="3" placeholder="Description du lieu"></textarea>';
	                form+='                    </div>';
	                form+='                </div>';
	                form+='                <div class="form-group">';
	                form+='                    <div class="col-sm-10 pull-right">';
	                form+='                        <select id="placeCategories" class="form-control">';
	                form+='                        </select>';
	                form+='                    </div>';
	                form+='                </div>';
	                form+='            </form>';
	                form+='        </div>';
	                form+='        <div class="modal-footer">';
	                form+='            <button type="button" class="btn btn-default annuler pull-left " data-dismiss="modal">Close</button>';
	                form+='            <button type="button" class="btn btn-info retour" id="retourAjoutLieu"> Précédent</button>';
	                form+='            <button type="button" class="btn btn-primary valider" id="valideAjoutLieu"> Valider</button>';
	                form+='        </div>';
	                form+='    </div>';				
				break;

			case "event":
					form+='		<div class="modal-header">';
                    form+='            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    form+='            <h3 class="modal-title">Ajout d\'un événement</h3>';
                    form+='        </div>';
                    form+='        <div class="modal-body">';
                    form+='            <form class="form-horizontal">';
                    form+='                <div class="form-group">';
                    form+='                    <label for="eventName" class="col-sm-2 control-label">Nom</label>';
                    form+='                    <div class="col-sm-10">';
                    form+='                        <input type="text" class="form-control" id="eventName" placeholder="Nom de l\'événement">';
                    form+='                    </div>';
                    form+='                </div>';
                    form+='                <div class="form-group">';
                    form+='                   <label for="eventDescription" class="col-sm-2 control-label">Description</label>';
                    form+='                    <div class="col-sm-10">';
                    form+='                        <textarea id="eventDescription" class="form-control" rows="3" placeholder="Description de l\'événement"></textarea>';
                    form+='                    </div>';
                    form+='                </div>';
                    form+='                <div class="form-group">';
                    form+='                    <div class="col-sm-10 pull-right">';
                    form+='                        <select id="eventCategories" class="form-control">';
                    form+='                        </select>';
                    form+='                    </div>';
                    form+='                </div>';
                    form+='                <div class="row">';
                    form+='                    <div class="col-md-6">';
                    form+='                        <label for="dateFormBegin">Date début:</label>   ';                             
                    form+='                        <div id="dateFormBegin" class="form-inline">';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventDayBegin" class="form-control">';
                    form+='                                </select>';
                    form+='                            </div>';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventMonthBegin" class="form-control">';
                    form+='                                </select> ';
                    form+='                            </div>';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventYearBegin" class="form-control finalInput">';
                    form+='                                </select>';
                    form+='                            </div>   ';                                                                            
                    form+='                        </div> ';
                    form+='                    </div>';
                    form+='                    <div class="col-md-6">';
                    form+='                        <label for="dateEventEnd">Date fin:</label>';
                    form+='                        <div id="dateEventEnd" class="form-inline">';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventDayEnd" class="form-control">';
                    form+='                                </select>';
                    form+='                            </div>';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventMonthEnd" class="form-control">';
                    form+='                                </select> ';
                    form+='                            </div>';
                    form+='                            <div class="form-group col-md-5">';
                    form+='                                <select id="dateEventYearEnd" class="form-control finalInput">';
                    form+='                                </select>';
                    form+='                            </div> ';                                                                              
                    form+='                        </div>';
                    form+='                    </div>';
                    form+='                </div>';
                    form+='            </form>';
                    form+='        </div>';
                    form+='        <div class="modal-footer">';
                    form+='            <button type="button" class="btn btn-default annuler pull-left " data-dismiss="modal">Close</button>';
                    form+='            <button type="button" class="btn btn-info retour" id="retourAjoutEvent"> Précédent</button>';
                    form+='           <button type="button" class="btn btn-primary valider" id="valideAjoutEvent"> Valider</button>';
                    form+='        </div>';
				break;
		}

		$('#formulaireAjoutPoint').find('.modal-content').append(form);
		self.setupNewPointForm(buttonData);
		$('#formulaireAjoutPoint').modal('show');

	};	

	self.remplirFiltreDate = function(){
		var date = self.remplirDate();

		$('#dateFilterDayBegin').html('');
		$('#dateFilterMonthBegin').html('');
		$('#dateFilterYearBegin').html('');
		$('#dateFilterDayEnd').html('');
		$('#dateFilterMonthEnd').html('');
		$('#dateFilterYearEnd').html('');

		$('#dateFilterDayBegin').append(date.jour);
		$('#dateFilterMonthBegin').append(date.mois);
		$('#dateFilterYearBegin').append(date.annee);
		$('#dateFilterDayEnd').append(date.jour);
		$('#dateFilterMonthEnd').append(date.mois);
		$('#dateFilterYearEnd').append(date.annee);		
	};

	self.remplirFormEventDate = function(){
		var date = self.remplirDate();

		$('#dateEventDayBegin').html('');
		$('#dateEventMonthBegin').html('');
		$('#dateEventYearBegin').html('');
		$('#dateEventDayEnd').html('');
		$('#dateEventMonthEnd').html('');
		$('#dateEventYearEnd').html('');

		$('#dateEventDayBegin').append(date.jour);
		$('#dateEventMonthBegin').append(date.mois);
		$('#dateEventYearBegin').append(date.annee);
		$('#dateEventDayEnd').append(date.jour);
		$('#dateEventMonthEnd').append(date.mois);
		$('#dateEventYearEnd').append(date.annee);
	};

	self.remplirDate = function(){
		var date = {
			jour:"",
			mois:"",
			annee:""
		}

		date.jour ='<option value="" disabled selected>Jour</option>';
		date.mois = '<option value="" disabled selected>Mois</option>';
		date.annee = '<option value="" disabled selected>Année</option>';

		for( var i=1; i<=31; i++){
			date.jour+=' <option value="'+i+'">'+i+'</option>';
		}
		for( var i=1; i<=12; i++){
			date.mois+=' <option value="'+i+'">'+i+'</option>';
		}
		for( var i=2015; i<=2020; i++){
			date.annee+=' <option value="'+i+'">'+i+'</option>';
		}

		return date;
	};

//----------------- Getters/Setters ------------------
	self.getCategories = function(callback){
		category.getCategories(callback);
	};

	self.fillCategories = function(){
		self.getCategories(self.cbFillCat);
	};

	self.setUser = function(id,nom){
		if(typeof(Storage) !== "undefined") {
			//premiere connexion
			if((typeof nameUser ===  "undefined" )|| (typeof idUser === "undefined")){
				idUser = localStorage.getItem('idUser');
				nameUser = localStorage.getItem('nameUser');	
			}			
			// localStorage vide
			if ((nameUser ===  null )|| (idUser === null)){
				nameUser='Anonyme';
				idUser=-1;
			}
			// connexion
			else if((typeof id !== "undefined") || (typeof nom !== "undefined")){
				idUser = id;
				nameUser = nom;
				$('#connectedUser').find('i').html(" "+nom);
				self.toggleBoutonsConnexion();	
			}
			// deconnexion
			else {
				nameUser='Anonyme';
				idUser=-1;
				mapManager.setIdUser(idUser)
				self.toggleBoutonsConnexion();
			}
			localStorage.setItem('idUser',idUser);
			localStorage.setItem('nameUser',nameUser);
		} else {
		    messageView.append(Messages.Navigator.WEB_STORAGE_ERROR);
		}		
	};

//---------------- Callbacks ------------------------ 
	self.cbFillCat = function (data) {
		mapManager.setListCategories(data.categories);
		var dataTree = self.transformToTreeFormat(data.categories , 0);

    	$('#categoryTreeView').fancytree({
    		extensions: ["glyph", "edit", "wide"],
    		autoActivate:false,
          	checkbox : true,
          	selectMode : 3,
          	source : dataTree,
          	select: function(event, data){
       			self.filterCategory(event,data);
			},
			toggleEffect: { effect: "drop", options: {direction: "left"}, duration: 400 },
			glyph: {
		        map: {
		          	doc: "glyphicon glyphicon-file",
		          	docOpen: "glyphicon glyphicon-file",
		          	checkbox: "glyphicon glyphicon-unchecked",
		          	checkboxSelected: "glyphicon glyphicon-check",
		          	checkboxUnknown: "glyphicon glyphicon-share",
		          	error: "glyphicon glyphicon-warning-sign",
		          	expanderClosed: "glyphicon glyphicon-plus-sign",
		          	expanderLazy: "glyphicon glyphicon-plus-sign",
		          	// expanderLazy: "glyphicon glyphicon-expand",
		          	expanderOpen: "glyphicon glyphicon-minus-sign",
		          	// expanderOpen: "glyphicon glyphicon-collapse-down",
		          	folder: "glyphicon glyphicon-folder-close",
		          	folderOpen: "glyphicon glyphicon-folder-open",
		          	loading: "glyphicon glyphicon-refresh"
		          	// loading: "icon-spinner icon-spin"
		        }
		    }
        });
    
	};


	self.transformToTreeFormat = function (data , father) {
		var result = [];
		for (var i = 0; i<data.length; i++) {
			if (father != 0 ) {
				if (data[i].idFather && data[i].idFather == father ) {
					var node = { 
						title : data[i].nom,
						folder : false,
						expanded : false,
						key : data[i].id,
						icons : false,
						selected : true						 
					}
					var nodes = self.transformToTreeFormat(data , data[i].id)
					if (nodes.length != 0 ) {
						node.children = nodes
					}
					result.push(node)
				} 
			}
			else {
				if(!data[i].idFather) {
					var node = { 
						title : data[i].nom,
						folder : false,
						expanded : false,
						key : data[i].id,
						selected:true
  					}
					var nodes = self.transformToTreeFormat(data , data[i].id)
					if (nodes.length != 0 ) {
						node.children = nodes
					}
					result.push(node)
				}
			}
		}
		return result;
	};

	self.cbAddUser = function(data){
		if (data['error'] == null){
			messageView.append(Messages.Register.REGISTER_SUCCESS, data.nameUser);			
			self.cbAuthUser(data);
		}else {
			alert(data['error']);
		}
	};

	self.cbAuthUser = function(data){
		if (data['error'] == null){
			self.setUser(data.idUser, data.nameUser);
			mapManager.setIdUser(idUser,nameUser);
			
			if(messageView.count()==1){
				messageView.show();
			} else if(messageView.count()==0){
				messageView.append(Messages.Login.LOGIN_SUCCESS, data.nameUser);
				messageView.show();
			}
		}else{
			//TODO a voir messageView
			messageView.appendMessage(data['error'], Messages.MessageType.ERROR);
			messageView.show();
		}
		
	};

	self.cbFillCatLieu = function(data){
		var categories = '<option value="" disabled selected>Categories</option>';
		self.getFeuilleList(data.categories).forEach(function (category , index) {
			categories += '<option value="'+ category.id +' ">'+ category.nom +'</option>'
		});
		$('#placeCategories').append(categories);
	};

	self.getFeuilleList = function (categories) {
		var result = [];
		categories.forEach(function (category , index) {
			if (category.idFather) {
				var found = false;
				for(var i = 0 ; i < categories.length ; i++ ) {
					if (categories[i].idFather && category.id == categories[i].idFather ){
						found = true;
					}
				}
				if (!found && self.isLieu(category.idFather , categories )) {
					result.push(category);
				}
			}
			
		});
		return result;
	};

	self.isLieu = function (idFather , categories) {
		for (var i = 0 ; i < categories.length ; i++) {
			if ( idFather == categories[i].id ) {
				if(categories[i].father) {
					return self.isLieu(categories[i].idFather , categories)
				}
				else if ( categories[i].nom === "Lieux" ) {
					return true;
				}
			}
		}
		return false;
	}

	self.cbFillCatEvent = function(data){
		var categories = '<option value="" disabled selected>Categories</option>';
		self.getFeuilleListEvent(data.categories).forEach(function (category , index) {
			categories += '<option value="'+ category.id +' ">'+ category.nom +'</option>'
		});
		$('#eventCategories').append(categories);
	}

	self.getFeuilleListEvent = function (categories) {
		var result = [];
		categories.forEach(function (category , index) {
			if (category.idFather) {
				var found = false;
				for(var i = 0 ; i < categories.length ; i++ ) {
					if (categories[i].idFather && category.id == categories[i].idFather ){
						found = true;
					}
				}
				if (!found && self.isEvent(category.idFather , categories )) {
					result.push(category);
				}
			}
			
		});
		return result;
	};

	self.isEvent = function (idFather , categories) {
		for (var i = 0 ; i < categories.length ; i++) {
			if ( idFather == categories[i].id ) {
				if(categories[i].father) {
					return self.isLieu(categories[i].idFather , categories)
				}
				else if ( categories[i].nom === "Evenement" || categories[i].nom === "Evenement utilisateur" ) {
					return true;
				}
			}
		}
		return false;
	};

	self.ouvrirPanelAuthentification = function(bouton){
		if(bouton.hasClass('active')){
			bouton.toggleClass('active');
			$('#inscriptionPanel').toggle();
		} else {
			self.fillAuthentificationForm(bouton);
			$('#nav').find('.active').toggleClass('active');
			bouton.toggleClass('active');
			$('#inscriptionPanel').show();
		}		
		$('#inscriptionPanel').find('form:first *:input[type!=hidden]:first').focus();
	};

	self.gererVisibilite = function(bouton){		
		$('#visibilityFilter').find('.active').toggleClass('active');
		bouton.toggleClass('active');
		var visibilite = bouton.data('visibility');
		switch(visibilite){
			case 'recherche':
				$('#searchInput').show();
				$('#dateFilter').hide();
			break;
			default:
				$('#searchInput').hide();
				$('#dateFilter').show();
				mapManager.filtrerVisibilite(visibilite);
		}
	};

	self.ajouterEvenemment = function(bouton){
		messageView.append(Messages.Point.NEW_POINT_INFO);
		messageView.show();
		google.maps.event.addListenerOnce(mapManager.theMap, 'click', function(e) {
			var buttonData = bouton.data('form-type');
			self.fillNewPointForm(buttonData);
			pos = e.latLng;
		});
	};

	self.toggleBoutonsConnexion = function(){
		$('#connectButton').toggle();
		$('#signinButton').toggle();
		$('#disconnectButton').toggle();
		$('#connectedUser').toggle();
		$('#optionUtilisateur').toggle();
	};

	self.toggleCategories = function(){
		$('.category-item').toggle();
	};
		
	self.signInUser = function(bouton){
		var pseudo= $('#inscrirePseudo1').val();
		var password= $('#inscrireMdP1').val();
		var password2= $('#confirmerMdP1').val();
		var mail = $('#inscrireEmail1').val();
		if (password === password2 && pseudo!=="" && password!==""){
			user.addUser(pseudo,password,self.cbAddUser);
		} //TODO: gere avec validator
		else if (pseudo ==""){
			alert("veuillez choisir un pseudo")
		}
		else if (password == ""){
			alert("Veuillez choisir un mot de passe")
		}
		else if (password2 == ""){
			alert("Les mots de passe ne correspondent pas")
		}
	};

	self.signUpUser = function(bouton){
		var pseudo = $('#inscrirePseudo2').val();
		var password = $('#inscrireMdP2').val();
		if (password!== "" && pseudo !== ""){
			user.authUser(pseudo,password,self.cbAuthUser);
		}else if (pseudo =="" || password == ""){
			//TODO: gerer avec validator
			// alert("votre peseudo ou votre mot de passe est vide")
		}		
	};

	self.setId = function (id){
        dernier_id = id;
    }

	self.chargerNews = function (){
        mapManager.getNews('/fil/'+dernier_id+'/' , self.cbNews);
    }

    self.cbNews = function(data){            

        if(data.logs!=''){
           	$(data.logs).prependTo('#filActubox').hide().animate({'height':'toggle','opacity':'toggle'},2000);
     	   	$('#filActubox div:last-child').remove();
     	   	self.setId(data.idLog);
        }
    };

	self.filterCategory=function(event,data){
		var node = data.node;
		self.filtreCategoryNode(node);
   		levelofSearch=0
   		mapManager.zoomHandler();
	};
	self.filtreCategoryNode=function(node){
		var idCategory = node.key;
   		var param =node.isSelected();
		mapManager.categoryFilter(param,idCategory);
		if (node.children!=null && levelofSearch<2){
				levelofSearch +=1;
				for(i=0;i<node.children.length;i++){
					self.filtreCategoryNode(node.children[i]);
				}
				
		}
	};

	self.ajouterEvent = function(){
		var name = $("#placeName").val();
		var desc = $("#placeDescription").val();
		var category = $("#placeCategories").val();
		pin.addStaticPin(name, desc, idUser, pos.lat(), pos.lng(), category , self.cbAddPin);
	};

	self.ajouterLieu = function(){
		var jour_debut= $("#dateEventDayBegin").val();
		var mois_debut= $("#dateEventMonthBegin").val();
		var annee_debut=$("#dateEventYearBegin").val();
		var heure_debut=0;
		var minute_debut=0;
		var jour_fin=$("#dateEventDayEnd").val();
		var mois_fin= $("#dateEventMonthEnd").val();
		var annee_fin=$("#dateEventYearEnd").val();
		var heure_fin=12
		var minute_fin=0
		var title =$("#eventName").val();
		var category = $("#eventCategories").val();
		var description =$("#eventDescription").val();
		pin.addDynPin(title,description,idUser,jour_debut,mois_debut,annee_debut,heure_debut,minute_debut,jour_fin,mois_fin,annee_fin,heure_fin,minute_fin, pos.lat(), pos.lng(),category,self.cbAddPin);
	};
}

var weLyon = new WeLyon();
weLyon.setup();
