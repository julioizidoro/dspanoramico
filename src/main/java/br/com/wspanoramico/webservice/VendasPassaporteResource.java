/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wspanoramico.webservice;

import br.com.wspanoramico.bean.PassaporteBean;
import br.com.wspanoramico.dao.BancoDao;
import br.com.wspanoramico.dao.ClienteDao;
import br.com.wspanoramico.dao.ContasReceberDao;
import br.com.wspanoramico.dao.ParametrosDao;
import br.com.wspanoramico.dao.PassaporteDao;
import br.com.wspanoramico.dao.RecebimentoDao;
import br.com.wspanoramico.dao.UsuarioDao;
import br.com.wspanoramico.managebean.SetarInformacoesPassaporteMB;
import br.com.wspanoramico.model.Banco;
import br.com.wspanoramico.model.Cliente;
import br.com.wspanoramico.model.Contasreceber;
import br.com.wspanoramico.model.Parametros;
import br.com.wspanoramico.model.Passaporte;
import br.com.wspanoramico.model.Recebimento;
import br.com.wspanoramico.model.Usuario;
import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Kamilla Rodrigues
 */
@Path("vendasPassaporte")
public class VendasPassaporteResource {

    @Context
    private UriInfo context;
    private Passaporte passaporte;
    private Cliente cliente;
    private Parametros parametros;
    @EJB
    private ParametrosDao parametrosDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private PassaporteDao passaporteDao;
    private Contasreceber contasreceber;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private RecebimentoDao recebimentoDao;

    /**
     * Creates a new instance of VendasPassaporteResource
     */
    public VendasPassaporteResource() {
    }

    /**
     * Retrieves representation of an instance of br.com.wspanoramico.webservice.VendasPassaporteResource
     * @param passaporteBean 
     * @return an instance of java.lang.String
     */
    @GET
    @Path("gerarPassaporte")
    @Produces(MediaType.APPLICATION_JSON)
    public Passaporte gerarPassaporte(PassaporteBean passaporteBean) {
        passaporteBean = new PassaporteBean();
        passaporte = salvarPassaporte(passaporteBean);
        return passaporte;
    }

    /**
     * PUT method for updating or creating an instance of VendasPassaporteResource
     * @param passaporteBean
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Passaporte salvarPassaporte(PassaporteBean passaporteBean) {
        passaporte = new Passaporte();
        parametros = parametrosDao.find(1);
        cliente = clienteDao.find(parametros.getCliente());
        SetarInformacoesPassaporteMB setarInformacoesPassaporteMB = new SetarInformacoesPassaporteMB();
        passaporte = setarInformacoesPassaporteMB.popularObjetoPassaporte(passaporteBean, cliente);
        passaporte = passaporteDao.update(passaporte);
        passaporte.setLocalizador("PPA" + passaporte.getIdpassaporte());
        passaporte = passaporteDao.update(passaporte);
        return passaporte;
    }
    
    
    /**
     * PUT method for updating or creating an instance of VendasPassaporteResource
     * @param idcontasReceber representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void recebimentoPassaporte(Integer idcontasReceber) {
        SetarInformacoesPassaporteMB setarInformacoesPassaporteMB = new SetarInformacoesPassaporteMB();
        Contasreceber contasreceber = contasReceberDao.find(idcontasReceber);
        parametros = parametrosDao.find(1);
        Banco banco = bancoDao.find(parametros.getBanco());
        Usuario usuario = usuarioDao.find(parametros.getUsuario());
        if (contasreceber.getSituacao().equalsIgnoreCase("PAGO")) {
            Recebimento recebimento = new Recebimento();
            recebimento = setarInformacoesPassaporteMB.receberValorPassaporte(contasreceber, usuario, banco);
            recebimentoDao.update(recebimento);
        }
    }
}
