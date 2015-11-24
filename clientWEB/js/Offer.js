function Offer() {
    var rest = new RESTful();
    var self = this;

    self.path = '/offers/';

     self.searchOffers = function(skills, ratings, callback) {
     	var searchPath =self.path+'/search?limit=10'; 
     	for(var i = 0; i< skills.length; i++){
     		searchPath +='&skills='+skills[i];
     	}
     	for(var i = 0; i< ratings.length; i++){
     		searchPath +='&'+ratings[i]+'=true';
     	}
     	alert(searchPath);
        rest.get(searchPath, null , callback );
    };

  }