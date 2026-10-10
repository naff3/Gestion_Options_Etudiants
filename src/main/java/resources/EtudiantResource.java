package resources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import utilities.Metiers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    private final EtudiantBusiness etudiantBusiness = Metiers.ETUDIANTS;
    private final OptionBusiness optionBusiness = Metiers.OPTIONS;

    // 1) Création d'un étudiant (404 si l'option n'existe pas)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant.getOption() == null || !etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 2) Liste de tous les étudiants
    @GET
    public List<Etudiant> getAllEtudiants() {
        return etudiantBusiness.getAllEtudiants();
    }

    // 6) Étudiants d'une option, en XML (404 si l'option n'existe pas)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new EtudiantList(etudiantBusiness.getEtudiantsByOption(option))).build();
    }

    // 3) Un étudiant par son identifiant
    @GET
    @Path("{identifiant}")
    public Response getEtudiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 5) Modification d'un étudiant
    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiant.getOption() != null) {
            Option option = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
            if (option == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            etudiant.setOption(option);
        }
        if (!etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 4) Suppression d'un étudiant
    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (!etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
