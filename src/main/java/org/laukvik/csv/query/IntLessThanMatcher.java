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
import org.laukvik.csv.columns.IntegerColumn;

/**
 * Compares a IntegerColumn to be less than a value.
 */
public final class IntLessThanMatcher extends RowMatcher {

    /**
     * The value to match.
     */
    private final int value;
    /** The column to match. */
    private final IntegerColumn column;

    /**
     * The value of the column must be value.
     *
     * @param integerColumn the column
     * @param value         the value
     */
    public IntLessThanMatcher(final IntegerColumn integerColumn, final int value) {
        super();
        this.column = integerColumn;
        this.value = value;
    }

    /**
     * Returns true when the row matches.
     *
     * @param row the row
     * @return true when the row matches
     */
    public boolean matches(final Row row) {
        Integer i = row.getInteger(column);
        return i != null && i < value;
    }

}