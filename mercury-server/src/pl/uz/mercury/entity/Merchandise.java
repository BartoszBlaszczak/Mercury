package pl.uz.mercury.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import pl.uz.mercury.entity.common.MercuryOptionEntity;

@Entity
public class Merchandise implements MercuryOptionEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	Idmerchandise;
	private String	name;
	@Transient
	String			transientField;

	public Long getIdmerchandise()
	{
		return Idmerchandise;
	}

	public void setIdmerchandise(Long idmerchandise)
	{
		Idmerchandise = idmerchandise;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTransientField()
	{
		return transientField;
	}

	public void setTransientField(String transientField)
	{
		this.transientField = transientField;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	@Version
	private Long	version;

	@Override
	public Long getId()
	{
		return getIdmerchandise();
	}
}
