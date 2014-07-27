package com.example

import javax.mail.Session
import javax.mail.PasswordAuthentication
import javax.mail.Authenticator

/**
 * Created by naga on 2014/07/25.
 */
class PopStore {

    def static instance() {


        def popHost = "your.host.example.com"
        def popPort = "995"
        def address = "reply_to@example.com"
        def password = "password"

        Properties props = new Properties()
        props.put("mail.pop3.host", popHost)
        props.put("mail.pop3.port", popPort)
        props.put("mail.pop3.socketFactory.port", popPort)
        props.put("mail.pop3.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory")
        props.put("mail.pop3.socketFactory.fallback", "false")

        Session session = Session.getDefaultInstance(props,
                [
                        getPasswordAuthentication: {
                            return new PasswordAuthentication(
                                    address, password)
                        }
                ] as Authenticator
        )

        session.getStore("pop3")
    }
}
