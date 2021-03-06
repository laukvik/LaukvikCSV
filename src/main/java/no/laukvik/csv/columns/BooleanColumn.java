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
package no.laukvik.csv.columns;

/**
 * Column with Boolean as the data type.
 */
public final class BooleanColumn extends Column<Boolean> {

    /**
     * Column with Boolean as the data type.
     *
     * @param name the name of the column
     */
    public BooleanColumn(final String name) {
        super(name);
    }

    /**
     * Returns the value as a String.
     *
     * @param value the value
     * @return value as a String
     */
    @Override
    public String asString(final Boolean value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Parses the value.
     *
     * @param value the string
     * @return the value
     */
    @Override
    public Boolean parse(final String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * Compares two booleans.
     *
     * @param one     one column
     * @param another another column
     * @return the comparable value
     */
    @Override
    public int compare(final Boolean one, final Boolean another) {
        return compareWith(one, another);
    }

}
