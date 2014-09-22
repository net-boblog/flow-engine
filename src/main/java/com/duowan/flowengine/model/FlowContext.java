package com.duowan.flowengine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.duowan.flowengine.engine.FlowEngine;
import com.duowan.flowengine.util.Listener;
import com.duowan.flowengine.util.Listenerable;

/**
 * 流程执行的上下文
 * 
 * @author badqiu
 * 
 */
public class FlowContext {

	private transient ExecutorService executorService;
	private transient FlowEngine flowEngine;
	private Map params; // 流程参数
	private Flow flow; // 流程
	private List<String> visitedTaskCodes = new ArrayList<String>(); //已经访问过的流程任务节点
	
	private transient Listenerable<FlowContext> listenerable = new Listenerable<FlowContext>();
	
	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}
	
	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<String> getVisitedTaskCodes() {
		return visitedTaskCodes;
	}

	public void setVisitedTaskCodes(List<String> visitedTaskCodes) {
		this.visitedTaskCodes = visitedTaskCodes;
	}

	public FlowEngine getFlowEngine() {
		return flowEngine;
	}

	public void setFlowEngine(FlowEngine flowEngine) {
		this.flowEngine = flowEngine;
	}
	
	public void addVisitedTaskCode(String taskCode) {
		getVisitedTaskCodes().add(taskCode);
		notifyListeners();
	}
	
	public void notifyListeners() {
		listenerable.notifyListeners(this, null);
	}

	public void addListener(Listener<FlowContext> t) {
		listenerable.addListener(t);
	}
	
	public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		ExecutorService executorService = getExecutorService();
		executorService.shutdown();
		executorService.awaitTermination(timeout, unit);
	}
	
}
