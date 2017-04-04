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
package no.laukvik.csv.query;

import no.laukvik.csv.columns.Column;

/**
 * Compares a Column to be not empty.
 */
public final class NotEmptyMatcher implements ValueMatcher<Object> {

    /**
     * The Column.
     */
    private final Column c;

    /**
     * The value of the column must be empty.
     *
     * @param column the column
     */
    public NotEmptyMatcher(final Column column) {
        this.c = column;
    }

    @Override
    public Column getColumn() {
        return c;
    }

    @Override
    public boolean matches(final Object value) {
        return value != null;
    }
}
