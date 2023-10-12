package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Arrays;

public class GroupRepositoryTests extends AbstractIntegrationTests {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void mustFindGroupById() {
        final String groupName = "Access Group Name";
        final Group group = new Group();
        group.setName(groupName);
        groupRepository.save(group);
        Assertions.assertThat(group.getId()).isNotNull();

        final Group groupSaved = groupRepository.findById(group.getId()).orElseThrow();

        Assertions.assertThat(groupSaved.getName()).isEqualTo(groupName);
    }

    @Test
    public void mustListGroupsByFilters() {
        final String firstGroupName = "First Group";
        final Group firstGroup = new Group();
        firstGroup.setName(firstGroupName);
        final String secondGroupName = "Second Group";
        final Group secondGroup = new Group();
        secondGroup.setName(secondGroupName);
        groupRepository.saveAll(Arrays.asList(firstGroup, secondGroup));

        final Page<Group> filteredGroups = groupRepository.listByFilters(firstGroupName, null);

        Assertions.assertThat(filteredGroups.getContent()).extracting(Group::getName).containsAnyOf(firstGroupName);
    }
}
