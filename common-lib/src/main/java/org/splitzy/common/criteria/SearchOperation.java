package org.splitzy.common.criteria;

public enum SearchOperation {
    EQUALITY,
    NEGATION,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN_OR_EQUAL,
    LIKE,
    STARTS_WITH,
    ENDS_WITH,
    IN,
    NOT_IN,
    IS_NULL,
    IS_NOT_NULL,
    BETWEEN,
    JOIN
}
