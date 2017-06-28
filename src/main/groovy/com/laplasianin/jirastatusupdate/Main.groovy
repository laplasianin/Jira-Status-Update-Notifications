package com.laplasianin.jirastatusupdate

import groovy.json.JsonSlurper
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import groovyx.gpars.GParsPool

import static com.laplasianin.jirastatusupdate.utils.HttpUtils.runGet
import static com.laplasianin.jirastatusupdate.utils.MailUtils.sendMail
import static java.net.URLEncoder.encode

class Main {

    public static final String configFileDest = "/configuration.json"
    public static final String templateFileDest = "/email.tpl"

    public static void main(String[] args) {

        Map config = new JsonSlurper().parse(Main.class.getResource(configFileDest)) as Map

        GParsPool.withPool {
            config.paragraphs.eachParallel { paragraph ->
                GParsPool.withPool {
                    paragraph.items.eachParallel { item ->
                        item.tickets = new JsonSlurper().parseText(runGet(config, "search", "?jql=" + encode(item.query)))
                    }
                }
            }
        }


        String template = new MarkupTemplateEngine(new TemplateConfiguration())
                .createTemplate(Main.class.getResource(templateFileDest))
                .make(config)

        sendMail(config, template)

    }
}