package com.yootk.mall.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.yootk.common.annotation.Repository;
import com.yootk.common.dao.abs.AbstractDAO;
import com.yootk.mall.dao.IGoodsDAO;
import com.yootk.mall.vo.Goods;

@Repository
public class GoodsDAOImpl extends AbstractDAO implements IGoodsDAO {

	@Override
	public boolean doCreate(Goods vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doEdit(Goods vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemove(Set<Long> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Goods findById(Long id) throws SQLException {
		String sql = "SELECT gid,name,price,photo FROM goods WHERE gid=?";
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setLong(1, id);
		return super.handleResultToVO(super.pstmt.executeQuery(),Goods.class);
	}

	@Override
	public List<Goods> findAll() throws SQLException {
		return null;
	}

	@Override
	public List<Goods> findSplit(Long currentPage, Integer lineSize) throws SQLException { 
		List<Goods> all = new ArrayList<Goods>();
		String sql = "SELECT gid,name,price,photo FROM goods LIMIT " + (currentPage - 1) * lineSize + "," + lineSize;
		super.pstmt = super.conn.prepareStatement(sql);
		return super.handleResultToList(super.pstmt.executeQuery(),Goods.class);
	}

	@Override
	public List<Goods> findSplit(Long currentPage, Integer lineSize, String column, String keyWord) throws SQLException {
		List<Goods> all = new ArrayList<Goods>();
		String sql = "SELECT gid,name,price,photo FROM goods WHERE " + column + " LIKE ? LIMIT " + (currentPage - 1) * lineSize + "," + lineSize;
		super.pstmt = super.conn.prepareStatement(sql);
		super.pstmt.setString(1, "%" + keyWord + "%");
		return super.handleResultToList(super.pstmt.executeQuery(),Goods.class);
	}

	@Override
	public Long getAllCount() throws SQLException {
		return super.handleCount("goods");
	}

	@Override
	public Long getAllCount(String column, String keyWord) throws SQLException {
		return super.handleCount("goods",column,keyWord) ;
	}

}
