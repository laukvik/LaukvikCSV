/*
 * Copyright 2015 Laukviks Bedrifter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laukvik.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.laukvik.csv.columns.Column;
import org.laukvik.csv.columns.StringColumn;
import org.laukvik.csv.query.Query;


/**
 * An API for reading and writing to CSV. The implementation is based on the
 * specficiations from http://tools.ietf.org/rfc/rfc4180.txt
 *
 * Read whole file into memory<br>
 *
 * <code>
 * CSV c = new CSV( new File("nerds.csv") );
 * </code>
 *
 * Write CSV to file
 *
 * <code>
 * CSV c = new CSV("First","Last");
 * c.addRow( "Bill","Gates" );
 * c.addRow( new Row("Steve","Jobs") );
 * c.removeColumn("First");
 * c.addColumn("Email);
 * c.addColumn("Address",0);
 * c.write( new File("nerds.csv") );
 * </code>
 *
 * <code>
 * CSV c = new CSV( Person.class );
 * c.add( new Person("Bill","Gates") );
 * c.write( new File("persons.csv") );
 * </code>
 *
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class CSV implements Serializable {

    public final static String MIME_TYPE = "text/csv";
    public final static String FILE_EXTENSION = "csv";
    public final static Charset CHARSET_DEFAULT = Charset.forName("utf-8");

    public final static char LINEFEED = 10;
    public final static char RETURN = 13;
    public final static char COMMA = ',';
    public final static char SEMINCOLON = ';';
    public final static char PIPE = '|';
    public final static char TAB = '\t';
    public final static char QUOTE = '"';
    public final static String CRLF = "\r\n";

    protected MetaData metaData;
    protected List<Row> rows;
    protected Charset charset;

    public CSV(MetaData metaData) {
        this.metaData = metaData;
        this.rows = new ArrayList<>();
        this.charset = CHARSET_DEFAULT;
    }

    public CSV() {
        this.metaData = new MetaData();
        this.rows = new ArrayList<>();
        this.charset = CHARSET_DEFAULT;
    }

    public CSV(Column... columns) {
        this.metaData = new MetaData(columns);
        this.rows = new ArrayList<>();
        this.charset = CHARSET_DEFAULT;
    }

    public CSV(File file, MetaData metadata) throws IOException, ParseException, InvalidRowDataException {
        this(new FileInputStream(file), CHARSET_DEFAULT, metadata);
    }

    public CSV(File file) throws IOException, ParseException, ParseException {
        this(new FileInputStream(file), CHARSET_DEFAULT, null);
    }

    public CSV(File file, Charset charset) throws IOException, ParseException {
        this(new FileInputStream(file), charset, null);
    }

    public CSV(InputStream inputStream, Charset charset) throws IOException, InvalidRowDataException {
        this(inputStream, charset, null);
    }

    public CSV(InputStream inputStream, Charset charset, MetaData metadata) throws IOException, InvalidRowDataException {
        rows = new ArrayList<>();
        this.charset = charset;


        try (CsvReader reader = new CsvReader(inputStream, charset, metadata)) {
            if (metadata == null) {
                this.metaData = reader.getMetaData();
            } else {
                this.metaData = metadata;

            }
            while (reader.hasNext()) {
                Row row = reader.getRow();
                row.setMetaData(metaData);
                if (row.getValues().size() != metaData.getColumnCount()) {
                    throw new InvalidRowDataException(row.getValues().size(), metaData.getColumnCount(), rows.size(), row);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            throw e;
        }
    }


    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public int getRowCount() {
        return rows.size();
    }

    public Row getRow(int rowIndex) {
        if (rowIndex > rows.size()) {
            throw new RowNotFoundException(rowIndex, rows.size());
        }
        return rows.get(rowIndex);
    }

    public Row addRow(Row row) {
        if (row.getValues().size() != metaData.getColumnCount()) {
            throw new IllegalArgumentException("Incorrect columns in row");
        }
        row.setMetaData(metaData);
        rows.add(row);
        return row;
    }

    public Row addRow(String... values) {
        return addRow(new Row(values));
    }

    public void removeRow(int rowIndex) {
        rows.remove(rowIndex);
    }

    public void removeAllRows() {
        rows.clear();
    }

//    public Query createQuery() {
//        Query q = new Query(metaData, this);
//        return q;
//    }

//    /**
//     * Find all rows matching the specified filters
//     *
//     * @param query
//     * @return
//     */
//    public List<Row> findWithQuery(Query query) {
//        List<Row> filteredRows = new ArrayList<>();
//        int matchesRequired = query.getFilters().size();
//        for (Row r : rows) {
//            if (matchesRequired == 0) {
//                /* Dont use filters - add all */
//                filteredRows.add(r);
//            } else {
//                /* Use filtering */
//                int matchCount = 0;
//                for (Filter f : query.getFilters()) {
//                    if (f.accepts(r)) {
//                        matchCount++;
//                    }
//                }
//                if (matchCount == matchesRequired) {
//                    filteredRows.add(r);
//                }
//            }
//        }
//        if (!query.getSortOrders().isEmpty()) {
//            MultipleRowSorter multipleSorter = new MultipleRowSorter(query.getSortOrders());
//            Collections.sort(filteredRows, multipleSorter);
//        }
//        return filteredRows;
//    }

    /**
     * Writes the contents to file
     *
     * @param file
     * @throws IOException
     */
    public void write(File file) throws IOException {
        try (CsvWriter writer = new CsvWriter(new FileOutputStream(file), charset)) {
            writer.writeMetaData(metaData);
            for (Row row : rows) {
                writer.writeRow(row);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Removes all rows
     *
     * @param fromRowIndex
     * @param endRowIndex
     */
    public void removeRows(int fromRowIndex, int endRowIndex) {
        rows.subList(fromRowIndex, endRowIndex + 1).clear();
    }

    public Row insertRow(Row row, int rowIndex) {
        row.setMetaData(metaData);
        rows.add(rowIndex, row);
        return row;
    }

    public String addColumn(String name) {
        metaData.addColumn(new StringColumn(name));
        for (Row r : rows) {
            r.add("");
        }
        return name;
    }

    public String insertColumn(String name, int columnIndex) {
        metaData.addColumn(name, columnIndex);
        for (Row r : rows) {
            r.insert("", columnIndex);
        }
        return name;
    }

    /**
     * Removes the column with the specified index
     *
     * @param index
     */
    public void removeColumn(int index) {
        metaData.removeColumn(index);
        for (Row r : rows) {
            r.remove(index);
        }
    }

    private static File getLibrary() {
        return new File(System.getProperty("user.home"), "Library");
    }

    private static File getHome() {
        File file = new File(getLibrary(), "org.laukvik.csv");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getFile(Class aClass) {
        File file = new File(getHome(), aClass.getCanonicalName() + ".csv");
        return file;
    }

    /**
     * Find an object directly
     *
     * @param <T>
     * @param aClass
     * @return
     */
    public static <T> T find(Class<T> aClass) {
        return null;
    }

    private static <T> T createInstance(Row row, Class<T> aClass) throws InstantiationException, IllegalAccessException {
        Object instance = aClass.newInstance();

        /* Iterate all fields in object*/
        for (Field f : instance.getClass().getDeclaredFields()) {
            /* Set accessible to allow injecting private fields - otherwise an exception will occur*/
            f.setAccessible(true);
            /* Get field value */
            Object value = f.get(instance);
            /* Find the name of the field - in code */
            String nameAttribute = f.getName();

            if (f.getType() == String.class) {
                f.set(instance, row.getString(nameAttribute));
            } else if (f.getType() == Integer.class) {
                f.set(instance, row.getInteger(nameAttribute));
            } else if (f.getType() == URL.class) {
                f.set(instance, row.getURL(nameAttribute));
            }

            f.setAccessible(false);
        }
        return (T) instance;
    }

    public static <T> List<T> findAll(Class<T> aClass) {
        File file = getFile(aClass);
        try {
            return findAll(new FileInputStream(file), CSV.CHARSET_DEFAULT, aClass);
        } catch (FileNotFoundException ex) {
            List<T> items = new ArrayList<>();
            return items;
        }
    }

    public static <T> List<T> findAll(InputStream inputStream, Charset charset, Class<T> aClass) {
        List<T> items = new ArrayList<>();
        try (CsvReader reader = new CsvReader(inputStream, charset, null)) {
            int x = 0;
            while (reader.hasNext()) {
                Row row = reader.getRow();
                row.setMetaData(reader.getMetaData());
                if (row.getValues().size() != reader.getMetaData().getColumnCount()) {
//                    throw new InvalidRowDataException(row.getValues().size(), reader.getMetaData().getColumnCount(), x, row);
                }
                items.add(createInstance(row, aClass));
            }
        } catch (IOException e) {

        } catch (InstantiationException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    public static <T> void saveAll(List<? extends Object> objects, Class<T> aClass) throws IllegalArgumentException, IllegalAccessException {
        File file = CSV.getFile(aClass);
        try (CsvWriter writer = new CsvWriter(new FileOutputStream(file), CSV.CHARSET_DEFAULT)) {
            writer.writeMetaData(aClass);
            for (Object o : objects) {
                writer.writeEntityRow(o);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Query findByQuery() {
        return new Query(metaData, this);
    }

}
