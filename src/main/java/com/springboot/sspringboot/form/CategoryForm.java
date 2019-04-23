package com.springboot.sspringboot.form;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class CategoryForm {
    /*** 类目Id*/
    private  Integer categoryId;

    /* 类目名字 */
    private  String categoryName;

    /*  类目编号 */

    private Integer categoryType;
}
