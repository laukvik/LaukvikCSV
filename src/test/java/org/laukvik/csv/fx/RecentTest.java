package org.laukvik.csv.fx;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Morten Laukvik
 */
public class RecentTest {

    @Test
    public void shouldRememberCorrect() throws IOException {
        File file = File.createTempFile("Recent", "csv");
        Recent r = new Recent(file);
        r.open( new File("first.txt") );
        assertEquals(1, r.getList().size());
        r.open( new File("second.txt") );
        assertEquals(2, r.getList().size());
        r.open( new File("third.txt") );
        assertEquals(3, r.getList().size());
    }

    @Test
    public void shouldReadSaved() throws IOException {
        File file = File.createTempFile("Recent", "csv");
        Recent r = new Recent(file, 3);
        r.open( new File("first.txt") );
        r.open( new File("second.txt") );
        r.open( new File("third.txt") );
        r.open(new File("fourth.txt"));
        r.open(new File("fifth.txt"));
        r.save();
        r = null;
        Recent r2 = new Recent(file);
        for (File f : r2.getList()){
        }
        assertEquals(3, r2.getList().size());
    }

}