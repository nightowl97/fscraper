import java.util.List;

public class Article {
    /**
     * Super class of all website-specific articles
     */

    String url, title, date, body, author, headlineImage;
    List<String> imgUrls, mediaUrls;

    @Override
    public String toString() {
        String summary = (body.length() > 20 ? body.substring(0, 20): body);
        return "Article{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", body= '" + "..." + summary + '\'' +
                ", headlineImg =" + headlineImage + '\'' +
                ", mediaUrls =" + mediaUrls +
                '}';
    }

    public String getUrl() {
        return this.url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return this.date;
    }

    public String getBody() {
        return this.body;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getHeadlineImage() {
        return this.headlineImage;
    }

    public List<String> getImgUrls() {
        return this.imgUrls;
    }

    public List<String> getMediaUrls() {
        return this.mediaUrls;
    }

}
