import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class UserMap {
	/** The maximum users in any disconnected group. Keeps data set small. */
	private final int NUM_USERS_PER_GROUP = 15;
	
	/** The number of disconnected groups to generate. */
	private final int NUM_DISCONNECTED_GROUPS = 30;
	
	/** The average number of students to generate for each teacher. */
	private final int AVG_STUDENTS_PER_TEACHER = 5;
	
	/** The probability that any user is a teacher. */
	private final double PROB_TEACHER = 0.3;
	
	/** The probability that any student has a second teacher. */
	private final double PROB_SECOND = 0.1;
	
	private static Random random = new Random();
	
	/** The list of users who have no teachers. */
	private List<User> heads = new ArrayList<>();
	
	public UserMap() {
		for (int i = 0; i < NUM_DISCONNECTED_GROUPS; i++) {
			User user = new User();
			heads.add(user);
			maybeCreateStudents(user, 1);
		}
	}
	
	/**
	 * Recursively creates students on Users.
	 * @param user The User to add students to.
	 */
	public void maybeCreateStudents(User teacher, int usersInGroup) {
		if (random.nextDouble() <= PROB_TEACHER) {
			// numStudents is normally distributed.
			int numStudents = (int) ((random.nextGaussian() + 1) * AVG_STUDENTS_PER_TEACHER);
			for (int i = 0; i < numStudents; i++) {
				if (++usersInGroup >= NUM_USERS_PER_GROUP) {
					return;
				}
				
				User student = new User();
				teacher.addStudent(student);
				student.addTeacher(teacher);
				
				maybeCreateStudents(student, usersInGroup);
			}
		}
		
		if (usersInGroup > 1 && random.nextDouble() <=  PROB_SECOND) {
			// Choose second teacher from heads list.
			User second = heads.get(random.nextInt(heads.size()));
			second.addStudent(teacher);
			teacher.addTeacher(second);
		}
		
	}
	
	/**
	 * Iterates user map and prints it.
	 * @param out
	 */
	public void printUserMap(PrintStream out) {
		HashMap<Integer, User> visited = new HashMap<>();
		for (User head : heads) {
			out.println(head.getId());
			visited.put(head.getId(), head);
			printRelations(head, visited, out);
		}
	}
	
	/**
	 * Recursively prints teacher->student relationships.
	 * @param user
	 * @param visited
	 * @param out
	 */
	public static void printRelations(User user, HashMap<Integer, User> visited, PrintStream out) {
		for (User student : user.getStudents()) {
			if (visited.put(student.getId(), student) == null) {
				out.println(user.getId() + " -> " + student.getId());
				printRelations(student, visited, out);
			}
		}
		for (User teacher : user.getTeachers()) {
			if (visited.put(teacher.getId(), teacher) == null) {
				out.println(teacher.getId() + " -> " + user.getId());
				printRelations(teacher, visited, out);
			}
		}
	}
}
