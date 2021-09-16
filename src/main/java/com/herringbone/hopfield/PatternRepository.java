package com.herringbone.hopfield;

import org.springframework.data.repository.CrudRepository;

public interface PatternRepository extends CrudRepository<HopfieldPattern, Integer> {
    HopfieldPattern findByMatrix(Integer[] matrix);
}
