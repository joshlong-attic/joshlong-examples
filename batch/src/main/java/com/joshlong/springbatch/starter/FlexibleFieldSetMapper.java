package com.joshlong.springbatch.starter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.HashMap;
import java.util.Map;

/**
 * The field set mapper maps the columns as parsed by Spring batch to whatever the domain model dictates. In our case,
 * the domain model dictates that we have a row with values whose dimensions are unknown until runtime (the
 * configuration is stored in a database). So, we have crafted a {@link com.joshlong.springbatch.starter.RowDTO} to hold
 * the key/value pairs that are extracted from processing as a {@link java.util.Map<String,String>}.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class FlexibleFieldSetMapper implements FieldSetMapper<RowDTO> {

    public RowDTO mapFieldSet(final FieldSet fieldSet) {

        String[] colNames = fieldSet.getNames();
        Map<String, String> kvs = new HashMap<String, String>();

        for (String colName : colNames) {
            kvs.put(colName, fieldSet.readString(colName));
        }

        return new RowDTO(kvs);
    }
}
