package com.fscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlyaoumArticle extends Article {

    public static final String BASE_URL = "http://www.alyaoum24.com";
    private Document doc;

    public AlyaoumArticle(String url) {
        this.url = url;
        try{
            this.doc = Jsoup.connect(this.url).get();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.title = this.doc.getElementById("single_content").select("h1").first().text();
        this.date = this.doc.getElementsByClass("post_info").select("time").first().attr("datetime");
        try {
            this.author = this.doc.getElementsByClass("author_name").select("a").first().text();
        } catch (NullPointerException e) {
            this.author = "no Author";
        }
        this.body = this.doc.getElementById("post_content").select("p").text();
        this.headlineImage = this.doc.getElementsByClass("img_holder").select("img").attr("src");
        this.imgUrls = new ArrayList<>();
        this.imgUrls.add(this.headlineImage);
        this.mediaContent = new ArrayList<>();
        if (this.hasMedia()){
            this.mediaContent.add(this.doc.getElementsByClass("video_iframe").select("iframe").first().attr("src"));
            String[] mainVidUrlID = this.mediaContent.get(0).split("/"); // Extract video ID
            this.headlineImage = "http://img.youtube.com/vi/" + mainVidUrlID[mainVidUrlID.length - 1] + "/0.jpg"; // Use it to get thumbnail from youtube API
        }
    }

    public boolean hasMedia() {
        return !this.doc.getElementsByClass("video_iframe").isEmpty();
    }

    public List<AlyaoumArticle> fetchSimilar() {
        List<AlyaoumArticle> similars = new ArrayList<>();
        Elements normalList = this.doc.getElementsByClass("related_post"); // Normal articles should have this div
        Elements mediaList = this.doc.getElementsByClass("related_videos"); // Video articles have this instead
//        System.out.println(normalList);
        if (!normalList.isEmpty()){
            for (Element li: normalList.select("li")){
                AlyaoumArticle article = new AlyaoumArticle(li.select("a").first().attr("href"));
                similars.add(article);
            }
        } else if (!mediaList.isEmpty()){
            for (Element vidDiv: mediaList.select("div.video_list_item")){
                AlyaoumArticle article = new AlyaoumArticle(vidDiv.select("a").first().attr("href"));
                similars.add(article);
            }
        }
        return similars;
    }

    public static List<AlyaoumArticle> fetchHeadlines() {
        List<AlyaoumArticle> headLines = new ArrayList<>();
        try{
            Document homedoc = Jsoup.connect(BASE_URL).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        // TODO: implement
        return headLines;
    }

    public static List<AlyaoumArticle> fetchRecent() {
        List<AlyaoumArticle> recents = new ArrayList<>();
        try{
            Document homedoc = Jsoup.connect(BASE_URL).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        // TODO: implement
        return recents;
    }
}
