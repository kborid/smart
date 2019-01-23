package com.kborid.smart.network;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建请求处理
 * 
 * @author LiaoBo
 */
public class RequestBeanBuilder {

	private Map<String, Object> head;
	private Map<String, Object> body;

	private RequestBeanBuilder(boolean isNeedTicket) {
		head = new HashMap<String, Object>();
		body = new HashMap<String, Object>();
	}

	/**
	 * 构建请求
	 * 
	 * @param isNeedTicket
	 *            是否需要ticket ,如果需要登录就需要ticket
	 * @return
	 */
	public static RequestBeanBuilder create(boolean isNeedTicket) {
		return new RequestBeanBuilder(isNeedTicket);
	}

	public RequestBeanBuilder addHeadToken(String token) {
		return addHead("accessTicket", token);
	}

	public RequestBeanBuilder addHead(String key, Object value) {
		head.put(key, value);
		return this;
	}

	public RequestBeanBuilder addBody(String key, Object value) {
		body.put(key, value);
		return this;
	}

	private String sign() {
		return "ac5d75069a9d970fe8ea594a8f0eabcbbae457d9cf69e9fc0114c69511d1d6bb5944481529aadb47";
	}

	/**
	 * 请求数据的json字符串
	 * 
	 * @return
	 */
	public String toJson() {
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("head", head);
		json.put("body", body);

		head.put("appid", "ARD-023-0001");
		head.put("sign", sign());
		head.put("version", "2.0");
		head.put("siteid", "500000");
		head.put("appversion", "4.0.4");
		return JSON.toJSONString(json);
	}

	/**
	 * 请求数据
	 * 
	 * @return
	 */
	public ResponseData syncRequest(RequestBeanBuilder builder) {
		ResponseData data = new ResponseData();
		data.data = builder.toJson();
		data.type = "POST";
		return data;
	}
}
