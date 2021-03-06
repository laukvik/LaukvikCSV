package no.laukvik.csv.query;

import no.laukvik.csv.CSV;
import no.laukvik.csv.columns.UrlColumn;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlHostMatcherTest {

    @Test
    public void getColumn() {
        UrlColumn c = new UrlColumn("value");
        UrlHostMatcher m = new UrlHostMatcher(c, "");
        assertEquals(c, m.getColumn());
    }

    @Test
    public void matches() throws Exception {
        CSV csv = new CSV();
        UrlColumn c = csv.addUrlColumn("value");
        UrlHostMatcher m = new UrlHostMatcher(c, "localhost");
        assertTrue(m.matches(new URL("http://localhost")));
        assertFalse(m.matches(new URL("http://wikipedia.org/")));
        assertFalse(m.matches(null));

        String s2 = null;
        UrlHostMatcher m2 = new UrlHostMatcher(c, s2);
        assertFalse(m2.matches(new URL("http://localhost")));
        assertFalse(m2.matches(new URL("http://wikipedia.org/")));
        assertTrue(m2.matches(null));
    }

}