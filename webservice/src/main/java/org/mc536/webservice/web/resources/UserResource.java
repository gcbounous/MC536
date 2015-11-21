package org.mc536.webservice.web.resources;

import java.util.List;
import org.mc536.webservice.domain.model.entity.User;
import org.mc536.webservice.domain.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> all() {
        return userService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public User create(@RequestParam("name") String name) {
        return userService.createUser(name);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public User update(@RequestParam("id") Integer id,
                        @RequestParam("name") String name) {

        return userService.updateUser(id, name);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

}
