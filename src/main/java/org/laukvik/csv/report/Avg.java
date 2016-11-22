package org.laukvik.csv.report;

import org.laukvik.csv.Row;
import org.laukvik.csv.columns.IntegerColumn;

import java.math.BigDecimal;

/**
 * Shows the average of all values.
 */
public final class Avg extends Aggregate {

    /**
     * Container for the total.
     */
    private BigDecimal sum;
    /**
     * The amount of values.
     */
    private long count;

    /**
     * @param column the column
     */
    public Avg(final IntegerColumn column) {
        super(column);
        sum = new BigDecimal(0);
    }

    @Override
    public void aggregate(final Row row) {
        Integer value = row.getInteger((IntegerColumn) getColumn());
        sum = sum.add(new BigDecimal(value));
        count++;
    }

    /**
     * @return
     */
    public BigDecimal getValue() {
        return sum.divideToIntegralValue(new BigDecimal(count));
    }

}