###Spring data Specification Dynamic filtering
[Spring Specification] give possibility to filtering query passing predicates in query method. 

```java
findAll(where(custoerHasBirthday()).and(isLongTermCustomer()));
```

######Here we make a dynamic filtering

Assume that we need filter records by the same json.

```json
  [{
  "type": "string",
  "value": "***",
  "field": "model"
  },{
  "type": "numeric",
  "value": "***",
  "field": "year",
  "comparison": "gt"
 }]
```
It real problem many js frameworks requested data with the same json filter;

In this example we will filtering cars.
```java
@Entity class Car {...}
```

Java model representing filter contain a collection of conditions

```java
class Filter implements Specification {
 List<Condition> conditions;
 @Override
 public Predicate toPredicate(/**...*/) {}
}
```
As you notice we implemented Specification interface. In Method we need to return a Predicare. To achieve it we create Predicate for all conditions iterating collection.
```java
private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }
    
     public Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.comparison) {
            case eq:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case gt:
                break;
            case lt:
                break;
        }
        throw new RuntimeException();
    }

    private Predicate buildEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.field), condition.value);
    }
```
To get Predicate we compare comparison type. For numeric filter may contain greater or less condition. For particular condition we add field name and value that came from json filter.

######Dynamic filtering is finished. Just run test and see results.

```java
@Test
public void retrieveCarByFilter() {
    Filter filter = new Filter();
    filter.addCondition(new Condition.Builder().
                            setComparison(Comparison.eq).
                            setField("brand").
                            setValue("volkswagen").
                            build()
                        );

        List<Car> carList = repository.findAll(filter);
        assertThat(carList.isEmpty(), is(false));
        assertEquals(carList.get(0).brand, "volkswagen");
    }
```

This filter is the good solution with REST approach. It improve a Spring crud repository and specification repository adding a dynamic filtering also you can use it with spring-data-rest to have api from scratch.

[Spring Specification]:http://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/



###As alternative you can use Querydsl web support when it will work or ActiveJpa
 
###As alternative to Spring data and you can use template pattern with base generic class to avoid bunch of interfaces