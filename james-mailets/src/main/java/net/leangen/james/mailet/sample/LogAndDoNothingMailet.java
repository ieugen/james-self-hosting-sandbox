package net.leangen.james.mailet.sample;

import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMailet;
import org.apache.mailet.base.GenericMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LogAndDoNothingMailet extends GenericMailet {

  @Override
  public void init() throws MessagingException {
    log.info("initialize Custom mailet");
  }

  @Override
  public void service(Mail mail) throws MessagingException {
    log.info("Service the email {} using the Custom mailet", mail.getName());
  }
}
