package org.splitzy.common.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//  Container for filter criteria with pagination and sorting
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterCriteria {

    private List<SearchCriteria> searchCriteria;
    private String sortBy;
    private String sortDirection;
    private int page;
    private int pageSize;

    public int getPage(){
        return page <= 0 ? 0 : page;
    }
    public int getSize(){
        return pageSize <= 0 ? 10 : pageSize;
    }
    public String getSortDirection(){
        return sortDirection == null ? "ASC" : sortDirection.toUpperCase();
    }
}
