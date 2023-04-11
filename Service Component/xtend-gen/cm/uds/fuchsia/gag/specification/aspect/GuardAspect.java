package cm.uds.fuchsia.gag.specification.aspect;

import cm.uds.fuchsia.gag.configuration.aspect.PendingLocalFunctionComputationAspect;
import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.FunctionDeclaration;
import cm.uds.fuchsia.gag.model.specification.Guard;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GuardAspect extends Guard {
  public GuardAspect() {
  }

  public GuardAspect(final Guard g) {
    this.setLocation(g.getLocation());
    this.setMethod(g.getMethod());
  }

  public boolean isApplicable(final Task t) {
    boolean result = false;
    FunctionDeclaration funcDec = new FunctionDeclaration();
    funcDec.setLocation(this.getLocation());
    funcDec.setMethod(this.getMethod());
    PendingLocalFunctionComputation funcCall = new PendingLocalFunctionComputation();
    funcCall.setFunctionDeclaration(funcDec);
    int _size = t.getInputs().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      funcCall.getActualParameters().add(t.getInputs().get((i).intValue()));
    }
    Object _execute = this.execute(funcCall);
    result = (((Boolean) _execute)).booleanValue();
    return result;
  }

  public Boolean isExecutable(final PendingLocalFunctionComputation computation) {
    return Boolean.valueOf(new PendingLocalFunctionComputationAspect(computation).isExecutable());
  }

  public Object execute(final PendingLocalFunctionComputation computation) {
    return new PendingLocalFunctionComputationAspect(computation).execute();
  }
}
