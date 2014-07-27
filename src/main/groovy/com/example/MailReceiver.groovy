package com.example

import javax.mail.Folder
import javax.mail.Multipart
import javax.mail.Part
import javax.mail.Store
import java.security.Security
import com.sun.net.ssl.internal.ssl.Provider

/**
 * Created by naga on 2014/07/25.
 */
class MailReceiver {

    def recieve() {

        Security.addProvider(new Provider())

        Store store = null
        try {
            store = PopStore.instance()
            store.connect()
            def folder = store.getFolder("INBOX")
            folder.open(Folder.READ_ONLY)
            def messages = folder.getMessages()
            printMessages(messages)

            folder.close(false)

        } finally {
            if( store != null )store.close()
        }
    }

    def printMessages(messages) {

        messages.each { msg ->
            println("subject: ${msg.getSubject()}")
            println("date: ${msg.getSentDate()}")
            println("from: ${msg.getFrom()[0].getAddress()}")
            println("content-type: ${msg.getContentType()}")

            if (msg.getContentType().startsWith("text/plain")){
                println("content: ${msg.getContent()}")
            } else {
                if(msg.getContent() instanceof Multipart){
                    msg.getContent().eachWithIndex { content, i ->
                        println("content-type: ${content.getBodyPart(i).getContentType()}")
                        def disposition = content.getBodyPart(i).getDisposition()
                        if(!disposition.equals(Part.ATTACHMENT) &&
                                !disposition.equals(Part.INLINE)){
                            println("content: ${content.getBodyPart(i).getContent()}")
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        new MailReceiver().recieve()
    }
}
