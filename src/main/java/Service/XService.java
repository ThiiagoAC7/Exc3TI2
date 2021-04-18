package Service;

import java.io.IOException;

import Dao.DAO;
import Model.X;
import spark.Request;
import spark.Response;


public class XService {

	private DAO XDAO;

	public XService() {
		try {
			XDAO = new DAO();
			XDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id = Integer.parseInt(request.queryParams(":idx"));
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		int idx = XDAO.getMaxId() + 1;

		X x = new X(idx, nome, email, senha);

		XDAO.add(x);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":idx"));
		
		X x = (X) XDAO.get(id);
		
		if (x != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<X>\n" + 
            		"\t<id>" + x.getId() + "</id>\n" +
            		"\t<nome>" + x.getNome() + "</nome>\n" +
            		"\t<email>" + x.getEmail() + "</email>\n" +
            		"\t<senha>" + x.getSenha() + "</senha>\n" +
            		"</X>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuario X " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		X x = (X) XDAO.get(id);

        if (x != null) {
        	x.setId(Integer.parseInt(request.queryParams("id")));
        	x.setNome(request.queryParams("nome"));
        	x.setEmail(request.queryParams("email"));
        	x.setSenha(request.queryParams("senhal"));

        	XDAO.update(x);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        X x = (X) XDAO.get(id);

        if (x != null) {

            XDAO.remove(x.getId());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (X x : XDAO.getAll()) {
			returnValue.append("\n<x>\n" + 
            		"\t<id>" + x.getId() + "</id>\n" +
            		"\t<nome>" + x.getNome() + "</nome>\n" +
            		"\t<email>" + x.getEmail() + "</email>\n" +
            		"\t<senha>" + x.getSenha() + "</senha>\n" +
            		"</x>\n");
		}
		returnValue.append("</x>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}