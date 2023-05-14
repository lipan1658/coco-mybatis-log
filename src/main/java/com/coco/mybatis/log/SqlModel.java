package com.coco.mybatis.log;

/**
 * SqlModel
 *
 * @author lp
 * @version 1.0
 * @description TODO
 * @date 2023/5/14 11:26
 */
public class SqlModel {

    private String sqlString;

    private String parameters;

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
