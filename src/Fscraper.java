import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Fscraper {

    public static void main(String[] args) throws IOException{
        String url = "http://www.hespress.com";
        Article a = new Article(getHessHeadlines(url).get(3)[3]);
        System.out.println(a.toString());
    }

    // Returns a list of headlines in the format [{Title1, ImageUrl1, Body1, articleUrl1}, {Title2,..},..]
    public static List<String[]> getHessHeadlines(String url) {
        List<String[]> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println("Could not retrieve page, verify internet connection");
            e.printStackTrace();
            return result;
        }
        Elements headlines = doc.getElementsByClass("headline_article_holder");
        for (Element e: headlines){
            String currentTitle = e.getElementsByTag("a").first().text();
            String currentImg = e.select("img").first().attr("src");
            String currentBody = e.select("div.headline_body").first().ownText();
            String articleUrl = e.select("span").last().select("a").attr("abs:href");
            String[] listElement = {currentTitle, currentImg, currentBody, articleUrl};
            result.add(listElement);
        }
        return result;
    }


}
