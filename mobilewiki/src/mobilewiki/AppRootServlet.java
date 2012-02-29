package mobilewiki;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AppRootServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException
	{
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/app-root.jsp");
		jsp.forward(req, resp);
	}
}
