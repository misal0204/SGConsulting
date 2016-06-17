/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.solution.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import sg.solution.util.MyUtil;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "appBeans")
@ApplicationScoped
public class AppBeans implements Serializable {

    /**
     * Creates a new instance of AppBeans
     */
    public AppBeans() {
    }

    public String baseUrl() {
        return MyUtil.baseUrl();
    }

    public String basePathLogin() {
        return MyUtil.basePathLogin();
    }

    public String baseUrlBootstrap() {
        return MyUtil.baseUrlBootstrap();
    }

    public String baseUrlBuild() {
        return MyUtil.baseUrlBuild();
    }

    public String baseUrlDist() {
        return MyUtil.baseUrlDist();
    }

    public String baseUrlDocumentation() {
        return MyUtil.baseUrlDocumentation();
    }

    public String baseUrlPages() {
        return MyUtil.baseUrlPages();
    }

    public String baseUrlPlugins() {
        return MyUtil.baseUrlPlugins();
    }

    public String baseUrlJSBootStrap() {
        return MyUtil.baseUrlJSBootstrap();
    }

    public String baseUrlJSPlugins() {
        return MyUtil.baseUrlJSPlugins();
    }

    public String baseUrlPagesNC() {
        return MyUtil.baseUrlPagesNC();
    }

    public String baseUrlPagesAM() {
        return MyUtil.baseUrlPagesAM();
    }
}
