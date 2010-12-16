package com.quietriot.services.util;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategyUtil;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.Column;
import org.hibernate.util.StringHelper;

import java.beans.Introspector;
import java.util.List;

public class ReverseEngineerStrategy extends DelegatingReverseEngineeringStrategy {

    public ReverseEngineerStrategy(ReverseEngineeringStrategy reverseEngineeringStrategy) {
        super(reverseEngineeringStrategy);
    }

    private String fkToEntityNameHelper(String in) {
        String in2 = toUpperCamelCase(in);
        if (Character.isUpperCase(in2.charAt(0)))
            in2 = (in2.charAt(0) + "").toLowerCase() + in2.substring(1);
        return in2;
    }

    public String foreignKeyToEntityName(String keyname, TableIdentifier fromTable, List fromColumnNames, TableIdentifier referencedTable, List referencedColumnNames, boolean uniqueReference) {
        String propertyName = Introspector.decapitalize(StringHelper.unqualify(tableToClassName(referencedTable)));
        if (uniqueReference) {
            propertyName = ((Column) fromColumnNames.get(0)).getName();
            propertyName = Introspector.decapitalize(toUpperCamelCase(propertyName));

            if (propertyName.endsWith("Id"))
                propertyName = propertyName.substring(0, propertyName.length() - 2);
        }

        if (!uniqueReference) {
            if (fromColumnNames != null && fromColumnNames.size() == 1) {
                String columnName = ((Column) fromColumnNames.get(0)).getName();
                propertyName = fkToEntityNameHelper(toUpperCamelCase((columnName.toLowerCase().endsWith("_id") ? columnName.substring(0, columnName.length() - 3) : columnName)));//+ toUpperCamelCase(propertyName));

            } else {
                propertyName = fkToEntityNameHelper(toUpperCamelCase(keyname) + toUpperCamelCase(propertyName));
            }
        }

        return propertyName;
    }

    protected String toUpperCamelCase(String s) {
        return ReverseEngineeringStrategyUtil.toUpperCamelCase(s);
    }
}