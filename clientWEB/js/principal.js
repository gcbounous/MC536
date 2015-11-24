function Principal(){
	var self = this;
	var skill = new Skill();
	var offer = new Offer();
    
	self.setup = function(){
		self.getSkills(self.fillSkillsCB);

		$('#searchOffers').on('click', function(){
			$('#ofertas').empty();
			self.searchOffers(self.searchResult());
		});

		$('.ratingItem').on('click', function(){
			self.toggleAtivo($(this));
		});
        
	};

	self.searchOffers = function(callback){
		var skills = self.skillsAtivos();
		var ratings = self.ratingsAtivos();
		offer.searchOffers(callback, skills, ratings);
	}

	self.getSkills = function(callback){
		skill.getSkills(callback);
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

	self.toggleAtivo = function(item){
		$(item).toggleClass('ativo');
	};

	self.searchResult = function(data){
		var offer = "";

		for(var i=0; i<data.length; i++){
			offer+= '<div class="col-md-12">';
          	offer+= '<h2>'+data[i].title+'</h2>';
          	offer+= '<p>'+data[i].description+'</p>';
        	offer+= '</div><!-- /.col-md-12 -->';
    	}

    	$("#ofertas").append(offer);
	};

	self.skillsAtivos = function(){
		var skills = [];
		$('.skillItem').each(function(i){
			if($(this).hasClass('ativo')){
				skills[i] = $(this).data('skill');
			}
		});
		return skills;
	};

	self.ratingsAtivos = function(){
		var ratings = [];
		$('.ratingItem').each(function(i){
			if($(this).hasClass('ativo')){
				ratings[i] = $(this).data('rating');
			}
		});
		return ratings;
	}

}

var principal = new Principal();
principal.setup();
