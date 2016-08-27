package de.retterdesapok.jettydooropener;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class OpenDoorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String PARAM_USERNAME = "username";
	public static String PARAM_PASSWORD = "password";

	GpioController gpio = null;
	GpioPinDigitalOutput pin = null;

	@Override
	public void init() {

		try {
			// Init GPIO
			gpio = GpioFactory.getInstance();
			pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "relay1", PinState.HIGH);

			// We never want low, except for the second where we open the door
			pin.setShutdownOptions(true, PinState.HIGH);
		} catch (Throwable e) {
			// Might happen if we test on a non-raspberry
			System.out.println("Info: No GPIO support.");
		}
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
				request.setAttribute("message", "lock");
				request.setAttribute("backgroundcolor", "red");

				if (user == null || user.getFailedLoginCount() > 3) {
					request.setAttribute("message2", "Wrong username or password.");
				} else if (user.getRemainingLogins() <= 0) {
					response.getOutputStream().println();
					request.setAttribute("message2", "No logins left.");
				} else if (user.getFailedLoginCount() <= 3 && user.getRemainingLogins() > 0) {
					request.setAttribute("message", "lock_open");
					request.setAttribute("backgroundcolor", "green");

					request.getRequestDispatcher("/info.jsp").forward(request, response);

					new Runnable() {

						@Override
						public void run() {
							try {
								openDoor();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}.run();
				}

				return;
			} catch (Exception e) {
				response.getOutputStream().println(e.getLocalizedMessage());
				response.getOutputStream().println(e.getStackTrace().toString());
				response.setStatus(500);
				return;
			}
		}
	}

	public void openDoor() throws InterruptedException {
		if (pin == null) {
			throw new RuntimeException("Output GPIO not initiated. This should not be able to happen.");
		}
		pin.low();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw e;
		} finally {
			// Make sure we always stop
			pin.high();
		}
	}

	public void destroy() {
		gpio.shutdown();
	}
}
