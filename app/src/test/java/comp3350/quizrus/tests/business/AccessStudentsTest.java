package comp3350.quizrus.tests.business;


import org.junit.Before;
import org.junit.Test;

import comp3350.quizrus.business.AccessStudents;

import comp3350.quizrus.objects.Student;

import comp3350.quizrus.tests.persistence.StudentPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessStudentsTest
{
	private AccessStudents accessStudents;

	@Before
    public void setUp() {
	    this.accessStudents = new AccessStudents(new StudentPersistenceStub());
    }

    @Test
	public void test1()
	{
		final Student student = accessStudents.getSequential();
		assertNotNull(student);
		assertTrue("100".equals(student.getStudentID()));
	}
}