package pl.uz.mercury.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import pl.uz.mercury.entity.common.MercuryEntity;

@Entity
public class Merchandise implements MercuryEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;
	
	@Column(unique=true)
	@NotNull
	private String	name;
	
	@NotNull
	private Integer quantity;
	
	@Version
	private Long	version;

	@Override
	public Long getId()
	{
		return id;
	}

	public void setId(Long idmerchandise)
	{
		id = idmerchandise;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getQuantity ()
	{
		return quantity;
	}

	public void setQuantity (Integer quantity)
	{
		this.quantity = quantity;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}
}
