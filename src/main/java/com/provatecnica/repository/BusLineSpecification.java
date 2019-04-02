package com.provatecnica.repository;

import com.provatecnica.model.BusLine;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BusLineSpecification implements Specification<BusLine> {

	private SearchCriteria criteria;

	public BusLineSpecification(SearchCriteria searchCriteria) {
		criteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate
		(Root<BusLine> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(
				root.<String> get(criteria.getKey()), criteria.getValue().toString());
		}
		else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(
				root.<String> get(criteria.getKey()), criteria.getValue().toString());
		}
		else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(
					root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}
}
