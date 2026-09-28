package com.project.microsave.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.microsave.Entity.Group;
import com.project.microsave.Repository.GroupRepo;

@Service
public class GroupService {

    private final GroupRepo groupRepository;

    public GroupService(GroupRepo groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group addGroup(Group group) {

        if (group.getName() == null || group.getName().isBlank()) {
            throw new RuntimeException("Group name is required");
        }

        group.setId(getNextId());

        return groupRepository.save(group);
    }

    public Group updateGroup(Integer id, Group updatedGroup) {

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.setName(updatedGroup.getName());
        group.setDescription(updatedGroup.getDescription());
        group.setCreatedDate(updatedGroup.getCreatedDate());

        return groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Integer id) {

        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void deleteGroup(Integer id) {

        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found");
        }

        groupRepository.deleteById(id);
    }

    private Integer getNextId() {

        List<Group> groups = groupRepository.findAll();

        Integer maxId = 0;

        for (Group group : groups) {

            if (group.getId() > maxId) {
                maxId = group.getId();
            }
        }

        return maxId + 1;
    }
}