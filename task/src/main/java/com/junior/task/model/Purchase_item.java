package com.junior.task.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parent;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="child")
@XmlRootElement(name = "purchase_items")
public class Purchase_item  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item",nullable=false)
    private Long id_item;

    @Column(name = "url",nullable=false)
    private String purchase_url;

    @Column(name = "name",nullable=false)
    private String purchase_name;

    @ManyToOne(optional=false, cascade= CascadeType.ALL)
    @JoinColumn(name = "mp")
    private PurchaseEntity purchase;

    public Purchase_item(){}

    public Purchase_item(String url, String name, PurchaseEntity purchase) {
        this.purchase_url = url;
        this.purchase = purchase;
        this.purchase_name = name;
    }

    public String getUrl() {
        return purchase_url;
    }

    public void setUrl(String url) {
        this.purchase_url = url;
    }

    public String getName() {
        return purchase_name;
    }

    public void setName(String name) {
        this.purchase_name = name;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }
}
