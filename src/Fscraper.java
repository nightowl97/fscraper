
import java.io.IOException;

/**
 * TODO: Add Tests
 * TODO: Test article with multiple images at http://www.hespress.com/societe/342362.html
 * TODO: Test article with video (no author) at http://www.hespress.com/videos/342418.html
 */

public class Fscraper {

    public static void main(String[] args) throws IOException{
        HesArticle test = new HesArticle("http://www.hespress.com/international/342454.html");
        boolean b = test.hasMedia();
        System.out.println(test.getHeadlineImage());
    }




}
