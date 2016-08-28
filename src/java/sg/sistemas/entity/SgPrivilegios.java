/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sg_privilegios")
@NamedQueries({
    @NamedQuery(name = "SgPrivilegios.findByUser", query = "SELECT s FROM SgPrivilegios s WHERE s.cod_usuario = :cod_usuario")})

public class SgPrivilegios implements Serializable {

    @Id
    private String cod_usuario;
    private double idprogram;
    private double idmenu;

    public SgPrivilegios() {
    }

    public SgPrivilegios(String cod_usuario, double idprogram, double idmenu) {
        this.cod_usuario = cod_usuario;
        this.idprogram = idprogram;
        this.idmenu = idmenu;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public double getIdprogram() {
        return idprogram;
    }

    public void setIdprogram(double idprogram) {
        this.idprogram = idprogram;
    }

    public double getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(double idmenu) {
        this.idmenu = idmenu;
    }

    @Override
    public String toString() {
        return "Sgprivilegios{" + "cod_usuario=" + cod_usuario + ", idprogram=" + idprogram + ", idmenu=" + idmenu + '}';
    }
}
