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
package org.laukvik.csv.query;

import org.laukvik.csv.Row;
import org.laukvik.csv.columns.Column;

/**
 * Compares a Column to be in an array of objects.
 *
 * @param <T> the type of object
 */
public final class IsInMatcher<T> extends RowMatcher {

    /** The column.  */
    private Column<T> column;
    /** The values. */
    private T[] values;

    /**
     * The value of the column must be in the values.
     *
     * @param column the column
     * @param values the values
     */
    public IsInMatcher(final Column<T> column, final T... values) {
        super();
        this.column = column;
        this.values = values;
    }

    /**
     * Returns true when the row matches.
     *
     * @param row the row
     * @return true when the row matches
     */
    public boolean matches(final Row row) {
        if (values == null || values.length == 0) {
            return true;
        }
        Object o = row.get(column);
        if (o == null) {
            return false;
        }
        for (T value : values) {
            if (value != null && o.equals(value)) {
                return true;
            }
        }
        return false;
    }

}
