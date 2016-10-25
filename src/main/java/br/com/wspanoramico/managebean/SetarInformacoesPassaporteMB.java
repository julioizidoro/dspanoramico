/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wspanoramico.managebean;

import br.com.wspanoramico.bean.PassaporteBean;
import br.com.wspanoramico.model.Banco;
import br.com.wspanoramico.model.Cliente;
import br.com.wspanoramico.model.Contasreceber;
import br.com.wspanoramico.model.Passaporte;
import br.com.wspanoramico.model.Recebimento;
import br.com.wspanoramico.model.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class SetarInformacoesPassaporteMB implements Serializable{
    
    private Cliente cliente;
    
    
    @PostConstruct
    public void init(){
        
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    public Passaporte popularObjetoPassaporte(PassaporteBean passaporteBean, Cliente cliente){
        Passaporte passaporte = new Passaporte();
        passaporte.setCliente(cliente);
        passaporte.setDatacompra(passaporteBean.getData());
        passaporte.setFormapagamento(passaporteBean.getFormaPagamento());
        passaporte.setValorpago(passaporteBean.getValor());
        return passaporte;
    }
    
    
    public Recebimento receberValorPassaporte(Contasreceber contasreceber, Usuario usuario, Banco banco) {
        Recebimento recebimento = new Recebimento();
        recebimento.setContasreceber(contasreceber);
        recebimento.setDatarecebimento(new Date());
        recebimento.setDesagio(0.0f);
        recebimento.setJuros(0.0f);
        recebimento.setValorrecebido(contasreceber.getValorconta());
        recebimento.setBanco(banco);
        recebimento.setUsuario(usuario);
        return recebimento;
    }
    
}
