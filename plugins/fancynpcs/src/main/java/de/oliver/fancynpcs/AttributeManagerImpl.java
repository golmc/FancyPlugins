package de.oliver.fancynpcs;

import de.oliver.fancynpcs.api.AttributeManager;
import de.oliver.fancynpcs.api.NpcAttribute;
import de.oliver.fancynpcs.v1_19_4.attributes.Attributes_1_19_4;
import de.oliver.fancynpcs.v1_20_1.attributes.Attributes_1_20_1;
import de.oliver.fancynpcs.v1_20_2.attributes.Attributes_1_20_2;
import de.oliver.fancynpcs.v1_20_4.attributes.Attributes_1_20_4;
import de.oliver.fancynpcs.v1_20_6.attributes.Attributes_1_20_5;
import de.oliver.fancynpcs.v1_21_1.attributes.Attributes_1_21_1;
import de.oliver.fancynpcs.v1_21_3.attributes.Attributes_1_21_3;
import de.oliver.fancynpcs.v1_21_4.attributes.Attributes_1_21_4;
import de.oliver.fancynpcs.v1_21_5.attributes.Attributes_1_21_5;
import de.oliver.fancynpcs.v1_21_6.attributes.Attributes_1_21_6;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class AttributeManagerImpl implements AttributeManager {

    private List<NpcAttribute> attributes;

    public AttributeManagerImpl() {
        this.attributes = new ArrayList<>();
        init();
    }

    private void init() {
        String mcVersion = Bukkit.getMinecraftVersion();
        switch (mcVersion) {
            case "1.21.6", "1.21.7", "1.21.8" -> attributes = Attributes_1_21_6.getAllAttributes();
            case "1.21.5" -> attributes = Attributes_1_21_5.getAllAttributes();
            case "1.21.4" -> attributes = Attributes_1_21_4.getAllAttributes();
            case "1.21.2", "1.21.3" -> attributes = Attributes_1_21_3.getAllAttributes();
            case "1.21", "1.21.1" -> attributes = Attributes_1_21_1.getAllAttributes();
            case "1.20.5", "1.20.6" -> attributes = Attributes_1_20_5.getAllAttributes();
            case "1.20.3", "1.20.4" -> attributes = Attributes_1_20_4.getAllAttributes();
            case "1.20.2" -> attributes = Attributes_1_20_2.getAllAttributes();
            case "1.20.1", "1.20" -> attributes = Attributes_1_20_1.getAllAttributes();
            case "1.19.4" -> attributes = Attributes_1_19_4.getAllAttributes();
        }
    }

    @Override
    public NpcAttribute getAttributeByName(EntityType type, String name) {
        for (NpcAttribute attribute : attributes) {
            if (attribute.getTypes().contains(type) && attribute.getName().equalsIgnoreCase(name)) {
                return attribute;
            }
        }

        return null;
    }

    @Override
    public void registerAttribute(NpcAttribute attribute) {
        attributes.add(attribute);
    }

    @Override
    public List<NpcAttribute> getAllAttributes() {
        return attributes;
    }

    @Override
    public List<NpcAttribute> getAllAttributesForEntityType(EntityType type) {
        return attributes.stream()
                .filter(attribute -> attribute.getTypes().contains(type))
                .toList();
    }
}
