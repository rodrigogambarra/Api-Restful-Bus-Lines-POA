package com.provatecnica.repository;

import com.provatecnica.model.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, Long>, JpaSpecificationExecutor<BusLine> {

	BusLine findOne(Long id);

	BusLine findByName(String name);

}
