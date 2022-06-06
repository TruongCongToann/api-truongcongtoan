package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	private int user_id;
	private String username;
	private String email;
	private String password;
	private String address;
	private String phonenumber;
	private String gender;
	private String role;
	private String image;
	private int active;
	private String position;
	private String hovaten;
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	  @Column(name = "createat")
	private Date createat;
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	  @Column(name = "updateat")
	private Date updateat;
	
}