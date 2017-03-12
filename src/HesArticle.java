import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HesArticle extends Article{
    /**
     * Scrapes Hes_press.com
     */

    public static final String BASE_URL = "http://www.hespress.com";
    private Document doc;

    HesArticle(String url) throws IOException {
        this.url = url;
        this.doc = Jsoup.connect(url).get();
        this.title = this.doc.getElementsByClass("page_title").first().text();
        this.date = this.doc.getElementsByClass("story_date").first().text();
        try {
            this.author = this.doc.getElementById("article_body")
                    .getElementsByClass("story_author").first().text();
        } catch (NullPointerException e){
            this.author = "No Author";
        }
        this.body = this.doc.getElementById("article_holder").select("p").text();
        this.imgUrls = fetchImages();
        this.headlineImage = this.imgUrls.get(0);
        if (this.hasMedia()){
            // Populate mediaUrls
            for(Element iframe: this.doc.getElementById("article_body").select("iframe")){
                this.mediaUrls.add(iframe.attr("src"));
            }
        }
    }

    // Returns list of url of article images.
    List<String> fetchImages(){
        List<String> result = new ArrayList<>();
        result.add(this.doc.getElementById("article_holder") // Main article image
                .getElementsByClass("image")
                .select("img").attr("src"));
        // Find all images nested in article paragraphs
        for (Element e: this.doc.getElementById("article_holder").select("p")){
            if (e.select("img").size() >= 1){
                result.add(e.select("img").first().attr("src"));
            }
        }
        return result;
    }

    public boolean hasMedia(){
        return !this.doc.getElementById("article_body").select("iframe").isEmpty();
    }

    public List<Article> fetchSimilar(){
        // TODO: implement
        return new ArrayList<>();
    }

    // Returns a list of headlines in the format [{Title1, ImageUrl1, Body1, articleUrl1}, {Title2,..},..]
    static List<HesArticle> getHeadlines() throws IOException{
        List<HesArticle> result = new ArrayList<>();
        Document homedoc = Jsoup.connect(BASE_URL).get();
        Elements headlines = homedoc.getElementsByClass("headline_article_holder");
        for (Element e: headlines){
            String articleUrl = e.select("span").last().select("a")
                    .attr("abs:href");
            result.add(new HesArticle(articleUrl));
        }
        return result;
    }

    static List<HesArticle> getRecent() throws IOException{
        List<HesArticle> result = new ArrayList<>();
        Document homedoc = Jsoup.connect(BASE_URL).get();
        Elements recentdivs = homedoc.select("div.latest_news_box");
        for (Element e: recentdivs){
            String recenturl = e.select("h3").first().select("a")
                    .attr("abs:href");
            result.add(new HesArticle(recenturl));
        }
        return result;
    }
}