package com.sdw.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdw.base.service.impl.IZXServiceImpl;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.dao.IBoardDAO;
import com.sdw.model.entity.Mo_Board;
import com.sdw.model.service.IBoardService;

@Service("boardService")
@Transactional
public class BoardServiceImpl  extends IZXServiceImpl <Mo_Board, String> implements IBoardService {
	@Autowired
	private IBoardDAO boardDao;


	@Override
	public ServiceResult<Page<Mo_Board>> pageList(Page<Mo_Board> page) {
		page.setSize(10);
		page.setStart(0);
		page.setLength(10);
		page = boardDao.pageList(page);
		return new ServiceResult<Page<Mo_Board>>(true, page);
	}


	@Override
	public ServiceResult<Mo_Board> addBoard(Mo_Board board) {
		boardDao.save(board);
		return new ServiceResult<>(true,board);
	}


	@Override
	public ServiceResult<Mo_Board> editBoard(Mo_Board board) {
		boardDao.update(board);
		return new ServiceResult<>(true,board);
	}


	@Override
	public ServiceResult<Mo_Board> delBoard(Mo_Board board) {
		boardDao.delete(board);
		//boardDao.delete(board.getId());
		return new ServiceResult<>(true,board);
	}

	public Mo_Board getById(String id) {
		return boardDao.get(id);
	}
}
