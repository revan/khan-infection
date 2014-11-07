import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Infection {

	public static void main(String[] args) {
		UserMap userMap = new UserMap();
		printVisualization(userMap);
		
		//TODO Total Infection
		
		//TODO Partial Infection
	}
	
	/**
	 * Assembles an SVG visualization using viz.js
	 * @param userMap
	 */
	public static void printVisualization(UserMap userMap) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream("out.html"));
			out.println("<html><body><script type='text/vnd.graphviz' id='infect'>digraph G {");
			
			userMap.printUserMap(out);
			
			out.println("}</script><script src='viz.js'></script><script> function inspect(s) { return '<pre>' + "
					+ "s.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\'/g, '&quot;') + '</pre>' } function "
					+ "src(id) { return document.getElementById(id).innerHTML; } function example(id, format, engine) "
					+ "{ var result; try { result = Viz(src(id), format, engine); if (format === 'svg') return result;"
					+ " else return inspect(result); } catch(e) { return inspect(e.toString()); } } "
					+ "document.body.innerHTML += example('infect', 'svg'); </script> </body> </html>");
			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
