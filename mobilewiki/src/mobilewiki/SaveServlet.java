package mobilewiki;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class SaveServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Check that user is logged in.
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null)
		{
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// Check that user is page owner.
		String pageOwner = (String) req.getAttribute("pageOwner");
		if (!user.getNickname().equals(pageOwner))
		{
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// Check for valid CSRF token.
		String givenCsrfToken = req.getParameter("csrfToken");
		String actualCsrfToken = WikiUser.getCsrfToken(pageOwner);
		if (givenCsrfToken == null || !givenCsrfToken.equals(actualCsrfToken))
		{
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		// Check for page text.
		String pageText = req.getParameter("pageText");
		if (pageText == null)
		{
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String pageName = (String) req.getAttribute("pageName");
				
		WikiPage.put(pageOwner, pageName, pageText);
		
		resp.sendRedirect("/wiki/" + pageOwner + "/" + pageName);
	}
}
