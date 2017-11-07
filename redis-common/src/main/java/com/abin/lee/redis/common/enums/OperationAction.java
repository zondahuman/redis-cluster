package com.abin.lee.redis.common.enums;

/**
 * Description:
 * Author: abin
 * Update: (2015-09-05 15:19)
 */
public enum OperationAction implements Action{
    SEARCH_SERVICE("SearchService");

    public String serviceName;

    private OperationAction(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public static OperationAction getAction(String param){
        for(OperationAction action:OperationAction.values()){
            if(action.getServiceName().equals(param))
                return action;
        }
        return null;
    }
}
