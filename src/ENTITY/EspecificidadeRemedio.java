package ENTITY;

import java.util.Date;

public class EspecificidadeRemedio extends Especificidade {
	protected String crm;
	protected Date data;
	public EspecificidadeRemedio() {
		// TODO Auto-generated constructor stub
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}
