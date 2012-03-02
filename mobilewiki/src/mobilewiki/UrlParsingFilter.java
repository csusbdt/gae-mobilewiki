package mobilewiki;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlParsingFilter implements Filter
{
	private static final String ILLEGAL_REQUEST = "ILLEGAL_REQUEST";
	
	private Pattern urlPattern = 
	        Pattern.compile("/([\\w\\-@\\.]+)/([ \\w\\-\\.]+)");
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;

		String pathInfo = httpReq.getPathInfo();
		if (pathInfo == null)
		{
			// We get here when url is "/wiki", "/edit", "/save"
			httpResp.sendError(300, ILLEGAL_REQUEST);
			return;
		}

		// Make sure url has only digits, letters, space, underscore, dash, 
        // arobase, and period.
		Matcher urlMatcher = urlPattern.matcher(pathInfo);
		if (!urlMatcher.matches())
		{
			httpResp.sendError(300, ILLEGAL_REQUEST);
			return;
		}
		String pageOwner = urlMatcher.group(1);
		String pageName = urlMatcher.group(2);
		
		httpReq.setAttribute("pageOwner", pageOwner);
		httpReq.setAttribute("pageName", pageName);

		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}
}
