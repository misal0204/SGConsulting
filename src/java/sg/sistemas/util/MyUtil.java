/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.util;

/**
 *
 * @author Misael
 */
public class MyUtil {

    private static String servidor="localhost";
    private static String puerto="8084";
    private static String project="SGSistemas";
    
    public static String baseUrl() {
        return "http://"+servidor+":"+puerto+"/"+project+"/";
    }

    public static String baseUrlIndex() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/menuprincipal.xhtml";
    }

    public static String basePathLogin() {
        return "/SGSolutions/faces/";
    }

    public static String baseUrlBootstrap() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/bootstrap/";
    }

    public static String baseUrlBuild() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/build/";
    }

    public static String baseUrlDist() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/dist/";
    }

    public static String baseUrlDocumentation() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/documentation/";
    }

    public static String baseUrlPages() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/pages/";
    }

    public static String baseUrlPlugins() {
        return "http://"+servidor+":"+puerto+"/"+project+"/recursos/bootstrap/plugins/";
    }

    public static String baseUrlJSBootstrap() {
        return "recursos/bootstrap/bootstrap/";
    }

    public static String baseUrlJSPlugins() {
        return "recursos/bootstrap/plugins/";
    }

    public static String baseUrlPagesNC() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/NoConformidad/";
    }

    public static String baseUrlPagesAM() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/AccionMejora/";
    }

    public static String baseUrlPagesAudit() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Auditorias/";
    }

    public static String baseUrlPagesClient() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Clientes/";
    }

    public static String baseUrlPagesConfig() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Configuraciones/";
    }
    
    public static String baseUrlPagesDatosBasicos() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/DatosBasicos/";
    }

    public static String baseUrlPagesDocumentacion() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Documentacion/";
    }

    public static String baseUrlPagesIndicadores() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Indicadores/";
    }

    public static String baseUrlPagesIndicadoresGestion() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/IndicadoresGestion/";
    }

    public static String baseUrlPagesPlanesObjetivos() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/PlanesObjetivos/";
    }

    public static String baseUrlPagesProveedores() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/Proveedores/";
    }

    public static String baseUrlPagesRevisionDireccion() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/RevisionDireccion/";
    }

    public static String baseUrlPagesRiesgosOportunidades() {
        return "http://"+servidor+":"+puerto+"/"+project+"/faces/recursos/bootstrap/pages/RiesgosOportunidades/";
    }
}
