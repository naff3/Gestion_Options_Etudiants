package resources;

import entities.Option;
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

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
public class OptionResource {

    private final OptionBusiness optionBusiness = Metiers.OPTIONS;

    // 1) Création d'une option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        optionBusiness.addOption(option);
        return Response.ok(option).build();
    }

    // 2) Liste de toutes les options  +  3) liste des options d'un domaine (?domaine=...)
    @GET
    public List<Option> getOptions(@QueryParam("domaine") String domaine) {
        if (domaine != null && !domaine.isEmpty()) {
            return optionBusiness.getOptionsByDomaine(domaine);
        }
        return optionBusiness.getListeOptions();
    }

    // 6) Une option par son code
    @GET
    @Path("{code}")
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // 5) Modification d'une option
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (!optionBusiness.updateOption(code, option)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // 4) Suppression d'une option
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (!optionBusiness.deleteOption(code)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
