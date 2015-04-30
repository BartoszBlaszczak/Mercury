package pl.uz.mercury.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity(name="APPLICATION_SECURITY_ROLE")
public class ApplicationSecurityRole
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long		id;
	@ManyToOne
	private Operator	Operator;
	private String		role;
	@Version
	private Long		version;

	

}
