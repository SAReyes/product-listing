package com.example.productlisting.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_product",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private List<Product> products;
}
