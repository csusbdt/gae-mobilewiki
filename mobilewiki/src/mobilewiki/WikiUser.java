package mobilewiki;

import java.security.SecureRandom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class WikiUser
{
	public static final String kind = "user";
	private static final String csrfTokenProperty = "csrf";
	
	private static SecureRandom secureRandom = new SecureRandom();
	
	// If the token doesn't exist yet, then create it.
	public static String getCsrfToken(String userNickname)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(kind, userNickname);
		try
		{
			Entity entity = datastore.get(key);
			return (String) entity.getProperty(csrfTokenProperty);
		}
		catch (EntityNotFoundException e)
		{
			String csrfToken = "" + secureRandom.nextLong();
			Entity entity = new Entity(key);
			entity.setProperty(csrfTokenProperty, csrfToken);
			datastore.put(entity);
			return csrfToken;
		}
	}
}

