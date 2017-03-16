package com.fscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KoooraArticle extends Article {
    /**
     * Scrapes Ko_ora.com
     */

    // TODO: Add browser engine dependency to build the JS generated html.

    public static final String BASE_URL = "http://www.kooora.com";
    private Document doc;

    KoooraArticle(String url) throws IOException{
        this.url = url;
        this.doc = Jsoup.connect(this.url).get();
        this.title = doc.getElementById("articleTitle").text();
        this.date = doc.getElementById("articleTime").text();
        this.author = doc.getElementsByClass("author").first().text();
        this.body = doc.getElementsByClass("articleBody").first().ownText();
        this.headlineImage = doc.getElementsByClass("article_image").first()
                .select("img").attr("src");
        // Populate images list
        this.imgUrls = new ArrayList<>();
        Elements imgs = doc.getElementsByClass("articleBody").first().children().select("img");
        for (Element img: imgs){
            this.imgUrls.add(img.attr("src"));
        }
        // Populate video/embeds list
        this.mediaContent = new ArrayList<>();
        if (this.hasMedia()){
            this.mediaContent.add(this.doc.getElementsByClass("videoContainer").first().html());
        }
    }

    public boolean hasMedia(){
        // Just to Keep the API consistent.
        // TODO: Add browser engine
        return !this.doc.getElementsByClass("article_video").isEmpty();
    }

    public List<KoooraArticle> fetchSimilar() throws IOException{
        List<KoooraArticle> similarResults = new ArrayList<>();
        // TODO: Add browser engine
        return similarResults;
    }

    public static List<KoooraArticle> fetchHeadlines() throws IOException{
        List<KoooraArticle> headlineResults = new ArrayList<>();
        // TODO: Add browser engine
        return headlineResults;
    }

    public static List<KoooraArticle> fetchRecent() throws IOException {
        List<KoooraArticle> recentResults = new ArrayList<>();
        // TODO: TODO: Add browser engine
        return recentResults;
    }

}
