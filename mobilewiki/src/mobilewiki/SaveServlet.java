package mobilewiki;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SaveServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String pageOwner = (String) req.getAttribute("pageOwner");
		String pageName = (String) req.getAttribute("pageName");
		
		String pageText = req.getParameter("pageText");

		
		Logger.getLogger("cse405").warning("pageText = " + pageText);

		// Save the pageText into the datastore.
		
		resp.sendRedirect("/wiki/" + pageOwner + "/" + pageName);
	}
}
