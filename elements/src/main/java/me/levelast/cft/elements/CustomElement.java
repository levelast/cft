package me.levelast.cft.elements;

import java.io.Serializable;
import java.util.UUID;

public class CustomElement implements Comparable<CustomElement>, Serializable {

    private static final long serialVersionUID = 1111575598422124441L;

    private final UUID itemId;
    private Long groupId;

    public CustomElement(UUID itemId, Long groupId) {
        this.itemId = itemId;
        this.groupId = groupId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public Long getGroupId() {
        return groupId;
    }

    @Override
    public int compareTo(CustomElement customElement) {
        return itemId.compareTo((customElement.getItemId()));
    }
}
