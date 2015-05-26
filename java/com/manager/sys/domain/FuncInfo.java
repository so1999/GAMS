package com.manager.sys.domain;

/**
 * 模块功能的实体类
 * 
 * @author HMY
 * 
 */
public class FuncInfo implements java.io.Serializable {
	private static final long serialVersionUID = 465516166666598659L;
	private Integer id;
	private String funcName;
	private String funcSign;
	private String funcDesc;
	private Module module;
	private String moduleName;
	private Integer moduleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncSign() {
		return funcSign;
	}

	public void setFuncSign(String funcSign) {
		this.funcSign = funcSign;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FuncInfo [id=");
		builder.append(id);
		builder.append(", funcName=");
		builder.append(funcName);
		builder.append(", funcSign=");
		builder.append(funcSign);
		builder.append(", funcDesc=");
		builder.append(funcDesc);
		builder.append(", module=");
		builder.append(module);
		builder.append(", moduleName=");
		builder.append(moduleName);
		builder.append(", moduleId=");
		builder.append(moduleId);
		builder.append("]");
		return builder.toString();
	}
}
