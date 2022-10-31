package cm.uds.fuchsia.gag.configuration.aspect;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Task;

@SuppressWarnings("all")
public class ConfigurationAspect extends Configuration {
  public ConfigurationAspect() {
  }

  public ConfigurationAspect(final Configuration conf) {
    this.setPendingLocalComputations(conf.getPendingLocalComputations());
    this.setRoot(conf.getRoot());
  }

  public String print() {
    return this.print(this.getRoot());
  }

  public String print(final Task task) {
    return new TaskAspect(task).print();
  }
}
