function Principal(){
	var self = this;
	var skill = new Skill();
	var offer = new Offer();
	var company = new Company();
	var user = new User();
    
	self.setup = function(){
		self.getSkills(self.fillSkillsCB);

		$('#searchOffers').on('click', function(){
			self.limparOfertas();
			self.searchOffers(self.searchResultCB);
		});

		$('.ratingItem').on('click', function(){
			self.toggleAtivo($(this));
		});  

		$('#userSpaceButton').on('click', function(){
			if($(this).hasClass('ativo'))
				self.fecharUserSpace();
			else
				self.abrirUserSpace();
		});

		$('#companySpaceButton').on('click', function(){
			if($(this).hasClass('ativo'))
				self.fecharCompanySpace();
			else
				self.abrirCompanySpace();
		});    
	};

	self.searchOffers = function(callback){
		var skills = self.skillsAtivos();
		var ratings = self.ratingsAtivos();
		if(skills.length>0 || ratings.length>0)
			offer.searchOffers(skills, ratings, callback);
	}

	self.getSkills = function(callback){
		skill.getSkills(callback);
	};

	self.getUsers = function(callback){
		user.getUsers(callback);
	};

	self.getUserRecommendations = function(id, callback){
		user.getRecommendations(id, callback);
	};

	self.getCompanies = function(callback){
		company.getCompanies(callback);
	};

	self.getCompanyOffers = function(companyId, callback){
		offer.findByCompanyId(companyId,callback);
	};

	self.recommendedByOffer = function (offerId, callback){
		offer.recommendedByOffer(offerId, callback);
	};

	self.fillSkillsCB = function(data){
		var skill = "";

		for(var i=0; i<data.length; i++){
			skill +='		<tr>';
	        skill +='        	<td class="skillItem" data-skill="'+data[i].name+'">'+data[i].name+'</td>';
	        skill +='        </tr>';
	        i++;
    	}

    	$("#skills").append(skill);

    	$('.skillItem').on('click', function(){
			self.toggleAtivo($(this));
		});
	};

	self.fillUsersCB = function(data){
		
		for (var i = 0; i < data.length; i++) {
			var user = '';
			user+= '<li class="list-group-item userItem" data-user-id="'+data[i].id+'">'+data[i].name+'</li>';
			$("#users").append(user);
		};		

		$('.userItem').on('click', function(){
			self.limparOfertas();
			var id = $(this).data('user-id')
			self.toggleAtivo($(this).closest('#users').find('.ativo'));
			self.toggleAtivo($(this));
			self.getUserRecommendations(id, self.searchResultCB);
		});
	
	};

	self.fillCompaniesCB = function(data){
		var company = '';
		for (var i = 0; i < data.length; i++) {
			company+= '<li class="list-group-item companyItem" data-company-id="'+data[i].id+'">'+data[i].name+'</li>';
		};
		$("#companies").append(company);

		$('.companyItem').on('click', function(){
			self.limparOfertas();
			var id = $(this).data('company-id')
			self.toggleAtivo($(this).closest('#companies').find('.ativo'));
			self.toggleAtivo($(this));
			self.getCompanyOffers(id, self.searchResultCB);
			//self.getCompanyRecomendations();
		});
	};

	self.fillOfferRecommendationsCB = function(data){
		var rec ="";		
		rec+= '				<p><strong><small> Recomendaçoes: | ';		
		for (var i = 0; i < data.length; i++) {
			rec+= data[i].name+ ' |';
		};
		rec+= '				</small></strong></p>';
	};

	self.searchResultCB = function(data){
		var offer = '';
		if(typeof data !== "undefined")
		{
			for(var i=0; i<data.length; i++){
				var htmlText = data[i].description
				htmlText = $("<div/>").html(htmlText).text();
				offer = '';
				offer+= '	<div  class="col-md-12">';
				offer+= '	  <div class="panel panel-default offerPanel">';
				offer+= '	  	<div class="panel-heading offerTitle" data-offer-id="'+data[i].id+'"><h3>'+data[i].title+'</h3></div>';
				offer+= '	    <div class="panel-body offerItem" style="display:none;"></div>';
				offer+= '	    <div class="panel-footer offerTags">';
				offer+= '				<p><strong><small> TAGS: | ';
				for(var j=0; j<data[i].skills.length; j++){
					offer+= data[i].skills[j].name + ' | ';
				}
				offer+= '				</small></strong></p></div>';
				offer+= '	  </div>';
				offer+= '	</div>';
				$("#ofertas").append(offer);				
				$('.offerItem:eq('+i+')').append(htmlText);	        	
	    	}

	    	$('.offerTitle').on('click', function(){
	    		$(this).closest("#ofertas").find()
				$(this).next('.offerItem').toggle();
			});
    	}
	};

	//tentativa de preencher todas as recomendacoes das ofertas das empresas 2
	self.fillOfferRecommendationsCB = function(data){
		alert();
		$('#ofertas').find('.offerRecommendations').each(function () {
			if($(this).html() === "")
				$(this).append("<p>oi</p>");
			alert($(this).html());
			alert($(this).text());
		});
	};
	//tentativa de preencher todas as recomendacoes das ofertas das empresas 1
	self.getCompanyRecomendations = function(){
		$('#ofertas').find('.offerTitle').each(function () {
		    self.recommendedByOffer($(this).data('offer-id'), self.fillOfferRecommendationsCB);
		});
	};

	self.abrirUserSpace = function(){
		self.getUsers(self.fillUsersCB);
		$('#mainContainer').removeClass('col-md-12')
							.addClass('col-md-8');
		$('#busca').hide('slow');		
		$('#companyContainer').hide('slow');		
		$('#companySpaceButton').removeClass('ativo');
		$('#userContainer').show('slow');
		$('#userSpaceButton').addClass('ativo');
		self.limparOfertas();	
	};

	self.fecharUserSpace = function(){
		$('#mainContainer').addClass('col-md-12')
							.removeClass('col-md-8');
		$('#busca').show('slow');
		$('#userContainer').hide('slow');
		$('#userSpaceButton').removeClass('ativo');
		self.limparOfertas()	
	};

	self.abrirCompanySpace = function(){
		self.getCompanies(self.fillCompaniesCB);
		$('#companyContainer').show('slow');
		$('#companySpaceButton').addClass('ativo');
		$('#mainContainer').removeClass('col-md-12')
							.addClass('col-md-8');
		$('#busca').hide('slow');		
		$('#userContainer').hide('slow');		
		$('#userSpaceButton').removeClass('ativo');
		self.limparOfertas();
	}

	self.fecharCompanySpace = function(){
		$('#mainContainer').addClass('col-md-12')
							.removeClass('col-md-8');
		$('#busca').show('slow');
		$('#companyContainer').hide('slow');
		$('#companySpaceButton').removeClass('ativo');
		self.limparOfertas()
	}

	self.skillsAtivos = function(){
		var skills = [];
		$('.skillItem').each(function(i){
			if($(this).hasClass('ativo')){
				skills[i] = $(this).data('skill');
				self.toggleAtivo($(this));
			}
		});
		return skills;
	};

	self.ratingsAtivos = function(){
		var ratings = [];
		$('.ratingItem').each(function(i){
			if($(this).hasClass('ativo')){
				ratings[i] = $(this).data('rating');
				self.toggleAtivo($(this));
			}
		});
		return ratings;
	}

	self.limparOfertas = function(){
		$('#ofertas').empty();
	}	

	self.toggleAtivo = function(item){
		$(item).toggleClass('ativo');
	};

}

var principal = new Principal();
principal.setup();
