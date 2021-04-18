package App;

import static spark.Spark.*;

import Service.XService;

public class Aplicacao {
	
	private static XService xService = new XService();
	
    public static void main(String[] args) {
        port(6789);

        post("/x", (request, response) -> xService.add(request, response));

        get("/x/:id", (request, response) -> xService.get(request, response));

        get("/x/update/:id", (request, response) -> xService.update(request, response));

        get("/x/delete/:id", (request, response) -> xService.remove(request, response));

        get("/x", (request, response) -> xService.getAll(request, response));
               
    }
}