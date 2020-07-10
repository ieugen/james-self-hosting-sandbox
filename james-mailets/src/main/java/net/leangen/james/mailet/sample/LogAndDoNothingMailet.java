package net.leangen.james.mailet.sample;

import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMailet;

@Slf4j
public class LogAndDoNothingMailet extends GenericMailet {

  @Override
  public void init() throws MessagingException {
    log.info("initialize mailet");
  }

  @Override
  public void service(Mail mail) throws MessagingException {
    log.info("Service the email {}", mail.getName());
  }
}
