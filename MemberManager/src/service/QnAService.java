package service;

import java.util.ArrayList;

import dao.MemberDAO;
import dao.QnADAO;
import dto.QnaDTO;

public class QnAService {
	private static QnAService instance = new QnAService();
	private QnADAO dao;
	private QnAService() {
		dao = QnADAO.getInstance();
	}
	
	public static QnAService getInstance() {
		if(instance==null)
			instance= new QnAService();
		return instance;
	}

	public int insertQnA(QnaDTO dto) {
		return dao.insertQna(dto);
	}

	public ArrayList<QnaDTO> selectQnaList(String id, int pageNo,String grade) {
		
		if(grade.equals("master"))
			return dao.selectQnaAdminList(pageNo);
		return dao.selectQnaList(id, pageNo);
	}
	
	
}










