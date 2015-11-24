function Offer() {
    var rest = new RESTful();
    var self = this;

    self.path = '/offers/';

     self.searchOffers = function(skills, ratings, callback) {
     	var searchPath =self.path+'search?limit=10'; 
     	for(var i = 0; i< skills.length; i++){
     		if(typeof skills[i]!== "undefined"){
     			searchPath +='&skills='+skills[i];
     		}
     	}
     	for(var i = 0; i< ratings.length; i++){
     		if(typeof ratings[i]!== "undefined"){
     			searchPath +='&'+ratings[i]+'=true';
     		}
     	}
        rest.get(searchPath, null , callback );
    };

  }