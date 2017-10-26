package com.augwit.carl.demo.persistence;

import com.augwit.carl.demo.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @Author: Carl
 * @Description:
 * @Date: Created in 9:51 AM 10/26/2017
 * @Modified By:
 */
@Repository
@Transactional
public interface CityRepository extends JpaRepository<City,Long> {
}
