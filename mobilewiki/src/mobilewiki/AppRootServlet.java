package mobilewiki;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AppRootServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException
	{

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null)
		{
			// User is not logged in.
			String loginUrl = userService.createLoginURL(req.getServletPath() + req.getPathInfo());
			req.setAttribute("loginUrl", loginUrl);
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/app-root.jsp");
			jsp.forward(req, resp);
		}
		else
		{
			resp.sendRedirect("/wiki/" + user.getNickname() + "/home");
		}
	}
}
