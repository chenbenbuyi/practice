package data_structure.list.linkedlist.step;


import lombok.Data;

@Data
public class Step {
	
	public Step(String name, String uri) {
		this.name = name;
		this.uri = uri;
	}

	
	/** 
	* 步骤序号
	*/  
	private int code;
	
	/** 
	* 步骤名称
	*/  
	private String name;
	
	/** 
	* 步骤对应的URI
	*/  
	private String uri;
	
	/**
	* 下一步骤
	*/  
	Step next;
	
	/** 
	* 上一步骤
	*/  
	private Step prev;

	@Override
	public String toString() {
		return "Step{" +
				"code=" + code +
				", name='" + name + '\'' +
				", uri='" + uri + '\'' +
				'}';
	}
}