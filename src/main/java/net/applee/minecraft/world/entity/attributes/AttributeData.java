package net.applee.minecraft.world.entity.attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttributeData {

    private final Attribute attribute;
    private final Map<UUID, Modifier> modifiers;
    private final double value;

    public AttributeData(String attributeKey, double value, Modifier[] modifiers) {
        this.attribute = Attribute.fromKey(attributeKey);
        this.value = value;
        this.modifiers = new HashMap<>();
        for (Modifier m : modifiers) this.modifiers.put(m.getUuid(), m);
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Iterable<Modifier> getModifiers() {
        return modifiers.values();
    }

    public Modifier getModifier(UUID uuid) {
        return modifiers.get(uuid);
    }

    public void removeModifier(UUID uuid) {
        modifiers.remove(uuid);
    }

    public double getValue() {
        return value;
    }
}
