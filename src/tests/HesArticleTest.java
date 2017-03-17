package tests;

import com.fscraper.HesArticle;

import java.util.List;

//TODO: pMore robust tests.

import static org.junit.jupiter.api.Assertions.*;

class HesArticleTest {

    @org.junit.jupiter.api.Test
    void hasMedia() {
        HesArticle vidarticle = new HesArticle("http://www.hespress.com/videos/342936.html");
        HesArticle novidarticle = new HesArticle("http://www.hespress.com/sport/343014.html");
        assertNotNull(vidarticle);
        assertNotNull(novidarticle);
        assertTrue(vidarticle.hasMedia());
        assertFalse(novidarticle.hasMedia());
        assertThrows(Exception.class, () -> new HesArticle("http://www.hespress.com/sport/34s.html"));
    }

    @org.junit.jupiter.api.Test
    void fetchSimilar() {
        HesArticle article = new HesArticle("http://www.hespress.com/sport/343014.html");
        List<HesArticle> similar = article.fetchSimilar();
        // Replace with latest before test
        assertEquals("هجوم المصورين على بنكيران", similar.get(0).getTitle());
        assertEquals("الخميس 16 مارس 2017 - 16:20", similar.get(1).getDate());
    }

    @org.junit.jupiter.api.Test
    void fetchImgUrls() {
        HesArticle fiveImgArticle = new HesArticle("http://www.hespress.com/societe/342362.html");
        HesArticle noImgArticle = new HesArticle("http://www.hespress.com/videos/342418.html");
        assertEquals(5, fiveImgArticle.getImgUrls().size());
        System.out.println("----------: " + noImgArticle.getImgUrls().toString());
        assertEquals(0, noImgArticle.getImgUrls().size());
    }


}