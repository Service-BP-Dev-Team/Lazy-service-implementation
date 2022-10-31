package cm.uds.fuchsia.gag.configuration.aspect;

import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.Task;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class TaskAspect extends Task {
  public TaskAspect() {
  }

  public TaskAspect(final Task t) {
    this.setAppliedRule(t.getAppliedRule());
    this.setInputs(t.getInputs());
    this.setOutputs(t.getOutputs());
    this.setSubTasks(t.getSubTasks());
    this.setService(t.getService());
    this.setOpen(t.isOpen());
  }

  public String print() {
    String conf = "";
    String _conf = conf;
    String _name = this.getService().getName();
    String _plus = (_name + "(");
    conf = (_conf + _plus);
    boolean _isOpen = this.isOpen();
    boolean _not = (!_isOpen);
    if (_not) {
      String _conf_1 = conf;
      String _appliedRule = this.getAppliedRule();
      String _plus_1 = (" appliedRule=" + _appliedRule);
      conf = (_conf_1 + _plus_1);
      String _conf_2 = conf;
      conf = (_conf_2 + ")[");
      int i = 1;
      ArrayList<Task> _subTasks = this.getSubTasks();
      for (final Task t : _subTasks) {
        {
          String _conf_3 = conf;
          String _print = this.print(t);
          conf = (_conf_3 + _print);
          int _size = this.getSubTasks().size();
          boolean _lessThan = (i < _size);
          if (_lessThan) {
            String _conf_4 = conf;
            conf = (_conf_4 + ", ");
          }
        }
      }
      String _conf_3 = conf;
      conf = (_conf_3 + "]");
    } else {
      int _size = this.getInputs().size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i_1 : _doubleDotLessThan) {
        {
          final Data el = this.getInputs().get((i_1).intValue());
          String _conf_4 = conf;
          String _print = this.print(el);
          conf = (_conf_4 + _print);
          int _size_1 = this.getInputs().size();
          int _minus = (_size_1 - 1);
          boolean _lessThan = ((i_1).intValue() < _minus);
          if (_lessThan) {
            String _conf_5 = conf;
            conf = (_conf_5 + ", ");
          }
        }
      }
      String _conf_4 = conf;
      conf = (_conf_4 + ")[");
      int _size_1 = this.getOutputs().size();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
      for (final Integer i_2 : _doubleDotLessThan_1) {
        {
          final Data el = this.getOutputs().get((i_2).intValue());
          String _conf_5 = conf;
          String _print = this.print(el);
          conf = (_conf_5 + _print);
          int _size_2 = this.getOutputs().size();
          int _minus = (_size_2 - 1);
          boolean _lessThan = ((i_2).intValue() < _minus);
          if (_lessThan) {
            String _conf_6 = conf;
            conf = (_conf_6 + ", ");
          }
        }
      }
      String _conf_5 = conf;
      conf = (_conf_5 + "]");
    }
    return conf;
  }

  public String prettyPrint() {
    String result = "(";
    int _size = this.getOutputs().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        String _result = result;
        String _fullDisplayName = this.getOutputs().get((i).intValue()).getFullDisplayName();
        result = (_result + _fullDisplayName);
        int _size_1 = this.getOutputs().size();
        int _minus = (_size_1 - 1);
        boolean _notEquals = ((i).intValue() != _minus);
        if (_notEquals) {
          String _result_1 = result;
          result = (_result_1 + ",");
        }
      }
    }
    String _result = result;
    String _name = this.getService().getName();
    String _plus = (") = " + _name);
    String _plus_1 = (_plus + "(");
    result = (_result + _plus_1);
    int _size_1 = this.getInputs().size();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        String _result_1 = result;
        String _fullDisplayName = this.getInputs().get((i_1).intValue()).getFullDisplayName();
        result = (_result_1 + _fullDisplayName);
        int _size_2 = this.getInputs().size();
        int _minus = (_size_2 - 1);
        boolean _notEquals = ((i_1).intValue() != _minus);
        if (_notEquals) {
          String _result_2 = result;
          result = (_result_2 + ",");
        }
      }
    }
    String _result_1 = result;
    result = (_result_1 + ")");
    return result;
  }

  public String print(final Task task) {
    return new TaskAspect(task).print();
  }

  public String print(final Data data) {
    return new DataAspect(data).print();
  }
}
