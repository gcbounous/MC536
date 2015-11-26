function User() {
    var rest = new RESTful();
    var self = this;

    self.path = '/users/';

    self.getUsers = function(callback) {
        rest.get(self.path, null , callback );
    };

    self.getRecommendations = function(id, callback){
    	rest.get(self.path+'recommendations/'+id, null, callback);
    }

  }