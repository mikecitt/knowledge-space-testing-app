package com.platform.kspace.component;

import java.util.Date;

import com.platform.kspace.model.*;
import com.platform.kspace.repository.ItemRepository;
import com.platform.kspace.repository.SectionRepository;
import com.platform.kspace.repository.TestRepository;
import com.platform.kspace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private TestRepository testRepository;
    private SectionRepository sectionRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Autowired
    public DataLoader(TestRepository testRepository, SectionRepository sectionRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.sectionRepository = sectionRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        LoadData();
    }

    private void LoadData() {
        Professor p = new Professor("Petar Petrovic", "petar@example.com", "qwerty");
        // p = this.userRepository.save(p);

        Student s = new Student("Mika Mikic", "mika@example.com", "qwerty");
        this.userRepository.save(s);
        
        Item item1 = new Item("Item1");
        //item1 = itemRepository.save(item1);

        Item item2 = new Item("Item2");
        //item2 = itemRepository.save(item2);

        Section section1 = new Section("Section1");
        section1.addItem(item1);
        section1.addItem(item2);
        //sectionRepository.save(section1);

        Test t1 = new Test("test1", 60.0, new Date(), new Date());
        t1.addSection(section1);
        t1.setCreatedBy(p);
        testRepository.save(t1);
    }
}
