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
