package com.platform.kspace.component;

import java.util.Calendar;

import com.platform.kspace.model.Professor;
import com.platform.kspace.model.Student;
import com.platform.kspace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        LoadData();
    }

    private void LoadData() {
        Professor p = new Professor("Petar", "Petrovic", "petar@example.com", "$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.");
        p.setPassword("qwerty", true);
        p = this.userRepository.save(p);

        Calendar cal = Calendar.getInstance();
        cal.set(2000, 10, 5);
        Student s = new Student(
                "Mika",
                "Mikic",
                "mika@example.com",
                "$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.",
                cal.getTime()
        );
        this.userRepository.save(s);
    }
}
