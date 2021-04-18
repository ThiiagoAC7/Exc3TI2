package Model;

public class X {
	private int idx;
	private String nome;
	private String email;
	private String senha;
	
	public X() {
		this.idx = -1;
		this.nome = "";
		this.email = "";
		this.senha = "*";
	}
	
	public X(int idx, String nome, String email, String senha) {
		this.idx = idx;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}	

	public int getId() {
		return idx;
	}

	public void setId(int idx) {
		this.idx = idx;
	}

	public String getNome() {
		return senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario [Id=" + idx +", Nome="+nome+ ", Email=" + email + ", senha=" + senha +"]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((X) obj).getId());
	}	
	
}
