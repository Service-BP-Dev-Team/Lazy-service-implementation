package cm.uds.fuchsia.gag.configuration.aspect;

import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.util.EncapsulatedValue;

@SuppressWarnings("all")
public class DataAspect extends Data {
  public DataAspect() {
  }

  public DataAspect(final Data d) {
    this.setParameter(d.getParameter());
    this.setValue(d.getValue());
  }

  public String print() {
    String stToPrint = "?";
    Object _value = this.getValue();
    EncapsulatedValue encVal = ((EncapsulatedValue) _value);
    boolean _isNull = encVal.isNull();
    boolean _not = (!_isNull);
    if (_not) {
      stToPrint = encVal.getValue().toString();
    }
    String _name = this.getParameter().getName();
    String _plus = (_name + "=");
    return (_plus + stToPrint);
  }
}
