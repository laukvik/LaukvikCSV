package org.laukvik.csv.report;

import org.laukvik.csv.Row;
import org.laukvik.csv.columns.IntegerColumn;

import java.math.BigDecimal;

/**
 * Sums all values found.
 */
public final class Sum extends Aggregate {

    /**
     * Container for the sum.
     */
    private BigDecimal sum;

    /**
     *
     * @param column the column
     */
    public Sum(final IntegerColumn column) {
        super(column);
        sum = new BigDecimal(0);
    }

    @Override
    public void aggregate(final Row row) {
        Integer value = row.getInteger((IntegerColumn) getColumn());
        sum.add(new BigDecimal(value == null ? 0  : value));
    }

    /**
     *
     * @return
     */
    public BigDecimal getValue() {
        return sum;
    }

    @Override
    public void reset() {
        sum = new BigDecimal(0);
    }

}
