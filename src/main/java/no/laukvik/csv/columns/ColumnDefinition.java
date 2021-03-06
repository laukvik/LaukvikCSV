package no.laukvik.csv.columns;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Defines the features of a column. In simplest form it's only the column name. It more complex it can contain
 * one or more attributes. An attribute is a key and a value.
 * <pre>
 * President(type=VARCHAR[20],primaryKey=true,increment=true,foreignKey=table[id])
 * </pre>
 */
public final class ColumnDefinition {

    /**
     * The name of the column.
     */
    private String columnName;
    /**
     * The attributeMap with keys and values.
     */
    private Map<String, Attribute> attributeMap;

    /**
     * Parses the column definition string and extracts column name and all attributes.
     *
     * @param compressedColumnDefinition the compressed column to read
     * @return the ColumnDefinition
     */
    public static ColumnDefinition parse(final String compressedColumnDefinition) {
        if (compressedColumnDefinition == null || compressedColumnDefinition.trim().isEmpty()) {
            return null;
        }
        ColumnDefinition cd = new ColumnDefinition();
        cd.attributeMap = new HashMap<>();
        /* Extract extra information about the column*/
        cd.columnName = null;
        // Look for metadata in column headers
        int firstIndex = compressedColumnDefinition.indexOf('(');
        if (firstIndex == -1) {
            // No extra information
            cd.columnName = compressedColumnDefinition;
        } else {
            // Found extra information
            int lastIndex = compressedColumnDefinition.indexOf(')', firstIndex);
            cd.columnName = compressedColumnDefinition.substring(0, firstIndex);
            if (lastIndex != -1) {
                // String with metadata
                String extraDetails = compressedColumnDefinition.substring(firstIndex + 1, lastIndex).trim();
                String[] keyValues;
                if (extraDetails.contains(",")) {
                    keyValues = extraDetails.split(",");
                } else {
                    keyValues = new String[]{extraDetails};
                }
                // Extract all key/value pairs from metadata
                for (String keyValue : keyValues) {
                    if (keyValue.contains("=")) {
                        String[] arr = keyValue.split("=");
                        String key = arr[0];
                        if (arr.length > 1) {
                            // Has value
                            String value = arr[1];
                            int extraIndex = value.indexOf('[');
                            if (extraIndex > -1) {
                                // Extra value
                                int extraLastIndex = value.lastIndexOf(']');
                                if (extraLastIndex > extraIndex) {
                                    String extraValue = value.substring(0, extraIndex);
                                    String extraNumber = value.substring(extraIndex + 1, extraLastIndex);
                                    cd.setAttribute(key, new ColumnDefinition.Attribute(extraValue, extraNumber));
                                } else {
                                    // End symbol is isBefore start symbol
                                    String extraValue = value.substring(0, extraIndex);
                                    String extraNumber = value.substring(extraIndex + 1);
                                    cd.setAttribute(key, new ColumnDefinition.Attribute(extraValue, extraNumber));
                                }
                            } else {
                                // No extra value
                                cd.setAttribute(key, new ColumnDefinition.Attribute(value));
                            }

                        }
                    }
                }
            }
        }
        return cd;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the string
     */
    String toCompressed() {
        StringBuilder b = new StringBuilder();
        b.append(columnName);
        if (!isEmpty()) {
            b.append("(");
            int x = 0;
            for (String key : attributeMap.keySet()) {
                if (x > 0) {
                    b.append(",");
                }
                b.append(key);
                b.append("=");
                b.append(attributeMap.get(key).toCompressed());
                x++;
            }
            b.append(")");
        }
        return b.toString();
    }

    /**
     * Sets a named attribute.
     *
     * @param name      the name
     * @param attribute the attribute
     */
    void setAttribute(final String name, final Attribute attribute) {
        if (name != null && attribute != null && !name.trim().isEmpty()) {
            attributeMap.put(name.toLowerCase(), attribute);
        }
    }

    /**
     * Sets an attribute.
     *
     * @param name the name
     */
    void removeAttribute(final String name) {
        attributeMap.remove(name.toLowerCase());
    }

    /**
     * Returns the column name.
     *
     * @return the column name
     */
    String getColumnName() {
        return columnName;
    }

    /**
     * Returns the value of the attribute.
     *
     * @param attributeName the name of the attribute
     * @return the value
     */
    Attribute get(final String attributeName) {
        return attributeMap.get(attributeName.toLowerCase());
    }

    /**
     * Returns the boolean value of an attribute.
     *
     * @param attributeName the name of the attribute
     * @return the value
     */
    boolean getBoolean(final String attributeName) {
        Attribute v = get(attributeName);
        if (v == null) {
            return false;
        }
        return "true".equalsIgnoreCase(v.getValue());
    }

    /**
     * Returns true if there are no attributes.
     *
     * @return true when no attributes.
     */
    boolean isEmpty() {
        return attributeMap.isEmpty();
    }

    /**
     * Returns the amount of attributes.
     *
     * @return the amount
     */
    int getAttributeCount() {
        return attributeMap.keySet().size();
    }

    /**
     * Returns a setRaw of all attribute names.
     *
     * @return the names
     */
    Set<String> getAttributeNames() {
        return attributeMap.keySet();
    }


    /**
     * Specifies an attribute value with optional extra value.
     */
    static final class Attribute {

        /**
         * The value.
         */
        private String value;
        /**
         * The optional value.
         */
        private String optional;

        /**
         * Creates an attribute with no extra value.
         *
         * @param value the value
         */
        Attribute(final String value) {
            this.value = value;
        }

        /**
         * Creates an attribute with value and the optional extra value.
         *
         * @param value    the value
         * @param optional the optional value
         */
        Attribute(final String value, final String optional) {
            this.value = value;
            this.optional = optional;
        }

        /**
         * Returns the compressed version.
         *
         * @return the compressed version
         */
        String toCompressed() {
            if (optional != null) {
                return value + "[" + optional + "]";
            } else {
                return value;
            }
        }

        /**
         * Returns the value.
         *
         * @return the value
         */
        String getValue() {
            return value;
        }

        /**
         * Sets the value.
         *
         * @param value the value
         */
        void setValue(final String value) {
            this.value = value;
        }

        /**
         * Returns the optional value.
         *
         * @return the optional value
         */
        String getOptional() {
            return optional;
        }

        /**
         * Sets the optional value.
         *
         * @param optional the optional value
         */
        void setOptional(final String optional) {
            this.optional = optional;
        }
    }


}
