package com.herringbone.hopfield;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HopfieldPattern {

    @Id
    private Integer id;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "pattern")
    private Integer[] matrix;

}
