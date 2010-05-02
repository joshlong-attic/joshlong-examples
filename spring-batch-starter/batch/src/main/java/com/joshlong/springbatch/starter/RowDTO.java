package com.joshlong.springbatch.starter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class RowDTO {

    private Map<String, String> row = new HashMap<String, String>();

    public RowDTO(Map<String, String> row) {
        this.row = row;
    }

    public Map<String, String> getRow() {
        return row;
    }

    public void setRow(final Map<String, String> row) {
        this.row = row;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
