package com.fscraper;

import java.io.IOException;
import java.util.List;

/**
 * TODO: Test article with multiple images at http://www.hespress.com/societe/342362.html
 */

public class Fscraper {

    public static void main(String[] args) throws IOException{
//        HesArticle test = new HesArticle("http://www.hespress.com/videos/342418.html");
//        List<HesArticle> headlines = test.fetchSimilar();
        AlyaoumArticle article = new AlyaoumArticle("http://www.alyaoum24.com/847254.html");
//        List<AlyaoumArticle> articles = AlyaoumArticle.fetchSimilar();
        for (AlyaoumArticle a: article.fetchSimilar()){
            System.out.println(a);
        }
    }




}
