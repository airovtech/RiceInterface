package net.smartworks.model.filter;

import net.smartworks.model.BaseObject;

public class SearchFilter extends BaseObject{
	
	public static final String SYSTEM_FILTER_PREFIX = "system.";
	public static final String FILTER_ALL_INSTANCES = SYSTEM_FILTER_PREFIX + "allInstances";
	
	private Condition[] conditions;

	public Condition[] getConditions() {
		return conditions;
	}
	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

	public SearchFilter(){
		super();
	}
	
	public SearchFilter(String id, String name){
		super(id, name);
	}
	
	public SearchFilter(String id, String name, Condition[] conditions){
		super(id, name);
		this.conditions = conditions;
	}

}
