package fr.marcdev.urlsbatch.model;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class {@code URLQuery} represents a Uniform Resource
 * Locator
 * @see {@code URL}
 * @author Marc Siramy
 */
public class URLQuery implements AutoCloseable {
		
	private static final Pattern URL_PATTERN = Pattern.compile("[=\\&]");
	
	private URL url;
	private URI uri;
	private Map<String, String> queryParameters;

	public URLQuery(final URL url, final String keyParam) {
		synchronized(this) {
			this.url = url;
			this.queryParameters = new HashMap<>();
			this.setQueryParameters(url.getQuery(), keyParam);
		}
	}
	
	public URLQuery(URI uri, String keyParam) {
		synchronized(this) {
			this.uri = uri;
			this.queryParameters = new HashMap<>();
			this.setQueryParameters(uri.getQuery(), keyParam);
		}
	}

	private synchronized void setQueryParameters(final String s, final String keyParam) {
	    final var parsed = URL_PATTERN.split(s);
	    for (int i=0; i< parsed.length; i+=2){
	    	if(Objects.equals(keyParam, parsed[i])) {
	    		this.queryParameters.put(parsed[i], parsed[i+1]);
	    		break;
	    	}
	     } 
	}

	public Map<String, String> getQueryParameters() {
		return this.queryParameters;
	}
	
	public URL getURL() {
		return this.url;
	}
	
	public URI getURI() {
		return this.uri;
	}

	@Override
	public void close() throws Exception {
		this.queryParameters = null;
		this.url = null;
		this.uri = null;
	}
}
