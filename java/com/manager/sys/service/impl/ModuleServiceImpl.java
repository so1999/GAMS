package com.manager.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manager.sys.base.dao.HibernateDao;
import com.manager.sys.domain.Module;
import com.manager.sys.service.ModuleService;

import static com.manager.sys.common.StringUtil.isEmpty;
import static com.manager.sys.common.StringUtil.isNum;

/**
 * 管理栏目信息的Service
 * 
 * @author HMY
 * 
 */
public class ModuleServiceImpl implements ModuleService {
	private HibernateDao hdao;

	@Override
	public void saveOrupdate(Module mod) {
		hdao.saveOrUpdate(mod);
	}

	/**
	 * @param hibernateDao
	 *            the hibernateDao to set
	 */
	public void setHdao(HibernateDao hdao) {
		this.hdao = hdao;
	}

	@Override
	public Module get(Integer id) throws Exception {
		if (!isEmpty(id)) {
			return (Module) hdao.get(Module.class, id);
		}
		return null;
	}

	/**
	 * 分页显示栏目信息的
	 */
	@Override
	public Map<String, Object> findByPage(Integer start, Integer limits, Object objs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> pObj = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select mods from Module as mods left join mods.parent as p where 1=1 ");
		if (null != objs) {
			queryString.append(" and (mods.moduleName like ?");
			pObj.add("%" + objs + "%");
			queryString.append(" or p.moduleName like ?");
			pObj.add("%" + objs + "%");
			queryString.append(")");
		}
		List<Module> list = new ArrayList<Module>();
		List<Object> objList = hdao.findPageByQuery(queryString.toString(), pObj.toArray(), start, limits);
		if (objList != null) {
			for (int i = 0, j = objList.size(); i < j; i++) {
				Module mod = (Module) objList.get(i);
				Module module = new Module();
				module.setId(mod.getId());
				module.setModuleName(mod.getModuleName());
				module.setModuleHref(mod.getModuleHref());
				module.setModuleDesc(mod.getModuleDesc());
				module.setModuleOrder(mod.getModuleOrder());
				module.setModuleSign(mod.getModuleSign());
				module.setParentName(!isEmpty(mod.getParent()) ? mod.getParent().getModuleName() : "顶级栏目");
				list.add(module);
			}
		}

		map.put("rows", list);
		map.put("total", hdao.getCount(Module.class, null, null));

		return map;
	}

	/**
	 * 获取父级栏目的集合信息
	 */
	@Override
	public List<Map<String, Object>> getParentModules() throws Exception {
		List<Map<String, Object>> modList = new ArrayList<Map<String, Object>>();
		List<Object> objList = hdao.findByQuery("from Module mod where mod.parent is null order by mod.moduleOrder asc ", null);
		Map<String, Object> fmap = new HashMap<String, Object>();
		fmap.put("id", 0);
		fmap.put("text", "顶级栏目");
		fmap.put("iconCls", "easyui-icon-folder");
		fmap.put("selected", true);
		modList.add(fmap);
		if (null != objList && objList.size() > 0) {
			for (Object moduleObj : objList) {
				Module module = (Module) moduleObj;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", module.getId());
				map.put("text", module.getModuleName());
				map.put("iconCls", "easyui-icon-folder");
				modList.add(map);
			}
		}
		return modList;
	}

