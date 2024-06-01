package ro.ieugen.sample.guice;

import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.apache.james.GuiceJamesServer;
import org.apache.james.JamesServerMain;
import org.apache.james.modules.MailboxModule;
import org.apache.james.modules.queue.activemq.ActiveMQQueueModule;
import org.apache.james.modules.data.JPADataModule;
import org.apache.james.modules.data.SieveJPARepositoryModules;
import org.apache.james.modules.mailbox.DefaultEventModule;
import org.apache.james.modules.mailbox.JPAMailboxModule;
import org.apache.james.modules.mailbox.LuceneSearchMailboxModule;
import org.apache.james.modules.mailbox.MemoryDeadLetterModule;
import org.apache.james.modules.protocols.IMAPServerModule;
import org.apache.james.modules.protocols.LMTPServerModule;
import org.apache.james.modules.protocols.ManageSieveServerModule;
import org.apache.james.modules.protocols.POP3ServerModule;
import org.apache.james.modules.protocols.ProtocolHandlerModule;
import org.apache.james.modules.protocols.SMTPServerModule;
import org.apache.james.modules.server.DataRoutesModules;
import org.apache.james.modules.server.DefaultProcessorsConfigurationProviderModule;
import org.apache.james.modules.server.InconsistencyQuotasSolvingRoutesModule;
import org.apache.james.modules.server.JMXServerModule;
import org.apache.james.modules.server.MailQueueRoutesModule;
import org.apache.james.modules.server.MailRepositoriesRoutesModule;
import org.apache.james.modules.server.MailboxRoutesModule;
import org.apache.james.modules.server.NoJwtModule;
import org.apache.james.modules.server.RawPostDequeueDecoratorModule;
import org.apache.james.modules.server.ReIndexingModule;
import org.apache.james.modules.server.SieveRoutesModule;
import org.apache.james.modules.server.TaskManagerModule;
import org.apache.james.modules.server.WebAdminReIndexingTaskSerializationModule;
import org.apache.james.modules.server.WebAdminServerModule;
import org.apache.james.spamassassin.SpamAssassinModule;
import org.apache.james.server.core.configuration.Configuration;

public class MyCustomServerMain implements JamesServerMain {

  private static final Module WEBADMIN =
      Modules.combine(
          new WebAdminServerModule(),
          new DataRoutesModules(),
          new InconsistencyQuotasSolvingRoutesModule(),
          new MailboxRoutesModule(),
          new MailQueueRoutesModule(),
          new MailRepositoriesRoutesModule(),
          new ReIndexingModule(),          
          new SieveRoutesModule(),
          new WebAdminReIndexingTaskSerializationModule());

  private static final Module PROTOCOLS =
      Modules.combine(
          new IMAPServerModule(),
          new LMTPServerModule(),
          new ManageSieveServerModule(),
          new POP3ServerModule(),
          new ProtocolHandlerModule(),
          new SMTPServerModule(),
          WEBADMIN);

  private static final Module JPA_SERVER_MODULE =
      Modules.combine(
          new ActiveMQQueueModule(),
          new DefaultProcessorsConfigurationProviderModule(),          
          new JPADataModule(),
          new JPAMailboxModule(),
          new MailboxModule(),
          new LuceneSearchMailboxModule(),
          new NoJwtModule(),
          new RawPostDequeueDecoratorModule(),
          new SieveJPARepositoryModules(),
          new DefaultEventModule(),
          new TaskManagerModule(),
          new MemoryDeadLetterModule(),
          new SpamAssassinModule());

  private static final Module JPA_MODULE_AGGREGATE = Modules.combine(JPA_SERVER_MODULE, PROTOCOLS);

  public static void main(String[] args) throws Exception {
    Configuration configuration = Configuration.builder().useWorkingDirectoryEnvProperty().build();

    LOGGER.info("Loading configuration {}", configuration.toString());
    GuiceJamesServer server = createServer(configuration).combineWith(new JMXServerModule());

    JamesServerMain.main(server);
  }

  static GuiceJamesServer createServer(Configuration configuration) {
    return GuiceJamesServer.forConfiguration(configuration).combineWith(JPA_MODULE_AGGREGATE);
  }
}
