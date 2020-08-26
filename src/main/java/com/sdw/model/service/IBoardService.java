package com.sdw.model.service;

import com.vogue.circle.database.service.BaseService;
import com.sdw.base.utils.Page;
import com.sdw.base.utils.ServiceResult;
import com.sdw.model.entity.Mo_Board;

public interface IBoardService extends BaseService<Mo_Board, String>{
	public ServiceResult<Mo_Board> addBoard(Mo_Board board);

	public ServiceResult<Mo_Board> editBoard(Mo_Board board);

	public ServiceResult<Mo_Board> delBoard(Mo_Board board);

	public ServiceResult<Page<Mo_Board>> pageList(Page<Mo_Board> page);

	public Mo_Board getById(String id);
}
