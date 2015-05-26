package com.manager.gams.action;
import static com.manager.sys.common.StringUtil.toJson;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manager.sys.domain.UserInfo;
import com.manager.gams.domain.PartInfo;
import com.manager.gams.service.PartInfoService;

@Controller
@RequestMapping(value = "/part")
public class PartAction {
	private PartInfoService partService;

	public void setPartService(PartInfoService partService) {
		this.partService = partService;
	}

	@RequestMapping(value = "/getpart")
	@ResponseBody
	public String getpartInfo() throws Exception {
		List<PartInfo> list = partService.findPartList();
		return toJson(list);
	}

	@RequestMapping(value = "/getpartid")
	@ResponseBody
	public String getpartidInfo(String part,HttpServletRequest req) throws Exception {

		List<PartInfo> list = partService.findPartidList(part);
		//获取session内容
		UserInfo usr =  (UserInfo) req.getSession().getAttribute("usrInfo");
		String str = usr.getPart();
		System.out.println(str);
		return toJson(list);
	}

}
