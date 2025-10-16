package org.splitzy.common.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key; // Field name
    private SearchOperation operation;
    private Object value;
    private String joinKey; // For join operations (optional)

    public SearchCriteria(String key, SearchOperation operation, Object value){
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
