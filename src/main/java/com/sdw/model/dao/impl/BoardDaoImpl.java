package com.sdw.model.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdw.base.dao.impl.BBaseDaoImpl;
import com.sdw.model.dao.IBoardDAO;
import com.sdw.model.entity.Mo_Board;

@Repository("boardDao")
public class BoardDaoImpl extends BBaseDaoImpl<Mo_Board, String>  implements IBoardDAO {


}
