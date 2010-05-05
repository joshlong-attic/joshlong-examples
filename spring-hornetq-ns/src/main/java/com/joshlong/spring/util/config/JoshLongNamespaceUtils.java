package com.joshlong.spring.util.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.Conventions;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * This class was stolen largely from {@link org.springframework.integration.config.xml.IntegrationNamespaceUtils} from the Spring Integration project: it's useful, but we can't risk having a forced dependency on it ATM.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class JoshLongNamespaceUtils {

    /**
     * Populates the specified bean definition property with the value of the attribute whose name is provided if that attribute is defined in the given element.
     *
     * @param builder       the bean definition to be configured
     * @param element       the XML element where the attribute should be defined
     * @param attributeName the name of the attribute whose value will be used to populate the property
     * @param propertyName  the name of the property to be populated
     */
    public static void setValueIfAttributeDefined(BeanDefinitionBuilder builder,
                                                  Element element, String attributeName, String propertyName) {
        String attributeValue = element.getAttribute(attributeName);
        if (StringUtils.hasText(attributeValue)) {
            builder.addPropertyValue(propertyName, attributeValue);
        }
    }

    /**
     * Populates the bean definition property corresponding to the specified attributeName with the value of that attribute if it is defined in the given element. <p>The property name will be the camel-case equivalent of the lower case hyphen separated attribute (e.g. the "foo-bar" attribute would
     * match the "fooBar" property).
     *
     * @param beanDefinitionBuilder - the bean definition to be configured
     * @param element               - the XML element where the attribute should be defined
     * @param attributeName         - the name of the attribute whose value will be set on the property
     *
     * @see org.springframework.core.Conventions#attributeNameToPropertyName(String)
     */
    public static void setValueIfAttributeDefined(BeanDefinitionBuilder beanDefinitionBuilder,
                                                  Element element, String attributeName) {
        setValueIfAttributeDefined(beanDefinitionBuilder, element, attributeName,
                                   Conventions.attributeNameToPropertyName(attributeName));
    }

    /**
     * Populates the specified bean definition property with the reference to a bean. The bean reference is identified by the value from the attribute whose name is provided if that attribute is defined in the given element.
     *
     * @param builder       the bean definition to be configured
     * @param element       the XML element where the attribute should be defined
     * @param attributeName the name of the attribute whose value will be used as a bean reference to populate the property
     * @param propertyName  the name of the property to be populated
     */
    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder,
                                                      Element element, String attributeName, String propertyName) {
        String attributeValue = element.getAttribute(attributeName);
        if (StringUtils.hasText(attributeValue)) {
            builder.addPropertyReference(propertyName, attributeValue);
        }
    }

    /**
     * Populates the bean definition property corresponding to the specified attributeName with the reference to a bean identified by the value of that attribute if the attribute is defined in the given element. <p>The property name will be the camel-case equivalent of the lower case hyphen
     * separated attribute (e.g. the "foo-bar" attribute would match the "fooBar" property).
     *
     * @param builder       - the bean definition to be configured
     * @param element       - the XML element where the attribute should be defined
     * @param attributeName - the name of the attribute whose value will be used as a bean reference to populate the property
     *
     * @see Conventions#attributeNameToPropertyName(String)
     * @see Conventions#attributeNameToPropertyName(String)
     */
    public static void setReferenceIfAttributeDefined(BeanDefinitionBuilder builder,
                                                      Element element, String attributeName) {
        setReferenceIfAttributeDefined(builder, element, attributeName,
                                       Conventions.attributeNameToPropertyName(attributeName));
    }

    /**
     * Provides a user friendly description of an element based on its node name and, if available, its "id" attribute value. This is useful for creating error messages from within bean definition parsers.
     */
    public static String createElementDescription(Element element) {
        String elementId = "'" + element.getNodeName() + "'";
        String id = element.getAttribute("id");
        if (StringUtils.hasText(id)) {
            elementId += " with id='" + id + "'";
        }
        return elementId;
    }

}
