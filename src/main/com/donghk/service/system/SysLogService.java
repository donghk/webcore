package com.donghk.service.system;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donghk.dao.system.SysLogMapper;
import com.donghk.entity.BaseEntity.ISVALID;
import com.donghk.entity.BaseEntity.YN;
import com.donghk.entity.system.SysLog;
import com.donghk.web.system.SysLogController;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月24日
 * @Description 日志管理
 */
@Service
@SuppressWarnings("unchecked")
public class SysLogService {

	private static final Logger log = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private SysLogMapper sysLogMapper;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月24日
	 * @Description 按条件获取日志列表
	 * @param map
	 * @return
	 */
	public List<SysLog> getAllLog(Map<String, Object> map) {

		List<SysLog> list = sysLogMapper.getAll(map);

		return list;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 插入日志
	 * @param type
	 *        日志类型
	 * @param detail
	 *        日志内容
	 * @return
	 */
	@Transactional
	public void AddLog(String type, String detail) {
		try {
			SysLog log = new SysLog();
			// 日志类型
			log.setLogType(type);
			// 日志详细信息
			log.setLogDetail(detail);
			log.setIsValid(ISVALID.YES.getValue());
			log.setIsDel(YN.NO.getValue());
			sysLogMapper.insert(log);
		} catch (Exception e) {
			log.error("记录系统操作日志出现系统错误：{}, 当前记录信息：{}", e.getMessage(), detail);
		}
	}

}
