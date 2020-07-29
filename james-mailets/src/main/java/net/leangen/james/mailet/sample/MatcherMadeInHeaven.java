package net.leangen.james.mailet.sample;

import java.util.Collection;
import java.util.Collections;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.james.core.MailAddress;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j
public class MatcherMadeInHeaven extends GenericMatcher {
  private static final Logger LOGGER = LoggerFactory.getLogger(GenericMatcher.class);

  @Override
  public void init() throws MessagingException {
    LOGGER.info("Build Heaven. Initialize the Custom matcher");
  }

  @Override
  public Collection<MailAddress> match(Mail mail) throws MessagingException {
    LOGGER.info("Matching for heaven {} using the Customer matcher", mail.getName());
    return Collections.emptyList();
  }
}
