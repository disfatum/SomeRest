package com.junior.task.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "purchase")
@XmlRootElement(name = "item")
public class PurchaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Purchase_ID",nullable=false)
    private Long Purchase_ID;

    @Column(name = "name",nullable=false)
    private String name;

    @Column(name = "lastname",nullable=false)
    private String lastname;

    @Column(name = "age",nullable=false)
    private int age;

    @Column(name = "year",nullable=false)
    private int year;

    @Column(name = "day",nullable=false)
    private int day;

    @Column(name = "month",nullable=false)
    private int month;

    @OneToMany(mappedBy="purchase", cascade = CascadeType.ALL)
    private List<Purchase_item> Purchase_item ;

    @Column(name = "count",nullable=false)
    private int count;

    @Column(name = "amount",nullable=false)
    private double amount;

    @Column(name = "time",nullable=false)
    private String time;
    public PurchaseEntity(){

    }
    public PurchaseEntity(String name, String last_name, int age,
                          List<Purchase_item> purchase_items, int count, double amount, String time) {

        this.name = name;
        this.lastname = last_name;
        this.age = age;
        this.Purchase_item =  purchase_items;
        this.count = count;
        this.amount = amount;
        this.year = Integer.valueOf(time.split("-")[0]);
        this.month = Integer.valueOf(time.split("-")[1]);
        this.day = Integer.valueOf(time.split("-")[2]);;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLastname() {

        return lastname;
    }

    public void setLastname(String last_name)
    {
        this.lastname = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public List<Purchase_item> getPurchase_items() {
        return this.Purchase_item;
    }

    public void setPurchase_items(List<Purchase_item> purchase_items) {

        this.Purchase_item = purchase_items;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {

        this.count = count;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    public void setDay(int day){

        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }
}
