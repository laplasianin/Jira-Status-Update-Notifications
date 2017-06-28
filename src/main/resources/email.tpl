paragraphs.each { paragraph ->

    h3(paragraph.paragraphDescription)
    paragraph.items.each { item ->
        ul {
            item.tickets.issues.each { ticket ->
                li("${baseUrl}browse/${ticket.key} ${epics[ticket.fields?.customfield_10008 ?: ""] ? [epics[ticket.fields?.customfield_10008 ?: ""]] : ''} ${ (!epics[ticket.fields?.customfield_10008 ?: ""] && ticket.fields?.components?.name) ? ticket.fields?.components?.name : ''} ${ticket.fields.summary} - ${item.description}")
            }
        }
    }
    hr()

}