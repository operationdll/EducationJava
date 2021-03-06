package com.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dao.BaseMapper;
import com.dto.SchoolDto;

@Controller
@RequestMapping(value = "/myschool")
public class SchoolControl {

	public static Logger log = Logger.getLogger(SchoolControl.class);

	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 初始化页面
	 */
	@RequestMapping(value = "/init.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String init() {
		return "school";
	}

	/**
	 * 获取信息
	 * 
	 * @param req
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/getSchoolList.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void getSchoolList(HttpServletRequest req,
			HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("datas", baseMapper.selectSchools());
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(map));
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("SchoolControl->getAreaList报错:" + e);
		}
	}

	/**
	 * 增加信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/addItem.do", method = { RequestMethod.POST })
	public void addItem(HttpServletRequest req, HttpServletResponse response,
			String name, int aid) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			SchoolDto SchoolDto = new SchoolDto();
			SchoolDto.setAid(aid);
			SchoolDto.setName(URLDecoder.decode(name, "UTF-8"));
			int num = baseMapper.insertSchool(SchoolDto);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->addItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 删除信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/delItem.do", method = { RequestMethod.POST })
	public void delItem(HttpServletRequest req, HttpServletResponse response,
			int id) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			int num = baseMapper.deleteSchool(id);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->delItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 修改信息
	 * 
	 * @param req
	 * @param response
	 * @param name
	 */
	@RequestMapping(value = "/updItem.do", method = { RequestMethod.POST })
	public void updItem(HttpServletRequest req, HttpServletResponse response,
			int id, String name,int aid) {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = null;
		try {
			SchoolDto SchoolDto = new SchoolDto();
			SchoolDto.setId(id);
			SchoolDto.setAid(aid);
			SchoolDto.setName(URLDecoder.decode(name, "UTF-8"));
			int num = baseMapper.updateSchool(SchoolDto);
			out = response.getWriter();
			if (num > 0) {
				out.print(JSON.parse("{code:0}"));
			} else {
				out.print(JSON.parse("{code:1}"));
			}
		} catch (Exception e) {
			log.error("SchoolControl->updItem报错:" + e.toString());
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public BaseMapper getBaseMapper() {
		return baseMapper;
	}

	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}
}
