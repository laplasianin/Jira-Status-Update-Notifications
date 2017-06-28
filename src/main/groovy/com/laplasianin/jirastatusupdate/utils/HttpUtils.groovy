package com.laplasianin.jirastatusupdate.utils

class HttpUtils {

    static final String runGet(config, name, params = '') {
        def string = "curl -u ${config.jiraUsername}:${config.jira{Password} -X GET -H \"Accept: application/json\" ${config.baseUrl}${config.restApi}${name}${params ?: ''}"
        println("Executing ${URLDecoder.decode(string)}")
        return string.execute().text
    }

}