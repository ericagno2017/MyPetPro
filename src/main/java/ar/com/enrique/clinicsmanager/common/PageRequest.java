package ar.com.enrique.clinicsmanager.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequest extends BackEndObject {

    private static final long serialVersionUID = -9175887858911261633L;

    private static final int DEFAULT_PAGE_NUMBER = 0;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final int pageNumber;

    private final int pageSize;

    public PageRequest(final Integer pageNumber, final Integer pageSize) {
        this.pageNumber = (pageNumber == null) ? DEFAULT_PAGE_NUMBER : pageNumber;
        this.pageSize = (pageSize == null) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public org.springframework.data.domain.PageRequest convert() {
        return new org.springframework.data.domain.PageRequest(pageNumber, pageSize);
    }

    public Pageable convert(Sort sort) {
        return new org.springframework.data.domain.PageRequest(pageNumber, pageSize, sort);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        PageRequest rhs = (PageRequest) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj))
                .append(pageNumber, rhs.pageNumber)
                .append(pageSize, rhs.pageSize)
                .isEquals();
    }
}