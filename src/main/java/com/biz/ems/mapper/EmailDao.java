package com.biz.ems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.ems.model.EmailVO;

public interface EmailDao {
	@Select(" SELECT * FROM tbl_ems ")
	public List<EmailVO> selectAll();
	public EmailVO findBySeq(long ems_seq);
	
	public List<EmailVO> findByFrom(String ems_from_email);
	public List<EmailVO> findByTo(String ems_to_email);
	
	/*
	 * 매개변수가 2개 이상일 경우는
	 * 반드시 @Param으로 변수 이름을 명시해 주어야 한다.
	 */
	public List<EmailVO> findByFromAndTo(@Param("em_form_email") String ems_from_email,
										@Param("em_to_email")	String ems_to_email);
	
	public int insert(EmailVO emailVO);
	public int update(EmailVO emailVO);
	public int delete(long ems_seq);
}
