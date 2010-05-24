package com.joshlong.hornetq.tests.cluster;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class SimplePojo implements Serializable {

    
    private String field1, field2;
    private int field3;
    private Date field4;
    public SimplePojo(){
        this.field1 = StringUtils.repeat("1", 100 );
        this.field2 = StringUtils.repeat("2", 100);
        this.field3 = 10100;
        this.field4 = new Date() ;
    }

    @Override
    public int hashCode() {
     return HashCodeBuilder.reflectionHashCode( this);
    }

    @Override
    public boolean equals(final Object o) {
     return EqualsBuilder.reflectionEquals( this,o) ;
    }

    @Override
    public String toString() {
     return ToStringBuilder.reflectionToString( this) ;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(final String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(final String field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(final int field3) {
        this.field3 = field3;
    }

    public Date getField4() {
        return field4;
    }

    public void setField4(final Date field4) {
        this.field4 = field4;
    }
}
