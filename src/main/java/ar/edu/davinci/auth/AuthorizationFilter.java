package ar.edu.davinci.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml", "/" })
public class AuthorizationFilter implements Filter {

	/* Lista de páginas públicas */
	private static final List<String> publicPath =
			Arrays.asList(
					"/",
					"/index.xhtml",
					"/register.xhtml",
					"/login.xhtml",
					".*\\.js.xhtml",
					".*\\.css.xhtml");

	@Inject
	private AuthMb authMb;

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;

			final String path = getCurrentPath(reqt);
			
			// Si es publico
			if (publicPath.stream().anyMatch((pp) -> path.matches(pp))) {
				chain.doFilter(request, response);
				return;
			}
			
			//Si está logueado
			if (authMb != null && authMb.isLogged()) {
				chain.doFilter(request, response);
				return;
			}
			
			resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCurrentPath(HttpServletRequest reqt) {
		String uri = reqt.getRequestURI().replaceAll(";.*", "");
		String contextPath = reqt.getContextPath();

		// Borra el el context path en caso de existir dentro de la uri
		if (uri.startsWith(contextPath)) {
			uri = uri.substring(contextPath.length());
		}
		return uri;
	}

	@Override
	public void destroy() {

	}

}