	/**
	 * 检查这个角色是否有这个模块
	 * 
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleModule(Integer moduleId) throws Exception {
		List<Object> obj = (List<Object>) hdao.findByQuery("select roleInfo.roleId  from RoleInfo roleInfo where roleInfo.moduleId = ?",
				new Object[] { moduleId });
		System.out.println("检查角色是否有模块：" + obj);
		if (obj != null) {
			return true;
		}
		return false;
	}

	/**
	 * 未知
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Module> findBySQL(Integer parentId, Integer roleId) throws Exception {
		List<Module> list = new ArrayList<Module>();
		String query = "select  tchmod.module_id , tchmod.module_name,role.role_id from tch_module as tchmod left join tch_role_module as role on tchmod.module_id = role.module_id  order by tchmod.module_order asc group by tchmod.module_id";
		@SuppressWarnings("unchecked")
		List<Object[]> objs = (List<Object[]>) hdao.findBySQLQuery(query, new Object[] { parentId, roleId });
		if (objs != null) {
			for (Object[] obj : objs) {
				Module mod = new Module();
				mod.setId(obj[0] != null ? Integer.valueOf(String.valueOf(obj[0])) : 0);
				mod.setModuleName(obj[1] != null ? String.valueOf(obj[1]) : null);
				mod.setRoleId(obj[2] != null ? String.valueOf(obj[2]) : null);
				list.add(mod);
			}
		}
		return list;
	}

	/**
	 * 根据父节点编号即parent ID 获取父栏目信息。之后通过父栏目信息获取子栏目信息从而形成树形结构的数据
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Object> findModule(Integer parentId) throws Exception {
		List<Object> pObj = new ArrayList<Object>();
		StringBuilder queryString = new StringBuilder();
		queryString.append(" from Module module ");
		if (isEmpty(parentId) || 0 == parentId) {
			queryString.append(" where  module.parent is null");
		} else {
			queryString.append(" where module.parent.id = ?");
			pObj.add(parentId);
		}
		queryString.append(" order by module.moduleOrder desc");
		List<Object> modList = hdao.findByQuery(queryString.toString(), pObj.toArray());
		return modList;
	}

	/**
	 * 通过父节点及角色编号获取已经赋予角色的信息并设置选中。
	 */
	@Override
	public List<Map<String, Object>> findTree(Integer parentId, Integer roleId) throws Exception {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		@SuppressWarnings("unchecked")
		List<Object[]> objs = (List<Object[]>) hdao.findBySQLQuery("select role_id,module_id from m_role_module where role_id = ?",
				new Object[] { roleId });
		if (objs != null && objs.size() > 0) {
			for (Object[] obj : objs) {
				map.put(obj[1], obj[0]);
			}
		}
		List<Object> list = findModule(parentId);
		if (list != null) {
			for (Object modObj : list) {
				Module mod = (Module) modObj;
				System.out.println("父栏目编号：" + parentId + "子栏目编号：" + mod.getId() + "__" + mod.getModuleName());
				Map<String, Object> dto = new HashMap<String, Object>();
				dto.put("id", mod.getId());
				dto.put("text", mod.getModuleName());
				if (map.get(mod.getId()) != null) {
					dto.put("checked", true);
				} else {
					dto.put("checked", false);
				}
				Map<String, String> attr = new HashMap<String, String>();
				attr.put("attr", "modules");
				dto.put("attributes", attr);
				treeList.add(dto);
			}
		}
		return treeList;
	}

	/**
	 * 根据栏目编号判断改编号是否是父栏目的编号
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean isParent(Integer id) throws Exception {
		List<Object> objs = (List<Object>) hdao.findByQuery("select mods.id from Module as mods left join mods.parent as p where p.id = ?",
				new Object[] { id });
		if (null != objs && objs.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除信息，同时判断栏目下是否有子栏目及将要删除的栏目是否存在
	 */
	@Override
	public void delete(String[] ids) throws Exception {
		if (null != ids && ids.length > 0) {
			for (String id : ids) {
				Integer tempId = isNum(id) ? Integer.valueOf(id) : 0;
				Module modules = (Module) hdao.get(Module.class, tempId);
				if (isEmpty(modules)) {
					throw new Exception("删除的栏目信息不存在，请重新验证");
				} else if (isParent(tempId)) {
					throw new Exception("编号：" + tempId + "  \"" + modules.getModuleName() + "\" 栏目下有子栏目不能删除");
				} else {
					hdao.delete(modules);
				}
			}
		}
	}

	@Override
	public String findModuleName(String sign) throws Exception {
		String query = "select mods.moduleName from Module mods where mods.moduleSign = ?";
		List<Object> objList = hdao.findByQuery(query, new Object[] { sign });
		if (objList != null && objList.size() > 0) {
			return String.valueOf(objList.get(0));
		}
		return null;
	}
}
