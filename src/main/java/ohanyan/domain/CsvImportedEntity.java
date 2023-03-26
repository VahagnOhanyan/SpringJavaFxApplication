/*
 * Copyright (c) 2008-2022
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */
package ohanyan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * todo Document type CsvImportedEntity
 */
@Getter
@Setter
@Entity
@Table(name = "csv_imported", schema = "public", catalog = "myDb")
public class CsvImportedEntity {
    @Id
    @Column(name = "monthyear")
    private String monthyear;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CsvImportedEntity that = (CsvImportedEntity) o;
        return Objects.equals(monthyear, that.monthyear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthyear);
    }
}
