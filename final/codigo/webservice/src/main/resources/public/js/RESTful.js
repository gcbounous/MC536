function RESTful() {
    var self = this;
    var URL = "http://localhost:8080";

    self.get = function(path, data , callback) {
        try {
            $.ajax({
                type: 'GET',
                dataType: 'json',
                ContentType: 'application/json; charset=utf-8',
                url: URL + path,
                data: data,
                async: true,
            }).done(function(resp) {
                callback(resp);
            }).fail(function(err) {
                console.log(err);
            });
        } catch (e) {
           console.log(e);
        }
    };

    this.insert = function(path, data , callback) {
        try {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                ContentType: 'application/json; charset=utf-8',
                url: URL + path,
                data: data,
                async: true
            }).done(function(resp) {
                callback(resp);
            }).fail(function(err) {
                console.log(err);
            });
        } catch (e) {
            console.log(e);
        }
    };


   this.update = function(path, data , callback) {
        try {
            $.ajax({
                type: 'PUT',
                dataType: 'json',
                url: URL + path,
                data: data,
                ContentType: 'application/json; charset=utf-8',
                async: true
            }).done(function(resp) {
                callback(resp);
            }).fail(function(err) {
                console.log(err);
            });
        } catch (e) {
            console.log(e);
        }
    };

    this.delete = function(path, data , callback) {
        try {
            $.ajax({
                type: 'DELETE',
                dataType: 'json',
                url: URL + path,
                data: data,
                ContentType: 'application/json; charset=utf-8',

                async: true
            }).done(function(resp) {
                callback(resp);
            }).fail(function(err) {
                console.log(err);
            });
        } catch (e) {
            console.log(e);
        }
    };
}