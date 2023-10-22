package rw.ac.rca.gradesclassb.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;
import org.testng.Assert;
import rw.ac.rca.gradesclassb.enumerations.EGender;
import rw.ac.rca.gradesclassb.models.Student;

import java.util.List;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
public class StudentRepository {
    @Autowired
    private IStudentRepository underTest;

    @Test
    void canSaveStudent(){
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGender(EGender.MALE);

        Student savedStudent = underTest.save(student);
        System.out.println(savedStudent);
        // check if the student is saved
         Assert.assertNotNull(savedStudent);
    }

    @Test
    void canSelectStudents(){
        List<Student> students = underTest.findAll();
        System.out.println(students);
        // check if the student list is available
        Assert.assertNotNull(students);
    }
}
