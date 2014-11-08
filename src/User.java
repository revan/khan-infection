import java.util.LinkedList;
import java.util.List;

/**
 * Represents a User of the system.
 * Users can be both students and teachers simultaneously.
 * @author revan
 */
public class User {
	private List<User> students = new LinkedList<User>();
	private List<User> teachers = new LinkedList<User>();
	private int id;
	private static int numberOfUsers = 0;
	
	public User() {
		id = numberOfUsers++;
	}
	
	/**
	 * Adds a student to the list of students.
	 * @param student
	 */
	public void addStudent(User student) {
		students.add(student);
	}

	/**
	 * Adds a teacher to the list of teachers.
	 * @param teacher
	 */
	public void addTeacher(User teacher) {
		teachers.add(teacher);
	}
	
	/**
	 * @return this User's students.
	 */
	public List<User> getStudents() {
		return students;
	}
	
	/**
	 * @return this User's teachers.
	 */
	public List<User> getTeachers() {
		return teachers;
	}
	
	/**
	 * @return this User's unique ID.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return number of unique Users
	 */
	public static int getNumUsers() {
		return numberOfUsers;
	}
}
