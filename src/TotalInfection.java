import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Runs the Total Infection plan.
 * @author revan
 */
public class TotalInfection {
	/** If false, will pause at each step. */
	private static final boolean AUTOMATIC = false;
	
	private static final String FILE_NAME = "infection.html";
	
	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		InfectionUtils.printVisualization(userMap, "userMap.html");
		
		runInfection(userMap, AUTOMATIC);
	}

	/**
	 * Runs the Total Infection plan.
	 * Randomly chooses users and infects their networks until all are infected.
	 * @param userMap
	 * @param automatic if true, will pause at each step.
	 */
	public static void runInfection(UserMap userMap, boolean automatic) {
		Random random = new Random();
		
		Collection<User> infected = new LinkedList<>();
		while (infected.size() < User.getNumUsers()) {
			User target;
			do {
				target = userMap.getUser(random.nextInt(User.getNumUsers()));
			} while (infected.contains(target));
			
			Collection<User> newlyInfected = UserMap.getRelations(target);
			
			System.out.print("Infected users");
			for (User user : newlyInfected) {
				System.out.print(" " + user.getId());
			}
			System.out.println(".");
			
			infected.addAll(newlyInfected);
			
			InfectionUtils.printVisualizationInfection(userMap, FILE_NAME , infected, automatic);
		}
		
		System.out.println("All users infected.");
	}
}
