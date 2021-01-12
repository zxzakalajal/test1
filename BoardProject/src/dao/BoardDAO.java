package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import config.DBManager;
import dto.BoardDTO;
import dto.CommentDTO;

public class BoardDAO {
	private static BoardDAO instance = new BoardDAO();
	private DBManager manager;

	private BoardDAO() {
		manager = DBManager.getInstance();
	}

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
		return instance;
	}

	// 게시글 번호 뽑는 부분
	public int getBoardNO() {
		String sql = "select bno_seq.nextval from dual";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, rs);
		}
		return result;
	}

	public void insertBoardDTO(BoardDTO dto) {
		String sql = "insert into board(bno,title,writer,content) values(?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			Connection conn = manager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getWriter());
			pstmt.setString(4, dto.getContent());

			int count = pstmt.executeUpdate();
			conn.commit();
			System.out.println(count + "건 게시글 등록 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, null);
		}
	}

	public BoardDTO selectBoardDTO(int bno) {
		String sql = "select * from board where bno = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO dto = null;
		try {
			pstmt =manager.getConn().prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, rs);
		}
		System.out.println(dto.toString());
		return dto;
	}

	public void addCount(int bno) {
		String sql = "update board set bcount=bcount + 1 where bno = ?";
		PreparedStatement pstmt = null;

		try {
			Connection conn = manager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, null);
		}

	}

	public void addLikeHate(int bno, int mode) {
		String sql;
		if (mode == 0)
			sql = "update board set blike = blike + 1 where bno=?";
		else
			sql = "update board set bhate = bhate + 1 where bno=?";

		PreparedStatement pstmt = null;

		try {
			Connection conn = manager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, null);
		}
	}

	public int selectLikeHate(int bno, int mode) {
		int result = 0;
		String sql;
		if (mode == 0)
			sql = "select blike from board where bno=?";
		else
			sql = "select bhate from board where bno=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, rs);
		}

		return result;
	}

	public ArrayList<BoardDTO> selectBoardList(int pageNo,String mode) {
		String sql = "select * from "
				+ "(select ceil(rownum / 7) as pagenum,bno,title,bdate,bcount,"
				+ "writer,content,blike,bhate,comment_count from "
				+ "(select b.*, nvl(c.comment_count,0) as comment_count from "
				+ "board b,(select bno, count(*) as comment_count from "
				+ "board_comment group by bno) c where b.bno = c.bno(+) order by b."+mode+" desc)) "
				+ "where pagenum = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			pstmt.setInt(1, pageNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new BoardDTO(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6),
						rs.getString(7), rs.getInt(8), rs.getInt(9),rs.getInt(10)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(pstmt, rs);
		}
		return list;
	}

	public int insertBoardComment(CommentDTO dto) {
		String sql = "insert into board_comment(cno, bno, writer, content) values(cno_seq.nextval,?,?,?)";
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setString(2, dto.getWriter());
			pstmt.setString(3, dto.getContent());
			count = pstmt.executeUpdate();
			manager.getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			manager.close(pstmt, null);
		}
		return count;
	}

	public ArrayList<CommentDTO> selectCommentDTO(int bno) {
		String sql = "select * from board_comment where bno = ? order by cno desc";
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new CommentDTO(rs.getInt(1),rs.getInt(2), 
						rs.getString(3),rs.getString(4),rs.getString(5),
						rs.getInt(6), rs.getInt(7)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			manager.close(pstmt, rs);
		}
		return list;
	}
	public int commentCount(int bno) {
		int result = 0;
		String sql = "select count(*) from board_comment where bno = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = DBManager.getInstance().getConn().prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.getInstance().close(pstmt, rs);
		}
		return result;
	}
	//전체 게시글 개수
	public int getCount() {
		String sql = "select count(*) from board";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = manager.getConn().prepareStatement(sql);
			rs  = pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			manager.close(pstmt, rs);
		}
		return result;		
	}
}

