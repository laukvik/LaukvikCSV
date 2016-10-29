package org.laukvik.csv.fx;

import org.laukvik.csv.CSV;
import org.laukvik.csv.Row;
import org.laukvik.csv.columns.StringColumn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Remembers a list of recently opened files.
 *
 * @author Morten Laukvik
 */
public final class Recent {

    final private CSV csv;
    final private File file;
    private final int limit;

    public Recent(final File file, final int limit) {
        this.csv = new CSV();
        this.file = file;
        this.limit = limit;
        load();
    }

    public Recent(final File file) {
        this(file, 10);
    }

    /**
     * Returns the Library folder for the user.
     *
     * @return the folder called Library in the users home folder
     */
    private static File getLibrary() {
        return new File(System.getProperty("user.home"), "Library");
    }

    /**
     * Returns the folder where all configuration of CSV is.
     *
     * @return the folder called org.laukvik.csv in the users library folder
     */
    private static File getHome() {
        File file = new File(getLibrary(), "org.laukvik.csv");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getConfigurationFile() {
        return new File(getHome(), "recent.csv");
    }

    private void load(){
        if (!file.exists() || file.length() == 0){
            csv.getMetaData().addColumn("filename");
        } else {
            try {
                csv.readFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean save(){
        try {
            csv.writeFile(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<File> getList(){
        StringColumn c = (StringColumn)csv.getMetaData().getColumn(0);
        List<File> list = new ArrayList<>();
        for (int y=0; y<csv.getRowCount(); y++){
            Row r = csv.getRow(y);
            list.add( new File(r.getString(c)) );
        }
        return list;
    }

    public void open(final File file){
        StringColumn c = (StringColumn) csv.getMetaData().getColumn(0);
        csv.addRow().update(c, file.getAbsolutePath());
        if (csv.getRowCount() > limit) {
            int extra = csv.getRowCount() - limit;
            csv.removeRowsBetween(0, extra);
        }
        save();
    }

    public void clear() {
        csv.removeRows();
    }

}
