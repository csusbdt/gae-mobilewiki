package mobilewiki;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.regex.Pattern;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class WikiPage
{	
	private static final String kind = "page";
	private static final String pageTextProperty = "text";

	private static Pattern angleBracketPattern = Pattern.compile("\\<");
	private static Pattern ownerLinkPattern = Pattern.compile("\\[\\[([\\w\\s]*)\\]\\]"); // [[Some Link]]
	private static Pattern nonOwnerLinkPattern = Pattern.compile("\\[\\[([\\w\\s]*)\\:([\\w\\s]*)\\]\\]"); // [[user_nickname:Some Link]]
	private static Pattern newLinePattern = Pattern.compile("\\n");
	

	public static String getWikifiedText(String pageOwner, String pageName)
	{
		// Pass the wikified page content to the jsp.
		String pageText = WikiPage.getText(pageOwner, pageName);
		pageText = angleBracketPattern.matcher(pageText).replaceAll("&lt;");
		pageText = ownerLinkPattern.matcher(pageText).replaceAll("<a href='$1'>$1</a>");
		pageText = nonOwnerLinkPattern.matcher(pageText).replaceAll("<a href='../$1/$2'>$1:$2</a>");
		pageText = newLinePattern.matcher(pageText).replaceAll("<br>");
		return pageText;
	}
	
	// If page doesn't exist, return empty string.
	public static String getText(String userNickname, String pageName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Key key = new KeyFactory.Builder(WikiUser.kind, userNickname).addChild(kind, pageName).getKey();
		Entity entity = null;
		try
		{
			entity = datastore.get(key);
			Text text = (Text) entity.getProperty(pageTextProperty);
			return text.getValue();
		}
		catch (EntityNotFoundException e)
		{
			return "";
		}
	}

	public static void put(String userNickname, String pageName, String text) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key wikiUserKey = KeyFactory.createKey(WikiUser.kind, userNickname);		
		Entity entity = new Entity(kind, pageName, wikiUserKey);
		entity.setProperty(pageTextProperty, new Text(text));
		datastore.put(entity);
	}
	
	public static void delete(String userNickname, String pageName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key wikiUserKey = KeyFactory.createKey(WikiUser.kind, userNickname);
		Key wikiPageKey = new KeyFactory.Builder(wikiUserKey).addChild(kind, pageName).getKey();
		datastore.delete(wikiPageKey);
		// If user has no other pages, then delete user.
		Query query = new Query(kind, wikiUserKey);
		if (datastore.prepare(query).countEntities(withLimit(1)) == 0)
		{
			datastore.delete(wikiUserKey);
		}
	}
}
