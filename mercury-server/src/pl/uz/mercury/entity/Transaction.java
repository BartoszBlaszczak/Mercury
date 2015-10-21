package pl.uz.mercury.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import pl.uz.mercury.entity.common.MercuryOptionEntity;

@MappedSuperclass
public abstract class Transaction implements MercuryOptionEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long	id;
	
	@NotNull
	private Date date;

	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	private Merchandise merchandise;
	
	@NotNull
	private Integer quantity;
	
	@NotNull
	private BigDecimal price;
	
	@NotNull
	private BigDecimal cost;

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
	
	public Date getDate ()
	{
		return date;
	}

	public void setDate (Date date)
	{
		this.date = date;
	}
	
	public Merchandise getMerchandise ()
	{
		return merchandise;
	}

	public void setMerchandise (Merchandise merchandise)
	{
		this.merchandise = merchandise;
	}

	public int getQuantity ()
	{
		return quantity;
	}

	public void setQuantity (int quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getPrice ()
	{
		return price;
	}

	public void setPrice (BigDecimal price)
	{
		this.price = price;
	}

	public BigDecimal getCost ()
	{
		return cost;
	}

	public void setCost (BigDecimal cost)
	{
		this.cost = cost;
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
