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
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class JsonWriterTest {

    public JsonWriterTest() {
    }

    @Test
    public void emptyRows() throws IOException {
        File file = File.createTempFile("EmptyRows", ".json");

        CSV csv = new CSV();
        csv.addColumn("First");
        csv.addColumn("Last");

        JsonWriter writer = new JsonWriter(new FileOutputStream(file));
        writer.write(csv);
        writer.close();

        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void singleRow() throws IOException {
        File file = File.createTempFile("SingleRow", ".json");

        CSV csv = new CSV();
        csv.addColumn("First");
        csv.addColumn("Last");

        csv.addRow("Morten", "Laukvik");

        JsonWriter writer = new JsonWriter(new FileOutputStream(file));
        writer.write(csv);
        writer.close();
        System.out.println(file.getAbsolutePath());
    }

}