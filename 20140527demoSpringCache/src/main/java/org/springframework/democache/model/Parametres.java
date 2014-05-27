package org.springframework.democache.model;

import java.io.Serializable;

public class Parametres implements Serializable{
	
	private Long param1;
	private String param2;
	private String param3;
	
	
	public Long getParam1() {
		return param1;
	}
	public void setParam1(Long param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	
	
	
	
	public Parametres() {
		super();
	}
	public Parametres(Long param1, String param2, String param3) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((param2 == null) ? 0 : param2.hashCode());
		result = prime * result + ((param3 == null) ? 0 : param3.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametres other = (Parametres) obj;
		if (param1 == null) {
			if (other.param1 != null)
				return false;
		} else if (!param1.equals(other.param1))
			return false;
		if (param2 == null) {
			if (other.param2 != null)
				return false;
		} else if (!param2.equals(other.param2))
			return false;
		if (param3 == null) {
			if (other.param3 != null)
				return false;
		} else if (!param3.equals(other.param3))
			return false;
		return true;
	}

	
	

}
