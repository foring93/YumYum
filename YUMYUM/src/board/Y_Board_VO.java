package board;

import java.sql.Date;

public class Y_Board_VO
{
	private int board_no;
	private String board_category;
	private String board_writer;
	private String board_title;
	private String board_content;
	private int board_reply_cnt;
	private int board_view_cnt;
	private Date board_regidate;
	private Date board_expirate;
	private int board_is_all_time;
	
	public Y_Board_VO()
	{
		
	}

	public int getBoard_no(){ return board_no; }
	public void setBoard_no(int board_no){ this.board_no = board_no; }

	public String getBoard_category() { return board_category; }
	public void setBoard_category(String board_category){ this.board_category = board_category; }
	
	public String getBoard_writer(){ return board_writer; }
	public void setBoard_writer(String board_writer){ this.board_writer = board_writer; }

	public String getBoard_title(){ return board_title; }
	public void setBoard_title(String board_title){ this.board_title = board_title; }

	public String getBoard_content(){ return board_content; }
	public void setBoard_content(String board_content){ this.board_content = board_content; }

	public int getBoard_reply_cnt(){ return board_reply_cnt; }
	public void setBoard_reply_cnt(int board_reply_cnt){ this.board_reply_cnt = board_reply_cnt; }

	public int getBoard_view_cnt(){ return board_view_cnt; }
	public void setBoard_view_cnt(int board_view_cnt){ this.board_view_cnt = board_view_cnt; }

	public Date getBoard_regidate(){ return board_regidate; }
	public void setBoard_regidate(Date board_regidate){ this.board_regidate = board_regidate; }

	public Date getBoard_expirate(){ return board_expirate; }
	public void setBoard_expirate(Date board_expirate){ this.board_expirate = board_expirate; }

	public int getBoard_is_all_time(){ return board_is_all_time; }
	public void setBoard_is_all_time(int board_is_all_time){ this.board_is_all_time = board_is_all_time; }
}