package it.rra.fe;

public class Sys {
    public Sys() {
    }
/*
    public void IFRChanged() {
        this.getLog().writeln(LogBean.DEBUG,"Sys.IFRChanged: start.");
        if (analyzeDefinition!=null)
            if (analyzeDefinition.isShowing()) {
                analyzeDefinition.setIFR(analyzeDefinition.fract);
                analyzeDefinition.paint(analyzeDefinition.getGraphics());
            }
        if (editFunction!=null)
            if (editFunction.isShowing())
                editFunction.updateGUI();
        if (editPolygon!=null)
            if (editPolygon.isShowing())
                editPolygon.updateGUI();
        this.getLog().writeln(LogBean.DEBUG,"Sys.IFRChanged: end.");
    }

    public void nfChanged() {
        this.getLog().writeln(LogBean.DEBUG,"Sys.nfChanged: start.");
        if (analyzeDefinition!=null)
            if (analyzeDefinition.isShowing()) {
                analyzeDefinition.fun=editCanvas.nf;
                analyzeDefinition.repaint();
            }
        if (editFunction!=null)
            if (editFunction.isShowing())  {
                editFunction.setFunction(fe.currentIFR.getFunction(editCanvas.nf));
                editFunction.updateGUI();
            }
        this.getLog().writeln(LogBean.DEBUG,"Sys.nfChanged: end.");
    }

    private FractalEditor fe = null;
    private JRender render = null;
    private JEditPalette editPalette = null;
    private JEditFunction editFunction = null;
    private JEditPolygon editPolygon = null;
    //  private JAnalyzePicture analyzePicture = null;
    private AnalyzePicture analyzePicture = null;
    private AnalyzeDefinition analyzeDefinition = null;
    private DrawDialog drawDialog = null;
    private EditComponent editComponent = null;

    boolean fromApplet = false;

    private LogBean log;

    // Getters & Setters
    public FractalEditor getFractalEditor() { return fe; }
    public void setFractalEditor(FractalEditor newValue) { fe=newValue;   }
    public JRender getRender() { return render; }
    public void setRender(JRender newValue) { render=newValue;   }
    public JEditPalette getEditPalette() { return editPalette; }
    public void setEditPalette(JEditPalette newValue) { editPalette=newValue;   }
    public JEditFunction getEditFunction() { return editFunction; }
    public void setEditFunction(JEditFunction newValue) { editFunction=newValue;   }
    public JEditPolygon getEditPolygon() { return editPolygon; }
    public void setEditPolygon(JEditPolygon newValue) { editPolygon=newValue;   }
    public AnalyzePicture getAnalyzePicture() { return analyzePicture; }
    public void setAnalyzePicture(AnalyzePicture newValue) { analyzePicture=newValue;   }
    public AnalyzeDefinition getAnalyzeDefinition() { return analyzeDefinition; }
    public void setAnalyzeDefinition(AnalyzeDefinition newValue) { analyzeDefinition=newValue;   }
    public DrawDialog getDrawDialog() { return drawDialog; }
    public void setDrawDialog(DrawDialog newValue) { drawDialog=newValue;   }
    public EditCanvas getEditCanvas() { return editCanvas; }
    public void setEditCanvas(EditCanvas newValue) { editCanvas=newValue;   }
    public EditComponent getEditComponent() { return editComponent; }
    public void setEditComponent(EditComponent newValue) { editComponent=newValue;   }
    public boolean isFromApplet() { return fromApplet; }
    public void setFromApplet(boolean newValue) { fromApplet=newValue;   }
    public LogBean getLog() {
        return ( log==null ? log = LogBean.getInstance() : log );
    }
    public void setLog(LogBean log) {
        this.log = log;
    }
    */
}
