package com.demo.api.rest.model;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlTransient
public abstract class ModelItem implements Serializable {

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
    }
}
