package by.lupach.patientaccountingsystemrestapiclient.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Page<T> {
    List<T> content;
    Integer pageSize;
    Integer totalPages;
    Integer number;

    public Page(List<T> content,
                Integer pageSize,
                Integer totalPages,
                Integer number){
        this.content = content;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.number = number;
    }

    public Boolean hasNext(){
        Boolean hasNext = false;
        if (number<totalPages-1){
            hasNext = true;
        }

        return hasNext;
    }

    public Boolean hasPrevious(){
        Boolean hasPrevious = false;
        if (number>0){
            hasPrevious = true;
        }

        return hasPrevious;
    }
}
