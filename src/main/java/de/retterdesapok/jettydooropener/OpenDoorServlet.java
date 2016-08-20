package de.retterdesapok.jettydooropener;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenDoorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String PARAM_USERNAME = "username";
	public static String PARAM_PASSWORD = "password";

	@Override
	public void init() {
	      // TODO init gpio
	   }
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String username = request.getParameter(PARAM_USERNAME);
		String password = request.getParameter(PARAM_PASSWORD);

		if (username == null || username.length() == 0 || password == null || password.length() == 0) {
			response.getOutputStream().println("Invalid arguments sent. Expected username and password.");
			response.setStatus(400);
			return;
		}

		response.getOutputStream().print("Authenticating...");
		String passwordmd5 = null;

		try {
			passwordmd5 = Utilities.getMD5(password);
		} catch (NoSuchAlgorithmException e) {
			response.getOutputStream().println(e.getLocalizedMessage());
			response.getOutputStream().println(e.getStackTrace().toString());
			response.setStatus(500);
			return;
		}

		if (passwordmd5 != null) {
			try {
				User user = DatabaseHelper.loginUserWithNameAndPasswordHash(username, passwordmd5);
				request.setAttribute("textcolor", "white-text");

				if (user == null || user.getFailedLoginCount() > 3) {
					request.setAttribute("message", "lock");
					request.setAttribute("message2", "Wrong username or password.");
					request.setAttribute("backgroundcolor", "red");
				} else if (user.getRemainingLogins() <= 0) {
					response.getOutputStream().println();
					request.setAttribute("message", "lock");
					request.setAttribute("message2", "No logins left.");
					request.setAttribute("backgroundcolor", "red");
				} else if (user.getFailedLoginCount() <= 3 && user.getRemainingLogins() > 0) {
					request.setAttribute("message", "lock_open");
					request.setAttribute("backgroundcolor", "green");
					
					// TODO open door
				}

				request.getRequestDispatcher("/info.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				response.getOutputStream().println(e.getLocalizedMessage());
				response.getOutputStream().println(e.getStackTrace().toString());
				response.setStatus(500);
				return;
			}
		}
	}

	public void destroy() {
	      // TODO release gpio in correct state
	   }
}
