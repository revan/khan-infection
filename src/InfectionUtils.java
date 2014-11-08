import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

/**
 * Methods shared by TotalInfection and PartialInfection.
 * @author revan
 */
public class InfectionUtils {
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
	 * @param automatic if false, pauses for input
	 */
	public static void printVisualizationInfection(UserMap userMap, String fileName, Collection<User> infected, boolean automatic) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			
			printVisualizationHeader(out);
			for (User user : infected) {
				out.println(user.getId() + "[color = red]");
			}
			userMap.printUserMap(out);
			printVisualizationFooter(out);
			
			out.close();
			
			if (!automatic) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Refresh " + fileName + " to view, or press enter to continue.");
				scanner.nextLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Overloaded method for automatic execution.
	 * @param userMap
	 * @param fileName
	 * @param infected
	 */
	public static void printVisualizationInfection(UserMap userMap, String fileName, Collection<User> infected) {
		printVisualizationInfection(userMap, fileName, infected, true);
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
