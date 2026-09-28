package com.project.microsave.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microsave.Entity.Group;
import com.project.microsave.Service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/add")
    public Group addGroup(@RequestBody Group group) {
        return groupService.addGroup(group);
    }

    @PutMapping("/update/{id}")
    public Group updateGroup(@PathVariable Integer id,
                             @RequestBody Group group) {

        return groupService.updateGroup(id, group);
    }

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/get/{id}")
    public Group getGroupById(@PathVariable Integer id) {
        return groupService.getGroupById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Integer id) {

        groupService.deleteGroup(id);

        return "Group deleted successfully";
    }
}