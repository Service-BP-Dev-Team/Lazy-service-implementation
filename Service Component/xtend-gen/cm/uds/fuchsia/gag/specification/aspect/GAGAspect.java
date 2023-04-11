package cm.uds.fuchsia.gag.specification.aspect;

import cm.uds.fuchsia.gag.configuration.aspect.ConfigurationAspect;
import cm.uds.fuchsia.gag.configuration.aspect.PendingLocalFunctionComputationAspect;
import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.DecompositionRule;
import cm.uds.fuchsia.gag.model.specification.Equation;
import cm.uds.fuchsia.gag.model.specification.Expression;
import cm.uds.fuchsia.gag.model.specification.FunctionExpression;
import cm.uds.fuchsia.gag.model.specification.GAG;
import cm.uds.fuchsia.gag.model.specification.Guard;
import cm.uds.fuchsia.gag.model.specification.IdExpression;
import cm.uds.fuchsia.gag.model.specification.RuntimeData;
import cm.uds.fuchsia.gag.model.specification.Service;
import cm.uds.fuchsia.gag.network.Component;
import cm.uds.fuchsia.gag.network.Subscription;
import cm.uds.fuchsia.gag.util.Console;
import cm.uds.fuchsia.gag.util.EncapsulatedValue;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GAGAspect extends GAG {
  private Component component;

  public Component getComponent() {
    return this.component;
  }

  public void setComponent(final Component comp) {
    this.component = comp;
  }

  public GAGAspect() {
  }

  public GAGAspect(final GAG g) {
    this.setName(g.getName());
    this.setConfiguration(g.getConfiguration());
    this.setServices(g.getServices());
  }

  public void initExecution() {
    RuntimeData _configuration = this.getConfiguration();
    boolean _tripleEquals = (_configuration == null);
    if (_tripleEquals) {
      Configuration _configuration_1 = new Configuration();
      this.setConfiguration(_configuration_1);
    }
  }

  public void run() {
    this.initExecution();
    RuntimeData _configuration = this.getConfiguration();
    final Configuration conf = ((Configuration) _configuration);
    this.chooseTheAxiom();
    ArrayList<Task> openTask = this.getOpenTask(conf.getRoot());
    while ((openTask.size() != 0)) {
      {
        Task task = this.chooseTask(openTask);
        DecompositionRule rule = this.chooseRule(task);
        boolean _notEquals = (!Objects.equal(rule, null));
        if (_notEquals) {
          this.applyRule(task, rule);
        }
        String _print = this.print(conf);
        String _plus = ("La configuration resultante est " + _print);
        Console.debug(_plus);
        openTask = this.getOpenTask(conf.getRoot());
      }
    }
    Console.debug("Ex�cution termin�e !");
  }

  public void runWithExternalOuputInterface(final OutputInterface OI) {
    this.initExecution();
    RuntimeData _configuration = this.getConfiguration();
    final Configuration conf = ((Configuration) _configuration);
    this.chooseTheAxiom();
    OI.update(this);
    ArrayList<Task> openTask = this.getOpenTask(conf.getRoot());
    while ((openTask.size() != 0)) {
      {
        Task task = this.chooseTask(openTask);
        DecompositionRule rule = this.chooseRule(task);
        boolean _notEquals = (!Objects.equal(rule, null));
        if (_notEquals) {
          this.applyRule(task, rule);
        }
        String _print = this.print(conf);
        String _plus = ("La configuration resultante est " + _print);
        Console.debug(_plus);
        OI.update(this);
        openTask = this.getOpenTask(conf.getRoot());
      }
    }
    Console.debug("Ex�cution termin�e !");
  }

  public ArrayList<Service> getAxioms() {
    final ArrayList<Service> services = this.getServices();
    final ArrayList<Service> axioms = new ArrayList<Service>();
    int _size = services.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Service element = services.get((i).intValue());
        Boolean _isAxiom = element.isAxiom();
        if ((_isAxiom).booleanValue()) {
          axioms.add(services.get((i).intValue()));
        }
      }
    }
    return axioms;
  }

  public void chooseTheAxiom() {
    final ArrayList<Service> services = this.getServices();
    final ArrayList<Service> axioms = this.getAxioms();
    Console.debug("Veuillez choisir le service axiome de d�marrage parmi les services suivants : ");
    String txtAf = "";
    int _size = axioms.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Service _get = axioms.get((i).intValue());
        final Service element = ((Service) _get);
        String _plus = (Integer.valueOf(((i).intValue() + 1)) + "- ");
        String _name = element.getName();
        String _plus_1 = (_plus + _name);
        Console.debug(_plus_1);
      }
    }
    final String choice = Console.readConsoleLine(txtAf);
    final int id = Integer.parseInt(choice);
    Service _get = axioms.get((id - 1));
    final Service serviceChoice = ((Service) _get);
    RuntimeData _configuration = this.getConfiguration();
    final Configuration conf = ((Configuration) _configuration);
    conf.setRoot(this.createRootTask(serviceChoice));
  }

  public Task createRootTask(final Service serviceChoice, final ArrayList<Object> inputParams) {
    Task root = new Task();
    root.setService(serviceChoice);
    int _size = root.getService().getInputParameters().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Data data = new Data();
        data.setParameter(root.getService().getInputParameters().get((i).intValue()));
        Object _get = inputParams.get((i).intValue());
        final EncapsulatedValue ecD = new EncapsulatedValue(_get);
        ecD.setContainerRef(data);
        data.setValue(ecD);
        root.getInputs().add(data);
      }
    }
    int _size_1 = root.getService().getOutputParameters().size();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        Data data = new Data();
        data.setParameter(root.getService().getOutputParameters().get((i_1).intValue()));
        final EncapsulatedValue ecD = new EncapsulatedValue();
        ecD.setContainerRef(data);
        data.setValue(ecD);
        root.getOutputs().add(data);
      }
    }
    return root;
  }

  public Task createRootTask(final Service serviceChoice) {
    Task root = new Task();
    root.setService(serviceChoice);
    Console.debug("Veuillez fournir les valeurs des entr�es de l\'axiome ");
    int _size = root.getService().getInputParameters().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Data data = new Data();
        data.setParameter(root.getService().getInputParameters().get((i).intValue()));
        String _name = data.getParameter().getName();
        String _plus = ("Veuillez entrer la valeur du param�tre " + _name);
        Console.debug(_plus);
        String _readConsoleLine = Console.readConsoleLine("");
        final EncapsulatedValue ecD = new EncapsulatedValue(_readConsoleLine);
        data.setValue(ecD);
        ecD.setContainerRef(data);
        root.getInputs().add(data);
      }
    }
    int _size_1 = root.getService().getOutputParameters().size();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        Data data = new Data();
        data.setParameter(root.getService().getOutputParameters().get((i_1).intValue()));
        final EncapsulatedValue ecD = new EncapsulatedValue();
        data.setValue(ecD);
        ecD.setContainerRef(data);
        root.getOutputs().add(data);
      }
    }
    return root;
  }

  public Task chooseTask(final ArrayList<Task> openTasks) {
    Console.debug("Veuillez choisir la t�che � traiter parmi les t�ches suivantes : ");
    String txtAf = "";
    int _size = openTasks.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Task element = openTasks.get((i).intValue());
        String _plus = (Integer.valueOf(((i).intValue() + 1)) + "- ");
        String _name = element.getService().getName();
        String _plus_1 = (_plus + _name);
        Console.debug(_plus_1);
      }
    }
    final String choice = Console.readConsoleLine(txtAf);
    final int id = Integer.parseInt(choice);
    return openTasks.get((id - 1));
  }

  public DecompositionRule chooseRule(final Task t) {
    ArrayList<DecompositionRule> applicableRules = new ArrayList<DecompositionRule>();
    int _size = t.getService().getRules().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final DecompositionRule element = t.getService().getRules().get((i).intValue());
        Guard guard = element.getGuard();
        if ((Objects.equal(guard, null) || this.isApplicable(guard, t))) {
          applicableRules.add(element);
        }
      }
    }
    int _size_1 = applicableRules.size();
    boolean _notEquals = (_size_1 != 0);
    if (_notEquals) {
      Console.debug("Veuillez choisir la r�gle de d�composition � appliquer parmi les r�gles suivantes : ");
      String txtAf = "";
      int _size_2 = applicableRules.size();
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_2, true);
      for (final Integer i_1 : _doubleDotLessThan_1) {
        String _plus = (Integer.valueOf(((i_1).intValue() + 1)) + "- ");
        String _name = applicableRules.get((i_1).intValue()).getName();
        String _plus_1 = (_plus + _name);
        Console.debug(_plus_1);
      }
      final String choice = Console.readConsoleLine(txtAf);
      final int id = Integer.parseInt(choice);
      final DecompositionRule rule = applicableRules.get((id - 1));
      return rule;
    } else {
      Console.debug("Aucune r�gle de d�composition n\'est actuellement applicable pour cette t�che ");
      Console.readConsoleLine("");
      return null;
    }
  }

  public ArrayList<DecompositionRule> getApplicablesRules(final Task t) {
    ArrayList<DecompositionRule> applicableRules = new ArrayList<DecompositionRule>();
    int _size = t.getService().getRules().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final DecompositionRule element = t.getService().getRules().get((i).intValue());
        Guard guard = element.getGuard();
        if ((Objects.equal(guard, null) || this.isApplicable(guard, t))) {
          applicableRules.add(element);
        }
      }
    }
    return applicableRules;
  }

  public ArrayList<Task> getOpenTask(final Task root) {
    final ArrayList<Task> openTasks = new ArrayList<Task>();
    boolean _isOpen = root.isOpen();
    if (_isOpen) {
      openTasks.add(root);
    } else {
      int _size = root.getSubTasks().size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final Task element = root.getSubTasks().get((i).intValue());
          final ArrayList<Task> subOpenTasks = this.getOpenTask(element);
          openTasks.addAll(subOpenTasks);
        }
      }
    }
    return openTasks;
  }

  public ArrayList<Task> getAllTasks(final Task root) {
    Task myroot = root;
    if ((root == null)) {
      RuntimeData _configuration = this.getConfiguration();
      myroot = ((Configuration) _configuration).getRoot();
    }
    final ArrayList<Task> allTasks = new ArrayList<Task>();
    allTasks.add(myroot);
    int _size = myroot.getSubTasks().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Task element = myroot.getSubTasks().get((i).intValue());
        final ArrayList<Task> subAllTasks = this.getAllTasks(element);
        allTasks.addAll(subAllTasks);
      }
    }
    return allTasks;
  }

  public void applyRule(final Task t, final DecompositionRule r) {
    t.setAppliedRule(r.getName());
    t.setOpen(false);
    int _size = r.getSubServices().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Service element = r.getSubServices().get((i).intValue());
        Task st = new Task();
        this.initTask(st, element);
        t.getSubTasks().add(st);
      }
    }
    RuntimeData _configuration = this.getConfiguration();
    Configuration conf = ((Configuration) _configuration);
    ArrayList<Task> context = new ArrayList<Task>();
    context.add(t);
    context.addAll(t.getSubTasks());
    int _size_1 = r.getSemantic().getEquations().size();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        Equation eq = r.getSemantic().getEquations().get((i_1).intValue());
        String _serviceName = eq.getLeftpart().getServiceName();
        String _parameterName = eq.getLeftpart().getParameterName();
        String[] ref1 = new String[] { _serviceName, _parameterName };
        Data data1 = this.findReference(ref1, context);
        Expression _rightpart = eq.getRightpart();
        if ((_rightpart instanceof IdExpression)) {
          Data data2 = ((Data) null);
          Expression _rightpart_1 = eq.getRightpart();
          final IdExpression rightPartIdExpression = ((IdExpression) _rightpart_1);
          String _serviceName_1 = rightPartIdExpression.getServiceName();
          String _parameterName_1 = rightPartIdExpression.getParameterName();
          final String[] ref2 = new String[] { _serviceName_1, _parameterName_1 };
          data2 = this.findReference(ref2, context);
          Object _value = data1.getValue();
          EncapsulatedValue ecData1 = ((EncapsulatedValue) _value);
          Object _value_1 = data2.getValue();
          ecData1.addReference(((EncapsulatedValue) _value_1));
        } else {
          Expression _rightpart_2 = eq.getRightpart();
          FunctionExpression func = ((FunctionExpression) _rightpart_2);
          Object _value_2 = data1.getValue();
          EncapsulatedValue ecData1_1 = ((EncapsulatedValue) _value_2);
          PendingLocalFunctionComputation runningFunction = new PendingLocalFunctionComputation();
          runningFunction.setDataToCompute(data1);
          runningFunction.setFunctionDeclaration(func.getFunction());
          int _size_2 = func.getIdExpressions().size();
          ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _size_2, true);
          for (final Integer k : _doubleDotLessThan_2) {
            {
              IdExpression elId = func.getIdExpressions().get((k).intValue());
              String _serviceName_2 = elId.getServiceName();
              String _parameterName_2 = elId.getParameterName();
              final String[] ref = new String[] { _serviceName_2, _parameterName_2 };
              Data data = this.findReference(ref, context);
              runningFunction.getActualParameters().add(data);
            }
          }
          conf.getPendingLocalComputations().add(runningFunction);
        }
      }
    }
    this.computeFunction(conf.getPendingLocalComputations());
    boolean _notEquals = (!Objects.equal(this.component, null));
    if (_notEquals) {
      int _size_2 = t.getSubTasks().size();
      ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _size_2, true);
      for (final Integer i_2 : _doubleDotLessThan_2) {
        {
          Task el = t.getSubTasks().get((i_2).intValue());
          Boolean _isRemote = el.getService().isRemote();
          if ((_isRemote).booleanValue()) {
            this.component.sendTask(el);
          }
        }
      }
    }
    this.notifyComponents();
  }

  public Data findReference(final String[] ref, final ArrayList<Task> tasks) {
    Data objectRef = ((Data) null);
    String serviceName = (ref[0]).toString().trim();
    String serviceParameter = (ref[1]).toString().trim();
    Console.debug(((serviceName + ".") + serviceParameter));
    int _size = tasks.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Task element = tasks.get((i).intValue());
        boolean _equals = element.getService().getName().equals(serviceName);
        if (_equals) {
          int _size_1 = element.getInputs().size();
          ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
          for (final Integer j : _doubleDotLessThan_1) {
            boolean _equals_1 = element.getInputs().get((j).intValue()).getParameter().getName().equals(serviceParameter);
            if (_equals_1) {
              objectRef = element.getInputs().get((j).intValue());
              Console.debug("i found");
            }
          }
          int _size_2 = element.getOutputs().size();
          ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _size_2, true);
          for (final Integer j_1 : _doubleDotLessThan_2) {
            boolean _equals_2 = element.getOutputs().get((j_1).intValue()).getParameter().getName().equals(serviceParameter);
            if (_equals_2) {
              objectRef = element.getOutputs().get((j_1).intValue());
              Console.debug("i found");
            }
          }
        }
      }
    }
    return objectRef;
  }

  public void computeFunction(final List<PendingLocalFunctionComputation> runningFunctions) {
    ArrayList<PendingLocalFunctionComputation> executableFunctions = new ArrayList<PendingLocalFunctionComputation>();
    int _size = runningFunctions.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        PendingLocalFunctionComputation func = runningFunctions.get((i).intValue());
        if (((this.isExecutable(func)).booleanValue() && (!func.isTerminated()))) {
          executableFunctions.add(func);
        }
      }
    }
    while ((executableFunctions.size() != 0)) {
      {
        int _size_1 = executableFunctions.size();
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
        for (final Integer i_1 : _doubleDotLessThan_1) {
          {
            PendingLocalFunctionComputation elFunc = executableFunctions.get((i_1).intValue());
            Object result = this.execute(elFunc);
            elFunc.setTerminated(true);
            Object _value = elFunc.getDataToCompute().getValue();
            EncapsulatedValue ecObj = ((EncapsulatedValue) _value);
            ecObj.setValue(result);
          }
        }
        ArrayList<PendingLocalFunctionComputation> _arrayList = new ArrayList<PendingLocalFunctionComputation>();
        executableFunctions = _arrayList;
        int _size_2 = runningFunctions.size();
        ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _size_2, true);
        for (final Integer i_2 : _doubleDotLessThan_2) {
          {
            PendingLocalFunctionComputation func = runningFunctions.get((i_2).intValue());
            if (((this.isExecutable(func)).booleanValue() && (!func.isTerminated()))) {
              executableFunctions.add(func);
            }
          }
        }
      }
    }
  }

  public void initTask(final Task t, final Service s) {
    t.setService(s);
    t.setOpen(true);
    int _size = s.getInputParameters().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Data data = new Data();
        data.setParameter(s.getInputParameters().get((i).intValue()));
        final EncapsulatedValue ecD = new EncapsulatedValue();
        data.setValue(ecD);
        ecD.setContainerRef(data);
        t.getInputs().add(data);
      }
    }
    int _size_1 = s.getOutputParameters().size();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _size_1, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        Data data = new Data();
        data.setParameter(s.getOutputParameters().get((i_1).intValue()));
        final EncapsulatedValue ecD = new EncapsulatedValue();
        data.setValue(ecD);
        ecD.setContainerRef(data);
        t.getOutputs().add(data);
      }
    }
  }

  public void notifyComponents() {
    boolean _notEquals = (!Objects.equal(this.component, null));
    if (_notEquals) {
      ArrayList<Subscription> array = new ArrayList<Subscription>();
      array.addAll(this.component.getSubscriptionList());
      int _size = array.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          Subscription sub = array.get((i).intValue());
          Data data = sub.getData();
          Object _value = data.getValue();
          EncapsulatedValue ecData = ((EncapsulatedValue) _value);
          boolean _isNull = ecData.isNull();
          boolean _not = (!_isNull);
          if (_not) {
            this.component.sendNotification(sub);
          }
        }
      }
    }
  }

  public boolean isApplicable(final Guard guard, final Task task) {
    GuardAspect guardAspect = new GuardAspect(guard);
    return guardAspect.isApplicable(task);
  }

  public Boolean isExecutable(final PendingLocalFunctionComputation computation) {
    return Boolean.valueOf(new PendingLocalFunctionComputationAspect(computation).isExecutable());
  }

  public Object execute(final PendingLocalFunctionComputation computation) {
    return new PendingLocalFunctionComputationAspect(computation).execute();
  }

  public String print(final Configuration configuration) {
    return new ConfigurationAspect(configuration).print();
  }
}
