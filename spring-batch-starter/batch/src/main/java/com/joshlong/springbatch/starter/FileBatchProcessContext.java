package com.joshlong.springbatch.starter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * We need a place to stick our metadata at runtime. The object describes the file that needs to be processed, the delimiter,
 * the column names to use to propogate the values, and (optionally) the date of the run.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class FileBatchProcessContext implements Serializable {
    private volatile String[] columnNames;
    private volatile char delimiter;
    private volatile Date date;
    private volatile File file;

    public FileBatchProcessContext(File file, char delimiter, String[] colNames, Date date) {
        this.delimiter = delimiter;
        this.file = file;
        this.columnNames = colNames;
        this.date = date;
    }

    public FileBatchProcessContext(File f, char d, String[] colNames) {
        this(f, d, colNames, new Date());
    }

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public void setColumnNames(final String[] columnNames) {
        this.columnNames = columnNames;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
