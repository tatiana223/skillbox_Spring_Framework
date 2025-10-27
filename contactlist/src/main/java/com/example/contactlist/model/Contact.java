package com.example.contactlist.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@FieldNameConstants
@Table("contact")
public class Contact {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
