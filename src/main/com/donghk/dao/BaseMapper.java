package com.donghk.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.donghk.entity.BaseEntity;

@Repository
@SuppressWarnings("rawtypes")
public interface BaseMapper {

	/**
	 * @param entity
	 */
	public void insert(BaseEntity entity);

	/**
	 * @param entity
	 */
	public void update(BaseEntity entity);

	/**
	 * 删除一条记录
	 */
	public void delete(Map<String, Object> params);

	/**
	 * 批量删除
	 */
	public void deleteItems(Map<String, Object> params);

	/**
	 * 根据ID获取记录
	 */
	public BaseEntity get(String id);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月24日
	 * @Description 按条件查询
	 * @param where
	 *            查询条件
	 * @param orderstr
	 *            排序条件
	 * @param limit
	 *            分页条件
	 * @return
	 */
	public List getAll(Map<String, Object> map);

	/**
	 * 根据查询条件，获取记录数 where:查询条件
	 */
	public int getCount(Map<String, Object> map);

}
