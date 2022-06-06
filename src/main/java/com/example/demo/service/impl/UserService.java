package com.example.demo.service.impl;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.IUserDAO;
import com.example.demo.DAO.PagingDAO;
import com.example.demo.entity.Users;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.UserModel;
import com.example.demo.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private PagingDAO pagingDAO;

	//  ham lay ra users
			private List<UserModel> getListUsersModels(List<Users> users) {
				List<UserModel> userModels = new ArrayList<>();
				for (Users user : users) {
					UserModel userModel = new UserModel();
					userModel.setUser_id(user.getUser_id());
					userModel.setUsername(user.getUsername());
					userModel.setEmail(user.getEmail());
					userModel.setPassword(user.getPassword());	
					userModel.setAddress(user.getAddress());
					userModel.setPhonenumber(user.getPhonenumber());
					userModel.setGender(user.getGender());
					userModel.setRole(user.getRole());
					userModel.setImage(user.getImage());
					userModel.setHovaten(user.getHovaten());		
					userModel.setPosition(user.getPosition());
					userModel.setCreateat(user.getCreateat());
					userModel.setUpdateat(user.getUpdateat());
					
					userModels.add(userModel);
				}
				return userModels;
			}

			//get list users
	@Override
	public List<UserModel> getListUser() throws SQLException {
		List<Users> users = userDAO.getAllUsers();
		List<UserModel> userModels = getListUsersModels(users);
		return userModels;
	}
//add user 

	@Override
	public Users addUser(UserModel userModel) throws SQLException {
		if (userDAO.findByName(userModel.getUsername()) == null) {
			Users user = new Users();
			
			user.setUsername(userModel.getUsername());;
			user.setEmail(userModel.getEmail());
			user.setPassword(userModel.getPassword());
			user.setAddress(userModel.getAddress());
			user.setPhonenumber(userModel.getPhonenumber());
			user.setGender(userModel.getGender());
			user.setRole(userModel.getRole());
			user.setHovaten(userModel.getHovaten());
			user.setImage(userModel.getImage());
			user.setPosition(userModel.getPosition());
			user.setCreateat(userModel.getCreateat());
			user.setUpdateat(userModel.getUpdateat());
			user.setActive(userModel.getActive());
			
			
			System.out.println("gia tri cuoi cung la :"+userDAO.save(user));
			return userDAO.save(user);
		}
		else {
			 throw new DuplicateRecordException("Da co user nay trong danh sach");
		}
	}
	// get user by username
	@Override
	public Users getUserByName(String inname) throws SQLException {
		if (inname != null) {
			Users user = userDAO.findByName(inname);
			if (user != null) {
				return user;
			}else {
				   throw new NotFoundException("Khong tim thay nguoi dung nay");
			}
		} else {
			throw new NotFoundException("Khong tim thay nguoi dung nay");
		}
	}
 //xoa nguoi dung khoi danh sach
	@Override
	public void deleteUser(String inname) throws SQLException {
		if (userDAO.findByName(inname) != null) {
			userDAO.deleteUser(inname);
		}
		else {
			throw new NotFoundException("Khong tim thay nguoi dung nay");
		}		
	}
 //update user
	@Override
	public void editUser(UserModel userModel, String inname) throws SQLException {
		if (userDAO.findByName(inname) != null) {
			Users user = userDAO.findByName(inname);

 			if (!user.getEmail().equals(userModel.getEmail()) ) {
 				user.setEmail(userModel.getEmail());	
 			}
 			if (!user.getPassword().equals(userModel.getPassword()) ) {
 				user.setPassword(userModel.getPassword());
 			}
 			if (!user.getAddress().equals(userModel.getAddress())) {
 				user.setAddress(userModel.getAddress());
 			}
 			if(!user.getPhonenumber().equals(userModel.getPhonenumber())) {
 				user.setPhonenumber(userModel.getPhonenumber());
 			}
 			if (!user.getGender().equals(userModel.getGender()) ) {
 				user.setGender(userModel.getGender());
 			}
 			if(!user.getRole().equals(userModel.getRole())) {
 				user.setRole(userModel.getRole());
 			}
// 			if(!user.getImage().equals(userModel.getImage())) {
// 				user.setImage(userModel.getImage());		
// 			}
 			if (!user.getPosition().equals(userModel.getPosition()) ) {
 				user.setPosition(userModel.getPosition());
 			}

 			user.setImage(userModel.getImage());	
 			user.setCreateat(userModel.getCreateat());
 			user.setUpdateat(userModel.getUpdateat());
 			user.setActive(userModel.getActive());
 			user.setHovaten(userModel.getHovaten());
 			
 			userDAO.saveAndFlush(user);
		}else {
			throw new NotFoundException("Khong tim thay nguoi dung nay");
		}
		
	}
//check login user

@Override
public int checkLogin(UserModel users) throws SQLException {
	Users userfindUsers = userDAO.findByName(users.getUsername());
	
	System.out.println(users);

	if (userfindUsers==null) {
		return 0;
	}
	else {
		if (users.getUsername().equals(
				userfindUsers.getUsername()) 
				&& users.getPassword().equals(userfindUsers.getPassword()) 
				&& userfindUsers.getActive()==1
				) {
			System.out.println(userfindUsers.getActive());
			return 1;
		}
		else {
			return 0;
		}
	}
}

//pagination 
@Override
public List<Users> findPaginated(int pageNo, int pageSize) {
	Pageable paging = PageRequest.of(pageNo, pageSize);
	Page<Users> pageResult = pagingDAO.findByRole(paging);
	return pageResult.toList();
}
//get all doctors
@Override
public List<Users> getListDoctors() throws SQLException {
	if (userDAO.getAllDoctors() != null) {
		List<Users> doctorList = userDAO.getAllDoctors();
		return doctorList;
	}else
	return null;
}
//get user by username
@Override
public Users getUserByID(int intID) throws SQLException {
	if (intID != 0) {
		System.out.println("gia tri id la "+intID);
		Users user = userDAO.findbyId(intID);
		if (user != null) {
			return user;
		}else {
			   throw new NotFoundException("Khong tim thay nguoi dung nay");
		}
	} else {
		throw new NotFoundException("Khong tim thay nguoi dung nay");
	}
}


}