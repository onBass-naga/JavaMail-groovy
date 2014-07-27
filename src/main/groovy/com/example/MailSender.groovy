package com.example

import com.sun.net.ssl.internal.ssl.Provider

import javax.mail.Message
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import java.security.Security

/**
 * Created by naga on 2014/07/25.
 */
class MailSender {

    def send(String address) {

        Security.addProvider(new Provider())

        def session = SmtpSession.instance()
        Message msg = new MimeMessage(session)
        msg.setFrom(new InternetAddress(
                "noreply@example.com", "iso-2022-jp"))
        msg.setRecipients(Message.RecipientType.TO, address)
        msg.setReplyTo(new InternetAddress("reply_to@example.com"))

        msg.setSubject("ご登録ありがとうございます","iso-2022-jp")

        def multipart = new MimeMultipart()
        def bp1 = new MimeBodyPart()
        bp1.setText("心当たりがなかったらそのまま返信してください。","iso-2022-jp")
        multipart.addBodyPart(bp1)

        msg.setContent(multipart)
        Transport.send(msg)
    }

    public static void main(String[] args) {

        def toAddress = ''
        new MailSender().send(toAddress)
    }
}
