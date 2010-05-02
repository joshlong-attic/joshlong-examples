package com.joshlong.springbatch.starter;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class MyCustomItemWriter implements ItemWriter<RowDTO> {
    private String[] names;
    private String delimiter;

    public void write(final List<? extends RowDTO> rowDTOs)
            throws Exception {

        System.out.println("Column Names: " + StringUtils.join(names, ","));
        System.out.println("Delimiter: " + delimiter);

        for (RowDTO rowDTO : rowDTOs) {
            System.out.println("rowDTO: " + rowDTO.toString());
        }
    }

    @Required
    public void setNames(final String[] names) {
        this.names = names;
    }

    @Required
    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }
}
