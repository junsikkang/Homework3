package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestInstructor {
	
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;
	
	@Before
	public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }
	
	// Test to check that addHomework adds a homework
	@Test
	public void testAddHomework() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		assertTrue(this.instructor.homeworkExists("Class1", 2017, "HW1"));
	}
	
	// Class doesn't exist shouldn't add a homework.
	@Test
	public void testAddHomework2() {
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Class1", 2017, "HW1"));
	}
	
	// Incorrect instructor
	@Test
	public void testAddHomework3() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor2", "Class1", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Class1", 2017, "HW1"));
	}
	
	//** Incorrect year in Class/Year pair
	@Test
	public void testAddHomework4() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2018, "HW1");
		assertFalse(this.instructor.homeworkExists("Class1", 2018, "HW1"));
	}
	
	//** Incorrect class in Class/Year pair
	@Test
	public void testAddHomework5() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class2", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Class2", 2017, "HW1"));
	}
	
	// Test assignGrade
	// Should assignGrade properly with correct parameters
	@Test
	public void testAssignGrade() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		this.instructor.assignGrade("Instructor1", "Class1", 2017, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class1", 2017, "HW1", "Student1");
		assertTrue(grade.equals(new Integer(90)));
	}
	
	// Should not assignGrade properly (wrong instructor)
	@Test
	public void testAssignGrade2() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		this.instructor.assignGrade("Instructor2", "Class1", 2017, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class1", 2017, "HW1", "Student1");
		assertFalse(grade.equals(new Integer(90)));
	}
	
	// Should not assignGrade properly (HW not assigned)
	@Test
	public void testAssignGrade3() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW2");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		this.instructor.assignGrade("Instructor1", "Class1", 2017, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class1", 2017, "HW1", "Student1");
		assertNull(grade);
	}
	
	// Should not assignGrade properly (HW not submitted by student)
	@Test
	public void testAssignGrade4() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.instructor.assignGrade("Instructor1", "Class1", 2017, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class1", 2017, "HW1", "Student1");
		assertFalse(grade.equals(new Integer(90)));
	}
	
	// Wrong class
	@Test
	public void testAssignGrade5() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		this.instructor.assignGrade("Instructor1", "Class2", 2017, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class2", 2017, "HW1", "Student1");
		assertNull(grade);
	}
	
	// Wrong year
	@Test
	public void testAssignGrade6() {
		this.admin.createClass("Class1", 2017, "Instructor1", 20);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		this.instructor.assignGrade("Instructor1", "Class1", 2018, "HW1", "Student1", 90);
		Integer grade = this.instructor.getGrade("Class1", 2018, "HW1", "Student1");
		assertNull(grade);
	}
}