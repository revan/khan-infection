import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Runs the Partial Infection plan.
 * @author revan
 */
public class PartialInfection {
	/** If false, will pause at each step. */
	private static final boolean AUTOMATIC = false;
	
	private static final String FILE_NAME = "infection.html";
	
	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		InfectionUtils.printVisualization(userMap, "userMap.html");
		
		System.out.print("Enter target number of users to infect: ");
		Scanner scanner = new Scanner(System.in);
		int target = scanner.nextInt();
		
		Collection<User> toInfect = runPartialInfection(userMap, target, AUTOMATIC);
		
		System.out.print("To reach " + toInfect.size() + " infections, infect ");
		for (User user : toInfect) {
			System.out.print(user.getId() + " ");
		}
		System.out.println();
		
		InfectionUtils.printVisualizationInfection(userMap, FILE_NAME, toInfect);		
		System.out.println("Open " + FILE_NAME + " to view.");
	}
	
	/**
	 * Runs the Partial Infection plan.
	 * Attempts to infect exactly the given number of users.
	 * @param userMap
	 * @param target number of users to infect
	 * @param automatic if false, will pause at each step
	 */
	public static Collection<User> runPartialInfection(UserMap userMap, int target, boolean automatic) {
		
		int remaining = target;

		// Create list of Goups, sort by size descending.
		ArrayList<User> heads = userMap.getHeads();
		ArrayList<Group> groups = new ArrayList<>();
		for (User head : heads) {
			groups.add(new Group(head));
		}
		Collections.sort(groups);
		Collections.reverse(groups);
		
		for(Group group : groups) {
			System.out.print(group.getSize() + " ");
		}
		System.out.println();
		
		List<User> toInfect = new LinkedList<>();
		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);
			if (group.getSize() <= remaining) {
				toInfect.addAll(group.getMembers());
				remaining -= group.getSize();
				groups.remove(i--);
				
				System.out.println("Infected " + group.getSize() + " users.");
				InfectionUtils.printVisualizationInfection(userMap, FILE_NAME, toInfect, automatic);
			}
		}
		
		if (groups.size() > 0) {
			// check if we can get closer by going over.
			Group smallest = groups.get(groups.size() - 1);
			if (Math.abs(remaining - smallest.getSize()) < remaining) {
				toInfect.addAll(smallest.getMembers());
			}
		}
		
		return toInfect;
	}
}
