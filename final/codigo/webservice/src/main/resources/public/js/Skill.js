function Skill() {
    var rest = new RESTful();
    var self = this;

    self.path = '/skills/';

     self.getSkills = function(callback) {
        rest.get(self.path, null , callback );
    };

  }