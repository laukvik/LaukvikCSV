package org.laukvik.csv.report;

import org.junit.Test;
import org.laukvik.csv.CSV;
import org.laukvik.csv.columns.IntegerColumn;

import java.io.File;

/**
 *
 */
public class ReportTest {

    public static File getResource(String filename) {
        ClassLoader classLoader = ReportTest.class.getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }

    @Test
    public void addColumn() throws Exception {
        Report r = new Report();
        IntegerColumn c = new IntegerColumn("frequency");
        Count count = new Count(c);
        r.addColumn(count);
    }

    @Test
    public void addGroup() throws Exception {
        Report r = new Report();
        IntegerColumn c = new IntegerColumn("frequency");
        r.addGroup(c);
    }

    @Test
    public void addSort() throws Exception {

    }

    @Test
    public void buildReport() throws Exception {
        CSV csv = new CSV();
        csv.readFile(getResource("metadata.csv"));
        IntegerColumn presidency = (IntegerColumn) csv.getColumn("presidency");
        Report r = new Report();

        Name name = new Name(presidency);
        Count count = new Count(presidency);
        Max max = new Max(presidency);
        Min min = new Min(presidency);
        Sum sum = new Sum(presidency);
        Avg avg = new Avg(presidency);


        r.addColumn(name);
        r.addColumn(count);
        r.addColumn(max);
        r.addColumn(min);
        r.addColumn(sum);
        r.addColumn(avg);

        CSV c = r.buildReport(csv);

    }

}