package com.nuance.imaging.ops.opsclient.enums;

public enum JobStateEnum {
	CREATED(0), STARTED(1), RUNNING(2), COMPLETED(3), FAILED(4), CANCELLED(5), ABANDONED(6);
	
	private final int id;
	
	private JobStateEnum(final int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}

}
