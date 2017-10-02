package foo.bar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class GZipServer
{
	public void init() throws Exception
	{
		Server server = new Server(8080);
		GzipHandler gzHandler = new GzipHandler();
		gzHandler.setCheckGzExists(true);
		gzHandler.setIncludedMimeTypes("text/html", "text/xml", "text/plain", "text/css", "text/javascript",
				"application/javascript", "text/cache-manifest");

		ServletHolder servletHolder = new ServletHolder();
		servletHolder.setInitParameter("precompressed", "true");
		servletHolder.setInitParameter("gzip", "true");
		servletHolder.setServlet(new DefaultServlet());

		ServletContextHandler ctx = new ServletContextHandler();
		ctx.setContextPath("/");
		ctx.setResourceBase("./src/main/resources/");
		ctx.setGzipHandler(gzHandler);
        // Add default servlet
        ctx.addServlet(servletHolder, "/");
        
        ctx.getMimeTypes().addMimeMapping("html", "text/html;");
        ctx.getMimeTypes().addMimeMapping("txt", "text/plain;");
        ctx.getMimeTypes().addMimeMapping("json", "application/json;");
        ctx.getMimeTypes().addMimeMapping("manifest", "text/cache-manifest;");
        
		server.setHandler(ctx);
		server.start();
	}

	public static void main(String[] args) throws Exception
	{
		new GZipServer().init();
	}
}
