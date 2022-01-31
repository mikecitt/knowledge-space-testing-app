package com.platform.kspace.dto;

import java.util.UUID;

public class ItemProblemDTO {
    private Integer itemId;
    private UUID domainProblemId;

    public ItemProblemDTO() {}

    public ItemProblemDTO(Integer itemId, UUID domainProblemId) {
        this.itemId = itemId;
        this.domainProblemId = domainProblemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public UUID getDomainProblemId() {
        return domainProblemId;
    }

    public void setDomainProblemId(UUID domainProblemId) {
        this.domainProblemId = domainProblemId;
    }
}
