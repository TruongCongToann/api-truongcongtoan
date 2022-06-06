package com.example.demo.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;

@Repository
@Transactional
public interface IUserDAO extends JpaRepository<Users, Integer> {
	// get all users
	@Query(nativeQuery = true, value = "select * from users order by username")
	public List<Users> getAllUsers();

	// get thong tin sv by masv
	@Query(value = "select * from users where username = :inname", nativeQuery = true)
	public Users findByName(@Param("inname") String inname);

	// get thong tin sv by masv
	@Query(value = "select * from users where user_id = :inid", nativeQuery = true)
	public Users findbyId(@Param("inid") int inid);
	// xoa sinh vien theo masv
	@Modifying
	@Query(value = "delete from users where username =:inname", nativeQuery = true)
	public void deleteUser(@Param("inname") String inname);
	
	//get all doctors
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE role='R2' ORDER BY username")
	public List<Users> getAllDoctors();
	
	//get all doctors by username
	@Query(nativeQuery = true, value = "SELECT * FROM users WHERE role='R2' and username = :inname ORDER BY username")
	public Users getDoctorByName(@Param("inname") String inname);

}