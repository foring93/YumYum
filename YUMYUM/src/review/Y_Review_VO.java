package review;

import java.sql.Date;

public class Y_Review_VO
{
	private int re_no;
	private String re_writer;
	private int re_ref_no;
	private String re_ref_name;
	private String re_content;
	private int re_grade;
	private int re_recommend_cnt;
	private Date re_regidate;
	private String re_img_url;
	private String re_writer_id;
	
	
	public int getRe_no(){ return re_no; }
	public void setRe_no(int re_no){ this.re_no = re_no; }
	public String getRe_writer(){ return re_writer; }
	public void setRe_writer(String re_writer){ this.re_writer = re_writer; }
	public int getRe_ref_no(){ return re_ref_no; }
	public void setRe_ref_no(int re_ref_no){ this.re_ref_no = re_ref_no; }
	public String getRe_ref_name(){ return re_ref_name; }
	public void setRe_ref_name(String re_ref_name){ this.re_ref_name = re_ref_name; }
	public String getRe_content(){ return re_content; }
	public void setRe_content(String re_content){ this.re_content = re_content; }
	public int getRe_grade(){ return re_grade; }
	public void setRe_grade(int re_grade){ this.re_grade = re_grade; }
	public int getRe_recommend_cnt(){ return re_recommend_cnt; }
	public void setRe_recommend_cnt(int re_recommend_cnt){ this.re_recommend_cnt = re_recommend_cnt; }
	public Date getRe_regidate(){ return re_regidate; }
	public void setRe_regidate(Date re_regidate){ this.re_regidate = re_regidate; }
	public String getRe_img_url(){ return re_img_url; }
	public void setRe_img_url(String re_img_url){ this.re_img_url = re_img_url; }
	public String getRe_writer_id(){ return re_writer_id; }
	public void setRe_writer_id(String re_writer_id){ this.re_writer_id = re_writer_id; }
}
