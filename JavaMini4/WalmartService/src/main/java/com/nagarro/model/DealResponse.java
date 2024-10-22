package com.nagarro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealResponse {
    private String categoryName;
    private List<DealItem> dealItems;
}
