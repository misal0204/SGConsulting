/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entity;

import java.io.Serializable;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

/**
 *
 * @author Misael
 */
public class SgPrivilegios implements Serializable {

    private int idprogram;
    private int idmenu;

    public SgPrivilegios() {
    }

    public SgPrivilegios(int idprogram, int idmenu) {
        this.idprogram = idprogram;
        this.idmenu = idmenu;
    }

    public int getIdprogram() {
        return idprogram;
    }

    public void setIdprogram(int idprogram) {
        this.idprogram = idprogram;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

}
