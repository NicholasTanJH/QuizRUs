package comp3350.quizrus.application;

import comp3350.quizrus.persistence.CoursePersistence;
import comp3350.quizrus.persistence.SCPersistence;
import comp3350.quizrus.persistence.StudentPersistence;
import comp3350.quizrus.persistence.hsqldb.CoursePersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.SCPersistenceHSQLDB;
import comp3350.quizrus.persistence.hsqldb.StudentPersistenceHSQLDB;

public class Services
{
	private static StudentPersistence studentPersistence = null;
	private static CoursePersistence coursePersistence = null;
	private static SCPersistence scPersistence = null;

	public static synchronized StudentPersistence getStudentPersistence()
    {
		if (studentPersistence == null)
		{
            studentPersistence = new StudentPersistenceHSQLDB(Main.getDBPathName());
        }

        return studentPersistence;
	}

    public static synchronized CoursePersistence getCoursePersistence()
    {
        if (coursePersistence == null)
        {
            coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());
        }

        return coursePersistence;
    }

	public static synchronized SCPersistence getScPersistence() {
        if (scPersistence == null) {
            scPersistence = new SCPersistenceHSQLDB(Main.getDBPathName());
        }

        return scPersistence;
    }
}
