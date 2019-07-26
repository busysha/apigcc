package com.apigcc.common.description;

import com.apigcc.schema.Row;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public abstract class TypeDescription {

    protected String key;
    protected String type;
    protected StringBuilder condition = new StringBuilder();
    protected String remark;
    protected Object value;
    protected Object defaultValue;
    protected Boolean required;

    public boolean isAvailable() {
        return !isUnAvailable();
    }

    public boolean isUnAvailable() {
        return this instanceof UnAvailableTypeDescription;
    }

    public boolean isPrimitive() {
        return this instanceof PrimitiveTypeDescription;
    }

    public PrimitiveTypeDescription asPrimitive() {
        return (PrimitiveTypeDescription) this;
    }

    public boolean isString() {
        return this instanceof StringTypeDescription;
    }

    public StringTypeDescription asString() {
        return (StringTypeDescription) this;
    }

    public boolean isArray() {
        return this instanceof ArrayTypeDescription;
    }

    public ArrayTypeDescription asArray() {
        return (ArrayTypeDescription) this;
    }

    public boolean isObject() {
        return this instanceof ObjectTypeDescription;
    }

    public ObjectTypeDescription asObject() {
        return (ObjectTypeDescription) this;
    }

    public Collection<Row> rows() {
        String def;
        if(defaultValue!=null){
            def = String.valueOf(defaultValue);
        }else if(value!=null){
            def = String.valueOf(value);
        }else{
            def = "";
        }

        if(required!=null){
            condition.append("required=").append(required);
        }
        return Lists.newArrayList(new Row(key, type, condition.toString(), def, remark));
    }

}
