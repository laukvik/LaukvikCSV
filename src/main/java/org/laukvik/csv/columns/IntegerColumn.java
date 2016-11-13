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

/**
 * Column with Integer as the data type.
 */
public final class IntegerColumn extends Column<Integer> {

    /**
     * Column with Integer as the data type.
     *
     * @param name the name of the column
     */
    public IntegerColumn(final String name) {
        super(name);
    }

    /**
     * Returns the value as a String.
     *
     * @param value the value
     * @return the value as a String
     */
    public String asString(final Integer value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Returns the value of the String.
     *
     * @param value the string
     * @return the integer value
     */
    public Integer parse(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    /**
     * Compares the two values.
     *
     * @param one     one column
     * @param another another column
     * @return the compare value
     */
    public int compare(final Integer one, final Integer another) {
        if (one == null && another == null) {
            return 0;
        } else if (one == null && another != null) {
            return -1;
        } else if (one != null && another == null) {
            return 1;
        }
        return one.compareTo(another);
    }


}
