package com.test.demo.DAO;

import com.test.demo.model.CurrentPrice;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CurrentPriceInterfaceDAO extends CrudRepository<CurrentPrice, Integer> {

	@Query(value = "select top 1 * from current_Price b where b.code=?1 order by upd_time desc", nativeQuery = true)
	CurrentPrice findByCode(String code);
}