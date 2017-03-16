package com.fscraper;

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
        this.mediaContent = new ArrayList<>();
        if (this.hasMedia()){
            // Populate mediaUrls
            for(Element iframe: this.doc.getElementById("article_body").select("iframe")){
                this.mediaContent.add(iframe.attr("src"));
            }
        }
    }

    // Returns list of url of article images.
    private List<String> fetchImages(){
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


    public List<HesArticle> fetchSimilar() throws IOException{
        List<HesArticle> similars = new ArrayList<>();
        Elements divs = this.doc.getElementById("entertainment_stripe_section1").select("div");
        for (Element div: divs){
            similars.add(new HesArticle(div.select("a").attr("href")));
        }
        return similars;
    }

    // Returns a list of headline articles
    static List<HesArticle> fetchHeadlines() throws IOException{
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

    // Returns a list of most recent articles
    static List<HesArticle> fetchRecent() throws IOException{
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