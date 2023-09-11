package net.applee.minecraft.world.entity.attributes;

import java.util.HashMap;
import java.util.Map;

public class AttributesContainer {

    private final Map<Attribute, AttributeData> attributes = new HashMap<>();

    public AttributesContainer() {

        for (Attribute attribute : Attribute.values()) {
            attributes.put(attribute, new AttributeData(attribute.getKey(), attribute.getDefaultValue(), new Modifier[0]));
        }

    }

    public void updateAttribute(AttributeData data) {
        attributes.put(data.getAttribute(), data);
    }

    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    public AttributeData getData(Attribute attribute) {
        return attributes.get(attribute);
    }

    public Iterable<AttributeData> getAttributes() {
        return attributes.values();
    }

}
