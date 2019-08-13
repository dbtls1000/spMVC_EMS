package com.biz.ems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.ems.model.EmailVO;

public interface EmailDao {
	@Select(" SELECT * FROM tbl_ems ")
	public List<EmailVO> selectAll();
	
	@Select(" SELECT * FROM tbl_ems WHERE ems_seq = #{ems_seq} ")
	public EmailVO findBySeq(long ems_seq);
	
	@Select(" SELECT * FROM tbl_ems WHERE ems_from_email LIKE '%' || #{ems_from_email} || '%' ")
	public List<EmailVO> findByToEmail(String ems_from_email);
	public List<EmailVO> findByFromEmail(String ems_to_email);
	
	@Select(" SELECT * FROM tbl_ems WHERE ems_from_name LIKE '%' || #{ems_from_name} || '%' ")
	public List<EmailVO> findByToName(String search);

	@Select(" SELECT * FROM tbl_ems WHERE ems_subject LIKE '%' || #{ems_subject} || '%' ")
	public List<EmailVO> findBySubject(String search);

	@Select(" SELECT * FROM tbl_ems WHERE ems_content LIKE '%' || #{ems_content} || '%' ")
	public List<EmailVO> findByContent(String search);
	
	/*
	 * 매개변수가 2개 이상일 경우는
	 * 반드시 @Param으로 변수 이름을 명시해 주어야 한다.
	 */
	public List<EmailVO> findByFromAndTo(@Param("ems_from_email") String ems_from_email,
										@Param("ems_to_email")	String ems_to_email);
	
	@InsertProvider(value=EmailSQL.class,method="ems_insert_sql")
	public int insert(EmailVO emailVO);
	
	@UpdateProvider(value=EmailSQL.class,method="ems_update_sql")
	public int update(EmailVO emailVO);
	
	@Delete(" DELETE FROM tbl_ems WHERE ems_seq = #{ems_seq} ")
	public int delete(long ems_seq);

	
}
