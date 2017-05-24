package com.tangwl.ssm.web;

import com.alibaba.fastjson.JSON;
import com.tangwl.ssm.entity.*;
import com.tangwl.ssm.service.*;
import com.tangwl.ssm.util.MultipleTree;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/costing")
public class CostingController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CbFactProjectDtService cbFactProjectDtService;
	@Autowired
	private CbFactYfkService cbFactYfkService;

	@ResponseBody
	@RequestMapping(value = "/getVnames", method = RequestMethod.GET)
	public List<String> getVnames(HttpServletResponse response,HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		String unitname = request.getParameter("unitName");
		List<String> list =cbFactProjectDtService.getVnames(unitname);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/getUnitNames", method = RequestMethod.GET)
	public List<String> getUnitNames(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin","*");
		List<String> list =cbFactProjectDtService.getUnitNames();
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/getCostMain", method = RequestMethod.GET)
	public List<CbFactProjectDt> getAllSalesplan(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
		List<CbFactProjectDt> list = cbFactProjectDtService.getCostMain();
		return  list;
	}

	/**
	 * 构成分析
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGcfx", method = RequestMethod.GET)
	public List<CbFactProjectDt> getGcfx(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		String vname = request.getParameter("vname");
		List<CbFactProjectDt> list =cbFactProjectDtService.getGcfxByVname(vname);
		return list;
	}

	/**
	 * 建筑单方对比分析
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getJzdf", method = RequestMethod.GET)
	public List<CbFactProjectDt> getJzdf(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		String typename2 = request.getParameter("typename2");
		List<CbFactProjectDt> list =cbFactProjectDtService.getJzdfByTypename2(typename2);
		return list;
	}
	/**
	 * 动态成本
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDtcb", method = RequestMethod.GET)
	public List<CbFactProjectDt> getDtcb(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Content-Type","application/json;charset=UTF-8");
		String vname = request.getParameter("vname");
		List<CbFactProjectDt> list =cbFactProjectDtService.getDtcbByVname(vname);

		return list;
	}
	/**
	 *
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDtcbSum", method = RequestMethod.GET)
	public List<CbFactProjectDt> getDtcbSum(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Content-Type","application/json;charset=UTF-8");
		String vname = request.getParameter("vname");
		List<CbFactProjectDt> list =cbFactProjectDtService.getDtcbByVname(vname);

		return list;
	}
	/**
	 * 动态成本合同明细
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDtcbContract", method = RequestMethod.GET)
	public List<CbFactProjectDt> getDtcbContract(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Content-Type","application/json;charset=UTF-8");
		String PK_CORP = request.getParameter("PK_CORP");
		String PK_PROJECT = request.getParameter("PK_PROJECT");
		String PK_ELEM = request.getParameter("PK_ELEM");
		List<CbFactProjectDt> list =cbFactProjectDtService.getContractDetails(PK_CORP,PK_PROJECT,PK_ELEM);

		return list;
	}

	/**
	 * 合同台账
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getHttz", method = RequestMethod.GET)
	public List<Object> getHttz(HttpServletResponse response, HttpServletRequest request){
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Content-Type","application/json;charset=UTF-8");
		String vname = request.getParameter("vname");
		List<CbFactYfk> list =cbFactYfkService.queryHttzByProject(vname);
		List<String> typeList = cbFactYfkService.getContracttypeByProject(vname);
		List<Object> resultList = new ArrayList<>();
		int dx = 1;
		for(int i=0;i<typeList.size();i++) {
			Map<String,Object> map = new HashMap<>();
			List<CbFactYfk> templist = new ArrayList<>();
			for (CbFactYfk cfy : list) {
				if(typeList.get(i).equals(cfy.getVname3())){
					cfy.setIndex(dx);
					templist.add(cfy);
					dx++;
				}
				if(cfy.getUnitname().equals("合计")){
					map.put("sum",cfy);
				}
			}

			map.put("name",typeList.get(i).toString());
			map.put("list",templist);
			resultList.add(map);
		}

		return resultList;
	}

	public static String replacer(String outBuffer) {
		String data = outBuffer;
		try {
			data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			data = data.replaceAll("\\+", "%2B");
			data = URLDecoder.decode(data, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
