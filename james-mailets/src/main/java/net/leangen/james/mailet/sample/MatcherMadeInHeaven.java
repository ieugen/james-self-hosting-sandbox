package net.leangen.james.mailet.sample;

import java.util.Collection;
import java.util.Collections;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.james.core.MailAddress;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMatcher;

@Slf4j
public class MatcherMadeInHeaven extends GenericMatcher {

  @Override
  public void init() throws MessagingException {
    log.info("Build Heaven. Initialize matcher");
  }

  @Override
  public Collection<MailAddress> match(Mail mail) throws MessagingException {
    log.info("Matching for heaven {}", mail.getName());
    return Collections.emptyList();
  }
}
