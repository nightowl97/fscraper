import java.util.List;
import java.util.Map;

public interface Scrapable {

    List<String> getHeadlines();

    List<Article> getRecent();

    List<Article> getSimilar(Article a);

}
