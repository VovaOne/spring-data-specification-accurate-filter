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
        Car md = new Car();
        repository.save(md);
//        repository.save(customer2);
    }


    @Test
    public void retrieveCarByFilter() {

        Filter filter = new Filter();
        filter.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("firstname").setValue("Dan").build());

        List<Car> carList = repository.findAll((root, criteriaQuery, criteriaBuilder) -> null);
        assertThat(carList.isEmpty(), is(false));
        assertEquals(carList.get(0).model, "");
    }


}
