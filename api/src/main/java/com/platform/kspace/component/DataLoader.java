package com.platform.kspace.component;

import java.util.Calendar;
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
        p = this.userRepository.save(p);

        Student s = new Student("Mika Mikic", "mika@example.com", "qwerty");
        this.userRepository.save(s);
        
        Item item111 = new Item("Item111");
        Item item112 = new Item("Item112");
        Item item113 = new Item("Item113");
        Item item114 = new Item("Item114");
        Item item115 = new Item("Item115");
        Item item116 = new Item("Item116");
        Item item117 = new Item("Item117");
        Item item118 = new Item("Item118");

        Section section11 = new Section("Section11");
        section11.addItem(item111);
        section11.addItem(item112);
        section11.addItem(item113);
        section11.addItem(item114);
        section11.addItem(item115);
        section11.addItem(item116);
        section11.addItem(item117);
        section11.addItem(item118);

        Item item121 = new Item("Item121");
        Item item122 = new Item("Item122");
        Item item123 = new Item("Item123");
        Item item124 = new Item("Item124");

        Section section12 = new Section("Section12");
        section12.addItem(item121);
        section12.addItem(item122);
        section12.addItem(item123);
        section12.addItem(item124);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 5);
        Test t1 = new Test("test1", 60.0, new Date(), c.getTime());
        t1.addSection(section11);
        t1.addSection(section12);
        t1.setCreatedBy(p);
        for(Section sec : t1.getSections()) {
            for(Item item : sec.getItems()) {
                item.addAnswer(new Answer("A", 5.0, item));
                item.addAnswer(new Answer("B", -5.0, item));
                item.addAnswer(new Answer("C", 10.0, item));
                item.addAnswer(new Answer("D", -5.0, item));
                item.addAnswer(new Answer("E", -5.0, item));
                item.addAnswer(new Answer("F", 10.0, item));
            }
        }
        testRepository.save(t1);

        Item item211 = new Item("Item211");
        Item item212 = new Item("Item212");
        Item item213 = new Item("Item213");
        Item item214 = new Item("Item214");
        Item item215 = new Item("Item215");
        Item item216 = new Item("Item216");
        Item item217 = new Item("Item217");

        Section section21 = new Section("Section21");
        section11.addItem(item211);
        section11.addItem(item212);
        section11.addItem(item213);
        section11.addItem(item214);
        section11.addItem(item215);
        section11.addItem(item216);
        section11.addItem(item217);

        Item item221 = new Item("Item221");
        Item item222 = new Item("Item222");
        Item item223 = new Item("Item223");
        Item item224 = new Item("Item224");

        Section section22 = new Section("Section22");
        section22.addItem(item221);
        section22.addItem(item222);
        section22.addItem(item223);
        section22.addItem(item224);

        Item item231 = new Item("Item231");
        Item item232 = new Item("Item232");
        Item item233 = new Item("Item233");

        Section section23 = new Section("Section23");
        section23.addItem(item231);
        section23.addItem(item232);
        section23.addItem(item233);

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, 10);
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.DATE, 14);
        Test t2 = new Test("test2", 120.0, c1.getTime(), c2.getTime());
        t2.addSection(section21);
        t2.addSection(section22);
        t2.addSection(section23);
        t2.setCreatedBy(p);
        testRepository.save(t2);
    }
}
