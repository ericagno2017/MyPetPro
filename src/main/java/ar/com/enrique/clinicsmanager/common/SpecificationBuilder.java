package ar.com.enrique.clinicsmanager.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class SpecificationBuilder<T> {

    private Specification<T> specifications;

    public SpecificationBuilder<T> and(Specification<T> other) {
        if (other != null) {
            specifications = (specifications == null) ? Specification.where(other) : specifications.and(other);
        }
        return this;
    }

    public Specification<T> getSpecification() {
        return specifications;
    }

    public SpecificationBuilder<T> attrEqualsIfNotNull(final String attr, final Object value) {
        if (value != null) {
            this.attrEqual(attr, value);
        }
        return this;
    }

    public SpecificationBuilder<T> attrEqual(final String attr, final Object value) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(attr), value);
            }
        });
        return this;
    }

    public SpecificationBuilder<T> attrEqual(final String attr, final String value) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(cb.lower(root.get(attr)), value.toLowerCase());
            }
        });
        return this;
    }

    public SpecificationBuilder<T> attrSimilarIfNotNull(final String attr, final String value) {
        if (value != null && StringUtils.isNotBlank(value)) {
            this.attrSimilar(attr, value);
        }

        return this;
    }

    public SpecificationBuilder<T> attrSimilar(final String attr, final String value) {

        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                StringBuilder criteria = new StringBuilder(value.length() + 2);
                criteria.append("%")
                        .append(value.trim()
                                .toLowerCase())
                        .append("%");

                return cb.like(cb.lower(root.<String> get(attr)), criteria.toString());
            }

        });
        return this;
    }

    public SpecificationBuilder<T> minorEqualDate(final String attr, final Date fecha) {
        if (fecha != null) {
            this.and(new Specification<T>() {

                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Path<Date> path = root.get(attr);
                    return cb.lessThanOrEqualTo(path, fecha);
                }
            });
        }
        return this;
    }

    public SpecificationBuilder<T> mayorEqualDate(final String attr, final Date fecha) {
        if (fecha != null) {
            this.and(new Specification<T>() {

                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Path<Date> path = root.get(attr);
                    return cb.greaterThanOrEqualTo(path, fecha);
                }
            });
        }
        return this;
    }

    public SpecificationBuilder<T> attrOfObjectEqual(final String pathAttr, final Object value) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String[] splitPathAttr = pathAttr.split("\\.");
                Path<T> pathObject = root.get(splitPathAttr[0]);
                for (int i = 1; i < splitPathAttr.length; i++) {
                    pathObject = pathObject.get(splitPathAttr[i]);
                }
                return cb.equal(pathObject, value);
            }
        });
        return this;
    }

    public SpecificationBuilder<T> attrOfObjectEqualsIfNotNull(final String pathAttr, final Object value) {
        if (value != null) {
            this.attrOfObjectEqual(pathAttr, value);
        }
        return this;
    }

    public SpecificationBuilder<T> attrNotEqualsThan(final String attr, final Object value) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get(attr), value);
            }
        });
        return this;
    }

    public SpecificationBuilder<T> attrOfCollectionEqualsIfNotNull(final String collectionAttr, final String attr,
            final Object value) {
        if (value != null) {
            this.and(new Specification<T>() {

                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.join(collectionAttr)
                            .get(attr), value);
                }
            });
        }
        return this;
    }

    public SpecificationBuilder<T> attrInIfNotEmptyOrNull(final String attr,
            @SuppressWarnings("rawtypes") final List collection) {
        if (collection != null && !collection.isEmpty()) {
            this.and(new Specification<T>() {

                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.isTrue(root.get(attr)
                            .in(collection));
                }
            });
        }
        return this;
    }

    public SpecificationBuilder<T> attrNull(final String attr) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNull(root.get(attr));
            }
        });
        return this;
    }

    public SpecificationBuilder<T> type(final Class<?> value) {
        this.and(new Specification<T>() {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.type(), cb.literal(value));
            }
        });
        return this;
    }
}