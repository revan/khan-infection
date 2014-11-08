import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Runs the Total Infection plan.
 * @author revan
 */
public class TotalInfection {
	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		InfectionUtils.printVisualization(userMap, "userMap.html");
		
		runInfection(userMap);
	}

	/**
	 * Runs the Total Infection plan.
	 * Randomly chooses users and infects their networks until all are infected.
	 * @param userMap
	 */
	public static void runInfection(UserMap userMap) {
		Random random = new Random();
		Scanner scanner = new Scanner(System.in);

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
			
			InfectionUtils.printVisualizationInfection(userMap, "infection.html", infected);
			
			// Comment this out for automation.
			System.out.println("Refresh infection.html to view, or press enter to continue.");
			scanner.nextLine();
		}
		
		System.out.println("All users infected.");
	}
}
