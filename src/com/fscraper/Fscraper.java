package com.fscraper;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Add Tests
 * TODO: Test article with multiple images at http://www.hespress.com/societe/342362.html
 * TODO: Test article with video (no author) at http://www.hespress.com/videos/342418.html
 */

public class Fscraper {

    public static void main(String[] args) throws IOException{
        HesArticle test = new HesArticle("http://www.kooora.com/?n=570494&o=n");
        List<HesArticle> headlines = test.fetchSimilar();
        System.out.println(headlines.toString());
    }




}
