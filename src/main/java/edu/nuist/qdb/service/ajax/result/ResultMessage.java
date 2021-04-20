package edu.nuist.qdb.service.ajax.result;

import lombok.Data;

@Data
public class ResultMessage {
	private int code;
	private String msg;

	private Object obj;

	public ResultMessage copy() {
		ResultMessage msg = new ResultMessage();
		msg.setCode(this.code);
		msg.setMsg(this.msg);
		return msg;
	}
}


