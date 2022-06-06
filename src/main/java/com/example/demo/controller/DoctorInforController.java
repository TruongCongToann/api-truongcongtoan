package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DoctorInfo;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.DoctorInfoModel;
import com.example.demo.service.impl.DoctorInfoService;

@RestController 
@CrossOrigin(origins = "https://itss-random.herokuapp.com")
public class DoctorInforController {
	@Autowired
	private DoctorInfoService doctorInforService;
	
	
	//get all doctor infor
	@GetMapping("/api/doctorinfo")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Object> getAllUsers() throws SQLException {
		HttpStatus httpStatus = null;
		List<DoctorInfo> doctorInfo = doctorInforService.getListDoctorInfo();
		try {
			httpStatus = HttpStatus.OK;
			
		} catch (Exception e) {
			 throw new InternalServerException("Không được bỏ trống các trường !");
		}
		return new ResponseEntity<Object>(doctorInfo, httpStatus);
	}
	
	//get doctor info by doctor id
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/api/doctorinfo/{doctorID}")
	public ResponseEntity<Object> editDoctorInfo(@Valid @PathVariable("doctorID") int doctorID) {
			HttpStatus httpStatus = null;
			DoctorInfo doctorInfo= new DoctorInfo();
			try {
				doctorInfo = doctorInforService.getInforByDoctorID(doctorID);
				httpStatus = HttpStatus.OK;

			} catch (Exception e) {	
				 throw new InternalServerException("Không được bỏ trống các trường !");
			}
			return new ResponseEntity<Object>(doctorInfo, httpStatus);
	}
	//add new info
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/api/doctorinfo/")
	public ResponseEntity<Object>  postInforDoctor(
			@Valid @RequestBody DoctorInfoModel doctorInfoModel) throws SQLException {
		HttpStatus httpStatus = null;
		try {
			doctorInforService.postInforDoctor(doctorInfoModel);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			 throw new DuplicateRecordException("bad resquest !");		 
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	
	//edit doctor info
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping("/api/doctorinfo/{doctorID}")
	public ResponseEntity<Object> editDoctorInfo(@Valid @RequestBody DoctorInfoModel doctorInfoModel,
			@PathVariable("doctorID") int doctorID) {
			HttpStatus httpStatus = null;
			try {
				doctorInforService.editDoctorInfo(doctorInfoModel, doctorID);
				httpStatus = HttpStatus.OK;

			} catch (Exception e) {
				
				 throw new InternalServerException("Không được bỏ trống các trường !");

			}
			return new ResponseEntity<Object>(httpStatus);
		}
	
	//delete doctor info
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("api/doctorinfo/{doctorID}")

	public ResponseEntity<Object> delStudent(@PathVariable("doctorID") int doctorID) {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		try {
			doctorInforService.deleteDoctorInfo(doctorID);
			httpStatus = HttpStatus.ACCEPTED;
		} catch (Exception e) {
//			e.getStackTrace();
			 throw new NotFoundException("Không tìm thấy thông tin trong danh sách !");
		}
		return new ResponseEntity<Object>(httpStatus);
	}
}