package com.im.db.repository;

import com.im.App;
import com.im.db.filter.internal.Comparison;
import com.im.db.filter.internal.Condition;
import com.im.db.filter.Filter;
import com.im.model.Car;
import com.im.model.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
public class CarRepositoryShould {

    @Autowired
    CarRepository repository;


    @Before
    public void prepare() {
        Car passat = new Car();
        passat.brand = "volkswagen";
        passat.model = "passat";

        Car mazda = new Car();
        mazda.brand = "mazda";
        mazda.model = "6";

        Car _350z = new Car();
        _350z.brand = "nissan";
        _350z.model = "350z";

        repository.save(passat);
        repository.save(mazda);
        repository.save(_350z);
    }


    @Test
    public void retrieveCarByFilter() {

        Filter filter = new Filter();
        filter.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("brand").setValue("volkswagen").build());

        List<Car> carList = repository.findAll(filter);
        assertThat(carList.isEmpty(), is(false));
        assertEquals(carList.get(0).brand, "volkswagen");
    }


}
