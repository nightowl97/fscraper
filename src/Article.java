import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Article {

    private String title, date, body, author;
    private List<String> imgUrls = new ArrayList<>();

    public Article(String title, String date, String body, String author, List<String> imgUrls) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.author = author;
        this.imgUrls = imgUrls;
    }


    public Article(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        String title = doc.getElementsByClass("page_title").first().text();
        String date = doc.getElementsByClass("story_date").first().text();
        String author = doc.getElementById("article_body")
                .getElementsByClass("story_author").first().text();
        String body = doc.getElementById("article_holder").select("p").text();
        this.title = title;
        this.date = date;
        this.body = body;
        this.author = author;
        //TODO: add all images
        this.imgUrls.add(doc.getElementById("article_holder")
                .getElementsByClass("image")
                .select("img").attr("src"));
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
