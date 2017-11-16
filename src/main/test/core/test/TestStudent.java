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
public class TestStudent {
	
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;
	
	@Before
	public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }
	
	//Test registerForClass
	@Test
	public void testRegisterForClass() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		assertTrue(this.student.isRegisteredFor("Student1", "Class1", 2017));
	}
	
	// Full class
	@Test
	public void testRegisterForClass2() {
		this.admin.createClass("Class1", 2017, "Instructor1", 2);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.student.registerForClass("Student2", "Class1", 2017);
		this.student.registerForClass("Student3", "Class1", 2017);
		assertFalse(this.student.isRegisteredFor("Student3", "Class1", 2017));
	}
	
	// Class doesn't exist
	@Test
	public void testRegisterForClass3() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class2", 2017);
		assertFalse(this.student.isRegisteredFor("Student1", "Class2", 2017));
	}
	
	// Class wrong year (doesn't exist)
	@Test
	public void testRegisterForClass4() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2018);
		assertFalse(this.student.isRegisteredFor("Student1", "Class1", 2018));
	}
	
	//Test dropClass
	@Test
	public void dropClass() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.student.dropClass("Student1", "Class1", 2017);
		assertFalse(this.student.isRegisteredFor("Student1", "Class1", 2017));
	}
	
	// Not registered
	@Test
	public void dropClass2() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.dropClass("Student1", "Class1", 2017);
		assertFalse(this.student.isRegisteredFor("Student1", "Class1", 2017));
	}
	
	
	// Test submitHomework
	@Test
	public void testSubmitHomework() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		assertTrue(this.student.hasSubmitted("Student1", "HW1", "Class1", 2017));
	}
	
	// homework doesn't exist
	@Test
	public void testSubmitHomework2() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		assertFalse(this.student.hasSubmitted("Student1", "HW1", "Class1", 2017));
	}
	
	// student not registered
	@Test
	public void testSubmitHomework3() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2017);
		assertFalse(this.student.hasSubmitted("Student1", "HW1", "Class1", 2017));
	}
	
	// wrong class
	@Test
	public void testSubmitHomework4() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class2", 2017);
		assertFalse(this.student.hasSubmitted("Student1", "HW1", "Class2", 2017));
	}
	
	// wrong year for class
	@Test
	public void testSubmitHomework5() {
		this.admin.createClass("Class1", 2017, "Instructor1", 5);
		this.student.registerForClass("Student1", "Class1", 2017);
		this.instructor.addHomework("Instructor1", "Class1", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Solution", "Class1", 2018);
		assertFalse(this.student.hasSubmitted("Student1", "HW1", "Class1", 2018));
	}
}