package com.example

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

/**
 * Created by naga on 2014/07/25.
 */
class SmtpSession {

    def static instance() {

        def smtpHost = "your.host.example.com"
        def smtpPort = "587"
        def address = "noreply@example.com"
        def password = "password"
        def returnPath = "return_path@example.com"

        Properties props = new Properties()
        props.put("mail.smtp.host", smtpHost)
        props.put("mail.smtp.port", smtpPort)
        props.put("mail.smtp.from", returnPath)
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.socketFactory.fallback", "true");

        Session session = Session.getDefaultInstance(props,
                [
                        getPasswordAuthentication: {
                            return new PasswordAuthentication(
                                    address, password)
                        }
                ] as Authenticator
        )

        return session
    }
}
