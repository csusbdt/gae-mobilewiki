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
public class EditServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null)
		{
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		String pageOwner = (String) req.getAttribute("pageOwner");
		if (!user.getNickname().equals(pageOwner))
		{
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		// Pass save and cancel urls to the jsp.
		String pageName = (String) req.getAttribute("pageName");
		req.setAttribute("cancelUrl", "/wiki/" + pageOwner + "/" + pageName);		
		req.setAttribute("saveUrl", "/save/" + pageOwner + "/" + pageName);

		// Pass the page text to the jsp.
		String pageText = WikiPage.getText(pageOwner, pageName);
		req.setAttribute("pageText", pageText);
		
		// Pass the csrf token to the JSP.
		String csrfToken = WikiUser.getCsrfToken(pageOwner);
		req.setAttribute("csrfToken", csrfToken);

		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/edit.jsp");
		jsp.forward(req, resp);
	}
}
