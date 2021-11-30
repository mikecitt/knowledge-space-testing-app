package com.platform.kspace.dto;

import java.util.List;

public class ItemAnswersDTO {
    private Integer itemId;
    private List<Integer> selectedAnswers;

    public ItemAnswersDTO() {}

    public ItemAnswersDTO(Integer itemId, List<Integer> selectedAnswers) {
        this.itemId = itemId;
        this.selectedAnswers = selectedAnswers;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public List<Integer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(List<Integer> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }
}
