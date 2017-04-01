package org.laukvik.csv.query;

import org.junit.Test;
import org.laukvik.csv.CSV;
import org.laukvik.csv.columns.StringColumn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotEmptyMatcherTest {

    @Test
    public void getColumn() {
        StringColumn c = new StringColumn("value");
        NotEmptyMatcher m = new NotEmptyMatcher(c);
        assertEquals(c, m.getColumn());
    }

    @Test
    public void matches() throws Exception {
        CSV csv = new CSV();
        StringColumn first = csv.addStringColumn("first");
        NotEmptyMatcher m = new NotEmptyMatcher(first);
        assertTrue(m.matches("Bob"));
        assertFalse(m.matches(null));
    }

}