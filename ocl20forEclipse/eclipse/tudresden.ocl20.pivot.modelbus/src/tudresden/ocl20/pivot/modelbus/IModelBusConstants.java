package tudresden.ocl20.pivot.modelbus;


/**
 * A collection of constants used by the Model Bus plugin.
 * 
 * @author Matthias Braeuer
 * @version 1.0 03.04.2007
 */
public interface IModelBusConstants {

  /**
   * The name of the extension point used for defining metamodel extensions;
   */
  String EXT_METAMODELS = "metamodels"; //$NON-NLS-1$

  /**
   * The name of the tag used to define a metamodel.
   */
  String TAG_METAMODEL = "metamodel"; //$NON-NLS-1$
  
  /**
   * The name of the attribute that contains the class name of the {@link IModelProvider} for a
   * metamodel.
   */
  String ATT_MODELPROVIDER = "modelProvider"; //$NON-NLS-1$

  /**
   * The name of the attribute that contains the path to an icon representing a metamodel.
   */
  String ATT_ICON = "icon"; //$NON-NLS-1$

  /**
   * The name of the attribute containing the name of the metamodel. 
   */
  String ATT_NAME = "name"; //$NON-NLS-1$
  
  /**
   * The ID of the class tudresden.ocl20.pivot.modelbus.ui.views.ModelsView.
   */
  public static final String MODELS_VIEW_ID = "tudresden.ocl20.pivot.modelbus.ui.views.models"; //$NON-NLS-1$

  /**
   * The ID of the class tudresden.ocl20.pivot.modelbus.ui.views.ModelInstancesView.
   */
  public static final String MODEL_INSTANCES_VIEW_ID = "tudresden.ocl20.pivot.modelbus.ui.internal.views.ModelInstancesView"; //$NON-NLS-1$

}