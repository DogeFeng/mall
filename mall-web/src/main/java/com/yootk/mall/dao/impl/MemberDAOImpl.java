package com.yootk.mall.dao.impl;

import com.yootk.common.annotation.Repository;
import com.yootk.common.dao.abs.AbstractDAO;
import com.yootk.mall.dao.IMemberDAO;
import com.yootk.mall.vo.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
@Repository
public class MemberDAOImpl extends AbstractDAO implements IMemberDAO {

	@Override
	public boolean doCreate(Member vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doEdit(Member vo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doRemove(Set<String> ids) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Member findById(String id) throws SQLException {
		String sql = "SELECT mid,name,password FROM member WHERE mid=?" ;
		super.pstmt = super.conn.prepareStatement(sql) ;
		super.pstmt.setString(1, id);
		ResultSet rs = super.pstmt.executeQuery() ;
		if (rs.next()) {
			Member vo = new Member() ;
			vo.setMid(rs.getString(1));
			vo.setName(rs.getString(2));
			vo.setPassword(rs.getString(3));
			return vo ;
		}
		return null;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findSplit(Long currentPage, Integer lineSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findSplit(Long currentPage, Integer lineSize,String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getAllCount() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getAllCount(String column, String keyWord) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
