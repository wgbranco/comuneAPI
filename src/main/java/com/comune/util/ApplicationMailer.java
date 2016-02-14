package com.comune.util;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.comune.model.User;

@Service("mailService")
public class ApplicationMailer
{
    @Autowired
    private MailSender mailSender;
 
    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    
    public void sendResetTokenEmail(String token, User user) 
	{
	    String message = "Sua nova senha �: ";
	    String message2 = "Utilize esta senha em seu pr�ximo acesso.\n\n� aconselh�vel que a senha seja trocada o mais r�pido poss�vel.";
	    
	    sendMail(user.getEmail(), "Sua nova senha", message + token + "\n\n" + message2);		    
	}
 
}
