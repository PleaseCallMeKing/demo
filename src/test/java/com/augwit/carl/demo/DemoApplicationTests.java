package com.augwit.carl.demo;

import com.augwit.carl.demo.domain.City;
import com.augwit.carl.demo.utils.GeneratorXls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
		City city=new City();

		city.setCityId((long) 3);
		city.setCityName("Beijing");
		city.setCityArea((double) 233);
		city.setProvince("Beijing Province");

		GeneratorXls xls=new GeneratorXls();
		xls.createXls(city);
	}

}
