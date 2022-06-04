package com.test.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current_Price")
public class CurrentPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name="CODE")
    private String code;
    @Column(name="CODE_NAME_CH")
    private String code_nameCh;
    @Column(name="UPD_TIME")
    private Date upd_time;
    
	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public String getCode_nameCh() {return code_nameCh;}
	public void setCode_nameCh(String code_nameCh) {this.code_nameCh = code_nameCh;}
	
	public Date getUpd_time() {return upd_time;}
	public void setUpd_time(Date upd_time) {this.upd_time = upd_time;}
	
	public CurrentPrice() {

    }

    public CurrentPrice(Integer id, String code, String code_nameCh, Date upd_time) {
        this.id = id;
        this.code = code;
        this.code_nameCh = code_nameCh;
        this.upd_time = upd_time;
    }   
    
}