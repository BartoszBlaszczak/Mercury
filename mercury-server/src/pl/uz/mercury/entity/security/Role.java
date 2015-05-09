package pl.uz.mercury.entity.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity(name = "APPLICATION_SECURITY_ROLE")
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;
	@ManyToOne
	private Operator	Operator;
	private String		roleName;
	@Version
	private Long		version;

	public Long getId ()
	{
		return id;
	}

	public void setId (Long id)
	{
		this.id = id;
	}

	public Operator getOperator ()
	{
		return Operator;
	}

	public void setOperator (Operator operator)
	{
		Operator = operator;
	}

	public String getRoleName ()
	{
		return roleName;
	}

	public void setRoleName (String roleName)
	{
		this.roleName = roleName;
	}

	public Long getVersion ()
	{
		return version;
	}

	public void setVersion (Long version)
	{
		this.version = version;
	}

}
