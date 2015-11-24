package org.mc536.webservice.web.resources;

import org.mc536.webservice.domain.model.entity.Skill;
import org.mc536.webservice.domain.model.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillResource {

    @Autowired
    private SkillService skillService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Skill> all() {
        return skillService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Skill create(@RequestParam("name") String name) {
        return skillService.createSkill(name);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Skill update(@RequestParam("id") Integer id,
                        @RequestParam("name") String name) {

        return skillService.updateSkill(id, name);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Skill findById(@PathVariable("id") Integer id) {
        return skillService.findById(id);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Skill findByName(@PathVariable("name") String name) {
        return skillService.findByName(name);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") Integer id) {
        skillService.delete(id);
    }
}
