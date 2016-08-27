package de.retterdesapok.jettydooropener;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateDatabaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
				
		// Init database
		Database.GET();
		
		response.getOutputStream().println("Connection string: " + Database.GET().getConnectionString());

		response.setStatus(200);
	}
}
