


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupGoogleSearch{

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public Map<String,String> getLinks(String s) throws IOException {
		//Taking search term input from console
		Scanner scanner = new Scanner(System.in);
		//System.out.println("Please enter the search term.");
		String searchTerm = s;//scanner.nextLine();
		//System.out.println("Please enter the number of results. Example: 5 10 20");
		int num = 10;//scanner.nextInt();
		scanner.close();
		
		String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
		//without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		
		//below will print HTML data, save it to a file and open in browser to compare
		//System.out.println(doc.html());
		
		//If google search results HTML change the <h3 class="r" to <h3 class="r1"
		//we need to change below accordingly
		Elements results = doc.select("h3.r > a");
		Map<String,String> map = new HashMap<String,String>();
		for (Element result : results) 
		{
			String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
			map.put(linkText,linkHref.substring(7, linkHref.indexOf("&")));
		}
		
		return map;
	}

}
