package com.biz.ems.mapper;

import org.apache.ibatis.jdbc.SQL;

public class EmailSQL {
	
	public String ems_insert_sql() {
		SQL sql = new SQL()
			.INSERT_INTO("tbl_ems")
			.INTO_COLUMNS("ems_seq").INTO_VALUES("SEQ_EMS.NEXTVAL")
			.INTO_COLUMNS("ems_to_email").INTO_VALUES("#{ems_to_email}")
			.INTO_COLUMNS("ems_from_email").INTO_VALUES("#{ems_from_email}")
			.INTO_COLUMNS("ems_to_name").INTO_VALUES("#{ems_to_name}")
			.INTO_COLUMNS("ems_from_name").INTO_VALUES("#{ems_from_name}")
			.INTO_COLUMNS("ems_send_date").INTO_VALUES("#{ems_send_date}")
			.INTO_COLUMNS("ems_send_time").INTO_VALUES("#{ems_send_time}")
			.INTO_COLUMNS("ems_subject").INTO_VALUES("#{ems_subject}")
			.INTO_COLUMNS("ems_content").INTO_VALUES("#{ems_content}")
			.INTO_COLUMNS("ems_file1").INTO_VALUES("#{ems_file1}")
			.INTO_COLUMNS("ems_file2").INTO_VALUES("#{ems_file2}");
		
		return sql.toString();
	}
}
