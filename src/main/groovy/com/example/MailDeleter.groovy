package com.example

import javax.mail.Flags
import javax.mail.Folder
import javax.mail.Store
import java.security.Security
import com.sun.net.ssl.internal.ssl.Provider

/**
 * Created by naga on 2014/07/25.
 */
class MailDeleter {

    def keeps = []

    def delete() {

        Security.addProvider(new Provider())

        Store store = null
        try {
            store = PopStore.instance()
            store.connect()
            def folder = store.getFolder("INBOX")
            folder.open(Folder.READ_WRITE)
            folder.getMessages().each { msg ->

                def address = msg.getFrom()[0].getAddress()
                println("address: ${address}")
                if (!keeps.contains(address)) {
                    msg.setFlag(Flags.Flag.DELETED, true)
                }
            }
            folder.close(true)

        } finally {
            if( store != null )store.close()
        }
    }

    public static void main(String[] args) {

        new MailDeleter().delete()
    }
}
