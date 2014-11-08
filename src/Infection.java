import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Runs Total Infection and Limited Infection plans.
 * @author revan
 */
public class Infection {

	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		printVisualization(userMap, "userMap.html");
		
//		runInfection(userMap);
		
		runPartialInfection(userMap);
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
			
			printVisualizationInfection(userMap, "infection.html", infected);
			
			System.out.println("Refresh infection.html to view, or press enter to continue.");
			
			scanner.nextLine();
		}
		
		System.out.println("All users infected.");
	}
	
	public static void runPartialInfection(UserMap userMap) {
		System.out.print("Enter target number of users to infect: ");
		Scanner scanner = new Scanner(System.in);
		int target = scanner.nextInt();
		int remaining = target;
		
		// construct a Map of the sizes of each group, sort descending.
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
				toInfect.add(group.getOneMember());
				remaining -= group.getSize();
				groups.remove(i--);
			}
		}
		
		if (groups.size() > 0) {
			// check if we can get closer by going over.
			Group smallest = groups.get(groups.size() - 1);
			if (Math.abs(remaining - smallest.getSize()) < remaining) {
				toInfect.add(smallest.getOneMember());
			}
		}
		
		System.out.print("To reach " + (target - remaining) + " infections, infect ");
		for (User user : toInfect) {
			System.out.print(user.getId() + " ");
		}
		System.out.println();
		
		printVisualizationInfection(userMap, "infection.html", toInfect);
	}
	
	/**
	 * Assembles an SVG visualization using viz.js
	 * @param userMap
	 * @param fileName
	 */
	public static void printVisualization(UserMap userMap, String fileName) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			
			printVisualizationHeader(out);
			userMap.printUserMap(out);
			printVisualizationFooter(out);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Assembles an SVG visualization using viz.js
	 * Outlines infected users in red.
	 * @param userMap
	 * @param fileName
	 * @param infected
	 */
	public static void printVisualizationInfection(UserMap userMap, String fileName, Collection<User> infected) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			
			printVisualizationHeader(out);
			for (User user : infected) {
				out.println(user.getId() + "[color = red]");
			}
			userMap.printUserMap(out);
			printVisualizationFooter(out);
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printVisualizationHeader(PrintStream out) {
		out.println("<html><body><script type='text/vnd.graphviz' id='infect'>digraph G {");
	}
	
	private static void printVisualizationFooter(PrintStream out) {
		out.println("}</script><script src='viz.js'></script><script> function inspect(s) { return '<pre>' + "
				+ "s.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\'/g, '&quot;') + '</pre>' } function "
				+ "src(id) { return document.getElementById(id).innerHTML; } function example(id, format, engine) "
				+ "{ var result; try { result = Viz(src(id), format, engine); if (format === 'svg') return result;"
				+ " else return inspect(result); } catch(e) { return inspect(e.toString()); } } "
				+ "document.body.innerHTML += example('infect', 'svg'); </script> </body> </html>");
	}

}
