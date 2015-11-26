function Company() {
    var rest = new RESTful();
    var self = this;

    self.path = '/companies/';

     self.getCompanies = function(callback) {
        rest.get(self.path, null , callback );
    };

  }