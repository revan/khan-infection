import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Runs the Partial Infection plan.
 * @author revan
 */
public class PartialInfection {
	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		InfectionUtils.printVisualization(userMap, "userMap.html");
		
		runPartialInfection(userMap);
	}
	
	/**
	 * Runs the Partial Infection plan.
	 * Attempts to infect exactly the given number of users.
	 * @param userMap
	 */
	public static void runPartialInfection(UserMap userMap) {
		System.out.print("Enter target number of users to infect: ");
		Scanner scanner = new Scanner(System.in);
		int target = scanner.nextInt();
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
			}
		}
		
		if (groups.size() > 0) {
			// check if we can get closer by going over.
			Group smallest = groups.get(groups.size() - 1);
			if (Math.abs(remaining - smallest.getSize()) < remaining) {
				toInfect.addAll(smallest.getMembers());
			}
		}
		
		System.out.print("To reach " + (target - remaining) + " infections, infect ");
		for (User user : toInfect) {
			System.out.print(user.getId() + " ");
		}
		System.out.println();
		
		System.out.println("Open infection.html to view.");
		
		InfectionUtils.printVisualizationInfection(userMap, "infection.html", toInfect);
	}
}
