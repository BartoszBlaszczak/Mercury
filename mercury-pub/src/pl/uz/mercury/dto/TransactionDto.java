package pl.uz.mercury.dto;

import java.math.BigDecimal;
import java.util.Date;

import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.util.MercuryDateFormat;

public class TransactionDto implements MercuryOptionDto
{
	private static final long	serialVersionUID	= 1L;
	private static MercuryDateFormat dateFormat = new MercuryDateFormat();

	public static final String DATE = "date";
	public static final String MERCHANDISE = "merchandise";
	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String COST = "cost";
	
	public Long id;
	public Date date;
	public MerchandiseDto merchandiseDto;
	public Integer quantity;
	public BigDecimal price;
	public BigDecimal cost;

	@Override
	public Long getId ()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s: %s %s(%s) %s x %s", id, dateFormat.format(date), merchandiseDto.name, merchandiseDto.id, quantity, price);
	}
}
