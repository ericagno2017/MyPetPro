package ar.com.enrique.clinicsmanager.common;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> extends BackEndObject {

    private static final long serialVersionUID = -6450655377404980062L;

    private List<T> items;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalSize;

    public PageResponse() {
    }

    public PageResponse(List<T> items, Integer pageNumber, Integer pageSize, Long totalSize) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<T>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public static <T> PageResponse<T> fromList(List<T> items, Integer pageNumber, Integer pageSize, Long totalSize) {
        return new PageResponse<T>(items, pageNumber, pageSize, totalSize);
    }

    public boolean addItem(T item) {
        if (items == null) {
            items = new ArrayList<T>();
        }
        return items.add(item);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

}