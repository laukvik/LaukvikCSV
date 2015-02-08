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
package org.laukvik.csv.columns;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class UrlColumn implements Column<URL> {

    String name;

    public UrlColumn(String name) {
        this.name = name;
    }

    public UrlColumn() {
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String asString(URL value) {
        return value.toExternalForm();
    }

    @Override
    public URL parse(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public int compare(URL one, URL another) {
        return one.toExternalForm().compareTo(another.toExternalForm());
    }
    @Override
    public String toString() {
        return name + "(URL)";
    }

}