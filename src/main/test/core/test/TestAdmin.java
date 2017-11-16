package core.test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    
    // Test for uniqueness of class/year pair
    
    @Test
    public void testMakeClass3() {
    	this.admin.createClass("Class1", 2017, "Instructor", 15);
    	this.admin.createClass("Class1", 2017, "Instructor2", 20);
    	assertFalse(this.admin.getClassInstructor("Class1", 2017).equals("Instructor2"));
    }
    
    @Test
    public void testMakeClass0() {
    	this.admin.createClass("Class1", 2017, "Instructor", 15);
    	this.admin.createClass("Class1", 2017, "Instructor2", 20);
    	assertTrue(this.admin.getClassInstructor("Class1", 2017).equals("Instructor"));
    }
    
    // Test for no instructor in more than 2 classes in a year
    @Test
    public void testMakeClass4() {
    	this.admin.createClass("Class1", 2017, "Instructor", 15);
    	this.admin.createClass("Class2", 2017, "Instructor", 15);
    	this.admin.createClass("Class3", 2017, "Instructor", 15);
    	assertFalse(this.admin.classExists("Class3", 2017));
    }
    
    // Test for instructor in more than 2 classes in different years
    @Test
    public void testMakeClass9() {
    	this.admin.createClass("Class1", 2017, "Instructor", 15);
    	this.admin.createClass("Class2", 2017, "Instructor", 15);
    	this.admin.createClass("Class3", 2018, "Instructor", 15);
    	assertTrue(this.admin.classExists("Class3", 2018));
    }
    // Test for capacity == 0
    @Test
    public void testMakeClass5() {
    	this.admin.createClass("Class1", 2017, "Instructor", 0);
    	assertFalse(this.admin.classExists("Class1", 2017));
    }
    
    // Test for capacity < 0
    @Test
    public void testMakeClass6() {
    	this.admin.createClass("Class1", 2017, "Instructor", -10);
    	assertFalse(this.admin.classExists("Class1", 2017));
    }
    
    // Future years
    
    @Test
    public void testMakeClass7() {
    	this.admin.createClass("Class1", 2018, "Instructor", 10);
    	assertTrue(this.admin.classExists("Class1", 2018));
    }
    
    // Test for changing capacity
    
    @Test
    public void testChangeCapacity() {
    	int newCap = 15;
    	this.admin.createClass("Class1", 2017, "Instructor", 10);
    	this.admin.changeCapacity("Class1", 2017, newCap);
    	assertEquals(newCap, this.admin.getClassCapacity("Class1", 2017));
    }
    
    @Test
    public void testChangeCapacity2() {
    	int newCap = 1;
    	this.admin.createClass("Class1", 2017, "Instructor", 2);
    	IStudent student = new Student();
    	student.registerForClass("StudentA", "Class1", 2017);
    	student.registerForClass("StudentB", "Class1", 2017);
    	this.admin.changeCapacity("Class1", 2017, newCap);
    	//assertTrue(this.admin.getClassCapacity("Class1", 2017) == 1);
    	assertFalse(newCap == this.admin.getClassCapacity("Class1", 2017));
    }
    
    @Test
    public void testChangeCapacity3() {
    	int newCap = -1;
    	this.admin.createClass("Class1", 2017, "Instructor", 5);
    	this.admin.changeCapacity("Class1", 2017, newCap);
    	//assertTrue(this.admin.getClassCapacity("Class1", 2017) == 1);
    	assertFalse(newCap == this.admin.getClassCapacity("Class1", 2017));
    }
    
    // enrolled = cap
    @Test
    public void testChangeCapacity4() {
    	int newCap = 2;
    	this.admin.createClass("Class1", 2017, "Instructor", 2);
    	IStudent student = new Student();
    	student.registerForClass("StudentA", "Class1", 2017);
    	student.registerForClass("StudentB", "Class1", 2017);
    	this.admin.changeCapacity("Class1", 2017, newCap);
    	//assertTrue(this.admin.getClassCapacity("Class1", 2017) == 1);
    	assertTrue(newCap == this.admin.getClassCapacity("Class1", 2017));
    }
}