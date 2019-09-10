package com.biz.ems.mapper;

import java.util.HashMap;

import org.apache.ibatis.jdbc.SQL;

public class EmailSQL {

	public String ems_insert_sql() {
		SQL sql = new SQL().INSERT_INTO("tbl_ems").INTO_COLUMNS("ems_seq").INTO_VALUES("SEQ_EMS.NEXTVAL")
				.INTO_COLUMNS("ems_to_email").INTO_VALUES("#{ems_to_email}").INTO_COLUMNS("ems_from_email")
				.INTO_VALUES("#{ems_from_email}").INTO_COLUMNS("ems_to_name").INTO_VALUES("#{ems_to_name}")
				.INTO_COLUMNS("ems_from_name").INTO_VALUES("#{ems_from_name}").INTO_COLUMNS("ems_send_date")
				.INTO_VALUES("#{ems_send_date}").INTO_COLUMNS("ems_send_time").INTO_VALUES("#{ems_send_time}")
				.INTO_COLUMNS("ems_subject").INTO_VALUES("#{ems_subject}").INTO_COLUMNS("ems_content")
				.INTO_VALUES("#{ems_content}").INTO_COLUMNS("ems_file1").INTO_VALUES("#{ems_file1}")
				.INTO_COLUMNS("ems_file2").INTO_VALUES("#{ems_file2}");

		return sql.toString();
	}

	public String ems_update_sql() {
		SQL sql = new SQL().UPDATE("tbl_ems").SET("ems_to_email = #{ems_to_email}")
				.SET("ems_from_email = #{ems_from_email}").SET("ems_to_name = #{ems_to_name}")
				.SET("ems_from_name = #{ems_from_name}").SET("ems_send_date = #{ems_send_date}")
				.SET("ems_send_time = #{ems_send_time}").SET("ems_subject = #{ems_subject}")
				.SET("ems_content = #{ems_content}").SET("ems_file1 = #{ems_file1}").SET("ems_file2 = #{ems_file2}")
				.WHERE("ems_seq = #{ems_seq}");

		return sql.toString();
	}

	public String ems_select_count_sql(final HashMap<String,String> test) {

		SQL sql = new SQL() {
			{
				SELECT(" count(*) ");
				FROM(" tbl_ems ");
				if (test.get("search_option").equalsIgnoreCase("toEmail")) {
					WHERE(" ems_to_email like #{keyword} ");
				} else if (test.get("search_option").equalsIgnoreCase("fromName")) {
					WHERE(" ems_from_name like #{keyword} ");
				} else if (test.get("search_option").equalsIgnoreCase("subject")) {
					WHERE(" ems_subject like #{keyword} ");
				} else if(test.get("search_option").equalsIgnoreCase("content")){
					WHERE(" ems_content like #{keyword} ");
				}else {
					WHERE(" ems_to_email LIKE #{keyword} ");
					OR();
					WHERE(" ems_from_name LIKE #{keyword} ");
					OR();
					WHERE(" ems_subject LIKE #{keyword} ");
					OR();
					WHERE(" ems_content LIKE #{keyword} ");
				}
			}
		};
		return sql.toString();
	}

	public String ems_list_all(final HashMap<String, Object> option) {

		final SQL main_sql = new SQL() {
			{
				SELECT("*");
				FROM(" tbl_ems ");
				ORDER_BY(" ems_send_date DESC,ems_send_time DESC ");
				if (((String) option.get("search_option")).equalsIgnoreCase("toEmail")) {
					WHERE(" ems_to_email like #{keyword} ");
				} else if (((String) option.get("search_option")).equalsIgnoreCase("fromName")) {
					WHERE(" ems_from_name like #{keyword} ");
				} else if (((String) option.get("search_option")).equalsIgnoreCase("subject")) {
					WHERE(" ems_subject like #{keyword} ");
				} else if(((String) option.get("search_option")).equalsIgnoreCase("content")){
					WHERE(" ems_content like #{keyword} ");
				}else {
					WHERE(" ems_to_email LIKE #{keyword} ");
					OR();
					WHERE(" ems_from_name LIKE #{keyword} ");
					OR();
					WHERE(" ems_subject LIKE #{keyword} ");
					OR();
					WHERE(" ems_content LIKE #{keyword} ");
				}
			}
		};

		SQL sql = new SQL() {
			{
				SELECT("*");
				FROM(" ( SELECT rownum AS rnum, A.* FROM( " + main_sql.toString() + ") A ) ");
				WHERE(" rnum BETWEEN #{start} AND #{end} ");

			}
		};
		return sql.toString();

	}

}
