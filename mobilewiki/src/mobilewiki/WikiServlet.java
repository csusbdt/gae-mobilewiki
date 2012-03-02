package mobilewiki;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class WikiServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String pageOwner = (String) req.getAttribute("pageOwner");
		String pageName = (String) req.getAttribute("pageName");
		String pageText = "Page text from the datastore.";		
		req.setAttribute("pageText", pageText);

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null)
		{
			// User is not logged in.
			String loginUrl = userService.createLoginURL(req.getServletPath() + req.getPathInfo());
			req.setAttribute("loginUrl", loginUrl);
		}
		else
		{
			// User is logged in.
			String logoutUrl = userService.createLogoutURL(req.getServletPath() + req.getPathInfo());
			req.setAttribute("logoutUrl", logoutUrl);
			String userName = user.getNickname();		
			req.setAttribute("homeUrl", "/wiki/" + userName + "/home");
			if (userName.equals(pageOwner))
			{
				req.setAttribute("editUrl", "/edit/" + userName + "/" + pageName);
			}
		}

		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/wiki.jsp");
		jsp.forward(req, resp);
	}
}
