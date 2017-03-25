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
import org.laukvik.csv.columns.BigDecimalColumn;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Compares a BigDecimalColumn to a list of values.
 */
public final class BigDecimalMatcher extends RowMatcher {

    /**
     * The BigDecimalColumn.
     */
    private final BigDecimalColumn column;
    /**
     * The list of values.
     */
    private final List<BigDecimal> values;

    /**
     * Compares a BigDecimal with the specified values.
     *
     * @param bigDecimalColumn the BigDecimalColumn
     * @param bigDecimals      the values
     */
    public BigDecimalMatcher(final BigDecimalColumn bigDecimalColumn, final BigDecimal... bigDecimals) {
        this(bigDecimalColumn, Arrays.asList(bigDecimals));
    }

    /**
     * Compares a BigDecimal with the specified values.
     *
     * @param bigDecimalColumn the BigDecimalColumn
     * @param bigDecimals      the values
     */
    public BigDecimalMatcher(final BigDecimalColumn bigDecimalColumn, final List<BigDecimal> bigDecimals) {
        this.column = bigDecimalColumn;
        this.values = bigDecimals;
    }

    /**
     * Returns true when the row matches.
     *
     * @param row the row
     * @return true when the row matches
     */
    public boolean matches(final Row row) {
        BigDecimal d = row.getBigDecimal(column);
        if (values.isEmpty()) {
            return true;
        }
        for (BigDecimal v : values) {
            if (v == null) {
                if (d == null) {
                    return true;
                }
            } else {
                if (v.equals(d)) {
                    return true;
                }
            }
        }
        return false;
    }

}