package cm.uds.fuchsia.gag.specification.aspect;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.GAG;
import cm.uds.fuchsia.gag.model.specification.RuntimeData;
import cm.uds.fuchsia.gag.ui.component.ChooseRuleDialog;
import cm.uds.fuchsia.gag.ui.component.ComponentIHM;
import cm.uds.fuchsia.gag.ui.component.CustomGraph;
import cm.uds.fuchsia.gag.ui.component.CustomGraphComponent;
import cm.uds.fuchsia.gag.ui.component.DefineInpuDialog;
import cm.uds.fuchsia.gag.util.Console;
import com.google.common.base.Objects;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraphView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JViewport;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GAGGraphAspect extends GAGAspect implements OutputInterface, MouseListener {
  private CustomGraphComponent graphComponent;

  private ComponentIHM windowContainer;

  private CustomGraph graph;

  private boolean pageView;

  private JPanel panel;

  private mxHierarchicalLayout layoutForParent;

  private Hashtable<Object, Object> mapDataGraph;

  private Hashtable<Object, Object> mapGraphData;

  public static String style = (mxConstants.STYLE_FILLCOLOR + "=#ffffff");

  public static String styleService = (((((GAGGraphAspect.style + ";") + mxConstants.STYLE_SHAPE) + "=") + mxConstants.SHAPE_ELLIPSE) + ";");

  public static String styleIN = "";

  public static String styleArrowIN = (((((((mxConstants.STYLE_ENDARROW + "=") + mxConstants.ARROW_OVAL) + ";") + mxConstants.STYLE_STARTARROW) + "=") + mxConstants.ARROW_CLASSIC) + ";");

  public static String styleArrowOut = (((((((mxConstants.STYLE_ENDARROW + "=") + mxConstants.ARROW_OPEN) + ";") + mxConstants.STYLE_STARTARROW) + "=") + mxConstants.ARROW_CLASSIC) + ";");

  public static String styleArrowReturn = ((((((((((mxConstants.STYLE_EDGE + "=") + mxConstants.EDGESTYLE_ENTITY_RELATION) + ";") + mxConstants.STYLE_DASHED) + "=1;") + mxConstants.STYLE_DASH_PATTERN) + "=3;") + mxConstants.STYLE_ENDARROW) + "=") + mxConstants.ARROW_OPEN);

  public static String styleWaitCollor = ((mxConstants.STYLE_STROKECOLOR + "=") + "#fb0000;");

  public static String stylePendingCollor = ((mxConstants.STYLE_STROKECOLOR + "=") + "#e8c218;");

  public static String styleFinishCollor = ((mxConstants.STYLE_STROKECOLOR + "=") + "#18e81c;");

  public static String styleArrowSun = (((((((mxConstants.STYLE_ENDARROW + "=") + mxConstants.ARROW_OPEN) + ";") + mxConstants.STYLE_STARTARROW) + "=") + mxConstants.ARROW_OVAL) + ";");

  public static String styleServiceOpen = ((((((mxConstants.STYLE_FILLCOLOR + "=#c0bfc6") + ";") + mxConstants.STYLE_SHAPE) + "=") + mxConstants.SHAPE_ELLIPSE) + ";");

  public static String styleServiceRemote = ((((((((((((((((mxConstants.STYLE_FILLCOLOR + "=#ffffff") + ";") + mxConstants.STYLE_SHAPE) + "=") + mxConstants.SHAPE_ELLIPSE) + ";") + mxConstants.STYLE_DASHED) + "=") + "1") + ";") + mxConstants.STYLE_DASH_PATTERN) + "=10") + ";") + mxConstants.STYLE_STROKECOLOR) + "=#c0bfc6") + ";");

  public static String styleServiceInput = (((((((((mxConstants.STYLE_FILLCOLOR + "=#ffffff") + ";") + mxConstants.STYLE_SHAPE) + "=") + mxConstants.SHAPE_RECTANGLE) + ";") + mxConstants.STYLE_STROKECOLOR) + "=red") + ";");

  public static String styleServiceOutput = (((((((((mxConstants.STYLE_FILLCOLOR + "=#ffffff") + ";") + mxConstants.STYLE_SHAPE) + "=") + mxConstants.SHAPE_RECTANGLE) + ";") + mxConstants.STYLE_STROKECOLOR) + "=green") + ";");

  private Object parent;

  private ChooseRuleDialog dialog;

  public GAGGraphAspect(final GAG g) {
    super(g);
    Hashtable<Object, Object> _hashtable = new Hashtable<Object, Object>();
    this.mapDataGraph = _hashtable;
    Hashtable<Object, Object> _hashtable_1 = new Hashtable<Object, Object>();
    this.mapGraphData = _hashtable_1;
  }

  public GAGGraphAspect() {
    Hashtable<Object, Object> _hashtable = new Hashtable<Object, Object>();
    this.mapDataGraph = _hashtable;
    Hashtable<Object, Object> _hashtable_1 = new Hashtable<Object, Object>();
    this.mapGraphData = _hashtable_1;
  }

  public CustomGraphComponent getGraphComponent() {
    return this.graphComponent;
  }

  public void setGraphComponent(final CustomGraphComponent graphComponent) {
    this.graphComponent = graphComponent;
  }

  public boolean isPageView() {
    return this.pageView;
  }

  public void setPageView(final boolean pageView) {
    this.pageView = pageView;
  }

  public JPanel getPanel() {
    return this.panel;
  }

  public void setPanel(final JPanel panel) {
    this.panel = panel;
  }

  public CustomGraph getGraph() {
    return this.graph;
  }

  public void setGraph(final CustomGraph graph) {
    this.graph = graph;
  }

  public void dispose(final JPanel jpanel) {
    this.panel = jpanel;
    this.dispose();
  }

  public void dispose() {
    this.panel.removeAll();
    BorderLayout _borderLayout = new BorderLayout();
    this.panel.setLayout(_borderLayout);
    mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
    mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";
    CustomGraph _customGraph = new CustomGraph();
    this.graph = _customGraph;
    this.graph.setGAG(this);
    this.parent = this.graph.getDefaultParent();
    this.graph.getModel().beginUpdate();
    mxHierarchicalLayout _mxHierarchicalLayout = new mxHierarchicalLayout(this.graph);
    this.layoutForParent = _mxHierarchicalLayout;
    mxFastOrganicLayout layout = new mxFastOrganicLayout(this.graph);
    layout.setForceConstant(40);
    layout.setDisableEdgeStyle(false);
    try {
      RuntimeData _configuration = this.getConfiguration();
      boolean _notEquals = (!Objects.equal(_configuration, null));
      if (_notEquals) {
        RuntimeData _configuration_1 = this.getConfiguration();
        Task root = ((Configuration) _configuration_1).getRoot();
        this.draw(root, null);
      }
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    } finally {
      this.layoutForParent.setParentBorder(5);
      this.layoutForParent.setParallelEdgeSpacing(50);
      this.layoutForParent.setIntraCellSpacing(150);
      this.layoutForParent.execute(this.parent);
      this.graph.getModel().endUpdate();
    }
    RuntimeData _configuration = this.getConfiguration();
    boolean _notEquals = (!Objects.equal(_configuration, null));
    if (_notEquals) {
      final ArrayList<Task> servicesOpen = this.getAllTasks(null);
      int _size = servicesOpen.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          this.drawInputs(servicesOpen.get((i).intValue()));
          this.drawOutputs(servicesOpen.get((i).intValue()));
        }
      }
    }
    CustomGraphComponent _customGraphComponent = new CustomGraphComponent(this.graph);
    this.graphComponent = _customGraphComponent;
    this.graphComponent.setBackground(Color.WHITE);
    this.graphComponent.zoomAndCenter();
    this.graphComponent.zoom(1.2);
    Dimension size = this.graphComponent.getViewport().getViewSize();
    Rectangle bounds = this.graphComponent.getViewport().getViewRect();
    int x = ((size.width - bounds.width) / 4);
    int y = (0 / 2);
    JViewport _viewport = this.graphComponent.getViewport();
    Point _point = new Point(50, y);
    _viewport.setViewPosition(_point);
    final int graphwidth = this.graphComponent.getPreferredSize().width;
    mxGraphView _view = this.graph.getView();
    double _width = this.graph.getGraphBounds().getWidth();
    double _divide = (_width / 2);
    double _minus = ((graphwidth / 2) - _divide);
    double _plus = (_minus + 200);
    mxPoint _mxPoint = new mxPoint(_plus, 50);
    _view.setTranslate(_mxPoint);
    this.graphComponent.setBorder(null);
    this.graphComponent.setAutoScroll(false);
    this.graphComponent.refresh();
    this.graphComponent.setLayoutStructure(this);
    this.graphComponent.setGAG(this);
    this.graphComponent.getGraphControl().addMouseListener(this);
    this.panel.add(this.graphComponent);
    this.panel.updateUI();
    this.panel.validate();
    this.panel.repaint();
  }

  public void drawInputs(final Task task) {
    final mxRectangle rec = this.graph.getCellBounds(this.mapDataGraph.get(task));
    int space = 70;
    int _size = task.getInputs().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Data data = task.getInputs().get((i).intValue());
        int _length = data.getDisplayName().length();
        int actualSpace = (11 * _length);
        int _space = space;
        space = (_space + 10);
        double _centerX = rec.getCenterX();
        double _minus = (_centerX - space);
        double _centerY = rec.getCenterY();
        double _plus = (_centerY + 30);
        final Object v = this.graph.insertVertex(this.parent, null, data, _minus, _plus, actualSpace, 22, GAGGraphAspect.styleServiceInput);
        this.mapDataGraph.put(data, v);
        this.mapGraphData.put(v, data);
        int _space_1 = space;
        space = (_space_1 + actualSpace);
      }
    }
  }

  public void drawOutputs(final Task task) {
    final mxRectangle rec = this.graph.getCellBounds(this.mapDataGraph.get(task));
    int space = 20;
    int _size = task.getOutputs().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Data data = task.getOutputs().get((i).intValue());
        int _length = data.getDisplayName().length();
        int actualSpace = (11 * _length);
        int _space = space;
        space = (_space + 10);
        double _centerX = rec.getCenterX();
        double _plus = (_centerX + space);
        double _centerY = rec.getCenterY();
        double _plus_1 = (_centerY + 30);
        final Object v = this.graph.insertVertex(this.parent, null, data, _plus, _plus_1, actualSpace, 22, GAGGraphAspect.styleServiceOutput);
        this.mapDataGraph.put(data, v);
        this.mapGraphData.put(v, data);
        int _space_1 = space;
        space = (_space_1 + actualSpace);
      }
    }
  }

  public void draw(final Task task, final Task parent) {
    String myStyleService = "";
    boolean _isOpen = task.isOpen();
    if (_isOpen) {
      myStyleService = GAGGraphAspect.styleServiceOpen;
    } else {
      myStyleService = GAGGraphAspect.styleService;
    }
    Boolean _isRemote = task.getService().isRemote();
    if ((_isRemote).booleanValue()) {
      myStyleService = GAGGraphAspect.styleServiceRemote;
      Console.debug("the service is remote");
    }
    int _length = task.getService().getName().length();
    int _multiply = (_length * 20);
    int _plus = (_multiply + 20);
    Object v = this.graph.insertVertex(this.parent, null, task, 400, 10, _plus, 50, myStyleService);
    this.mapDataGraph.put(task, v);
    this.mapGraphData.put(v, task);
    boolean _notEquals = (!Objects.equal(parent, null));
    if (_notEquals) {
      this.graph.insertEdge(this.parent, null, "", this.mapDataGraph.get(parent), v, GAGGraphAspect.styleArrowSun);
    }
    int _size = task.getSubTasks().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        Task element = task.getSubTasks().get((i).intValue());
        this.draw(element, task);
      }
    }
    boolean _isOpen_1 = task.isOpen();
    if (_isOpen_1) {
    }
  }

  public void drawInput(final Data data, final Task t) {
    final mxRectangle rec = this.graph.getCellBounds(this.mapDataGraph.get(t));
    double _centerX = rec.getCenterX();
    double _minus = (_centerX - 10);
    double _centerY = rec.getCenterY();
    double _plus = (_centerY + 20);
    int _length = data.getParameter().getName().length();
    int _multiply = (_length * 20);
    int _plus_1 = (_multiply + 20);
    final Object v = this.graph.insertVertex(this.parent, null, data, _minus, _plus, _plus_1, 20, (((GAGGraphAspect.styleIN + mxConstants.STYLE_STROKECOLOR) + "=") + "#ffffff;"));
    this.mapDataGraph.put(data, v);
    this.mapGraphData.put(v, data);
  }

  public void proceedArtefact() {
  }

  public String getTermColor() {
    return "";
  }

  public ComponentIHM getWindowContainer() {
    return this.windowContainer;
  }

  public void setWindowContainer(final ComponentIHM ihm) {
    this.windowContainer = ihm;
  }

  public void update(final GAG g) {
    this.setConfiguration(g.getConfiguration());
    this.dispose();
    this.windowContainer.updateUI();
  }

  public void mouseClicked(final MouseEvent e) {
  }

  public void mouseEntered(final MouseEvent e) {
  }

  public void mouseExited(final MouseEvent e) {
  }

  public void mousePressed(final MouseEvent e) {
  }

  public void mouseReleased(final MouseEvent e) {
    DefineInpuDialog inputDialog = ((DefineInpuDialog) null);
    Object cell = this.graphComponent.getCellAt(e.getX(), e.getY());
    if ((cell != null)) {
      Object data = this.mapGraphData.get(cell);
      boolean _notEquals = (!Objects.equal(data, null));
      if (_notEquals) {
        if ((data instanceof Task)) {
          final Task task = ((Task) data);
          Boolean _isRemote = task.getService().isRemote();
          boolean _not = (!(_isRemote).booleanValue());
          if (_not) {
            Console.debug("clicking on task");
            int _x = e.getX();
            String _plus = ("(X:" + Integer.valueOf(_x));
            String _plus_1 = (_plus + ", Y:");
            int _y = e.getY();
            String _plus_2 = (_plus_1 + Integer.valueOf(_y));
            String _plus_3 = (_plus_2 + ")");
            Console.debug(_plus_3);
            if ((this.dialog != null)) {
              this.dialog.dispose();
            }
            ChooseRuleDialog _chooseRuleDialog = new ChooseRuleDialog();
            this.dialog = _chooseRuleDialog;
            final Point location = e.getLocationOnScreen();
            this.dialog.setBounds(location.x, location.y, 200, 200);
            this.dialog.setIconImage(null);
            this.dialog.setGraph(this);
            this.dialog.setRulesForTask(task);
            this.dialog.setVisible(true);
          } else {
            DefineInpuDialog _defineInpuDialog = new DefineInpuDialog();
            inputDialog = _defineInpuDialog;
            inputDialog.setGagAspect(this);
            inputDialog.setTask(task);
            inputDialog.setVisible(true);
          }
        } else {
          if ((this.dialog != null)) {
            this.dialog.dispose();
          }
        }
      } else {
        if ((this.dialog != null)) {
          this.dialog.dispose();
        }
      }
    } else {
      if ((this.dialog != null)) {
        this.dialog.dispose();
      }
    }
  }
}
