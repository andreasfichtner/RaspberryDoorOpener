package de.retterdesapok.jettydooropener;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenDoorServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

	@Override 
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException
	  {
	      response.getOutputStream().print("Authenticating...");
	      try {
			DatabaseHelper.ensureDatabaseExists();
		} catch (Exception e) {
			response.getWriter().print(e.getMessage());
			response.setStatus(403);
		}
	  }
}
